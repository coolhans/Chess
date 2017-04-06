import javafx.geometry.Pos;

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
    private PossibleMoves checkList = new PossibleMoves();
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
            this.list = new PossibleMoves();
            this.list = new PossibleMoves(this.currentSelectedPiece, this.board);

            //first click on piece
            if (currentSelectedPiece.getPieceType() != PieceType.NONE && currentSelectedPiece.getPieceType() != PieceType.OUTSIDE){
                this.clickCounter++;
                if(clickCounter>1){
                    for(int i=0; i<checkList.getList().size(); i++){
                        if(checkList.get(i).getX() == currentSelectedPiece.getPosition().getX() && checkList.get(i).getY() == currentSelectedPiece.getPosition().getY()){
                            //Move
                            this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                            this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                            savedSelectedPiece.Move(currentSelectedPiece.getPosition());
                            GameComponent.selectedPiece.selectPiece();
                            this.list = new PossibleMoves();
                            this.checkList = new PossibleMoves();
                            this.clickCounter = 0;
                            System.out.println("cCOUNTER @ MOVE: " + clickCounter);
                        }
                        else if(!(checkList.get(i).getX() == currentSelectedPiece.getPosition().getX() && checkList.get(i).getY() == currentSelectedPiece.getPosition().getY())){
                            //Try to move on position not allowed
                            this.savedSelectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
                            GameComponent.selectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
                            this.board.selectPiece(new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY())));
                            this.clickCounter = 0;
                            this.list = new PossibleMoves();
                            System.out.println("cCOUNTER @ TRY NOT ALLOWED: " + clickCounter);
                        }
                        else{
                            System.out.println("cCOUNTER @ bug: " + clickCounter);
                        }

                    }

                }
                else{
                    //Press and select piece
                    this.savedSelectedPiece = currentSelectedPiece;
                    savedSelectedPiece.selectPiece();
                    GameComponent.selectedPiece = savedSelectedPiece;
                    this.board.selectPiece(savedSelectedPiece);
                    GameComponent.list = this.list;
                    this.checkList = new PossibleMoves(this.savedSelectedPiece, this.board);
                    for(int i=0; i<checkList.getList().size(); i++){
                        System.out.println("checklist element: " + checkList.getList().get(i).getX() + ", " + checkList.getList().get(i).getY());
                    }
                    System.out.println("cCOUNTER @ select piece: " + clickCounter);
                }
            }

            //click on empty boardspace
            if (currentSelectedPiece.getPieceType() == PieceType.NONE){
                if(clickCounter < 1){
                    //Deselect
                    this.savedSelectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
                    GameComponent.selectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
                    this.board.selectPiece(new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY())));
                    this.clickCounter = 0;
                    this.list = new PossibleMoves();
                    GameComponent.list = this.list;
                    this.checkList = new PossibleMoves();
                    System.out.println("cCOUNTER @ empty board1: " + clickCounter);
                }
                else if (currentSelectedPiece.getPieceType() != PieceType.OUTSIDE){ //&& this.list.contains(currentSelectedPiece.getPosition())
                    System.out.println("cCOUNTER @ empty board2: " + clickCounter);
                    //Move to empty spot
                    System.out.println("Current piece: " + this.currentSelectedPiece);
                    for(int i=0; i<checkList.getList().size(); i++){
                        System.out.println("checklist element222: " + checkList.getList().get(i).getX() + ", " + checkList.getList().get(i).getY());
                        if(checkList.get(i).getX() == currentSelectedPiece.getPosition().getX() && checkList.get(i).getY() == currentSelectedPiece.getPosition().getY()){
                            this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                            this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                            savedSelectedPiece.Move(currentSelectedPiece.getPosition());
                            GameComponent.selectedPiece.selectPiece();
                            this.clickCounter = 0;
                            this.list = new PossibleMoves();
                            GameComponent.list = this.list;
                            this.checkList = new PossibleMoves();
                            System.out.println("cCOUNTER @ empty board3: " + clickCounter);
                            break;
                        }
                        else if(i==checkList.getList().size()-1){
                            this.savedSelectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
                            GameComponent.selectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
                            this.board.selectPiece(new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY())));
                            this.clickCounter = 0;
                            this.list = new PossibleMoves();
                            GameComponent.list = this.list;
                            this.checkList = new PossibleMoves();
                        }
                    }
                  //  if(this.checkList.contains(currentSelectedPiece.getPosition())){

                  //  }
                }
            }
            System.out.println("Selected Piece is: "+ this.board.getPiece(GameComponent.clickedX,GameComponent.clickedY));
            System.out.println("Selected Piece is: "+ this.board.getPiece(GameComponent.clickedX,GameComponent.clickedY).getPosition());
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