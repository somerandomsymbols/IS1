package main;

import creatures.Ghost;
import creatures.GhostColor;
import creatures.Pacman;
import map.Level;
import map.TileType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.EnumMap;
import java.util.Map;

class jPanel2 extends JPanel implements KeyListener
{
    private static final BufferedImage imageTile;
    private static final BufferedImage imageFood;

    private static final BufferedImage[] imagePacman;
    private static final Map<GhostColor, BufferedImage[]> imageGhost;
    private static final BufferedImage[] imageEyes;
    private static final BufferedImage[] imageWall;

    /*private static final BufferedImage wallImage;
    private static final BufferedImage ghostImage;
    private static final BufferedImage pacmanImage;

    private static final BufferedImage imageGhostRed0;
    private static final BufferedImage imageGhostRed1;
    private static final BufferedImage imageGhostYellow0;
    private static final BufferedImage imageGhostYellow1;
    private static final BufferedImage imageGhostGreen0;
    private static final BufferedImage imageGhostGreen1;
    private static final BufferedImage imageGhostBlue0;
    private static final BufferedImage imageGhostBlue1;

    private static final BufferedImage imageEyesUp;
    private static final BufferedImage imageEyesDown;
    private static final BufferedImage imageEyesLeft;
    private static final BufferedImage imageEyesRight;*/

    private static int length = 35;

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Level level = Main.level;

        //g.drawString("asd", 20, 20);
        g.setColor(Color.black);
        g.drawRect(0, 0, level.getWidth() * length, level.getHeight() * length);

        for (int y = 0; y < level.getHeight(); ++y)
        {
            for (int x = 0; x < level.getWidth(); ++x)
            {
                g.drawImage(imageTile, x * length, y * length, length, length, null);

                BufferedImage image = null;
                switch (level.getTile(x, y).getType())
                {
                    case FOOD:
                        image = imageFood;
                        break;
                    case WALL:
                        byte code = 0b0000;

                        if (level.getTile(x - 1, y).getType() == TileType.WALL)
                            code |= 0b1000;

                        if (level.getTile(x, y - 1).getType() == TileType.WALL)
                            code |= 0b0100;

                        if (level.getTile(x + 1, y).getType() == TileType.WALL)
                            code |= 0b0010;

                        if (level.getTile(x, y + 1).getType() == TileType.WALL)
                            code |= 0b0001;

                        image = imageWall[code];
                        break;
                    default:
                        break;
                }

                g.drawImage(image, x * length, y * length, length, length, null);
            }
        }

        if (level.getPacman() != null)
            g.drawImage(imagePacman[(int) (System.currentTimeMillis() % 500 / 125)], level.getPacman().getPos().getX() * length, level.getPacman().getPos().getY() * length, length, length, null);

        for (Ghost ghost : level.getGhosts())
        {
            /*BufferedImage imageGhost = imageGhostRed0;
            BufferedImage imageEyes = imageEyesRight;

            switch (ghost.getColor())
            {
                case RED:
                    imageGhost = System.currentTimeMillis() % 250 < 125 ? imageGhostRed0 : imageGhostRed1;
                    break;
                case YELLOW:
                    imageGhost = System.currentTimeMillis() % 250 < 125 ? imageGhostYellow0 : imageGhostYellow1;
                    break;
                case GREEN:
                    imageGhost = System.currentTimeMillis() % 250 < 125 ? imageGhostGreen0 : imageGhostGreen1;
                    break;
                case BLUE:
                    imageGhost = System.currentTimeMillis() % 250 < 125 ? imageGhostBlue0 : imageGhostBlue1;
                    break;
            }

            switch (ghost.getDirection())
            {
                case 0:
                    imageEyes = imageEyesLeft;
                    break;
                case 1:
                    imageEyes = imageEyesUp;
                    break;
                case 2:
                    imageEyes = imageEyesRight;
                    break;
                case 3:
                    imageEyes = imageEyesDown;
                    break;
            }*/

            g.drawImage(imageGhost.get(ghost.getColor())[(int) (System.currentTimeMillis() % 250 / 125)], ghost.getPos().getX() * length, ghost.getPos().getY() * length, length, length, null);
            g.drawImage(imageEyes[ghost.getDirection()], ghost.getPos().getX() * length, ghost.getPos().getY() * length, length, length, null);
        }
    }

    static
    {
        try {
            imageTile = ImageIO.read(new File("textures/tile.png"));
            imageFood = ImageIO.read(new File("textures/food.png"));

            imagePacman = new BufferedImage[4];

            for (int i = 0; i < 3; ++i)
                imagePacman[i] = ImageIO.read(new File("textures/pacman_" + i + ".png"));

            imagePacman[3] = imagePacman[1];

            imageGhost = new EnumMap<>(GhostColor.class);

            imageGhost.put(GhostColor.RED, new BufferedImage[]
                    {
                            ImageIO.read(new File("textures/ghost_0_0.png")),
                            ImageIO.read(new File("textures/ghost_0_1.png"))
                    });

            imageGhost.put(GhostColor.YELLOW, new BufferedImage[]
                    {
                            ImageIO.read(new File("textures/ghost_1_0.png")),
                            ImageIO.read(new File("textures/ghost_1_1.png"))
                    });

            imageGhost.put(GhostColor.GREEN, new BufferedImage[]
                    {
                            ImageIO.read(new File("textures/ghost_2_0.png")),
                            ImageIO.read(new File("textures/ghost_2_1.png"))
                    });

            imageGhost.put(GhostColor.BLUE, new BufferedImage[]
                    {
                            ImageIO.read(new File("textures/ghost_3_0.png")),
                            ImageIO.read(new File("textures/ghost_3_1.png"))
                    });

            imageEyes = new BufferedImage[4];

            for (int i = 0; i < 4; ++i)
                imageEyes[i] = ImageIO.read(new File("textures/eyes_" + i + ".png"));

            imageWall = new BufferedImage[16];

            for (int i = 0b0000; i <= 0b1111; ++i)
                imageWall[i] = ImageIO.read(new File("textures/walls/wall" + String.format("%4s", Integer.toBinaryString(i)).replace(' ', '0') + ".png"));

            /*wallImage = ImageIO.read(new File("textures/wall.png"));
            ghostImage = ImageIO.read(new File("textures/ghost.png"));
            pacmanImage = ImageIO.read(new File("textures/pacman.png"));

            imageGhostRed0 = ImageIO.read(new File("textures/ghost_0_0.png"));
            imageGhostRed1 = ImageIO.read(new File("textures/ghost_0_1.png"));
            imageGhostYellow0 = ImageIO.read(new File("textures/ghost_1_0.png"));
            imageGhostYellow1 = ImageIO.read(new File("textures/ghost_1_1.png"));
            imageGhostGreen0 = ImageIO.read(new File("textures/ghost_2_0.png"));
            imageGhostGreen1 = ImageIO.read(new File("textures/ghost_2_1.png"));
            imageGhostBlue0 = ImageIO.read(new File("textures/ghost_3_0.png"));
            imageGhostBlue1 = ImageIO.read(new File("textures/ghost_3_1.png"));

            imageEyesUp = ImageIO.read(new File("textures/eyes_up.png"));
            imageEyesDown = ImageIO.read(new File("textures/eyes_down.png"));
            imageEyesLeft = ImageIO.read(new File("textures/eyes_left.png"));
            imageEyesRight = ImageIO.read(new File("textures/eyes_right.png"));*/
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        Level level = Main.level;

        switch (e.getKeyCode())
        {
            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT:
                Main.dx = 1;
                Main.dy = 0;
                break;
            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:
                Main.dx = 0;
                Main.dy = 1;
                break;
            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:
                Main.dx = -1;
                Main.dy = 0;
                break;
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:
                Main.dx = 0;
                Main.dy = -1;
                break;
            case KeyEvent.VK_R:
                if (level.getPacman() == null)
                    new Pacman(level, level.getTile(0, 0));
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}

public class Main
{
    public static Level level;
    public static int dx = 0, dy = 0;
    public static final int gameplayTicksPerSecond = 4;
    public static final int framesPerSecond = 30;
    public static void printMap(Level level)
    {
        System.out.println("Ghosts: " + level.getGhosts().size());
        for (int y = 0; y < level.getHeight(); ++y)
        {
            for (int x = 0; x < level.getWidth(); ++x)
                switch (level.getTile(x, y).getType())
                {
                    case FOOD:
                        System.out.print('+');
                        break;
                    case WALL:
                        System.out.print('=');
                        break;
                    case EMPTY:
                        System.out.print(' ');
                        break;
                    default:
                        break;
                }

            System.out.println();
        }
    }
    public static void main(String[] args) throws InterruptedException {
        JFrame frame = new JFrame("FrameDemo");

        //2. Optional: What happens when the frame closes?
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //3. Create components and put them in the frame.
        // ...create emptyLabel...
        //frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        jPanel2 panel = new jPanel2();

        panel.addKeyListener(panel);
        panel.setFocusable(true);

        // Adding buttons and textfield to panel
        // using add() method
        //panel.add(b);
        //panel.add(b1);
        //panel.add(b2);
        //panel.add(l);

        // setbackground of panel
        panel.setBackground(Color.lightGray);
        panel.setPreferredSize(new Dimension(800,500));

        // Adding panel to frame
        frame.add(panel);

        // Setting the size of frame
        //frame.setSize(300, 300);
        // 4. Size the frame.
        frame.pack();

        //5. Show it.
        frame.setVisible(true);

        level = Level.generate(1488);
        new Ghost(level, level.getTile(10, 10), GhostColor.RED);
        new Ghost(level, level.getTile(10, 12), GhostColor.YELLOW);
        new Ghost(level, level.getTile(12, 10), GhostColor.GREEN);
        new Ghost(level, level.getTile(12, 12), GhostColor.BLUE);
        new Pacman(level, level.getTile(level.getWidth() - 1, level.getHeight() - 1));

        //printMap(level);

        /*AStar aStar = new AStar(map);
        List<Tile> list = aStar.getPath(map.getTile(0,0), map.getTile(map.getWidth() - 1, map.getHeight() - 1));

        if (list != null)
        {
            System.out.println("not null");
            for (Tile tile : list)
                map.getTile(tile.getX(), tile.getY()).setType(TileType.FOOD);
            //System.out.println(tile.getX() + " " + tile.getY());
        }
        else
            System.out.println("null");*/

        //printMap(map);

        Thread graphicsThread = new Thread(() -> {
            long time = System.currentTimeMillis();

            while (true)
            {
                while (System.currentTimeMillis() < time + 1000 / framesPerSecond)
                    ;

                time = System.currentTimeMillis();
                frame.repaint();
            }
        });
        graphicsThread.start();

        Thread gameplayThread = new Thread(() -> {
            long time = System.currentTimeMillis();

            while (true)
            {
                while (System.currentTimeMillis() < time + 1000 / gameplayTicksPerSecond)
                    ;

                time = System.currentTimeMillis();

                for (Ghost ghost : level.getGhosts())
                    ghost.tick();

                if (level.getPacman() != null)
                    level.getPacman().move(dx, dy);
            }
        });
        gameplayThread.start();
    }
}
