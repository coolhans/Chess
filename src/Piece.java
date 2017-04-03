import java.util.*;

public abstract class Piece {
    private boolean moved = false;
    private boolean selected = false;
    private boolean available;

    private Board board;

    private Coords position;
    private PieceColor pieceColor;
    private PieceType pieceType;



    public abstract ArrayList<Coords> possibleMoves();

    public abstract void Move(Coords coords);

    public boolean isSelected(){
        return this.selected;
    }

    public void selectPiece(){
        this.selected = !selected;
    }

    public abstract Coords getPosition();

    public abstract PieceColor getPieceColor();

    public abstract boolean hasMoved();

    public Board getBoard(){
        return board;
    }

    public abstract PieceType getPieceType();

}

