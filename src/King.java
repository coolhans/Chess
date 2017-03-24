import java.util.ArrayList;

public class King extends Piece {

    private Coords position = new Coords();
    private PieceColor color;
    private ArrayList<Coords> movesList;
    private Coords tempCoords = new Coords();
    private Piece compPiece = new None();

    public King(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }
    @Override
    public ArrayList<Coords> possibleMoves(){

        switch(color) {
            case WHITE:
                for(int i=-1; i<2; i++){
                    for(int j=-1; j<2; j++){
                        if(getBoard().getPiece(position.getX()+i,position.getY()+j) != null && getBoard().getPiece(position.getX()+i,position.getY()+j).getPieceColor() != PieceColor.WHITE){
                            movesList.add(new Coords(position.getX()+i, position.getY()+j));
                        }
                    }
                }

            case BLACK:
                for(int i=-1; i<2; i++){
                    for(int j=-1; j<2; j++){
                        if(getBoard().getPiece(position.getX()+i,position.getY()+j) != null && getBoard().getPiece(position.getX()+i,position.getY()+j).getPieceColor() != PieceColor.BLACK){
                            movesList.add(new Coords(position.getX()+i, position.getY()+j));
                        }
                    }
                }
                //removeCheckPosFromList();
        }
        return movesList;
    }


    @Override
    public PieceType getPieceType(){
        return PieceType.KING;
    }

    @Override
    public void Move(Coords newCoords){
        getBoard().setPiece(position.getX(),position.getY(), compPiece);
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
    }
    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }

/**
 private void removeCheckPosFromList(){
 for(int spot=0; spot<this.movesList.size(); spot++){ //iterate through possible moves
 for(int i=1; i<9; i++){ //iterate through board and see if any piece will check king if he moves to that spot
 for(int j=1; j<9; j++){
 ArrayList<Coords> otherPiecesList = getBoard().getPiece(i,j).possibleMoves();
 for(int spot2=0; spot2<otherPiecesList.size(); spot2++){
 if(this.movesList[spot].getX() == otherPiecesList[spot2].getX() && this.movesList[spot].getY() == otherPiecesList[spot2].getY()){ //Lös Why not work
 movesList.remove(spot);
 }
 }
 }
 }
 }
 }
 **/


}
