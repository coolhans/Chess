import javax.swing.*;
import java.awt.*;

public class GameComponent extends JComponent implements BoardListener {
    static int clickedX; //changed by clicking on frame
    static int clickedY; //changed by clicking on frame
    private final Board board;
    public static Piece selectedPiece = new None();
    private ImageIcon img = new ImageIcon();
    public GameComponent(Board board) {
        this.board = board;

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
        super.paintComponent(g);
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

        if (selectedPiece.isSelected()){
            g.setColor(Color.YELLOW);
            g.fillRect((clickedX* 60), (clickedY * 60), 60, 60);
            /*for(int i = 0; i<selectedPiece.possibleMoves().size(); i++) {
                g.setColor(Color.YELLOW);
                g.fillRect((selectedPiece.possibleMoves().get(i).getX() * 60), (selectedPiece.possibleMoves().get(i).getY() * 60), 60, 60);
            }*/
        }


        for (int y = 0; y < board.getHeight(); y++) {
            for (int x = 0; x < board.getWidth(); x++) {
                Piece piece = board.getPiece(x, y);

                if(piece.getPieceType() == PieceType.PAWN && piece.getPieceColor()==PieceColor.WHITE){
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
                    g.fillRect(y * 60, x * 60, 60, 60);
                }

                else if(piece.getPieceType() == PieceType.NONE && piece.getPieceColor()==PieceColor.NOCOLOR){
                    img = new ImageIcon("/images/Blank.jpg");
                }

                else{
                    img = new ImageIcon("/images/Blank.jpg");
                }
                //img = new ImageIcon("/images/wQueen.png");
                img.paintIcon(this, g, (x * 60), (y * 60));
            }
        }
    }

    @Override
    public void boardChanged() {
        repaint();
    }


}