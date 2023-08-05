package fr.battleship.Ship;

import fr.battleship.Listeners.Warship;

public class Submarine extends Warship {

    public Submarine() {
        this.size = 1;
        this.style = "■";
    }

    public String getStyle(){
        return this.style;
    }
    public int getSize() {
        return this.size;
    }
    public String getName(){return "Submarine";}
}
