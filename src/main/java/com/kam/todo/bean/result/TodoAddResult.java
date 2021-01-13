package com.kam.todo.bean.result;

import com.kam.todo.bean.BaseCommandResult;
import com.kam.todo.bean.TodoItem;

import java.util.List;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todoAddResult
 * </p>
 */
public class TodoAddResult extends BaseCommandResult {

    public TodoAddResult() {
    }

    public TodoAddResult(String content, String exitResult) {
        super(content, exitResult);
    }

    public static TodoAddResult create(List<TodoItem> all, int index) {
        StringBuilder sb = new StringBuilder();
        all.forEach(x -> {
            sb.append(x.getIndex())
                    .append(".")
                    .append(x.getContent())
                    .append("\n");
        });
        return new TodoAddResult(sb.toString(), String.format("Item %s added", index));
    }
}
