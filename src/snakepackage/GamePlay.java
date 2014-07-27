package snakepackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import snakepackage.GameEvent.gameEventType;

@SuppressWarnings("serial")
public class GamePlay extends JPanel implements Constants, Serializable {

    public static Timer animationTimer;
    public static Board snakeBoard;
    public boolean gameOver = false;
    public boolean loop = true;
    private SimpleScreenManager screen;

    private int score;
    private int level;
    private int init_level;
    private String levelStr;
    private static String scoreStr;
    private SnakePiece snake;
    private int ANIMATION_DELAY;// 1200;
    private int currDir;
    private Random xRan = new Random();
    private Random yRan = new Random();
    private GameHandler gl = new GameHandler();


    public GamePlay() {
        restartGame();
    }// end

    public void restartGame() {

        snakeBoard = new Board(XLENGTH, YLENGTH);
        snake = new SnakePiece();

        snake.addGameListener(gl);
        currDir = UP;// snake start moving this way
        setNewFood();
        score = 0;
        init_level = 0;
        level = 0;
        ANIMATION_DELAY = 450;
        screen = new SimpleScreenManager();
        runAnimation();

    }

    private void animationLoop(Graphics2D g) {
        g.setColor(Color.white);
        g.drawRect(0, 0, GAMEWIDTH, GAMEHEIGHT);
        g.translate(0, GAMEHEIGHT); // Move the origin to the lower left
        g.scale(1.0, -1.0); // Flip the sign of the coordin

        if (!gameOver) {
            snakeBoard.draw(g);
            if (level != init_level) {
                ANIMATION_DELAY -= 100;// increase game time
                animationTimer.setDelay(ANIMATION_DELAY);
                init_level = level;
            }
            scoreStr = Integer.toString((score * 100));
            levelStr = Integer.toString(level);
            GameMain.scoreLabel.setText("SCORE " + scoreStr + "    LEVEL " + levelStr);
        } else {

            GameMain.scoreLabel.setText("GAME OVER!! " + "YOUR SCORE = " + scoreStr);
        }

    }

    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);
        RenderingHints renderHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        renderHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        this.setBackground(Color.yellow);
        Graphics2D g = (Graphics2D) gr;
        animationLoop(g);

    }

    public Dimension getPreferredSize() {
        return new Dimension(GAMEWIDTH + 10, GAMEHEIGHT + 10);
    }

    public void runAnimation() {
        if (animationTimer == null) {
            // create timer

            animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler());
            animationTimer.start(); // start Timer
        } // end if
        else // animationTimer already exists, restart animation
        {
            if (!animationTimer.isRunning()) {
                animationTimer.restart();
            }
        } // end else
    } // end method startAnimation

    private class TimerHandler implements ActionListener {
        // respond to Timer's event
        public void actionPerformed(ActionEvent actionEvent) {
            snake.move(currDir);
            repaint(); //
        }
    }

    public void move(int direction) {
        snake.move(direction);
        currDir = direction;
        repaint();
    }


    /** put food in rnd place not occupied in snake body */
    private void setNewFood() {
        int x = xRan.nextInt(XLENGTH);
        int y = yRan.nextInt(YLENGTH);

        if (Board.isPositionFilled(x, y)) {
            setNewFood();
        } else
            Board.setPosition(x, y, FOOD_BLOCK);
    }

    private class GameHandler implements GameListener {
        @Override
        public void gameRestart(GameEvent e) {

        }

        public void snakeChange(GameEvent e) {
            if (e.getType() == gameEventType.HITWALL) {
                System.out.println(" event hitwall");
                gameOver = true;
                animationTimer.stop();
            } else if (e.getType() == gameEventType.EATFOOD) {
                System.out.println(" event eat food");
                score++;
                level = score / 10;
                setNewFood();
            }
            // else if (e.getType()==gameEventType.HITSELF) {
            // System.out.println(" evnt hitself");
            // gameOver=true;
            // animationTimer.stop();
            // }


        }
    }

}
