package snakepackage;


import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.Serializable;


public class Board implements Constants, Serializable {
    private static int nX;
    private static int nY;
    private static int[][] boardGrid;
    /**
     * GameBoard is like a cartesian graph, the no X is the column needed in an array the no Y
     * needed is the no of row of an array
     * 
     * @param rows
     * @param cols
     */
    private Color brickColor = new Color(196, 26, 26);

    public Board(int x, int y) {
        nX = x;
        nY = y;
        init_board();

    }

    public static void init_board() {

        boardGrid = new int[nX][nY];
        int i, j;
        for (i = 0; i < nX; i++) {
            for (j = 0; j < nY; j++) {
                boardGrid[i][j] = EMPTY_BLOCK;
            }
        }

    }


    public static void setPoints(Point p, int blockType) {
        setPosition(p.x, p.y, blockType);
    }

    public static void setPosition(int rowIdx, int colIdx, int blockType) {
        System.out.println("setpts i " + rowIdx + " j " + colIdx);
        boardGrid[rowIdx][colIdx] = blockType;
    }

    public static void removePoints(int rowIdx, int colIdx) {
        System.out.println("remove i " + rowIdx + " j " + colIdx);
        boardGrid[rowIdx][colIdx] = EMPTY_BLOCK;
    }

    public static int getnRows() {
        return nX;
    }

    public static int getnCols() {
        return nY;
    }


    public void draw(Graphics2D g) {
        int i, j;

        // g.setColor(brickColor);
        for (i = 0; i < nX; i++) {
            for (j = 0; j < nY; j++) {
                if (boardGrid[i][j] != EMPTY_BLOCK) {
                    if (boardGrid[i][j] == FOOD_BLOCK) {
                        g.setColor(Color.cyan);
                    } else
                        g.setColor(brickColor);

                    g.fillOval(i * BRICKWIDTH, j * BRICKHEIGHT, BRICKWIDTH - 2, BRICKHEIGHT - 2);
                    // g.setColor(Color.BLACK);
                    g.drawOval(i * BRICKWIDTH, j * BRICKHEIGHT, BRICKWIDTH, BRICKHEIGHT);
                }

            }
        }

    }

    public static boolean isPositionFilled(int x, int y) {
        if (x >= nX || x < 0 || y < 0 || y >= nY)
            return true;
        if (boardGrid[x][y] == FILLED_BLOCK)
            return true;
        return false;
    }

    public int getPieceAt(int i, int j) {
        return boardGrid[i][j];
    }


    public static int foodIsthere(int x, int y) {
        // prevent from going out of bound since food is added 2 distance away
        if ((x >= nX - 1 || x == 0 || y == 0 || y >= nY - 1) && (boardGrid[x][y] == FOOD_BLOCK)) {
            System.out.println("food at border");
            return 0;// food at border
        }

        if (boardGrid[x][y] == FOOD_BLOCK) {
            System.out.println("food not border");
            return 1;// food present not @ border
        }
        return -1;
    }



}
