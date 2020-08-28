package clariones.tool.bob.example;

import clariones.tool.bob.generator.BobGenerator;

public class Main {
    public static void main(String[] args) throws Exception {
        BobGenerator generator = new BobGenerator();
        generator.setScript(new ScriptExample().getScript());
    }
}
