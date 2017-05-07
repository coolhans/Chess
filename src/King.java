public class King extends Piece {

    private Coords position = new Coords();
    private PieceColor color;
    private boolean hasMoved = false;

    public King(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
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
        return PieceType.KING;
    }

    @Override
    public void move(Coords coords){
        this.hasMoved = true;
        this.position.setX(coords.getX());
        this.position.setY(coords.getY());
    }
    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }
}
