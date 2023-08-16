public class Square {
    private boolean isVisible = false;
    private boolean mine = false;
    private int numOfCloseMines = 0;
    private final int sideLength = 30;
    public int x, y;
    public boolean getVisibility(){
        return isVisible;
    }
    public boolean isMine(){
        return mine;
    }
    public void turnVisible(){
        isVisible = true;
    }
    public void turnMine(){
        mine = true;
    }
    public void calculateMines(Square[][] grid, int x, int y){

    }
    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }
}
