import java.util.ArrayList;

public class Outside extends Piece{

    private Coords position = new Coords();
    private PieceColor color = PieceColor.NOCOLOR;
    private ArrayList<Coords> movesList = null;
    private Piece movePiece = new None();
    private PieceType pieceType = PieceType.OUTSIDE;
    private boolean hasMoved = false;

    public Outside(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }


    public ArrayList<Coords> possibleMoves(){
        return this.movesList;
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
    public void Move(Coords newCoords){
        getBoard().setPiece(position.getX(),position.getY(), movePiece);
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
    }
    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }

    @Override
    public PieceType getPieceType(){
        return this.pieceType;
    }
}