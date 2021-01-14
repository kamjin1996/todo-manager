package com.kam.todo.command;

import com.kam.todo.bean.enums.CommandEnum;
import com.kam.todo.command.impl.TodoAddCommand;
import com.kam.todo.command.impl.TodoDoneCommand;
import com.kam.todo.command.impl.TodoListCommand;
import com.kam.todo.command.impl.WrongCommand;
import com.kam.todo.data.TodoItemService;
import com.kam.todo.data.impl.LocalFileStoreTodoItemServiceImpl;
import com.kam.todo.data.impl.MemoryTodoItemServiceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * 输入命令处理
 * </p>
 */
public class InputCommandResolver implements CommandResolver<String> {

    /**
     * 命令与服务关系map
     */
    private static final Map<CommandEnum, Command<?>> commandServiceMap = new HashMap<>();

    static {
        //支持文件存储 支持内存存储
        TodoItemService todoItemService = new LocalFileStoreTodoItemServiceImpl(new MemoryTodoItemServiceImpl());
        commandServiceMap.put(CommandEnum.TODO_ADD, new TodoAddCommand(todoItemService));
        commandServiceMap.put(CommandEnum.TODO_DONE, new TodoDoneCommand(todoItemService));
        commandServiceMap.put(CommandEnum.TODO_LIST, new TodoListCommand(todoItemService));
    }

    @Override
    public Command<?> resolver(String input) {
        for (CommandEnum commandEnum : CommandEnum.values()) {
            Optional<Pattern> patternOpt = Optional.ofNullable(commandEnum.getPattern());
            if (patternOpt.isPresent()) {
                if (patternOpt.get().matcher(input).matches()) {
                    return commandServiceMap.get(commandEnum);
                }
            }
        }
        return new WrongCommand();
    }
}
