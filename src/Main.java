
import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int width = -1;
        int height = -1;
        int dim = -1;
        for (int i = 0; i < args.length; i++) {
            switch (i) {
                case 0:
                    dim = Integer.parseInt(args[i]);
                    break;
                case 1:
                    width = Integer.parseInt(args[i]);
                    break;
                case 2:
                    height = Integer.parseInt(args[i]);
                    break;
                default:
                    System.out.println("You put too much arguments!");
                    break;
            }
            if (i > 3) {
                break;
            }
        }
        if (dim == -1) {
            dim = 30;
        }
        if (width == -1) {
            width = 800;
        }
        if (height == -1) {
            height = 800;
        }
        Display gUI = new Display("Random Map", width, height);
        List<Tile> tiles = new ArrayList<>();
        Dimension tilesSize = new Dimension(width / dim, height / dim);
        try {
            tiles = createElectricTiles(tilesSize);
        } catch (IOException e) {
            System.exit(1);
        }


        long startTime = System.currentTimeMillis();

        ImagMap randMap = new ImagMap(gUI, tiles, width, height, dim);
        Cell[][] map = randMap.createMap();

        long endTime = System.currentTimeMillis();
        long workTime = (endTime - startTime) / 1000;
        System.out.println("Running time is : " + workTime+" seconds.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Do you Want to save the Map? (Y/N)");
        String input = scanner.nextLine();
        if (input.equals("Y")) {
            System.out.println("Please enter the file name you want");
            String fileName = scanner.nextLine();
            BufferedImage[][] images = new BufferedImage[dim][dim];
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    Cell c = map[i][j];
                    Tile t = tiles.get(c.getOptions().get(0));
                    images[i][j] = t.getImg();
                }
            }
            ImageUtil.SaveImage(images, gUI.getSize(), dim,fileName);
            System.out.println("Saved!");
        }
        System.exit(0);

    }

    public static List<Tile> createElectricTiles(Dimension size) throws IOException {
        /*
            A->סגול כהה
            B->סגול בהיר
            C->פס ורוד
            D->תכלת
        */
        File dir = new File("tiles/circuit-coding-train");
        Image[] images = new Image[dir.listFiles().length];
        for (int i = 0; i < dir.listFiles().length; i++) {
            File f = dir.listFiles()[i];
            images[i] = ImageIO.read(f);
        }
        List<Tile> tiles = new ArrayList<>();

        tiles.add(new Tile(ImageUtil.toBufferedImage(images[0], size), new String[] {"AAA", "AAA", "AAA", "AAA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[1], size), new String[] {"BBB", "BBB", "BBB", "BBB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[2], size), new String[] {"BBB", "BCB", "BBB", "BBB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[3], size), new String[] {"BBB", "BDB", "BBB", "BDB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[4], size), new String[] {"ABB", "BCB", "BBA", "AAA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[5], size), new String[] {"ABB", "BBB", "BBB", "BBA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[6], size), new String[] {"BBB", "BCB", "BBB", "BCB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[7], size), new String[] {"BDB", "BCB", "BDB", "BCB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[8], size), new String[] {"BDB", "BBB", "BCB", "BBB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[9], size), new String[] {"BCB", "BCB", "BBB", "BCB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[10], size), new String[] {"BCB", "BCB", "BCB", "BCB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[11], size), new String[] {"BCB", "BCB", "BBB", "BBB"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[12], size), new String[] {"BBB", "BCB", "BBB", "BCB"},1));
        List<Tile> tempTiles = new ArrayList<>();
        tempTiles.add(tiles.get(0));
        tempTiles.add(tiles.get(1));
        for (int i = 2; i < tiles.size(); i++) {
            Tile t = tiles.get(i);
            tempTiles.add(t);
            tempTiles.add(t.rotate(1));
            tempTiles.add(t.rotate(2));
            tempTiles.add(t.rotate(3));

        }
        for (Tile tile : tempTiles) {
            tile.analyze(tempTiles);
        }
        return tempTiles;
    }

    public static List<Tile> createRailTiles(Dimension size) throws IOException {

        File dir = new File("tiles/rail");
        Image[] images = new Image[dir.listFiles().length];
        for (int i = 0; i < dir.listFiles().length; i++) {
            File f = dir.listFiles()[i];
            images[i] = ImageIO.read(f);
        }
        List<Tile> tiles = new ArrayList<>();

        tiles.add(new Tile(ImageUtil.toBufferedImage(images[0], size), new String[] {"AAA", "AAA", "AAA", "AAA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[1], size), new String[] {"ABA", "ABA", "ABA", "AAA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[2], size), new String[] {"BAA", "AAB", "AAA", "AAA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[3], size), new String[] {"BAA", "AAA", "AAB", "AAA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[4], size), new String[] {"ABA", "ABA", "AAA", "AAA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[5], size), new String[] {"ABA", "AAA", "ABA", "AAA"},1));
        tiles.add(new Tile(ImageUtil.toBufferedImage(images[6], size), new String[] {"ABA", "ABA", "ABA", "ABA"},1));
        List<Tile> tempTiles = new ArrayList<>();
        tempTiles.add(tiles.get(0));
        for (int i = 1; i < tiles.size(); i++) {
            Tile t = tiles.get(i);
            tempTiles.add(t);
            tempTiles.add(t.rotate(1));
            tempTiles.add(t.rotate(2));
            tempTiles.add(t.rotate(3));

        }
        for (Tile tile : tempTiles) {
            tile.analyze(tempTiles);
        }
        return tempTiles;

    }

}
