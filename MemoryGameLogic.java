import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class MemoryGameLogic {
    private String[][] board;
    private boolean[][] revealed;
    private int gridSize;

    public MemoryGameLogic(int size) {
        this.gridSize = size;
        board = new String[size][size];
        revealed = new boolean[size][size];
        initializeBoard();
    }

    public void initializeBoard() {
        List<String> values = new ArrayList<>();
        for (int i = 0; i < (gridSize * gridSize) / 2; i++) {
            values.add(String.valueOf(i));
            values.add(String.valueOf(i));
        }
        Collections.shuffle(values);

        int index = 0;
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                board[i][j] = values.get(index++);
                revealed[i][j] = false;
            }
        }
    }

    public void revealCard(int x, int y) {
        revealed[x][y] = true;
    }

    public void hideCard(int x, int y) {
        revealed[x][y] = false;
    }

    public String getCardValue(int x, int y) {
        return board[x][y];
    }

    public boolean isRevealed(int x, int y) {
        return revealed[x][y];
    }
}
