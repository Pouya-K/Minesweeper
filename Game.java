import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Game {
    JFrame frame = new JFrame("Minesweeper");
    Drawing drawing = new Drawing();
    private Color label1 = Color.black, label2 = Color.black, label3 = Color.black;
    private Mouselistener mouseListen = new Mouselistener();
    public Game(){
        frame.setSize(300,250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(drawing);
        frame.addMouseListener(mouseListen);
        frame.addMouseMotionListener(mouseListen);
        frame.setVisible(true);
    }
    class Drawing extends JComponent{
        public void paint(Graphics g){
            g.setColor(Color.red);
            g.setFont(new Font("Serif", Font.BOLD, 30));
            g.drawString("Minesweeper", (getWidth() - g.getFontMetrics().stringWidth("Minesweeper")) / 2,40);
            g.setColor(label1);
            g.drawString("Beginner", (getWidth() - g.getFontMetrics().stringWidth("Beginner")) / 2,90);
            g.setColor(label2);
            g.drawString("Intermediate", (getWidth() - g.getFontMetrics().stringWidth("Intermediate")) / 2,140);
            g.setColor(label3);
            g.drawString("Expert", (getWidth() - g.getFontMetrics().stringWidth("Expert")) / 2,190);
        }
    }
    class Mouselistener extends MouseAdapter{
        public void mouseClicked(MouseEvent e){
            if(e.getX()>=95 && e.getX()<=200 && e.getY()>=95 && e.getY()<=120){
                frame.dispose();
                new Level('b');
            }
            else if(e.getX()>=70 && e.getX()<=230 && e.getY()>=150 && e.getY()<=165){
                frame.dispose();
                new Level('i');
            }
            else if(e.getX()>=105 && e.getX()<=190 && e.getY()>=200 && e.getY()<=215){
                frame.dispose();
                new Level('x');
            }
            drawing.repaint();
        }
        public void mouseMoved(MouseEvent e){
            if(e.getX()>=95 && e.getX()<=200 && e.getY()>=95 && e.getY()<=120){
                label1 = Color.red;
            }
            else if(e.getX()>=70 && e.getX()<=230 && e.getY()>=150 && e.getY()<=165){
                label2 = Color.red;
            }
            else if(e.getX()>=105 && e.getX()<=190 && e.getY()>=200 && e.getY()<=215){
                label3 = Color.red;
            }
            else{
                label1 = Color.black;
                label2 = Color.black;
                label3 = Color.black;
            }
            drawing.repaint();
        }
    }
    public static void main(String[] args) {
        new Game();
    }
}
