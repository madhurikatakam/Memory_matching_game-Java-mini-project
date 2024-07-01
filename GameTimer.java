import javax.swing.SwingUtilities;

public class GameTimer extends Thread {
    private MemoryGame game;
    private boolean running = true;

    public GameTimer(MemoryGame game) {
        this.game = game;
    }

    @Override
    public void run() {
        int time = 0; 
        while (running) {
            try {
                Thread.sleep(1000); 
                time++;
                final int currentTime = time;
                SwingUtilities.invokeLater(() -> game.updateStatusLabel("Time: " + currentTime + " seconds"));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    public void stopTimer() {
        running = false;
        this.interrupt(); 
    }
}
