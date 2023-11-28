package com.termo.connection;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface SQLResultAdapter<T> {

    T adapt(ResultSet resultSet) throws SQLException;

}
