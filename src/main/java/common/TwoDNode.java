package common;

/* WIP */
public class TwoDNode {
    private final int row;
    private final int column;

    private TwoDNode nodeAbove;
    private TwoDNode nodeBelow;
    private TwoDNode nodeToLeft;
    private TwoDNode nodeToRight;

    public TwoDNode(int row, int column) {
        this.row = row;
        this.column = column;
    }
}
