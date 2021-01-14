package com.kam.todo.data.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.kam.todo.bean.TodoItem;
import com.kam.todo.data.TodoItemService;
import com.kam.todo.exception.LocalFileException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todoitem本地存储服务实现类
 * </p>
 */
public class LocalFileStoreTodoItemServiceImpl implements TodoItemService {

    private File todoLocalFile;//默认

    private TodoItemService delegate;

    private AtomicInteger index = new AtomicInteger(0);

    public LocalFileStoreTodoItemServiceImpl() {
        init();
    }

    public LocalFileStoreTodoItemServiceImpl(TodoItemService todoItemService) {
        this();
        this.delegate = todoItemService;
        putData2Delegate(this.delegate);
    }

    public LocalFileStoreTodoItemServiceImpl(TodoItemService todoItemService, File todoLocalFile) {
        this(todoItemService);
        this.todoLocalFile = todoLocalFile;
    }

    /**
     * 将数据和索引从文件同步到delegate
     */
    private void putData2Delegate(TodoItemService delegate) {
        //设置索引
        delegate.setIndex(this.index);

        //放入数据
        List<TodoItem> localFileTodoItems = readAllTodoItemsInFile();

        if (CollectionUtil.isNotEmpty(localFileTodoItems)) {
            delegate.saveAll(localFileTodoItems);
        }
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化文件
        todoLocalFile = new File("F://todo.txt");

        //不存在则创建
        if (!FileUtil.exist(todoLocalFile)) {
            try {
                todoLocalFile.createNewFile();
            } catch (IOException e) {
                throw new LocalFileException("文件创建失败");
            }
        }

        //读取最大索引
        //初始化索引
        List<TodoItem> localFileTodoItems = readAllTodoItemsInFile();
        //放入索引
        OptionalInt max = localFileTodoItems.stream()
                .mapToInt(TodoItem::getIndex)
                .max();
        max.ifPresent(x -> index.set(x));
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
    public List<TodoItem> saveAll(List<TodoItem> items) {
        for (TodoItem item : items) {
            this.save(item);
        }
        return items;
    }

    @Override
    public TodoItem save(TodoItem item) {
        if (Objects.nonNull(this.delegate)) {
            item = this.delegate.save(item);
        }

        //存到本地
        if (!FileUtil.exist(todoLocalFile)) {
            try {
                todoLocalFile.createNewFile();
            } catch (IOException e) {
                throw new LocalFileException("文件创建失败");
            }
        }
        String jsonTodoItem = JSONUtil.toJsonStr(item);
        FileUtil.appendUtf8String(jsonTodoItem + "\n", todoLocalFile);
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
        List<TodoItem> todoItems = null;
        if (Objects.nonNull(this.delegate)) {
            todoItems = this.delegate.listAll();
        }

        if (CollectionUtil.isEmpty(todoItems)) {
            if (FileUtil.exist(todoLocalFile)) {
                List<TodoItem> localFileTodoItems = readAllTodoItemsInFile();

                if (CollectionUtil.isNotEmpty(localFileTodoItems) && Objects.nonNull(this.delegate)) {
                    this.delegate.saveAll(localFileTodoItems);
                }

                return localFileTodoItems;
            }

        }
        return todoItems;
    }

    @Override
    public TodoItem done(Integer index) {
        AtomicReference<TodoItem> todoItem = new AtomicReference<>();
        if (Objects.nonNull(this.delegate)) {
            this.delegate.done(index);
        }

        //删除原数据
        if (!FileUtil.exist(todoLocalFile)) {
            return null;
        }

        List<TodoItem> all = readAllTodoItemsInFile();
        all.stream().filter(x -> Objects.equals(index, x.getIndex())).forEach(x -> {
            x.setIsDone(Boolean.TRUE);
            todoItem.set(x);
        });
        FileUtil.del(todoLocalFile);
        try {
            todoLocalFile.createNewFile();
        } catch (IOException e) {
            throw new LocalFileException("文件创建失败");
        }
        List<String> collect = all.stream()
                .map(JSONUtil::toJsonStr)
                .collect(Collectors.toList());
        FileUtil.appendUtf8Lines(collect, todoLocalFile);
        return todoItem.get();
    }

    /**
     * 从文件中读取所有todo
     *
     * @return
     */
    private List<TodoItem> readAllTodoItemsInFile() {
        if (FileUtil.exist(todoLocalFile)) {
            List<String> jsonArray = FileUtil.readUtf8Lines(todoLocalFile);
            return jsonArray.stream()
                    .map(x -> JSONUtil.toBean(x, TodoItem.class))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public List<TodoItem> listUndone() {
        List<TodoItem> todoItems = null;
        if (Objects.nonNull(delegate)) {
            todoItems = this.delegate.listUndone();
        }

        if (CollectionUtil.isEmpty(todoItems)) {
            if (FileUtil.exist(todoLocalFile)) {
                List<TodoItem> localFileTodoItems = readAllTodoItemsInFile();

                //只查询undone的
                List<TodoItem> collect = localFileTodoItems.stream()
                        .filter(x -> !x.getIsDone())
                        .collect(Collectors.toList());
                if (CollectionUtil.isNotEmpty(collect) && Objects.nonNull(delegate)) {
                    this.delegate.saveAll(collect);
                }

                return collect;
            }
        }
        return todoItems;
    }
}
