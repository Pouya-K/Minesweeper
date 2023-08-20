import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Level {
    private JFrame frame = new JFrame("Minesweeper");
    private Drawing drawing = new Drawing();
    private boolean leftClick = false, rightClick = false;
    private int flagsLeft = 10;
    public static Square lostSquare;
    public static ArrayList<Square> checkedSquares = new ArrayList<Square>();
    private int screenWidth, screenHeight, numOfMines, gridWidth, gridHeight;
    private Square[][] grid;
    public static boolean gameOver = false;
    private boolean gameWon = false;
    public Level(char level){
        if(level == 'b'){
            numOfMines = 10;
            gridWidth = 9;
            gridHeight = 9;
            screenWidth = 310;
            screenHeight = 430;
        }
        else if(level == 'i'){
            numOfMines = 40;
            gridWidth = 16;
            gridHeight = 16;
            screenWidth = 520;
            screenHeight = 640;
        }
        else{
            numOfMines = 99;
            gridWidth = 30;
            gridHeight = 16;
            screenWidth = 940;
            screenHeight = 640;
        }
        grid = new Square[gridHeight][gridWidth];
        generateSquares();
        frame.setSize(screenWidth,screenHeight);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(drawing);
        frame.addMouseListener(new Mousehandler());
        frame.setVisible(true);
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
            g.fillRect(10,100, getWidth()-20, getHeight() - 110);
            g.setColor(Color.black);
            g.fillRect(20,20, 100, 60);
            g.fillRect(getWidth()-120,20, 100, 60);
            if(gameOver){
                for (Square[] array : grid) {
                    for (Square gridSpot : array) {
                        if(gridSpot.isMine()) gridSpot.turnVisible();
                    }
                }
            }
            drawGrid(g);
        }
        public void drawGrid(Graphics g){
            g.setColor(Color.lightGray);
            g.fillRect(20,110, getWidth()-40, getHeight() - 130);
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

    }
    class Mousehandler extends MouseAdapter{
        public void mousePressed(MouseEvent e){
            if(e.getButton() == MouseEvent.BUTTON1) leftClick = true;
            else if(e.getButton() == MouseEvent.BUTTON3) rightClick = true;
            for(int i = 0; i<grid.length; i++){
                for(int j = 0; j<grid[i].length; j++){
                    Square space = grid[i][j];
                    if(e.getX() >= space.x && e.getX() <= space.x+30 && e.getY() - 30 >= space.y && e.getY() - 30 <= space.y+30 && !gameOver){
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
            drawing.repaint();
        }
        public void mouseReleased(MouseEvent e){
            if(e.getButton() == MouseEvent.BUTTON1) leftClick = false;
            else if(e.getButton() == MouseEvent.BUTTON3) rightClick = false;
            drawing.repaint();
        }
    }

    public static void main(String[] args) {
        new Level('b');
    }
}
