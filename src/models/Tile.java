package models;

import java.awt.Image;

public class Tile {
    private int number;
    private Image image;

    public Tile(int number, Image image) {
        this.number = number;
        this.image = image;
    }

    public int getNumber() {
        return number;
    }

    public Image getImage()	 {
        return image;
    }
}
