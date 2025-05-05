package core.io;

import core.encryption.ICipher;
import core.io.fs.FileProxy;
import core.io.fs.Pointer;

import java.nio.file.Path;
import java.util.Arrays;

public class EncryptedVaultIO implements VaultIO{

    private FileProxy file;
    private ICipher cipher;
    private Header header;
    private Index index;
    private final int SECTOR_SIZE = 4096;

    private long next_sector = 24;


    public EncryptedVaultIO(FileProxy file, ICipher cipher) {
        this.file = file;
        this.cipher = cipher;
    }

    private Pointer allocate(int size, String path) {
        int num_sectors = (size + SECTOR_SIZE - 1) / SECTOR_SIZE;
        int padding = num_sectors * SECTOR_SIZE - size;
        Pointer pointer = new Pointer(next_sector, num_sectors, padding);
        index.addPointer(path, pointer);
        next_sector += num_sectors + 1;
        return pointer;
    }

    @Override
    public IndexNode getIndexRoot() {
        return index.getRoot();
    }

    @Override
    public void writeFile(byte[] data, String path){
        Pointer pointer = this.allocate(data.length, path);

        byte[] padded = Arrays.copyOf(data, data.length + pointer.getPadding_size());

        byte[] encryptedData;
        try {
             encryptedData = cipher.encrypt(padded);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt file", e);
        }

        file.write(encryptedData, index.getPointer(path));
    }

    @Override
    public byte[] readFile(String path) {
        Pointer pointer = index.getPointer(path);
        try {
            byte[] decrypted = cipher.decrypt(file.read(pointer));
            int padding = pointer.getPadding_size();
            if (padding > 0) {
                return Arrays.copyOfRange(decrypted, 0, decrypted.length - padding);
            } else {
                return decrypted;
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt file", e);
        }
    }


    @Override
    public void format() {
        file.purge();
    }
}
