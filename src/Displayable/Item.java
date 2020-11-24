
package src.Displayable;

import src.Action.Action;

import java.util.ArrayList;

public class Item extends Displayable {
    private Creature owner;
    private ArrayList<Action> actions = new ArrayList<>();
    private String name;

    public Item (String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String _n) {
        name = _n;
    }

    public void addAction(Action a) {
        actions.add(a);
    }

    public Action getAction(String name) {
        for (Action a:actions) {
            if (a.getName().equalsIgnoreCase(name)) {
                return a;
            }
        }
        System.out.println("Action cannot be found!");
        return null;
    }

    public void setOwner(Creature _owner) { //Is this required? Only player has items?
        owner = _owner;
    }
    public Creature getOwner(){
        return owner;
    }
}
