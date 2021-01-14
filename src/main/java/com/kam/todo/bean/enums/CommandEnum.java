package com.kam.todo.bean.enums;

import lombok.Getter;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * 命令枚举
 * </p>
 */
@Getter
public enum CommandEnum {

    /**
     * _todo add 命令
     */
    TODO_ADD("todo add ", "todo add <item>") {
        @Override
        public Pattern getPattern() {
            return TODO_ADD_PATTERN;
        }
    },
    /**
     * _todo done 命令
     */
    TODO_DONE("todo done ", "todo done <itemIndex>") {
        @Override
        public Pattern getPattern() {
            return TODO_DONE_PATTERN;
        }
    },
    /**
     * _todo list 命令 支持可选参数
     */
    TODO_LIST("todo list", "todo list [option]--all") {
        @Override
        public Pattern getPattern() {
            return TODO_LIST_PATTERN;
        }
    },
    /**
     * 表示未定义命令
     */
    UNDEFINED("", "") {
        @Override
        public Pattern getPattern() {
            return null;
        }
    };

    private final static Pattern TODO_ADD_PATTERN = Pattern.compile("todo add [^\\s]{1,}");
    private final static Pattern TODO_DONE_PATTERN = Pattern.compile("todo done \\+?[1-9]\\d*$");
    private final static Pattern TODO_LIST_PATTERN = Pattern.compile("todo list( --all)?");

    /**
     * 命令前缀
     */
    private String commandPrifix;

    /**
     * 帮助内容
     */
    private String help;

    CommandEnum(String commandPrifix, String help) {
        this.commandPrifix = commandPrifix;
        this.help = help;
    }

    /**
     * 获取帮助内容
     *
     * @return
     */
    public static String obtainHelps() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (CommandEnum value : CommandEnum.values()) {
            String help = value.getHelp();
            if (!Objects.equals(help, "")) {
                sb.append(help);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * 获取输入内容
     *
     * @param input
     * @return
     */
    public String obtainContent(String input) {
        return input.substring(input.indexOf(this.getCommandPrifix()) + this.getCommandPrifix().length());
    }

    /**
     * 获取正则
     *
     * @return
     */
    public abstract Pattern getPattern();
}
