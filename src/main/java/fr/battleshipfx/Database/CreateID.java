package fr.battleshipfx.Database;

import fr.battleshipfx.Utils.Utils;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.UUID;

public class CreateID extends DatabaseBuilder{

    protected final String jsonFileLink = "src/main/resources/fr/battleshipfx/json/settings.json";

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


    public static String getUUID(){
        final CreateID createID = new CreateID();
        final String macAddress;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = createID.makeConnection();
            macAddress = Utils.getMacAdress(InetAddress.getLocalHost());
            String query = "select bs_id as uuid from bs_mac_id where mac_address = '" + macAddress + "'";
            ResultSet resultSet = createID.prepareStatement(connection, query);
            resultSet.next();
            return resultSet.getString("uuid");
        }catch (ClassNotFoundException | SocketException | UnknownHostException | SQLException e){
            System.err.println("An error occurred, the uuid may not exist or the mac address may not be found. " +
                    "Please try again.");
            return null;
        }
    }

    public static void main(String[] args) {
        createUUIDForPlayer();
        System.out.println(getUUID());
    }
}
