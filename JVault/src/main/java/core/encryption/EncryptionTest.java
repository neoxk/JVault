package core.encryption;

import core.encryption.aes.AESCtrCipher;
import org.junit.BeforeClass;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.junit.Assert.*;

public class EncryptionTest {
    @BeforeClass
    public static void setupProvider() {
        // nothing to register for CTR (it's builtin), but GCM is standard too
        // (If you were using BC-FIPS you'd register here)
    }

    private final SecureRandom rnd = new SecureRandom();

    @Test
    public void testAesCtrRoundTrip() throws Exception {
        Password pwd = new Password("unit-test-pass");
        AESCtrCipher cipher = new AESCtrCipher(pwd);

        // pick some random lengths, including > sector size (4096)
        int[] lengths = {0, 1, 100, 4095, 4096, 4097, 5000, 10_000};
        for (int len : lengths) {
            byte[] plain = randomBytes(len);
            byte[] ct    = cipher.encrypt(plain);
            byte[] pt    = cipher.decrypt(ct);
            assertArrayEquals("CTR failed for length=" + len, plain, pt);
        }
    }

    @Test
    public void testWrongPasswordFails() throws Exception {
        // GCM: wrong key => exception
        Password good = new Password("correct");
        Password bad  = new Password("incorrect");

        // CTR: wrong key => wrong plaintext (no exception)
        AESCtrCipher ctrGood = new AESCtrCipher(good);
        AESCtrCipher ctrBad  = new AESCtrCipher(bad);

        byte[] rand = randomBytes(5000);
        byte[] ctC  = ctrGood.encrypt(rand);
        byte[] ptBad= ctrBad.decrypt(ctC);
        assertFalse("CTR decrypt with wrong password should not match",
                Arrays.equals(rand, ptBad));
    }

    private byte[] randomBytes(int length) {
        byte[] b = new byte[length];
        rnd.nextBytes(b);
        return b;
    }
}
