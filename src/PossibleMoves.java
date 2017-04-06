import java.util.ArrayList;

/**
 * Created by Christopher on 2017-04-04.
 */
public class PossibleMoves {
    private ArrayList<Coords> movesList = new ArrayList<Coords>();
    private final Board board;

    private Coords upLeft = new Coords(-1,-1);
    private Coords upRight = new Coords(-1,1);
    private Coords downRight = new Coords(1,1);
    private Coords downLeft = new Coords(1,-1);


    public PossibleMoves(Piece piece, Board board){
        this.board = board;
        PieceType piecetype = piece.getPieceType();
        switch(piecetype){
            case PAWN:
                PawnMoves(piece);
                break;
            case ROOK:
                RookMoves(piece);
                break;
            case BISHOP:
                BishopMoves(piece);
                break;
            case KNIGHT:
                KnightMoves(piece);
                break;
            case KING:
                KingMoves(piece);
                break;
            case QUEEN:
                QueenMoves(piece);
                break;
        }
    }

    public PossibleMoves(){
        this.board = new Board(10,10);
    }

    private void PawnMoves(Piece piece){
        PieceColor color = piece.getPieceColor();
        Coords position = piece.getPosition();
        switch(color) {
            case WHITE:
                for (int i = -1; i < 2; i++){
                    if(i==0){
                        if (this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceType() == PieceType.NONE && this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceType() != PieceType.OUTSIDE) {
                            this.movesList.add(new Coords(position.getX() +i, position.getY() - 1));
                        }
                    }
                    else if(i != 0){
                        if (this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceColor() == PieceColor.BLACK && this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceType() != PieceType.OUTSIDE) {
                            this.movesList.add(new Coords(position.getX() + i, position.getY() - 1));
                        }
                    }
                }

                if (!piece.hasMoved() && this.board.getPiece(position.getX(), position.getY() - 2).getPieceType() == PieceType.NONE && this.board.getPiece(position.getX(), position.getY() - 2).getPieceType() != PieceType.OUTSIDE) {
                    if (this.board.getPiece(position.getX(), position.getY() - 1).getPieceType() == PieceType.NONE) {
                        this.movesList.add(new Coords(position.getX(), position.getY() - 2));
                    }
                }
                break;

            case BLACK:
                for (int i = -1; i < 2; i++){
                    if(i==0){
                        if (this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceType() == PieceType.NONE && this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceType() != PieceType.OUTSIDE) {
                            this.movesList.add(new Coords(position.getX() +i, position.getY() + 1));
                        }
                    }
                    else if(i != 0){
                        if (this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceColor() == PieceColor.WHITE && this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceType() != PieceType.OUTSIDE) {
                            this.movesList.add(new Coords(position.getX() + i, position.getY() + 1));
                        }
                    }
                }

                if (!piece.hasMoved() && this.board.getPiece(position.getX(), position.getY() + 2).getPieceType() == PieceType.NONE && this.board.getPiece(position.getX(), position.getY() + 2).getPieceType() != PieceType.OUTSIDE) {
                    if (this.board.getPiece(position.getX(), position.getY() + 1).getPieceType() == PieceType.NONE) {
                        this.movesList.add(new Coords(position.getX(), position.getY() + 2));
                    }
                }
                break;
        }
    }

    private void RookMoves(Piece piece){
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

    private void BishopMoves(Piece piece){
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
                    else{
                        break; //maybe check piececolor.white
                    }
                }
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
                        break; //maybe check piececolor.white
                    }
                }
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
                    else{
                        break; //maybe check piececolor.white
                    }
                }
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
                        break; //maybe check piececolor.white
                    }
                }
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
                    else{
                        break; //maybe check piececolor.white
                    }
                }
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
                        break; //maybe check piececolor.white
                    }
                }
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
                    else{
                        break; //maybe check piececolor.white
                    }
                }
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
                        break; //maybe check piececolor.white
                    }
                }
        }
    }

    private void KnightMoves(Piece piece){
        PieceColor color = piece.getPieceColor();
        Coords position = piece.getPosition();

        switch(color) {
            case WHITE:
                    if(piece.getPosition().getY()>1){
                        if (board.getPiece(position.getX() - 1, position.getY() - 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() - 2).getPieceColor() == PieceColor.BLACK) {
                            movesList.add(new Coords(piece.getPosition().getX() - 1, piece.getPosition().getY() - 2));
                        }
                        if (board.getPiece(position.getX() + 1, position.getY() - 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() - 2).getPieceColor() == PieceColor.BLACK) {
                            movesList.add(new Coords(piece.getPosition().getX() + 1, piece.getPosition().getY() - 2));
                        }
                    }

                    if(piece.getPosition().getX()>1){
                        if (board.getPiece(position.getX() - 2, position.getY() - 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 2, position.getY() - 1).getPieceColor() == PieceColor.BLACK) {
                            movesList.add(new Coords(piece.getPosition().getX() - 2, piece.getPosition().getY() - 1));
                        }
                        if (board.getPiece(position.getX() - 2, position.getY() + 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 2, position.getY() + 1).getPieceColor() == PieceColor.BLACK) {
                            movesList.add(new Coords(piece.getPosition().getX() - 2, piece.getPosition().getY() + 1));
                        }
                    }
                    if(piece.getPosition().getX()<8){
                        if (board.getPiece(position.getX() + 2, position.getY() - 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 2, position.getY() - 1).getPieceColor() == PieceColor.BLACK) {
                            movesList.add(new Coords(piece.getPosition().getX() + 2, piece.getPosition().getY() - 1));
                        }
                        if (board.getPiece(position.getX() + 2, position.getY() + 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 2, position.getY() + 1).getPieceColor() == PieceColor.BLACK) {
                            movesList.add(new Coords(piece.getPosition().getX() + 2, piece.getPosition().getY() + 1));
                        }
                    }

                    if(piece.getPosition().getY()<8){
                        if (board.getPiece(position.getX() - 1, position.getY() + 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() + 2).getPieceColor() == PieceColor.BLACK) {
                            movesList.add(new Coords(piece.getPosition().getX() - 1, piece.getPosition().getY() + 2));
                        }
                        if (board.getPiece(position.getX() + 1, position.getY() + 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() + 2).getPieceColor() == PieceColor.BLACK) {
                            movesList.add(new Coords(piece.getPosition().getX() + 1, piece.getPosition().getY() + 2));
                        }
                    }
                break;

            case BLACK:
                if(piece.getPosition().getY()>1){
                    if (board.getPiece(position.getX() - 1, position.getY() - 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() - 2).getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(piece.getPosition().getX() - 1, piece.getPosition().getY() - 2));
                    }
                    if (board.getPiece(position.getX() + 1, position.getY() - 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() - 2).getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(piece.getPosition().getX() + 1, piece.getPosition().getY() - 2));
                    }
                }

                if(piece.getPosition().getX()>1){
                    if (board.getPiece(position.getX() - 2, position.getY() - 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 2, position.getY() - 1).getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(piece.getPosition().getX() - 2, piece.getPosition().getY() - 1));
                    }
                    if (board.getPiece(position.getX() - 2, position.getY() + 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 2, position.getY() + 1).getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(piece.getPosition().getX() - 2, piece.getPosition().getY() + 1));
                    }
                }
                if(piece.getPosition().getX()<8){
                    if (board.getPiece(position.getX() + 2, position.getY() - 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 2, position.getY() - 1).getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(piece.getPosition().getX() + 2, piece.getPosition().getY() - 1));
                    }
                    if (board.getPiece(position.getX() + 2, position.getY() + 1).getPieceType() == PieceType.NONE || board.getPiece(position.getX() + 2, position.getY() + 1).getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(piece.getPosition().getX() + 2, piece.getPosition().getY() + 1));
                    }
                }

                if(piece.getPosition().getY()<8){
                    if (board.getPiece(position.getX() - 1, position.getY() + 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() + 2).getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(piece.getPosition().getX() - 1, piece.getPosition().getY() + 2));
                    }
                    if (board.getPiece(position.getX() + 1, position.getY() + 2).getPieceType() == PieceType.NONE || board.getPiece(position.getX() - 1, position.getY() + 2).getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(piece.getPosition().getX() + 1, piece.getPosition().getY() + 2));
                    }
                }
                break;
        }
    }

    private void KingMoves(Piece piece){
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
                            if(board.getPiece(position.getX()+i,position.getY()+j).getPieceType() != PieceType.OUTSIDE && board.getPiece(position.getX()+i,position.getY()+j).getPieceColor() != PieceColor.WHITE){
                                movesList.add(new Coords(position.getX()+i, position.getY()+j));
                            }
                        }
                    }
                }
                break;

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
            //removeCheckPosFromList();
        }
    }

    private void QueenMoves(Piece piece){
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
                    else{
                        break; //maybe check piececolor.white
                    }
                }
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
                        break; //maybe check piececolor.white
                    }
                }
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
                    else{
                        break; //maybe check piececolor.white
                    }
                }
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
                        break; //maybe check piececolor.white
                    }
                }
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
                    else{
                        break; //maybe check piececolor.white
                    }
                }
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
                        break; //maybe check piececolor.white
                    }
                }
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
                    else{
                        break; //maybe check piececolor.white
                    }
                }
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
                        break; //maybe check piececolor.white
                    }
                }
        }
    }


    public int size(){
        return movesList.size();
    }

    public Coords get(int index){
        return movesList.get(index);
    }


    public ArrayList<Coords> getList(){
        return movesList;
    }

    public boolean contains(Coords position) {
        if(movesList.contains(position)){
            return true;
        }
        else{
            return false;
        }
    }

}
