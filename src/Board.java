import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;

public class Board {
    public int width;
    public int height;
    public Piece[][] board;
    private List<BoardListener> listener = new ArrayList<BoardListener>();

    private static Random random = new Random();

    public int getWidth() {  //get width of the board
        return width;
    }

    public int getHeight() { //get height of the board
        return height;
    }

    public Board(int height, int width) {  //the board, initiate a board
        this.width = width;
        this.height = height;
        board = new Piece[height][width];

        for ( int row = 0; row<height; row++){//makes all squares as OUTSIDE
            for (int column = 0; column<width; column++){
                board[column][row]= Piece.OUTSIDE;
            }
        }
        for ( int row = 1; row<height-1; row++){//makes inner squares as EMPTY
        for (int column = 1; column<width-1; column++){
                board[column][row]= Piece.EMPTY;
            }
        }
        board[1][1] = Piece.BROOK;
        board[1][2] = Piece.BKNIGHT;
        board[1][3] = Piece.BBISHOP;
        board[1][4] = Piece.BQUEEN;
        board[1][5] = Piece.BKING;
        board[1][6] = Piece.BBISHOP;
        board[1][7] = Piece.BKNIGHT;
        board[1][8] = Piece.BROOK;
        for (int col = 1;col<width-1;col++){
            board[2][col] = Piece.BPAWN;
        }

        board[8][1] = Piece.WROOK;
        board[8][2] = Piece.WKNIGHT;
        board[8][3] = Piece.WBISHOP;
        board[8][4] = Piece.WQUEEN;
        board[8][5] = Piece.WKING;
        board[8][6] = Piece.WBISHOP;
        board[8][7] = Piece.WKNIGHT;
        board[8][8] = Piece.WROOK;
        for (int col = 1;col<width-1;col++){
            board[7][col] = Piece.WPAWN;
        }
    }

    public Piece getPiece(int x, int y){
        return board[y][x];
    }



    private void notifyListeners(){ //function when to make changes
        for (BoardListener boardListener: listener){
            boardListener.boardChanged();
        }
    }

    public void addBoardListener(BoardListener bl) { //to add listeners
        listener.add(bl);

    }


    public void tick(Board myboard){ //function of every tick (timer)
        /*if(){
        }
        else{//
            this.blockY--;
            //spiderX++;
            //checkCollision();
            notifyListeners();
            //do what
        }
        */

}
    final Action moveRight = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
                notifyListeners();
            }
    };

}
