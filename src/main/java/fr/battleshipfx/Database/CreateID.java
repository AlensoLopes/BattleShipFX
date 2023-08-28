package fr.battleshipfx.Database;

import com.mysql.cj.util.StringUtils;
import fr.battleshipfx.Utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.UUID;

public class CreateID {

    private final String jsonFileLink = "src/main/resources/fr/battleshipfx/json/settings.json";

    public static void createUUIDForPlayer(){
        CreateID createID = new CreateID();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = createID.makeConnection();

            String macAddress = Utils.getMacAdress(InetAddress.getLocalHost());
            if(createID.generateID(macAddress) == null){
                System.out.println("Already have uuid");
                return;
            }

            String query = "INSERT INTO bs_mac_id values ('"+ macAddress+"','"+createID.generateID(macAddress)+"')";
            PreparedStatement statement = connection.prepareStatement(query);

            int rows = statement.executeUpdate();
            if(rows > 0) System.out.println("good");

            createID.closeConnection(connection, statement, null);
        } catch (ClassNotFoundException | SQLException | SocketException | UnknownHostException e) {
            System.err.println("An error occurred during the creation of the uuid, please try again.");
        }

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
            array[i] = String.valueOf(information.get(i)).replaceAll("\"", "")
                    .replaceAll("}", "").replaceAll("\\{", "");
        }
        return array;
    }

    private UUID generateID(String macAddress){
        if(isMacAddressAlreadyLinked(macAddress)) return null;
        return UUID.randomUUID();
    }

    private boolean isMacAddressAlreadyLinked(String macAddress){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = makeConnection();

            String query = "select count(*) as nb from bs_mac_id where mac_address = '" + macAddress + "'";
            ResultSet resultSet = prepareStatement(connection, query);

            if(countColumn(resultSet, "nb") == 0) return false;
            closeConnection(connection, null, resultSet);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private void closeConnection(Connection connection, Statement statement, ResultSet resultSet){
        try {
            if(connection != null) connection.close();
            if(statement != null) statement.close();
            if(resultSet != null) resultSet.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    private Connection makeConnection(){
        try {
            return DriverManager.getConnection(getDatabaseConnection()[0].split("url:")[1],
                    getDatabaseConnection()[1].split(":")[1], getDatabaseConnection()[2].split(":")[1]);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private int countColumn(ResultSet resultSet, String id){
        try {
            resultSet.next();
            return resultSet.getInt(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ResultSet prepareStatement(Connection connection, String query){
        try {
            return connection.createStatement().executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
