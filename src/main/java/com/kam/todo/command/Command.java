package com.kam.todo.command;

import com.kam.todo.bean.BaseCommandResult;
import com.kam.todo.bean.enums.CommandEnum;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 *
 * </p>
 */
public interface Command<T extends BaseCommandResult> {

    /**
     * 获取命令行枚举
     *
     * @return
     */
    CommandEnum getCommandEnum();

    /**
     * 执行
     *
     * @param input
     */
    T exec(String input);
}
