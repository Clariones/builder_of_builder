package clariones.tool.bob.example;

import clariones.tool.bob.script.BobScript;

public class ScriptExample {
    public BobScript getScript() {
        return new BobScript()
                .builder_for("test")
                .has_element("change request")
                    .introduce_by("change_request")
                    .has_method("show_in_console")
                ;
    }
}
