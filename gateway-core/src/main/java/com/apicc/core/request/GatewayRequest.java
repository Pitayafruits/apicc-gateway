package com.apicc.core.request;

import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.cookie.Cookie;
import lombok.Getter;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;

import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

/**
 * 网关请求对象
 */
public class GatewayRequest implements IGatewayRequest {

    /**
     * 服务唯一id.
     */
    @Getter
    private final String uniqueId;

    /**
     * 进入网关开始时间.
     */
    @Getter
    private final long brginTime;

    /**
     * 进入网关结束时间.
     */
    @Getter
    private final long endTime;

    /**
     * 字符集.
     */
    @Getter
    private final Charset charset;

    /**
     * 客户端ip.
     */
    @Getter
    private final String clientIp;

    /**
     * 服务端主机名.
     */
    @Getter
    private final String host;

    /**
     * 服务端请求路径.
     */
    @Getter
    private final String path;

    /**
     * 统一资源标识符.
     */
    @Getter
    private final String uri;

    /**
     * 请求方式.
     */
    @Getter
    private final HttpMethod httpMethod;

    /**
     * 请求的格式.
     */
    @Getter
    private final HttpMethod contentType;

    /**
     * 请求头.
     */
    @Getter
    private final HttpHeaders httpHeaders;

    /**
     * 参数解析器.
     */
    @Getter
    private final QueryStringDecoder queryStringDecoder;

    /**
     * http校验.
     */
    @Getter
    private final FullHttpRequest fullHttpRequest;

    /**
     * 请求体.
     */
    private String body;

    /**
     * cookie.
     */
    private Map<String, Cookie> cookieMap;

    /**
     * post请求参数.
     */
    private Map<String, List<String>> postParameters;

    /**
     * 可修改的Scheme，默认为Http://
     */
    private String modifyScheme;

    /**
     * 可修改的主机名
     */
    private String modifyHost;

    /**
     * 可修改的请求路径.
     */
    private String modifyPath;

    /**
     * 构建下游请求时的Http构建器.
     */
    private final RequestBuilder requestBuilder;

    public GatewayRequest(String uniqueId,
                          long brginTime,
                          long endTime,
                          Charset charset,
                          String clientIp,
                          String host,
                          String path,
                          String uri,
                          HttpMethod httpMethod,
                          HttpMethod contentType,
                          HttpHeaders httpHeaders,
                          QueryStringDecoder queryStringDecoder,
                          FullHttpRequest fullHttpRequest,
                          RequestBuilder requestBuilder) {
        this.uniqueId = uniqueId;
        this.brginTime = brginTime;
        this.endTime = endTime;
        this.charset = charset;
        this.clientIp = clientIp;
        this.host = host;
        this.path = path;
        this.uri = uri;
        this.httpMethod = httpMethod;
        this.contentType = contentType;
        this.httpHeaders = httpHeaders;
        this.queryStringDecoder = queryStringDecoder;
        this.fullHttpRequest = fullHttpRequest;
        this.requestBuilder = requestBuilder;
    }

    @Override
    public void setModifyHost(String host) {

    }

    @Override
    public String getModifyHost() {
        return null;
    }

    @Override
    public void setModifyPath(String path) {

    }

    @Override
    public String getModifyPath() {
        return null;
    }

    @Override
    public void addHeader(CharSequence name, String value) {

    }

    @Override
    public void setHeader(CharSequence name, String value) {

    }

    @Override
    public void addQueryParam(String name, String value) {

    }

    @Override
    public void addFormParam(String name, String value) {

    }

    @Override
    public void addOrReplaceCookie(Cookie cookie) {

    }

    @Override
    public void setRequestTimeout(int requestTimeout) {

    }

    @Override
    public void getFinalUrl(int requestTimeout) {

    }

    @Override
    public Request build() {
        return null;
    }
}
