public class Square {
    private boolean isVisible = false;
    private boolean mine = false;
    private boolean flag = false;
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
    public void turnFlag(){
        flag = !flag;
    }
    public int getNumOfCloseMines() {
        return numOfCloseMines;
    }
    public void setNumOfCloseMines(int n){
        numOfCloseMines = n;
    }
    public void calculateMines(Square[][] grid, int x, int y){
        int numOfMines = 0;
        grid[x][y].turnVisible();
        if(x != 0){
            for(int i = y-1; i<=y+1; i++){
                if(grid[x-1][i].isMine()) numOfMines++;
            }
        }
        if(x != grid.length-1){
            for(int i = y-1; i<=y+1; i++){
                if(grid[x+1][i].isMine()) numOfMines++;
            }
        }
        if(y != 0 && grid[x][y-1].isMine()) numOfMines++;
        if(y != grid[0].length-1 && grid[x][y+1].isMine()) numOfMines++;
        grid[x][y].numOfCloseMines = numOfMines;
        if(numOfMines == 0){
            if(x != 0){
                calculateMines(grid, x - 1, y - 1);
                calculateMines(grid, x - 1, y);
                calculateMines(grid, x - 1, y + 1);
            }
            if(x != grid.length-1){
                calculateMines(grid, x + 1, y - 1);
                calculateMines(grid, x + 1, y);
                calculateMines(grid, x + 1, y + 1);
            }
            if(y != 0) calculateMines(grid, x, y - 1);
            if(y != grid[0].length-1) calculateMines(grid, x, y + 1);
        }
    }
    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }
}
