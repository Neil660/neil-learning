package com.neil.designpattern.creational.lowuse.abstractfactory.eg1;

/**
 * @Decription 数据库工厂及功能具体实现类
 * @Author Huang Chengyi
 * @Date 2022/8/2 13:59
 * @Version 1.0
 */
public class MysqlDatabaseFactory implements IDatabaseFactory {
    @Override
    public IConnectionFactory getConnection() {
        return new MysqlConnectionFactory();
    }

    @Override
    public ICommandFactory getCommand() {
        return new MysqlCommandFactory();
    }
}

class MysqlConnectionFactory implements IConnectionFactory {
    @Override
    public void connect() {
        System.out.println("mysql connected.");
    }
}

class MysqlCommandFactory implements ICommandFactory {
    @Override
    public void command() {
        System.out.println("mysql command.");
    }
}
