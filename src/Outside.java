public class Outside extends Piece{

    private Coords position = new Coords();
    private PieceColor color = PieceColor.NOCOLOR;
    private PieceType pieceType = PieceType.OUTSIDE;

    public Outside(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }

    @Override
    public Coords getPosition(){
        return this.position;
    }

    @Override
    public boolean hasMoved(){
	final boolean hasMoved = false;
	return hasMoved;
    }

    @Override
    public void move(Coords coords){
        this.position.setX(coords.getX());
        this.position.setY(coords.getY());
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