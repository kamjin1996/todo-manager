package com.kam.todo.exception;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * 非法参数异常
 * </p>
 */
public class LocalFileException extends RuntimeException {

    public LocalFileException(String msg) {
        super(msg);
    }
}
