package fr.battleshipfx.Database;


import com.mysql.jdbc.Driver;

public class CreateID {

    public static void createIDForPlayer(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
