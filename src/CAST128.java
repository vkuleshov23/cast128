import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.nio.file.*;
import java.lang.StringBuilder;
import java.util.stream.Stream;
import java.io.*;

public class CAST128 {

	private static byte[] xor(byte[] a, byte[] b) {
		byte[] result = new byte[a.length];
		for(int i = 0; i < a.length; i++) {
			result[i] = (byte)(a[i] ^ b[i]);
		}
		return result;
	}

	private static byte[] cfb(byte[] iv, byte[] openBlock, int[] k) throws UnsupportedEncodingException, IOException {
		byte[] chipedIV = CastEncryptor.encoding(iv, k);
		return xor(openBlock, chipedIV);
	}

	public static byte[] encryption(byte[] byteData, String key, int[] iv) throws UnsupportedEncodingException, IOException {
		
		int[] k = CastEncryptor.getKey(key);
		byteData = Arrays.copyOf(byteData, byteData.length + ((byteData.length%8 == 0) ? 0 : (8-byteData.length%8)) );
		byte[] bIV = CastEncryptor.intMassToByte(iv);

		for(int i = 0; i < byteData.length; i+=8) {

			byte[] openBlock = Arrays.copyOfRange(byteData, i, i+8);        
			bIV = cfb(bIV, openBlock, k);
			fill(bIV, byteData, i, i+8);
		}
		return byteData;
	}

	public static byte[] decryption(byte[] byteData, String key, int[] iv) throws UnsupportedEncodingException, IOException {
		
		int[] k = CastEncryptor.getKey(key);
		byte[] bIV = CastEncryptor.intMassToByte(iv);

		for(int i = 0; i < byteData.length; i+=8) {

			byte[] closedBlock = Arrays.copyOfRange(byteData, i, i+8);        
			fill(cfb(bIV, closedBlock, k), byteData, i, i+8);
			bIV = closedBlock;
		}
		return byteData;
	}

	public static void fill(byte[] src, byte[] dst, int from, int to) {
		for(int i = from, j = 0; i < to; i++, j++) {
			dst[i] = src[j];
		}
	}

	public static byte[] encryptionImage(String srcImage, String dstImage, String key, int[] iv) throws UnsupportedEncodingException, IOException {
		Image img = new Image();
        byte[] data = img.readImage(srcImage);
        data = encryption(data, key, iv);
        img.writeImage(dstImage, data);
        return data;
	}

	public static byte[] decryptionImage(String srcImage, String dstImage, String key, int[] iv) throws UnsupportedEncodingException, IOException {
		Image img = new Image();
        byte[] data = img.readImage(srcImage);
        data = decryption(data, key, iv);
        img.writeImage(dstImage, data);
        return data;
	}

	@SuppressWarnings("unchecked")
	public static byte[] encryptionFile(String readFile, String writeFile, String key, int[] iv) throws UnsupportedEncodingException, IOException {

		byte[] data = readFile(readFile);
		data = encryption(data, key, iv);
		writeToFile(writeFile, data, false);

		return data;
	}

	public static byte[] decryptionFile(String readFile, String writeFile, String key, int[] iv) throws UnsupportedEncodingException, IOException {

        byte[] datasrc = readFile(readFile);
        byte[] data = decryption(datasrc, key, iv);
		writeToFile(writeFile, data, true);

		return data;
	}

	private static byte[] readFile(String filename) throws IOException {
		try(FileInputStream fin = new FileInputStream(filename)) {
            byte[] data = new byte[fin.available()];
            fin.read(data);
        	return data;
        }
	}

	private static void writeToFile(String filename, byte[] data, boolean decode) throws IOException {
		try(FileOutputStream writer = new FileOutputStream(filename)) {
        	if(!decode){
        		writer.write( data );
        	} else {
        		writer.write( (bytesToString(data).trim()).getBytes() );
        	}
        }
	}

	public static String bytesToString(byte[] data) {
		try {
			return CastEncryptor.byteMassToString(data);
		} catch(UnsupportedEncodingException err) {
			return "null";
		}
	}
}