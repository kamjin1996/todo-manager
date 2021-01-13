package com.kam.todo.data.impl;

import com.kam.todo.bean.TodoItem;
import com.kam.todo.data.TodoItemService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todo项handler
 * </p>
 */
public class MemoryTodoItemServiceImpl implements TodoItemService {

    /**
     * 索引计数
     */
    private static final AtomicInteger index = new AtomicInteger(1);

    /**
     * item数据存储
     */
    private static List<TodoItem> items = new ArrayList<>();

    @Override
    public List<TodoItem> saveAll(List<TodoItem> allItems) {
         items.addAll(allItems);
        return items;
    }

    @Override
    public TodoItem save(TodoItem item) {
        items.add(item);
        return item;
    }

    @Override
    public TodoItem save(String content) {
        TodoItem todoItem = new TodoItem();
        todoItem.setContent(content);
        todoItem.setIndex(index.getAndIncrement());
        todoItem.setIsDone(Boolean.FALSE);
        return this.save(todoItem);
    }

    @Override
    public List<TodoItem> listAll() {
        return items;
    }

    @Override
    public TodoItem done(Integer index) {
        TodoItem todoItem = items.get(index);
        if (Objects.nonNull(todoItem)) {
            items.remove(todoItem);
            todoItem.setIsDone(Boolean.TRUE);
            items.add(index, todoItem);
        }
        return todoItem;
    }

    @Override
    public List<TodoItem> listUndone() {
        return items.stream().filter(x -> !x.getIsDone()).collect(Collectors.toList());
    }
}
