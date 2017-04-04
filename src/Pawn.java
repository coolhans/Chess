import java.util.ArrayList;

public class Pawn extends Piece{
    private Board board = super.getBoard();
    private Coords position = new Coords();
    private PieceColor color;
    private ArrayList<Coords> movesList = new ArrayList<Coords>();
    private PieceType pieceType = PieceType.PAWN;
    private boolean hasMoved = false;
    public Pawn(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }

    @Override
    public ArrayList<Coords> possibleMoves(){
        switch(this.color) {
            case WHITE:
                for(int i=-1; i<2; i++)
                    if(this.board.getPiece(position.getX()+i,position.getY()-1).getPieceType() != pieceType.NONE && this.board.getPiece(position.getX()+i,position.getY()-1).getPieceColor() != PieceColor.WHITE && this.board.getPiece(position.getX()+i,position.getY()-1).getPieceType() != pieceType.OUTSIDE){
                        this.movesList.add(new Coords(position.getX()+i, position.getY()-1));
                    }

                if(!hasMoved() && this.board.getPiece(position.getX(),position.getY()-2).getPieceColor() != PieceColor.WHITE && this.board.getPiece(position.getX(),position.getY()-2).getPieceType() != PieceType.OUTSIDE){
                    if(this.board.getPiece(position.getX(), position.getY() - 1).getPieceType() == PieceType.NONE) {
                        this.movesList.add(new Coords(position.getX(), position.getY() - 2));
                    }
                }


            case BLACK:
                for(int i=-1; i<2; i++)
                    if(this.board.getPiece(position.getX()+i,position.getY()+1).getPieceType() != pieceType.NONE && this.board.getPiece(position.getX()+i,position.getY()+1).getPieceColor() != PieceColor.BLACK){
                        this.movesList.add(new Coords(position.getX()+i, position.getY()+1));
                    }

                if(!hasMoved() && this.board.getPiece(position.getX(),position.getY()+2).getPieceColor() != PieceColor.BLACK && this.board.getPiece(position.getX(),position.getY()+2).getPieceType() != PieceType.OUTSIDE){
                    if(this.board.getPiece(position.getX(), position.getY() + 1).getPieceType() == PieceType.NONE){
                        this.movesList.add(new Coords(position.getX(), position.getY() + 2));
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
        return PieceType.PAWN;
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