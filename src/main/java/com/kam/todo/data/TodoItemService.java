package com.kam.todo.data;

import com.kam.todo.bean.TodoItem;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * todo项操作
 * </p>
 */
public interface TodoItemService {

    /**
     * 获取索引值
     *
     * @return
     */
    AtomicInteger getIndex();

    /**
     * 设置索引
     *
     * @param index
     */
    void setIndex(AtomicInteger index);

    /**
     * 保存todoItem
     *
     * @param items
     * @return
     */
    List<TodoItem> saveAll(List<TodoItem> items);

    /**
     * 保存todoItem
     *
     * @param item
     * @return
     */
    TodoItem save(TodoItem item);

    /**
     * 保存todoItem
     *
     * @param content
     * @return
     */
    TodoItem save(String content);

    /**
     * 查询所有
     *
     * @return
     */
    List<TodoItem> listAll();

    /**
     * 完成todo
     *
     * @param index
     * @return
     */
    TodoItem done(Integer index);

    /**
     * 查询未完成的
     *
     * @return
     */
    List<TodoItem> listUndone();
}
