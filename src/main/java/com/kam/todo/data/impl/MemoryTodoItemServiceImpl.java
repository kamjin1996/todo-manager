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
     * item数据存储
     */
    private static List<TodoItem> items = new ArrayList<>();

    /**
     * 索引计数
     */
    private AtomicInteger index = new AtomicInteger(0);

    private TodoItemService delegate;

    public MemoryTodoItemServiceImpl() {
    }

    public MemoryTodoItemServiceImpl(TodoItemService todoItemService) {
        this.delegate = todoItemService;
        if (Objects.nonNull(this.delegate)) {
            index = this.delegate.getIndex();
        }
    }

    @Override
    public AtomicInteger getIndex() {
        return index;
    }

    @Override
    public void setIndex(AtomicInteger index) {
        this.index = index;
    }

    @Override
    public List<TodoItem> saveAll(List<TodoItem> allItems) {
        if (Objects.nonNull(this.delegate)) {
            this.delegate.saveAll(allItems);
        }
        items.addAll(allItems);
        return items;
    }

    @Override
    public TodoItem save(TodoItem item) {
        if (Objects.nonNull(this.delegate)) {
            this.delegate.save(item);
        }
        items.add(item);
        return item;
    }

    @Override
    public TodoItem save(String content) {
        TodoItem todoItem = new TodoItem();
        todoItem.setContent(content);
        todoItem.setIndex(index.incrementAndGet());
        todoItem.setIsDone(Boolean.FALSE);
        return this.save(todoItem);
    }

    @Override
    public List<TodoItem> listAll() {
        if (Objects.nonNull(this.delegate)) {
            this.delegate.listAll();
        }
        return items;
    }

    @Override
    public TodoItem done(Integer index) {
        if (Objects.nonNull(this.delegate)) {
            this.delegate.done(index);
        }
        int arrIndex = index - 1;//需要减去1 因为数组下标默认从0开始 但不能对原值赋值 会有问题
        if (arrIndex >= items.size()) {
            return null;
        }
        TodoItem todoItem = items.get(arrIndex);
        if (Objects.nonNull(todoItem)) {
            todoItem.setIsDone(Boolean.TRUE);
        }
        return todoItem;
    }

    @Override
    public List<TodoItem> listUndone() {
        if (Objects.nonNull(this.delegate)) {
            this.delegate.listUndone();
        }
        return items.stream()
                .filter(x -> !x.getIsDone())
                .collect(Collectors.toList());
    }
}
