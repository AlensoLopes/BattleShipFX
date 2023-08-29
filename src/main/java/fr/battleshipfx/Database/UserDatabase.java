package fr.battleshipfx.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDatabase extends DatabaseBuilder{

    public static void insertUser(String pseudo){
        GameDatabase gameDatabase = new GameDatabase();
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into bs_user values (" + CreateID.getUUID() + ", " + pseudo + ')';
            PreparedStatement preparedStatement = gameDatabase.preparedStatement(query);
            int rows = preparedStatement.executeUpdate();
            if(rows == 0) System.out.println("good");
            gameDatabase.closeConnection(null, preparedStatement, null);
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }

}
