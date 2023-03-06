package com.neil.mybatis.controller;

import com.neil.mybatis.model.TestTable;
import com.neil.mybatis.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/9/20 15:07
 * @Version 1.0
 */
// swagger API注解
@Api(value = "test Controller")
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    TestService service;

    @PostMapping(value = "/addTestTable")
    public int addTestTable(@RequestParam("testTable") TestTable testTable) {
        return service.addTestTable(testTable);
    }

    @PostMapping(value = "/addTestTable2")
    public int addTestTable2() {
        return service.addTestTable2();
    }

    @ApiOperation(value = "获取用户信息", notes = "通过ID获取用户信息")
    @ApiImplicitParam(name = "id", value = "测试表ID",required = true, dataType = "Integer", paramType = "path")
    @GetMapping(value = "/getById/{id}")
    public String getById(@PathVariable int id) {
        return service.getTestTableById(id).toString();
    }

    @GetMapping(value = "/getByName/{name}")
    public List<TestTable> getByName(@PathVariable String name) {
        return service.getTestTableByName(name);
    }

    // swagger注解
    @ApiOperation(value = "获取所有用户", notes = "获取所有用户")
    @GetMapping(value = "/getAllInfos")
    public List<TestTable> getAllInfos() {
        return service.getTestTable();
    }

    // TODO Oracle配置没加载进去
    @RequestMapping(value = "/getAllInfosByOracle")
    @ApiIgnore // 忽略
    public List<TestTable> getAllInfosByOracle() {
        return service.getTestTableByOracle();
    }

    @GetMapping(value = "/get")
    public List<TestTable> get() {
        return service.get();
    }
}
