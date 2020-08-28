package clariones.tool.bob.script;

import java.util.ArrayList;
import java.util.List;

public class ElementSpec {
    public String name;
    public ElementSpec parent;
    public String introduceCommand;
    public List<MethodSpec> methods = new ArrayList<>();
    public MethodSpec curMethod;

    public void addMethod(String methodName) {
        curMethod = new MethodSpec();
        curMethod.name = methodName;
        methods.add(curMethod);
    }
}
