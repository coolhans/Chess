import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameFrame extends JFrame implements BoardListener {
    public GameFrame(Board board){
        super("Chess");
        setFocusable(true);
        final GameComponent gameComponent = new GameComponent(board);
        this.setLayout(new BorderLayout());
        board.addBoardListener(gameComponent);
        this.add(gameComponent,BorderLayout.CENTER);

        JLabel scoreLabel = new JLabel("Player: ");
        this.add(scoreLabel,BorderLayout.NORTH);

        createMenu();
        this.pack();
        this.setVisible(true);
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
    public void boardChanged() {
        System.out.println("hej");
        repaint();

    }
}