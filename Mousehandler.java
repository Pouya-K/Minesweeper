import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Mousehandler extends MouseAdapter {
    GameState gameState;
    Level level;
    public Mousehandler(GameState gameState, Level level){
        this.gameState = gameState;
        this.level = level;
    }
    public void mousePressed(MouseEvent e) {
        if (e.getX() >= gameState.getDrawing().getWidth() / 2 - 50 && e.getX() <= gameState.getDrawing().getWidth() / 2 - 5 && e.getY() >= gameState.getDrawing().getHeight() - 20 && e.getY() <= gameState.getDrawing().getHeight() + 25) {
            level.getFrame().dispose();
            new Game();
        } else if (e.getX() >= gameState.getDrawing().getWidth() / 2 && e.getX() <= gameState.getDrawing().getWidth() / 2 + 45 && e.getY() >= gameState.getDrawing().getHeight() - 20 && e.getY() <= gameState.getDrawing().getHeight() + 25) {
            gameState.setFlagsLeft(gameState.getNumOfMines());
            gameState.setTime(0);
            gameState.setTimeTicker(System.currentTimeMillis());
            gameState.setGameWon(false);
            gameState.setGameOver(false);
            gameState.setLostSquare(null);
            gameState.getCheckedSquares().clear();
            level.generateSquares();
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