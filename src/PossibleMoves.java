import java.util.ArrayList;

/**
 * Created by Christopher on 2017-04-04.
 */
public class PossibleMoves {
    private ArrayList<Coords> movesList = new ArrayList<Coords>();
    private final Board board;

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
                for (int i = -1; i < 2; i++)
                    if (this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceType() != PieceType.NONE && this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceColor() != PieceColor.WHITE && this.board.getPiece(position.getX() + i, position.getY() - 1).getPieceType() != PieceType.OUTSIDE) {
                        this.movesList.add(new Coords(position.getX() + i, position.getY() - 1));
                    }

                if (!piece.hasMoved() && this.board.getPiece(position.getX(), position.getY() - 2).getPieceColor() != PieceColor.WHITE && this.board.getPiece(position.getX(), position.getY() - 2).getPieceType() != PieceType.OUTSIDE) {
                    if (this.board.getPiece(position.getX(), position.getY() - 1).getPieceType() == PieceType.NONE) {
                        this.movesList.add(new Coords(position.getX(), position.getY() - 2));
                    }
                }


            case BLACK:
                for (int i = -1; i < 2; i++)
                    if (this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceType() != PieceType.NONE && this.board.getPiece(position.getX() + i, position.getY() + 1).getPieceColor() != PieceColor.BLACK) {
                        this.movesList.add(new Coords(position.getX() + i, position.getY() + 1));
                    }

                if (!piece.hasMoved() && this.board.getPiece(position.getX(), position.getY() + 2).getPieceColor() != PieceColor.BLACK && this.board.getPiece(position.getX(), position.getY() + 2).getPieceType() != PieceType.OUTSIDE) {
                    if (this.board.getPiece(position.getX(), position.getY() + 1).getPieceType() == PieceType.NONE) {
                        this.movesList.add(new Coords(position.getX(), position.getY() + 2));
                    }
                }
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

}
