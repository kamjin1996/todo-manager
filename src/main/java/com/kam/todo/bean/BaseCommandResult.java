package com.kam.todo.bean;

import lombok.Data;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * 命令执行结果
 * </p>
 */
@Data
public abstract class BaseCommandResult {

    /**
     * 命令执行结果内容
     */
    private String content;

    /**
     * 命令执行退出结果
     */
    private String exitResult;

    public BaseCommandResult() {
    }

    public BaseCommandResult(String content, String exitResult) {
        this.content = content;
        this.exitResult = exitResult;
    }

    /**
     * 输出结果 实现为控制台打印
     */
    public void output() {
        System.out.println(content + "\n" + exitResult);
    }
}
