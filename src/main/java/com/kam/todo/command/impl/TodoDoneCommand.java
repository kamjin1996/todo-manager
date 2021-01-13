package com.kam.todo.command.impl;

import com.kam.todo.bean.TodoItem;
import com.kam.todo.bean.enums.CommandEnum;
import com.kam.todo.bean.result.TodoDoneResult;
import com.kam.todo.bean.result.WrongCommandResult;
import com.kam.todo.command.Command;
import com.kam.todo.data.TodoItemService;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todo_done命令实现
 * </p>
 */
public class TodoDoneCommand implements Command<TodoDoneResult> {

    private TodoItemService todoItemService;

    public TodoDoneCommand(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @Override
    public CommandEnum getCommandEnum() {
        return CommandEnum.TODO_DONE;
    }

    @Override
    public TodoDoneResult exec(String input) {
        CommandEnum commandEnum = this.getCommandEnum();
        String index = commandEnum.obtainContent(input);
        int indexInt;
        try {
            indexInt = Integer.parseInt(index);
        } catch (NumberFormatException e) {
            return WrongCommandResult.create();
        }

        TodoItem item = this.todoItemService.done(indexInt - 1);//需要减去1 因为数组下标默认从0开始
        return TodoDoneResult.create(item.getIndex());
    }
}
