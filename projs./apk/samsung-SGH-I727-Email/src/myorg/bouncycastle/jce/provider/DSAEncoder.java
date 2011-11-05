package myorg.bouncycastle.jce.provider;

import java.io.IOException;
import java.math.BigInteger;

public interface DSAEncoder {

   BigInteger[] decode(byte[] var1) throws IOException;

   byte[] encode(BigInteger var1, BigInteger var2) throws IOException;
}
