import javax.swing.*;
import java.awt.event.ActionEvent;

public class Main {
    public static void main(String[] args) {
        final Board board = new Board(10,10);
        final GameFrame frame = new GameFrame(board);
        frame.setSize(800,720);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        final Action doOneStep = new AbstractAction(){
            public void actionPerformed(ActionEvent e){
                board.tick();
            }
        };

        final Timer clockTimer = new Timer (1000, doOneStep);
        clockTimer.setCoalesce(true);
        clockTimer.start();
    }
}
