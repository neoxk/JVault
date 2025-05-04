package core.io;

import core.encryption.Cipher;
import core.io.fs.FileProxy;
import core.io.fs.Pointer;

import java.nio.file.Path;

public class EncryptedVaultIO implements VaultIO{

    private FileProxy file;
    private Cipher cipher;
    private Header header;
    private Index index;

    private int contentCounter = 12288;


    public EncryptedVaultIO(FileProxy file, Cipher cipher) {
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
    public void writeFile(byte[] data, String path) {
        byte[] encryptedData = cipher.encrypt(data);
        this.allocate(encryptedData.length, path);
        file.write(encryptedData, index.getPointer(path));
    }

    @Override
    public byte[] readFile(String path) {
        Pointer pointer = index.getPointer(path);
        return cipher.decrypt(file.read(pointer));
    }


    @Override
    public void format() {
        file.write(new byte[file.getSize()], new Pointer(0, file.getSize()));
        file.purge();
    }
}
