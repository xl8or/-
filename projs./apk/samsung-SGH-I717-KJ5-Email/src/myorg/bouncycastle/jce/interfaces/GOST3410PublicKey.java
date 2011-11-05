package myorg.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PublicKey;
import myorg.bouncycastle.jce.interfaces.GOST3410Key;

public interface GOST3410PublicKey extends GOST3410Key, PublicKey {

   BigInteger getY();
}
