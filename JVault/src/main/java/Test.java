import core.encryption.Password;
import core.vault.Vault;
import core.vault.VaultFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
    public static void main(String[] args) {
        VaultFactory vaultFactory = new VaultFactory();
        Password password = new Password("my pass");
        Vault vault = vaultFactory.open(Paths.get("/home/nxk/Downloads/vault.jvault"), password);

        vault.decryptFile("/home/nxk/Downloads/ttneo.txt", "test.txt");

//        Vault vault = vaultFactory.create(Path.of("/home/nxk/Downloads/vault.jvault"), password);
//        vault.addFile(Path.of("/home/nxk/Downloads/test.txt"), "test.txt");
//        vault.addFile(Path.of("/home/nxk/Downloads/test2.txt"), "test2.txt");
//        vault.getPaths().forEach(System.out::println);
//        vault.decryptFile("/home/nxk/Downloads/tt.txt", "test.txt");
//        vault.decryptFile("/home/nxk/Downloads/tt2.txt", "test2.txt");
//        vault.save();
    }
}
