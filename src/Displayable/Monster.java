package src.Displayable;

public class Monster extends Creature {
    private static int roomId;
    private static String name;
    private static int serial;

    public Monster() {}

    public void setName(String n) {
        name = n;
    }

    public void setID(int room, int serial) {
        roomId = room;
        this.serial = serial;
    }

    public int getSerial() {
        return serial;
    }

    @Override
    public char getCharacter() {
        return getType(); // T/S/H
    }
}
