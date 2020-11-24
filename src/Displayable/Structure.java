package src.Displayable;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Structure extends Displayable {
    private ConcurrentLinkedQueue<Item> items = new ConcurrentLinkedQueue<>();
    public ConcurrentLinkedQueue<Item> getItems() {
        return items;
    }

    public void addItem(Item it) {
        items.add(it);
    }
    public void removeItem(Item it) {
        items.remove(it);
    }
}
