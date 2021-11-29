import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class Image {

    public byte[] image;
    public int headLength;

    public Image() {
        headLength = 0;
        image = new byte[1];
    }

    public byte[] readImage(String filename) throws IOException {
        File file = new File(filename);
        BufferedImage img = ImageIO.read(file);

        int width = img.getWidth();
        int height = img.getHeight();
        int bpp = getBitsPerPixel(img);

        System.out.println("Input image size: " + width + " x " + height + " (bpp = " + bpp + ")\n");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "bmp", baos);
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();

        System.out.println("imageInByte.length = " + imageInByte.length + "     image size = " + width * height * bpp);
        this.image = imageInByte;
        this.headLength = imageInByte.length - (width * height * bpp);
        byte[] noHeadImage = this.cutHead();
        if(noHeadImage.length%8 != 0) {
            System.out.println("\n[x] Bad image size!!!!!!!!!!\n");
            return null;
        } else {
            return noHeadImage;
        }
    }

    private byte[] cutHead() {
        byte[] imageNoHead = new byte[image.length - headLength];

        for(int i = headLength, j = 0; i < imageNoHead.length; i++, j++) {
            imageNoHead[j] = image[i];
        }
        System.out.println("newImage size: " + imageNoHead.length);
        return imageNoHead;
    }

    public void writeImage(String filename, byte[] imageInByte) throws IOException {
        this.addHead(imageInByte);
        BufferedImage newimg = ImageIO.read(new ByteArrayInputStream(this.image));
        ImageIO.write(newimg, "bmp", new File(filename));
    }

    private void addHead(byte[] img) {
        System.out.println("img.length: " + img.length);
        for(int i = headLength, j = 0; i < img.length; i++, j++) {
            this.image[i] = img[j];
        }
        System.out.println("end");
    }

    public int getBitsPerPixel(BufferedImage img) {
        return img.getColorModel().getPixelSize() / Byte.SIZE;
    }
}