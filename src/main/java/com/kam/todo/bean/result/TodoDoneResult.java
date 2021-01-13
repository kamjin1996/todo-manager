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

    public static TodoDoneResult create(int index) {
        return new TodoDoneResult("", String.format("Item %s done", index));
    }
}
