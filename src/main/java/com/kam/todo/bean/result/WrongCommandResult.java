package com.kam.todo.bean.result;

import com.kam.todo.bean.BaseCommandResult;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * 错误的命令结果返回
 * </p>
 */
public class WrongCommandResult extends BaseCommandResult {

    public WrongCommandResult() {
    }

    public WrongCommandResult(String content, String exitResult) {
        super(content, exitResult);
    }

    public WrongCommandResult create() {
        this.setContent("");
        this.setExitResult("命令错误 请对照命令表重新输入");
        return this;
    }
}
