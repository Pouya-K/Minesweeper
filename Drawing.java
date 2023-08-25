import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Drawing extends JComponent {
    private GameState gameState;

    public Drawing(GameState gameState) {
        this.gameState = gameState;
    }


    public void paint(Graphics g) {
        drawWindow(g);
        checkForWin();

        String faceStatus = "";
        if (gameState.isGameOver()) {
            revealAllMines();
            faceStatus = "\uD83D\uDE35";
        } else if (gameState.isGameWon()) {
            flagAllMines();
            faceStatus = "\uD83D\uDE0E";
        } else {
            faceStatus = "\uD83D\uDE42";
        }


        g.setFont(new Font("Serif", Font.BOLD, 40));
        g.drawString(faceStatus, getWidth() / 2 - (g.getFontMetrics().stringWidth(faceStatus)) / 2, 65);
        g.setColor(Color.lightGray);
        g.fillRect(getWidth() / 2 - 50, getHeight() - 50, 45, 45);
        g.fillRect(getWidth() / 2, getHeight() - 50, 45, 45);

        String homeEmoji = "\uD83C\uDFE0";
        g.setFont(new Font("Serif", Font.BOLD, 35));
        g.drawString(homeEmoji, getWidth() / 2 - 27 - (g.getFontMetrics().stringWidth(homeEmoji)) / 2, getHeight() - 15);
        String restartEmoji = "\uD83D\uDD04";
        g.drawString(restartEmoji, getWidth() / 2 + 22 - (g.getFontMetrics().stringWidth(restartEmoji)) / 2, getHeight() - 15);

        setupFont(g);
        displayFlagTime(g);

        displayGameTime(g);

        if (System.currentTimeMillis() - gameState.getTimeTicker() >= 1000 && !gameState.isGameWon() && !gameState.isGameOver()) {
            gameState.incrementTime();
            gameState.setTimeTicker(System.currentTimeMillis());
        }
        drawGrid(g);

    }

    private void displayGameTime(Graphics g) {
        String timeLeftDisplay = "";
        if (gameState.getTime() < 10) timeLeftDisplay = "00" + gameState.getTime();
        else if (gameState.getTime() < 100) timeLeftDisplay = "0" + gameState.getTime();
        else timeLeftDisplay = "" + gameState.getTime();
        g.drawString(timeLeftDisplay, getWidth() - 25 - g.getFontMetrics().stringWidth(timeLeftDisplay), 70);
    }

    private void displayFlagTime(Graphics g) {
        g.setColor(Color.red);
        String flagLeftDisplay = "";
        if (gameState.getFlagsLeft() < 0) flagLeftDisplay = "0" + gameState.getFlagsLeft();
        else if (gameState.getFlagsLeft() < 10) flagLeftDisplay = "00" + gameState.getFlagsLeft();
        else if (gameState.getFlagsLeft() < 100) flagLeftDisplay = "0" + gameState.getFlagsLeft();
        g.drawString(flagLeftDisplay, 115 - g.getFontMetrics().stringWidth(flagLeftDisplay), 70);
    }

    private void revealAllMines() {
        for (Square[] array : gameState.getGrid()) {
            for (Square gridSpot : array) {
                if (gridSpot.isMine()) gridSpot.turnVisible();
            }
        }
    }

    private void flagAllMines() {
        for (Square[] array : gameState.getGrid()) {
            for (Square gridSpot : array) {
                if (gridSpot.isMine()) gridSpot.turnFlag();
            }
        }
    }

    private void drawWindow(Graphics graphics) {
        graphics.setColor(Color.gray);
        graphics.fillRect(0, 0, getWidth(), getHeight());
        graphics.setColor(Color.darkGray);
        graphics.fillRect(10, 10, getWidth() - 20, 80);
        graphics.fillRect(10, 100, getWidth() - 20, getHeight() - 160);
        graphics.setColor(Color.black);
        graphics.fillRect(20, 20, 100, 60);
        graphics.fillRect(getWidth() - 120, 20, 100, 60);
        graphics.setColor(Color.lightGray);
        graphics.fillRect(getWidth() / 2 - 25, 25, 50, 50);
    }

    private void setupFont(Graphics graphics) {
        try {
            File font_file = new File("digital-7.ttf");
            Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
            Font sizedFont = font.deriveFont(60f);
            graphics.setFont(sizedFont);
        } catch (IOException | FontFormatException e) {
            System.out.println("Error creating font");
            e.printStackTrace();
        }
    }

    public void drawGrid(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(20, 110, getWidth() - 40, getHeight() - 180);
        for (Square[] array : gameState.getGrid()) {
            for (Square gridSpot : array) {
                if (!gridSpot.getVisibility()) {
                    g.setColor(Color.white);
                    g.drawRect(gridSpot.x, gridSpot.y, 30, 30);
                    g.drawRect(gridSpot.x + 1, gridSpot.y + 1, 29, 29);
                    g.drawRect(gridSpot.x + 2, gridSpot.y + 2, 28, 28);
                    if (gridSpot.isFlag()) {
                        g.setFont(new Font("Serif", Font.BOLD, 25));
                        String str = "\uD83D\uDEA9";
                        g.drawString(str, gridSpot.x + 15 - (g.getFontMetrics().stringWidth(str)) / 2, gridSpot.y + 25);
                    }
                } else {
                    g.setColor(Color.darkGray);
                    g.drawRect(gridSpot.x, gridSpot.y, 30, 30);
                    if (gridSpot.isMine()) {
                        if (gridSpot.equals(gameState.getLostSquare()) && gameState.isGameOver()) {
                            g.setColor(Color.red);
                            g.fillRect(gridSpot.x, gridSpot.y, 30, 30);
                        }
                        g.setFont(new Font("Serif", Font.BOLD, 25));
                        String str = "\uD83D\uDCA3";
                        g.drawString(str, gridSpot.x + 15 - (g.getFontMetrics().stringWidth(str)) / 2, gridSpot.y + 25);
                    }
                    if (!gridSpot.isMine() && gridSpot.getNumOfCloseMines() != 0) {
                        switch (gridSpot.getNumOfCloseMines()) {
                            case 1 -> g.setColor(Color.blue);
                            case 2 -> g.setColor(Color.green);
                            case 3 -> g.setColor(Color.red);
                            case 4 -> g.setColor(new Color(42, 42, 147));
                            case 5 -> g.setColor(new Color(128, 0, 1));
                            case 6 -> g.setColor(new Color(42, 148, 148));
                            case 7 -> g.setColor(Color.black);
                            case 8 -> g.setColor(Color.darkGray);
                        }
                        g.setFont(new Font("Serif", Font.BOLD, 25));
                        String num = gridSpot.getNumOfCloseMines() + "";
                        g.drawString(num, gridSpot.x + 15 - (g.getFontMetrics().stringWidth(num)) / 2, gridSpot.y + 25);
                    }
                }
            }
        }
    }

    public void checkForWin() {
        boolean won = false, notWon = false;
        for (Square[] array : gameState.getGrid()) {
            for (Square gridSpot : array) {
                if (gridSpot.isMine() && gridSpot.isFlag()) won = true;
                else notWon = true;
            }
        }
        if (won && !notWon) gameState.setGameWon(true);

        won = false;
        notWon = false;
        for (Square[] array : gameState.getGrid()) {
            for (Square gridSpot : array) {
                if (!gridSpot.isMine() && gridSpot.isVisible) won = true;
                else if (!gridSpot.isMine() && !gridSpot.isVisible) notWon = true;
            }
        }
        if (won && !notWon) gameState.setGameWon(true);
    }
}
