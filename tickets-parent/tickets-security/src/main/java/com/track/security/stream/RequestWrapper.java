package com.track.security.stream;//package com.track.security.http.servlet.input.stream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @Author cheng
 * @create 2019-10-21 16:07
 *
 * 由于流只能读取一次，所以
 * 定义一个容器，将输入流里面的数据存储到这个容器里，这个容器可以是数组或集合。
 * 重写getInputStream方法，每次都从这个容器里读数据，这样输入流就可以读取任意次了
 *
 * 没生效--待完善
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    private  byte[] requestBody;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        requestBody = HttpUtil.getBodyString(request).getBytes(Charset.forName("UTF-8"));
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    @Override
    public ServletInputStream getInputStream() throws IOException{
        if (requestBody == null) {
            requestBody = new byte[0];
        }
        final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setReadListener(ReadListener readListener) {

            }

            @Override
            public int read() throws IOException {
                return bais.read();
            }
        };
    }
}
