package com.termo.connection;


import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLPreparedStatement {
    void accept(PreparedStatement preparedStatement) throws SQLException;

}
