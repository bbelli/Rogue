package src.Action;

import src.Displayable.Creature;
import src.Displayable.Item;

public class Action {
    private String msg;
    private String name;
    private int intVal;
    private char charValue;
    private String type;

    public Action() {}
    public void setOwner(Creature owner) {
        //System.out.println("setOwner Creature");
    }
    public void setOwner(Item owner) {
        //System.out.println("setOwner Item");
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIntVal() {
        return intVal;
    }

    public void setIntVal(int intVal) {
        this.intVal = intVal;
    }

    public char getCharValue() {
        return charValue;
    }

    public void setCharValue(char charValue) {
        this.charValue = charValue;
    }
}
