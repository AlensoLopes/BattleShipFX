package fr.battleship.Ship;

import fr.battleship.Listeners.Warship;

public class Cruiser extends Warship {
    public Cruiser() {
        this.size = 3;
        this.style = "▀";
    }

    public String getStyle(){
        return this.style;
    }
    public int getSize() {
        return this.size;
    }
    public String getName(){return "Cruiser";}
}
