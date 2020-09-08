package cla.bob.pageflow.generator;

import cla.bob.pageflow.script.PageFlowScriptExample;
import clariones.tool.builder.BaseGenerator;
import clariones.tool.builder.GenrationResult;
import clariones.tool.builder.Utils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class PageFlowBobGenerator extends BaseGenerator {
    @Override
    public List<GenrationResult> runJob() throws Exception {
        List<GenrationResult> resultList = new ArrayList<>();
        resultList.add(generateCustomClient());
        resultList.add(generateBaseClient());
        resultList.add(generateServiceInterface());
        resultList.add(generateService());
        resultList.addAll(generateSpec());
        resultList.addAll(generateTemplates());
        resultList.add(generateBaseGenerator());
        resultList.add(generateCustomGenerator());
        return resultList;
    }

    private GenrationResult generateBaseGenerator() throws Exception {
        String keyName = "baseGenerator.java";
        String fileNamePattern = "cla/edg/${helper.nameAsThis(script.name)?lower_case}/generator/Base${helper.NameAsThis(script.name)}Generator.java";
        boolean createNewFile = true;

        return doGenerateWork(keyName, fileNamePattern, createNewFile);
    }



    private GenrationResult generateCustomGenerator() throws Exception {
        String keyName = "customGenerator.java";
        String fileNamePattern = "cla/edg/${helper.nameAsThis(script.name)?lower_case}/generator/${helper.NameAsThis(script.name)}Generator.java";
        boolean createNewFile = false;

        return doGenerateWork(keyName, fileNamePattern, createNewFile);
    }

    private List<? extends GenrationResult> generateTemplates() {
        return new LinkedList<>();
    }

    private List<? extends GenrationResult> generateSpec() {
        return new LinkedList<>();
    }

    private GenrationResult generateServiceInterface() throws Exception {
        String keyName = "ServiceInterface.java";
        String fileNamePattern = "cla/edg/${helper.nameAsThis(script.name)?lower_case}/${helper.NameAsThis(script.name)}Service.java";
        boolean createNewFile = false;

        return doGenerateWork(keyName, fileNamePattern, createNewFile);
    }

    private GenrationResult generateService() throws Exception {
        String keyName = "ServiceLocalImpl.java";
        String fileNamePattern = "cla/edg/${helper.nameAsThis(script.name)?lower_case}/${helper.NameAsThis(script.name)}ServiceImpl.java";
        boolean createNewFile = false;

        return doGenerateWork(keyName, fileNamePattern, createNewFile);
    }

    private GenrationResult generateBaseClient() throws Exception {
        String keyName = "BaseBuilder.java";
        String fileNamePattern = "cla/edg/${helper.nameAsThis(script.name)?lower_case}/Base${helper.NameAsThis(script.name)}Builder.java";
        boolean createNewFile = true;

        return doGenerateWork(keyName, fileNamePattern, createNewFile);
    }

    private GenrationResult generateCustomClient() throws Exception {
        String keyName = "CustomBuilder.java";
        String fileNamePattern = "cla/edg/${helper.nameAsThis(script.name)?lower_case}/${helper.NameAsThis(script.name)}Builder.java";
        boolean createNewFile = false;

        return doGenerateWork(keyName, fileNamePattern, createNewFile);
    }

    protected PageFlowScriptExample script;

    public PageFlowScriptExample getScript() {
        return script;
    }

    public void setScript(PageFlowScriptExample script) {
        this.script = script;
    }

    private GenrationResult doGenerateWork(String keyName, String fileNamePattern, boolean createNewFile) throws Exception {
        Map<String, Object> data = Utils.put("script", getScript())
                .put("helper", new GeneratorHelper())
                .into_map();
        String templateName = String.format("pageflowbob/%s.flt", keyName);
        String fileName = toFileName(data, fileNamePattern);
        GenrationResult result = doGeneration(data, templateName, fileName).with_code(keyName);
        result.setActionCode(createNewFile ? GenrationResult.ACTION_REPLACE : GenrationResult.ACTION_CREATE_WHEN_NEED);
        return result;
    }
}
