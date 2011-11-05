package myorg.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;
import myorg.bouncycastle.jce.interfaces.ElGamalKey;

public interface ElGamalPrivateKey extends ElGamalKey, PrivateKey {

   BigInteger getX();
}
