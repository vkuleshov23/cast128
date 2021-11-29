import java.io.UnsupportedEncodingException;
import java.io.IOException;


public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException, IOException {

        // String data1 = "kek-3456789ABCDEF-----------121231234";
        String data1 = "1-2-1-2";
        String key = "1234567890abcd12";

        System.out.println("DATA: " + data1 + '\n');
        System.out.println("KEY: " + key + '\n');
        int[] k = CastEncryptor.getKey(key);

        byte[] res = CastEncryptor.encoding(data1, k);
        System.out.println("ENCRYPTED DATA");
        for(int i = 0; i < res.length; i++ ) {
            System.out.print(res[i] + " ");
        }        
        System.out.println();

        String res1 = CastEncryptor.decoding(res, k);
        System.out.println("DECRYPTED DATA");
        System.out.println(res1);
        System.out.println();
    }
}