//An abstract class to specify the properties of all the different pieces
public abstract class Piece {

    private boolean selected = false;

    public abstract void move(Coords coords);

    public boolean isSelected(){
        return this.selected;
    }

    public void selectPiece(){
        this.selected = !selected;
    }

    public abstract Coords getPosition();

    public abstract PieceColor getPieceColor();

    public abstract boolean hasMoved();

    public abstract PieceType getPieceType();

}

