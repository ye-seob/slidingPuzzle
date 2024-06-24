package models;

public class Score { 
    private String name;
    private int time;
    private int move; 

    public Score(String name, int time, int moves) { 
        this.name = name;
        this.time = time; 
        this.move = moves; 
    }

    public String getName() { 
        return name; 
    }

    public int getTime() { 
        return time;
    }

    public int getMoves() { 
        return move; 
    }
    
    @Override
    public String toString() { 
        return name + "," + time + "," + move;
    }

    public static Score fromString(String str) { 
        String[] parts = str.split(",");
        return new Score(parts[0], Integer.parseInt(parts[1]), Integer.parseInt(parts[2])); 
    }
}
