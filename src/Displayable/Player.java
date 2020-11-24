package src.Displayable;

import src.Action.Action;
import src.Dungeon;
import src.ObjectDisplayGrid;

public class Player extends Creature {
    private static String name;
    private static Item weapon;
    private static Item armor;
    private static Item sword;
    private static int room;
    private static int serial;
    public static int halDuration;
    public static int fightDropInt;

    public void setName(String _name) {
        name = _name;
    }
    public void setID(int rm, int srl) {
        room = rm;
        serial = srl;
    }

    public int getRoom() {
        return room;
    }
    public void setWeapon(Item sword) {
        weapon = sword;
    }
    public void setArmor(Item _armor) {
        armor = _armor;
        //armor.setVisible();
        Dungeon.player.equipItem(armor);
    }
    public void setSword(Item _sword){
        sword = _sword;
        Dungeon.player.equipItem(sword);
    }
    public Item getArmor (){
        return armor;
    }
    public Item getWeapon (){
        return weapon;
    }
    public int armorVal (){
        return armor.getIntValue();
    }

    public void setHallucination(int val) {
        halDuration = val;
    }

    public void read(int i) { //just handles scrolls with hallucination
        if((i > Dungeon.player.getItems().size() || i == 0)){
            ObjectDisplayGrid.updateDisplay.clearInfo();
            ObjectDisplayGrid.updateDisplay.writeToInfo("No item in chosen slot!");
        } else {
            if (Dungeon.player.getItems().get(i-1) instanceof Scroll) {
                if (Dungeon.player.getItems().get(i-1).getAction("Hallucinate") != null) {
                    Scroll sc = (Scroll) Dungeon.player.getItems().get(i-1);
                    setHallucination(sc.getAction("Hallucinate").getIntVal());
                    ObjectDisplayGrid.removeItem(sc);
                    String s = String.format("Hallucination will continue for %d moves!", sc.getAction("Hallucinate").getIntVal());
                    ObjectDisplayGrid.updateDisplay.writeToInfo(s);
                    System.out.printf("hallucination began with %d rounds\n", sc.getAction("Hallucinate").getIntVal());

                } else if (Dungeon.player.getItems().get(i-1).getAction("BlessArmor") != null) {
                    Action blessCurse = Dungeon.player.getItems().get(i-1).getAction("BlessArmor");
                    if (blessCurse.getCharValue() == 'w') {
                        if (Dungeon.player.getHasSword()) {
                            int curse = blessCurse.getIntVal();
                            Item sword = Dungeon.player.getEquippedItems().get(0);
                            sword.setIntValue(sword.getIntValue() + curse);
                            System.out.println(sword.getIntValue());
                            String msg = String.format("Sword cursed! %d taken from its effectiveness", curse);
                            ObjectDisplayGrid.updateDisplay.writeToInfo(msg);
                        } else  {
                            ObjectDisplayGrid.updateDisplay.writeToInfo("Scroll of cursing does nothing" +
                                    " because Sword isn't wielded!");
                        }
                    } else if (blessCurse.getCharValue() == 'a') {
                        if (Dungeon.player.getHasArmor()) {
                            int curse = blessCurse.getIntVal();
                            Item armor = Dungeon.player.getEquippedItems().get(0);
                            armor.setIntValue(armor.getIntValue() + curse);
                            String msg = String.format("Armor cursed! %d taken from its effectiveness", curse);
                            ObjectDisplayGrid.updateDisplay.writeToInfo(msg);
                        } else {
                            ObjectDisplayGrid.updateDisplay.writeToInfo("Scroll of cursing does nothing" +
                                    " because Armor isn't worn!");
                        }
                    }
                } //TODO any other scroll type isn't given in the xml files

            } else {
                ObjectDisplayGrid.updateDisplay.writeToInfo("Specified item isn't a Scroll!");
            }
        }
    }
    public void wearArmor(int i){
        if((i > Dungeon.player.getItems().size() || i == 0)){
            ObjectDisplayGrid.updateDisplay.clearInfo();
            ObjectDisplayGrid.updateDisplay.writeToInfo("No item in chosen slot!");
        }
        else{
            if(!Dungeon.player.getHasArmor()){
                ObjectDisplayGrid.armor = Dungeon.player.getItems().get(i-1);
                if(ObjectDisplayGrid.armor.getCharacter() != ']'){
                    ObjectDisplayGrid.updateDisplay.clearInfo();
                    ObjectDisplayGrid.updateDisplay.writeToInfo("Item chosen is not Armor!");
                }
                else {
                    Dungeon.player.setArmor(ObjectDisplayGrid.armor);
                    ObjectDisplayGrid.armor.setOwner(Dungeon.player);
                    Dungeon.player.hasArmor(true);
                    Dungeon.player.removeItem(ObjectDisplayGrid.armor); //Remove armor from item array add to equipped array
                    ObjectDisplayGrid.updateDisplay.displayEquippedItems();
                    ObjectDisplayGrid.updateDisplay.updateHpPlayer(Dungeon.player.getHp());
                }
            }
            else{
                ObjectDisplayGrid.updateDisplay.clearInfo();
                ObjectDisplayGrid.updateDisplay.writeToInfo("Player already has armor!");
            }
        }
    }
    public void wearSword(int i) {
        if (i > Dungeon.player.getItems().size() || i == 0) {
            ObjectDisplayGrid.updateDisplay.clearInfo();
            ObjectDisplayGrid.updateDisplay.writeToInfo(("No item in chosen slot!"));
        } else {
            if (!Dungeon.player.getHasSword()) {
                ObjectDisplayGrid.sword = Dungeon.player.getItems().get(i - 1);
                if (ObjectDisplayGrid.sword.getCharacter() != ')') {
                    ObjectDisplayGrid.updateDisplay.clearInfo();
                    ObjectDisplayGrid.updateDisplay.writeToInfo("Item chosen is not Sword!");
                }
                else {
                    Dungeon.player.setSword(ObjectDisplayGrid.sword);
                    ObjectDisplayGrid.sword.setOwner(Dungeon.player);
                    Dungeon.player.hasSword(true);
                    //Dungeon.player.removeItem(ObjectDisplayGrid.sword); //Remove sword from item array add to equipped array
                    ObjectDisplayGrid.updateDisplay.displayEquippedItems();
                    ObjectDisplayGrid.updateDisplay.updateHpPlayer(Dungeon.player.getHp());
                }
            }
            else{
                ObjectDisplayGrid.updateDisplay.clearInfo();
                ObjectDisplayGrid.updateDisplay.writeToInfo("Player already has sword!");
            }

        }
    }
    public void changeArmor(){
         ObjectDisplayGrid.armor = Dungeon.player.getArmor();
        if(!Dungeon.player.getHasArmor()){
            ObjectDisplayGrid.updateDisplay.writeToInfo("No armor equipped!");
        }
        else{
            Dungeon.player.unequipArmor(ObjectDisplayGrid.armor);
            Dungeon.player.addItem(ObjectDisplayGrid.armor); //add to pack
            Dungeon.player.hasArmor(false);
            ObjectDisplayGrid.updateDisplay.displayEquippedItems();
            ObjectDisplayGrid.updateDisplay.updateHpPlayer(Dungeon.player.getHp());
        }

    }
    public int getFightDropInt() {return fightDropInt;}
    @Override
    public char getCharacter() {
        return '@';
    }
}
