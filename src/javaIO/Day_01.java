package javaIO;

import java.io.*;
import java.util.Date;
import java.util.Random;

/**
 * @author YueHao <YueHao, yuehao@caimao.com>
 * @version v1.0
 * @Description
 * @encoding UTF-8
 * @date 2016/7/1
 * @time 22:38
 * @修改记录 <pre>
 * 输入流
 * --------------------------------------------------
 * <p/>
 * --------------------------------------------------
 * </pre>
 */
public class Day_01 {

    public static void main(String[] args) {
        String pathURL = "C:\\yuehao\\demo\\01.txt";
        String imgURL = "C:\\yuehao\\demo\\Ol8H3P.jpg";
        String targetURL = "C:\\yuehao\\demo\\";
//        String content = getFileContent(pathURL);
        getImgContent(imgURL, targetURL);
    }

    /**
     * 读取源路径图片，生成目标路径图片
     *
     * @param pathURL   源图片
     * @param targetURL 目标目录
     */
    private static void getImgContent(String pathURL, String targetURL) {
        File file = new File(pathURL);
        File toFile = new File(targetURL);
        try {
            InputStream inputStream = new FileInputStream(file);
            OutputStream outputStream = new FileOutputStream(toFile + "\\" + String.valueOf(new Date().getTime()) + ".jpg");
            byte[] bytes = new byte[1024];
            int len = 0;
            try {
                while ((len = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes);
                }
                outputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }


    /**
     * 从文件中读取文本内容
     *
     * @param pathURL
     * @return String
     */
    private static String getFileContent(String pathURL) {
        StringBuffer buffer = new StringBuffer();
        byte[] bytes = new byte[1024];
        File file = new File(pathURL);
        try {
            InputStream inputStream = new FileInputStream(file);
            try {
                int len = 0;
                while ((len = inputStream.read(bytes)) != -1) {
                    //编码格式来源与输出保持一致
                    String content = new String(bytes, "GBK");
                    buffer.append(content);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }
}
