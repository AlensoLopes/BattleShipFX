package fr.battleship.Listeners;

public class ProcessInput extends InputUser{

    protected String processInputTypeShip(){
        int t = reqTypeShip();
        String tmp = "";
        switch (t) {
            case 1 -> tmp = "A";
            case 2 -> tmp = "B";
            case 3 -> tmp = "C";
            case 4 -> tmp = "D";
            default -> {
            }
        }
        return tmp;
    }

    protected String processInputAxis(){
        int s = reqAxis();
        String sens = "";
        switch (s){
            case 1 -> sens = "V";
            case 2 -> sens = "H";
            default -> {

            }
        }
        return sens;
    }
}
