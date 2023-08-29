package fr.battleshipfx.Database;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.*;

public class DatabaseBuilder {

    private String[] getDatabaseConnection(){
        InputStream is;
        try{
            is = new FileInputStream(new CreateID().jsonFileLink);
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        JSONObject object= new JSONObject(new JSONTokener(is));
        JSONArray information = object.getJSONArray("database");

        String[] array = new String[information.length()];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(information.get(i)).replaceAll("\"", "")
                    .replaceAll("}", "").replaceAll("\\{", "");
        }
        return array;
    }

    protected void closeConnection(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(connection != null) connection.close();
            if(statement != null) statement.close();
            if(resultSet != null) resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected Connection makeConnection(){
        try {
            return DriverManager.getConnection(getDatabaseConnection()[0].split("url:")[1],
                    getDatabaseConnection()[1].split(":")[1], getDatabaseConnection()[2].split(":")[1]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected int countColumn(ResultSet resultSet, String id){
        try {
            resultSet.next();
            return resultSet.getInt(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected ResultSet prepareStatement(Connection connection, String query){
        try {
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    protected PreparedStatement preparedStatement(String query) throws SQLException {
        return new DatabaseBuilder().makeConnection().prepareStatement(query);
    }
}
