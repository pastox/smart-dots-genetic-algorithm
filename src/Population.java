import java.sql.SQLOutput;

public class Population {

    private Dot[] dots;

    double fitnessSum;
    int gen = 1;

    int bestDot = 0; //index of the best dot in the dots array

    int minStep = 2000;

    Population(int size){
        this.dots = new Dot[size];
        for (int i = 0; i<size; i++){
            this.dots[i] = new Dot();
        }
    }

    //--------------------------------------------------------------------------------------------

    //show all dots
    void show(){
        Algorithm.getInstance().showGeneration(this.gen);
        for (int i = 1; i<this.dots.length; i++){
            dots[i].show();
        }
        dots[0].show();
    }

    //---------------------------------------------------------------------------------------------

    //update all dots
    void update() {
        for (int i = 0; i<dots.length; i++){
            if (this.dots[i].getBrain().getStep() > this.minStep) {//if the dot has already made more steps than it took the best point to reach the goal, then it dies
                this.dots[i].setDead(true);
            } else {
                this.dots[i].update();
            }
        }
    }

    //----------------------------------------------------------------------------------------------

    //calculate all the fitnesses
    void calculateFitness(){
        for (int i = 0; i<dots.length; i++){
            dots[i].calculateFitness();
        }
    }

    //-----------------------------------------------------------------------------------------------

    //returns whether all the dots are either dead or have reached the goal
    boolean allDotsDead(){
        for (int i = 0; i<this.dots.length; i++){
            if (!this.dots[i].isDead() && !this.dots[i].isReachedGoal()){
                return false;
            }
        }
        return true;
    }

    //------------------------------------------------------------------------------------------------

    //gets the next generation of dots
    void naturalSelection(){
        Dot[] newDots = new Dot[this.dots.length]; //next generation
        this.setBestDot();
        this.calculateFitnessSum();
        //we keep the best dot
        newDots[0] = this.dots[this.bestDot].cloneDot();
        newDots[0].setBest(true);
        for (int i=1; i<newDots.length; i++){
            //select parent based on fitness
            Dot parent = this.selectParent();
            //get baby from them
            newDots[i] = parent.cloneDot();
        }

        this.dots = newDots.clone();
        this.gen++;
    }

    //-------------------------------------------------------------------------------------------------

    void calculateFitnessSum(){
        double fitnessSum = 0;
        for (int i = 0; i<this.dots.length; i++){
            fitnessSum += this.dots[i].getFitness();
        }
        this.fitnessSum = fitnessSum;
    }

    //---------------------------------------------------------------------------------------------------

    //choosing dot from the population to return randomly (considering fitness)
    //this function works by randomly choosing a value between 0 and the sum of all the fitnesses
    //then go through all the dots and add their fitness to a running sum and if that sum is greater than the random value generated that dot is chosen
    //since dots with a higher fitness function add more to the running sum then they have a higher chance of being chosen
    Dot selectParent(){
        double rand = Math.random()*this.fitnessSum;
        double runningSum = 0;
        for (int i = 0; i<this.dots.length; i++){
            runningSum += this.dots[i].getFitness();
            if (runningSum > rand){
                return this.dots[i];
            }
        }
        return null;
    }

    //----------------------------------------------------------------------------------------------------

    //mutates all the brains of the babies
    void mutateBabies(){
        for (int i = 0; i<this.dots.length; i++){
            this.dots[i].getBrain().mutate();
        }
    }

    //-----------------------------------------------------------------------------------------------------

    //finds the dot with the highest fitness and sets it as the best dot
    void setBestDot(){
        double max = 0;
        int maxIndex = 0;
        for (int i = 0; i<this.dots.length; i++){
            if (dots[i].fitness > max){
                max = dots[i].fitness;
                maxIndex = i;
            }
        }
        this.bestDot = maxIndex;
        //if this dot reached the goal then reset the minimum number of steps it takes to get to the goal
        if (this.dots[bestDot].isReachedGoal()){
            this.minStep = this.dots[bestDot].getBrain().getStep();
            System.out.println("step:" + minStep);
        }
    }

}
