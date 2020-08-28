package clariones.tool.bob.generator;

import clariones.tool.bob.script.BobScript;
import clariones.tool.builder.BaseGenerator;
import clariones.tool.builder.GenrationResult;

import java.util.List;

public class BobGenerator extends BaseGenerator {
    public BobScript script;

    public BobScript getScript() {
        return script;
    }

    public void setScript(BobScript script) {
        this.script = script;
    }

    @Override
    public List<GenrationResult> runJob() throws Exception {
        return null;
    }
}
