public class None extends Piece{

    private Coords position = new Coords();
    private PieceColor color = PieceColor.NOCOLOR;
    private PieceType pieceType = PieceType.NONE;
    private boolean hasMoved = false;
    public None(Coords position){
        this.position = position;
    }

    public None(){
    }

    @Override
    public void move(Coords coords){
        this.hasMoved = true;
        this.position.setX(coords.getX());
        this.position.setY(coords.getY());
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