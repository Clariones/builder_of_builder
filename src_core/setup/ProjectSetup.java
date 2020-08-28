package setup;



import clariones.tool.builder.BaseGenerator;
import clariones.tool.builder.BaseGeneratorHelper;
import clariones.tool.builder.GenrationResult;
import clariones.tool.builder.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 使用例子:
 * <pre><code>
 *     public static final String BASE = "/works/jobs/saleschain_v1/workspace";
 *
 *     public static void main(String[] args) throws Exception {
 *         ProjectSetup setup = new ProjectSetup();
 *         setup.setProjectFolder(BASE);
 *
 *         setup.setPackageName("com.doublechaintech.saleschain");
 *         List<GenrationResult> list = setup.runJob();
 *
 *         // String baseFolder = "/works/jobs/saleschain_v1/workspace/code-gen-client";
 *         String baseFolder = BASE+"/code-gen-client";
 *         setup.saveToFiles( Utils.put("ALL", new File(baseFolder)).into_map(File.class), list); // "changeReque
 *     }
 * </code></pre>
 */
public class ProjectSetup extends BaseGenerator {
    protected String packageName;
    protected String projectFolder;

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProjectFolder() {
        return projectFolder;
    }

    public void setProjectFolder(String projectFolder) {
        this.projectFolder = projectFolder;
    }

    @Override
    public List<GenrationResult> runJob() throws Exception {
        List<GenrationResult> result = new ArrayList<>();
        result.add(generateMain());
        result.add(generateMainChangeRequest());
        result.add(generateExampleChangeRequest());
        result.add(generateMainPageFlow());
        result.add(generateExamplePageFlow());
        result.add(generateMainWorkProcessor());
        result.add(generateExampleWorkProcessor());
        return result;
    }




    private Map<String, Object> getData() {
        return Utils.put("orgName", packageName.split("\\.")[1])
                .put("prjName", packageName.split("\\.")[2])
                .put("prjFolder", projectFolder)
                .put("helper", new BaseGeneratorHelper())
                .into_map()
                ;
    }
    private GenrationResult generateMain() throws Exception {
        String fileName = this.toFileName(getData(),
                "cla/edg/project/${prjName?lower_case}/Main.java");
        return doGeneration(getData(), "prjsetup/Main.java.ftl", fileName).when_not_exist().with_code("Main.java");
    }

    private GenrationResult generateExampleWorkProcessor() throws Exception{
        String fileName = this.toFileName(getData(),
                "cla/edg/project/${prjName?lower_case}/workprocessor/WP01_Example.java");
        return doGeneration(getData(), "prjsetup/ExampleProcessor.java.ftl", fileName).when_not_exist().with_code("ExampleProcessor.java");
    }
    private GenrationResult generateMainWorkProcessor() throws Exception{
        String fileName = this.toFileName(getData(),
                "cla/edg/project/${prjName?lower_case}/MainWorkProcessor.java");
        return doGeneration(getData(), "prjsetup/MainWorkProcessor.java.ftl", fileName).when_not_exist().with_code("MainWorkProcessor.java");
    }

    private GenrationResult generateExamplePageFlow() throws Exception{
        String fileName = this.toFileName(getData(),
                "cla/edg/project/${prjName?lower_case}/pageflow/P01_Example.java");
        return doGeneration(getData(), "prjsetup/ExamplePageFlow.java.ftl", fileName).when_not_exist().with_code("ExamplePageFlow.java");
    }

    private GenrationResult generateMainPageFlow() throws Exception{
        String fileName = this.toFileName(getData(),
                "cla/edg/project/${prjName?lower_case}/MainPageFlow.java");
        return doGeneration(getData(), "prjsetup/MainPageFlow.java.ftl", fileName).when_not_exist().with_code("MainPageFlow.java");
    }

    private GenrationResult generateExampleChangeRequest() throws Exception{
        String fileName = this.toFileName(getData(),
                "cla/edg/project/${prjName?lower_case}/changerequest/CR01_Example.java");
        return doGeneration(getData(), "prjsetup/ExampleCR.java.ftl", fileName).when_not_exist().with_code("ExampleCR.java");
    }

    private GenrationResult generateMainChangeRequest() throws Exception{
        String fileName = this.toFileName(getData(),
                "cla/edg/project/${prjName?lower_case}/MainChangeRequest.java");
        return doGeneration(getData(), "prjsetup/MainChangeRequest.java.ftl", fileName).when_not_exist().with_code("MainChangeRequest.java");
    }


}
