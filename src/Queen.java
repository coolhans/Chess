public class Queen extends Piece {

    private Coords position = new Coords();
    private PieceColor color;
    private boolean hasMoved = false;
    private PieceType pieceType = PieceType.QUEEN;

    //Constructor
    public Queen(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }

    //Overriding the methods declared in the super abstract class
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
    public void move(Coords coords){
        this.hasMoved = true;
        this.position.setX(coords.getX());
        this.position.setY(coords.getY());
    }
    @Override
    public PieceType getPieceType(){
        return this.pieceType;
    }
}