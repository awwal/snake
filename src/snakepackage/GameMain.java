package snakepackage;


import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class GameMain implements Constants, KeyListener, ActionListener {

    private static GamePlay playSnake;
    private JFrame snakeFrame = new JFrame("SNAAAKE--sss");
    private JButton startButton;
    public static JLabel scoreLabel;
    private static boolean gamePaused = false;

    JMenuBar menuBar;
    JMenu menu;
    JMenuItem menuItem;
    private JMenuItem menuItem2;
    private JMenuItem menuItem3;
    private JMenu menu2;
    private JMenuItem menu2Item;
    private JMenuItem gmenuItem2;
    private JMenuItem gmenuItem1;
    private boolean openSelected;
    private static boolean saveSelected;

    public GameMain() {


        playSnake = new GamePlay();
        startButton = new JButton("START");
        scoreLabel = new JLabel("score ");

        menuBar = new JMenuBar();
        menu = new JMenu("Game");
        menu2 = new JMenu("Help");
        menu.setMnemonic('G');
        gmenuItem1 = new JMenuItem("Open saved game");
        gmenuItem1.setActionCommand("OPEN");
        gmenuItem2 = new JMenuItem("Save game");
        gmenuItem2.setActionCommand("SAVE");
        menuItem = new JMenuItem("Restart game");
        menuItem.setActionCommand("RESTART");
        menuItem2 = new JMenuItem("Pause Game");
        menuItem2.setActionCommand("PAUSE");
        menuItem3 = new JMenuItem("Exit");
        menuItem3.setActionCommand("EXIT");
        menuItem3.setMnemonic('X');

        menu2Item = new JMenuItem("About");
        menu2Item.setActionCommand("ABOUT");

        menu.add(gmenuItem1);
        menu.add(gmenuItem2);
        menu.addSeparator();

        menu.add(menuItem);
        menu.add(menuItem2);
        menu.addSeparator();
        menu.add(menuItem3);

        menu2.add(menu2Item);

        menuBar.add(menu);
        menuBar.add(menu2);

        menu2Item.addActionListener(this);

        menu.addActionListener(this);
        menuItem.addActionListener(this);
        menuItem2.addActionListener(this);
        menuItem3.addActionListener(this);
        gmenuItem1.addActionListener(this);
        gmenuItem2.addActionListener(this);


        Container pane = snakeFrame.getContentPane();
        pane.setLayout(new BorderLayout());
        pane.add(playSnake, BorderLayout.CENTER);
        // pane.add(startButton, BorderLayout.SOUTH);

        pane.add(scoreLabel, BorderLayout.NORTH);

        snakeFrame.setJMenuBar(menuBar);

        snakeFrame.addKeyListener(this);
        snakeFrame.pack();
        snakeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        snakeFrame.setVisible(true);

    }

    private static void saveGame() {
        // if(saveSelected==true) {
        gamePaused = true;
        GamePlay.animationTimer.stop();
        try {
            FileOutputStream fos = new FileOutputStream("temp.snake");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(playSnake);

            oos.flush();
            oos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // saveSelected=false;
        // }
    }

    public static void main(String[] args) {

        new GameMain();

        if (saveSelected == true) {
            System.out.println("save fool");
            GamePlay.animationTimer.stop();
            saveGame();
            saveSelected = false;
        }


    }

    public void keyPressed(KeyEvent e) {
        if (!gamePaused) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    playSnake.move(LEFT);
                    break;
                case KeyEvent.VK_RIGHT:
                    playSnake.move(RIGHT);
                    break;
                case KeyEvent.VK_UP:
                    playSnake.move(UP);
                    break;
                case KeyEvent.VK_DOWN:
                    playSnake.move(DOWN);
                    break;
                case KeyEvent.VK_SHIFT:
                    playSnake.move(FALL);
            }
        }
    }

    public void keyReleased(KeyEvent e) {}

    public void keyTyped(KeyEvent e) {}



    @Override
    public void actionPerformed(ActionEvent e) {
        // System.out.println(e.getID());
        if (e.getActionCommand() == "OPEN") {

            openSelected = true;
        } else if (e.getActionCommand() == "SAVE") {
            saveSelected = true;
            System.out.println("save");
        }

        else if (e.getActionCommand() == "RESTART") {
            playSnake.restartGame();
            playSnake.gameOver = false;

        } else if (e.getActionCommand() == "PAUSE") {
            if (gamePaused) {
                GamePlay.animationTimer.start();
                gamePaused = false;
            } else {
                GamePlay.animationTimer.stop();
                gamePaused = true;
            }

        } else if (e.getActionCommand() == "EXIT") {
            System.exit(0);

        } else if (e.getActionCommand() == "ABOUT") {

            JOptionPane.showMessageDialog(snakeFrame, "This is a free Game created by Olufowobi Lawal", "ABOUT SNAKE v0.1", JOptionPane.INFORMATION_MESSAGE);

        }

    }

}
