package src.Displayable;

public class Sword extends Item {
    private static String name;
    private static int room;
    private static int serial;

    public Sword(String _name) {
        super(_name);
    }

    public void setID(int _room, int _serial) {
        room = _room;
        serial = _serial;
    }
}
