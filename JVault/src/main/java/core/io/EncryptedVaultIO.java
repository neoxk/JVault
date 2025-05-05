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
    private final int SECTOR_SIZE = 512;

    private int contentCounter = 12288;


    public EncryptedVaultIO(FileProxy file, ICipher cipher) {
        this.file = file;
        this.cipher = cipher;
    }

    private void allocate(int size, String path) {
        Pointer newPointer = new Pointer(contentCounter, size);
        contentCounter += size;
        index.addPointer(path, newPointer);
    }

    @Override
    public IndexNode getIndexRoot() {
        return index.getRoot();
    }

    @Override
    public void writeFile(byte[] data, String path){
        int sector_num = (data.length + SECTOR_SIZE - 1) / SECTOR_SIZE;

        byte[] padded = Arrays.copyOf(data, sector_num * SECTOR_SIZE);

        byte[] encryptedData;
        try {
             encryptedData = cipher.encrypt(padded);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt file", e);
        }

        this.allocate(encryptedData.length, path);
        file.write(encryptedData, index.getPointer(path));
    }

    @Override
    public byte[] readFile(String path) {
        Pointer pointer = index.getPointer(path);
        try {
            return cipher.decrypt(file.read(pointer));
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt file", e);
        }
    }


    @Override
    public void format() {
        file.write(new byte[file.getSize()], new Pointer(0, file.getSize()));
        file.purge();
    }
}
