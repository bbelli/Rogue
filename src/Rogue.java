package src;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;


public class Rogue  implements Runnable{
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    private static Dungeon dungeon;

    private static ObjectDisplayGrid displayGrid = null;
    private Thread keyStrokePrinter;

    public Rogue(Dungeon dungeon) {
        displayGrid = new ObjectDisplayGrid(dungeon);
    }

    @Override
    public void run() {
        displayGrid.initializeDisplay();
    }

    public static void main(String[] args) throws Exception {
        String fileName = null;
        switch(args.length) {
            case 1:
                    fileName = "xmlfiles/" + args[0];
                    break;
            default:
                System.out.println("java Test <xmlfilename>");
                return;
        }

        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            DungeonXMLHandler handler = new DungeonXMLHandler();
            saxParser.parse(new File(fileName), handler);

            dungeon = handler.getDungeon();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace(System.out);
        }

        Rogue test = new Rogue(dungeon);
        Thread testThread = new Thread(test);
        testThread.start();

        test.keyStrokePrinter = new Thread(new KeyStrokePrinter(displayGrid));
        test.keyStrokePrinter.start();

        testThread.join();
        test.keyStrokePrinter.join();
    }
}
