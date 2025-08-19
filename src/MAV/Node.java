package MAV;

//Node Class to Represent each square of the grid. Each node is given a type and X and Y position
class Node {
    private int cellType;
    private int hops, x, y, lastX, lastY;

    Node(int type, int x, int y) {
        this.cellType = type;
        this.x = x;
        this.y = y;
        this.hops = -1;
    }

    int getX() { return x; }
    int getY() { return y; }
    int getLastX() { return lastX; }
    int getLastY() { return lastY; }
    int getType() { return cellType; }
    int getHops() { return hops; }

    void setType(int type) { cellType = type; }
    void setLastNode(int x, int y) { lastX = x; lastY = y; }
    void setHops(int hops) { this.hops = hops; }
}