public class Square {
    public boolean isVisible = false;
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
    public boolean equals(Square other){
        if(this.x == other.x && this.y == other.y) return true;
        return false;
    }
    public void calculateMines(Square[][] grid, int x, int y){
        int numOfMines = 0;
        if(!grid[x][y].isFlag()) grid[x][y].turnVisible();
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
    public void chord(Square[][] grid, int x, int y){
        boolean ableToChord = false;
        boolean notPossible = false;
        boolean lost = false;
        if(grid[x][y].isVisible){
            if(x != 0){
                if(y != 0 && grid[x-1][y-1].isMine()){
                    if(grid[x-1][y-1].isFlag()){
                        ableToChord = true;
                    }
                    else{
                        notPossible = true;
                    }
                }
                else if(y != 0 && grid[x-1][y-1].isFlag() && grid[x-1][y-1].isMine()){
                    Level.lostSquare = grid[x-1][y-1];
                    lost = true;
                }

                if(grid[x-1][y].isMine()){
                    if(grid[x-1][y].isFlag()){
                        ableToChord = true;
                    }
                    else{
                        notPossible = true;
                    }
                }
                else if(grid[x-1][y].isFlag() && grid[x-1][y].isMine()){
                    Level.lostSquare = grid[x-1][y];
                    lost = true;
                }

                if(y != grid[0].length-1 && grid[x-1][y+1].isMine()){
                    if(grid[x-1][y+1].isFlag()){
                        ableToChord = true;
                    }
                    else{
                        notPossible = true;
                    }
                }
                else if(y != grid[0].length-1 && grid[x-1][y+1].isFlag() && grid[x-1][y+1].isMine()){
                    Level.lostSquare = grid[x-1][y+1];
                    lost = true;
                }
            }
            if(x != grid.length-1){
                if(y != 0 && grid[x+1][y-1].isMine()){
                    if(grid[x+1][y-1].isFlag()){
                        ableToChord = true;
                    }
                    else{
                        notPossible = true;
                    }
                }
                else if(y != 0 && grid[x+1][y-1].isFlag() && grid[x+1][y-1].isMine()){
                    Level.lostSquare = grid[x+1][y-1];
                    lost = true;
                }

                if(grid[x+1][y].isMine()){
                    if(grid[x+1][y].isFlag()){
                        ableToChord = true;
                    }
                    else{
                        notPossible = true;
                    }
                }
                else if(grid[x+1][y].isFlag() && grid[x+1][y].isMine()){
                    Level.lostSquare = grid[x+1][y];
                    lost = true;
                }

                if(y != grid[0].length-1 && grid[x+1][y+1].isMine()){
                    if(grid[x+1][y+1].isFlag()){
                        ableToChord = true;
                    }
                    else{
                        notPossible = true;
                    }
                }
                else if(y != grid[0].length-1 && grid[x+1][y+1].isFlag() && grid[x+1][y+1].isMine()){
                    Level.lostSquare = grid[x+1][y+1];
                    lost = true;
                }
            }
            if(y != 0 && grid[x][y-1].isMine()){
                if(grid[x][y-1].isFlag()){
                    ableToChord = true;
                }
                else{
                    notPossible = true;
                }
            }
            else if(y != 0 && grid[x][y-1].isMine() && grid[x][y-1].isFlag()){
                Level.lostSquare = grid[x][y-1];
                lost = true;
            }

            if(y != grid[0].length-1 && grid[x][y+1].isMine()){
                if(grid[x][y+1].isFlag()){
                    ableToChord = true;
                }
                else{
                    notPossible = true;
                }
            }
            else if(y != grid[0].length-1 && grid[x][y+1].isFlag() && grid[x][y+1].isMine()){
                Level.lostSquare = grid[x][y+1];
                lost = true;
            }

            if(lost){
                System.out.println("lost in chord");
                Level.gameOver = true;
            }
            else if(ableToChord && !notPossible){
                if(x != 0){
                    if(y != 0 && !grid[x-1][y-1].isMine()){
                        calculateMines(grid, x-1, y-1);
                    }
                    if(!grid[x-1][y].isMine()){
                        calculateMines(grid, x-1, y);
                    }
                    if(y != grid[0].length-1 && !grid[x-1][y+1].isMine()){
                        calculateMines(grid, x-1, y+1);
                    }
                }
                if(x != grid.length-1){
                    if(y != 0 && !grid[x+1][y-1].isMine()){
                        calculateMines(grid, x+1, y-1);
                    }
                    if(!grid[x+1][y].isMine()){
                        calculateMines(grid, x+1, y);
                    }
                    if(y != grid[0].length-1 && !grid[x+1][y+1].isMine()){
                        calculateMines(grid, x+1, y+1);
                    }
                }
                if(y != 0 && !grid[x][y-1].isMine()){
                    calculateMines(grid, x, y-1);
                }
                if(y != grid[0].length-1 && !grid[x][y+1].isMine()){
                    calculateMines(grid, x, y+1);
                }
            }

        }
    }
    public Square(int x, int y){
        this.x = x;
        this.y = y;
    }
}
