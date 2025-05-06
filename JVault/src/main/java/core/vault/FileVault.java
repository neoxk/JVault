package core.vault;

import core.io.VaultIO;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileVault implements Vault {
    private VaultIO vaultIO;

    public FileVault(VaultIO vaultIO) {
        this.vaultIO = vaultIO;
    }


    @Override
    public void addFile(Path sys_path, String internal_path) {
        try {
            byte[] data = Files.readAllBytes(sys_path);
            vaultIO.writeFile(data, internal_path);
        } catch (IOException e ){
            throw new RuntimeException("Failed to read file at " + sys_path, e);
        }
    }

    @Override
    public void removeFile(String internal_path) {
        vaultIO.removeFile(internal_path);
    }

    @Override
    public void decryptFile(String sysPath, String internalPath) {
        byte[] data = vaultIO.readFile(internalPath);
        Path outPath = Path.of(sysPath);
        try {
            if (outPath.getParent() != null) {
                Files.createDirectories(outPath.getParent());
            }
            try (OutputStream out = Files.newOutputStream(outPath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                out.write(data);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write file at " + sysPath, e);
        }
    }

    @Override
    public void decryptFile(String internalPath) {
        // 1) read the raw bytes from inside the vault
        byte[] data = vaultIO.readFile(internalPath);

        // 2) figure out where the vault file lives on disk
        Path vaultFile = vaultIO.getVaultPath();

        // 3) use its *parent* directory as the extraction root
        Path vaultDir  = vaultFile.getParent();
        if (vaultDir == null) {
            // fallback to current working dir or throw
            vaultDir = Paths.get("");
        }

        // 4) now resolve the internalPath under that dir
        Path outPath = vaultDir.resolve(internalPath);

        try {
            // make sure any sub-folders exist
            if (outPath.getParent() != null) {
                Files.createDirectories(outPath.getParent());
            }
            // write the decrypted bytes
            try (OutputStream out = Files.newOutputStream(outPath,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE)) {
                out.write(data);
            }
        } catch (IOException e) {
            // include the real file path in the error for clarity
            throw new RuntimeException("Failed to write file to "
                    + outPath.toAbsolutePath(), e);
        }
    }


    @Override
    public List<String> getPaths() {
        return vaultIO.getPaths();
    }

    @Override
    public void save() {
        vaultIO.saveMeta();
    }
}
