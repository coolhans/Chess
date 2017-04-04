import java.util.ArrayList;

public class Rook extends Piece {
    private Coords position = new Coords();
    private PieceColor color;
    private ArrayList<Coords> movesList = new ArrayList<Coords>();
    private Piece compPiece = new None();
    private PieceType pieceType = PieceType.ROOK;
    private boolean hasMoved = false;
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
                    if(tempPiece.getPieceType() == pieceType.NONE){
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
                    if(tempPiece.getPieceType() == pieceType.NONE){
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
                    if(tempPiece.getPieceType() == pieceType.NONE){
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
                    if(tempPiece.getPieceType() == pieceType.NONE){
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
                    if(tempPiece.getPieceType() == pieceType.NONE){
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
                    if(tempPiece.getPieceType() == pieceType.NONE){
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
                    if(tempPiece.getPieceType() == pieceType.NONE){
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
                    if(tempPiece.getPieceType() == pieceType.NONE){
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
    public Coords getPosition(){
        return this.position;
    }


    @Override
    public boolean hasMoved(){
        return this.hasMoved;
    }

    @Override
    public PieceType getPieceType(){
        return this.pieceType;
    }

    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }

    @Override
    public void Move(Coords newCoords){
        this.hasMoved = true;
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
    }
}