package com.axe.common.core.utils;

import com.axe.common.core.constant.CommonConstant;
import com.axe.common.core.exception.AxeException;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: TODO HTTP 工具类
 * @Date: 2025/7/10
 * @Author: Sxt
 * @Version: v1.0
 */
public class HttpUtils {
    private HttpUtils(){}

    /**
     * TODO HTTP发送GET请求
     * @param url    网址
     * @param headers 请求头
     * @param params 参数
     * @author: sxt
     * @date: 2025/07/12 10:54:32
     */
    public static String doGet(String url, Map<String, String> headers, Map<String, String> params) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ClassicRequestBuilder requestBuilder = ClassicRequestBuilder.get(url);
            if (CollectionUtils.isNotEmpty(headers)) {
                headers.forEach(requestBuilder::addHeader);
            }
            if (CollectionUtils.isNotEmpty(params)) {
                params.forEach(requestBuilder::addParameter);
            }
            ClassicHttpRequest httpRequest = requestBuilder.build();
            return httpClient.execute(httpRequest, response -> {
                int statusCode = response.getCode();
                HttpEntity entity = response.getEntity();
                String content = null;
                try {
                    content = entity != null ? EntityUtils.toString(entity, StandardCharsets.UTF_8) : null;
                } finally {
                    EntityUtils.consume(entity);
                }
                if (statusCode == CommonConstant.HTTP_SUCCESS_CODE) {
                    return content;
                } else {
                    throw new IOException("HTTP Error: " + statusCode + ", Response: " + content);
                }
            });
        } catch (IOException e) {
            throw new AxeException("请求失败: " + e.getMessage(), e);
        }
    }


    /**
     * TODO HTTP POST请求-JSON
     * @param url     网址
     * @param headers 请求头
     * @param body    请求体
     * @return 一串
     * @author: sxt
     * @date: 2025/07/12 11:35:44
     */
    public static String doPostJson(String url, Map<String, String> headers, Map<String, Object> body){
        return doPost(url, headers, JsonUtils.convertMapToJson(body),CommonConstant.CONTENT_TYPE_JSON);
    }

    /**
     * TODO POST请求-表单
     * @param url        网址
     * @param headers    请求头
     * @param formParams 表单参数
     * @return 一串
     * @author: sxt
     * @date: 2025/07/16 13:17:20
     */
    public static String doPostForm(String url, Map<String, String> headers, Map<String, String> formParams) {
        // 构建表单格式的请求体
        StringBuilder formBody = new StringBuilder();
        if (formParams != null && !formParams.isEmpty()) {
            formParams.forEach((key, value) -> {
                if (formBody.length() > 0) {
                    formBody.append("&");
                }
                formBody.append(key).append("=").append(value);
            });
        }
        return doPost(url, headers, formBody.toString(), CommonConstant.CONTENT_TYPE_FORM);
    }

    private static String doPost(String url, Map<String, String> headers, String body, String contentType) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            ClassicRequestBuilder requestBuilder = ClassicRequestBuilder.post(url);
            // 添加请求头
            if (CollectionUtils.isNotEmpty(headers)) {
                headers.forEach(requestBuilder::addHeader);
            }
            if (headers == null){
                headers = new HashMap<>();
            }
            // 设置内容类型（如果未在headers中指定）
            if (!headers.containsKey("Content-Type") && StringUtils.isNotEmpty(contentType)) {
                requestBuilder.addHeader("Content-Type", contentType);
            }
            // 添加请求体
            if (StringUtils.isNotEmpty(body)) {
                ContentType type = ContentType.parse(contentType);
                requestBuilder.setEntity(new StringEntity(body, type));
            }
            ClassicHttpRequest request = requestBuilder.build();
            return httpClient.execute(request, response -> {
                int statusCode = response.getCode();
                HttpEntity entity = response.getEntity();
                String content = null;

                try {
                    content = entity != null ?
                            EntityUtils.toString(entity, StandardCharsets.UTF_8) : "";
                } finally {
                    // 确保资源释放
                    EntityUtils.consume(entity);
                }
                if (statusCode == CommonConstant.HTTP_SUCCESS_CODE) {
                    return content;
                } else {
                    throw new IOException("HTTP Error: " + statusCode + ", Response: " + content);
                }
            });
        } catch (IOException e) {
            throw new AxeException("POST请求失败: " + e.getMessage(), e);
        }
    }
}
