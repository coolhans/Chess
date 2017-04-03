import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements MouseListener {
    private Board board;
    private int clickCounter = 0;
    private Piece savedSelectedPiece = new None();
    private Piece currentSelectedPiece = new None();
    public GameFrame(Board board){
        super("Chess");
        setFocusable(true);
        final GameComponent gameComponent = new GameComponent(board);
        this.setLayout(new BorderLayout());
        board.addBoardListener(gameComponent);
        this.add(gameComponent,BorderLayout.CENTER);
        this.board = board;

        JLabel scoreLabel = new JLabel("Player: ");
        this.add(scoreLabel,BorderLayout.NORTH);

        createMenu();
        this.pack();
        this.setVisible(true);
        addMouseListener(this);
    }

    private void createMenu(){
        final JMenu file = new JMenu("File");
        file.addSeparator();
        JMenuItem exit = file.add(new JMenuItem("exit"));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "exit", "choose", JOptionPane.YES_NO_OPTION);
                if(option == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        file.add(exit);
        final JMenuBar bar = new JMenuBar();
        bar.add(file);
        this.setJMenuBar(bar);
    }

    @Override
    public void mouseClicked(MouseEvent e){
        if (e.getX()<600 && e.getY()<660){
            int x = e.getX();
            int y = e.getY();
            System.out.println("Mouse Clicked at X: " +x+ " -Y: " + y);
            GameComponent.clickedX = (x / 60);
            GameComponent.clickedY = (y / 60) - 1;
            this.currentSelectedPiece = this.board.getPiece(GameComponent.clickedX, GameComponent.clickedY);
            if (currentSelectedPiece.getPieceType() != PieceType.NONE){ //chaos
                this.clickCounter++;
                if(clickCounter>1){
                    //Move
                    this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                    this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                    savedSelectedPiece.Move(currentSelectedPiece.getPosition());


                    this.clickCounter = 0;
                }
                else{
                    this.savedSelectedPiece = currentSelectedPiece;
                    savedSelectedPiece.selectPiece();
                    GameComponent.selectedPiece = savedSelectedPiece;
                    this.board.selectPiece(savedSelectedPiece);
                }
                boardChanged();
            }

            System.out.println("Mouse Clicked at PieceX: "+ GameComponent.clickedX+ " -Y: " + GameComponent.clickedY);
            System.out.println("Selected Piece is: "+ this.board.getPiece(GameComponent.clickedX,GameComponent.clickedY));

            System.out.println("Selected Piece is: "+ savedSelectedPiece.getPieceColor());
            //Piece piece = selectPiece(x,y);
        }
    }

    @Override
    public void mouseEntered(MouseEvent e){
        int x = e.getX();
        int y = e.getY();

        //System.out.println("Mouse Entered at X: " +x+ " -Y: " + y);
    }
    @Override
    public void mouseExited(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        //System.out.println("Mouse Exited at X: " +x+ " -Y: " + y);
    }
    @Override
    public void mousePressed(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        //System.out.println("Mouse Pressed at X: " +x+ " -Y: " + y);
    }
    @Override
    public void mouseReleased(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        //System.out.println("Mouse Released at X: " +x+ " -Y: " + y);
    }

    public void boardChanged() {
        repaint();

    }

}