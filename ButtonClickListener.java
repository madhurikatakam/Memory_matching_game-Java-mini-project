import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ButtonClickListener implements ActionListener {
    private int x, y;
    private MemoryGame game;
    private static final int DELAY = 500;
    private static boolean firstSelection = true;
    private static JButton firstButton;
    private static JButton secondButton;
    private static String firstValue;
    private static int firstX, firstY;
    private static boolean awaitingSecond = false;

    public ButtonClickListener(int x, int y, MemoryGame game) {
        this.x = x;
        this.y = y;
        this.game = game;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (awaitingSecond) {
            return;
        }

        JButton button = (JButton) e.getSource();
        MemoryGameLogic gameLogic = game.getGameLogic();

        if (gameLogic.isRevealed(x, y)) {
            return;
        }

        String value = gameLogic.getCardValue(x, y);
        button.setText(value);
        gameLogic.revealCard(x, y);

        if (firstSelection) {
            firstButton = button;
            firstValue = value;
            firstX = x;
            firstY = y;
            firstSelection = false;
        } else {
            secondButton = button;
            awaitingSecond = true;

            if (firstValue.equals(value)) {
                firstButton.setEnabled(false);
                secondButton.setEnabled(false);
                game.updateStatusLabel("Matched!");
                game.pairMatched();
                awaitingSecond = false;
            } else {
                Timer timer = new Timer(DELAY, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        firstButton.setText("");
                        secondButton.setText("");
                        gameLogic.hideCard(firstX, firstY);
                        gameLogic.hideCard(x, y);
                        awaitingSecond = false;
                    }
                });
                timer.setRepeats(false);
                timer.start();
                game.updateStatusLabel("Try again!");
            }
            firstSelection = true;
        }
    }
}
