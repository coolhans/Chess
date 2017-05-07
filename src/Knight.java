public class Knight extends Piece {

    private Coords position = new Coords();
    private PieceColor color;
    private boolean hasMoved = false;
    public Knight(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }

    //Hard coding Knight moves according to below illustration and taking to account how where the Knight is positioned will limit its possible moves.
    //-1-2-
    //8---3
    //--k--
    //7---4
    //-6-5-


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
        return PieceType.KNIGHT;
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