package setup;

import clariones.tool.builder.GenrationResult;
import clariones.tool.builder.Utils;

import java.io.File;
import java.util.List;

public class SetUpMain {

    public static final String BASE = "/works/jobs/repairchain_v1/workspace/";

    public static void main(String[] args) throws Exception {
        ProjectSetup setup = new ProjectSetup();
        setup.setProjectFolder(BASE);

        setup.setPackageName("com.doublechaintech.repairchain");
        List<GenrationResult> list = setup.runJob();

        // String baseFolder = "/works/jobs/saleschain_v1/workspace/code-gen-client";
        String baseFolder = BASE+"/code-gen-client/project_repairchain";
        setup.saveToFiles( Utils.put("ALL", new File(baseFolder)).into_map(File.class), list); // "changeReque
    }

}
