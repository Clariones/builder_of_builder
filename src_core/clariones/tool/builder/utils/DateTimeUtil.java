package clariones.tool.builder.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtil {
    public static String curTimeStr() {
        return format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    public static String format(Date date, String format) {
        if (format == null){
            format = "yyyy-MM-dd HH:mm:ss z";
        }
        return new SimpleDateFormat(format).format(date);
    }
}
