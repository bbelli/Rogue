package src.Displayable;

import src.Action.Action;
import src.Action.CreatureAction;
import src.Dungeon;
import src.ObjectDisplayGrid;

import java.util.ArrayList;
import java.util.Random;

public class Creature extends Displayable {

    //public static String name;
    private CreatureAction deathaction;
    private CreatureAction hitaction;
    private ArrayList<Item> items = new ArrayList<>();
    private ArrayList<Item> equippedItems = new ArrayList<>();
    private ArrayList<Action> actions = new ArrayList<>();
    private ObjectDisplayGrid displayGrid = null;
    public int height = Dungeon.getGameHeight() + Dungeon.getTopHeight() + Dungeon.getBottomHeight();
    private int playerHit;
    private int enemyHit;
    private boolean hasArmor = false;
    private boolean hasSword = false;
    int newHpPlayer;
    int newHpEnemy;

    public Creature(){}

    public void addItem(Item it) {
        items.add(it);
    }
    public void removeItem(int i){
        items.remove(i);
    }
    public void removeItem(Item i){
        items.remove(i);
    }
    public void equipItem (Item it) {
        equippedItems.add(it);
    }
    public void unequipArmor(Item ar){
        equippedItems.remove(ar);
    }
    public void hasArmor (boolean a){
        hasArmor = a;
    }
    public void hasSword (boolean a){ hasSword = a; }
    public boolean getHasArmor(){
        return hasArmor;
    }
    public boolean getHasSword(){ return  hasSword;}
    public ArrayList<Item> getItems() {
        return items;
    }
    public ArrayList<Item> getEquippedItems() {
        return equippedItems;
    }
    public void setDeathAction(CreatureAction da) {
        deathaction = da;
    }
    public void setHitAction(CreatureAction ha) {
        hitaction = ha;
    }

    public void getDeathaction(ObjectDisplayGrid displayGrid) {
        // ChangeDisplay first
    }


    public void addAction(Action a) {
        System.out.println("hey");
        System.out.println(a.getName());
        actions.add(a);
    }

    public ArrayList<Action> getActions() {
        return actions;
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
    // puts damage on both sides, adjusts the new hps
    public void fight(Creature enemy) {
        int armorLeft = 0;
        Random rnd = new Random();
        playerHit = rnd.nextInt(this.getMaxhit()+1);
        enemyHit = rnd.nextInt(enemy.getMaxhit()+1);
//        if(enemy.getMaxhit() > 0) {
//            enemyHit = rnd.nextInt(enemy.getMaxhit());
//            return;
//        }

        if(getHasArmor()){
            int armorProtection = Dungeon.player.armorVal();
            System.out.println(armorProtection);
            armorLeft = armorProtection - getEnemyHit();
            Dungeon.player.getArmor().setIntValue(armorLeft);
            if(armorLeft >= 0){
                newHpPlayer = this.getHp();
            }
            else{
                Dungeon.player.removeItem(Dungeon.player.getArmor());
                Dungeon.player.hasArmor(false);
                Dungeon.player.unequipArmor(Dungeon.player.getArmor());
            }
        }
        else {
            newHpPlayer = Dungeon.player.getHp() - getEnemyHit();
        }
            newHpEnemy = enemy.getHp() - getPlayerHit();
        if (newHpPlayer <= 0) {
            System.out.println("player dies");
            Dungeon.player.setHp(0);
            return;
        } else {
            Dungeon.player.setHp(newHpPlayer);
        }
        if (newHpEnemy <= 0) {
            System.out.println("Creature died");
            enemy.setHp(0);
            return;
        }else {
            enemy.setHp(newHpEnemy);
        }
//        ObjectDisplayGrid.terminal.write("Player did "+Dungeon.player.getPlayerHit() + "hp damage!", 7, height - 1);
//        ObjectDisplayGrid.terminal.write("Monster did " + Dungeon.player.getEnemyHit() + "hp damage!" + " Monster Hp: " + enemy.getHp(), 30, height -1);
//        ObjectDisplayGrid.terminal.repaint();

        String damageInfo = String.format("Player did %d hp damage! Monster did %d hp damage! Monster Hp: %d",
                                            Dungeon.player.getPlayerHit(),Dungeon.player.getEnemyHit(), enemy.getHp());
        ObjectDisplayGrid.updateDisplay.writeToInfo(damageInfo);

        //DropItem
        Action dropItem = Dungeon.player.getAction("DropPack");
        if (dropItem != null) {
            //drop an item
            Random r = new Random();
            int len = Dungeon.player.getItems().size();
            if (len > 0) {
                //int drop = r.nextInt(len);
                //Dungeon.player.removeItem(drop);
                //Dungeon.player.getItems().remove(drop);
                ObjectDisplayGrid.fightDrop = true;
                //Player.fightDropInt = drop;
                //ObjectDisplayGrid
                //print dropPack msg
                ObjectDisplayGrid.updateDisplay.writeToInfo(dropItem.getMsg());

            }
        }

    }
    public int getPlayerHit(){
        return playerHit;
    }
    public int getEnemyHit(){
        return enemyHit;
    }

}
