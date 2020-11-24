package src.Action;

import asciiPanel.AsciiPanel;
import src.Displayable.Armor;
import src.Displayable.Char;
import src.Displayable.Creature;
import src.Displayable.Sword;
import src.Dungeon;
import src.ObjectDisplayGrid;

public class UpdateDisplay extends CreatureAction {
    public int height = Dungeon.getGameHeight() + Dungeon.getTopHeight() + Dungeon.getBottomHeight();
    public UpdateDisplay(String name, Creature owner) {
        super(owner);
        System.out.println("UpdateDisplay - const" + name);
    }
    public UpdateDisplay() {

    }
    public void writeToInfo(String info){
        clearInfo();
        int offsetX = 7;
        ObjectDisplayGrid.terminal.write(info, offsetX, height -1);
        ObjectDisplayGrid.terminal.repaint();
    }
    public void writeToPack(String pack){
        int offsetX = 7;
        ObjectDisplayGrid.terminal.write(pack, offsetX, height -1);
        ObjectDisplayGrid.terminal.repaint();
    }
    public void clearPack(){
        int offsetX = 7;
        ObjectDisplayGrid.terminal.clear(' ', offsetX,  height-3, Dungeon.getWidth() - 8, 1);
        ObjectDisplayGrid.terminal.repaint();
    }
    public void clearInfo(){
        int offsetX = 7;
        ObjectDisplayGrid.terminal.clear(' ', offsetX,  height-1, Dungeon.getWidth() - 8, 1);
        ObjectDisplayGrid.terminal.repaint();
    }
    public void clearEquipped(){
        ObjectDisplayGrid.terminal.clear(' ', 12, 0, 20, 1);
        ObjectDisplayGrid.terminal.repaint();
    }
    public void updateHpPlayer(int hp){
        ObjectDisplayGrid.terminal.clear(' ', 4, 0, 3, 1);
        ObjectDisplayGrid.terminal.write("HP: ", 0, 0);
        if(hp > 70){
            ObjectDisplayGrid.terminal.write(""+hp, 4, 0, AsciiPanel.brightGreen);
        }
        else if((hp < 70) && (hp > 20)){
            ObjectDisplayGrid.terminal.write(""+hp, 4, 0, AsciiPanel.brightYellow.darker());
        }
        else if((hp <= 20) && (hp > 0)){
            ObjectDisplayGrid.terminal.write(""+hp, 4, 0, AsciiPanel.brightRed);
        }
        else if(hp == 0){
            //DEBUG FOR IF PLAYER IS DEAD
            ObjectDisplayGrid.terminal.write("HP: 0", 0, 0, AsciiPanel.brightRed);
        }
        if(Dungeon.player.getHasArmor()){
            //ObjectDisplayGrid.terminal.write(""+Dungeon.player.getArmor().getIntValue() + ' ', 8, 0, AsciiPanel.brightBlue);
        }
        else{
            ObjectDisplayGrid.terminal.clear(' ',8,0, 3, 1);
        }
        ObjectDisplayGrid.terminal.repaint();
    }

    public void printInventory(){
        int length = Dungeon.player.getItems().size();
        int x = 7;
        Char idx;
        if(length == 0) {
            ObjectDisplayGrid.terminal.write("You have no items to display!", 7, height - 3);
        }
        else {
            ObjectDisplayGrid.terminal.clear(' ', 7, height -3, Dungeon.getWidth() - 8, 1);
            for (int i = 0; i < length; i++) {
                idx = new Char(Character.forDigit(i+1,10));
                ObjectDisplayGrid.terminal.write(idx.getCharacter(), x, height - 3);
                x++;
                ObjectDisplayGrid.terminal.write("-", x, height - 3);
                x++;
                ObjectDisplayGrid.terminal.write(Dungeon.player.getItems().get(i).getCharacter(), x, height - 3, AsciiPanel.brightCyan.darker());
                x++;

                if (Dungeon.player.getItems().get(i) instanceof Sword || Dungeon.player.getItems().get(i) instanceof Armor) {
//                    String itemName = Dungeon.player.getItems().get(i).getName();
//                    String strength = itemName.split(" ")[0];
//                    System.out.println(strength);
//                    ObjectDisplayGrid.terminal.write(strength, x, height - 3);
//                    x += strength.length() + 1;
                    int itemIntVal = Dungeon.player.getItems().get(i).getIntValue();
                    String strength = String.format("+%d", itemIntVal);
                    ObjectDisplayGrid.terminal.write(strength, x, height - 3);
                    x += strength.length() + 1;
                } else {
                    ObjectDisplayGrid.terminal.write(" ", x, height - 3);
                    x++;
                }

            }
            ObjectDisplayGrid.terminal.clear(' ', x-1, height -3, 1, 1); //Removes the last "-"
        }
        ObjectDisplayGrid.terminal.repaint();
    }
    public void displayEquippedItems(){
        int scoreOffsetx = 15;
        int x= scoreOffsetx + 11;
        ObjectDisplayGrid.terminal.clear(' ', x, 0, 10, 1);
        ObjectDisplayGrid.terminal.write( "Equipped: ", scoreOffsetx, 0);
        for (int i = 0; i < Dungeon.player.getEquippedItems().size(); i++) {
            ObjectDisplayGrid.terminal.write("" + Dungeon.player.getEquippedItems().get(i).getCharacter(), x, 0);
            x++;
        }
        ObjectDisplayGrid.terminal.repaint();
    }
    public void displayCommands(){
        writeToInfo("Commands: c, d, i, p, r, w, E, H, T Movement: h, j, k, l");
    }
    public void infoCommand(char c){
        clearInfo();
        if(c == 'c'){
            writeToInfo("c: Change or take off armor");
        } else if(c == 'd'){
            writeToInfo("d: Drop item from pack");
        } else if(c == 'i'){
            writeToInfo("i: View pack/inventory");
        } else if(c == 'p'){
            writeToInfo("p: Pick item from ground put in pack");
        } else if(c == 'r'){
            writeToInfo("r: Read an item (must be scroll)");
        } else if(c == 'w'){
            writeToInfo("w: Wear item (must be armor or sword)");
        } else if(c == 'E'){
            writeToInfo("E: End Game");
        } else if(c == 'H'){
            writeToInfo("H: Get detailed information on commands");
        } else if(c == 'T'){
            writeToInfo("T: Take out weapon (must be sword)");
        } else if(c == 'h'){
            writeToInfo("h: Move left");
        } else if(c == 'j'){
            writeToInfo("j: Move down");
        } else if(c == 'k'){
            writeToInfo("k: Move up");
        } else if(c == 'l'){
            writeToInfo("l: Move right");
        }
    }
}
