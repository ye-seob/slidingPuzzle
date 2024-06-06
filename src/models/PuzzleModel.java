package models;

import java.awt.Image;
import java.util.*;

public class PuzzleModel {
    private Tile[] tiles;
    private int size;
    private int emptyTileIndex;
    private int moveCount;

    public PuzzleModel(int size) {
        this.size = size;
        this.tiles = new Tile[size * size];
        initializeTiles();
        this.moveCount = 0; // 이동 횟수 초기화
    }

    private void initializeTiles() {
        for (int i = 0; i < size * size - 1; i++) {
            tiles[i] = new Tile(i + 1, null); // 숫자 퍼즐로 초기화
        }
        tiles[size * size - 1] = new Tile(0, null); // 빈 타일
        emptyTileIndex = size * size - 1;
    }

    public void setTiles(Tile[] tiles) {
        this.tiles = tiles;
        for (int i = 0; i < tiles.length; i++) {
            if (tiles[i].getNumber() == 0) {
                emptyTileIndex = i;
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
                emptyTileIndex = i;
                break;
            }
        }
        moveCount = 0; // 섞을 때 이동 횟수 초기화
    }

    public boolean moveTile(int index) {
        if (isValidMove(index)) {
            Tile temp = tiles[emptyTileIndex];
            tiles[emptyTileIndex] = tiles[index];
            tiles[index] = temp;
            emptyTileIndex = index;
            moveCount++; // 이동 횟수 증가
            return true;
        }
        return false;
    }

    private boolean isValidMove(int index) {
        int row = index / size;
        int col = index % size;
        int emptyRow = emptyTileIndex / size;
        int emptyCol = emptyTileIndex % size;

        return (Math.abs(row - emptyRow) == 1 && col == emptyCol) ||
               (Math.abs(col - emptyCol) == 1 && row == emptyRow);
    }

    public boolean isSolved() {
        for (int i = 0; i < size * size - 1; i++) {
            if (tiles[i].getNumber() != i + 1) {
                return false;
            }
        }
        return tiles[size * size - 1].getNumber() == 0;
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
