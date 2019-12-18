public class Brain {

    private PVector[] directions;
    int step = 0;

    Brain(int size){
        this.directions = new PVector[size];
        this.randomize();
    }

    //------------------------------------------------------------------------------------------

    //sets all the vectors in directions to a random vector with length 1
    void randomize(){
        for (int i=0; i<this.directions.length; i++){
            double randomAngle = Math.random()*2*Math.PI;
            this.directions[i] = PVector.fromAngle(randomAngle);
        }
    }

    //--------------------------------------------------------------------------------------------

    //returns a perfect copy of this brain object
    Brain cloneBrain(){
        Brain clone = new Brain(this.directions.length);
        for (int i = 0; i<this.directions.length; i++){
            clone.directions[i] = this.directions[i];
        }
        return clone;
    }

    //---------------------------------------------------------------------------------------------

    //mutates the brain by setting some of the directions to random vectors
    void mutate(){
        double mutationRate = 0.01;
        for (int i = 0; i<this.directions.length; i++){
            double rand = Math.random();
            if (rand < mutationRate){
                double randomAngle = Math.random()*2*Math.PI;
                this.directions[i] = PVector.fromAngle(randomAngle);
            }
        }
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public PVector[] getDirections() {
        return directions;
    }

}
