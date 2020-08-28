package clariones.tool.builder;

import clariones.tool.builder.utils.*;

import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static boolean isBlank(String inputName) {
        return TextUtil.isBlank(inputName);
    }

    public static String nameAsThis(String inputName) {
        return TextUtil.nameAsThis(inputName);
    }

    public static String NameAsThis(String inputName) {
        return TextUtil.NameAsThis(inputName);
    }

    public static String NAME_AS_THIS(String inputName) {
        return TextUtil.NAME_AS_THIS(inputName);
    }

    public static String name_as_this(String inputName) {
        return TextUtil.name_as_this(inputName);
    }

    public static String message(Object format, Object... params) {
        return TextUtil.message(format, params);
    }

    public static String toJson(Object object, boolean pretty) {
        return TextUtil.toJson(object, pretty);
    }

    public static void saveIntoFile(File tgtFile, String content) throws Exception {
        FileUtil.saveIntoFile(tgtFile, content);
    }

    public static MapUtil.MapBuilder put(String key, Object value) {
        return MapUtil.put(key, value);
    }

    public static MapUtil.MapBuilder putIf(String key, Object value) {
        return MapUtil.putIf(key, value);
    }

    public static MapUtil.MapBuilder putIf(boolean condition, String key, Object value) {
        return MapUtil.putIf(condition, key, value);
    }

    public static String curTimeStr() {
        return DateTimeUtil.curTimeStr();
    }

    public static String debug(Object format, Object... params) {
        return debug(1, format, params);
    }

    public static String debug(int uplevel, Object format, Object... params) {
        StackTraceElement st = new Throwable().getStackTrace()[1 + uplevel];
        String causeClass = st.getClassName();
        String message = Utils.message(format, params);
        System.out.printf("[%20s-%-4d, %s] %s\r\n",
                causeClass.substring(causeClass.lastIndexOf(".") + 1), st.getLineNumber(),
                Utils.curTimeStr(), message);
        return message;
    }

    public static void error(Object format, Object... params) {
        error(2, format, params);
    }

    public static void error(int skipStacks, Object format, Object... params) {
        String message = debug(skipStacks, format, params);
        throw new RuntimeException(message);
    }

    public static void assertSize(Collection<?> collection, int size) {
        CollectionUtil.assertSize(collection, size);
    }

    public static String makeSqlParamPlaceholder(int n) {
        return TextUtil.makeSqlParamPlaceholder(n);
    }

    public static String format(Date date, String format) {
        return DateTimeUtil.format(date, format);
    }

    public static String toCamelCase(String inputName) {
        return NameAsThis(inputName);
    }

    public static String toJavaConstStyle(String inputName) {
        return NAME_AS_THIS(inputName);
    }


    public static String removeSeperator(String name) {
        return name.replace("_", " ");
    }

    public static String packageNameToPath(String packageName) {
        return packageName.replace('.', '/');
    }

    public static String toJavaVariableName(String paramName) {
        return nameAsThis(paramName);
    }

    public static String uncapFirst(String tn) {
        return TextUtil.uncapFirst(tn);
    }

    public static String capFirst(String tn) {
        return TextUtil.capFirst(tn);
    }

    public static String getClassNameFromFullName(String typeClassName) {
        int pos = typeClassName.lastIndexOf('.');
        if (pos > 0) {
            return typeClassName.substring(pos + 1);
        }
        return typeClassName;
    }

    public static <T> List<T> toList(T... objs) {
        return new ArrayList<T>(Arrays.asList(objs));
    }

    public static List<String> findAllMatched(String source, Pattern pattern) {
        Matcher matcher = pattern.matcher(source);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            list.add(matcher.group());
        }
        return list;

    }

    public static String repeat(int time) {
        return repeat("?", ",", time);
    }

    public static String repeat(String text, String seperator, int times) {
        StringBuilder sb = new StringBuilder();
        while (times-- > 0) {
            sb.append(text);
            if (times > 0) {
                sb.append(seperator);
            }
        }
        return sb.toString();
    }

    public static List<Object> convertToList(Object data) {
        if (data == null) {
            return new ArrayList<>();
        }
        if (data instanceof Collection) {
            return new ArrayList<>((Collection) data);
        }
        if (data.getClass().isArray()) {
            return new ArrayList<>(Arrays.asList((Object[]) data));
        }
        return new ArrayList<>(Arrays.asList(data));
    }

    protected static final Map<String, List<Object>> additionalRecord = new HashMap<>();

    public static boolean record(String groupName, Object data) {
        List<Object> list = additionalRecord.get(groupName);
        if (list == null) {
            list = new ArrayList<>();
            additionalRecord.put(groupName, list);
        }
        if (list.contains(data)) {
            return false;
        }
        list.add(data);
        return true;
    }

    protected static Pattern ptnStrConst = Pattern.compile("\\$\\{\\'(.*)\\'\\}");
    protected static Pattern ptnVarConst = Pattern.compile("\\$\\{(.*)\\}");

    public static String asELVariable(String exp) {
        Matcher m;
        m = ptnStrConst.matcher(exp);
        if (m.matches()) {
            return toJson(m.group(1), false);
        }

        m = ptnVarConst.matcher(exp);
        if (m.matches()) {
            return toJavaVariableName(m.group(1));
        }

        return exp;
    }

    public static boolean isElVariable(String exp) {
        Matcher m;
        m = ptnStrConst.matcher(exp);
        if (m.matches()) {
            return false;
        }

        m = ptnVarConst.matcher(exp);
        if (m.matches()) {
            return true;
        }

        return false;
    }

    public static String toModelName(String name) {
        return name.trim().replace(' ', '_').toLowerCase();
    }

    public static String toWords(String name) {
        return name.trim().toLowerCase().replace('_', ' ');
    }

}
