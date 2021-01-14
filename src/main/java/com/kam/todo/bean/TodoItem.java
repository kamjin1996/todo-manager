package com.kam.todo.bean;

import lombok.Data;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * Todo项的Item
 * </p>
 */
@Data
public class TodoItem {

    /**
     * todo的内容体 任何非空字符串
     */
    private String content;

    /**
     * todo项的索引
     */
    private Integer index;

    /**
     * 是否已完成
     */
    private Boolean isDone = Boolean.FALSE;
}
