import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class PlaneGame extends JFrame implements KeyListener, ActionListener {

    private static final int FRAME_WIDTH = 1500;
    private static final int FRAME_HEIGHT = 800;
    private static final int PLANE_WIDTH = 300;
    private static final int PLANE_HEIGHT = 150;
    private static final int BULLET_RADIUS = 12;
    private static final int PLANE_SPEED = 5;
    private static final int BULLET_SPEED = 10;

    private int player1Y = FRAME_HEIGHT / 2 - PLANE_HEIGHT / 2;
    private int player2Y = FRAME_HEIGHT / 2 - PLANE_HEIGHT / 2;
    private int player1Life = 5;
    private int player2Life = 5;
    private ArrayList<Point> player1Bullets = new ArrayList<>();
    private ArrayList<Point> player2Bullets = new ArrayList<>();
    private ImageIcon bg = new ImageIcon("Images\\k1.jpg");
    private ImageIcon planeIcon = new ImageIcon("Images\\p1.gif");
    private ImageIcon planeIcon2 = new ImageIcon("Images\\p2.gif");
    private GamePanel gamePanel;
    Timer timer;
    Graphics g;

    public PlaneGame() {
        setTitle("AIRCRAFT BATTLEGROUND....");
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        addKeyListener(this);
        gamePanel = new GamePanel();
        add(gamePanel);
        Timer timer = new Timer(50, this);
        timer.start();
    }

    private class GamePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(bg.getImage(), 0, 0, FRAME_WIDTH, FRAME_HEIGHT, null);

            g.drawImage(planeIcon.getImage(), 50, player1Y, PLANE_WIDTH, PLANE_HEIGHT, null);
            g.drawImage(planeIcon2.getImage(), FRAME_WIDTH - 50 - PLANE_WIDTH, player2Y, PLANE_WIDTH, PLANE_HEIGHT, null);
            Font font = new Font("Arial", Font.BOLD, 20); // Change font name, style, and size as needed
            g.setFont(font);
            g.setColor(Color.BLUE);
            g.drawString("Player 1Life: " + player1Life, 20, 20);
            g.drawString("Player 2 Life: " + player2Life, FRAME_WIDTH - 250, 20);

            g.setColor(Color.RED);
            for (Point bullet : player1Bullets) {
                g.fillOval(bullet.x - BULLET_RADIUS, bullet.y - BULLET_RADIUS, 2 * BULLET_RADIUS, 2 * BULLET_RADIUS);
            }
            for (Point bullet : player2Bullets) {
                g.fillOval(bullet.x - BULLET_RADIUS, bullet.y - BULLET_RADIUS, 2 * BULLET_RADIUS, 2 * BULLET_RADIUS);
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_UP:
                player1Y = Math.max(player1Y - PLANE_SPEED, 0);
                break;
            case KeyEvent.VK_DOWN:
                player1Y = Math.min(player1Y + PLANE_SPEED, FRAME_HEIGHT - PLANE_HEIGHT);
                break;
            case KeyEvent.VK_SPACE:
                player1Bullets.add(new Point(100 + PLANE_WIDTH, player1Y + PLANE_HEIGHT / 2));
                break;
            case KeyEvent.VK_U:
                player2Y = Math.max(player2Y - PLANE_SPEED, 0);
                break;
            case KeyEvent.VK_D:
                player2Y = Math.min(player2Y + PLANE_SPEED, FRAME_HEIGHT - PLANE_HEIGHT);
                break;
            case KeyEvent.VK_S:
                player2Bullets.add(new Point(FRAME_WIDTH - 100 - PLANE_WIDTH, player2Y + PLANE_HEIGHT / 2));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        updateBullets(player1Bullets, BULLET_SPEED, true);
        updateBullets(player2Bullets, -BULLET_SPEED, false);
        checkGameOver(g);
        gamePanel.repaint();
    }

    private void updateBullets(ArrayList<Point> bullets, int speed, boolean isPlayer1) {
        for (int i = bullets.size() - 1; i >= 0; i--) {
            Point bullet = bullets.get(i);
            bullet.x += speed;
            if (isPlayer1) {
                if (bullet.x >= FRAME_WIDTH - 50 - PLANE_WIDTH && bullet.y >= player2Y && bullet.y <= player2Y + PLANE_HEIGHT) {
                    player2Life--;
                    bullets.remove(i);
                } else if (bullet.x >= FRAME_WIDTH) {
                    bullets.remove(i);
                }
            } else {
                if (bullet.x <= 50 + PLANE_WIDTH && bullet.y >= player1Y && bullet.y <= player1Y + PLANE_HEIGHT) {
                    player1Life--;
                    bullets.remove(i);
                } else if (bullet.x <= 0) {
                    bullets.remove(i);
                }
            }
        }
    }

    private void checkGameOver(Graphics pen) {
        if (player1Life <= 0) {
        
            JOptionPane.showMessageDialog(this, "Player 2 Wins!!!");
            System.exit(0);
        } else if (player2Life <= 0) {
        
            JOptionPane.showMessageDialog(this, "Player 1 Wins!!!");
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        PlaneGame game = new PlaneGame();
        game.setVisible(true);
    }
}