package com.changfa.frame.core.util;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MyBatisDecimal转换器
 * Created by wyy on 2017/2/21.
 */
public class DecimalHandler implements TypeHandler<BigDecimal> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, BigDecimal bigDecimal, JdbcType jdbcType) throws SQLException {
        preparedStatement.setBigDecimal(i, bigDecimal);
    }

    @Override
    public BigDecimal getResult(ResultSet resultSet, String s) throws SQLException {
        return transfer(resultSet.getString(s));
    }

    @Override
    public BigDecimal getResult(ResultSet resultSet, int i) throws SQLException {
        return transfer(resultSet.getString(i));
    }

    @Override
    public BigDecimal getResult(CallableStatement callableStatement, int i) throws SQLException {
        return transfer(callableStatement.getString(i));
    }

    private BigDecimal transfer(String val) {
        if (val != null) {
            val = val.replace(",", "");
            return new BigDecimal(val);
        }
        return null;
    }

}
