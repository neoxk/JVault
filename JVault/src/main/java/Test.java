import core.encryption.Password;
import core.vault.Vault;
import core.vault.VaultFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) {
        VaultFactory vaultFactory = new VaultFactory();
        Password password = new Password("my pass");

        Vault vault = vaultFactory.create(Path.of("/home/nxk/Downloads/vault.jvault"), password);


    }
}
