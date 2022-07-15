

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    private BufferedImage img;
    private String[] edges;
    public List<Integer> up;
    public List<Integer> right;
    public List<Integer> left;
    public List<Integer> down;
    private int chance;

    public Tile(BufferedImage img, String[] edges, int chance) {
        this.img = img;
        this.edges = edges;
        this.up = new ArrayList<>();
        this.down = new ArrayList<>();
        this.left = new ArrayList<>();
        this.right = new ArrayList<>();
        this.chance = chance;
    }

    public int getChance() {
        return chance;
    }

    public BufferedImage getImg() {
        return img;
    }

    private boolean compareEdge(String a, String b) {
        String r = "";
        for (int i = b.length() - 1; i >= 0; i--) {
            r += b.charAt(i);
        }
        return a.equals(r);
    }

    public void analyze(List<Tile> tiles) {
        for (int i = 0; i < tiles.size(); i++) {
            Tile tile = tiles.get(i);

            // UP
            if (compareEdge(tile.edges[2], this.edges[0])) {
                this.up.add(i);
            }
            // RIGHT
            if (compareEdge(tile.edges[3], this.edges[1])) {
                this.right.add(i);
            }
            // DOWN
            if (compareEdge(tile.edges[0], this.edges[2])) {
                this.down.add(i);
            }
            // LEFT
            if (compareEdge(tile.edges[1], this.edges[3])) {
                this.left.add(i);
            }
        }
    }

    public void draw(Graphics d, int x, int y) {
        d.drawImage(this.img, x, y, null);
    }

    public Tile rotate(int times) {
        BufferedImage newImg = ImageUtil.rotateImage(this.img, times);

        String[] newEdges = new String[4];
        int len = this.edges.length;
        for (int i = 0; i < len; i++) {
            newEdges[i] = this.edges[(i - times + len) % len];
        }
        return new Tile(newImg, newEdges,this.chance);
    }


}
