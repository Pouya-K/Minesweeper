import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Timer;
import java.util.ArrayList;
import java.util.TimerTask;

public class Level {
    private JFrame frame = new JFrame("Minesweeper");
    public static Drawing drawing;
    private boolean leftClick = false, rightClick = false;
    private int flagsLeft = 10;
    public static Square lostSquare;
    private int time = 0;
    private long timeTicker;
    public static ArrayList<Square> checkedSquares = new ArrayList<Square>();
    private int screenWidth, screenHeight, numOfMines, gridWidth, gridHeight;
    private Square[][] grid;
    public static boolean gameOver = false;
    private boolean gameWon = false;
    public Level(char level){
        drawing = new Drawing();
        timeTicker = System.currentTimeMillis();
        if(level == 'b'){
            numOfMines = 10;
            gridWidth = 9;
            gridHeight = 9;
            screenWidth = 310;
            screenHeight = 480;
        }
        else if(level == 'i'){
            numOfMines = 40;
            gridWidth = 16;
            gridHeight = 16;
            screenWidth = 520;
            screenHeight = 690;
        }
        else{
            numOfMines = 99;
            gridWidth = 30;
            gridHeight = 16;
            screenWidth = 940;
            screenHeight = 690;
        }
        grid = new Square[gridHeight][gridWidth];
        generateSquares();
        frame.setSize(screenWidth,screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(drawing);
        frame.addMouseListener(new Mousehandler());
        frame.setVisible(true);
        int gap = 1000/60;
        Timer timer = new Timer();
        timer.schedule(new Runner(), 0,gap);
    }
    public void generateSquares(){
        int x = 20, y = 110;
        for(int i = 0; i<gridHeight; i++){
            for(int j = 0; j<gridWidth; j++){
                grid[i][j] = new Square(x, y);
                x += 30;
            }
            x = 20;
            y += 30;
        }
        int counter = 0;
        while(counter < numOfMines){
            int x1 = (int) (Math.random() * gridHeight);
            int y1 = (int) (Math.random() * gridWidth);
            if(!grid[x1][y1].isMine()){
                grid[x1][y1].turnMine();
                counter++;
            }
        }
    }
    class Drawing extends JComponent{
        public void paint(Graphics g){
            g.setColor(Color.gray);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.darkGray);
            g.fillRect(10,10,getWidth()-20, 80);
            g.fillRect(10,100, getWidth()-20, getHeight() - 160);
            g.setColor(Color.black);
            g.fillRect(20,20, 100, 60);
            g.fillRect(getWidth()-120,20, 100, 60);
            g.setColor(Color.lightGray);
            g.fillRect(getWidth()/2-25, 25, 50, 50);
            checkForWin();
            String str = "";
            if(gameOver){
                for (Square[] array : grid) {
                    for (Square gridSpot : array) {
                        if(gridSpot.isMine()) gridSpot.turnVisible();
                    }
                }
                str = "\uD83D\uDE35";
            }
            else if(gameWon){
                for (Square[] array : grid) {
                    for (Square gridSpot : array) {
                        if(gridSpot.isMine()) gridSpot.turnFlag();
                    }
                }
                str = "\uD83D\uDE0E";
            }
            else{
                str = "\uD83D\uDE42";
            }
            g.setFont(new Font("Serif", Font.BOLD, 40));
            g.drawString(str, getWidth()/2 - (g.getFontMetrics().stringWidth(str)) / 2, 65);
            g.setColor(Color.lightGray);
            g.fillRect(getWidth()/2-50, getHeight()-50, 45, 45);
            g.fillRect(getWidth()/2, getHeight()-50, 45, 45);
            str = "\uD83C\uDFE0";
            g.setFont(new Font("Serif", Font.BOLD, 35));
            g.drawString(str, getWidth()/2-27-(g.getFontMetrics().stringWidth(str)) / 2, getHeight()-15);
            str = "\uD83D\uDD04";
            g.drawString(str, getWidth()/2+22-(g.getFontMetrics().stringWidth(str)) / 2, getHeight()-15);

            try {
                File font_file = new File("digital-7.ttf");
                Font font = Font.createFont(Font.TRUETYPE_FONT, font_file);
                Font sizedFont = font.deriveFont(60f);
                g.setFont(sizedFont);
            } catch (Exception e) {
            }
            g.setColor(Color.red);
            if(flagsLeft<0) str = "0"+flagsLeft;
            else if(flagsLeft<10) str = "00" + flagsLeft;
            else if(flagsLeft<100) str = "0" + flagsLeft;
            g.drawString(str, 115-g.getFontMetrics().stringWidth(str), 70);
            if(time<10) str = "00"+time;
            else if(time<100) str = "0"+time;
            else str = ""+time;
            g.drawString(str, getWidth()-25-g.getFontMetrics().stringWidth(str), 70);
            if(System.currentTimeMillis() - timeTicker >= 1000 && !gameWon && !gameOver){
                time++;
                timeTicker = System.currentTimeMillis();
            }
            drawGrid(g);

        }
        public void drawGrid(Graphics g){
            g.setColor(Color.lightGray);
            g.fillRect(20,110, getWidth()-40, getHeight() - 180);
            for (Square[] array : grid) {
                for (Square gridSpot : array) {
                    if(!gridSpot.getVisibility()){
                        g.setColor(Color.white);
                        g.drawRect(gridSpot.x, gridSpot.y, 30,30);
                        g.drawRect(gridSpot.x+1, gridSpot.y+1, 29,29);
                        g.drawRect(gridSpot.x+2, gridSpot.y+2, 28,28);
                        if(gridSpot.isFlag()){
                            g.setFont(new Font("Serif", Font.BOLD, 25));
                            String str = "\uD83D\uDEA9";
                            g.drawString(str, gridSpot.x + 15 - (g.getFontMetrics().stringWidth(str)) / 2, gridSpot.y+25);
                        }
                    }
                    else{
                        g.setColor(Color.darkGray);
                        g.drawRect(gridSpot.x, gridSpot.y, 30,30);
                        if(gridSpot.isMine()){
                            if(gridSpot.equals(lostSquare) && gameOver){
                                g.setColor(Color.red);
                                g.fillRect(gridSpot.x, gridSpot.y, 30,30);
                            }
                            g.setFont(new Font("Serif", Font.BOLD, 25));
                            String str = "\uD83D\uDCA3";
                            g.drawString(str, gridSpot.x + 15 - (g.getFontMetrics().stringWidth(str)) / 2, gridSpot.y+25);
                        }
                        if(!gridSpot.isMine() && gridSpot.getNumOfCloseMines() != 0){
                            switch (gridSpot.getNumOfCloseMines()){
                                case 1:
                                    g.setColor(Color.blue);
                                    break;
                                case 2:
                                    g.setColor(Color.green);
                                    break;
                                case 3:
                                    g.setColor(Color.red);
                                    break;
                                case 4:
                                    g.setColor(new Color(42, 42, 147));
                                    break;
                                case 5:
                                    g.setColor(new Color(128,0,1));
                                    break;
                                case 6:
                                    g.setColor(new Color(42, 148, 148));
                                    break;
                                case 7:
                                    g.setColor(Color.black);
                                    break;
                                case 8:
                                    g.setColor(Color.darkGray);
                            }
                            g.setFont(new Font("Serif", Font.BOLD, 25));
                            String num = gridSpot.getNumOfCloseMines() + "";
                            g.drawString(num, gridSpot.x + 15 - (g.getFontMetrics().stringWidth(num)) / 2, gridSpot.y+25);
                        }
                    }
                }
            }
        }
        public void checkForWin(){
            boolean won = false, notWon = false;
            for (Square[] array : grid) {
                for (Square gridSpot : array) {
                    if(gridSpot.isMine() && gridSpot.isFlag()) won = true;
                    else notWon = true;
                }
            }
            if(won && !notWon) gameWon = true;

            won = false;
            notWon = false;
            for (Square[] array : grid) {
                for (Square gridSpot : array) {
                    if(!gridSpot.isMine() && gridSpot.isVisible) won = true;
                    else if(!gridSpot.isMine() && !gridSpot.isVisible) notWon = true;
                }
            }
            if(won && !notWon) gameWon = true;
        }
    }
    class Mousehandler extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            if(e.getX() >= drawing.getWidth()/2-50 && e.getX()<= drawing.getWidth()/2-5 && e.getY()>=drawing.getHeight()-20 && e.getY()<=drawing.getHeight()+25){
                frame.dispose();
                new Game();
            }
            else if(e.getX()>=drawing.getWidth()/2 && e.getX()<=drawing.getWidth()/2+45 && e.getY()>=drawing.getHeight()-20 && e.getY()<=drawing.getHeight()+25){
                flagsLeft = 10;
                time = 0;
                timeTicker = System.currentTimeMillis();
                gameWon = false;
                gameOver = false;
                lostSquare = null;
                checkedSquares.clear();
                generateSquares();
            }
            if(e.getButton() == MouseEvent.BUTTON1) leftClick = true;
            else if(e.getButton() == MouseEvent.BUTTON3) rightClick = true;
            for(int i = 0; i<grid.length; i++){
                for(int j = 0; j<grid[i].length; j++){
                    Square space = grid[i][j];
                    if(e.getX() >= space.x && e.getX() <= space.x+30 && e.getY() - 30 >= space.y && e.getY() - 30 <= space.y+30 && !gameOver && !gameWon){
                        if((rightClick && leftClick) || e.getButton() == MouseEvent.BUTTON2){
                            if(!space.isMine() && space.isVisible){
                                space.chord(grid, i, j);
                            }
                        }
                        else if(leftClick){
                            if(space.isMine()){
                                lostSquare = space;
                                gameOver = true;
                            }
                            else{
                                space.calculateMines(grid, i, j);
                            }
                        }
                        else if(rightClick){
                            if(space.isFlag()) flagsLeft++;
                            else flagsLeft--;
                            space.turnFlag();
                        }
                    }
                }
            }
        }
        public void mouseReleased(MouseEvent e){
            if(e.getButton() == MouseEvent.BUTTON1) leftClick = false;
            else if(e.getButton() == MouseEvent.BUTTON3) rightClick = false;
        }
    }
}
class Runner extends TimerTask {
    public void run(){
        Level.drawing.repaint();
    }
}

