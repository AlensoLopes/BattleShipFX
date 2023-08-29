package fr.battleshipfx.Database;

import fr.battleship.Win.Win;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.Instant;

public class GameDatabase extends DatabaseBuilder{

    public static void insertGameInformation(int[] numberRound){
        GameDatabase gameDatabase = new GameDatabase();
        DatabaseBuilder databaseBuilder = new DatabaseBuilder();
        String[] informations = gameDatabase.getGameInformation(numberRound);
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String query = "insert into game values (" + informations[4] + ',' + informations[0] + ',' +
                    informations[2] + ',' +
                    informations[1] + ',' + informations[3] + ')';

            PreparedStatement preparedStatement = databaseBuilder.preparedStatement(query);
            int rows = preparedStatement.executeUpdate();
            if(rows == 0) System.out.println("good");
            databaseBuilder.closeConnection(null, preparedStatement, null);
        }catch (ClassNotFoundException | SQLException e){
            throw new RuntimeException(e);
        }
    }

    public String[] getGameInformation(int[] numberRound){
        String date = getDate();
        int player_round = numberRound[0];
        int bot_round = numberRound[1];
        String winner = getWinner();
        String uuid = CreateID.getUUID();
        return new String[]{date, String.valueOf(player_round), String.valueOf(bot_round), winner, uuid};
    }

    private String getDate(){
        return String.valueOf(Instant.now());
    }

    private String getWinner(){
        return Win.winner;
    }
}
