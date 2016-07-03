package javaIO;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sun.applet.Main;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

/**
 * @author YueHao <YueHao, yuehao@caimao.com>
 * @version v1.0
 * @Description
 * @encoding UTF-8
 * @date 2016/7/2
 * @time 0:17
 * @修改记录 <pre>
 * <p/>
 * 爬虫
 * --------------------------------------------------
 * <p/>
 * --------------------------------------------------
 * </pre>
 */
public class Spider {
    private static final String ROOT_URL = "http://cc.iiiaaa.me/";

    private static final String PAGE_URL = "thread0806.php?fid=16";

    //TODO 1-151
    private static final String NEXT_PAGE = "&search=&page=";

    private static final Integer PAGE_NUMBER = 151;

    private static final String TARGET_URL = "C:\\yuehao\\demo\\caoliu\\";

    /**
     * 返回图片详情页面地址
     *
     * @param URL
     * @return
     */
    private static List<String> getURL(String URL) {
        List<String> list = new ArrayList();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < PAGE_NUMBER; i++) {
            String result = HttpSendUtils.doGet((URL + NEXT_PAGE + 1));
            System.out.println(result);
            Document doc = Jsoup.parse(result);
            Elements news = doc.select(".t").select("tbody").select("tr").select(".tr3").select("td").select("h3").select("a");
            for (int j = 0; j < news.size(); j++) {
                if (j != 0 && j != 1) {
                    System.out.println("获取到资源URL：" + news.get(j).attr("href"));
                    list.add(news.get(j).attr("href"));
                }
            }
        }
        if (list != null) {
            try {
                FileOutputStream outStream = new FileOutputStream("C:\\yuehao\\demo\\url.txt");
                ObjectOutputStream outputStream = new ObjectOutputStream(outStream);
                outputStream.writeObject(list);
                outStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 解析图片详情页面IMG
     *
     * @param list
     */
    private static void getIMG(List<String> list) {
        for (int i = 0; i < list.size(); i++) {
            String result = HttpSendUtils.doGet(ROOT_URL + list.get(i));
            Document doc = Jsoup.parse(result);
            Elements news = doc.select("tbody").select("tr").select("td").select(".tpc_content").select("p").select("input");
            for (int j = 0; j < news.size(); j++) {
                getImgContent(news.get(j).attr("src"));
            }
        }
    }

    /**
     * 启动爬虫
     *
     * @param args
     */
    public static void main(String[] args) {
        List<String> urlList = getURL(ROOT_URL + PAGE_URL);
        getIMG(urlList);
    }


    /**
     * 读取源路径图片，生成目标路径图片
     *
     * @param pathURL 源图片
     */
    private static void getImgContent(String pathURL) {
        //判断caoliu当前目录最大文件夹是否满载
        //如果满载则新建文件夹，如果不满载则继续往里写数据
        File file = new File(TARGET_URL);
        String[] strings = file.list();

        int[] array = new int[strings.length];
        for (int i = 0; i < strings.length; i++) {
            array[i] = Integer.parseInt(strings[i]);
        }

        int maxValue = getMaxNumber(array);
        File imgFiles = new File(TARGET_URL + maxValue);
        if (imgFiles.list().length == 100) {
            imgFiles = new File(TARGET_URL + (maxValue + 1));
            imgFiles.mkdir();
        }
        try {
            System.out.println("写入当前图片到指定文件夹目录：" + pathURL);
            OutputStream outputStream = new FileOutputStream(imgFiles + "\\" + String.valueOf(new Date().getTime()) + ".jpg");
            byte[] bytes = getImageFromNetByUrl(pathURL);
            if (bytes != null) {
                outputStream.write(bytes);
            }
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟浏览器访问，可以解决403问题
     *
     * @param strUrl
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            conn.setRequestProperty("Accept", "*/*");
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 5.1; Trident/4.0; CIBA)"); //模拟ie浏览器
            conn.setRequestProperty("Accept-Language", "zh-cn");
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
            return btImg;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 数组内取最大值
     *
     * @param arr
     * @return
     */
    public static int getMaxNumber(int[] arr) {
        int max = arr[0];
        for (int x = 1; x < arr.length; x++) {
            if (arr[x] > max)
                max = arr[x];
        }
        return max;
    }
}
