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
    private Drawing drawing;
    private GameState gameState;
    public JFrame getFrame(){
        return frame;
    }

    public Level(char level) {
        gameState = new GameState();
        gameState.setTimeTicker(System.currentTimeMillis());
        drawing = gameState.getDrawing();
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
            gameState.setScreenHeight(690);
            gameState.setScreenWidth(520);
        } else {
            gameState.setNumOfMines(99);
            gameState.setGridWidth(30);
            gameState.setGridHeight(16);
            gameState.setScreenHeight(690);
            gameState.setScreenWidth(940);
        }
        gameState.setFlagsLeft(gameState.getNumOfMines());
        gameState.setGrid(new Square[gameState.getGridHeight()][gameState.getGridWidth()]);

        generateSquares();
        frame.setSize(gameState.getScreenWidth(), gameState.getScreenHeight());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addMouseListener(new Mousehandler(gameState, this));
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
}

