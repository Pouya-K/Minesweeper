import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;

public class Level {
    private JFrame frame = new JFrame("Minesweeper");
    private Drawing drawing = new Drawing();
    private int screenWidth, screenHeight, numOfMines, gridWidth, gridHeight;
    private Square[][] grid;
    public Level(char level){
        if(level == 'b'){
            numOfMines = 10;
            gridWidth = 9;
            gridHeight = 9;
            screenWidth = 300;
            screenHeight = 400;
        }
        else if(level == 'i'){
            numOfMines = 40;
            gridWidth = 16;
            gridHeight = 16;
        }
        else{
            numOfMines = 99;
            gridWidth = 30;
            gridHeight = 16;
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
        for(int i = 0; i<gridHeight; i++){
            for(int j = 0; j<gridWidth; j++){
                grid[i][j] = new Square();
            }
        }
        int counter = 0;
        while(counter < numOfMines){
            int x = (int) (Math.random() * gridHeight);
            int y = (int) (Math.random() * gridWidth);
            if(!grid[x][y].isMine()){
                grid[x][y].turnMine();
                counter++;
            }
        }
    }
    class Drawing extends JComponent{
        public void paint(Graphics g){
            g.setColor(Color.gray);
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.darkGray);
            g.fillRect(10,10,getWidth()-20, 50);
            g.fillRect(10,70, getWidth()-20, getHeight() - 80);
            
        }
    }
    class Mousehandler extends MouseAdapter{

    }

    public static void main(String[] args) {
        new Level('b');
    }
}
