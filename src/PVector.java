public class PVector {

    private double x;
    private double y;

    PVector(double x, double y){
        this.x = x;
        this.y = y;
    }

    void add(PVector b){
        this.x += b.getX();
        this.y += b.getY();
    }

    double getX(){
        return this.x;
    }

    double getY(){
        return this.y;
    }

    double getNorm(){
        return Math.sqrt(Math.pow(this.x,2)+Math.pow(this.y,2));
    }

    void limit(double max){
        if (this.getNorm() > max) {
            this.x = this.x / this.getNorm() * max;
            this.y = this.y / this.getNorm() * max;
        }
    }

    double dist(PVector b){
        return Math.sqrt(Math.pow(this.x-b.getX(),2)+Math.pow(this.y-b.getY(),2));
    }

    //creates the 1-sized vector for the angle angle
    static PVector fromAngle(double angle){
        return new PVector(Math.cos(angle), Math.sin(angle));
    }

}
