import javax.swing.*;
import java.awt.*;

public class Algorithm {

    private static Algorithm instance = null;
    JFrame window = new JFrame();
    JPanel pane = new JPanel();

    private Algorithm(){}

    public static Algorithm getInstance() {
        if (instance == null) {
            instance = new Algorithm();
            instance.window.setTitle("Smart Dots");
            instance.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            instance.window.setSize(800,1000);
            LayoutManager overlay = new OverlayLayout(instance.pane);
            instance.pane.setLayout(overlay);
            instance.window.add(instance.pane, BorderLayout.CENTER);
            instance.window.setVisible(true);
        }
        return instance;
    }


    public static void main(String[] args) throws  InterruptedException{

        Population test = new Population(50);

        while(true){
            if (test.allDotsDead()){
                test.calculateFitness();
                test.naturalSelection();
                test.mutateBabies();
                Algorithm.getInstance().pane.removeAll();
            } else {
                //draw goal
                Dot goal = new Dot();
                goal.setPos(new PVector(400,10));
                Algorithm.getInstance().showDot(goal, Color.red, 10);
                //draw obstacle
                Algorithm.getInstance().showObstacle(0, 300, 600, 10);
                test.update();
                test.show();
                Algorithm.getInstance().pane.setVisible(true);
                Algorithm.getInstance().pane.revalidate();
                Thread.sleep(10);
            }
        }

    }

    void showDot(Dot dot, Color color, int size){
        DotComponent dotComponent = new DotComponent(size, color, dot);
        dotComponent.setVisible(true);
        this.getInstance().pane.add(dotComponent);
        dotComponent.repaint();
    }

    void showObstacle(double x, double y, double width, double height){
        ObstacleComponent obstacleComponent = new ObstacleComponent(x, y, width, height);
        obstacleComponent.setVisible(true);
        this.getInstance().pane.add(obstacleComponent);
    }

    void showGeneration(int gen){
        this.pane.setBorder(BorderFactory.createTitledBorder("Generation : " + Integer.toString(gen)));
    }

}
