package src;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import src.Action.Action;
import src.Action.CreatureAction;
import src.Action.ItemAction;
import src.Displayable.*;


import java.util.ArrayList;

public class DungeonXMLHandler extends DefaultHandler{
    private static final int DEBUG = 1;
    private static final String CLASSID = "DungeonXMLHandler";

    private StringBuilder data = null;

    private Dungeon dungeon = null;

    private ArrayList<Room> rooms = null; //TODO: probably don't need these two fields
    private ArrayList<Passage> passages = null;

    private Structure structureParsed = null;
    private Creature creatureParsed = null;
    private Item itemParsed = null;
    private Action actionParsed = null;

    private boolean bvisible = false;
    private boolean bposx = false;
    private boolean bposY = false;
    private boolean bwidth = false;
    private boolean bheight = false;
    private boolean bactionMsg = false;
    private boolean bactionIntVal = false;
    private boolean bactionCharVal = false;
    private boolean bitemIntVal = false;
    private boolean btype = false;
    private boolean bhp = false;
    private boolean bmaxhit = false;
    private boolean bhpMoves = false;

    public DungeonXMLHandler() {}

    public Dungeon getDungeon() {
        //Dungeon.dungeon = dungeon;
        return dungeon;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".startElement qName: " + qName);
        }

        if (qName.equalsIgnoreCase("Dungeon")) {
            dungeon = new Dungeon();

            String name = attributes.getValue("name");
            int width = Integer.parseInt(attributes.getValue("width"));
            int gameHeight = Integer.parseInt(attributes.getValue("gameHeight"));
            int bottomHeight = Integer.parseInt(attributes.getValue("bottomHeight"));
            int topHeight = Integer.parseInt(attributes.getValue("topHeight"));
            Dungeon.setWidth(width);
            Dungeon.setBottomHeight(bottomHeight);
            Dungeon.setGameHeight(gameHeight);
            Dungeon.setTopHeight(topHeight);
        } else if (qName.equalsIgnoreCase("Rooms")) {
            rooms = new ArrayList<Room>();
        } else if (qName.equalsIgnoreCase("Passages")) {
            passages = new ArrayList<Passage>();
        } else if (qName.equalsIgnoreCase("Room")) {
            int id = Integer.parseInt(attributes.getValue("room"));
            Room room = new Room(id);
            rooms.add(room);
            //dungeon.addRoom(room);
            structureParsed = room;
        } else if (qName.equalsIgnoreCase("Passage")) {
            int room1 = Integer.parseInt(attributes.getValue("room1"));
            int room2 = Integer.parseInt(attributes.getValue("room2"));
            Passage passage = new Passage();
            passage.setID(room1, room2);
            passages.add(passage);
            //dungeon.addPassage(passage);
            structureParsed = passage;
        } else if (qName.equalsIgnoreCase("Monster")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Monster monster = new Monster();
            monster.setID(room, serial);
            monster.setName(name);
            dungeon.addCreature(monster);
            creatureParsed = monster;
            if (structureParsed instanceof Room) {
                ((Room) structureParsed).setCreature(monster);
            }
        } else if (qName.equalsIgnoreCase("Player")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Player player = new Player();
            player.setName(name);
            player.setID(room, serial);
            dungeon.addCreature(player);
            dungeon.addPlayer(player);
            creatureParsed = player;
            if (structureParsed instanceof Room) {
                ((Room) structureParsed).setCreature(player);
            }
        } else if (qName.equalsIgnoreCase("Armor")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Armor armor = new Armor(name);
            armor.setId(room, serial);
            armor.setName(name);
            armor.setCharacter(']');
            dungeon.addItem(armor);
            itemParsed = armor;
//            if (creatureParsed instanceof Player) {
//                ((Player) creatureParsed).setArmor(armor);
//            }
        } else if (qName.equalsIgnoreCase("Sword")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Sword sword = new Sword(name);
            sword.setID(room, serial);
            sword.setCharacter(')');
            dungeon.addItem(sword);
            itemParsed = sword;
//            if (creatureParsed instanceof Player) {
//                ((Player) creatureParsed).setWeapon(sword);
//            }
        } else if (qName.equalsIgnoreCase("Scroll")) {
            String name = attributes.getValue("name");
            int room = Integer.parseInt(attributes.getValue("room"));
            int serial = Integer.parseInt(attributes.getValue("serial"));
            Scroll scroll = new Scroll(name);
            scroll.setID(room, serial);
            scroll.setCharacter('?');
            dungeon.addItem(scroll);
            itemParsed = scroll;
        } else if (qName.equalsIgnoreCase("CreatureAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            actionParsed = new CreatureAction();
            System.out.printf("%s and %s parsed for %s\n", name, type, creatureParsed.toString());
            actionParsed.setName(name);
            actionParsed.setType(type);
            actionParsed.setOwner(creatureParsed);
        } else if (qName.equalsIgnoreCase("ItemAction")) {
            String name = attributes.getValue("name");
            String type = attributes.getValue("type");
            actionParsed = new ItemAction();
            actionParsed.setName(name);
            actionParsed.setType(type);
            actionParsed.setOwner(itemParsed);
        } else if (qName.equalsIgnoreCase("visible")) {
            bvisible = true;
        } else if (qName.equalsIgnoreCase("posX")){
            bposx = true;
        } else if (qName.equalsIgnoreCase("posY")){
            bposY = true;
        } else if (qName.equalsIgnoreCase("type")){
            btype = true;
        } else if (qName.equalsIgnoreCase("width")){
            bwidth = true;
        } else if (qName.equalsIgnoreCase("height")){
            bheight = true;
        } else if (qName.equalsIgnoreCase("hp")){
            bhp = true;
        } else if (qName.equalsIgnoreCase("maxhit")){
            bmaxhit = true;
        } else if (qName.equalsIgnoreCase("hpMoves")){
            bhpMoves = true;
        } else if (qName.equalsIgnoreCase("actionMessage")){
            bactionMsg = true;
        } else if (qName.equalsIgnoreCase("actionIntValue")){
            bactionIntVal = true;
        } else if (qName.equalsIgnoreCase("actionCharValue")){
            bactionCharVal = true;
        } else if (qName.equalsIgnoreCase("ItemIntValue")){
            bitemIntVal = true;
        }

        else {
            System.out.println("Unknown qname: " + qName);
        }

        data = new StringBuilder();
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        Structure structure;
        Creature creature;
        Item item;
        Action action;

        if (bvisible) {
            String visibility = data.toString();
            if (itemParsed != null) {
                if (visibility.equalsIgnoreCase("1")) itemParsed.setVisible();
                else if (visibility.equalsIgnoreCase("0")) itemParsed.setInvinsible();
            } else if (creatureParsed != null) {
                if (visibility.equalsIgnoreCase("1")) creatureParsed.setVisible();
                else if (visibility.equalsIgnoreCase("0")) creatureParsed.setInvinsible();
            } else if (structureParsed != null) {
                if (visibility.equalsIgnoreCase("1")) structureParsed.setVisible();
                else if (visibility.equalsIgnoreCase("0")) structureParsed.setInvinsible();
            }
            bvisible = false;
        } else if (bposx) {
            if (itemParsed != null) itemParsed.setPosX(Integer.parseInt(data.toString()));
            else if (creatureParsed != null) creatureParsed.setPosX(Integer.parseInt(data.toString()));
            else if (structureParsed != null) structureParsed.setPosX(Integer.parseInt(data.toString()));
            bposx = false;
        } else if (bposY) {
            if (itemParsed != null) itemParsed.setPosY(Integer.parseInt(data.toString()));
            else if (creatureParsed != null) creatureParsed.setPosY(Integer.parseInt(data.toString()));
            else if (structureParsed != null) structureParsed.setPosY(Integer.parseInt(data.toString()));
            bposY = false;
        } else if (btype) {
            if (itemParsed != null) itemParsed.setType(data.charAt(0));
            else if (creatureParsed != null) creatureParsed.setType(data.charAt(0));
            else if (structureParsed != null) structureParsed.setType(data.charAt(0));
            btype = false;
        } else if (bwidth) {
            if (itemParsed != null) itemParsed.setWidth(Integer.parseInt(data.toString()));
            else if (creatureParsed != null) creatureParsed.setWidth(Integer.parseInt(data.toString()));
            else if (structureParsed != null) structureParsed.setWidth(Integer.parseInt(data.toString()));
            bwidth = false;
        } else if (bheight) {
            if (itemParsed != null) itemParsed.setHeight(Integer.parseInt(data.toString()));
            else if (creatureParsed != null) creatureParsed.setHeight(Integer.parseInt(data.toString()));
            else if (structureParsed != null) structureParsed.setHeight(Integer.parseInt(data.toString()));
            bheight = false;
        } else if (bhp) {
            if (creatureParsed != null) creatureParsed.setHp(Integer.parseInt(data.toString()));
            else if (itemParsed != null) itemParsed.setHp(Integer.parseInt(data.toString()));
            bhp = false;
        } else if (bmaxhit) {
            if (creatureParsed != null) creatureParsed.setMaxHit(Integer.parseInt(data.toString()));
            else if (itemParsed != null) itemParsed.setMaxHit(Integer.parseInt(data.toString()));
            bmaxhit = false;
        } else if (bhpMoves) {
            if (creatureParsed != null) creatureParsed.setHpMove(Integer.parseInt(data.toString()));
            else if (itemParsed != null) itemParsed.setHpMove(Integer.parseInt(data.toString()));
            bhpMoves = false;
        } else if (bactionMsg) { //TODO: maybe add action related stuff to proper creature or item
            String msg = data.toString();
            actionParsed.setMsg(msg);
            bactionMsg = false;
        } else if (bactionIntVal) { //TODO: maybe add action related stuff to proper creature or item
            int val = Integer.parseInt(data.toString());
            actionParsed.setIntVal(val);
            bactionIntVal = false;
        } else if (bactionCharVal) { //TODO: maybe add action related stuff to proper creature or item
            char val = data.charAt(0);
            actionParsed.setCharValue(val);
            bactionCharVal = false;
        } else if (bitemIntVal) { //TODO: maybe add action related stuff to proper creature or item
            int val = Integer.parseInt(data.toString());
            itemParsed.setIntValue(val);
            bitemIntVal = false;
        }


        if (qName.equalsIgnoreCase("Rooms")) {
            System.out.printf("Total number of rooms parsed: %d\n", rooms.size());
        } else if (qName.equalsIgnoreCase("Passages")) {
            System.out.printf("Total number of passages parsed: %d\n", passages.size());
        } else if (qName.equalsIgnoreCase("Room")) {
            dungeon.addRoom((Room) structureParsed);
            structureParsed = null;
        } else if (qName.equalsIgnoreCase("Passage")) {
            dungeon.addPassage((Passage) structureParsed);
            structureParsed = null;
        } else if (qName.equalsIgnoreCase("Monster")) {
            creatureParsed = null;
        } else if (qName.equalsIgnoreCase("Player")) {
            dungeon.setPlayerRoom(structureParsed.getPosx(), structureParsed.getPosy());
            creatureParsed = null;
        } else if (qName.equalsIgnoreCase("Armor") || qName.equalsIgnoreCase("Scroll") ||
                        qName.equalsIgnoreCase("Sword")) {
            if (creatureParsed != null) creatureParsed.addItem(itemParsed);
            else if (structureParsed != null) structureParsed.addItem(itemParsed);
            itemParsed = null;
        } else if (qName.equalsIgnoreCase("CreatureAction") || qName.equalsIgnoreCase("ItemAction")) {
            actionParsed = null;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        data.append(new String(ch, start, length));
        if (DEBUG > 1) {
            System.out.println(CLASSID + ".characters: " + new String(ch, start, length));
            System.out.flush();
        }
    }

}
