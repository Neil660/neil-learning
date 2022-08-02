package com.neil.designpattern.creational.lowuse.abstractfactory.eg1;

/**
 * @Decription 数据库工厂及功能接口
 * @Author Huang Chengyi
 * @Date 2022/8/2 13:58
 * @Version 1.0
 */
public interface IDatabaseFactory {
    IConnectionFactory getConnection();

    ICommandFactory getCommand();
}

// 数据库工厂--连接功能接口
interface IConnectionFactory {
    void connect();
}

// 数据库工厂--指令功能接口
interface ICommandFactory {
    void command();
}
