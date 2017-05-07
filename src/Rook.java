public class Rook extends Piece {
    private Coords position = new Coords();
    private PieceColor color;
    private PieceType pieceType = PieceType.ROOK;
    private boolean hasMoved = false;
    public Rook(PieceColor color, Coords position){
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
        return this.pieceType;
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
}