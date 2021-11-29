import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {

        String data1 = "1-2-1-2-";
        String key = "12345678vbabcd12";

        System.out.println("ORGData:\n" + data1 + '\n');
        byte[] res1 = CAST128.encryption(data1.getBytes(), key);
        System.out.println("ENCData:\n" + CAST128.bytesToString(res1) + '\n');
        byte[] result = CAST128.decryption(res1, key);
        System.out.println("DECData:\n" + CAST128.bytesToString(result) + '\n');
        
        System.out.println("---------files------------");
        res1 = CAST128.encryptionFile("test.txt", "ENCtest.txt", key);
        System.out.println("ENCData:\n" + CAST128.bytesToString(res1) + '\n');
        result = CAST128.decryptionFile("ENCtest.txt", "testRES.txt", key);
        System.out.println("DECData:\n" + CAST128.bytesToString(result) + '\n');

        System.out.println("---------images------------");
        result = CAST128.encryptionImage("winter.bmp", "enc.bmp", key);
        result = CAST128.decryptionImage("enc.bmp", "dec.bmp", key);
    }
}