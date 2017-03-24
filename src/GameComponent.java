import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class GameComponent extends JComponent implements BoardListener {
    static int clickedX; //changed by clicking on frame
    static int clickedY; //changed by clicking on frame
    private final Board board;
    private EnumMap<PieceType, ImageIcon> enumMapWhite;
    private EnumMap<PieceType, ImageIcon> enumMapBlack;
    private ImageIcon img = new ImageIcon();
    public GameComponent(Board board) {
        this.board = board;

        EnumMap<PieceType, ImageIcon> enumMapWhite =  new EnumMap<PieceType, ImageIcon>(PieceType.class);
        enumMapWhite.put(PieceType.PAWN, new ImageIcon("/images/wPawn.png"));
        enumMapWhite.put(PieceType.ROOK, new ImageIcon("/images/wRook.png"));
        enumMapWhite.put(PieceType.KNIGHT, new ImageIcon("/images/wKnight.png"));
        enumMapWhite.put(PieceType.BISHOP, new ImageIcon("/images/wBishop.png"));
        enumMapWhite.put(PieceType.QUEEN, new ImageIcon("/images/wQueen.png"));
        enumMapWhite.put(PieceType.KING, new ImageIcon("/images/wKing.png"));
       /* enumMap.put(PieceType.BPAWN, new ImageIcon("/images/Pawn.png"));
        enumMap.put(PieceType.BROOK, new ImageIcon("/images/Rook.png"));
        enumMap.put(PieceType.BKNIGHT, new ImageIcon("/images/Knight.png"));
        enumMap.put(PieceType.BBISHOP, new ImageIcon("/images/Bishop.png"));
        enumMap.put(PieceType.BQUEEN, new ImageIcon("/images/Queen.png"));
        enumMap.put(PieceType.BKING, new ImageIcon("/images/King.png"));
        */
        enumMapWhite.put(PieceType.NONE, new ImageIcon("/images/Blank.jpg"));//image not used, avoid error
        enumMapWhite.put(PieceType.OUTSIDE, new ImageIcon("/images/Blank.jpg"));//image not used, avoid error

        //this.enumMap = enumMap;

        EnumMap<PieceType, ImageIcon> enumMapBlack =  new EnumMap<PieceType, ImageIcon>(PieceType.class);
        enumMapBlack.put(PieceType.PAWN, new ImageIcon("/images/Pawn.png"));
        enumMapBlack.put(PieceType.ROOK, new ImageIcon("/images/Rook.png"));
        enumMapBlack.put(PieceType.KNIGHT, new ImageIcon("/images/Knight.png"));
        enumMapBlack.put(PieceType.BISHOP, new ImageIcon("/images/Bishop.png"));
        enumMapBlack.put(PieceType.QUEEN, new ImageIcon("/images/Queen.png"));
        enumMapBlack.put(PieceType.KING, new ImageIcon("/images/King.png"));

        enumMapBlack.put(PieceType.NONE, new ImageIcon("/images/Blank.jpg"));//image not used, avoid error
        enumMapBlack.put(PieceType.OUTSIDE, new ImageIcon("/images/Blank.jpg"));//image not used, avoid error
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth() - 100, screenSize.getHeight() - 100);
        return screenSize;
    }

    @Override
    //Paint the board.
    protected void paintComponent (Graphics g) {
        //super.paintComponent(g);
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {

                if (((x + y) % 2) == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(y * 60, x * 60, 60, 60);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(y * 60, x * 60, 60, 60);
                }
            }
        }
//------Paint the pieces correspondingly, including OUTSIDE.
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Piece piece = board.getPiece(x, y);

                if(board.getPiece(x,y).getPieceColor()==PieceColor.WHITE){
                    this.img = enumMapWhite.get(piece);
                }

                else if(board.getPiece(x,y).getPieceColor()==PieceColor.BLACK){
                    this.img = enumMapBlack.get(piece);
                }

            if (piece != null) {
                if (piece.getPieceType() == PieceType.OUTSIDE) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(y * 60, x * 60, 60, 60);
                    //img.paintIcon(this, g, (x * 60), (y * 60));
                }
                else {
                    //g.setColor(Color.GREEN);
                    //g.fillRect(y * 50, x * 50, 40, 40);
                    if (piece.getPieceType() != PieceType.NONE) {
                        img.paintIcon(this, g, (x * 60), (y * 60));
                    }
                }
            }
        }
    }

    }

    @Override
    public void boardChanged() {
        repaint();

    }

    //show possible moves
    public void highlightMoves(){
        board.getPiece(clickedX,clickedY);
    }

    //get selected piece
}