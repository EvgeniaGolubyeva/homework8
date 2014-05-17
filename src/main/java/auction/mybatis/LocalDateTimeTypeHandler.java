package auction.mybatis;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.*;
import java.time.LocalDateTime;

/**
 * Created by gamussa.
 */

public class LocalDateTimeTypeHandler implements TypeHandler<LocalDateTime> {
    @Override
    public void setParameter(PreparedStatement ps, int i, LocalDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, Timestamp.valueOf(parameter));
    }

    @Override
    public LocalDateTime getResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnName);
        return ts.toLocalDateTime();
    }

    @Override
    public LocalDateTime getResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp ts = rs.getTimestamp(columnIndex);
        return ts.toLocalDateTime();
    }

    @Override
    public LocalDateTime getResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp ts = cs.getTimestamp(columnIndex);
        return ts.toLocalDateTime();
    }
}