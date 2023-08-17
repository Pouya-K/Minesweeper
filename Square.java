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
    public boolean isFlag(){
        return flag;
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
            if(y != 0 && grid[x-1][y-1].isMine()) numOfMines++;
            if(grid[x-1][y].isMine()) numOfMines++;
            if(y != grid[0].length-1 && grid[x-1][y+1].isMine()) numOfMines++;
        }
        if(x != grid.length-1){
            if(y != 0 && grid[x+1][y-1].isMine()) numOfMines++;
            if(grid[x+1][y].isMine()) numOfMines++;
            if(y != grid[0].length-1 && grid[x+1][y+1].isMine()) numOfMines++;
        }
        if(y != 0 && grid[x][y-1].isMine()) numOfMines++;
        if(y != grid[0].length-1 && grid[x][y+1].isMine()) numOfMines++;
        grid[x][y].numOfCloseMines = numOfMines;
        Level.checkedSquares.add(grid[x][y]);
        if(numOfMines == 0){
            if(x != 0){
                if(y != 0 && !Level.checkedSquares.contains(grid[x-1][y-1])) calculateMines(grid, x-1, y-1);
                if(!Level.checkedSquares.contains(grid[x-1][y])) calculateMines(grid, x-1, y);
                if(y != grid[0].length-1 && !Level.checkedSquares.contains(grid[x-1][y+1])) calculateMines(grid, x-1, y+1);
            }
            if(x != grid.length-1){
                if(y != 0 && !Level.checkedSquares.contains(grid[x+1][y-1])) calculateMines(grid, x+1, y-1);
                if(!Level.checkedSquares.contains(grid[x+1][y])) calculateMines(grid, x+1, y);
                if(y != grid[0].length-1 && !Level.checkedSquares.contains(grid[x+1][y+1])) calculateMines(grid, x+1, y+1);
            }
            if(y != 0){
                if(!Level.checkedSquares.contains(grid[x][y-1])) calculateMines(grid, x, y - 1);
            }
            if(y != grid[0].length-1){
                if(!Level.checkedSquares.contains(grid[x][y+1])) calculateMines(grid, x, y + 1);
            }
        }
    }
    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }
}
