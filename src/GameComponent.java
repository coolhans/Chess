import javax.swing.*;
import java.awt.*;
import java.util.EnumMap;

public class GameComponent extends JComponent implements BoardListener {
    private final Board board;
    private EnumMap<Piece, Color> enumMap;

    public GameComponent(Board board) {
        this.board = board;


        EnumMap<Piece, Color> enumMap =  new EnumMap<Piece, Color>(Piece.class);
        enumMap.put(Piece.WPAWN, Color.YELLOW);
        enumMap.put(Piece.WROOK, Color.GREEN);
        enumMap.put(Piece.WKNIGHT, Color.PINK);
        enumMap.put(Piece.WBISHOP, Color.BLUE);
        enumMap.put(Piece.WQUEEN, Color.RED);
        enumMap.put(Piece.WKING, Color.ORANGE);
        enumMap.put(Piece.BPAWN, Color.DARK_GRAY);
        enumMap.put(Piece.BROOK, Color.DARK_GRAY);
        enumMap.put(Piece.BKNIGHT, Color.DARK_GRAY);
        enumMap.put(Piece.BBISHOP, Color.DARK_GRAY);
        enumMap.put(Piece.BQUEEN, Color.LIGHT_GRAY);
        enumMap.put(Piece.BKING, Color.CYAN);

        enumMap.put(Piece.OUTSIDE, Color.LIGHT_GRAY);
        this.enumMap = enumMap;
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        screenSize.setSize(screenSize.getWidth() - 100, screenSize.getHeight() - 100);
        return screenSize;
    }

    @Override
    protected void paintComponent (Graphics g){
        for (int y = 0; y < board.getWidth() ; y++) {
            for (int x = 0; x < board.getHeight() ; x++) {

                if(((x+y) % 2)==0) {
                    g.setColor(Color.WHITE);  //indicates null surface
                    g.fillRect(y * 50, x * 50, 50, 50);
                }
                else {
                    g.setColor(Color.BLACK);  //indicates null surface
                    g.fillRect(y * 50, x * 50, 50, 50);
                }

                Piece piece = board.getPiece(y,x);
                Color color = enumMap.get(piece);
                if (color !=null) {
                    if (piece == Piece.OUTSIDE){
                        g.setColor(color);
                        g.fillRect(y * 50, x * 50, 50, 50);
                    }
                    else {
                        g.setColor(color);
                        g.fillRect(y * 50, x * 50, 40, 40);
                    }
                }
            }

        }
    }

    @Override
    public void boardChanged() {
        repaint();

    }
}