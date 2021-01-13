package com.kam.todo.command.impl;

import com.kam.todo.bean.enums.CommandEnum;
import com.kam.todo.bean.result.TodoListResult;
import com.kam.todo.command.Command;
import com.kam.todo.data.TodoItemService;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todo_list命令实现
 * </p>
 */
public class TodoListCommand implements Command<TodoListResult> {

    private TodoItemService todoItemService;

    public TodoListCommand(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @Override
    public CommandEnum getCommandEnum() {
        return CommandEnum.TODO_LIST;
    }

    @Override
    public TodoListResult exec(String input) {
        CommandEnum commandEnum = this.getCommandEnum();
        String index = commandEnum.obtainContent(input);
        switch (index) {
            case " --all":
                //需查询已完成的
                return TodoListResult.create(this.todoItemService.listAll(), true);
            default:
                return TodoListResult.create(this.todoItemService.listUndone(), false);
        }
    }
}
