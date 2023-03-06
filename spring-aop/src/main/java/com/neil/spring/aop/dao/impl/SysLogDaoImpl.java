package com.neil.spring.aop.dao.impl;

import com.neil.spring.aop.model.SysLog;
import com.neil.spring.aop.dao.SysLogDao;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Override
    public List<SysLog> getAllSysLog() {
        String sql = "SELECT " + fields + " FROM SYS_LOG";
        return this.jdbcTemplate.query(sql, BeanPropertyRowMapper.newInstance(SysLog.class));
    }

    @Override
    public SysLog getSysLogById(int id) {
        String sql = "SELECT " + fields + " FROM SYS_LOG WHERE ID = ?";
        return this.jdbcTemplate.queryForObject(
                sql,
                new Object[]{id},
                BeanPropertyRowMapper.newInstance(SysLog.class));
    }
}
