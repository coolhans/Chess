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
    private JLabel playerLabel;
    private JLabel checkLabel;
    private ArrayList<Coords> checkPieces = new ArrayList<Coords>();
    private boolean checkState = false;

    public GameFrame(Board board){
        super("Chess");
        setFocusable(true);
        final GameComponent gameComponent = new GameComponent(board);
        this.setLayout(new BorderLayout());
        board.addBoardListener(gameComponent);
        this.add(gameComponent,BorderLayout.CENTER);
        this.board = board;

        playerLabel = new JLabel("Player: White");
        this.add(playerLabel,BorderLayout.NORTH);

        checkLabel = new JLabel("");
        this.add(checkLabel,BorderLayout.SOUTH);



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


    public void updateLabel() {
        if(this.board.getTurn()%2==0){
            playerLabel.setText("Player: Black");
        }
        else if(this.board.getTurn()%2==1){
            playerLabel.setText("Player: White");
        }
        if(checkState){
            checkLabel.setText("Check");
        }
        if(!checkState){
            checkLabel.setText("");
        }
        boardChanged();
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

            //Remove non eligeble moves from Kings possible moves list
            if(currentSelectedPiece.getPieceType()==PieceType.KING){
                ArrayList<Coords> removedCoords = new ArrayList<Coords>();
                for(int i=1; i<9; i++) {
                    for (int j = 1; j < 9; j++) {
                        if (currentSelectedPiece.getPieceColor() == PieceColor.WHITE) {
                            if (this.board.getPiece(j, i).getPieceColor() == PieceColor.BLACK){
                                if(this.board.getPiece(j, i).getPieceType() != PieceType.PAWN){
                                    PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                                    for(int k=0; k<this.list.size(); k++){
                                        for(int l=0; l<tempList.size(); l++){
                                            if(tempList.get(l).getX()==this.list.get(k).getX() && tempList.get(l).getY()==this.list.get(k).getY() && !removedCoords.contains(this.list.get(k))){
                                                removedCoords.add(this.list.get(k));
                                                this.list.remove(k);
                                                if(k!=0){
                                                    k= k-1;
                                                }
                                                else{
                                                    k=0;
                                                }
                                            }
                                        }
                                    }
                                }
                                //Check with enemy pawns
                                else{
                                    for(int k=0; k<this.list.size(); k++){
                                        for (int p = -1; p < 2; p++){
                                            if(p!=0 && this.board.getPiece(this.board.getPiece(j, i).getPosition().getX()+p, this.board.getPiece(j, i).getPosition().getY()+1).getPieceType() != PieceType.OUTSIDE){
                                                if(this.list.get(k).getX()== this.board.getPiece(j, i).getPosition().getX()+p && this.list.get(k).getY()== this.board.getPiece(j, i).getPosition().getY()+1 && !removedCoords.contains(this.list.get(k))){
                                                    removedCoords.add(this.list.get(k));
                                                    this.list.remove(k);
                                                    if(k!=0){
                                                        k= k-1;
                                                    }
                                                    else{
                                                        k=0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }
                        else if (currentSelectedPiece.getPieceColor() == PieceColor.BLACK) {
                            if (this.board.getPiece(j, i).getPieceColor() == PieceColor.WHITE) {
                                if(this.board.getPiece(j, i).getPieceType() != PieceType.PAWN){
                                    PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                                    for(int k=0; k<this.list.size(); k++){
                                        for(int l=0; l<tempList.size(); l++){
                                            if(tempList.get(l).getX()==this.list.get(k).getX() && tempList.get(l).getY()==this.list.get(k).getY() && !removedCoords.contains(this.list.get(k))){
                                                removedCoords.add(this.list.get(k));
                                                this.list.remove(k);
                                                if(k!=0){
                                                    k= k-1;
                                                }
                                                else{
                                                    k=0;
                                                }
                                            }
                                        }
                                    }
                                }
                                //Check with enemy pawns
                                else{
                                    for(int k=0; k<this.list.size(); k++){
                                        for (int p = -1; p < 2; p++){
                                            if(p!=0 && this.board.getPiece(this.board.getPiece(j, i).getPosition().getX()+p, this.board.getPiece(j, i).getPosition().getY()+1).getPieceType() != PieceType.OUTSIDE){
                                                if(this.list.get(k).getX()== this.board.getPiece(j, i).getPosition().getX()+p && this.list.get(k).getY()== this.board.getPiece(j, i).getPosition().getY()-1 && !removedCoords.contains(this.list.get(k))){
                                                    removedCoords.add(this.list.get(k));
                                                    this.list.remove(k);
                                                    if(k!=0){
                                                        k= k-1;
                                                    }
                                                    else{
                                                        k=0;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            }

            //Player Label
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
                        if(checkSelfCheck(savedSelectedPiece, currentSelectedPiece)){
                            this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                            this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                            savedSelectedPiece.Move(currentSelectedPiece.getPosition());
                            GameComponent.selectedPiece.selectPiece();

                            this.board.nextTurn();

                            System.out.println("cCOUNTER @ MOVE: " + clickCounter);

                            if(checkEnemyCheck(savedSelectedPiece)){
                                GameComponent.checkPieces = this.checkPieces;
                                this.checkPieces = new ArrayList<Coords>();
                            }
                            else if(!checkEnemyCheck(savedSelectedPiece)){
                                this.checkPieces = new ArrayList<Coords>();
                                GameComponent.checkPieces = this.checkPieces;
                                this.checkState = false;
                            }
                            updateLabel();
                            clearPiece();
                        }
                        else if(!checkSelfCheck(savedSelectedPiece, currentSelectedPiece)){
                            clearPiece();
                            break;
                        }
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
                        if(checkSelfCheck(savedSelectedPiece, currentSelectedPiece)){
                            System.out.println("An OK move...");
                            this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                            this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                            savedSelectedPiece.Move(currentSelectedPiece.getPosition());
                            GameComponent.selectedPiece.selectPiece();

                            this.board.nextTurn();


                            if(checkEnemyCheck(savedSelectedPiece)){
                                GameComponent.checkPieces = this.checkPieces;
                                this.checkPieces = new ArrayList<Coords>();
                            }
                            else if(!checkEnemyCheck(savedSelectedPiece)){
                                this.checkPieces = new ArrayList<Coords>();
                                GameComponent.checkPieces = this.checkPieces;
                                this.checkState = false;
                            }
                            updateLabel();
                            clearPiece();
                            break;
                        }
                        else if(!checkSelfCheck(savedSelectedPiece, currentSelectedPiece)){
                            System.out.println("NOT an OK move...");
                            clearPiece();
                            break;
                        }



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

    private boolean checkEnemyCheck(Piece piece){
        boolean returnValue = false;
        for(int i=1; i<9; i++) {
            for(int j=1; j<9; j++) {
                if(this.board.getPiece(j,i).getPieceColor() == piece.getPieceColor()){
                    PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                    for(int k=0; k<tempList.size(); k++){
                        if(this.board.getPiece(tempList.get(k).getX(), tempList.get(k).getY()).getPieceType() == PieceType.KING){
                            this.checkState = true;
                            returnValue = true;
                            if(!checkPieces.contains(tempList.get(k))){
                                this.checkPieces.add(tempList.get(k));
                            }
                            if(!checkPieces.contains(new Coords(j,i))){
                                this.checkPieces.add(new Coords(j,i));
                            }
                        }
                    }
                }
            }
        }
        return returnValue;
    }

    private boolean checkSelfCheck(Piece piece, Piece movePiece){
        //Temporarily ignore selected piece
        Piece savePiece = this.board.getPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY());
        this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), new None());
        this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), piece);
        //Tempo....
        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                if(piece.getPieceColor() == PieceColor.WHITE){
                    if(this.board.getPiece(j,i).getPieceColor() == PieceColor.BLACK){
                        PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j,i), this.board);
                        for(int k=0; k<tempList.getList().size(); k++){
                            if (this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceType() == PieceType.KING  && this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceColor() == PieceColor.WHITE){
                                this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), piece);
                                this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), savePiece);
                                System.out.println("CheckSelfCheck: list for piece: x= " + i + ", y=" + j);
                                System.out.println("CheckSelfCheck: x= " + tempList.get(k).getX() + ", y= "+ tempList.get(k).getY());
                                return false;
                            }
                        }
                    }
                }
                else if(piece.getPieceColor() == PieceColor.BLACK){
                    if(this.board.getPiece(j,i).getPieceColor() == PieceColor.WHITE){
                        PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j,i), this.board);
                        for(int k=0; k<tempList.getList().size(); k++){
                            if (this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceType() == PieceType.KING && this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceColor() == PieceColor.BLACK){
                                this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), piece);
                                this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), savePiece);
                                System.out.println("CheckSelfCheck: list for piece: x= " + i + ", y=" + j);
                                System.out.println("CheckSelfCheck: King pos: x= " + tempList.get(k).getX() + ", y= "+ tempList.get(k).getY());
                                return false;
                            }
                        }
                    }
                }
            }
        }
        this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), piece);
        this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), savePiece);
        return true;
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