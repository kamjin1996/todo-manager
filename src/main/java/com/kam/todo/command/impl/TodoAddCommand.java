package com.kam.todo.command.impl;

import com.kam.todo.bean.TodoItem;
import com.kam.todo.bean.enums.CommandEnum;
import com.kam.todo.bean.result.TodoAddResult;
import com.kam.todo.command.Command;
import com.kam.todo.data.TodoItemService;

import java.util.List;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todo_add命令实现
 * </p>
 */
public class TodoAddCommand implements Command<TodoAddResult> {

    private TodoItemService todoItemService;

    public TodoAddCommand(TodoItemService todoItemService) {
        this.todoItemService = todoItemService;
    }

    @Override
    public CommandEnum getCommandEnum() {
        return CommandEnum.TODO_ADD;
    }

    @Override
    public TodoAddResult exec(String input) {
        CommandEnum commandEnum = this.getCommandEnum();
        String content = commandEnum.obtainContent(input);

        TodoItem item = this.todoItemService.save(content);
        List<TodoItem> todoItems = this.todoItemService.listAll();
        return new TodoAddResult().create(todoItems, item.getIndex());
    }
}
