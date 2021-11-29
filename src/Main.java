import java.io.UnsupportedEncodingException;


public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {

        // String data1 = "kek-3456789ABCDEF-----------121231234";
        String data1 = "kek";
        String key = "1234567890abcdef";

        System.out.println("DATA: " + data1 + '\n');
        System.out.println("KEY: " + key + '\n');

        // byte[] data = CastEncryptor.stringToByteArray(data1);

        byte[] res = CastEncryptor.encoding(data1, key);
        System.out.println("ENCRYPTED DATA");
        for(int i = 0; i < res.length; i++ ) {
            System.out.print(res[i] + " ");
        }        
        System.out.println();

        String res1 = CastEncryptor.decoding(res, key);
        System.out.println("DECRYPTED DATA");
        System.out.println(res1);
        System.out.println();
    }
}