package src.Action;

import src.Displayable.Creature;

public class Teleport extends CreatureAction {
    public Teleport(String name, Creature owner) {
        super(owner);
        System.out.println("Teleport - const" + name);
    }
}
