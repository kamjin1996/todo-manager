package com.kam.todo.bean.result;

import com.kam.todo.bean.BaseCommandResult;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todoAddResult
 * </p>
 */
public class WrongCommandResult extends BaseCommandResult {

    public WrongCommandResult() {
    }

    public WrongCommandResult(String content, String exitResult) {
        super(content, exitResult);
    }

    public static <T> T create() {
        return (T) new WrongCommandResult("", "命令错误 请对照命令表重新输入");
    }
}
