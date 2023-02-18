package main;

import creatures.Ghost;
import map.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class jPanel2 extends JPanel
{
    private static final BufferedImage ghostImage;
    private static int length = 50;

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        Map map = Main.map;

        //g.drawString("asd", 20, 20);
        g.setColor(Color.black);
        g.drawRect(0, 0, map.getWidth() * length, map.getHeight() * length);

        for (int y = 0; y < map.getHeight(); ++y)
        {
            for (int x = 0; x < map.getWidth(); ++x)
            {
                switch (map.getTile(x, y).getType())
                {
                    case EMPTY:
                        g.setColor(Color.lightGray);
                        break;
                    case FOOD:
                        g.setColor(Color.yellow);
                        break;
                    case WALL:
                        g.setColor(Color.blue);
                        break;
                    default:
                        break;
                }
                g.fillRect(x * length, y * length, length, length);
            }
        }

        for (Ghost ghost : map.getGhosts())
        {
            g.drawImage(ghostImage, ghost.getPos().getX() * length, ghost.getPos().getY() * length, length, length, null);
        }
    }

    static
    {
        try {
            ghostImage = ImageIO.read(new File("ghost.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

public class Main
{
    public static Map map;
    public static void printMap(Map map)
    {
        System.out.println("Ghosts: " + map.getGhosts().size());
        for (int y = 0; y < map.getHeight(); ++y)
        {
            for (int x = 0; x < map.getWidth(); ++x)
                switch (map.getTile(x, y).getType())
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
        JPanel panel = new jPanel2();

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

        map = Map.getRandom();
        new Ghost(map, map.getTile(0, 0));

        printMap(map);

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

        while (true)
        {
            for (Ghost ghost : map.getGhosts())
            {
                ghost.tick();
                /*System.out.println(ghost.getPos().getX() + " " + ghost.getPos().getY());

                if (ghost.getTarget() != null)
                    System.out.println(ghost.getTarget().getX() + " " + ghost.getTarget().getY());
                else
                    System.out.println("null null");*/
            }

            Thread.sleep(1000/2);
            //Scanner sc = new Scanner(System.in);
            //sc.nextLine();
            frame.repaint();
            //++Main.t;
        }
    }
}
