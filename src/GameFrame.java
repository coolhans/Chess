import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements MouseListener {
    private Board board;
    private int clickCounter = 0;
    private Piece savedSelectedPiece = new None();
    private Piece currentSelectedPiece = new None();
    private PossibleMoves list = new PossibleMoves();
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
            this.list = new PossibleMoves(this.currentSelectedPiece, this.board);
            //first click on piece
            if (currentSelectedPiece.getPieceType() != PieceType.NONE && currentSelectedPiece.getPieceType() != PieceType.OUTSIDE){
                this.clickCounter++;
                if(clickCounter>1){ //&& this.list.contains(currentSelectedPiece.getPosition())
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
            }

            //Second click on empty boardspace
            if (currentSelectedPiece.getPieceType() == PieceType.NONE){
                if(clickCounter < 1){
                    this.savedSelectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
                    GameComponent.selectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
                    this.board.selectPiece(new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY())));
                    this.clickCounter = 0;
                }
                else if (currentSelectedPiece.getPieceType() != PieceType.OUTSIDE){ //&& this.list.contains(currentSelectedPiece.getPosition())
                    this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                    this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                    savedSelectedPiece.Move(currentSelectedPiece.getPosition());
                    this.clickCounter = 0;

                }


                    //if(not in movelist){

                    //}
                    //else if(is in movelist){
                    //   this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                    //   this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                    //  savedSelectedPiece.Move(currentSelectedPiece.getPosition());
                    // this.clickCounter = 0;
                    //
            }
            //PossibleMoves list = new PossibleMoves(currentSelectedPiece, this.board);
            System.out.println("Mouse Clicked at PieceX: "+ GameComponent.clickedX+ " -Y: " + GameComponent.clickedY);
            System.out.println("Selected Piece is: "+ this.board.getPiece(GameComponent.clickedX,GameComponent.clickedY));

            GameComponent.list = this.list;
            for(int i=0; i<list.getList().size(); i++){
                System.out.println("moveslist: " + list.getList().get(i).getX() + ", " + list.getList().get(i).getY());
            }
            System.out.println("Selected Piece is: "+ currentSelectedPiece.getPieceColor());
            //Piece piece = selectPiece(x,y);
        }
        boardChanged();
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