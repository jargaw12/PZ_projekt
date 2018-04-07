package configuration;

import events.NewRecordEvent;
import events.listeners.BaseListener;
import panels.Frame;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;

public class Base {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet resultSet = null;
    private MyConfiguration configuration;
    List<BaseListener> baseListeners;

    public Base() {
        baseListeners = new ArrayList<>();
    }

    public void polacz() throws SQLException {
        configuration = new MyConfiguration();
        connection = DriverManager.getConnection(configuration.getValue("database"), configuration.getValue("user"), configuration.getValue("password"));
    }

    public ResultSet zapytanie(String selectSql) throws SQLException {
        statement = connection.createStatement();
        resultSet = statement.executeQuery(selectSql);
        return resultSet;
    }

    public void wstawienieWyniku(String name, int score) throws SQLException {
        String sql = "INSERT INTO wyniki([user], score, date)" + "VALUES ('" + name + "'," + score + ", getdate());";
        statement = connection.createStatement();
        if (sprawdzCzyRekord(score))
            fireNewRecord();
        statement.executeUpdate(sql);
    }

    public void rozlacz() {
        if (resultSet != null) try {
            resultSet.close();
        } catch (Exception e) {
            Logger.getInstance().logger.log(Level.WARNING, "Błąd rozłączania z bazą danych");
        }
        if (statement != null) try {
            statement.close();
        } catch (Exception e) {
        }
        if (connection != null) try {
            connection.close();
        } catch (Exception e) {
        }
    }

    boolean sprawdzCzyRekord(int score) {
        String sql = "SELECT TOP 1 score FROM wyniki ORDER BY score ASC";
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            int oldRecord=Integer.MAX_VALUE;
            //resultSet.first();
            while (resultSet.next()){
                oldRecord=resultSet.getInt("score");
            }
            if (score < oldRecord)
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void addBaseListener(BaseListener l) {
        baseListeners.add(l);
    }

    public void removeBaseListener(BaseListener l) {
        baseListeners.remove(l);
    }

    void fireNewRecord() {
        NewRecordEvent recordEvent = new NewRecordEvent(this);
        Iterator listeners = baseListeners.iterator();
        while (listeners.hasNext()) {
            ((BaseListener) listeners.next()).celebrateNewRecord(recordEvent);
        }
    }

}
