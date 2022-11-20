package com.neil.spring.boot.redis.dao.impl;

import com.neil.spring.boot.redis.dao.SysLogDao;
import com.neil.spring.boot.redis.model.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Decription
 * @Author NEIL
 * @Date 2022/11/17 17:18
 * @Version 1.0
 */
@Slf4j
@Repository
public class SysLogDaoImpl implements SysLogDao {
    private final static String fields = "ID,USERNAME,OPERATION,TIME,METHOD,PARAMS,IP,CREATE_TIME";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int addSysLog(SysLog sysLog) {
        String sql = "INSERT INTO SYS_LOG(" + fields + ") VALUES (?,?,?,?,?,?,?,?)";
        int result = this.jdbcTemplate.update(
                sql,
                new Object[]{sysLog.getId(), sysLog.getUsername(), sysLog.getOperation(), sysLog.getTime(),
                        sysLog.getMethod(), sysLog.getParams(), sysLog.getIp(), sysLog.getCreateTime()});
        return result;
    }

    @Override
    public int delSysLog(int id) {
        String sql = "DELETE FROM SYS_LOG WHERE ID = ?";
        return this.jdbcTemplate.update(sql, id);
    }

    @Override
    public int updateSysLog(SysLog sysLog) {
        String sql = "UPDATE SYS_LOG(" + fields + ") VALUES (?,?,?,?,?,?,?,?)";
        int result = this.jdbcTemplate.update(
                sql,
                new Object[]{sysLog.getId(), sysLog.getUsername(), sysLog.getOperation(), sysLog.getTime(),
                        sysLog.getMethod(), sysLog.getParams(), sysLog.getIp(), sysLog.getCreateTime()},
                BeanPropertyRowMapper.newInstance(SysLog.class));
        return result;
    }

    // @Cacheable相关参数：
    // cacheNames/value ：用来指定缓存组件的名字
    // key ：缓存数据时使用的 key，可以用它来指定。默认是使用方法参数的值。（这个 key 你可以使用 spEL 表达式来编写）
    // keyGenerator ：key 的生成器。 key 和 keyGenerator 二选一使用
    // cacheManager ：可以用来指定缓存管理器。从哪个缓存管理器里面获取缓存。
    // condition ：可以用来指定符合条件的情况下才缓存
    // unless ：否定缓存。当 unless 指定的条件为 true ，方法的返回值就不会被缓存。当然你也可以获取到结果进行判断。（通过 #result 获取方法结果）
    // sync ：是否使用异步模式。
    // allEntries：是否清空所有缓存内容
    // 开启缓存
    @Cacheable(cacheNames = "getAllSysLog")
    @Override
    public List<SysLog> getAllSysLog() {
        log.info("start to get all sys_log data...");
        String sql = "SELECT " + fields + " FROM SYS_LOG";
        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SysLog.class));
    }

    // 刷新缓存
    @CacheEvict(value = "getAllSysLog", allEntries = true)
    @Override
    public void refreshGetAllSysLog() { }

    // 缓存更新
    @CachePut(value = "getSysLogById", key = "sysLog.id")
    @Override
    public SysLog cacheUpdateGetSysLogById(SysLog sysLog) {
        this.updateSysLog(sysLog);
        return sysLog;
    }

    @Cacheable(cacheNames = "getSysLogById", key = "#id")
    @Override
    public SysLog getSysLogById(int id) {
        String sql = "SELECT " + fields + " FROM SYS_LOG WHERE ID = ?";
        return this.jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                BeanPropertyRowMapper.newInstance(SysLog.class));
    }

    @CacheEvict(value = "getAllSysLog", allEntries = true)
    @Override
    public void refreshGetSysLogById() { }
}
