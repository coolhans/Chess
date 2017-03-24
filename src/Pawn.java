import java.util.ArrayList;

public class Pawn extends Piece{

    private Coords position = new Coords();
    private PieceColor color;
    private ArrayList<Coords> movesList;
    private Piece compPiece = new None();
    private PieceType pieceType = PieceType.PAWN;
    public Pawn(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }

    @Override
    public ArrayList<Coords> possibleMoves(){
        switch(color) {
            case WHITE:
                for(int i=-1; i<2; i++)
                    if(getBoard().getPiece(position.getX()+i,position.getY()-1) != null && getBoard().getPiece(position.getX()+i,position.getY()-1).getPieceColor() != PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i, position.getY()-1));
                    }

                if(!hasMoved() && getBoard().getPiece(position.getX(),position.getY()-1) == compPiece){
                    if(getBoard().getPiece(position.getX(), position.getY() - 2) == compPiece){
                        movesList.add(new Coords(position.getX(), position.getY() - 2));
                    }
                }


            case BLACK:
                for(int i=-1; i<2; i++)
                    if(getBoard().getPiece(position.getX()+i,position.getY()+1) != null && getBoard().getPiece(position.getX()+i,position.getY()-1).getPieceColor() != PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i, position.getY()+1));
                    }

                if(!hasMoved() &&  getBoard().getPiece(position.getX(),position.getY()+1) == compPiece){
                    if(getBoard().getPiece(position.getX(), position.getY() + 2) == compPiece){
                        movesList.add(new Coords(position.getX(), position.getY() + 2));
                    }
                }

        }
        return movesList;
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.PAWN;
    }

    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }

    @Override
    public void Move(Coords newCoords){
        getBoard().setPiece(position.getX(),position.getY(), new None());
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
    }


}