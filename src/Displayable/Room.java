package src.Displayable;

import java.util.ArrayList;

public class Room extends Structure {
    private int id;
    private ArrayList<Creature> creatures = new ArrayList<>();



    public Room(int _id) {
        id = _id;
    }

    public void setId(int room) {
        id = room;
    }

    public void setCreature(Creature creature) {
        creatures.add(creature);
    }

    public ArrayList<Creature> getCreatures() {
        return creatures;
    }

//    public void removeCreature(Creature cr) {
//        creatures.remove(cr);
//    }

    public Creature getCreature(int serial) {
        for (Creature c:creatures) {
            if (((Monster) c).getSerial() == serial) {
                return c;
            }
        }
        return null; //if not found
    }

    @Override
    public String toString() {
        String res ="";

        res = String.format("Room : %d\n" +
                "(x,y): (%d,%d)\n" +
                "width: %d\n" +
                "height: %d\n" +
                "#creatures: %d", id, getPosx(),getPosy(),getWidth(), getHeight(), creatures.size());


        return res;
    }
}
