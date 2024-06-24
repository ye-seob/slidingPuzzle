package models;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

public class PuzzleModel {
    private Tile[] tiles;
    private int size;
    private int emptyIndex;
    private int moveCount;

    public PuzzleModel(int size) {
        this.size = size;
        this.tiles = new Tile[size * size];
        this.moveCount = 0;
        setNumTile();
    }

    public void setNumTile() {
        for (int i = 0; i < size * size - 1; i++) {
            tiles[i] = new Tile(i + 1, null);
        }
        tiles[size * size - 1] = new Tile(0, null);
        emptyIndex = size * size - 1;
    }


    public ImageIcon[] getTileIcons(Image image) {
        ImageIcon[] tileIcons = new ImageIcon[size * size - 1];
        
        int tileWidth = image.getWidth(null) / size;
        int tileHeight = image.getHeight(null) / size;
        
        for (int i = 0; i < size * size - 1; i++) {
            int x = (i % size) * tileWidth;
            int y = (i / size) * tileHeight;
            
            Image tileImage = Toolkit.getDefaultToolkit().createImage(
                new FilteredImageSource(image.getSource(), new CropImageFilter(x, y, tileWidth, tileHeight))
            );
            
            tileIcons[i] = new ImageIcon(tileImage);
        }
        return tileIcons;
    }


    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getNumber() == 0) {
                emptyIndex = i;
                break;
            }
        }
    }

    public void shuffle() {
        List<Tile> tileList = Arrays.asList(tiles);
        Collections.shuffle(tileList);
        tiles = tileList.toArray(new Tile[0]);
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getNumber() == 0) {
                emptyIndex = i;
                break;
            }
        }
        moveCount = 0;
    }

    public boolean moveTile(int index) {
        int emptyX = emptyIndex % size;
        int emptyY = emptyIndex / size;
        
        int targetX = index % size;
        int targetY = index / size;
        
        boolean canMove = (Math.abs(emptyX - targetX) + Math.abs(emptyY - targetY)) == 1;
        
        if (canMove) {
            Tile temp = tiles[emptyIndex];
            tiles[emptyIndex] = tiles[index];
            tiles[index] = temp;
            emptyIndex = index;
            moveCount++;
            return true;
        }
        return false;
    }

    public boolean isSolved() {
        for (int i = 0; i < size * size - 1; i++) {
            if (tiles[i].getNumber() != i + 1) {
                return false;
            }
        }
        return true;
    }

    public Tile[] getTiles() {
        return tiles;
    }

    public int getSize() {
        return size;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
