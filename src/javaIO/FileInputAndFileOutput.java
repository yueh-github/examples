package javaIO;

import java.io.*;
import java.util.Date;

/**
 * @author YueHao <YueHao, yuehao@caimao.com>
 * @version v1.0
 * @Description
 * @encoding UTF-8
 * @date 2016/7/1
 * @time 22:38
 * @修改记录 <pre>
 * --------------------------------------------------
 * FileInputStream与FileInputStream关系与用法
 * --------------------------------------------------
 * </pre>
 */
public class FileInputAndFileOutput {
    /**
     * txt文件 *
     */
    private static final String PATH_URL = "C:\\yuehao\\demo\\01.txt";
    /**
     * 图片文件 *
     */
    private static final String IMG_URL = "C:\\yuehao\\demo\\Ol8H3P.jpg";
    /**
     * 文件夹目录 *
     */
    private static final String TARGET_URL = "C:\\yuehao\\demo\\";

    //java io 流分为字符流与字节流
    //字符流只能读取字符内容
    //字节流可以读取一切
    //FileInputStream＆FileOutputSteam属于字节流

    /**
     * 读取源路径图片，生成目标路径图片
     *
     * @param pathURL   源图片
     * @param targetURL 目标目录
     */
    private static void getImgContent(String pathURL, String targetURL) {
        //源文件地址
        File file = new File(pathURL);
        //目标文件地址
        File toFile = new File(targetURL);
        try {
            //从源文件地址读取
            InputStream inputStream = new FileInputStream(file);
            //写出到目标文件
            OutputStream outputStream = new FileOutputStream(toFile + "\\" + String.valueOf(new Date().getTime()) + ".jpg");
            //建立一个字节数组
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
