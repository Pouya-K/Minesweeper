import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;

public class Level {
    private JFrame frame = new JFrame("Minesweeper");
    public static Drawing drawing;
    private GameState gameState;

    public Level(char level) {
        gameState = new GameState();
        gameState.setTimeTicker(System.currentTimeMillis());
        if (level == 'b') {
            gameState.setNumOfMines(10);
            gameState.setGridWidth(9);
            gameState.setGridHeight(9);
            gameState.setScreenHeight(480);
            gameState.setScreenWidth(310);
        } else if (level == 'i') {
            gameState.setNumOfMines(40);
            gameState.setGridWidth(16);
            gameState.setGridHeight(16);
            gameState.setScreenHeight(520);
            gameState.setScreenWidth(690);
        } else {
            gameState.setNumOfMines(99);
            gameState.setGridWidth(30);
            gameState.setGridHeight(16);
            gameState.setScreenHeight(940);
            gameState.setScreenWidth(690);
        }
        gameState.setFlagsLeft(gameState.getNumOfMines());
        gameState.setGrid(new Square[gameState.getGridHeight()][gameState.getGridWidth()]);

        generateSquares();
        frame.setSize(gameState.getScreenWidth(), gameState.getScreenHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(new Mousehandler());
        frame.setVisible(true);
        int gap = 1000 / 60;
        Timer timer = new Timer();
        timer.schedule(new Runner(gameState), 0, gap);
        frame.add(drawing);
    }

    public void generateSquares() {
        int x = 20, y = 110;
        for (int i = 0; i < gameState.getGridHeight(); i++) {
            for (int j = 0; j < gameState.getGridWidth(); j++) {
                gameState.getGrid()[i][j] = new Square(x, y, gameState);
                x += 30;
            }
            x = 20;
            y += 30;
        }
        int counter = 0;
        while (counter < gameState.getNumOfMines()) {
            int x1 = (int) (Math.random() * gameState.getGridHeight());
            int y1 = (int) (Math.random() * gameState.getGridWidth());
            if (!gameState.getGrid()[x1][y1].isMine()) {
                gameState.getGrid()[x1][y1].turnMine();
                counter++;
            }
        }
    }


    class Mousehandler extends MouseAdapter {
        public void mousePressed(MouseEvent e) {
            if (e.getX() >= drawing.getWidth() / 2 - 50 && e.getX() <= drawing.getWidth() / 2 - 5 && e.getY() >= drawing.getHeight() - 20 && e.getY() <= drawing.getHeight() + 25) {
                frame.dispose();
                new Game();
            } else if (e.getX() >= drawing.getWidth() / 2 && e.getX() <= drawing.getWidth() / 2 + 45 && e.getY() >= drawing.getHeight() - 20 && e.getY() <= drawing.getHeight() + 25) {
                gameState.setFlagsLeft(gameState.getNumOfMines());
                gameState.setTime(0);
                gameState.setTimeTicker(System.currentTimeMillis());
                gameState.setGameWon(false);
                gameState.setGameOver(false);
                gameState.setLostSquare(null);
                gameState.getCheckedSquares().clear();
                generateSquares();
            }
            if (e.getButton() == MouseEvent.BUTTON1) gameState.setLeftClick(true);
            else if (e.getButton() == MouseEvent.BUTTON3) gameState.setRightClick(true);

            for (int i = 0; i < gameState.getGrid().length; i++) {
                for (int j = 0; j < gameState.getGrid()[i].length; j++) {
                    Square space = gameState.getGrid()[i][j];
                    if (e.getX() >= space.x && e.getX() <= space.x + 30 && e.getY() - 30 >= space.y && e.getY() - 30 <= space.y + 30 && !gameState.isGameOver() && !gameState.isGameWon()) {
                        if ((gameState.isRightClick() && gameState.isLeftClick()) || e.getButton() == MouseEvent.BUTTON2) {
                            if (!space.isMine() && space.isVisible) {
                                space.chord(gameState.getGrid(), i, j);
                            }
                        } else if (gameState.isLeftClick()) {
                            if (space.isMine()) {
                                gameState.setLostSquare(space);
                                gameState.setGameOver(true);
                            } else {
                                space.calculateMines(gameState.getGrid(), i, j);
                            }
                        } else if (gameState.isRightClick()) {
                            if (space.isFlag()) gameState.incrementFlag();
                            else gameState.decrementFlag();
                            space.turnFlag();
                        }
                    }
                }
            }
        }

        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == MouseEvent.BUTTON1) gameState.setLeftClick(false);
            else if (e.getButton() == MouseEvent.BUTTON3) gameState.setRightClick(false);
        }
    }
}

class Runner extends TimerTask {
    private final Drawing drawing;

    public Runner(GameState gameState) {
        drawing = new Drawing(gameState);
    }

    public void run() {
        drawing.repaint();
    }
}

