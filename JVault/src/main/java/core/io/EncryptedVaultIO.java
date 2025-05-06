package core.io;

import core.encryption.ICipher;
import core.io.fs.FileProxy;
import core.io.fs.Pointer;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

public class EncryptedVaultIO implements VaultIO{

    private FileProxy file;
    private ICipher cipher;
    private Header header;
    private Index index;
    private final int SECTOR_SIZE = 4096;

    private long next_sector = 24;


    public EncryptedVaultIO(FileProxy file, ICipher cipher, boolean existing) {
        this.file = file;

        if (this.file.getSize() > next_sector * SECTOR_SIZE) {
            int fileSize      = this.file.getSize();
            int occupiedSects = (fileSize + SECTOR_SIZE - 1) / SECTOR_SIZE;
            next_sector = occupiedSects;
        }

        this.cipher = cipher;
        if (!existing) {
            this.header = new Header();
            this.index = new Index();
        } else {
            Pointer headerPointer = new Pointer(0, 1, 0);
            Pointer indexPointer  = new Pointer(1, 4, 0);
            byte[] headerBytes = this.file.read(headerPointer);
            byte[] indexBytes  = this.file.read(indexPointer);
            this.header = Header.load(headerBytes);
            this.index = Index.load(indexBytes);
        }

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
    public List<String> getPaths() {
        return this.index.listPaths();
    }

    @Override
    public void writeFile(byte[] data, String path){
        byte[] encryptedData;
        try {
             encryptedData = cipher.encrypt(data);
        } catch (Exception e) {
            throw new RuntimeException("Failed to encrypt file", e);
        }

        Pointer pointer = this.allocate(encryptedData.length, path);
        byte[] padded = Arrays.copyOf(encryptedData, encryptedData.length + pointer.getPadding_size());

        file.write(padded, index.getPointer(path));
    }

    @Override
    public byte[] readFile(String path) {
        Pointer pointer = index.getPointer(path);
        byte[] raw = file.read(pointer);
        try {
            int padding = pointer.getPadding_size();
            return cipher.decrypt(Arrays.copyOfRange(raw, 0, raw.length - padding));
        } catch (Exception e) {
            throw new RuntimeException("Failed to decrypt file", e);
        }
    }

    @Override
    public void removeFile(String path) {
        Pointer pointer = index.getPointer(path);
        file.purge(pointer);
        index.removePointer(path);
    }

    @Override
    public void saveMeta() {
        byte[] headerBytes = header.serialize();
        byte[] indexBytes  = index.serialize();

        //TODO encrypt header and index
//        try {
//            headerBytes = cipher.encrypt(headerBytes);
//            indexBytes  = cipher.encrypt(indexBytes);
//        } catch(Exception e) {
//            throw new RuntimeException("Failed to encrypt header", e);
//        }
//
        byte[] paddedHeader = Arrays.copyOf(headerBytes, SECTOR_SIZE);
        byte[] paddedIndex  = Arrays.copyOf(indexBytes, 4* SECTOR_SIZE);

        file.write(paddedHeader, new Pointer(0, 1, 0));
        file.write(paddedIndex, new Pointer(1, 4, 0));
    }

    @Override
    public Path getVaultPath() {
        return file.getPath();
    }
}
