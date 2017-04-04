import java.util.ArrayList;

public class None extends Piece{

    private Coords position = new Coords();
    private PieceColor color = PieceColor.NOCOLOR;
    private ArrayList<Coords> movesList = new ArrayList<Coords>();
    private PieceType pieceType = PieceType.NONE;
    private boolean hasMoved = false;
    public None(Coords position){
        this.position = position;
    }

    public None(){

    }

    public ArrayList<Coords> possibleMoves(){
        return this.movesList;
    }

    @Override
    public void Move(Coords newCoords){
        this.hasMoved = true;
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
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
    public PieceColor getPieceColor(){
        return this.color;
    }

    @Override
    public PieceType getPieceType(){
        return this.pieceType;
    }
}