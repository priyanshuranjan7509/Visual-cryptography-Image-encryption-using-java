package Algorithm;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;
public class AES {

	public void AES_() throws NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, InvalidKeyException, FileNotFoundException {
		// TODO Auto-generated method stub
		
				BufferedImage img=null;
//				BufferedImage  redImg=null;
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
				
				KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
		        keyGenerator.init(128);
		        SecretKey secretKey = keyGenerator.generateKey();
		        byte[] secretKeyBytes = secretKey.getEncoded();
		        SecretKey secretKey1 = new SecretKeySpec(secretKeyBytes, "AES");
		        
		        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
		        BufferedImage encImage = new BufferedImage(width,height,img.getType());
		        cipher.init(Cipher.ENCRYPT_MODE, secretKey1);
		        
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

		                // Cipher
		                byte [] enc = cipher.doFinal(pixelBytes);	
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
			       f = new File("C:/Users/nr/Desktop/VisualCryptographyAssignment/CipherImages/AESEncryptedImage.jpg");
			      ImageIO.write(encImage, "jpg", f);
				  }
				  catch (IOException e) {
						System.out.println(e);
					}
				 
			}
			

public static final int byteArrayToInt(byte [] b)
{
    return (b[0] << 24)
            + ((b[1] & 0xFF) << 16)
            + ((b[2] & 0xFF) << 8)
            + (b[3] & 0xFF);
}
public static final byte[] intToByteArray(int value)
{
    return new byte[] {
            (byte)(value >>> 24),
            (byte)(value >>> 16),
            (byte)(value >>> 8),
            (byte)value};
}
}
