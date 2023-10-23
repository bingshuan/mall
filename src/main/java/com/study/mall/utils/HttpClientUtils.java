package com.study.mall.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.http.HttpMethod;

import java.io.IOException;

/**
 * @author peng
 * @version 1.0
 * @description TODO
 * @date 2023/10/18 23:07
 */
public class HttpClientUtils {
    public static JSONObject execute(String url, HttpMethod httpMethod) {
        HttpRequestBase http = null;
        try {
            CloseableHttpClient client = HttpClients.createDefault();
            //根据 httpMethod 进行 httpRequest的创建
            if (httpMethod == HttpMethod.GET) {
                http = new HttpGet(url);
            } else {
                http = new HttpPost(url);
            }
            HttpEntity entity = null;
            entity = client.execute(http).getEntity();
            return JSONObject.parseObject(EntityUtils.toString(entity));
        } catch (IOException e) {
            throw new RuntimeException("request failed. url = " + url);
        } finally {
            http.releaseConnection();
        }
    }
}
