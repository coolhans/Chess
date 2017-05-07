import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 2017-04-04.
 */
public class PossibleMoves {
    private List<Coords> movesList = new ArrayList<Coords>();
    private final Board board;

    //vectors to iterate with a scalar
    private Coords upLeft = new Coords(-1,-1);
    private Coords upRight = new Coords(1,-1);
    private Coords downRight = new Coords(1,1);
    private Coords downLeft = new Coords(-1,1);

    //Constructor function that locates the correct algorithm for corresponding piece
    public PossibleMoves(Piece piece, Board board){
        this.board = board;
        PieceType piecetype = piece.getPieceType();
        switch(piecetype){
            case PAWN:
                pawnMoves(piece);
                break;
            case ROOK:
                rookMoves(piece);
                break;
            case BISHOP:
                bishopMoves(piece);
                break;
            case KNIGHT:
                knightMoves(piece);
                break;
            case KING:
                kingMoves(piece);
                break;
            case QUEEN:
                queenMoves(piece);
                break;
        }
    }

    //A constructor to be able to instanciate an possiblemoves object for later use
    public PossibleMoves(){
        this.board = new Board(10,10);
    }

    //Pawn moves is probobly the most complex algorithm seeing how it changes dynamically depending of where it is
    //and what enemies are near.
    private void pawnMoves(Piece piece){
        PieceColor color = piece.getPieceColor();
        Coords position = piece.getPosition();
        switch(color) {
            case WHITE:
                //Pawns can only kill an enemy piece if they are diagonally 1 step ahead of them so we iterate the numbers -1,0,1 and look at them seperatly
                //when i is -1 or 1 it can kill the piece, if i is 0 it can only move if it is an empty spot
                for (int i = -1; i < 2; i++){
                    if(i==0){
                        if (this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceType() == PieceType.NONE && this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceType() != PieceType.OUTSIDE) {
                            this.movesList.add(new Coords(position.getX() +i, position.getY() - 1));
                        }
                    }
                    else {
                        if (this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceColor() == PieceColor.BLACK && this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceType() != PieceType.OUTSIDE) {
                            this.movesList.add(new Coords(position.getX() + i, position.getY() - 1));
                        }
                    }
                }

                //The first move a pawn makes it has the option to jump two spots, we introduced a boolean variable to solve that issue
                if (!piece.hasMoved() && this.board.getPiece(position.getX(), position.getY() - 2).getPieceType() == PieceType.NONE) {
                    if (this.board.getPiece(position.getX(), position.getY() - 1).getPieceType() == PieceType.NONE) {
                        this.movesList.add(new Coords(position.getX(), position.getY() - 2));
                    }
                }
                break;

            //Same but for black
            case BLACK:
                for (int i = -1; i < 2; i++){
                    if(i==0){
                        if (this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceType() == PieceType.NONE && this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceType() != PieceType.OUTSIDE) {
                            this.movesList.add(new Coords(position.getX() +i, position.getY() + 1));
                        }
                    }
                    else {
                        if (this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceColor() == PieceColor.WHITE && this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceType() != PieceType.OUTSIDE) {
                            this.movesList.add(new Coords(position.getX() + i, position.getY() + 1));
                        }
                    }
                }

                if (!piece.hasMoved() && this.board.getPiece(position.getX(), position.getY() + 2).getPieceType() == PieceType.NONE) {
                    if (this.board.getPiece(position.getX(), position.getY() + 1).getPieceType() == PieceType.NONE) {
                        this.movesList.add(new Coords(position.getX(), position.getY() + 2));
                    }
                }
                break;
        }
    }

    //As for rook, bishop and queen, we used the same logic. We iterate through the board in the corresponding directions
    //and add the coordinations to the possiblemoves list if they either are empty or an enemy in which case we also stop iterating
    private void rookMoves(Piece piece){
        PieceColor color = piece.getPieceColor();
        Coords position = piece.getPosition();
        switch(color){
            //up
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX(), position.getY()-i);
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX(), position.getY()-i);
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }
        //down
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX(), position.getY()+i);
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX(), position.getY()+i);
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //left
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()-i, position.getY());
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()-i, position.getY());

                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //right
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i, position.getY());
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i, position.getY());
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }
    }

    private void bishopMoves(Piece piece){
        PieceColor color = piece.getPieceColor();
        Coords position = piece.getPosition();

        //Check available moves diagonal up right
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*upRight.getX(), position.getY()+i*upRight.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                break;
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*upRight.getX(), position.getY()+i*upRight.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }
        //Check available moves diagonal down right
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*downRight.getX(), position.getY()+i*downRight.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                break;
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*downRight.getX(), position.getY()+i*downRight.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //Check available moves diagonal down left
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*downLeft.getX(), position.getY()+i*downLeft.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                break;
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*downLeft.getX(), position.getY()+i*downLeft.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //Check available moves diagonal up left
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*upLeft.getX(), position.getY()+i*upLeft.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                break;
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*upLeft.getX(), position.getY()+i*upLeft.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }
    }

    //Knight moves were a bit tricky to implement at first, but we figured it was best to hard code its moves as there are always
    //a maximum of 8 moves. Depending of where the knight is standing its logic has to be adjusted
    private void knightMoves(Piece piece){
        PieceColor color = piece.getPieceColor();
        Coords position = piece.getPosition();

        switch(color) {
            case WHITE:
                //If the knights position has at least 2 spots above it, it can look and see if at the two spots where Y-coordinates are -2
                //this applies for all 8, so there are 4 cases where Y is greater than 2, greater than 1, less than 7 or less than 8
                //and for each of those there are two spots it can look at.
                    if(piece.getPosition().getY()>2){
                        if(piece.getPosition().getX()>1){
                            if (board.getPiece(position.getX() - 1, position.getY() - 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() - 2).getPieceColor() == PieceColor.BLACK) {
                                movesList.add(new Coords(piece.getPosition().getX() - 1, piece.getPosition().getY() - 2));
                            }
                        }

                        if(piece.getPosition().getX()<8){
                            if (board.getPiece(position.getX() + 1, position.getY() - 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() - 2).getPieceColor() == PieceColor.BLACK) {
                                movesList.add(new Coords(piece.getPosition().getX() + 1, piece.getPosition().getY() - 2));
                            }
                        }
                    }
                    if(piece.getPosition().getY()>1){
                        if(piece.getPosition().getX()>2){
                            if (board.getPiece(position.getX() - 2, position.getY() - 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 2, position.getY() - 1).getPieceColor() == PieceColor.BLACK) {
                                movesList.add(new Coords(piece.getPosition().getX() - 2, piece.getPosition().getY() - 1));
                            }
                        }
                        if(piece.getPosition().getX()<7){
                            if (board.getPiece(position.getX() + 2, position.getY() - 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 2, position.getY() - 1).getPieceColor() == PieceColor.BLACK) {
                                movesList.add(new Coords(piece.getPosition().getX() + 2, piece.getPosition().getY() - 1));
                            }
                        }
                    }
                    if(piece.getPosition().getY()<7) {
                        if (piece.getPosition().getX() > 1) {
                            if (board.getPiece(position.getX() - 1, position.getY() + 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() + 2).getPieceColor() == PieceColor.BLACK) {
                                movesList.add(new Coords(piece.getPosition().getX() - 1, piece.getPosition().getY() + 2));
                            }
                        }
                        if (piece.getPosition().getX() < 8) {
                            if (board.getPiece(position.getX() + 1, position.getY() + 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 1, position.getY() + 2).getPieceColor() == PieceColor.BLACK) {
                                movesList.add(new Coords(piece.getPosition().getX() + 1, piece.getPosition().getY() + 2));
                            }
                        }
                    }
                    if(piece.getPosition().getY() < 8){
                        if(piece.getPosition().getX()>2){
                            if (board.getPiece(position.getX() - 2, position.getY() + 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 2, position.getY() + 1).getPieceColor() == PieceColor.BLACK) {
                                movesList.add(new Coords(piece.getPosition().getX() - 2, piece.getPosition().getY() + 1));
                            }
                        }

                        if(piece.getPosition().getX()<7){
                            if (board.getPiece(position.getX() + 2, position.getY() + 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 2, position.getY() + 1).getPieceColor() == PieceColor.BLACK) {
                                movesList.add(new Coords(piece.getPosition().getX() + 2, piece.getPosition().getY() + 1));
                            }
                        }
                    }
                break;

            case BLACK:
                if(piece.getPosition().getY()>2){
                    if(piece.getPosition().getX()>1){
                        if (board.getPiece(position.getX() - 1, position.getY() - 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() - 2).getPieceColor() == PieceColor.WHITE) {
                            movesList.add(new Coords(piece.getPosition().getX() - 1, piece.getPosition().getY() - 2));
                        }
                    }

                    if(piece.getPosition().getX()<8){
                        if (board.getPiece(position.getX() + 1, position.getY() - 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() - 2).getPieceColor() == PieceColor.WHITE) {
                            movesList.add(new Coords(piece.getPosition().getX() + 1, piece.getPosition().getY() - 2));
                        }
                    }
                }
                if(piece.getPosition().getY()>1){
                    if(piece.getPosition().getX()>2){
                        if (board.getPiece(position.getX() - 2, position.getY() - 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 2, position.getY() - 1).getPieceColor() == PieceColor.WHITE) {
                            movesList.add(new Coords(piece.getPosition().getX() - 2, piece.getPosition().getY() - 1));
                        }
                    }
                    if(piece.getPosition().getX()<7){
                        if (board.getPiece(position.getX() + 2, position.getY() - 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 2, position.getY() - 1).getPieceColor() == PieceColor.WHITE) {
                            movesList.add(new Coords(piece.getPosition().getX() + 2, piece.getPosition().getY() - 1));
                        }
                    }
                }
                if(piece.getPosition().getY()<7) {
                    if (piece.getPosition().getX() > 1) {
                        if (board.getPiece(position.getX() - 1, position.getY() + 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() + 2).getPieceColor() == PieceColor.WHITE) {
                            movesList.add(new Coords(piece.getPosition().getX() - 1, piece.getPosition().getY() + 2));
                        }
                    }
                    if (piece.getPosition().getX() < 8) {
                        if (board.getPiece(position.getX() + 1, position.getY() + 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 1, position.getY() + 2).getPieceColor() == PieceColor.WHITE) {
                            movesList.add(new Coords(piece.getPosition().getX() + 1, piece.getPosition().getY() + 2));
                        }
                    }
                }
                if(piece.getPosition().getY() < 8){
                    if(piece.getPosition().getX()>2){
                        if (board.getPiece(position.getX() - 2, position.getY() + 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 2, position.getY() + 1).getPieceColor() == PieceColor.WHITE) {
                            movesList.add(new Coords(piece.getPosition().getX() - 2, piece.getPosition().getY() + 1));
                        }
                    }

                    if(piece.getPosition().getX()<7){
                        if (board.getPiece(position.getX() + 2, position.getY() + 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 2, position.getY() + 1).getPieceColor() == PieceColor.WHITE) {
                            movesList.add(new Coords(piece.getPosition().getX() + 2, piece.getPosition().getY() + 1));
                        }
                    }
                }
        }
    }

    //King moves is quite simple, just 1 step around him whereever he is
    private void kingMoves(Piece piece){
        PieceColor color = piece.getPieceColor();
        Coords position = piece.getPosition();
        switch(color) {
            case WHITE:
                for(int i=-1; i<2; i++){
                    for(int j=-1; j<2; j++){
                        if(i==0 && j==0){
                            continue;
                        }
                        else {
                            //If spot isnt outside and not same piece color, then add to list
                            if(board.getPiece(position.getX()+i,position.getY()+j).getPieceType() != PieceType.OUTSIDE && board.getPiece(position.getX()+i,position.getY()+j).getPieceColor() != PieceColor.WHITE){
                                movesList.add(new Coords(position.getX()+i, position.getY()+j));
                            }
                        }
                    }
                }
                break;

            //Same but for black
            case BLACK:
                for(int i=-1; i<2; i++){
                    for(int j=-1; j<2; j++){
                        if(i==0 && j==0){
                            continue;
                        }
                        else{
                            if(board.getPiece(position.getX()+i,position.getY()+j).getPieceType() != PieceType.OUTSIDE && board.getPiece(position.getX()+i,position.getY()+j).getPieceColor() != PieceColor.BLACK){
                                movesList.add(new Coords(position.getX()+i, position.getY()+j));
                            }
                        }
                    }
                }
                break;
        }
    }

    private void queenMoves(Piece piece){
        PieceColor color = piece.getPieceColor();
        Coords position = piece.getPosition();

        //ROOK MOVES
        //up
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX(), position.getY()-i);
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX(), position.getY()-i);
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }
        //down
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX(), position.getY()+i);
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX(), position.getY()+i);
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //left
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()-i, position.getY());
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()-i, position.getY());

                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //right
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i, position.getY());
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i, position.getY());
                    if(tempPiece.getPieceType() == PieceType.NONE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //BISHOP MOVES
        //Check available moves diagonal up right
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*upRight.getX(), position.getY()+i*upRight.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                break;
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*upRight.getX(), position.getY()+i*upRight.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }
        //Check available moves diagonal down right
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*downRight.getX(), position.getY()+i*downRight.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                break;
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*downRight.getX(), position.getY()+i*downRight.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //Check available moves diagonal down left
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*downLeft.getX(), position.getY()+i*downLeft.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                break;
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*downLeft.getX(), position.getY()+i*downLeft.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }

        //Check available moves diagonal up left
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*upLeft.getX(), position.getY()+i*upLeft.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                        break;
                    }
                    else {
                        break;
                    }
                }
                break;
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = board.getPiece(position.getX()+i*upLeft.getX(), position.getY()+i*upLeft.getY());
                    if(tempPiece.getPieceType() == PieceType.OUTSIDE){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
                break;
        }
    }

    //Here are some methods to help form the Possible moves object, most are extended array methods
    public int size(){
        return movesList.size();
    }

    public Coords get(int index){
        return movesList.get(index);
    }

    public List<Coords> getList(){
        return movesList;
    }

    //This is only used to reduce king size
    public void remove(int index){
        this.movesList.remove(index);

        //To avoid arraysize issues, we complement with an unusable coordiante (-1,-1).
        this.movesList.add(new Coords(-1,-1));
    }

}
