package src;


import src.Displayable.*;

import java.util.ArrayList;

public class Dungeon {
    String name;
    private static int width;
    private static int gameHeight;
    private static int topHeight;
    private static int bottomHeight;
    public static Player player;
    private static int playerRoomX;
    private static int playerRoomY;


    //public static Dungeon dungeon;

    private static final ArrayList<Room> rooms = new ArrayList<>();
    private static final ArrayList<Passage> passages = new ArrayList<>();
    private static final ArrayList<Creature> creatures = new ArrayList<>();
    private static final ArrayList<Item> items = new ArrayList<>();

    public Dungeon() {
        //System.out.println("Dungeon - constructor");
    }

    public void addPlayer(Player pl) {
        player = pl;
    }

    public void setPlayerRoom(int x, int y) {
        playerRoomX = x;
        playerRoomY = y;
    }

    public int getPlayerRoomX() {
        return playerRoomX;
    }

    public int getPlayerRoomY() {
        return playerRoomY;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Dungeon.width = width;
    }

    public static int getGameHeight() {
        return gameHeight;
    }

    public static void setGameHeight(int gameHeight) {
        Dungeon.gameHeight = gameHeight;
    }

    public static int getTopHeight() {
        return topHeight;
    }

    public static void setTopHeight(int topHeight) {
        Dungeon.topHeight = topHeight;
    }

    public static int getBottomHeight() {
        return bottomHeight;
    }

    public static void setBottomHeight(int bottomHeight) {
        Dungeon.bottomHeight = bottomHeight;
    }

    public String getName() {
        return name;
    }

    public void addRoom(Room rm) {
        rooms.add(rm);
    }

    public void addCreature(Creature cr) {
        creatures.add(cr);
    }

    public void addPassage(Passage psg) {
        passages.add(psg);
    }

    public void addItem(Item itm) {
        items.add(itm);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public ArrayList<Passage> getPassages() {
        return passages;
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public Creature getCreature(int x, int y) {
        // x and y are coordinates relative to the room which the creature is inside
        for (Creature cr : creatures) {
            if (x == cr.getPosx() && y == cr.getPosy()) {
                return cr;
            }
        }
        return null; //if not found
    }

//    public void removeCreature(Creature cr) {
//        for (Room r:rooms) {
//            for (Creature creture : r.getCreatures()) {
//                if (cr == creture)
//                    r.removeCreature(cr);
//            }
//        }
//    }

}
