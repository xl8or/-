package myorg.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;
import myorg.bouncycastle.jce.interfaces.ElGamalKey;

public interface ElGamalPublicKey extends ElGamalKey, PublicKey {

   BigInteger getY();
}
