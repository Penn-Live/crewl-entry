package com.yzyw.penn.reqeust;

/**
 * Created by david on 2017-7-5.
 */
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashSet;

public class HttpRequestUtil {
    /**
     * 发起http请求并获取结果
     * @param requestUrl 请求地址
     */
    public static JsonObject getXpath(String requestUrl){
        String res;
        JsonObject object = null;
        StringBuffer buffer = new StringBuffer();
        try{
            URL url = new URL(requestUrl);
            HttpURLConnection urlCon= (HttpURLConnection)url.openConnection();
            if(200==urlCon.getResponseCode()){
                InputStream is = urlCon.getInputStream();
                InputStreamReader isr = new InputStreamReader(is,"utf-8");
                BufferedReader br = new BufferedReader(isr);

                String str = null;
                while((str = br.readLine())!=null){
                    buffer.append(str);
                }
                br.close();
                isr.close();
                is.close();
                res = buffer.toString();
                JsonParser parse =new JsonParser();
                object = (JsonObject) parse.parse(res);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return object;
    }

    public static JsonObject postDownloadJson(String path,String post){
        URL url ;
        try {
            url = new URL(path);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数
            printWriter.write(post);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();
            //开始获取数据
            BufferedInputStream bis = new  BufferedInputStream(httpURLConnection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            JsonParser parse = new JsonParser();
            return (JsonObject)parse.parse(bos.toString("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //读取html
    public static Document readHtml(String url) {
        return Jsoup.parse(getGetRequestStr(url));
    }


    public static JsonObject readContentFromGet(String url) {
        JsonParser parse = new JsonParser();
        return (JsonObject)parse.parse(getGetRequestStr(url));
    }


    private static String getGetRequestStr(String url){
        try{
            // 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
            String getURL = url + URLEncoder.encode(" fat man ", "utf-8");
            URL getUrl = new URL(getURL);
            // 根据拼凑的URL，打开连接，URL.openConnection函数会根据 URL的类型，
            // 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
            HttpURLConnection connection = (HttpURLConnection) getUrl
                    .openConnection();
            // 进行连接，但是实际上get request要在下一句的 connection.getInputStream()函数中才会真正发到
            // 服务器
            connection.connect();

            //开始获取数据
            BufferedInputStream bis = new  BufferedInputStream(connection.getInputStream());
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int len;
            byte[] arr = new byte[1024];
            while((len=bis.read(arr))!= -1){
                bos.write(arr,0,len);
                bos.flush();
            }
            bos.close();
            return bos.toString("utf-8");
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }




}
