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

        final JLabel scoreLabel = new JLabel("Player: ");
        this.add(scoreLabel,BorderLayout.NORTH);

        createMenu();
        this.pack();
        this.setVisible(true);
        addMouseListener(this);
        final Action updateLabel = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                scoreLabel.setText("Player: " + Board.getTurn());
                gameComponent.boardChanged();
            }
        };
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
            if(this.board.getTurn()%2==0){
                clickFunc(PieceColor.WHITE);
                System.out.println("TurnCounter: " + this.board.getTurn());
            }
            else if(this.board.getTurn()%2==1){
                clickFunc(PieceColor.BLACK);
                System.out.println("TurnCounter: " + this.board.getTurn());
            }

        }
        boardChanged();
    }

    private void clickFunc(PieceColor color){
        //first click on piece
        if (currentSelectedPiece.getPieceType() != PieceType.NONE && currentSelectedPiece.getPieceType() != PieceType.OUTSIDE){
            if(clickCounter==1){
                for(int i=0; i<checkList.getList().size(); i++){
                    if(checkList.get(i).getX() == currentSelectedPiece.getPosition().getX() && checkList.get(i).getY() == currentSelectedPiece.getPosition().getY()){
                        //Move
                        this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                        this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                        savedSelectedPiece.Move(currentSelectedPiece.getPosition());
                        GameComponent.selectedPiece.selectPiece();
                        //updateLabel();
                        this.board.nextTurn();
                        clearPiece();
                        System.out.println("cCOUNTER @ MOVE: " + clickCounter);
                    }

                    else if(i==checkList.getList().size()-1){
                        clearPiece();
                        System.out.println("cCOUNTER @ empty board3: " + clickCounter);
                    }
                }
            }
            else if(clickCounter == 0 && currentSelectedPiece.getPieceColor() == color){
                this.clickCounter++;
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
                if (checkList.size() == 0){
                    clearPiece();
                }
            }
            else{
                //this.clickCounter = 0;
                clearPiece();
                System.out.println("elsecase - no movelist: " + clickCounter);
            }
        }

        //click on empty boardspace
        else if (currentSelectedPiece.getPieceType() == PieceType.NONE){
            if(clickCounter == 0){
                clearPiece();
                System.out.println("cCOUNTER @ empty board1: " + clickCounter);
            }
            else if (currentSelectedPiece.getPieceType() != PieceType.OUTSIDE && clickCounter == 1){ //&& this.list.contains(currentSelectedPiece.getPosition())
                System.out.println("cCOUNTER @ empty board2: " + clickCounter);
                //Move to empty spot
                System.out.println("Current piece: " + this.currentSelectedPiece);
                for(int i=0; i<checkList.getList().size(); i++){
                    if(checkList.get(i).getX() == currentSelectedPiece.getPosition().getX() && checkList.get(i).getY() == currentSelectedPiece.getPosition().getY()){
                        this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                        this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                        savedSelectedPiece.Move(currentSelectedPiece.getPosition());
                        GameComponent.selectedPiece.selectPiece();
                        GameComponent.changeTurnLabel(this.board.getTurn());
                        this.board.nextTurn();
                        clearPiece();
                        break;
                    }
                    else if(i==checkList.getList().size()-1){
                        clearPiece();
                    }
                }
            }
        }
        else{
            clearPiece();
            System.out.println("We are on ELSE case: " + clickCounter);
        }
        System.out.println("Clickcounter: " + this.clickCounter);
    }


    private void clearPiece(){
        this.savedSelectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
        GameComponent.selectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
        this.board.selectPiece(new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY())));
        this.clickCounter = 0;
        this.list = new PossibleMoves();
        GameComponent.list = this.list;
        this.checkList = new PossibleMoves();
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