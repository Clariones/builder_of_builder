package clariones.tool.builder;

import cla.edg.page.PageBuilder;
import cla.edg.pageflow.BasePageFlowScript;
import cla.edg.pageflow.PageFlowScript;
import cla.edg.pageflow.QueryInfo;
import clariones.tool.builder.utils.InternalNaming;
import freemarker.cache.StringTemplateLoader;
import freemarker.core.ParseException;
import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class BaseGenerator {
    protected String basePackageName;
    protected String projectName;
    protected String baseOutputFolder;

    public String getBaseOutputFolder() {
        return baseOutputFolder;
    }

    public void setBaseOutputFolder(String baseOutputFolder) {
        this.baseOutputFolder = baseOutputFolder;
    }

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    protected File getBaseOutputFolderFile() {
        return new File(baseOutputFolder);
    }
    public abstract List<GenrationResult> runJob() throws Exception;

    public String toFileName(Map<String, Object> data, String fileNameTemplate) throws Exception {
        StringWriter out = renderStringTemplate(data, fileNameTemplate);
        return out.toString();
    }

    protected StringWriter renderStringTemplate(Map<String, Object> data, String strTemplate)
            throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException,
            TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        stringLoader.putTemplate("SimpleStringTemplate", strTemplate);
        cfg.setTemplateLoader(stringLoader);

        Template template = cfg.getTemplate("SimpleStringTemplate");
        StringWriter out = new StringWriter();
        template.process(data, out);
        return out;
    }

    protected GenrationResult doGeneration(Map<String, Object> data, String templatePath, String fileName) throws Exception {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_28);
        cfg.setClassForTemplateLoading(this.getClass(), "/template");
        cfg.setDefaultEncoding("UTF-8");
        // cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        // cfg.setLogTemplateExceptions(false);
        // cfg.setWrapUncheckedExceptions(true);
        // cfg.setFallbackOnNullLoopVariable(false);
        Template template = cfg.getTemplate(templatePath);
        StringWriter out = new StringWriter();
        template.process(data, out);

        GenrationResult result = new GenrationResult();
        result.setFileName(fileName);
        result.setContent(out.toString());
        return result;
    }

    protected void saveFiles(Map<String, File> baseFolders, List<GenrationResult> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        list.forEach(it -> {
            if (it == null){
                return;
            }
            try {
                File baseFolder = findBaseFolder(it.getContentCode(), baseFolders);
                File tgtFile = new File(baseFolder, it.getFileName());
                boolean fileExisted = tgtFile.exists();
                switch (it.getActionCode()) {
                    case GenrationResult.ACTION_REPLACE:
                        Utils.saveIntoFile(tgtFile, it.getContent());
                        return;
                    case GenrationResult.ACTION_CREATE_WHEN_NEED:
                        if (fileExisted) {
                            Utils.debug(" : skip " + it.getFileName());
                            return;
                        }
                        Utils.saveIntoFile(tgtFile, it.getContent());
                        return;
                    default:
                        Utils.debug(" : you need copy below content and handle it yourself\r\n " + it.getContent());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    protected File findBaseFolder(String contentCode, Map<String, File> baseFolders) {
        // first, find by exactly name
        if (baseFolders.containsKey(contentCode)) {
            return baseFolders.get(contentCode);
        }
        // next, find by patter
        Iterator<Map.Entry<String, File>> it = baseFolders.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, File> ent = it.next();
            String codePattern = ent.getKey();
            if (contentCode.matches(codePattern)) {
                return ent.getValue();
            }
        }
        // finally, return the only one, or the ALL one
        if (baseFolders.containsKey("ALL")) {
            return baseFolders.get("ALL");
        }
        return baseFolders.values().iterator().next();
    }

    public void saveToFiles(File baseFolder, List<GenrationResult> resultList) {
        saveFiles(Utils.put("ALL", baseFolder).into_map(File.class), resultList);
    }

    public void saveToFiles(Map<String, File> baseFolders, List<GenrationResult> resultList) {
        saveFiles(baseFolders, resultList);
    }


    protected Map<String, Object> prepareData(Object script, String packageName, String className) {
        Map<String, Object> data = new HashMap<>();
        data.put("base_package", basePackageName);
        data.put("package", packageName);
        data.put("project_name", projectName);
        data.put("class_name", className);
        data.put("context_name", Utils.toCamelCase(projectName)+"UserContext");
        data.put("custom_context_name", "Custom"+ Utils.toCamelCase(projectName)+"UserContextImpl");
        data.put("script", script);
        data.put("NAMING", new Utils());
        return data;
    }

    protected void verifyPageBuilder(PageFlowScript script, PageBuilder pageBuilder) {
        verifyPageBuilderQueryExists(script, pageBuilder);
        verifyPageBuilderRequestExists(script, pageBuilder.getItemRequestName());
        verifyPageBuilderRequestExists(script, pageBuilder.getNextPageRequestName());
    }

    protected void verifyPageBuilderRequestExists(PageFlowScript script, String reqName) {
       boolean exists = script.getRequests().stream().anyMatch(it->it.getName().equals(reqName));
       if (exists){
           return;
       }
       throw new RuntimeException("声明的查询\""+reqName+"\"不存在");
    }

    protected void verifyPageBuilderQueryExists(PageFlowScript script, PageBuilder pageBuilder) {
        if (pageBuilder.getQueryTargetBean() == null){
            return;
        }
        if (pageBuilder.getQueryListName() == null){
            throw new RuntimeException("查询必须声明目标类型和名称,否则不能区分("+pageBuilder.getDeclaredPosition()+")");
        }
        String targetQueryName = InternalNaming.makeQueryName(pageBuilder.getQueryTargetBean(), pageBuilder.getQueryListName());
        QueryInfo queryInfo = script.findQueryByName(targetQueryName);
        if (queryInfo == null) {
            throw new RuntimeException("声明的查询未定义("+pageBuilder.getDeclaredPosition()+")");
        }
        pageBuilder.setRelatedQuery(queryInfo);
    }
}
