package fr.battleship.Ship;

import fr.battleship.Listeners.Warship;

public class Torpedo extends Warship {

    public Torpedo() {
        this.size = 2;
        this.style = "▄";
    }

    public String getStyle(){
        return this.style;
    }
    public int getSize() {
        return this.size;
    }

}
