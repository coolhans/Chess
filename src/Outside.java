import java.util.ArrayList;

public class Outside extends Piece{

    private Coords position = new Coords();
    private PieceColor color = PieceColor.NOCOLOR;
    private ArrayList<Coords> movesList = null;
    private Piece movePiece = new None();

    public Outside(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }


    public ArrayList<Coords> possibleMoves(){
        return this.movesList;
    }

    @Override
    public void Move(Coords newCoords){
        getBoard().setPiece(position.getX(),position.getY(), movePiece);
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
    }
    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }


}