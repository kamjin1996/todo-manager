package com.kam.todo.data.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.kam.todo.bean.TodoItem;
import com.kam.todo.data.TodoItemService;
import com.kam.todo.exception.LocalFileException;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todoitem本地存储服务实现类
 * </p>
 */
public class LocalFileStoreTodoItemServiceImpl implements TodoItemService {

    private static File todoLocalFile = new File("F://todo.txt");

    private TodoItemService delegate;

    public LocalFileStoreTodoItemServiceImpl() {
    }

    public LocalFileStoreTodoItemServiceImpl(TodoItemService todoItemService) {
        this.delegate = todoItemService;
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
        item = this.delegate.save(item);
        //存到本地
        if (!FileUtil.exist(todoLocalFile)) {
            try {
                boolean newFile = todoLocalFile.createNewFile();
                if (newFile) {
                    String jsonTodoItem = JSONUtil.toJsonStr(item);
                    FileUtil.appendString(jsonTodoItem, todoLocalFile, "utf-8");
                }
            } catch (IOException e) {
                throw new LocalFileException("文件创建失败");
            }
        }
        return item;
    }

    @Override
    public TodoItem save(String content) {
        TodoItem item = this.delegate.save(content);
        return this.save(item);
    }

    @Override
    public List<TodoItem> listAll() {
        List<TodoItem> todoItems = this.delegate.listAll();
        if (CollectionUtil.isEmpty(todoItems)) {
            List<String> jsonArray = FileUtil.readLines(todoLocalFile, "utf-8");
            List<TodoItem> localFileTodoItems = JSONUtil.toList(JSONUtil.parseArray(jsonArray), TodoItem.class);
            this.delegate.saveAll(localFileTodoItems);
            return localFileTodoItems;
        }
        return todoItems;
    }

    @Override
    public TodoItem done(Integer index) {
        TodoItem done = this.delegate.done(index);

        //删除原数据
        if (FileUtil.exist(todoLocalFile)) {
            //回写新数据
            List<String> all = FileUtil.readLines(todoLocalFile, "utf-8");
            all.stream().forEach(x -> {
                TodoItem todoItem = JSONUtil.toBean(x, TodoItem.class);
                if (Objects.isNull(todoItem)) {
                    return;
                }
                if (Objects.equals(index, todoItem.getIndex())) {
                    //更改并回写到文件
                }
            });


        }
        return done;

    }

    @Override
    public List<TodoItem> listUndone() {
        return null;
    }
}
