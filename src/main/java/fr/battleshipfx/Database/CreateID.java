package fr.battleshipfx.Database;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CreateID {

    private final String jsonFileLink = "src/main/resources/fr/battleshipfx/json/settings.json";

    public static void createIDForPlayer(){
        String url = "jdbc:mysql://localhost:3306/bdd_battleship";
        String username = "admin";
        String password = "admin";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            String query = "INSERT INTO bs_user (bs_id, bs_pseudo) values ('test2', 'test2')";
            PreparedStatement statement = connection.prepareStatement(query);
            int rows = statement.executeUpdate();
            if(rows > 0) System.out.println("good");
            statement.close();
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private int getIDSize(){
        InputStream is;
        try{
            is = new FileInputStream(jsonFileLink);
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }
        JSONObject object = new JSONObject(new JSONTokener(is));
        return object.getInt("idSize");
    }

    private String[] getDatabaseConnection(){
        InputStream is;
        try{
            is = new FileInputStream(jsonFileLink);
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }

        JSONObject object= new JSONObject(new JSONTokener(is));
        JSONArray information = object.getJSONArray("database");

        String[] array = new String[information.length()];
        for (int i = 0; i < array.length; i++) {
            array[i] = String.valueOf(information.get(i));
        }
        return array;
    }

    public static void main(String[] args) {
        System.out.println(new CreateID().getDatabaseConnection()[2]);
    }
}
