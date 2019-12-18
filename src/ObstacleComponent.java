import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ObstacleComponent extends JComponent {

    private Rectangle2D.Double rectangle;
    private double x;
    private double y;
    private double width;
    private double height;

    public ObstacleComponent(double x, double y, double width, double height){
        super();
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        rectangle = new Rectangle2D.Double(this.x, this.y, this.width, this.height);
        g2.setColor(Color.blue);
        g2.draw(rectangle);
    }

}
