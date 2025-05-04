package core.io;

public class EncryptedVaultIO implements VaultIO{
    @Override
    public void writeProp(HeaderProp prop, String value) {

    }

    @Override
    public String readProp(HeaderProp prop) {
        return "";
    }

    @Override
    public void writeFile() {

    }

    @Override
    public byte[] readFile() {
        return new byte[0];
    }
}
