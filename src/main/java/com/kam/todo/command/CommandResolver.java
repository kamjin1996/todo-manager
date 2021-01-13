package com.kam.todo.command;

import com.kam.todo.bean.BaseCommandResult;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * 命令解析
 * T 命令格式
 * </p>
 */
public interface CommandResolver<T> {

    /**
     * 解析
     *
     * @param t
     * @return
     */
    Command<? extends BaseCommandResult> resolver(T t);
}
