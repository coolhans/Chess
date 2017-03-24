import java.util.*;

public abstract class Piece {
    private boolean moved = false;
    private boolean selected = false;
    private boolean avaialable;

    private Board board;

    private Coords position;
    private PieceColor pieceColor;
    private PieceType pieceType;



    public abstract ArrayList<Coords> possibleMoves();

    public abstract void Move(Coords coords);

    public boolean isSelected(){
        return selected;
    }

    public Coords getPosition(){
        return position;
    }

    public abstract PieceColor getPieceColor();

    public boolean hasMoved(){
        return moved;
    }

    public Board getBoard(){
        return board;
    }

    public abstract PieceType getPieceType();

}

