package com.neil.common.controller;

import com.neil.common.model.TestTable;
import com.neil.common.service.TestService;
import org.apache.ibatis.io.ResolverUtil.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/9/20 15:07
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    TestService service;

    @RequestMapping(value = "/getAllInfos")
    public List<TestTable> getAllInfos() {
        return service.getTestTable();
    }

    @RequestMapping(value = "/getById/{id}")
    public String getById(@PathVariable int id) {
        return service.getTestTableById(id).toString();
    }

/*    @RequestMapping(value = "/getById")
    public String getById2(@RequestParam("id") int id) {
        return service.getTestTableById(id).toString();
    }*/

    @RequestMapping(value = "/addTestTable")
    public int addTestTable(@RequestParam("testTable") TestTable testTable) {
        return service.addTestTable(testTable);
    }
}
