package com.github.trang.autoconfigure.redisson.enums;

/**
 * Redisson 客户端模式
 *
 * @author trang
 */
public enum ClientMode {

    /**
     * 默认客户端
     */
    DEFAULT,

    /**
     * Reactive 客户端
     */
    REACTIVE,

    /**
     * RxJava2 客户端
     */
    RXJAVA2,

    /**
     * 禁用 Redisson
     */
    NONE

}