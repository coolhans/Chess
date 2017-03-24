public class Coords {
    private int x = 0;
    private int y = 0;


    public Coords(int x, int y){
        this.x = x;
        this.y = y;
    }


    public Coords(){
    }

    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }
}
