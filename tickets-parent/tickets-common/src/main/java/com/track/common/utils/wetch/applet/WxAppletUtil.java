package com.track.common.utils.wetch.applet;

import com.track.common.constant.wetch.applet.WxAppletConstant;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description: 小程序工具类
 * @author yeJH
 * @since 2019/10/16 23:37
 */
@Slf4j
public class WxAppletUtil {

    public static int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public static int getHttpReadTimeoutMs() {
        return 10000;
    }


    public static String codeToSession(String code) throws Exception {

//        log.error("测试====----"+WxAppletConfig.APP_ID);

        //替换参数
        String strUrl = WxAppletConstant.CODE_2_SESSION.replace("APPID", WxAppletConfig.APP_ID)
                .replace("SECRET", WxAppletConfig.SECRET)
                .replace("JSCODE", code);

        String responseData = getRequest(strUrl, getHttpConnectTimeoutMs(), getHttpReadTimeoutMs());
        responseData = responseData.replace("session_key", "sessionKey")
                .replace("openid", "openId")
                .replace("errcode", "errCode")
                .replace("errmsg", "errMsg");

        return responseData;

    }

    public static String getRequest(String strUrl, int connectTimeoutMs, int readTimeoutMs) throws Exception {
        String UTF8 = "UTF-8";
        URL httpUrl = new URL(strUrl);
        HttpURLConnection httpURLConnection = (HttpURLConnection)httpUrl.openConnection();
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.setConnectTimeout(connectTimeoutMs);
        httpURLConnection.setReadTimeout(readTimeoutMs);
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, UTF8));
        StringBuffer stringBuffer = new StringBuffer();
        String line = null;

        while((line = bufferedReader.readLine()) != null) {
            stringBuffer.append(line);
        }

        String resp = stringBuffer.toString();
        if (stringBuffer != null) {
            try {
                bufferedReader.close();
            } catch (IOException var18) {
                var18.printStackTrace();
            }
        }

        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException var17) {
                var17.printStackTrace();
            }
        }

        return resp;
    }
}
