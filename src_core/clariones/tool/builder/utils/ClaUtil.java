package clariones.tool.builder.utils;


import clariones.tool.builder.Utils;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.zip.*;

public class ClaUtil{
    /**
     *
     * @param inputByte
     *      待解压缩的字节数组
     * @return 解压缩后的字节数组
     * @throws IOException
     */
    public static byte[] uncompress(byte[] inputByte) throws IOException {
        int len = 0;
        Inflater infl = new Inflater();
        infl.setInput(inputByte);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] outByte = new byte[1024];
        try {
            while (!infl.finished()) {
                // 解压缩并将解压缩后的内容输出到字节输出流bos中
                len = infl.inflate(outByte);
                if (len == 0) {
                    break;
                }
                bos.write(outByte, 0, len);
            }
            infl.end();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }


    /**
     * 压缩.
     *
     * @param inputByte
     *      待压缩的字节数组
     * @return 压缩后的数据
     * @throws IOException
     */
    public static byte[] compress(byte[] inputByte) throws IOException {
        int len = 0;
        Deflater defl = new Deflater();
        defl.setInput(inputByte);
        defl.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] outputByte = new byte[1024];
        try {
            while (!defl.finished()) {
                // 压缩并将压缩后的内容输出到字节输出流bos中
                len = defl.deflate(outputByte);
                bos.write(outputByte, 0, len);
            }
            defl.end();
        } finally {
            bos.close();
        }
        return bos.toByteArray();
    }

    private static byte[] zipText(byte[] licenseText) throws IOException {
        int len;
        byte[] buff = new byte[64];
        ByteArrayInputStream in = new ByteArrayInputStream(licenseText);
        DeflaterInputStream deflater = new DeflaterInputStream(in, new Deflater());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            while ((len = deflater.read(buff)) > 0) {
                out.write(buff, 0, len);
            }

            return out.toByteArray();
        } finally {
            out.close();
            deflater.close();
            in.close();
        }
    }

    public static byte[] compressZlib(byte[] data) {
        byte[] output = new byte[0];

        Deflater compresser = new Deflater();

        compresser.reset();
        compresser.setInput(data);
        compresser.finish();
        ByteArrayOutputStream bos = new ByteArrayOutputStream(data.length);
        try {
            byte[] buf = new byte[1024];
            while (!compresser.finished()) {
                int i = compresser.deflate(buf);
                bos.write(buf, 0, i);
            }
            output = bos.toByteArray();
        } catch (Exception e) {
            output = data;
            e.printStackTrace();
        } finally {
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        compresser.end();
        return output;
    }

    public static byte[] compressGzip(byte[] input) throws IOException {
//        if (null == str || str.length() <= 0) {
//            return null;
//        }
        // 创建一个新的 byte 数组输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        // 使用默认缓冲区大小创建新的输出流
        GZIPOutputStream gzip = new GZIPOutputStream(out);
        // 将 b.length 个字节写入此输出流
        gzip.write(input);
        gzip.close();
        // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
        //return out.toString("ISO-8859-1");
        // return out.toString("UTF-8");
        return out.toByteArray();
    }

    public static String unCompressGZip(byte[] b) {
        try {
            if (null == b || b.length <= 0) {
                return null;
            }
            // 创建一个新的 byte 数组输出流
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 创建一个 ByteArrayInputStream，使用 buf 作为其缓冲区数组
            ByteArrayInputStream in;
            in = new ByteArrayInputStream(b);

            // 使用默认缓冲区大小创建新的输入流
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[256];
            int n = 0;
            while ((n = gzip.read(buffer)) >= 0) {// 将未压缩数据读入字节数组
                // 将指定 byte 数组中从偏移量 off 开始的 len 个字节写入此 byte数组输出流
                out.write(buffer, 0, n);
            }
            // 使用指定的 charsetName，通过解码字节将缓冲区内容转换为字符串
            return out.toString("UTF-8");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) throws Exception{
//        String input = "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";
//
//        byte[] x = Base64.getDecoder().decode(input.getBytes());
//        System.out.println("x=\r\n"+new String(x));
//
//        String y = unCompressGZip(x);
//        System.out.println("y=\r\n"+y);
//        case2();
        case3();
    }

    private static void case3() throws Exception{
        String input="7VrLctowFP0aLZuxLT+XhDhtZtImM1k07aajYGErEZZHyDzy9ZVs2WAUKJ1pMIVkE92jx+We+5INAA4ni88cFdlXlmAKHCtZAHgFHMe2Q1/+U8iyRgLHq4GUk0QvWgEP5BVr0NJoSRI87SwUjFFBii44YnmOR6KDIc7ZvLtszGhXa4FSbAAPI0RN9DtJRFajoROs8C+YpFmj2fajeuYJjV5Szspc6wMOvK7+6umpWDYq0GvJcY1OUKNBmz/NUMLmaxCMARxyxkQ9miyGmCrGGzLrfddbZltrOM7FPhtuvNdfL4OQL6ybnzfeLLy85d8+Nb6ZIVrixroNo3AiadQi4yJjKcsRjVfo5ajkM6zU2FJYLbllrNDgMxZiqUMClYJJKBMTqmenAnExUC6WQM5ydWb9MZTurfZqaMpKPsI7jNQmSSUpFjvWua1XZA5gNsGCL+U+jikSZNb9HEgHY9quW1EvB5r9v/GE1wPxeEHEoxxbevxjbXylEj9sJZX0vqele8yJNBvzZn0uKXhcF6qTLrxGrA5rJ6vDWmnzsCOJBq/XaPA/ouGoosHvMxoco0jf20Z8yP5S4IojoTiaZ0TghwJV1s9lR+/6etXT7kpBiaK1whPEX+7kLiIqr1xYymljQumQUcYrTTB5wmg8ljiiJM0lNpK0K4dpx8wwF3ix2zUmlXqDrzuSvmSEWpyvdWwNZevN2rLei3vLYPpwmWhvy8ROIv5vOQf3zDk76jXpzBTrx/H2+Tk+7NPx0Ki2/I1QoFQ+t+xRaNG0qB9mxmShIuOgxRS63WrqQs8op/CNcgrfq5raZis7vecNd98w3+K8w4S5a3oijsDABpcRiAMQXYJBDGIPhCGIAhCHamogE9OnQt0fuBylalRw9qwi3LEomYpTvZa0bzGO5V5iw48nhIM0Lbt5V/WndO61adnuRzgcVzgEvYaDvVd1l0X9Ggx8EPsghCD0dlX3BAtE6KnWdxgcWX33DAcW5juhEyF/s7m298/+umtwBvdUf89KBvusZL6ZB+G55IFr9Z0HZhoU5vcUJ8K+B7vs21Hf7IcG+/fmNe9E2Xf7Jt822S+iU2Xf9TbYD3qnPzLpD86FfvjGe7p/Rb8UVz86qObWfu8B498=";
        byte[] base64Decodeed = Base64.getDecoder().decode(input);
        System.out.println("base64Decoded: " + base64Decodeed.length +" bytes");
        System.out.println("" + new String(base64Decodeed, StandardCharsets.ISO_8859_1));
        showBytes(base64Decodeed);
        showBytes2(base64Decodeed);
        FileOutputStream fout = new FileOutputStream("./output/test.zip");
        fout.write(base64Decodeed);
        fout.close();


        byte[] inflateArray = decompress(base64Decodeed);
        System.out.println("inflateArray: " + inflateArray.length +" bytes");
        System.out.println("" + new String(inflateArray, "UTF-8"));

        String urlDecoded = URLDecoder.decode(new String(inflateArray, "UTF-8"), "UTF-8");
        System.out.println("urlDecoded: " + urlDecoded);

        LightXmlData xmlData = new LightXmlData.Builder().fromXmlString(urlDecoded);
        System.out.print(Utils.toJson(xmlData, true));
    }

    public static byte[] uncompress3(byte[] input) throws IOException {
        int len = input.length;
//        byte[] out = new byte[len];
        byte[] src = new byte[len + 6];
        System.arraycopy(input, 0, src, 2, len);
        src[0] = (byte) (0x1f);
        src[1] = (byte) (0x8b);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ByteArrayInputStream in = new ByteArrayInputStream(src);
        GZIPInputStream gunzip = new GZIPInputStream(in);
        byte[] buffer = new byte[256];
        int n;
        while ((n = gunzip.read(buffer)) >= 0) {
            out.write(buffer, 0, n);
        }
        // toString()使用平台默认编码，也可以显式的指定如toString("GBK")
        return out.toByteArray();
    }


    public static byte[] decompress(byte[] input) throws DataFormatException {
        int len = input.length;
        byte[] out = new byte[len];
        byte[] src = new byte[len + 6];
        System.arraycopy(input, 0, src, 2, len);
        src[0] = (byte) (120);
        src[1] = (byte) (-100);


        Inflater decompresser = new Inflater(true);
        byte[] output =  new byte[10240];
        decompresser.setInput(input, 0, input.length);
        byte[] result = new byte[10240];
        int resultLength = decompresser.inflate(result);
        decompresser.end();
        return Arrays.copyOf(result,  resultLength);
    }

    private static void case1() {
        try {
            File file = new File("./testdeflate.txt");
            System.out.println(file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            int len = fis.available();
            byte[] b = new byte[len];
            fis.read(b);

            byte[] bd = compress(URLEncoder.encode(new String(b), "UTF-8").getBytes());
            // 为了压缩后的内容能够在网络上传输，一般采用Base64编码
            String encodestr = Base64.getEncoder().encodeToString(bd); //Base64.encodeBase64String(bd);
            System.out.println(encodestr);
            byte[] bi = uncompress(Base64.getDecoder().decode(encodestr));     // Base64.decodeBase64(encodestr));
            FileOutputStream fos = new FileOutputStream("./testinflate.txt");
            fos.write(bi);
            fos.flush();
            fos.close();
            fis.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void case2() {
        try {
            File file = new File("./testdeflate.txt");
            System.out.println(file.getAbsolutePath());
            FileInputStream fis = new FileInputStream(file);
            int len = fis.available();
            byte[] b = new byte[len];
            fis.read(b);

            String inputStr = new String(b, "UTF-8");
            System.out.println("inputStr=\r\n" + inputStr);

            String urlEncoded = URLEncoder.encode(inputStr, StandardCharsets.UTF_8.displayName());
            System.out.println("urlEncoded=\r\n" + urlEncoded);

            byte[] compressed = compressZlib(urlEncoded.getBytes());//StandardCharsets.UTF_8));
            System.out.println("compressed=\r\n" + new String(compressed, StandardCharsets.UTF_8));
            showBytes(compressed);

            String base64ed = Base64.getEncoder().encodeToString(compressed);
            System.out.println("base64ed=\r\n" + base64ed);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showBytes(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
//            if ((b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z') || (b >= '0' && b <= '9')){
//                sb.append((char)b);
//                continue;
//            }
            sb.append(String.format("%03d ",(b & 0xFF)));
        }
        System.out.println(sb.toString());
    }

    private static void showBytes2(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
//            if ((b >= 'a' && b <= 'z') || (b >= 'A' && b <= 'Z') || (b >= '0' && b <= '9')){
//                sb.append((char)b);
//                continue;
//            }
            sb.append(String.format("%02X ",(b & 0xFF)));
        }
        System.out.println(sb.toString());
    }

    public static String compressAndEncodeString(String str) {
        DeflaterOutputStream def = null;
        String compressed = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // create deflater without header
            def = new DeflaterOutputStream(out, new Deflater(Deflater.BEST_COMPRESSION, true));
            def.write(str.getBytes());
            def.close();
            compressed = new String(Base64.getDecoder().decode(out.toByteArray()));
        } catch(Exception e) {
            //Log.e(TAG, "could not compress data: " + e);
            e.printStackTrace();
        }
        return compressed;
    }
}
