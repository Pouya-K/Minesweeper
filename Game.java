import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {
    JFrame frame = new JFrame("Minesweeper");
    public Game(){
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new Drawing());
        frame.addMouseListener(new Mousehandler());
        frame.setVisible(true);
    }
    class Drawing extends JComponent{
        public void paint(Graphics g){
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Minesweeper", (getWidth() - g.getFontMetrics().stringWidth("Minesweeper")) / 2,50);
        }
    }
    class Mousehandler extends MouseAdapter{
        public void mouseClicked(MouseEvent e){

        }
    }
    public static void main(String[] args) {
        new Game();
    }
}
