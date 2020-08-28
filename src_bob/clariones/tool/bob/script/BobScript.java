package clariones.tool.bob.script;

import java.util.HashMap;
import java.util.Map;

public class BobScript {
    public String name;
    public ElementSpec curSpec;
    public Map<String, ElementSpec> allElements = new HashMap<>();

    public BobScript builder_for(String name) {
        this.name = name;
        return this;
    }

    public BobScript has_element(String elementName) {
        curSpec = new ElementSpec();
        curSpec.name = elementName;
        ElementSpec existed = allElements.put(elementName, curSpec);
        if (existed != null){
            error(elementName + " 已经被定义" );
        }
        return this;
    }

    private void error(String message) {
        throw new RuntimeException(message);
    }

    public BobScript introduce_by(String introduceCommand) {
        curSpec.introduceCommand = introduceCommand;
        return this;
    }

    public BobScript has_method(String methodName) {
        curSpec.addMethod(methodName);
        return this;
    }
}
