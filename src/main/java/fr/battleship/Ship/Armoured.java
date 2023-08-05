package fr.battleship.Ship;

import fr.battleship.Listeners.Warship;

public class Armoured extends Warship {
    public Armoured() {
        this.size = 4;
        this.style = "█";
    }

    public String getStyle(){
        return this.style;
    }
    public int getSize() {
        return this.size;
    }
    public String getName(){return "Battleship";}
}
