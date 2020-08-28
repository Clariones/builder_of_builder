package clariones.tool.builder.utils;

import java.io.File;
import java.io.FileWriter;

public class FileUtil {
    public static void saveIntoFile(File file, String content) throws Exception {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        try(FileWriter fout = new FileWriter(file)) {
            fout.write(content);
            fout.flush();
            System.out.println("write into " + file.getCanonicalPath());
        }
    }
}
