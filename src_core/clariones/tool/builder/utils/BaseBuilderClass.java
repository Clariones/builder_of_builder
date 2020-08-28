package clariones.tool.builder.utils;

import clariones.tool.builder.Utils;

public class BaseBuilderClass {

    //    protected String debug(Object format, Object ... params){
//        String message = Utils.message(format, params);
//        System.out.printf("[%20s, %s] %s\r\n", this.getClass().getSimpleName(), Utils.curTimeStr(), message);
//        return message;
//    }
    protected String debug(Object format, Object... params) {
        return Utils.debug(1, format, params);
    }

    //    protected void error(Object format, Object ... params){
//        String message = debug(format, params);
//        throw new RuntimeException(message);
//    }
    protected void error(Object format, Object... params) {
        Utils.error(1, format, params);
    }
}
