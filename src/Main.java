import java.io.UnsupportedEncodingException;
import java.io.IOException;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {

        String data1 = "1-2-1-2-12123123123";
        String key = "12345678vbabcd12";
        int[] iv = new int[2];
        iv[0] = 232324;
        iv[1] = 45678;

        System.out.println("ORGData:\n" + data1 + '\n');
        // System.out.println("ORGData:\n" + Arrays.toString(data1.getBytes()) + '\n');
        byte[] res1 = CAST128.encryption(data1.getBytes(), key, iv);
        System.out.println("ENCData:\n" + CAST128.bytesToString(res1) + '\n');
        // System.out.println("ENCData:\n" + Arrays.toString(res1) + '\n');
        byte[] result = CAST128.decryption(res1, key, iv);
        System.out.println("DECData:\n" + CAST128.bytesToString(result) + '\n');
        // System.out.println("DECData:\n" + Arrays.toString(result) + '\n');
        
        System.out.println("---------files------------");
        res1 = CAST128.encryptionFile("test.txt", "ENCtest.txt", key, iv);
        System.out.println("ENCData:\n" + CAST128.bytesToString(res1) + '\n');
        result = CAST128.decryptionFile("ENCtest.txt", "testRES.txt", key, iv);
        System.out.println("DECData:\n" + CAST128.bytesToString(result) + '\n');

        System.out.println("---------images------------");
        result = CAST128.encryptionImage("ball1.bmp", "enc.bmp", key, iv);
        result = CAST128.decryptionImage("enc.bmp", "dec.bmp", key, iv);
        System.out.println("Done!");

    }
}