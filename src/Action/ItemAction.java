package src.Action;

import src.Displayable.Item;

public class ItemAction extends Action {
    public ItemAction() {}
    public ItemAction(Item owner) {
        owner.addAction(this);
    }

    @Override
    public void setOwner(Item owner) {

        System.out.println("setOwner Item");
        owner.addAction(this);
    }
}
