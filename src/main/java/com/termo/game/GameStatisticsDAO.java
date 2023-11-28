package com.termo.game;

import com.termo.connection.SQLConnection;
import com.termo.connection.SQLType;

import java.util.LinkedList;
import java.util.List;

public class GameStatisticsDAO {

    private final SQLConnection sqlConnection;

    public GameStatisticsDAO(SQLConnection sqlConnection) {
        this.sqlConnection = sqlConnection;

        sqlConnection.registerAdapter(new SQLType<List<GameStatistics>>() {
        }.getType(), resultSet -> {

            List<GameStatistics> statistics = new LinkedList<>();
            while (resultSet.next()) {
                statistics.add(new GameStatistics(
                        resultSet.getInt("size"),
                        resultSet.getInt("tries"),
                        resultSet.getBoolean("has_won"),
                        resultSet.getTimestamp("date_time").toLocalDateTime()
                ));
            }

            return statistics;
        });


        createTable();
    }

    private void createTable() {
        sqlConnection.execute("CREATE TABLE IF NOT EXISTS game_statistics (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "size INTEGER NOT NULL," +
                "tries INTEGER NOT NULL," +
                "has_won BOOLEAN NOT NULL," +
                "date_time TIMESTAMP NOT NULL" +
                ")");
    }

    public void insert(GameStatistics gameStatistics) {
        sqlConnection.execute("INSERT INTO game_statistics (size, tries, has_won, date_time) VALUES (?, ?, ?, ?)", statement -> {
            statement.setInt(1, gameStatistics.size());
            statement.setInt(2, gameStatistics.tries());
            statement.setBoolean(3, gameStatistics.hasWon());
            statement.setTimestamp(4, java.sql.Timestamp.valueOf(gameStatistics.dateTime()));
        });
    }

    public List<GameStatistics> getAll() {
        return sqlConnection.execute("SELECT * FROM game_statistics ORDER BY date_time ASC", new SQLType<List<GameStatistics>>() {
                })
                .orElse(new LinkedList<>());
    }


}
