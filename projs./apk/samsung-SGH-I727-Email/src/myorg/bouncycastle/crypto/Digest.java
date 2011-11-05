package myorg.bouncycastle.crypto;


public interface Digest {

   int doFinal(byte[] var1, int var2);

   String getAlgorithmName();

   int getDigestSize();

   void reset();

   void update(byte var1);

   void update(byte[] var1, int var2, int var3);
}
