package clariones.tool.builder.utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

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

    public static String readFileAsString(File file, Charset charset) throws Exception{
        if (!file.exists()){
            throw new IOException("file " + file.getAbsolutePath()+" not exists");
        }
        try (FileInputStream fin = new FileInputStream(file)){
            byte[] content = readAsBytes(fin);
            return new String(content, charset);
        }
    }

    public static byte[] readAsBytes(InputStream fin) throws IOException {
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        byte[] buf = new byte[10240];
        while(fin.available() > 0){
            int size = fin.read(buf);
            bout.write(buf,0,size);
        }
        return bout.toByteArray();
    }
}
