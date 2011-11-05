package myorg.bouncycastle.util.encoders;


public interface Translator {

   int decode(byte[] var1, int var2, int var3, byte[] var4, int var5);

   int encode(byte[] var1, int var2, int var3, byte[] var4, int var5);

   int getDecodedBlockSize();

   int getEncodedBlockSize();
}
