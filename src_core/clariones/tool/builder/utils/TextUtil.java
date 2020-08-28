package clariones.tool.builder.utils;

import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class TextUtil {
    public static boolean isBlank(String value){
        if (value == null){
            return true;
        }
        return value.trim().isEmpty();
    }

    public static String message(Object format, Object ... params){
        if (params == null || params.length == 0) {
            return String.valueOf(format);
        }
        if (format instanceof String){
            return String.format((String) format, params);
        }
        List<Object> list = new ArrayList<>();
        list.add(format);
        list.addAll(Arrays.asList(params));
        return list.toString();
    }

    public static String nameAsThis(String inputName) {
        if (isBlank(inputName)){
            return "";
        }
        if (inStyle_nameAsThis(inputName)){
            return uncapFirst(inputName);
        }
        List<String> segments = splitToSegments(inputName, true);
        AtomicBoolean first = new AtomicBoolean(true);
        List<String> results = segments.stream().map(r->first.getAndSet(false)?r:capFirst(r)).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        results.forEach(sb::append);
        return sb.toString();
    }

    public static String NameAsThis(String inputName) {
        if (isBlank(inputName)){
            return "";
        }
        if (inStyle_NameAsThis(inputName)){
            return capFirst(inputName);
        }
        List<String> segments = splitToSegments(inputName, true);
        List<String> results = segments.stream().map(TextUtil::capFirst).collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        results.forEach(sb::append);
        return sb.toString();
    }



    public static String NAME_AS_THIS(String inputName) {
        if (isBlank(inputName)){
            return "";
        }
        if (inStyle_NAME_AS_THIS(inputName)){
            return inputName;
        }
        List<String> segments = splitToSegments(inputName, false);
        List<String> results = segments.stream().map(String::toUpperCase).collect(Collectors.toList());
        return String.join("_", results);
    }

    public static String name_as_this(String inputName) {
        if (isBlank(inputName)){
            return "";
        }
        if (inStyle_name_as_this(inputName)){
            return inputName;
        }
        List<String> segments = splitToSegments(inputName, true);
        return String.join("_", segments);
    }




    protected static List<String> splitToSegments(String inputName, boolean toLower) {
        List<String> segments = Arrays.asList(inputName.split("[ _\\-]"));
        Iterator<String> it = segments.iterator();
        List<String> resultSegs = new ArrayList();
        while(it.hasNext()){
            String seg = it.next();
            if (isBlank(seg)){
                it.remove();
                continue;
            }
            if (toLower) {
                resultSegs.add(seg.trim().toLowerCase());
            }else{
                resultSegs.add(seg.trim());
            }
        }
        return resultSegs;
    }

    public static String capFirst(String trim) {
        if (isBlank(trim)){
            return "";
        }
        char[] chars = trim.toCharArray();
        for(int i=0;i< chars.length; i++){
            if (Character.isWhitespace(chars[i])){
                continue;
            }
            chars[i] = Character.toUpperCase(chars[i]);
            break;
        }
        return new String(chars);
    }

    public static String toJson(Object object, boolean pretty) {
        GsonBuilder gb = new GsonBuilder().disableHtmlEscaping();
        if (pretty) {
            gb.setPrettyPrinting();
        }
        return gb.create().toJson(object);
    }

    protected static boolean inStyle_nameAsThis(String inputName) {
        return inputName.trim().matches("[A-Za-z\\$][^ _\\-]*");
    }
    private static boolean inStyle_NameAsThis(String inputName) {
        return inputName.trim().matches("[A-Za-z\\$][^ _\\-]*");
    }
    private static boolean inStyle_NAME_AS_THIS(String inputName) {
        return inputName.trim().matches("[^a-z \\-]+");
    }
    private static boolean inStyle_name_as_this(String inputName) {
        return inputName.trim().matches("[^A-Z \\-]+");
    }

    public static String makeSqlParamPlaceholder(int n) {
        return repeat("?", n, ",");
    }

    public static String repeat(String repeated, int n, String separator) {
        StringBuilder sb = new StringBuilder();
        for(int i=1;i<n;i++){
            sb.append(repeated).append(separator);
        }
        return sb.append(repeated).toString();
    }

    public static String uncapFirst(String tn) {
        if (tn == null || tn.isEmpty()) {
            return tn;
        }
        return Character.toLowerCase(tn.charAt(0)) + tn.substring(1);
    }

}
