import java.util.ArrayList;

public class Queen extends Piece {

    private Coords position = new Coords();
    private PieceColor color;
    private ArrayList<Coords> movesList;
    private Piece compPiece = new None();
    private Coords upLeft = new Coords(-1,-1);
    private Coords upRight = new Coords(-1,1);
    private Coords downRight = new Coords(1,1);
    private Coords downLeft = new Coords(1,-1);

    public Queen(PieceColor color, Coords position){
        this.color = color;
        this.position = position;
    }

    @Override
    public ArrayList<Coords> possibleMoves(){

        //ROOK MOVES
        //up
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()-i);
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()-i);
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
        }
        //down
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()+i);
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX(), position.getY()+i);
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
        }

        //left
        switch(color){
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()-i, position.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }

            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()-i, position.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(tempPiece.getPosition().getX(),tempPiece.getPosition().getY()));
                        break;
                    }
                    else{
                        break;
                    }
                }
        }

        //right
        switch(color) {
            case WHITE:
                for (int i = 1; i < 8; i++) {
                    Piece tempPiece = getBoard().getPiece(position.getX() + i, position.getY());
                    if (tempPiece == null) {
                        break;
                    } else if (tempPiece.getPieceColor() == PieceColor.NOCOLOR) {
                        movesList.add(new Coords(tempPiece.getPosition().getX(), tempPiece.getPosition().getY()));
                    } else if (tempPiece.getPieceColor() == PieceColor.BLACK) {
                        movesList.add(new Coords(tempPiece.getPosition().getX(), tempPiece.getPosition().getY()));
                        break;
                    } else {
                        break;
                    }
                }

            case BLACK:
                for (int i = 1; i < 8; i++) {
                    Piece tempPiece = getBoard().getPiece(position.getX() + i, position.getY());
                    if (tempPiece == null) {
                        break;
                    } else if (tempPiece.getPieceColor() == PieceColor.NOCOLOR) {
                        movesList.add(new Coords(tempPiece.getPosition().getX(), tempPiece.getPosition().getY()));
                    } else if (tempPiece.getPieceColor() == PieceColor.WHITE) {
                        movesList.add(new Coords(tempPiece.getPosition().getX(), tempPiece.getPosition().getY()));
                        break;
                    } else {
                        break;
                    }
                }

        }



        //Check available moves diagonal up right
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()+i*upRight.getX(), position.getY()+i*upRight.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                        break;
                    }
                    else{
                        break; //maybe check piececolor.white
                    }
                }
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()+i*upRight.getX(), position.getY()+i*upRight.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*upRight.getX(),position.getY()+i*upRight.getY()));
                        break;
                    }
                    else{
                        break; //maybe check piececolor.white
                    }
                }
        }
        //Check available moves diagonal down right
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()+i*downRight.getX(), position.getY()+i*downRight.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                        break;
                    }
                    else{
                        break; //maybe check piececolor.white
                    }
                }
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()+i*downRight.getX(), position.getY()+i*downRight.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*downRight.getX(),position.getY()+i*downRight.getY()));
                        break;
                    }
                    else{
                        break; //maybe check piececolor.white
                    }
                }
        }

        //Check available moves diagonal down left
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()+i*downLeft.getX(), position.getY()+i*downLeft.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                        break;
                    }
                    else{
                        break; //maybe check piececolor.white
                    }
                }
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()+i*downLeft.getX(), position.getY()+i*downLeft.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*downLeft.getX(),position.getY()+i*downLeft.getY()));
                        break;
                    }
                    else{
                        break; //maybe check piececolor.white
                    }
                }
        }


        //BISHOP MOVES
        //Check available moves diagonal up left
        switch(color) {
            case WHITE:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()+i*upLeft.getX(), position.getY()+i*upLeft.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.BLACK){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                        break;
                    }
                    else{
                        break; //maybe check piececolor.white
                    }
                }
            case BLACK:
                for(int i=1; i<8; i++){
                    Piece tempPiece = getBoard().getPiece(position.getX()+i*upLeft.getX(), position.getY()+i*upLeft.getY());
                    if(tempPiece == null){
                        break;
                    }
                    else if(tempPiece.getPieceColor() == PieceColor.NOCOLOR){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                    }
                    else if (tempPiece.getPieceColor() == PieceColor.WHITE){
                        movesList.add(new Coords(position.getX()+i*upLeft.getX(),position.getY()+i*upLeft.getY()));
                        break;
                    }
                    else{
                        break; //maybe check piececolor.white
                    }
                }
        }

        return movesList;
    }

    @Override
    public PieceType getPieceType(){
        return PieceType.QUEEN;
    }

    @Override
    public PieceColor getPieceColor(){
        return this.color;
    }

    @Override
    public void Move(Coords newCoords){
        getBoard().setPiece(position.getX(),position.getY(), new None());
        this.position.setX(newCoords.getX());
        this.position.setY(newCoords.getY());
    }

}