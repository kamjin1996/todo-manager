package com.kam.todo;

import com.kam.todo.bean.BaseCommandResult;
import com.kam.todo.bean.enums.CommandEnum;
import com.kam.todo.command.Command;
import com.kam.todo.command.CommandResolver;
import com.kam.todo.command.InputCommandResolver;
import org.apache.commons.lang3.StringUtils;

import java.util.Scanner;

/**
 * @author kam
 * @since 2021/1/13 0013
 *
 * <p>
 * APP入口
 * </p>
 */
public class TodoCommandApplication {

    public static void main(String[] args) {
        print("hi 欢迎使用todo命令行工具 支持的命令如下：" + CommandEnum.obtainHelps());
        print("----------------------------------------------------");

        CommandResolver<String> inputCommandResolver = new InputCommandResolver();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (scanner.hasNext()) {
                String inputLine = scanner.nextLine();

                //检查
                if (StringUtils.isBlank(inputLine)) {
                    continue;
                }

                //trim命令前后空格
                inputLine = inputLine.trim();

                //解析：拿到input 匹配正则 找到对应的命令
                Command<? extends BaseCommandResult> command = inputCommandResolver.resolver(inputLine);

                //处理：执行命令 得到结果
                BaseCommandResult result = command.exec(inputLine);

                //输出：打印结果
                result.print();
            }
        }
    }

    /**
     * 打印
     *
     * @param text
     */
    public static void print(String text) {
        System.out.println(text);
    }
}
