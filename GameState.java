import java.util.ArrayList;

public class GameState {
    private boolean leftClick = false, rightClick = false;
    private int flagsLeft;
    public Square lostSquare;
    private int time = 0;
    private long timeTicker;
    private ArrayList<Square> checkedSquares = new ArrayList<Square>();
    private int screenWidth, screenHeight, numOfMines, gridWidth, gridHeight;
    private Square[][] grid;
    public boolean gameOver = false;
    private boolean gameWon = false;


    public boolean isLeftClick() {
        return leftClick;
    }

    public void setLeftClick(boolean leftClick) {
        this.leftClick = leftClick;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    public void setRightClick(boolean rightClick) {
        this.rightClick = rightClick;
    }

    public int getFlagsLeft() {
        return flagsLeft;
    }

    public void setFlagsLeft(int flagsLeft) {
        this.flagsLeft = flagsLeft;
    }

    public Square getLostSquare() {
        return lostSquare;
    }

    public void setLostSquare(Square lostSquare) {
        this.lostSquare = lostSquare;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public long getTimeTicker() {
        return timeTicker;
    }

    public void setTimeTicker(long timeTicker) {
        this.timeTicker = timeTicker;
    }

    public ArrayList<Square> getCheckedSquares() {
        return checkedSquares;
    }

    public void setCheckedSquares(ArrayList<Square> checkedSquares) {
        this.checkedSquares = checkedSquares;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public int getNumOfMines() {
        return numOfMines;
    }

    public void setNumOfMines(int numOfMines) {
        this.numOfMines = numOfMines;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public void setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }

    public void setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
    }

    public Square[][] getGrid() {
        return grid;
    }

    public void setGrid(Square[][] grid) {
        this.grid = grid;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public void incrementFlag(){
        this.flagsLeft++;
    }

    public void decrementFlag(){
        this.flagsLeft--;
    }

    public void incrementTime(){
        this.time++;
    }

    public void addCheckedSquare(Square square){
        checkedSquares.add(square);
    }

    public boolean checkedSquaresContains(Square square){
        return checkedSquares.contains(square);
    }
}
