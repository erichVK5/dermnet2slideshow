
import java.io.*;
import java.util.Scanner;
import java.awt.image.BufferedImage;
import java.awt.Graphics;
import javax.imageio.ImageIO;

public class labelImages {

  public static void main(String[] args) throws Exception {
    File rawData = new File(args[0]);
    Scanner imagesAndLabels = new Scanner(rawData);
    int counter = 0;
    Boolean verbose = false;
    while (imagesAndLabels.hasNextLine()) {
	String[] data = imagesAndLabels.nextLine().split("@");
	String imageFile = data[0];
	String imageLabel = data[1];
	File currentImage = new File(imageFile);
	if (verbose) {
		System.out.println("current image: " + currentImage + "\ncurrent tag: " + imageLabel);
	}
	BufferedImage image = ImageIO.read(currentImage);
	int origHeight = image.getHeight();
	int origWidth = image.getWidth();
	BufferedImage newImage = new BufferedImage(origWidth * 3, origHeight * 2, BufferedImage.TYPE_INT_RGB);
	for (int i = 0; i < origWidth; i++) {
		for (int j = 0; j <  origHeight; j++) {
			newImage.setRGB(i+origWidth,j,image.getRGB(i,j));
		}
	}
	Graphics g = newImage.getGraphics();
	g.setFont(g.getFont().deriveFont(30f));
	int currentTextYCoord = origHeight*2 - 35;
	g.drawString(imageLabel, 10, currentTextYCoord);
	String newFile = currentImage.getName();
	int index = newFile.indexOf("__Protect");
	String truncatedFilename = newFile.substring(0,index);
	currentTextYCoord -= 35;
	g.drawString(truncatedFilename, 10, currentTextYCoord);
	index = imageFile.indexOf(truncatedFilename);
	String pathToImage = imageFile.substring(0,index);
	currentTextYCoord -= 35;
	g.drawString(pathToImage, 10, currentTextYCoord);
	g.dispose();
	ImageIO.write(newImage, "png", new File("modified/" + newFile + ".png"));
	counter++;
	if (false && counter > 300) {
		break;
	}
    }

  }
}
