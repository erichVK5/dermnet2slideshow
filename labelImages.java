
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
    Boolean verbose = true;
    int textHeight = 35;
    while (imagesAndLabels.hasNextLine()) {
	String[] data = imagesAndLabels.nextLine().split("@");
	String imageFile = data[0];
	String imageLabel = imageFile.split("_")[0];
	if (data.length > 1) {
		imageLabel = data[1];
	}
	// weed out most of pathology sections here
	boolean HistoPathologyImage = (imageFile.contains("pathology")
		&& !imageFile.contains("blaschko"));
	File currentImage = new File(imageFile);
	if (currentImage.exists() && !HistoPathologyImage) {
                if (verbose) {
                        System.out.println("Processing : " + imageFile + "\nwith tag : " + imageLabel);
                }
		BufferedImage image = ImageIO.read(currentImage);
		int origHeight = image.getHeight();
		int origWidth = image.getWidth();
		int newHeight = origHeight + textHeight*3 + textHeight/2;
		int newWidth = origWidth;
		int offset = 0;
		if (origWidth < 900) {
			newWidth = 900;
			offset = (newWidth - origWidth)/2;
		}

		BufferedImage newImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < origWidth; i++) {
			for (int j = 0; j < origHeight; j++) {
				newImage.setRGB(offset + i,j,image.getRGB(i,j));
			}
		}
		Graphics g = newImage.getGraphics();
		g.setFont(g.getFont().deriveFont(30f));
		int currentTextYCoord = newHeight - textHeight/2;
		g.drawString(imageLabel, 10, currentTextYCoord);
		String newFile = currentImage.getName();
		int index = newFile.indexOf("__Water");
		if (index == -1) {
			index = newFile.indexOf("__Protect");
		}
		String truncatedFilename = newFile.substring(0,index);
		currentTextYCoord -= textHeight;
		g.drawString(truncatedFilename, 10, currentTextYCoord);
		index = imageFile.indexOf(truncatedFilename);
		String pathToImage = imageFile.substring(0,index);
		currentTextYCoord -= textHeight;
		g.drawString(pathToImage, 10, currentTextYCoord);
		g.dispose();
        	if (newFile.endsWith(".jpg") || newFile.endsWith(".JPG")
        	        || newFile.endsWith(".gif") || newFile.endsWith(".GIF")) {
        	                newFile = newFile.substring(0,newFile.length()-4);
        	} else if (newFile.endsWith(".JPEG") || newFile.endsWith(".jpeg")) {
        	                newFile = newFile.substring(0,newFile.length()-5);
        	}
		File output = new File("modified/" + newFile + ".png");
		if (!output.exists()) {
			ImageIO.write(newImage, "png", output);
		}
		counter++;
		if (false && counter > 300) {
			break;
		}
    	}
    }

  }
}
