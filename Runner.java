import java.util.TimerTask;
public class Runner extends TimerTask {
    private final Drawing drawing;

    public Runner(GameState gameState) {
        drawing = gameState.getDrawing();
    }

    public void run() {
        drawing.repaint();
    }
}