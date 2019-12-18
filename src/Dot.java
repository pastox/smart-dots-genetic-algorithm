import java.awt.*;

public class Dot {

    private PVector pos;
    private PVector vel;
    private PVector acc;

    private PVector goal = new PVector(400, 10);
    private double width = 800;
    private double height = 800;

    private Brain brain;

    private boolean dead = false;
    private boolean reachedGoal = false;
    private boolean isBest = false; //true if this dot is the best dot from the previous generation

    double fitness = 0;

    Dot(){
        this.brain = new Brain(1000); //new brain with 1000 instructions
        this.pos = new PVector(width/2, height-10);
        this.vel = new PVector(0,0);
        this.acc = new PVector(0,0);
    }


    //----------------------------------------------------------------------------------------------

    //draws the dot on the screen
    void show(){
        //if this dot is the best dot from the previous generation then draw it as a big green dot
        if (isBest){
            Algorithm.getInstance().showDot(this, Color.green, 8);
        } else {
            Algorithm.getInstance().showDot(this, Color.black, 4);
        }
    }

    //----------------------------------------------------------------------------------------------

    //moves the dot according to the brain's directions
    void move(){
        PVector[] directions = this.brain.getDirections();
        int brainStep = this.brain.getStep();
        if (directions.length > this.brain.getStep()) { //if there are still directions left then set the acceleration as the next PVector in the directions array
            this.acc = directions[brainStep];
            this.brain.setStep(brainStep+1);
        } else { //if at the end of the directions array then the dot is dead
            this.dead = true;
        }
        //apply the acceleration and move the dot
        this.vel.add(this.acc);
        this.vel.limit(5);
        this.pos.add(this.vel);
    }

    //----------------------------------------------------------------------------------------------

    //calls the move function and checks for collision and stuff
    void update(){
        if (!dead && !reachedGoal) {
            move();
            if (this.pos.getX() < 2 || this.pos.getY() < 2 || this.pos.getX() > width - 2 || this.pos.getY() > height - 2) { //if near the edges of the window then kill it
                this.dead = true;
            } else if (this.pos.dist(this.goal) < 5) { //if reached goal
                this.reachedGoal = true;
            } else if (this.pos.getX() < 600 && this.pos.getY() < 310 && this.pos.getX() > 0 && this.pos.getY() > 300) { //if hit obstacle
                this.dead = true;
            }
        }
    }

    //----------------------------------------------------------------------------------------------

    //calculates the fitness
    void calculateFitness() {
        if (this.reachedGoal) { //if it reached the goal then the fitness is computed based on the amount of steps it took the point to get there
            this.fitness = 1.0 / 16.0 + 10000.0 / (float) (Math.pow(this.brain.getStep(), 2));
        } else { //else it is based on how close the point is to the goal
            double distanceToGoal = this.pos.dist(this.goal);
            this.fitness = 1.0 / (distanceToGoal * distanceToGoal);
        }
    }

    //----------------------------------------------------------------------------------------------

    public void setBrain(Brain brain) {
        this.brain = brain;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public Brain getBrain() {
        return brain;
    }

    public void setBest(boolean best) {
        this.isBest = best;
    }

    public boolean isReachedGoal() {
        return reachedGoal;
    }

    public boolean isDead() {
        return dead;
    }

    public double getFitness() {
        return fitness;
    }

    public PVector getPos() {
        return pos;
    }

    public void setPos(PVector pos) {
        this.pos = pos;
    }

    //clone the dot
    Dot cloneDot(){
        Dot baby = new Dot();
        baby.setBrain(this.brain.cloneBrain());
        return baby;
    }

}
