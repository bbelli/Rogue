package src.Action;


import src.Displayable.Creature;

public class EndGame extends CreatureAction {
    public EndGame(String name, Creature owner) {
        super(owner);
        System.out.println("EndGame - const" + name);
    }
}
