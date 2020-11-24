package src.Displayable;


public class Displayable {
    private boolean visibility;
    private int maxhit;
    private int hpMove;
    private int hp;
    private char type;
    private int intValue;
    private int posx;
    private int posy;
    private int width;
    private int height;
    private char character;

    public Displayable() {
        //System.out.println("--- src.Displayable ---");
    }

    public boolean isVisible() { return visibility; }

    public void setInvinsible() {
        visibility = false;
    }

    public void setVisible() { visibility = true; }

    public void setMaxHit(int maxHit) {
        maxhit = maxHit;
    }

    public void setHpMove(int hpMoves) {
        hpMove = hpMoves;
    }

    public void setHp(int _hp) {
        hp = _hp;
    }

    public void setType(char t) {
        type = t;
    }

    public void setIntValue(int v) {
        intValue = v;
    }

    public void setPosX(int x) {
        posx = x;
    }

    public void setPosY(int y) { posy = y; }

    public void setWidth(int x) {
        width = x;
    }

    public void setHeight(int y) {
        height = y;
    }

    public int getMaxhit() {
        return maxhit;
    }

    public int getHpMove() {
        return hpMove;
    }

    public int getHp() {
        return hp;
    }

    public char getType() {
        return type;
    }

    public int getIntValue() {
        return intValue;
    }


    public int getPosx() {
        return posx;
    }

    public int getPosy() {
        return posy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public char getCharacter() { return character; }

    public void setCharacter(char ch) { character = ch; }

}
