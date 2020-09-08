package cla.bob.pageflow;

import cla.bob.pageflow.generator.PageFlowBobGenerator;
import cla.bob.pageflow.script.PageFlowScriptExample;
import clariones.tool.builder.GenrationResult;

import java.io.File;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {
        PageFlowBobGenerator generator = new PageFlowBobGenerator();
        generator.setScript(new PageFlowScriptExample());
        List<GenrationResult> files = generator.runJob();
        File outputFolder = new File("./output/test_pageflow");
        generator.saveToFiles(outputFolder, files);
    }
}
