package src;

import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class KeyStrokePrinter implements InputObserver, Runnable {

    private static int DEBUG = 0;
    private static String CLASSID = "KeyStrokePrinter";
    private static Queue<Character> inputQueue = null;
    private final ObjectDisplayGrid displayGrid;
    public static boolean playerAlive = true;

    public KeyStrokePrinter(ObjectDisplayGrid grid) {
        inputQueue = new ConcurrentLinkedQueue<>();
        displayGrid = grid;
    }

    @Override
    public void observerUpdate(char ch) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".observerUpdate receiving character " + ch);
        }
        inputQueue.add(ch);
    }

    private void rest() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private boolean processInput() {

        char ch;

        boolean processing = true;
        while (processing) {

            if (inputQueue.peek() == null) { //if queue is empty
                processing = false;
            }
              else {
                ch = inputQueue.poll();
                if (DEBUG > 1) {
                    System.out.println(CLASSID + ".processInput peek is " + ch);
                }
                if (ch == 'X') {
                    System.out.println("got an X, ending input checking");
                    return false;
                } else if (Objects.equals(inputQueue.peek(), 'H')) {
                    ObjectDisplayGrid.updateDisplay.infoCommand(ch);
                    System.out.println("A");
                    inputQueue.remove(Character.toUpperCase('H'));
                    return true;
                }else if (ch == 'h') { //move left
                    displayGrid.move("left");
                } else if (ch == 'l') { //move right
                    displayGrid.move("right");
                } else if (ch == 'k') { //move up
                    displayGrid.move("up");
                } else if (ch == 'j') { //move down
                    displayGrid.move("down");
                } else if (ch == 'p') { //pick up item
                    displayGrid.pickItem();
                } else if (ch == 'd') { //drop item
                    inputQueue.add('d');
                } else if (ch == '`') { //Get stack elements, FOR DEBUG
                    System.out.println(displayGrid.printStack());
                } else if (ch == 'i') { //show inventory
                    ObjectDisplayGrid.updateDisplay.printInventory();
                } else if (ch == 'w') {
                    inputQueue.add('w');
                } else if (ch == 'c') { //change armor
                    Dungeon.player.changeArmor();
                } else if (ch == 'r') { //read scroll
                    System.out.println("R1");
                    inputQueue.add('r');
                } else if (ch == '?') { //help command (shows all commands in pdf)
                    ObjectDisplayGrid.updateDisplay.displayCommands();
                } else if(ch == 'T'){
                    System.out.println("T1");
                    inputQueue.add('T');
                } else if (ch == 'H'){
                    inputQueue.add('H');
                } else if(Objects.equals(inputQueue.peek(), 'd')){
                    inputQueue.remove('d');
                    displayGrid.dropItem(Character.getNumericValue(ch));
                    return true;
                } else if(Objects.equals(inputQueue.peek(), 'w')){
                    Dungeon.player.wearArmor(Character.getNumericValue(ch));
                    //displayGrid(Character.getNumericValue(ch));
                    inputQueue.remove('w');
                    return true;
                } else if(Objects.equals(inputQueue.peek(), 'T')){
                    System.out.println("T2");
                    Dungeon.player.wearSword(Character.getNumericValue(ch));
                    inputQueue.remove('T');
                } else if (Objects.equals(inputQueue.peek(), 'r')) {
                    Dungeon.player.read(Character.getNumericValue(ch));
                    inputQueue.remove('r');
                } else {
                    System.out.println("character " + ch + " entered on the keyboard");
                }
            }
        }
        return true;
    }

    @Override
    public void run() {
        displayGrid.registerInputObserver(this);
        boolean working = true;
        while (working && playerAlive) {
            rest();
            working = (processInput( ));
        }
    }
}
