package Algorithm;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import java.security.*;
public class RSA {

public void RSA_() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

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

			
			
			//RSA
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
	        generator.initialize(1024);
	        KeyPair pair = generator.generateKeyPair();
	        PrivateKey privateKey = pair.getPrivate();
	        PublicKey publicKey = pair.getPublic();
	        Cipher cipher = Cipher.getInstance("RSA");
	        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
	        
	        
	        
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
	                byte[] enc = blockCipher(pixelBytes,Cipher.ENCRYPT_MODE,cipher);
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
			       f = new File("C:/Users/nr/Desktop/VisualCryptographyAssignment/CipherImages/RSAEncryptedImage.jpg");
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
		  private static byte[] blockCipher(byte[] bytes, int mode,Cipher cipher) throws IllegalBlockSizeException, BadPaddingException {
				// TODO Auto-generated method stub
		    	
		    	byte[] scrambled = new byte[0];

		    	// toReturn will hold the total result
		    	byte[] toReturn = new byte[0];
		    	// if we encrypt we use 100 byte long blocks. Decryption requires 128 byte long blocks (because of RSA)
		    	int length = (mode == Cipher.ENCRYPT_MODE)? 100 : 128;

		    	// another buffer. this one will hold the bytes that have to be modified in this step
		    	byte[] buffer = new byte[length];

				for (int i=0; i< bytes.length; i++){

		    		// if we filled our buffer array we have our block ready for de- or encryption
		    		if ((i > 0) && (i % length == 0)){
		    			//execute the operation
		    			scrambled = cipher.doFinal(buffer);
		    			// add the result to our total result.
		    			toReturn = append(toReturn,scrambled);
		    			// here we calculate the length of the next buffer required
		    			int newlength = length;

		    			// if newlength would be longer than remaining bytes in the bytes array we shorten it.
		    			if (i + length > bytes.length) {
		    				 newlength = bytes.length - i;
		    			}
		    			// clean the buffer array
		    			buffer = new byte[newlength];
		    		}
		    		// copy byte into our buffer.
		    		buffer[i%length] = bytes[i];
		    	}

		    	// this step is needed if we had a trailing buffer. should only happen when encrypting.
		    	// example: we encrypt 110 bytes. 100 bytes per run means we "forgot" the last 10 bytes. they are in the buffer array
		    	scrambled = cipher.doFinal(buffer);

		    	// final step before we can return the modified data.
		    	toReturn = append(toReturn,scrambled);

		    	return toReturn;
			}

			private static byte[] append(byte[] prefix, byte[] suffix) {
				// TODO Auto-generated method stub
				byte[] toReturn = new byte[prefix.length + suffix.length];
				for (int i=0; i< prefix.length; i++){
					toReturn[i] = prefix[i];
				}
				for (int i=0; i< suffix.length; i++){
					toReturn[i+prefix.length] = suffix[i];
				}
				return toReturn;
			}
}
