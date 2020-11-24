package src.Action;

import src.Displayable.Creature;

public class YouWin extends CreatureAction {
    public YouWin(String name, Creature owner) {
        super(owner);
        System.out.println("YouWin - const" + name);
    }
}
