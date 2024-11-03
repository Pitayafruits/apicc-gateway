package com.apicc.core.request;

import com.apicc.common.constants.BasicConst;
import com.apicc.common.utils.TimeUtil;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.cookie.Cookie;
import io.netty.handler.codec.http.cookie.ServerCookieDecoder;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.asynchttpclient.Request;
import org.asynchttpclient.RequestBuilder;

import java.nio.charset.Charset;
import java.util.*;

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
    private final HttpMethod method;

    /**
     * 请求的格式.
     */
    @Getter
    private final String contentType;

    /**
     * 请求头.
     */
    @Getter
    private final HttpHeaders headers;

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
                          HttpMethod method,
                          String contentType,
                          HttpHeaders headers,
                          QueryStringDecoder queryStringDecoder,
                          FullHttpRequest fullHttpRequest,
                          RequestBuilder requestBuilder) {
        this.uniqueId = uniqueId;
        this.brginTime = TimeUtil.currentTimeMillis();
        this.endTime = endTime;
        this.charset = charset;
        this.clientIp = clientIp;
        this.host = host;
        this.path = path;
        this.uri = uri;
        this.method = method;
        this.contentType = contentType;
        this.headers = headers;
        this.queryStringDecoder = new QueryStringDecoder(uri, charset);
        this.fullHttpRequest = fullHttpRequest;
        this.requestBuilder = new RequestBuilder();

        this.modifyHost = host;
        this.modifyPath = path;
        this.modifyScheme = BasicConst.HTTP_PREFIX_SEPARATOR;

        this.requestBuilder.setMethod(getMethod().name());
        this.requestBuilder.setHeaders(getHeaders());
        this.requestBuilder.setQueryParams(queryStringDecoder.parameters());

        ByteBuf contentBuffer = fullHttpRequest.content();
        if (Objects.nonNull(contentBuffer)) {
            this.requestBuilder.setBody(contentBuffer.nioBuffer());
        }
    }

    /**
     * 获取请求体.
     *
     * @return 请求体
     */
    public String getBody() {
        if (StringUtils.isEmpty(body)) {
            body = fullHttpRequest.content().toString(charset);
        }
        return body;
    }


    /**
     * 获取Cookie.
     *
     * @return Cookie
     */
    public Cookie getCookie(String name) {
        if (cookieMap == null) {
            cookieMap = new HashMap<String, Cookie>();
            String cookieStr = getHeaders().get(HttpHeaderNames.COOKIE);
            Set<Cookie> cookies = ServerCookieDecoder.STRICT.decode(cookieStr);
            for (Cookie cookie : cookies) {
                cookieMap.put(name, cookie);
            }
        }
        return cookieMap.get(name);
    }

    /**
     * 获取指定名称的参数.
     *
     * @return 参数值
     */
    public List<String> getQueryParametersMultiple(String name) {
        return queryStringDecoder.parameters().get(name);
    }

    /**
     * 获取Post请求指定名称的参数.
     *
     * @return 参数值
     */
    public List<String> getPostParametersMultiple(String name) {
        String body = getBody();
        if (isFormPost()) {
            if (postParameters == null) {
                QueryStringDecoder paramDecoder = new QueryStringDecoder(body, false);
                postParameters = paramDecoder.parameters();
            }
            if (postParameters == null || postParameters.isEmpty()) {
                return null;
            } else {
                return postParameters.get(name);
            }
        } else if (isJsonPost()) {
            return Lists.newArrayList(JsonPath.read(body, name).toString());
        }
        return null;
    }


    public boolean isFormPost() {
        return HttpMethod.POST.equals(method) && (
                contentType.startsWith(HttpHeaderValues.FORM_DATA.toString())
                        ||
                        contentType.startsWith(HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString()));
    }

    public boolean isJsonPost() {
        return HttpMethod.POST.equals(method) && contentType.startsWith(HttpHeaderValues.APPLICATION_JSON.toString());
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
