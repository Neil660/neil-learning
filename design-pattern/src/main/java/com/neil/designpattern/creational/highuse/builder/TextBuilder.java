package com.neil.designpattern.creational.highuse.builder;

/**
 * @Decription 角色--具体的建造者。负责实现Builder角色的API的类
 * @Author Huang Chengyi
 * @Date 2022/8/3 16:14
 * @Version 1.0
 */
class TextBuilder extends Builder {
    private StringBuffer sb = new StringBuffer();

    @Override
    public void makeTitle(String title) {
        sb.append(title); // 加标题
    }

    @Override
    public void makeString(String str) {
        sb.append(str); // 加内容
    }

    @Override
    public void makeItems(String[] items) {
        for (int i = 0; i < items.length; i++) {
            sb.append(items[i]).append("\n");
        }
    }

    @Override
    public void close() {
        System.out.println("完成");
    }

    public String getResult() {
        return sb.toString();
    }
}
