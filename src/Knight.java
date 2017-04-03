import java.util.ArrayList;

public class Knight extends Piece {

    private Coords position = new Coords();
    private PieceColor color;
    private ArrayList<Coords> movesList;
    private Piece compPiece = new None();
    private PieceType pieceType = PieceType.KNIGHT;
    private boolean hasMoved = false;
    public Knight(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }

    //-1-2-
    //8---3
    //--k--
    //7---4
    //-6-5-


    @Override
    public  ArrayList<Coords> possibleMoves(){
        /**
         switch(color){
         case WHITE:
         Piece tempPiece = getBoard().getPiece(position.getX(), position.getY());
         if(getBoard().getPiece(position.getX()-2, position.getY()-1) == null)
         Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()-1);
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
         **/
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
        return PieceType.KNIGHT;
    }

    @Override
    public void Move(Coords newCoords){
        this.hasMoved = true;
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
    }
    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }


}