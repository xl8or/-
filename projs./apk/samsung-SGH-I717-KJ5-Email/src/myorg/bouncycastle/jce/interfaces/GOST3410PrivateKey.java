package myorg.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;
import myorg.bouncycastle.jce.interfaces.GOST3410Key;

public interface GOST3410PrivateKey extends GOST3410Key, PrivateKey {

   BigInteger getX();
}
