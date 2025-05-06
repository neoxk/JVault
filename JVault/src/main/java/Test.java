import core.encryption.Password;
import core.vault.Vault;
import core.vault.VaultFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) {
        VaultFactory vaultFactory = new VaultFactory();
        Password password = new Password("my pass");
//        Vault vault = vaultFactory.create(Path.of("/home/nxk/Downloads/jvault/vault.jvault"), password);
//
//        vault = vaultFactory.open(Paths.get("/home/nxk/Downloads/jvault/vault.jvault"), password);
//        vault.addFile(Paths.get("/home/nxk/Downloads/jvault/test.txt"), "test.txt");
//        vault.addFile(Paths.get("/home/nxk/Downloads/jvault/test2.txt"), "test2.txt");

        VaultFactory vaultFactory1 = new VaultFactory();
        Vault vault = vaultFactory.open(Paths.get("/home/nxk/Downloads/jvault/vault.jvault"), password);

        vault.decryptFile("test.txt");

    }
}
