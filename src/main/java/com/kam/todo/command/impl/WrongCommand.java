package com.kam.todo.command.impl;

import com.kam.todo.bean.enums.CommandEnum;
import com.kam.todo.bean.result.WrongCommandResult;
import com.kam.todo.command.Command;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * 错误的命令
 * </p>
 */
public class WrongCommand implements Command<WrongCommandResult> {

    @Override
    public CommandEnum getCommandEnum() {
        return CommandEnum.UNDEFINED;
    }

    @Override
    public WrongCommandResult exec(String input) {
        return new WrongCommandResult().create();
    }

}
