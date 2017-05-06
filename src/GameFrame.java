import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JFrame;

import static java.lang.Math.abs;

public class GameFrame extends JFrame implements MouseListener {
    private Board board;
    private int clickCounter = 0;
    private Piece savedSelectedPiece = new None();
    private Piece currentSelectedPiece = new None();
    private PossibleMoves list = new PossibleMoves();
    private PossibleMoves checkList = new PossibleMoves();

    private JLabel playerLabel;
    private JLabel checkLabel;
    private JLabel checkmateLabel;
    public static JLabel timeLabel;
    public static int timeCounter = 0;
    private Piece killer = new None();
    private ArrayList<Coords> killerPath = new ArrayList<Coords>();
    //private ArrayList<Coords> checkPieces = new ArrayList<Coords>();
    private boolean checkState = false;

    public GameFrame(Board board){
        super("Chess");
        setFocusable(true);
        final GameComponent gameComponent = new GameComponent(board);
        this.setLayout(new BorderLayout());
        board.addBoardListener(gameComponent);
        this.add(gameComponent,BorderLayout.CENTER);
        this.board = board;

	createLabelPanel();
        createMenu();
        this.pack();
        this.setVisible(true);
        addMouseListener(this);
    }

    private void createLabelPanel(){
        JPanel labelPanel = new JPanel(new BorderLayout());
        playerLabel = new JLabel("Player: White");
        checkLabel = new JLabel("Check: ");
	checkmateLabel = new JLabel("");
        timeLabel = new JLabel("Time Elapsed: " + timeCounter);
        playerLabel.setPreferredSize(new Dimension(150, 100));

        playerLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        checkLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
        timeLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));
	checkmateLabel.setBorder(BorderFactory.createCompoundBorder(
         BorderFactory.createRaisedBevelBorder(), BorderFactory.createLoweredBevelBorder()));

        labelPanel.setBorder(BorderFactory.createTitledBorder("Information"));
        labelPanel.setOpaque(true);
        labelPanel.setBackground(Color.WHITE);
        this.add(labelPanel, BorderLayout.EAST);

        BoxLayout boxLayout = new BoxLayout(labelPanel, BoxLayout.Y_AXIS); // top to bottom
        labelPanel.setLayout(boxLayout);
        labelPanel.add(playerLabel);
	labelPanel.add(timeLabel);
        labelPanel.add(checkLabel);
	labelPanel.add(checkmateLabel);

        labelPanel.setPreferredSize(new Dimension(195,200));
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
            playerLabel.setText("Player: White");
        }
        else if(this.board.getTurn()%2==1){
            playerLabel.setText("Player: Black");
        }
        if(checkState) {
	    checkLabel.setText("CHECK!");
	}
        if(!checkState){
            checkLabel.setText("Not check");
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
                this.list = fixKingList(currentSelectedPiece, this.list);
            }
            System.out.println("MoveList Size: " + this.list.getList().size());
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

    private PossibleMoves fixKingList(Piece piece, PossibleMoves kingList){
        PossibleMoves newKingList = kingList;
        ArrayList<Coords> removedCoords = new ArrayList<Coords>();
        for(int i=1; i<9; i++) {
            for (int j = 1; j < 9; j++) {
                if (piece.getPieceColor() == PieceColor.WHITE) {
                    if (this.board.getPiece(j, i).getPieceColor() == PieceColor.BLACK){
                        if(this.board.getPiece(j, i).getPieceType() != PieceType.PAWN){
                            PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                            for(int k=0; k<newKingList.size(); k++){
                                for(int l=0; l<tempList.size(); l++){
                                    if(tempList.get(l).getX()==newKingList.get(k).getX() && tempList.get(l).getY()==newKingList.get(k).getY() && !removedCoords.contains(newKingList.get(k))){
                                        removedCoords.add(newKingList.get(k));
                                        newKingList.remove(k);
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
                            for(int k=0; k<newKingList.size(); k++){
                                for (int p = -1; p < 2; p++){
                                    if(p!=0 && this.board.getPiece(this.board.getPiece(j, i).getPosition().getX()+p, this.board.getPiece(j, i).getPosition().getY()+1).getPieceType() != PieceType.OUTSIDE){
                                        if(newKingList.get(k).getX()== this.board.getPiece(j, i).getPosition().getX()+p && newKingList.get(k).getY()== this.board.getPiece(j, i).getPosition().getY()+1 && !removedCoords.contains(newKingList.get(k))){
                                            removedCoords.add(newKingList.get(k));
                                            newKingList.remove(k);
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
                else if (piece.getPieceColor() == PieceColor.BLACK) {
                    if (this.board.getPiece(j, i).getPieceColor() == PieceColor.WHITE) {
                        if(this.board.getPiece(j, i).getPieceType() != PieceType.PAWN){
                            PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                            for(int k=0; k<newKingList.size(); k++){
                                for(int l=0; l<tempList.size(); l++){
                                    if(tempList.get(l).getX()==newKingList.get(k).getX() && tempList.get(l).getY()==newKingList.get(k).getY() && !removedCoords.contains(newKingList.get(k))){
                                        removedCoords.add(newKingList.get(k));
                                        newKingList.remove(k);
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
                            for(int k=0; k<newKingList.size(); k++){
                                for (int p = -1; p < 2; p++){
                                    if(p!=0 && this.board.getPiece(this.board.getPiece(j, i).getPosition().getX()+p, this.board.getPiece(j, i).getPosition().getY()+1).getPieceType() != PieceType.OUTSIDE){
                                        if(newKingList.get(k).getX()== this.board.getPiece(j, i).getPosition().getX()+p && newKingList.get(k).getY()== this.board.getPiece(j, i).getPosition().getY()-1 && !removedCoords.contains(newKingList.get(k))){
                                            removedCoords.add(newKingList.get(k));
                                            newKingList.remove(k);
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
        return newKingList;
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


                            //See if pawn moves across the board to switch it for a new queen
                            if(savedSelectedPiece.getPieceType()==PieceType.PAWN){
                                if(savedSelectedPiece.getPieceColor() == PieceColor.WHITE && savedSelectedPiece.getPosition().getY() == 1){
                                    pawnToQueen(savedSelectedPiece);
                                }
                                else if(savedSelectedPiece.getPieceColor() == PieceColor.BLACK && savedSelectedPiece.getPosition().getY() == 8){
                                    pawnToQueen(savedSelectedPiece);
                                }
                            }

                            GameComponent.selectedPiece.selectPiece();
                            this.board.nextTurn();

                            System.out.println("cCOUNTER @ MOVE: " + clickCounter);

                            //CHECK CHECK
                            if(checkEnemyCheck(savedSelectedPiece)){
                                System.out.println("Checking check----------------------------------------------------------------------");
                                GameComponent.killer = this.killer;
                                //CHECK CHECKMATE
                                System.out.println("entering checkmatefunction");
                                if(checkCheckMate(savedSelectedPiece)){
				    checkmateLabel.setText("CHECKMATE, "+ color  +" WINS");
                                    System.out.println("CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE LOL CHECKMATE LOL CHECKMATE LOL CHECKMATE LOL CHECKMATE LOL ");
                                }
                                this.killer = new None();
                            }
                            else if(!checkEnemyCheck(savedSelectedPiece)){
                                this.killer = new None();
                                GameComponent.killer = this.killer;
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
                //Press and select piece
                selectPiece();
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

                            //See if pawn moves across the board to switch it for a new queen
                            if(savedSelectedPiece.getPieceType()==PieceType.PAWN){
                                if(savedSelectedPiece.getPieceColor() == PieceColor.WHITE && savedSelectedPiece.getPosition().getY() == 1){
                                    pawnToQueen(savedSelectedPiece);
                                }
                                else if(savedSelectedPiece.getPieceColor() == PieceColor.BLACK && savedSelectedPiece.getPosition().getY() == 8){
                                    pawnToQueen(savedSelectedPiece);
                                }
                            }

                            GameComponent.selectedPiece.selectPiece();

                            this.board.nextTurn();

                            //CHECK CHECK
                            if(checkEnemyCheck(savedSelectedPiece)){
                                System.out.println("Checking check----------------------------------------------------------------------");
                                GameComponent.killer = this.killer;
                                //CHECK CHECKMATE
                                System.out.println("entering checkmatefunctionn");
                                if(checkCheckMate(savedSelectedPiece)){
				    checkmateLabel.setText("CHECKMATE, "+ color  +" WINS");
                                    System.out.println("CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE CHECKMATE LOL CHECKMATE LOL CHECKMATE LOL CHECKMATE LOL CHECKMATE LOL CHECKMATE LOL CHECKMATE LOL ");
                                }
                                this.killer = new None();
                            }
                            else if(!checkEnemyCheck(savedSelectedPiece)){
                                this.killer = new None();
                                GameComponent.killer = this.killer;
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
            //selectPiece();
            System.out.println("We are on ELSE case: " + clickCounter);
        }
        System.out.println("Clickcounter: " + this.clickCounter);
    }

    private void selectPiece(){
        this.clickCounter++;
        this.savedSelectedPiece = currentSelectedPiece;
        savedSelectedPiece.selectPiece();
        GameComponent.selectedPiece = savedSelectedPiece;
        this.board.selectPiece(savedSelectedPiece);
        GameComponent.list = this.list;
        this.checkList = new PossibleMoves(this.savedSelectedPiece, this.board);
        System.out.println("cCOUNTER @ select piece: " + clickCounter);
        if (checkList.size() == 0){
            clearPiece();
        }
        //boardChanged();
    }
    private boolean checkEnemyCheck(Piece piece){
        int diffX, diffY;
        boolean returnValue = false;
        for(int i=1; i<9; i++) {
            for(int j=1; j<9; j++) {
                if(this.board.getPiece(j,i).getPieceColor() == piece.getPieceColor()){
                    PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                    for(int k=0; k<tempList.size(); k++){
                        if(this.board.getPiece(tempList.get(k).getX(), tempList.get(k).getY()).getPieceType() == PieceType.KING){
                            this.checkState = true;
                            returnValue = true;
                            this.killer = this.board.getPiece(j,i);
                            if(this.board.getPiece(j, i).getPieceType() != PieceType.KNIGHT && this.board.getPiece(j, i).getPieceType() != PieceType.PAWN){
                                diffX = j-tempList.get(k).getX();
                                diffY = i-tempList.get(k).getY();
                                System.out.println("decidingKillerPath");
                                System.out.println("diffX: " + diffX + " diffY: " + diffY);
                                //bishopmoves
                                if(this.board.getPiece(j, i).getPieceType() == PieceType.BISHOP || (this.board.getPiece(j, i).getPieceType() == PieceType.QUEEN && (j!=tempList.get(k).getX() || i!=tempList.get(k).getY()))){
                                    if(diffX<0&&diffY<0){
                                        for(int l=-1; l>diffX; l--) {
                                            for(int m=-1; m>diffY; m--) {
                                                if(abs(l) == abs(m)){
                                                    this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()+m));
                                                    System.out.println("killerpath 1");
                                                    System.out.println("killerPath Element: x=" + tempList.get(k).getX()+l + "y=" + tempList.get(k).getY()+m);
                                                }
                                            }
                                        }
                                    }
                                    else if(diffX>0&&diffY<0){
                                        for(int l=1; l<diffX; l++) {
                                            for(int m=-1; m>diffY; m--) {
                                                if(abs(l) == abs(m)){
                                                    this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()+m));
                                                    System.out.println("killerpath 2");
                                                    System.out.println("killerPath Element: x=" + tempList.get(k).getX()+l + "y=" + tempList.get(k).getY()+m);
                                                }
                                            }
                                        }
                                    }
                                    else if(diffX<0&&diffY>0){
                                        for(int l=-1; l>diffX; l--) {
                                            for(int m=1; m<diffY; m++) {
                                                if(abs(l) == abs(m)){
                                                    this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()+m));
                                                    System.out.println("killerpath 3");
                                                    System.out.println("killerPath Element: x=" + (tempList.get(k).getX()+l) + "y=" + (tempList.get(k).getY()+m));
                                                }
                                            }
                                        }
                                    }
                                    else if(diffX>0&&diffY>0){
                                        for(int l=1; l<diffX; l++) {
                                            for(int m=1; m<diffY; m++) {
                                                if(abs(l) == abs(m)){
                                                    System.out.println("killerpath 4");
                                                    this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()+m));
                                                    System.out.println("killerPath Element: x=" + tempList.get(k).getX()+l + "y=" + tempList.get(k).getY()+m);
                                                }
                                            }
                                        }
                                    }
                                }
                                //rook moves
                                else if(this.board.getPiece(j, i).getPieceType() == PieceType.ROOK || (this.board.getPiece(j, i).getPieceType() == PieceType.QUEEN && (j==tempList.get(k).getX() || i==tempList.get(k).getY()))){
                                    if(diffX==0&&diffY<0){
                                        for(int m=-1; m>diffY; m--) {
                                            this.killerPath.add(new Coords(tempList.get(k).getX(), tempList.get(k).getY()+m));
                                            System.out.println("killerpath 1");
                                            System.out.println("killerPath Element: x=" + tempList.get(k).getX() + "y=" + tempList.get(k).getY()+m);

                                        }

                                    }
                                    else if(diffX==0&&diffY>0){
                                        for(int l=1; l<diffX; l++) {
                                            for(int m=1; m>diffY; m++) {
                                                this.killerPath.add(new Coords(tempList.get(k).getX(), tempList.get(k).getY()+m));
                                                System.out.println("killerpath 2");
                                                System.out.println("killerPath Element: x=" + tempList.get(k).getX()+l + "y=" + tempList.get(k).getY()+m);

                                            }
                                        }
                                    }
                                    else if(diffX<0&&diffY==0){
                                        for(int l=-1; l>diffX; l--) {
                                            this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()));
                                            System.out.println("killerpath 3");
                                            System.out.println("killerPath Element: x=" + (tempList.get(k).getX()+l) + "y=" + (tempList.get(k).getY()));
                                        }
                                    }
                                    else if(diffX>0&&diffY==0){
                                        for(int l=1; l<diffX; l++) {
                                            System.out.println("killerpath 4");
                                            this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()));
                                            System.out.println("killerPath Element: x=" + tempList.get(k).getX()+l + "y=" + tempList.get(k).getY());
                                        }
                                    }
                                }
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
        Coords saveCoordsForPiece = piece.getPosition();
        this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), new None(piece.getPosition()));
        //piece.Move(movePiece.getPosition());
        this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), piece);


        System.out.println("x=4, y=3 : " + this.board.getPiece(4,3).getPieceType());
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
                                System.out.println("CheckSelfCheck: (WHITE) list for piece: x= " + j + ", y=" + i);
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
                            System.out.println("selfcheck BLACK: Piece(j,i):" + this.board.getPiece(j,i).getPieceType() + "its templist: x= " + tempList.get(k).getX() + ", y= "+ tempList.get(k).getY() + " and piecetype: " + this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceType());
                            if (this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceType() == PieceType.KING && this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceColor() == PieceColor.BLACK){
                                this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), piece);
                                this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), savePiece);
                                System.out.println("CheckSelfCheck: (BLACK) list for piece: x= " + j + ", y=" + i);
                                System.out.println("CheckSelfCheck: King pos: x= " + tempList.get(k).getX() + ", y= "+ tempList.get(k).getY());
                                return false;
                            }
                        }
                    }
                }
            }
        }
        //piece.Move(saveCoordsForPiece);
        this.board.setPiece(saveCoordsForPiece.getX(),saveCoordsForPiece.getY(), piece);
        this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), savePiece);
        return true;
    }

    private boolean checkCheckMate(Piece piece){
        boolean kingListSizeIsNull = true;
        boolean checkMate = false;
        for(int i=1; i<9; i++) {
            for (int j = 1; j < 9; j++) {
                if (piece.getPieceColor() == PieceColor.WHITE) {
                    if (this.board.getPiece(j, i).getPieceColor() == PieceColor.BLACK) {
                        PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j,i), this.board);
                        for(int k=0; k<tempList.getList().size(); k++){
                            if(this.board.getPiece(j, i).getPieceType() != PieceType.KNIGHT && this.board.getPiece(j, i).getPieceType() != PieceType.PAWN && this.board.getPiece(j, i).getPieceType() != PieceType.KING){
                                for(int l=0; l<killerPath.size(); l++){
                                    if(tempList.get(k).getX() == killerPath.get(l).getX() && tempList.get(k).getY() == killerPath.get(l).getY()){
                                        System.out.println("finding interrupt 1");
                                        System.out.println("checking piece: " + this.board.getPiece(j,i));
                                        if(checkSelfCheck(this.board.getPiece(j,i),this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()))){
                                            System.out.println("checkmate checkselfcheck1");
                                            checkMate = true;
                                        }
                                    }
                                }
                            }
                            else if(this.board.getPiece(j, i).getPieceType() == PieceType.KNIGHT || this.board.getPiece(j, i).getPieceType() == PieceType.PAWN){
                                System.out.println("knight or pawn:");
                                System.out.println("finding interrupt 2");
                                System.out.println("checking piece: " + this.board.getPiece(j,i));
                                if(tempList.get(k).getX() == this.killer.getPosition().getX() && tempList.get(k).getY() == this.killer.getPosition().getY()){
                                    System.out.println("checkmate checkselfcheck2");
                                    checkMate = true;
                                }
                            }
                            else if(this.board.getPiece(j, i).getPieceType() == PieceType.KING){
                                PossibleMoves kingList = fixKingList(this.board.getPiece(j,i), new PossibleMoves(this.board.getPiece(j, i), this.board)) ;
                                System.out.println("checking piece: " + this.board.getPiece(j,i));
                                System.out.println("checkmate checkselfcheck3 IF KING: ¨¨¨¨: " + kingList.getList().size());
                                for(int c=0; c<kingList.getList().size(); c++){
                                    System.out.println("kinglist element c: " + c +  ", x: "+ kingList.get(c).getX() + ", y: " + kingList.get(c).getY());
                                    if(kingList.get(c).getX()!= -1){
                                      kingListSizeIsNull = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                else if (piece.getPieceColor() == PieceColor.BLACK) {
                    if (this.board.getPiece(j, i).getPieceColor() == PieceColor.WHITE) {
                        PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j,i), this.board);
                        for(int k=0; k<tempList.getList().size(); k++){
                            if(this.board.getPiece(j, i).getPieceType() != PieceType.KNIGHT && this.board.getPiece(j, i).getPieceType() != PieceType.PAWN && this.board.getPiece(j, i).getPieceType() != PieceType.KING){
                                for(int l=0; l<killerPath.size(); l++){
                                    if(tempList.get(k).getX() == killerPath.get(l).getX() && tempList.get(k).getY() == killerPath.get(l).getY()){
                                        System.out.println("finding interrupt 3");
                                        if(checkSelfCheck(this.board.getPiece(j,i),this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()))){
                                            System.out.println("checkmate checkselfcheck4");
                                            checkMate = true;
                                        }
                                    }
                                }
                            }
                            else if(this.board.getPiece(j, i).getPieceType() == PieceType.KNIGHT || this.board.getPiece(j, i).getPieceType() == PieceType.PAWN){
                                System.out.println("knight or pawn:");
                                System.out.println("finding interrupt 4");
                                if(tempList.get(k).getX() == this.killer.getPosition().getX() && tempList.get(k).getY() == this.killer.getPosition().getY()){
                                    System.out.println("checkmate checkselfcheck5");
                                    checkMate = true;
                                }
                            }
                            else if(this.board.getPiece(j, i).getPieceType() == PieceType.KING){
                                PossibleMoves kingList = fixKingList(this.board.getPiece(j, i), new PossibleMoves(this.board.getPiece(j, i), this.board));
                                System.out.println("checkmate checkselfcheck6 IF KING: " + kingList.getList().size());
                                for(int c=0; c<kingList.getList().size(); c++){
                                    System.out.println("kinglist element: "+ kingList.get(c).getX());
                                    if(kingList.get(c).getX() != -1){
                                        kingListSizeIsNull = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        System.out.println("kinglistsizeisnull: " + kingListSizeIsNull);
        if(kingListSizeIsNull && !checkMate){
            return true;
        }
        return false;
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

    private void pawnToQueen(Piece piece){
        //this.board.removePiece(piece.getPosition().getX(), piece.getPosition().getY());
        this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), new Queen(piece.getPieceColor(), piece.getPosition()));
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