package src;
import asciiPanel.AsciiPanel;
import src.Action.UpdateDisplay;
import src.Action.Action;
import src.Displayable.*;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ObjectDisplayGrid extends JFrame implements KeyListener, InputSubject {

    private static final int DEBUG = 0;
    private static final String CLASSID = ".ObjectDisplayGrid";

    //private static AsciiPanel terminal; a
    public static AsciiPanel terminal;
    private static Dungeon dungeon = null;
    private Stack<Displayable>[][] objectGrid = null;
    public ArrayList<Item> printedItems = new ArrayList<>();
    private List<InputObserver> inputObservers = null;
    public static UpdateDisplay updateDisplay = null;
    public static Item armor = new Armor("Ar");
    public static Item sword = new Sword("Sw");
    private static int height;
    private static int width;
    private int moveCounter;
    private int halRoundCounter = 0;


    public ObjectDisplayGrid(Dungeon _dungeon) {
        dungeon = _dungeon;
        width = Dungeon.getWidth();
        height = Dungeon.getBottomHeight() + Dungeon.getGameHeight() + Dungeon.getTopHeight();
        updateDisplay = new UpdateDisplay();
        terminal = new AsciiPanel(width, height);
        objectGrid = (Stack<Displayable>[][]) new Stack[width][height]; //TODO: change height to gameHeight

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                objectGrid[i][j] = new Stack<Displayable>();
            }
        }

        super.add(terminal);
        super.setSize(width * 9, height * 16);
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
        terminal.setVisible(true);
        super.addKeyListener(this);
        inputObservers = new ArrayList<>();
        super.repaint();
    }
    @Override
    public void registerInputObserver(InputObserver observer) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".registerInputObserver " + observer.toString());
        }
        inputObservers.add(observer);
    }
    @Override
    public void keyTyped(KeyEvent e) {
        if (DEBUG > 0) {
            System.out.println(CLASSID + ".keyTyped entered" + e.toString());
        }
        KeyEvent keypress = (KeyEvent) e;
        notifyInputObservers(keypress.getKeyChar());
    }

    private void notifyInputObservers(char ch) {
        for (InputObserver observer : inputObservers) {
            observer.observerUpdate(ch);
            if (DEBUG > 0) {
                System.out.println(CLASSID + ".notifyInputObserver " + ch);
            }
        }
    }
    // we have to override, but we don't use this
    @Override
    public void keyPressed(KeyEvent even) {
    }
    // we have to override, but we don't use this
    @Override
    public void keyReleased(KeyEvent e) {
    }

    public final void initializeDisplay() {
        terminal.write("Pack: ", 0, height - 3); //Offsetx = 7;
        terminal.write("Info: ", 0, height - 1); //Offsetx = 7;
        updateDisplay.updateHpPlayer(Dungeon.player.getHp());
        terminal.repaint();
        for (Room rm: dungeon.getRooms()) {
            paintRoom(rm);
        }
        for (Passage psg: dungeon.getPassages()) {
            paintPassage(psg);
        }
        terminal.repaint();
    }

    public void paintRoom(Room room) {
        int heightOffsetTop = Dungeon.getTopHeight();
        int widthOffset = room.getPosx();
        int heightOffset = room.getPosy() + heightOffsetTop;

        int width = room.getWidth();
        int height = room.getHeight();
        Displayable floor = new Floor();
        Displayable wall = new Wall();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //walls
                if (i == 0 || i == width-1 || j == 0 || j == height-1) {
                    addObjectToDisplay(wall, i+widthOffset, j+heightOffset);
                } else { //floors
                    addObjectToDisplay(floor, i+widthOffset , j+heightOffset);
                }
            }
        } //end for
        // Paint creatures
        for (Creature cr: room.getCreatures()) {
            addObjectToDisplay(cr, cr.getPosx()+widthOffset, cr.getPosy()+heightOffset);

        }
        // Paint items
        for (Item it: room.getItems()) {
            addObjectToDisplay(it, it.getPosx()+widthOffset, it.getPosy()+heightOffset);
            printedItems.add(it);
        }
    }

    public void paintPassage(Passage psg) {
        Char hashtag = new Char('#');
        Char plus = new Char('+');
        int heightOffsetTop = Dungeon.getTopHeight();
        int x1, x2;
        int y1, y2;

        for (int i = 0; i < psg.getXs().size()-1; i++) {
            x1 = psg.getXs().get(i);
            y1 = psg.getYs().get(i);
            x2 = psg.getXs().get(i+1);
            y2 = psg.getYs().get(i+1);

            //adjust increasing trend
            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }
            if (y1 > y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }

            if (x1 == x2) { //paint along y axis
                for (int j = y1; j != y2+1; j++) {
                    if ((!(objectGrid[x1][j+heightOffsetTop].isEmpty()))&&(objectGrid[x1][j+heightOffsetTop].peek().getCharacter() == 'X')) {
                        addObjectToDisplay(plus, x1, j+heightOffsetTop);
                    } else {
                        addObjectToDisplay(hashtag, x1, j+heightOffsetTop);
                    }
                }
            } else if (y1 == y2) { //paint along x axis
                for (int k = x1; k != x2+1; k++) {
                    if ((!objectGrid[k][y1+heightOffsetTop].isEmpty())&&(objectGrid[k][y1+heightOffsetTop].peek().getCharacter() == 'X')) {
                        addObjectToDisplay(plus, k, y1+heightOffsetTop);
                    } else {
                        addObjectToDisplay(hashtag, k, y1+heightOffsetTop);
                    }
                }
            }
        }
    }

    public void setPlayerDisplay(Char c) {
        int widthOff = dungeon.getPlayerRoomX();
        int heightOff = dungeon.getPlayerRoomY() + Dungeon.getTopHeight();
        int currentX = Dungeon.player.getPosx() + widthOff;
        int currentY = Dungeon.player.getPosy() + heightOff;
        objectGrid[currentX][currentY].pop();
        writeToTerminal(currentX, currentY);
        addObjectToDisplay(c, currentX, currentY);
    }

    public Displayable getRandomDisp() {
        Random r = new Random();
        char[] c = {'T', 'S', 'H', ']', '?', ')'};
        Displayable disp = new Displayable();
        disp.setCharacter(c[r.nextInt(6)]);
        return disp;
    }

    public static void removeItem(Item item) {
        for (Room r: dungeon.getRooms()) {
            for (Item i: r.getItems()) {
                if (i == item) {
                   r.removeItem(i);
                }
            }
        }
    }

    public void hallucinate() {
        if (Dungeon.player.halDuration > 0) {

            //change displays
            Queue<Displayable> oldCreatureDisplays = new ConcurrentLinkedQueue<>();
            Queue<Displayable> oldItemDisplays = new ConcurrentLinkedQueue<>();

            for (Room rm: dungeon.getRooms()) {
                int roomX = rm.getPosx();
                int roomY = rm.getPosy();
                //Creatures
                for (Displayable disp: rm.getCreatures()) {
                    oldCreatureDisplays.add(disp);
                    int x = disp.getPosx() + roomX;
                    int y = disp.getPosy() + roomY + Dungeon.getTopHeight();
                    Displayable randomDisp = getRandomDisp();
                    objectGrid[x][y].pop();
                    objectGrid[x][y].push(randomDisp);
                    writeToTerminal(x,y);
                }
                //Items
                for (Displayable dispItem: rm.getItems()) {
                    oldItemDisplays.add(dispItem);
                    int x = dispItem.getPosx() + roomX;
                    int y = dispItem.getPosy() + roomY + Dungeon.getTopHeight();
                    Displayable randomDisp = getRandomDisp();
                    objectGrid[x][y].pop();
                    objectGrid[x][y].push(randomDisp);
                    writeToTerminal(x,y);
                }
            }

            if (halRoundCounter == Dungeon.player.halDuration) {
                Dungeon.player.halDuration = 0;
                halRoundCounter = 0;

                //TODO return back to normal displays
                for (Room rm: dungeon.getRooms()) {
                    int roomX = rm.getPosx();
                    int roomY = rm.getPosy();
                    //Creatures
                    for (Displayable disp: rm.getCreatures()) {
                        int x = disp.getPosx() + roomX;
                        int y = disp.getPosy() + roomY + Dungeon.getTopHeight();
                        objectGrid[x][y].pop();
                        objectGrid[x][y].push(oldCreatureDisplays.remove());
                        writeToTerminal(x,y);
                    }
                    //Items
                    for (Displayable dispItem: rm.getItems()) {
                        int x = dispItem.getPosx() + roomX;
                        int y = dispItem.getPosy() + roomY + Dungeon.getTopHeight();
                        objectGrid[x][y].pop();
                        objectGrid[x][y].push(oldItemDisplays.remove());
                        writeToTerminal(x,y);
                    }
                }
            }
            halRoundCounter++;
        }
    }

    public void move(String direction) {
        updateDisplay.clearInfo();
        updateDisplay.clearPack();
        if(Dungeon.player.getEquippedItems().size() <= 0) {
            terminal.clear(' ', 12, 0, 20, 1);
        }
        int currentX = 0;
        int currentY = 0;
        int row = 0;
        int col = 0;
        if(moveCounter == Dungeon.player.getHpMove()){
            moveCounter = 0;
            int gainHp = Dungeon.player.getHp() + 1;
            Dungeon.player.setHp(gainHp);
            updateDisplay.updateHpPlayer(gainHp);
        }

        //Hallucinate if possible
        hallucinate();

        try {
            int widthOff = dungeon.getPlayerRoomX();
            int heightOff = dungeon.getPlayerRoomY() + Dungeon.getTopHeight();
            currentX = Dungeon.player.getPosx() + widthOff;
            currentY = Dungeon.player.getPosy() + heightOff;
            if (direction.equalsIgnoreCase("right")) {
               row =  currentX + 1;
               col = currentY;
            } else if (direction.equalsIgnoreCase("left")){
                row = currentX - 1;
                col = currentY;
            } else if (direction.equalsIgnoreCase("up")) {
                row = currentX;
                col = currentY - 1;
            } else if (direction.equalsIgnoreCase("down")) {
                row = currentX;
                col = currentY + 1;

            }
            boolean isBlank = objectGrid[row][col].isEmpty();
            char destination = objectGrid[row][col].peek().getCharacter();
            int destX = objectGrid[row][col].peek().getPosx();
            int destY = objectGrid[row][col].peek().getPosy();
            if (!isBlank) {
                if (destination == 'X') {
                    updateDisplay.writeToInfo("You can't move there!");
                }
                else if(destination == 'T' || destination == 'S' || destination == 'H') {
                    Creature cr = dungeon.getCreature(destX, destY);

                    // Teleport (if possible) before fight
                    if (teleport(cr, row, col)) return;

                    Dungeon.player.fight(cr);
                    if(Dungeon.player.getHp() == 0) {
                        //TODO creature death action
                        //changedisplay first
                        Action cd = Dungeon.player.getAction("ChangeDisplayedType");
                        Action rm = Dungeon.player.getAction("Remove");
                        if (rm != null) {
                            objectGrid[currentX][currentY].pop();
                            writeToTerminal(currentX, currentY);
                        } else {
                            if (cd != null) {
                                Char val = new Char(cd.getCharValue());
                                // change player's display
                                setPlayerDisplay(val);
                            }
                        }
                        // EndGame second
                        Action eg = Dungeon.player.getAction("EndGame");
                        if (eg != null) {
                            ObjectDisplayGrid.updateDisplay.writeToInfo(eg.getMsg());
                        }

                        //Print YouWin message if exists
                        Action yw = Dungeon.player.getAction("YouWing");
                        if (yw != null) {
                            ObjectDisplayGrid.updateDisplay.writeToInfo(yw.getMsg());
                        }
                        KeyStrokePrinter.playerAlive = false;
                    }
                    if(cr.getHp() == 0) { //Creature dead
                        objectGrid[row][col].pop();
                        writeToTerminal(row, col);
                        //creature death action
                        Action yw = cr.getAction("YouWin");
                        String msg = yw.getMsg();
                        int i = yw.getIntVal();
                        ObjectDisplayGrid.updateDisplay.writeToInfo(msg);
                    }
                    updateDisplay.updateHpPlayer(Dungeon.player.getHp());
                    updateDisplay.displayEquippedItems();
                }
                else {
                    objectGrid[currentX][currentY].pop();
                    writeToTerminal(currentX, currentY);
                    addObjectToDisplay(new Char('@'), row, col);
                    Dungeon.player.setPosX(row - widthOff);
                    Dungeon.player.setPosY(col - heightOff);
                    terminal.repaint();
                    moveCounter++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateDisplay.writeToInfo("You can't move there!");
        }
    }

    public int getRandX(Room r) {
        Random random = new Random();
        int boundX = r.getPosx() + 1;
        int width = r.getWidth();
        return random.nextInt(width-2) + boundX;
    }

    public int getRandY(Room r) {
        Random random = new Random();
        int boundy = r.getPosy() + 1;
        int height = r.getHeight();
        int topOffset = Dungeon.getTopHeight();
        int res = random.nextInt(height-2) + boundy + topOffset;
        return res;
    }

    public boolean teleport(Creature enemy, int x, int y) {
        Action enemyTeleport = enemy.getAction("Teleport");
        Action playerTeleport = Dungeon.player.getAction("Teleport");

        if (enemyTeleport != null) {
            System.out.println("Creature teleport");
            //TODO teleport enemy creature and display teleport msg
            Displayable poppedCreature = objectGrid[x][y].pop();
            writeToTerminal(x,y);
            Random r = new Random();
            int roomLen = dungeon.getRooms().size();
            Room randRoom = dungeon.getRooms().get(r.nextInt(roomLen));
            int randX = getRandX(randRoom);
            int randY = getRandY(randRoom);
            try {
                while (objectGrid[randX][randY].peek().getCharacter() != '.') {
                    randX = getRandX(randRoom);
                    randY = getRandY(randRoom);
                }
                addObjectToDisplay(poppedCreature, randX, randY);
            } catch (EmptyStackException e) {
            }

            //display teleport message
            ObjectDisplayGrid.updateDisplay.writeToInfo(enemyTeleport.getMsg());
            return true;
        }
        if (playerTeleport != null) {
            //TODO teleport player and display teleport msg
            int widthOff = dungeon.getPlayerRoomX();
            int heightOff = dungeon.getPlayerRoomY() + Dungeon.getTopHeight();
            int currentX = Dungeon.player.getPosx() + widthOff;
            int currentY = Dungeon.player.getPosy() + heightOff;
            return true;
        }
        return false;
    }

    public void pickItem(){
        int currentX = 0;
        int currentY = 0;
        int widthOff = dungeon.getPlayerRoomX();
        int heightOff = dungeon.getPlayerRoomY() + Dungeon.getTopHeight();
        currentX = Dungeon.player.getPosx() + widthOff;
        currentY = Dungeon.player.getPosy() + heightOff;
        int stackSize = objectGrid[currentX][currentY].size();
        Displayable it = objectGrid[currentX][currentY].get(stackSize - 2);

        if(stackSize > 2) {
            Item item = (Item) it;
            Dungeon.player.addItem((Item) item);
            if (item instanceof Scroll) {
                if (item.getAction("Hallucinate") != null)
                    updateDisplay.writeToInfo(item.getAction("Hallucinate").getMsg());
                if (item.getAction("BlessArmor") != null)
                    updateDisplay.writeToInfo(item.getAction("BlessArmor").getMsg());
            }
            objectGrid[currentX][currentY].remove(item);
            writeToTerminal(currentX, currentY);
            //TODO unnecessary code below, bbelli hayirdir?
//            for (int i = 0; i < printedItems.size(); i++) {
//                int x = printedItems.get(i).getPosx() ;
//                int y = printedItems.get(i).getPosy() ;
//                if((item.getPosx() == x) & (item.getPosy() == y)){
//                    Item it = printedItems.get(i);
//                    Dungeon.player.addItem(it);
//                    if (it instanceof Scroll) {
//                        if (it.getAction("Hallucinate") != null)
//                            updateDisplay.writeToInfo(it.getAction("Hallucinate").getMsg());
//                        if (it.getAction("BlessArmor") != null)
//                            updateDisplay.writeToInfo(it.getAction("BlessArmor").getMsg());
//                    }
//                    objectGrid[currentX][currentY].pop();
//                    //updateDisplay.printInventory();
//                }
//            }
        }
        else{
            updateDisplay.writeToInfo("No item to pick up!");
        }
    }

    public void dropItem(int i){
        int currentX = 0;
        int currentY = 0;
        int widthOff = dungeon.getPlayerRoomX();
        int heightOff = dungeon.getPlayerRoomY() + Dungeon.getTopHeight();
        currentX = Dungeon.player.getPosx() + widthOff;
        currentY = Dungeon.player.getPosy() + heightOff;

        try {
            if ((i > Dungeon.player.getItems().size()) || i == 0) {
                updateDisplay.writeToInfo("No item in chosen slot!");
                return;
            } else {
                objectGrid[currentX][currentY].pop();
                Displayable itemToDrop = Dungeon.player.getItems().get(i-1);
                addObjectToDisplay(itemToDrop, currentX, currentY);
                terminal.repaint();
                Dungeon.player.removeItem(i-1);
                terminal.clear(' ', 7, height - 3, Dungeon.getWidth() - 8, 1);
            }
            addObjectToDisplay(Dungeon.player, currentX, currentY);
        } catch (Exception e) {
            System.out.println("Out of bounds");
        }
    }

    public void addObjectToDisplay(Displayable ch, int x, int y) {
        if ((0 <= x) && (x < objectGrid.length)) {
            if ((0 <= y) && (y < objectGrid[0].length)) {
                (objectGrid[x][y]).push(ch);
                writeToTerminal(x, y);
            }
        }
    }

    private void writeToTerminal(int x, int y) {
        char ch = objectGrid[x][y].peek().getCharacter();
        if(ch == '@'){
            terminal.write(ch, x, y, AsciiPanel.brightYellow);
        }
        else if((ch == 'T') || (ch == 'S') || (ch == 'H')){
            terminal.write(ch, x, y, AsciiPanel.red);
        }
        else if((ch == '?') || (ch == ')') || (ch == ']')){
            terminal.write(ch, x, y, AsciiPanel.brightCyan.darker());
        }
        else if((ch == 'X')){
            terminal.write(ch, x, y, AsciiPanel.brightWhite.brighter());
        }
        else{
            terminal.write(ch, x, y);
        }
        terminal.repaint();
    }
    //Stack functions for debugging
    public int getStackSize(){
        int currentX = 0;
        int currentY = 0;
        int widthOff = dungeon.getPlayerRoomX();
        int heightOff = dungeon.getPlayerRoomY() + Dungeon.getTopHeight();
        currentX = Dungeon.player.getPosx() + widthOff;
        currentY = Dungeon.player.getPosy() + heightOff;
        return objectGrid[currentX][currentY].size();
    }

    public String printStack(){
        int currentX = 0;
        int currentY = 0;
        int widthOff = dungeon.getPlayerRoomX();
        int heightOff = dungeon.getPlayerRoomY() + Dungeon.getTopHeight();
        String toString = "";
        currentX = Dungeon.player.getPosx() + widthOff;
        currentY = Dungeon.player.getPosy() + heightOff;

        for (int i = 0; i < getStackSize(); i++) {
            toString += objectGrid[currentX][currentY].get(i).getCharacter() + ",";
        }
        return toString;
    }
}
