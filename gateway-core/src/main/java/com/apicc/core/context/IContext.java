package com.apicc.core.context;

import io.netty.channel.ChannelHandlerContext;

import java.util.function.Consumer;

public interface IContext {

    /**
     * 上下文生命周期, 运行中
     */
    int Running = 1;

    /**
     * 运行过程中发生错误，对其进行标记，告诉我们请求已经结束，需要返回客户端.
     */
    int Written = 0;

    /**
     * 标记写回成功，防止并发情况下多次写回.
     */
    int Completed = 1;

    /**
     * 表示网关请求结束
     */
    int Terminated = 2;

    /**
     * 设置上下文状态为运行中.
     */
    void runned();

    /**
     * 设置上下文状态为标记写回.
     */
    void writtened();

    /**
     * 设置上下文状态为标记写回成功.
     */
    void completed();

    /**
     * 设置上下文状态为标记请求结束.
     */
    void terminated();

    /**
     * 判断网关状态.
     */
    boolean isRunning();

    boolean isWrittened();

    boolean isCompleted();

    boolean isTerminated();

    /**
     * 获取协议.
     *
     * @return
     */
    String getProtocol();

    /**
     * 获取请求对象.
     *
     * @return
     */
    Object getRequest();

    /**
     * 获取响应对象.
     *
     * @return
     */
    Object getResponse();

    /**
     * 设置响应对象.
     *
     * @return
     */
    void setResponse(Object response);

    /**
     * 获取异常对象.
     *
     * @return
     */
    Throwable getThrowable();

    /**
     * 设置异常对象.
     *
     * @param throwable
     */
    void setThrownable(Throwable throwable);

    /**
     * 获取netty上下文对象.
     *
     * @return
     */
    ChannelHandlerContext getNettyCtx();

    /**
     * 是否保持长连接.
     *
     * @return
     */
    boolean isKeepAlive();

    /**
     * 释放请求资源.
     *
     * @return
     */
    boolean releaseRequest();

    /**
     * 设置写回接收回调函数.
     *
     * @param consumer
     */
    void setCompletedCallback(Consumer<IContext> consumer);

    /**
     * 执行写回回调函数.
     *
     * @param consumer
     */
    void invokeCompletedCallback(Consumer<IContext> consumer);
}
