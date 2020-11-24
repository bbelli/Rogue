package src.Displayable;

import java.util.ArrayList;

public class Passage extends Structure {
    private int room1;
    private int room2;
    private String name;
    private ArrayList<Integer> xs = new ArrayList();
    private ArrayList<Integer> ys = new ArrayList();

    public Passage() {
        //System.out.println("---Passage---");
    }

    public void setName(String n) {
        name = n;
    }

    public void setID(int _room1, int _room2) {
        room1 = _room1;
        room2 = _room2;
    }

    @Override
    public void setPosX(int x) {
        xs.add(x);
    }

    @Override
    public void setPosY(int y) {
        ys.add(y);
    }

    public ArrayList<Integer> getXs() {
        return xs;
    }

    public ArrayList<Integer> getYs() {
        return ys;
    }

    @Override
    public String toString() {
        String res = "";
        res = String.format("room1: %d room2: %d\n", room1, room2);
        for (int i=0; i < xs.size(); i++) {
            res += String.format("%d %d\n", xs.get(i), ys.get(i));
        }
        return res;
    }

    public char getCharacter() {
        return '#';
    }

}
