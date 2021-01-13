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
public class TodoListResult extends BaseCommandResult {

    public TodoListResult() {
    }

    public TodoListResult(String content, String exitResult) {
        super(content, exitResult);
    }

    public static TodoListResult create(List<TodoItem> todoItems, boolean isShowDoneCount) {
        StringBuilder sb = new StringBuilder();
        todoItems.forEach(x -> {
            sb.append(x.getIndex())
                    .append(".")
                    .append(x.getIsDone() ? "[Done] " : "")
                    .append(x.getContent())
                    .append("\n");
        });
        int count = todoItems.size();
        long doneSize = todoItems.stream().filter(TodoItem::getIsDone).count();
        return new TodoListResult(sb.toString(), isShowDoneCount ?
                String.format("Total: %s items， %s item done", count, doneSize)
                : String.format("Total: %s items", count));
    }
}
