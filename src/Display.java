import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Display extends Canvas {

    private JFrame frame;
    private Dimension size;
    private String title;

    @Override
    public Dimension getSize() {
        return size;
    }

    public Display(String title, int width, int height) {
        this.frame = new JFrame();
        this.title=title;
        this.size = new Dimension(width, height);
        this.setPreferredSize(size);
        this.frame.setTitle(title);
        this.frame.add(this);
        this.frame.pack();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setVisible(true);
    }

    public void updateTitle(int fps){
        String fpsTitle=title+"    "+fps+"  FPS";
        this.frame.setTitle(fpsTitle);
    }

    public Graphics getGraphics() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return null;
        }
        return bs.getDrawGraphics();
    }
    public void showGraphics(Graphics g) {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }
        g.dispose();
        bs.show();
    }

}