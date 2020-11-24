package src.Action;

import src.Displayable.Creature;

public class Remove extends CreatureAction {
    public Remove(String name, Creature owner) {
        super(owner);
        System.out.println("Remove - const");
    }
}
