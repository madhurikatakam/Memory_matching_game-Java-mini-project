import javax.swing.*;
import java.awt.*;

public class MemoryGame extends JFrame {
    private JPanel mainPanel;
    private JButton[][] buttons;
    private int gridSize = 4;
    private MemoryGameLogic gameLogic;
    private JLabel statusLabel;
    private JButton startButton;
    private GameTimer gameTimer;
    private JButton resetButton;
    private int pairsMatched = 0;
    private int totalPairs;

    public MemoryGame() {
        setTitle("Memory Matching Game");
        setSize(800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        gameLogic = new MemoryGameLogic(gridSize);
        totalPairs = gridSize * gridSize / 2;
        initUI();
    }

    private void initUI() {
        mainPanel = new JPanel(new GridLayout(gridSize, gridSize));
        mainPanel.setBackground(new Color(255, 255, 200)); 
        buttons = new JButton[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {    
                buttons[i][j] = new JButton();
                buttons[i][j].setFont(new Font("Arial", Font.PLAIN, 24));
                buttons[i][j].setPreferredSize(new Dimension(40, 40));
                buttons[i][j].addActionListener(new ButtonClickListener(i, j, this));
                mainPanel.add(buttons[i][j]);
            }
        }

        JLabel welcomeLabel = new JLabel("Welcome to the Memory Matching Game!", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));

        statusLabel = new JLabel("Click 'Start Game' to begin!", JLabel.CENTER);
        statusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        startButton = new JButton("Start Game");
        startButton.setFont(new Font(startButton.getFont().getName(), Font.BOLD, 18));
        startButton.setPreferredSize(new Dimension(150, 50));
        startButton.addActionListener(e -> resetGame());

        resetButton = new JButton("Reset");
        resetButton.setFont(new Font(resetButton.getFont().getName(), Font.BOLD, 18));
        resetButton.setPreferredSize(new Dimension(150, 50));
        resetButton.addActionListener(e -> resetGame());

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(welcomeLabel, BorderLayout.NORTH);
        topPanel.add(statusLabel, BorderLayout.SOUTH);

        JPanel bottomPanel = new JPanel(new FlowLayout());
        bottomPanel.add(startButton);
        bottomPanel.add(resetButton);

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public JButton[][] getButtons() {
        return buttons;
    }

    public MemoryGameLogic getGameLogic() {
        return gameLogic;
    }

    public void updateStatusLabel(String text) {
        statusLabel.setText(text);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MemoryGame game = new MemoryGame();
            game.setVisible(true);
        });
    }

    private void resetGame() {
        gameLogic = new MemoryGameLogic(gridSize);
        pairsMatched = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                buttons[i][j].setText("");
                buttons[i][j].setEnabled(true);
            }
        }
        updateStatusLabel("Game started! Match the pairs.");
        if (gameTimer != null) {
            gameTimer.stopTimer();
        }
        gameTimer = new GameTimer(this);
        gameTimer.start();
    }

    public void pairMatched() {
        pairsMatched++;
        if (pairsMatched >= totalPairs) {
            updateStatusLabel("Congratulations! You've matched all pairs!");
            if (gameTimer != null) {
                gameTimer.stopTimer();
            }
           
        }
        
    }

    
}