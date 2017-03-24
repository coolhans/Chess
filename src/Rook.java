import java.util.ArrayList;

public class Rook extends Piece {

    private Coords position = new Coords();
    private PieceColor color;
    private ArrayList<Coords> movesList;
    private Piece compPiece = new None();
    private PieceType pieceType = PieceType.ROOK;

    public Rook(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }

    @Override
    public ArrayList<Coords> possibleMoves(){
        //up
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()-i);
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
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
                    Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()-i);
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
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
                    Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()+i);
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
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
                    Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()+i);
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
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
                    Piece tempPiece = getBoard().getPiece(position.getX()-i, position.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
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
                    Piece tempPiece = getBoard().getPiece(position.getX()-i, position.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
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
                    Piece tempPiece = getBoard().getPiece(position.getX()+i, position.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
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
                    Piece tempPiece = getBoard().getPiece(position.getX()+i, position.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
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


        return movesList;
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.ROOK;
    }

    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }

    @Override
    public void Move(Coords newCoords){
        getBoard().setPiece(position.getX(),position.getY(), compPiece);
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
    }
}