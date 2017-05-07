import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent implements BoardListener {
    public static int clickedX = 0; //changed by clicking on frame
    public static int clickedY = 0; //changed by clicking on frame
    public static PossibleMoves list = null;
    private final Board board;
    public static Piece selectedPiece = new None();
    public static Piece killer = new None();

    public GameComponent(Board board) {
        this.board = board;

    }

    //Scaling our game
    @Override
    public Dimension getPreferredSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth() - 100, screenSize.getHeight() - 100);
        return screenSize;
    }

    @Override
    //Paint the board.
    protected void paintComponent (Graphics g) {
        super.paintComponent(g);
        final int squareSize = 60;
        for (int x = 0; x < board.getWidth(); x++) {
            for (int y = 0; y < board.getHeight(); y++) {

                if (((x + y) % 2) == 0) {
                    g.setColor(Color.WHITE);
                    g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
                } else {
                    g.setColor(Color.LIGHT_GRAY);
                    g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
                }

                //Checker
                if (killer.getPosition().getY() == y &&killer.getPosition().getX() == x) {
                    g.setColor(Color.PINK);
                    g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
                }

            }
        }

        //Highlighting selected piece and that pieces possible moves
        if (selectedPiece.isSelected() && selectedPiece.getPieceType() != PieceType.NONE){
            g.setColor(Color.YELLOW);
            g.fillRect((clickedX * squareSize), (clickedY * squareSize), squareSize, squareSize);
            for(int i=0; i<list.size(); i++){
                g.setColor(Color.RED);
                g.fillRect((list.get(i).getX() * squareSize), (list.get(i).getY() * squareSize), squareSize, squareSize);
            }
        }

        //Import images
        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Piece piece = board.getPiece(x, y);

                ImageIcon img;
                if(piece.getPieceType() == PieceType.PAWN && piece.getPieceColor() == PieceColor.WHITE){
                    img = new ImageIcon("./images/wPawn.png");
                }
                else if(piece.getPieceType() == PieceType.ROOK && piece.getPieceColor()==PieceColor.WHITE){
                    img = new ImageIcon("./images/wRook.png");
                }
                else if(piece.getPieceType() == PieceType.KNIGHT && piece.getPieceColor()==PieceColor.WHITE){
                    img = new ImageIcon("./images/wKnight.png");
                }
                else if(piece.getPieceType() == PieceType.BISHOP && piece.getPieceColor()==PieceColor.WHITE){
                    img = new ImageIcon("./images/wBishop.png");
                }
                else if(piece.getPieceType() == PieceType.KING && piece.getPieceColor()==PieceColor.WHITE){
                    img = new ImageIcon("./images/wKing.png");
                }
                else if(piece.getPieceType() == PieceType.QUEEN && piece.getPieceColor()==PieceColor.WHITE){
                    img = new ImageIcon("./images/wQueen.png");
                }

                else if(piece.getPieceType() == PieceType.PAWN && piece.getPieceColor()==PieceColor.BLACK){
                    img = new ImageIcon("./images/Pawn.png");
                }
                else if(piece.getPieceType() == PieceType.ROOK && piece.getPieceColor()==PieceColor.BLACK){
                    img = new ImageIcon("./images/Rook.png");
                }
                else if(piece.getPieceType() == PieceType.KNIGHT && piece.getPieceColor()==PieceColor.BLACK){
                    img = new ImageIcon("./images/Knight.png");
                }
                else if(piece.getPieceType() == PieceType.BISHOP && piece.getPieceColor()==PieceColor.BLACK){
                    img = new ImageIcon("./images/Bishop.png");
                }
                else if(piece.getPieceType() == PieceType.KING && piece.getPieceColor()==PieceColor.BLACK){
                    img = new ImageIcon("./images/King.png");
                }
                else if(piece.getPieceType() == PieceType.QUEEN && piece.getPieceColor()==PieceColor.BLACK){
                    img = new ImageIcon("./images/Queen.png");
                }

                else if(piece.getPieceType() == PieceType.OUTSIDE && piece.getPieceColor()==PieceColor.NOCOLOR){
                    img = new ImageIcon("/images/Blank.jpg");
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(x * squareSize, y * squareSize, squareSize, squareSize);
                }

                else {
                    img = new ImageIcon("/images/Blank.jpg");
                }
                img.paintIcon(this, g, (x * squareSize), (y * squareSize));
            }
        }
    }

    @Override
    public void boardChanged() {
        repaint();
    }


}