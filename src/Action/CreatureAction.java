
package src.Action;

import src.Displayable.Creature;

public class CreatureAction extends Action {

    public CreatureAction(){}

    @Override
    public void setOwner(Creature owner) {
        owner.addAction(this);
    }

    public CreatureAction(Creature owner) {
        System.out.println("setOwner Creature");

    }


}
