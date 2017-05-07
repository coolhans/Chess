import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

import static java.lang.Math.abs;

public class GameFrame extends JFrame implements MouseListener {
    //Logic fields
    private Board board;
    private int clickCounter = 0;
    private Piece savedSelectedPiece = new None();
    private Piece currentSelectedPiece = new None();
    private PossibleMoves list = new PossibleMoves();
    private PossibleMoves checkList = new PossibleMoves(); // The selected piece possible moves
    private Piece killer = new None();
    private java.util.List<Coords> killerPath = new ArrayList<Coords>();
    private boolean checkState = false;

    //Jframe fields
    private JLabel playerLabel;
    private JLabel checkLabel;
    private JLabel checkmateLabel;
    public static JLabel timeLabel = null;
    public static int timeCounter = 0;

    //Constructor of Game Frame
    public GameFrame(Board board){
        super("Chess");
        this.board = board;
        setFocusable(true);
        final GameComponent gameComponent = new GameComponent(board);
        this.setLayout(new BorderLayout());
        this.add(gameComponent,BorderLayout.CENTER);
	    createLabelPanel();
        createMenu();
        this.pack();
        this.setVisible(true);
        addMouseListener(this);
    }

    //Gui Label Panel customized
    private void createLabelPanel(){
        JPanel labelPanel = new JPanel(new BorderLayout());
        playerLabel = new JLabel("Player: White");
        checkLabel = new JLabel("Check: ");
	    checkmateLabel = new JLabel("");
        timeLabel = new JLabel("Time Elapsed: " + timeCounter);
        playerLabel.setPreferredSize(new Dimension(150, 100));

        //Set borders to labels
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

    //Simple exit option in file tab
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
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(file);
        this.setJMenuBar(menuBar);
    }

    //Update labels
    public void updateLabel() {
        if(this.board.getTurn()%2==0){
            playerLabel.setText("Player: White");
        }
        else if(this.board.getTurn()%2==1){
            playerLabel.setText("Player: Black");
        }
        else{
            playerLabel.setText("Player: Invalid");
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
        final int boardX = 600;
        final int boardY = 660;
        if (e.getX() < boardX && e.getY() < boardY){
            int x = e.getX();
            int y = e.getY();
            final int squareSize = 60;
            GameComponent.clickedX = (x / squareSize);
            GameComponent.clickedY = (y / squareSize) - 1;
            this.currentSelectedPiece = this.board.getPiece(GameComponent.clickedX, GameComponent.clickedY);
            this.list = new PossibleMoves();
            this.list = new PossibleMoves(this.currentSelectedPiece, this.board);

            //Remove non eligeble moves from Kings possible moves list
            if(currentSelectedPiece.getPieceType()==PieceType.KING){
                this.list = fixKingList(currentSelectedPiece, this.list);
            }

            //Decide correct players turn
            if(this.board.getTurn()%2==0){
                clickFunc(PieceColor.WHITE);
            }
            else if(this.board.getTurn()%2==1){
                clickFunc(PieceColor.BLACK);
            }
            else{
                clickFunc(PieceColor.WHITE);
            }

        }
        boardChanged();
    }

    private void clickFunc(PieceColor color){
        //Click on piece (not NONE nor OUTSIDE)
        if (currentSelectedPiece.getPieceType() != PieceType.NONE && currentSelectedPiece.getPieceType() != PieceType.OUTSIDE){
            //(A piece is previously selected and now we are clicking on a piece again)
            if(clickCounter==1){
                //Iterate checklist
                for(int i=0; i<checkList.getList().size(); i++){
                    //If next click is in checklist, then it's an OK move
                    if(checkList.get(i).getX() == currentSelectedPiece.getPosition().getX() && checkList.get(i).getY() == currentSelectedPiece.getPosition().getY()){
                        //Move
                        //Make sure that move doesnt check your own king
                        if(checkSelfCheck(savedSelectedPiece, currentSelectedPiece)){
                            this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                            this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                            savedSelectedPiece.move(currentSelectedPiece.getPosition());
                            //See if pawn moves across the board to switch it for a new queen
                            if(savedSelectedPiece.getPieceType()==PieceType.PAWN){
                                if(savedSelectedPiece.getPieceColor() == PieceColor.WHITE &&
                                   savedSelectedPiece.getPosition().getY() == 1 ||
                                   savedSelectedPiece.getPieceColor() == PieceColor.BLACK &&
                                   savedSelectedPiece.getPosition().getY() == 8){
                                    pawnToQueen(savedSelectedPiece);
                                }
                            }
                            //deselect piece
                            GameComponent.selectedPiece.selectPiece();
                            this.board.nextTurn();

                            //See if you are checking enemy king
                            if(checkEnemyCheck(savedSelectedPiece)){
                                GameComponent.killer = this.killer;

                                //See if you are check mating enemy
                                if(checkCheckMate(savedSelectedPiece)){
				                    checkmateLabel.setText("CHECKMATE, "+ color  +" WINS");
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

                        //Don't move if you are trying to move to a spot whre your own king would end up in check, reset
                        else if(!checkSelfCheck(savedSelectedPiece, currentSelectedPiece)){
                            clearPiece();
                            break;
                        }
                    }

                    //finished iterating through checklist
                    else if(i==checkList.getList().size()-1){
                        clearPiece();
                    }
                }
            }

            //First click and on a piece -> Selecting piece
            else if(clickCounter == 0 && currentSelectedPiece.getPieceColor() == color){
                //Press and select piece
                selectPiece();
            }
            //Reset any other scenario (when pressing on a piece)
            else{
                //this.clickCounter = 0;
                clearPiece();
            }
        }

        //click on empty boardspace
        else if (currentSelectedPiece.getPieceType() == PieceType.NONE){
            //clicking on an empty spot will not do anything
            if(clickCounter == 0){
                clearPiece();
            }
            //You have a piece selected and you want to move it to an empty spot
            else if (currentSelectedPiece.getPieceType() != PieceType.OUTSIDE && clickCounter == 1){
                //See if the empty spot is in the checklist
                for(int i=0; i<checkList.getList().size(); i++){
                    //If spot is in checklist, try to move in spot
                    if(checkList.get(i).getX() == currentSelectedPiece.getPosition().getX() && checkList.get(i).getY() == currentSelectedPiece.getPosition().getY()){
                        //If you can move to the spot without putting your own king at check, move to empty spot
                        if(checkSelfCheck(savedSelectedPiece, currentSelectedPiece)){
                            this.board.setPiece(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY(), savedSelectedPiece);
                            this.board.removePiece(savedSelectedPiece.getPosition().getX(), savedSelectedPiece.getPosition().getY());
                            savedSelectedPiece.move(currentSelectedPiece.getPosition());

                            //See if pawn moves across the board to switch it for a new queen
                            if(savedSelectedPiece.getPieceType()==PieceType.PAWN){
                                if(savedSelectedPiece.getPieceColor() == PieceColor.WHITE &&
                                   savedSelectedPiece.getPosition().getY() == 1 ||
                                   savedSelectedPiece.getPieceColor() == PieceColor.BLACK &&
                                   savedSelectedPiece.getPosition().getY() == 8){
                                    pawnToQueen(savedSelectedPiece);
                                }
                            }
                            GameComponent.selectedPiece.selectPiece();
                            this.board.nextTurn();

                            //See if you are checking enemy king
                            if(checkEnemyCheck(savedSelectedPiece)){
                                GameComponent.killer = this.killer;
                                //See if you are check mating the enemy
                                if(checkCheckMate(savedSelectedPiece)){
				                    checkmateLabel.setText("CHECKMATE, "+ color  +" WINS");
                                }
                                this.killer = new None();
                            }

                            //reset check logic if not check
                            else if(!checkEnemyCheck(savedSelectedPiece)){
                                this.killer = new None();
                                GameComponent.killer = this.killer;
                                this.checkState = false;
                            }
                            updateLabel();
                            clearPiece();
                            break;
                        }
                        //Don't move if you are putting your own king at check if you make that move
                        else if(!checkSelfCheck(savedSelectedPiece, currentSelectedPiece)){
                            clearPiece();
                            break;
                        }
                    }
                    //Finished iterating through checklist
                    else if(i==checkList.getList().size()-1){
                        clearPiece();
                    }
                }
            }
        }
        //Avoid bugs
        else{
            clearPiece();
        }
    }

    //A method to remove the take away the moves that arent available because they would check the king
    private PossibleMoves fixKingList(Piece piece, PossibleMoves kingList){
        PossibleMoves newKingList = kingList;
        List<Coords> removedCoords = new ArrayList<Coords>();
        //Iterate through board
        for(int i=1; i<9; i++) {
            for (int j = 1; j < 9; j++) {
                //If you are white
                if (piece.getPieceColor() == PieceColor.WHITE) {
                    //Look at Black pieces
                    if (this.board.getPiece(j, i).getPieceColor() == PieceColor.BLACK){
                        //Case of any piece but pawn
                        if(this.board.getPiece(j, i).getPieceType() != PieceType.PAWN){
                            PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                            //Iterate through the tempList and eventually returning newKinglist
                            for(int kingItr=0; kingItr<newKingList.size(); kingItr++){
                                for(int l=0; l<tempList.size(); l++){
                                    //Remove the spot from the kings existing checklist IF the templist has a spot on any of the ones in checklist AND the removedCoords not already has it
                                    if(tempList.get(l).getX()==newKingList.get(kingItr).getX() && tempList.get(l).getY()==newKingList.get(kingItr).getY() && !removedCoords.contains(newKingList.get(kingItr))){
                                        removedCoords.add(newKingList.get(kingItr));
                                        newKingList.remove(kingItr);
                                        //To not get iteration error
                                        if(kingItr!=0){
                                            kingItr -= 1;
                                        }
                                        else{
                                            kingItr=0;
                                        }
                                    }
                                }
                            }
                        }
                        //Check with enemy pawns
                        else{
                            //Iterate through the update kingslist
                            for(int kingItr=0; kingItr<newKingList.size(); kingItr++){
                                //See if that pawns potential moves collids with any of the spots in kingslist
                                for (int p = -1; p < 2; p++){
                                    if(p!=0 && this.board.getPiece(this.board.getPiece(j, i).getPosition().getX()+p, this.board.getPiece(j, i).getPosition().getY()+1).getPieceType() != PieceType.OUTSIDE){
                                        if(newKingList.get(kingItr).getX()== this.board.getPiece(j, i).getPosition().getX()+p && newKingList.get(kingItr).getY()== this.board.getPiece(j, i).getPosition().getY()+1 && !removedCoords.contains(newKingList.get(kingItr))){
                                            removedCoords.add(newKingList.get(kingItr));
                                            newKingList.remove(kingItr);
                                            //To not get iteration error
                                            if(kingItr!=0){
                                                kingItr -= 1;
                                            }
                                            else{
                                                kingItr=0;
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }
                }
                //Same but for black
                else if (piece.getPieceColor() == PieceColor.BLACK) {
                    if (this.board.getPiece(j, i).getPieceColor() == PieceColor.WHITE) {
                        if(this.board.getPiece(j, i).getPieceType() != PieceType.PAWN){
                            PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                            for(int kingItr=0; kingItr<newKingList.size(); kingItr++){
                                for(int l=0; l<tempList.size(); l++){
                                    if(tempList.get(l).getX()==newKingList.get(kingItr).getX() && tempList.get(l).getY()==newKingList.get(kingItr).getY() && !removedCoords.contains(newKingList.get(kingItr))){
                                        removedCoords.add(newKingList.get(kingItr));
                                        newKingList.remove(kingItr);
                                        if(kingItr!=0){
                                            kingItr -= 1;
                                        }
                                        else{
                                            kingItr=0;
                                        }
                                    }
                                }
                            }
                        }
                        //Check with enemy pawns
                        else{
                            for(int kingItr=0; kingItr<newKingList.size(); kingItr++){
                                for (int p = -1; p < 2; p++){
                                    if(p!=0 && this.board.getPiece(this.board.getPiece(j, i).getPosition().getX()+p, this.board.getPiece(j, i).getPosition().getY()+1).getPieceType() != PieceType.OUTSIDE){
                                        if(newKingList.get(kingItr).getX()== this.board.getPiece(j, i).getPosition().getX()+p && newKingList.get(kingItr).getY()== this.board.getPiece(j, i).getPosition().getY()-1 && !removedCoords.contains(newKingList.get(kingItr))){
                                            removedCoords.add(newKingList.get(kingItr));
                                            newKingList.remove(kingItr);
                                            if(kingItr!=0){
                                                kingItr -= 1;
                                            }
                                            else{
                                                kingItr=0;
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

    //A generic method to select a piece
    private void selectPiece(){
        this.clickCounter++;
        this.savedSelectedPiece = currentSelectedPiece;
        savedSelectedPiece.selectPiece();
        GameComponent.selectedPiece = savedSelectedPiece;
        GameComponent.list = this.list;
        this.checkList = new PossibleMoves(this.savedSelectedPiece, this.board);
        //If you are trying to select a piece that doesnt have anything to do,start over and deselect
        if (checkList.size() == 0){
            clearPiece();
        }
    }

    //See if you will check the enemy king (this method is always called in a move scenario, so the piece paraameter here is the one that is checking the ememy)
    private boolean checkEnemyCheck(Piece piece){
        int diffX, diffY; //Difference in X,Y axis
        boolean returnValue = false;
        //Iterate through board
        for(int i=1; i<9; i++) {
            for(int j=1; j<9; j++) {
                //Find all same colored pieces as the one that is moving
                if(this.board.getPiece(j,i).getPieceColor() == piece.getPieceColor()){
                    PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j, i), this.board);
                    //Iterate through every same colored piece
                    for(int k=0; k<tempList.size(); k++){
                        //If they have a King, that means they this move will result in a check. (They will never have a king of their own color)
                        if(this.board.getPiece(tempList.get(k).getX(), tempList.get(k).getY()).getPieceType() == PieceType.KING){
                            this.checkState = true;
                            returnValue = true;
                            this.killer = this.board.getPiece(j,i);
                            //If current iterated piece is any piece but knight or pawn we can start finding the killer path
                            if(this.board.getPiece(j, i).getPieceType() != PieceType.KNIGHT && this.board.getPiece(j, i).getPieceType() != PieceType.PAWN){
                                diffX = j-tempList.get(k).getX();
                                diffY = i-tempList.get(k).getY();

                                //Bishop or Queen, make sure its diagnoally positioned with King
                                if(this.board.getPiece(j, i).getPieceType() == PieceType.BISHOP || (this.board.getPiece(j, i).getPieceType() == PieceType.QUEEN && (j!=tempList.get(k).getX() || i!=tempList.get(k).getY()))){
                                    //Depending of where the bishop or queen is positioned relative to the king, iteration is done differently
                                    if(diffX<0&&diffY<0){
                                        for(int l=-1; l>diffX; l--) {
                                            for(int m=-1; m>diffY; m--) {
                                                //As for the killer path, it will always be the l and m where they are the same
                                                if(abs(l) == abs(m)){
                                                    this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()+m));
                                                }
                                            }
                                        }
                                    }
                                    else if(diffX>0&&diffY<0){
                                        for(int l=1; l<diffX; l++) {
                                            for(int m=-1; m>diffY; m--) {
                                                if(abs(l) == abs(m)){
                                                    this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()+m));
                                                }
                                            }
                                        }
                                    }
                                    else if(diffX<0&&diffY>0){
                                        for(int l=-1; l>diffX; l--) {
                                            for(int m=1; m<diffY; m++) {
                                                if(abs(l) == abs(m)){
                                                    this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()+m));
                                                }
                                            }
                                        }
                                    }
                                    else if(diffX>0&&diffY>0){
                                        for(int l=1; l<diffX; l++) {
                                            for(int m=1; m<diffY; m++) {
                                                if(abs(l) == abs(m)){
                                                    this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()+m));
                                                }
                                            }
                                        }
                                    }
                                }

                                //Rook or Queen, make sure they are alligned with the king
                                else if(this.board.getPiece(j, i).getPieceType() == PieceType.ROOK || (this.board.getPiece(j, i).getPieceType() == PieceType.QUEEN && (j==tempList.get(k).getX() || i==tempList.get(k).getY()))){
                                    //Here the killerpath logic is accordingly
                                    if(diffX==0&&diffY<0){
                                        for(int m=-1; m>diffY; m--) {
                                            this.killerPath.add(new Coords(tempList.get(k).getX(), tempList.get(k).getY()+m));
                                        }

                                    }
                                    else if(diffX==0&&diffY>0){
                                        for(int l=1; l<diffX; l++) {
                                            for(int m=1; m>diffY; m++) {
                                                this.killerPath.add(new Coords(tempList.get(k).getX(), tempList.get(k).getY()+m));
                                            }
                                        }
                                    }
                                    else if(diffX<0&&diffY==0){
                                        for(int l=-1; l>diffX; l--) {
                                            this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()));
                                        }
                                    }
                                    else if(diffX>0&&diffY==0){
                                        for(int l=1; l<diffX; l++) {
                                            this.killerPath.add(new Coords(tempList.get(k).getX()+l, tempList.get(k).getY()));
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

    //See if the attempted move you are going to make is available or not. If it isn't available you can't move to it
    private boolean checkSelfCheck(Piece piece, Piece movePiece){
        //Here we want temporarily change the board with the attempted move and see if the resulting
        //board wouuld be a check on your own king. So we temporarily save the piece we are overriding to back later
        //Piece is the piece trying to move, movePiece is the piece on the spot piece is trying to move to (sometimes it's empty)
        Piece savePiece = this.board.getPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY());
        Coords saveCoordsForPiece = piece.getPosition();
        this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), new None(piece.getPosition()));
        this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), piece);

        //Iterate through board
        for(int i=1; i<9; i++){
            for(int j=1; j<9; j++){
                //check oposite colors relatively to piece (WHITE)
                if(piece.getPieceColor() == PieceColor.WHITE){
                    if(this.board.getPiece(j,i).getPieceColor() == PieceColor.BLACK){
                        PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j,i), this.board);
                        for(int k=0; k<tempList.getList().size(); k++){
                            //Does any of the oposite color pieces have your own king in their movelist after this potential move was made? If so, that move is not possible to make
                            if (this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceType() == PieceType.KING  && this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceColor() == PieceColor.WHITE){
                                this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), piece);
                                this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), savePiece);
                                return false;
                            }
                        }
                    }
                }
                //Same but for Black
                else if(piece.getPieceColor() == PieceColor.BLACK){
                    if(this.board.getPiece(j,i).getPieceColor() == PieceColor.WHITE){
                        PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j,i), this.board);
                        for(int k=0; k<tempList.getList().size(); k++){
                            if (this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceType() == PieceType.KING && this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()).getPieceColor() == PieceColor.BLACK){
                                this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), piece);
                                this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), savePiece);
                                return false;
                            }
                        }
                    }
                }
            }
        }

        //Return to old boardstate
        this.board.setPiece(saveCoordsForPiece.getX(),saveCoordsForPiece.getY(), piece);
        this.board.setPiece(movePiece.getPosition().getX(), movePiece.getPosition().getY(), savePiece);
        return true;
    }

    //See if you have won the game, if this returns true game is over and you the one who just moved won the game.
    private boolean checkCheckMate(Piece piece){
        boolean kingListSizeIsNull = true;
        boolean checkMate = false;
        //Iterate through board
        for(int i=1; i<9; i++) {
            for (int j = 1; j < 9; j++) {
                //Look at oposite colored pieces relatively to piece
                if (piece.getPieceColor() == PieceColor.WHITE) {
                    if (this.board.getPiece(j, i).getPieceColor() == PieceColor.BLACK) {
                        PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j,i), this.board);
                        for(int k=0; k<tempList.getList().size(); k++){
                            //If any piece that isn't knight or pawn, check if they have a spot in their possible move to counter the check against their own king
                            if(this.board.getPiece(j, i).getPieceType() != PieceType.KNIGHT && this.board.getPiece(j, i).getPieceType() != PieceType.PAWN && this.board.getPiece(j, i).getPieceType() != PieceType.KING){
                                for(int l=0; l<killerPath.size(); l++){
                                    if(tempList.get(k).getX() == killerPath.get(l).getX() && tempList.get(k).getY() == killerPath.get(l).getY()){
                                        //Make sure that move doesn´t put your own king at check
                                        if(checkSelfCheck(this.board.getPiece(j,i),this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()))){
                                            checkMate = true;
                                        }
                                    }
                                }
                            }
                            //If piece is knight or pawn, see if it can interupt the check mate
                            else if(this.board.getPiece(j, i).getPieceType() == PieceType.KNIGHT || this.board.getPiece(j, i).getPieceType() == PieceType.PAWN){
                                if(tempList.get(k).getX() == this.killer.getPosition().getX() && tempList.get(k).getY() == this.killer.getPosition().getY()){
                                    checkMate = true;
                                }
                            }
                            //Eventually the enemy king piece will be found in the iteration, in this iteration we want to make sure the king has no where to move
                            else if(this.board.getPiece(j, i).getPieceType() == PieceType.KING){
                                PossibleMoves kingList = fixKingList(this.board.getPiece(j,i), new PossibleMoves(this.board.getPiece(j, i), this.board)) ;
                                //Since the size isn't changed we have to be sure that for all index in kingslist, they have to be (-1,-1)
                                for(int c=0; c<kingList.getList().size(); c++){
                                    if(kingList.get(c).getX()!= -1){
                                      kingListSizeIsNull = false;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
                //Same but for black
                else if (piece.getPieceColor() == PieceColor.BLACK) {
                    if (this.board.getPiece(j, i).getPieceColor() == PieceColor.WHITE) {
                        PossibleMoves tempList = new PossibleMoves(this.board.getPiece(j,i), this.board);
                        for(int k=0; k<tempList.getList().size(); k++){
                            if(this.board.getPiece(j, i).getPieceType() != PieceType.KNIGHT && this.board.getPiece(j, i).getPieceType() != PieceType.PAWN && this.board.getPiece(j, i).getPieceType() != PieceType.KING){
                                for(int l=0; l<killerPath.size(); l++){
                                    if(tempList.get(k).getX() == killerPath.get(l).getX() && tempList.get(k).getY() == killerPath.get(l).getY()){
                                        if(checkSelfCheck(this.board.getPiece(j,i),this.board.getPiece(tempList.get(k).getX(),tempList.get(k).getY()))){
                                            checkMate = true;
                                        }
                                    }
                                }
                            }
                            else if(this.board.getPiece(j, i).getPieceType() == PieceType.KNIGHT || this.board.getPiece(j, i).getPieceType() == PieceType.PAWN){
                                if(tempList.get(k).getX() == this.killer.getPosition().getX() && tempList.get(k).getY() == this.killer.getPosition().getY()){
                                    checkMate = true;
                                }
                            }
                            else if(this.board.getPiece(j, i).getPieceType() == PieceType.KING){
                                PossibleMoves kingList = fixKingList(this.board.getPiece(j, i), new PossibleMoves(this.board.getPiece(j, i), this.board));
                                for(int c=0; c<kingList.getList().size(); c++){
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
        //Return true if checkMate==false AND kinglistsizeisnull == 0
        if(kingListSizeIsNull && !checkMate){
            return true;
        }
        return false;
    }

    //A generic method to clear the state of what piece is selected
    private void clearPiece(){
        this.savedSelectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
        GameComponent.selectedPiece = new None(new Coords(currentSelectedPiece.getPosition().getX(), currentSelectedPiece.getPosition().getY()));
        this.clickCounter = 0;
        this.list = new PossibleMoves();
        GameComponent.list = this.list;
        this.checkList = new PossibleMoves();
    }

    private void pawnToQueen(Piece piece){
        this.board.removePiece(piece.getPosition().getX(), piece.getPosition().getY());
        this.board.setPiece(piece.getPosition().getX(), piece.getPosition().getY(), new Queen(piece.getPieceColor(), piece.getPosition()));
    }

    @Override
    public void mouseEntered(MouseEvent e){
    }
    @Override
    public void mouseExited(MouseEvent e){
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseReleased(MouseEvent e){
    }

    public void boardChanged() {
        repaint();

    }

}