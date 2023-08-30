package fr.battleshipfx.Database;

import fr.battleship.Win.Win;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

public class GameDatabase extends DatabaseBuilder{

    public static void insertGameInformation(int numberRound, int[] nbBoat){
        GameDatabase gameDatabase = new GameDatabase();
        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        String[] informations = gameDatabase.getGameInformation(numberRound, nbBoat);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into game(bs_id, game_date, game_nb_round, game_nb_boat_bot, game_nb_boat_player, " +
                    "game_winner) values ('"
                    + informations[0] + "', '" + informations[1] + "', '" +
                    informations[2] + "', '" + informations[3] + "', '" +
                    informations[4] + "', '" + informations[5] + "')";

            PreparedStatement preparedStatement = databaseBuilder.preparedStatement(query);
            int rows = preparedStatement.executeUpdate();
            if(rows == 0) System.out.println("good");
            databaseBuilder.closeConnection(null, preparedStatement, null);
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String[] getGameInformation(int numberRound, int[] nbBoat){
        return new String[]{CreateID.getUUID(), getDate().split("T")[0].replaceAll("-", "/"),
                String.valueOf(numberRound), String.valueOf(nbBoat[0]),
                String.valueOf(nbBoat[1]), getWinner()};
    }

    private String getDate(){
        return String.valueOf(Instant.now());
    }

    private String getWinner(){
        return Win.winner;
    }
}
