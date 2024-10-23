package com.apicc.core.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class BaseContext implements IContext {

    //转发协议
    protected final String protocol;

    //状态：多线程情况下考虑使用volatile关键字
    protected volatile int status = IContext.Running;

    //Netty上下文
    protected final ChannelHandlerContext nettyCtx;

    //存放上下文参数。
    protected final Map<String, Object> attributes = new HashMap<String, Object>();

    //请求过程中发生的异常.
    protected Throwable throwable;

    //是否保持长连接.
    protected final  boolean keepAlive;

    //定义是否已经释放资源
    protected final AtomicBoolean requestReleased = new AtomicBoolean(false);

    //存放回调函数集合.
    protected List<Consumer<IContext>> completedCallbacks;

    public BaseContext(String protocol, ChannelHandlerContext nettyCtx, boolean keepAlive) {
        this.protocol = protocol;
        this.nettyCtx = nettyCtx;
        this.keepAlive = keepAlive;
    }

    @Override
    public void runned() {
        status = IContext.Running;
    }

    @Override
    public void writtened() {
        status = IContext.Written;
    }

    @Override
    public void completed() {
        status = IContext.Completed;
    }

    @Override
    public void terminated() {
        status = IContext.Terminated;
    }

    @Override
    public boolean isRunning() {
        return status == IContext.Running;
    }

    @Override
    public boolean isWrittened() {
        return status == IContext.Written;
    }

    @Override
    public boolean isCompleted() {
        return status == IContext.Completed;
    }

    @Override
    public boolean isTerminated() {
        return status == IContext.Terminated;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public Object getRequest() {
        return null;
    }

    @Override
    public Object getResponse() {
        return null;
    }

    @Override
    public void setResponse(Object response) {

    }

    @Override
    public Throwable getThrowable() {
        return this.throwable;
    }

    @Override
    public void setThrownable(Throwable throwable) {
        this.throwable = throwable;
    }

    @Override
    public ChannelHandlerContext getNettyCtx() {
        return this.nettyCtx;
    }

    @Override
    public boolean isKeepAlive() {
        return this.keepAlive;
    }

    @Override
    public boolean releaseRequest() {
        return false;
    }

    @Override
    public void setCompletedCallback(Consumer<IContext> consumer) {
        if (completedCallbacks == null) {
            completedCallbacks = new ArrayList<>();
        }
        completedCallbacks.add(consumer);
    }

    @Override
    public void invokeCompletedCallback(Consumer<IContext> consumer) {
        if (completedCallbacks != null) {
            completedCallbacks.forEach(call -> call.accept(this));
        }
    }
}
