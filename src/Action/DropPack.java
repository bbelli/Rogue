package src.Action;


import src.Displayable.Creature;

public class DropPack extends CreatureAction {

    public DropPack(String name, Creature owner) {
        super(owner);
        System.out.println("DropPack - const");
    }
//    public void dropItem(int i){
//        int currentX = 0;
//        int currentY = 0;
//        int widthOff = dungeon.getPlayerRoomX();
//        int heightOff = dungeon.getPlayerRoomY() + Dungeon.getTopHeight();
//        currentX = Dungeon.player.getPosx() + widthOff;
//        currentY = Dungeon.player.getPosy() + heightOff;
//
//        try {
//            if ((i > Dungeon.player.getItems().size()) || i == 0) {
//                terminal.clear(' ', 7, height - 1, Dungeon.getWidth() - 8, 1);
//                terminal.write("No item in chosen slot!", 7, height - 1);
//                terminal.repaint();
//                return;
//            } else {
//                objectGrid[currentX][currentY].pop();
//                Displayable itemToDrop = Dungeon.player.getItems().get(i-1);
//                addObjectToDisplay(itemToDrop, currentX, currentY);
//                terminal.repaint();
//                Dungeon.player.removeItem(i-1);
//                terminal.clear(' ', 7, height - 3, Dungeon.getWidth() - 8, 1);
//            }
//            addObjectToDisplay(Dungeon.player, currentX, currentY);
//        } catch (Exception e) {
//            //System.out.println("Out of Bounds");
//        }
//    }
}
