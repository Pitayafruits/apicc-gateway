package com.apicc.core.request;

/**
 * 提供可修改的Request参数操作接口.
 */
public interface IGatewayRequest {

    /**
     * 修改目标服务主机.
     *
     * @param host
     */
    void setModifyHost(String host);

    /**
     * 获取目标服务主机.
     *
     * @return
     */
    String getModifyHost();

    /**
     * 设置目标服务路径.
     *
     * @param path
     */
    void setModifyPath(String path);

    /**
     * 获取目标服务路径.
     *
     * @return
     */
    String getModifyPath();

    /**
     * 添加请求头信息.
     *
     * @param name
     * @param value
     */
    void addHeader(CharSequence name, String value);

    /**
     * 设置请求头信息.
     *
     * @param name
     * @param value
     */
    void setHeader(CharSequence name, String value);

    /**
     * 添加Get请求参数.
     *
     * @param name
     * @param value
     */
    void addQueryParam(String name, String value);

    /**
     * 添加Post请求参数.
     *
     * @param name
     * @param value
     */
    void addFormParam(String name, String value);

    /**
     * 添加或替换Cookie.
     *
     * @param cookie
     */
    void addOrReplaceCookie(Cookie cookie);

    /**
     * 设置请求超时时间.
     *
     * @param requestTimeout
     */
    void setRequestTimeout(int requestTimeout);

    /**
     * 获取最终请求URL，包含请求参数。例如：http://localhost:8000/api/admin?name=111
     *
     * @return
     */
    void getFinalUrl(int requestTimeout);

    Request build();
}