package com.track.common.utils.wetch.applet;

import com.track.common.constant.wetch.applet.WxAppletConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.Map;

/**
 * @description: 小程序工具类
 * @author yeJH
 * @since 2019/10/16 23:37
 */
public class WxAppletUtil {

    private WxAppletConfig wxAppletConfig;

    public int getHttpConnectTimeoutMs() {
        return 8000;
    }

    public int getHttpReadTimeoutMs() {
        return 10000;
    }

    public WxAppletUtil(WxAppletConfig wxAppletConfig) {
        this.wxAppletConfig = wxAppletConfig;
    }

    public String codeToSession(String code) throws Exception {

        //替换参数
        String strUrl = WxAppletConstant.CODE_2_SESSION.replace("APPID", wxAppletConfig.getAPP_ID())
                .replace("SECRET", wxAppletConfig.getSECRET())
                .replace("JSCODE", code);

        String responseData = getRequest(strUrl, getHttpConnectTimeoutMs(), getHttpReadTimeoutMs());
        responseData = responseData.replace("session_key", "sessionKey")
                .replace("openid", "openId")
                .replace("errcode", "errCode")
                .replace("errmsg", "errMsg");

        return responseData;

    }

    public String getRequest(String strUrl, int connectTimeoutMs, int readTimeoutMs) throws Exception {
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
