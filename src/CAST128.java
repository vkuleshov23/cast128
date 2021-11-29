import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.nio.file.*;
import java.lang.StringBuilder;
import java.util.stream.Stream;
import java.io.*;

public class CAST128 {
	public static byte[] encryption(byte[] byteData, String key) throws UnsupportedEncodingException, IOException {
		
		int[] k = CastEncryptor.getKey(key);

		byteData = Arrays.copyOf(byteData, byteData.length + ((byteData.length%8 == 0) ? 0 : (8-byteData.length%8)) );

		for(int i = 0; i < byteData.length; i+=8) {
			byte[] block = Arrays.copyOfRange(byteData, i, i+8);
        	
        	block = CastEncryptor.encoding(block, k);
			
			fill(block, byteData, i, i+8);
		}
		return byteData;
	}

	public static byte[] decryption(byte[] byteData, String key) throws UnsupportedEncodingException, IOException {
		
		int[] k = CastEncryptor.getKey(key);
		byte[] res = new byte[byteData.length];
		
		for(int i = 0; i < byteData.length; i+=8) {
			byte[] block = Arrays.copyOfRange(byteData, i, i+8);
        	fill(CastEncryptor.decoding(block, k), res, i, i+8);

		}
		return res;
	}

	public static void fill(byte[] src, byte[] dst, int from, int to) {
		for(int i = from, j = 0; i < to; i++, j++) {
			dst[i] = src[j];
		}
	}

	public static byte[] encryptionImage(String srcImage, String dstImage, String key) throws UnsupportedEncodingException, IOException {
		Image img = new Image();
        byte[] data = img.readImage(srcImage);
        System.out.println("data.length: " + data.length);
        data = encryption(data, key);
        System.out.println("data.length: " + data.length);
        img.writeImage(dstImage, data);
        return data;
	}

	public static byte[] decryptionImage(String srcImage, String dstImage, String key) throws UnsupportedEncodingException, IOException {
		Image img = new Image();
        byte[] data = img.readImage(srcImage);
        System.out.println("data.length: " + data.length);
        data = decryption(data, key);
        System.out.println("data.length: " + data.length);
        img.writeImage(dstImage, data);
        return data;
	}

	@SuppressWarnings("unchecked")
	public static byte[] encryptionFile(String readFile, String writeFile, String key) throws UnsupportedEncodingException, IOException {

		byte[] data = readFile(readFile);
		data = encryption(data, key);
		writeToFile(writeFile, data, false);

		return data;
	}

	public static byte[] decryptionFile(String readFile, String writeFile, String key) throws UnsupportedEncodingException, IOException {

        byte[] datasrc = readFile(readFile);
        byte[] data = decryption(datasrc, key);
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