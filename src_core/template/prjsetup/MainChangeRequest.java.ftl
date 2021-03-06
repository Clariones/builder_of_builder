package cla.edg.project.${prjName?lower_case};

import cla.edg.project.${prjName?lower_case}.changerequest.*;
import com.terapico.changerequest.builder.ChangeRequestSpecBuilder;
import com.terapico.changerequest.builder.ChangeRequestSpecFactory;

import java.util.Map;

public class MainChangeRequest implements ChangeRequestSpecFactory {

    @Override
    public Map<String, Map<String, Object>> getSpec() {
        return getScript();
    }

    private Map<String, Map<String, Object>> getScript() {
        return ChangeRequestSpecBuilder.for_project(Main.TARGET_PROJECT_NAME)
                .request_base("you_should_handle_CR_here")

                .import_from(new CR01_Example())

                .getChangeRequestSpec();
    }
}
