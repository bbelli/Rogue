package src.Displayable;

public class Armor extends Item {
    private static int room;
    private static int serial;

    public Armor(String _name) {
        super(_name);
    }


    public void setId(int _room, int _serial) {
        room = _room;
        serial = _serial;
    }

}
