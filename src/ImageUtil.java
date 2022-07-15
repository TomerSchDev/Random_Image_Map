import javax.imageio.ImageIO;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtil {
    public static BufferedImage toBufferedImage(Image img, Dimension size) {
        img = img.getScaledInstance((int)size.getWidth(),(int)size.getHeight(), Image.SCALE_DEFAULT);
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        // Create a buffered image with transparency
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bimage;
    }

    public static BufferedImage rotateImage(BufferedImage img, int times) {

        // Getting Dimensions of image
        int width = img.getWidth();
        int height = img.getHeight();

        // Creating a new buffered image
        BufferedImage newImage = new BufferedImage(
                width, height, img.getType());

        // creating Graphics in buffered image
        Graphics2D g2 = newImage.createGraphics();

        // Rotating image by degrees using toradians()
        // method
        // and setting new dimension t it
        g2.rotate(Math.toRadians(90 * times), width / 2,
                height / 2);
        g2.drawImage(img, null, 0, 0);

        // Return rotated buffer image
        return newImage;
    }

    public static void SaveImage(BufferedImage[][] images, Dimension size, int dim, String fileName) {
        if(!fileName.contains(".png")){
            fileName +=".png";
        }
        BufferedImage combine = new BufferedImage(size.width,size.height, images[0][0].getType());
        Graphics2D graphics = combine.createGraphics();
        int imgWidth = size.width / dim;
        int imgHeight = size.height / dim;
        for (int i = 0; i < images.length; i++) {
            for (int j = 0; j < images.length; j++) {
                graphics.drawImage(images[i][j],j*imgWidth,i*imgHeight,null);
            }
        }
        graphics.dispose();
        try {
            ImageIO.write(combine,"PNG",new File(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
