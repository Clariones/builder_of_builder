package clariones.tool.builder.utils;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class DataTypeUtil {
    public static Integer getInteger(Object value, Integer defaultValue) {
        if (value == null){
            return defaultValue;
        }
        if (value instanceof Number){
            return ((Number) value).intValue();
        }
        if (value instanceof String){
            return Integer.parseInt(value.toString());
        }
        return defaultValue;
    }

    public static Boolean getBoolean(Object value, Boolean defaultValue) {
        if (value == null){
            return defaultValue;
        }
        if (value instanceof Boolean){
            return (Boolean) value;
        }
        if (value instanceof Number){
            return ((Number) value).equals(0);
        }
        if (value instanceof String){
            switch (value.toString().toLowerCase().trim()){
                case "":
                case "0":
                case "false":
                case "no":
                case "not":
                case "n":
                case "fail":
                case "failure":
                    return false;
                case "1":
                case "true":
                case "yes":
                case "ok":
                case "sure":
                case "pass":
                case "success":
                    return true;
                default:
                    break;
            }
        }
        return defaultValue;
    }

    public static String getString(Object value, String defaultValue) {
        if (value == null){
            return defaultValue;
        }
        if (value instanceof String){
            return value.toString();
        }
        return String.valueOf(value);
    }
}
