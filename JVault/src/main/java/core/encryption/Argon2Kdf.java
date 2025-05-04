package core.encryption;

import de.mkammerer.argon2.Argon2Advanced;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;

import java.nio.charset.StandardCharsets;

public class Argon2Kdf {
    private static final int TIME_COST    = 3;
    private static final int MEMORY_COST  = 1 << 16;
    private static final int PARALLELISM  = 1;


    public static byte[] derive(char[] password, byte[] salt, int keyLenBytes) {
        Argon2Advanced argon2 = Argon2Factory.createAdvanced(
                Argon2Types.ARGON2id,
                salt.length,
                keyLenBytes
        );
        try {
            return argon2.rawHash(
                    TIME_COST,
                    MEMORY_COST,
                    PARALLELISM,
                    password,
                    StandardCharsets.UTF_8,
                    salt
            );
        } finally {
            argon2.wipeArray(password);
        }
    }
}
