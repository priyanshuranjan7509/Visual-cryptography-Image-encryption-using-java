package Algorithm;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.imageio.ImageIO;
import java.security.*;
public class DES {

public void DES_() throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
	
	
			BufferedImage img=null;
			File f = null;
			
			// read image
			try {
				f = new File(
					"C:/Users/nr/Desktop/VisualCryptographyAssignment/target.jpg");
				img = ImageIO.read(f);
			}
			catch (IOException e) {
				System.out.println(e);
			}

			// get width and height
			int width = img.getWidth();
			int height = img.getHeight();

	        BufferedImage encImage = new BufferedImage(width,height,img.getType());
						 
	        
	        
	        KeyGenerator keygen = KeyGenerator.getInstance("DES");
			keygen.init(56);
			SecretKey desKey = keygen.generateKey();
			Cipher sipher = null;
			try {
				sipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
			} catch (NoSuchAlgorithmException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (NoSuchPaddingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

//			We use the desKey generated previously to initialize the Cipher object for encryption:

			 // Initialize the cipher for encryption
			 sipher.init(Cipher.ENCRYPT_MODE,desKey);

			 
	        
	       //BufferedImage.TYPE_4BYTE_ABGR
	        for(int x=0;x<width; x+=2){
	            for(int y=0;y<height; y+=2){
	                int counter =0;
	                byte [] pixelBytes = new byte[16];
	                // Loop through internal block
	                for (int i=0;i<2;i++){
	                    for (int j=0;j<2;j++){
	                        int val = img.getRGB(x+i,y+j);
	                        byte [] sub  = intToByteArray(val);
	                        for(int k=0;k<4;k++) pixelBytes[(counter)*4+k] = sub[k];
	                        counter++;
	                   }
	                }

	                
	                byte [] enc = sipher.doFinal(pixelBytes);
	                counter =0;
	         // Re-encode the new image
	                for (int i=0;i<2;i++){
	                  for (int j=0;j<2;j++){
	                     byte [] sub = new byte[4];
	  					for(int k=0;k<4;k++) 
	  					sub[k] = enc[(counter)*4+k];

	                int val = byteArrayToInt(sub);
	                encImage.setRGB(x+i,y+j,val);
	                counter++;
	                    }
	                }
	            }
	        }
	        // write output image
			  try {
		       f = new File("C:/Users/nr/Desktop/VisualCryptographyAssignment/CipherImages/DESEncryptedImage.jpg");
		      ImageIO.write(encImage, "jpg", f);
			  }
			  catch (IOException e) {
					System.out.println(e);
				}
			 
		}
		public static final byte[] intToByteArray(int value)
		  {
		      return new byte[] {
		              (byte)(value >>> 24),
		              (byte)(value >>> 16),
		              (byte)(value >>> 8),
		              (byte)value};
		  }

		  public static final int byteArrayToInt(byte [] b)
		  {
		      return (b[0] << 24)
		              + ((b[1] & 0xFF) << 16)
		              + ((b[2] & 0xFF) << 8)
		              + (b[3] & 0xFF);
		  }
		 



}
