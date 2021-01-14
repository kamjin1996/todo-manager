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
public class TodoDoneResult extends BaseCommandResult {

    public TodoDoneResult() {
    }

    public TodoDoneResult(String content, String exitResult) {
        super(content, exitResult);
    }

    public TodoDoneResult create(int index) {
        this.setContent("");
        this.setExitResult(String.format("Item %s done", index));
        return this;
    }

    public TodoDoneResult wrong(String msg) {
        this.setContent("");
        this.setExitResult(msg);
        return this;
    }
}
