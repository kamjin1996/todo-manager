package com.kam.todo.bean;

import lombok.Data;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * 待做项Item
 * </p>
 */
@Data
public class TodoItem {

    private String content;

    private Integer index;

    private Boolean isDone;
}
