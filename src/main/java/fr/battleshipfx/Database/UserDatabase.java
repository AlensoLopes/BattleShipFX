package fr.battleshipfx.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDatabase extends DatabaseBuilder{

    public static void insertUser(String pseudo){
        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into bs_user values (" + CreateID.getUUID() + ", " + pseudo + ')';
            PreparedStatement preparedStatement = new UserDatabase().preparedStatement(query);
            int rows = preparedStatement.executeUpdate();
            if(rows == 0) System.out.println("good");
            databaseBuilder.closeConnection(null, preparedStatement, null);
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }

    private PreparedStatement preparedStatement(String query) throws SQLException {
        return new DatabaseBuilder().makeConnection().prepareStatement(query);
    }
}
