//package com.track.security.http.servlet.input.stream;
//
//import javax.servlet.ServletRequest;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//
///**
// * @Author cheng
// * @create 2019-10-21 16:51
// *
// * InputStream只会被读一次
// *
// * 重写request的inputstream方法。。然后在需要部署应用中加上过滤器，在过滤器中加上这个重写的request的方法
// *
// * 没生效--待完善
// *
// */
//public class HttpUtil {
//
//    public static String getBodyString(ServletRequest request) {
//        StringBuilder sb = new StringBuilder();
//        InputStream inputStream = null;
//        BufferedReader reader = null;
//        try {
//            inputStream = request.getInputStream();//不走自定义的，故失败了，默认的Request会改变状态，第二次就拿不到了
//            reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
//            String line = "";
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            if (inputStream != null) {
//                try {
//                    inputStream.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return sb.toString();
//    }
//
//}
