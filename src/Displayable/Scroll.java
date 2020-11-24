package src.Displayable;

public class Scroll extends Item {
    private static int room;
    private static int serial;

    public Scroll(String _name) {
        super(_name);
    }

    public void setID(int _room, int _serial) {
        room = _room;
        serial = _serial;
    }

}
