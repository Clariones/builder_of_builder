package clariones.tool.bob.script;

public enum BaseType {
    STRING("string")
    ;


    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private BaseType(String name){
        setName(name);
    }
}
