import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class DotComponent extends JComponent {

    private Ellipse2D.Double ellipse;
    private Dot dot;
    private Color color;
    private int size;

    public DotComponent(int size, Color color, Dot dot){
        super();
        this.dot = dot;
        this.color = color;
        this.size = size;
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        ellipse = new Ellipse2D.Double(this.dot.getPos().getX(), this.dot.getPos().getY(), this.size, this.size );
        g2.setBackground(this.color);
        g2.draw(ellipse);
    }

}
