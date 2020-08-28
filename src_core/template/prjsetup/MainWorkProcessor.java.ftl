package cla.edg.project.${prjName?lower_case};

import cla.edg.project.${prjName?lower_case}.workprocessor.*;
import cla.poc.workflow.WorkProcessorBuilder;

public class MainWorkProcessor {
    public WorkProcessorBuilder getBuilder() {
        return WorkProcessorBuilder.start()
            .basePackage(Main.TARGET_BASE_PACKAGE_NAME)
            .import_from(new WP01_Example());
    }
}
