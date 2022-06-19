package Main;
import Algorithm.*;
import java.io.PrintWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
public class test {
	public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, FileNotFoundException {
  
		//we are reading pixel value of given target image in targettext.txt file
		imageReadasPixels();
		
		//now we will call AES,DES,3DES,RSA algorithm function call for image (target.jpg) encryption
		
 	    
		
		try (PrintWriter executionTime = new PrintWriter("C:/Users/nr/Desktop/VisualCryptographyAssignment/ExecutionTime.txt")) {
			executionTime.println( " ALGO " +  "  EXECUTION TIME(In NanoSeconds)  ");
			//AES
			AES algo1 = new AES();
			long start1 = System.nanoTime();
			algo1.AES_();
			long end1 = System.nanoTime(); 
			
			executionTime.println( " AES:   " +  (end1-start1));
			
			//DES
			
			DES algo2 = new DES();
			long start2 = System.nanoTime();
			algo2.DES_();
			long end2 = System.nanoTime(); 
			
			executionTime.println( " DES:   " +  (end2-start2));
			
			
			
			//3DES
			TRIPLE_DES algo3 = new TRIPLE_DES();
			long start3 = System.nanoTime();
			algo3.TRIPLE_DES_();
			long end3 = System.nanoTime();

			executionTime.println( " 3DES:   " +  (end3-start3));
			
			
			
			//RSA
			RSA algo4 = new RSA();
			long start4 = System.nanoTime();
			algo4.RSA_();
			long end4 = System.nanoTime();
			
			executionTime.println( " RSA:   " +  (end4-start4));
		}
			 
	}
		  

	
	
	
	
	
	static void imageReadasPixels() throws FileNotFoundException {
			BufferedImage img=null;
			File f = null;
			// read image
			try {
				f = new File("C:/Users/nr/Desktop/VisualCryptographyAssignment/target.jpg");
				img = ImageIO.read(f);
			}
			catch (IOException e) {
				System.out.println(e);
			}
		      PrintWriter out = new PrintWriter("C:/Users/nr/Desktop/VisualCryptographyAssignment/targettext.txt"); 
			
	         int count =0;
	        int[][] image_arr = new int[1000][1000];
			for (int y = 0; y < img.getHeight(); y++) {
//				out.println("{");  
				for (int x = 0; x < img.getWidth(); x++) {
					Color c = new Color(img.getRGB(x, y));
				     image_arr[y][x] =img.getRGB(x, y);
				     count++;		
	 	    	     out.println("S.No: " + count + " Red: " + c.getRed() +"  Green: " + c.getGreen() + " Blue: " + c.getBlue());
				   
				}
			}
			out.close();
		}
}
