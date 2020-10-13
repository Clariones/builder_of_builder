package clariones.tool.builder.utils;

import clariones.tool.builder.Utils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtil {
    public static void saveIntoFile(File file, String content) throws Exception {
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        try(FileWriter fout = new FileWriter(file)) {
            fout.write(content);
            fout.flush();
            Utils.debug("write into " + file.getCanonicalPath());
        }
    }

    public static String readFileAsString(File file, Charset charset){
        if (!file.exists()){
            throw new RuntimeException("file " + file.getAbsolutePath()+" not exists");
        }
        try (FileInputStream fin = new FileInputStream(file)){
            byte[] content = readAsBytes(fin);
            return new String(content, charset);
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
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

    public static File findLatestMatchedFile(File checkingFile, File currentFile, String fileNamePattern) {
        if (checkingFile.isFile()){
            if (currentFile != null) {
                if (checkingFile.lastModified() < currentFile.lastModified()){
                    return currentFile;
                }
            }
            Pattern ptn = Pattern.compile(fileNamePattern);
            Matcher m = ptn.matcher(checkingFile.getAbsolutePath());
            if (m.find()){
                return checkingFile;
            }
            return currentFile;
        }
        // 是个文件夹
        File[] files = checkingFile.listFiles();
        if (files == null || files.length == 0){
            return currentFile;
        }
        for (File file : files) {
            currentFile = findLatestMatchedFile(file, currentFile, fileNamePattern);
        }
        return currentFile;
    }
}
