package myorg.bouncycastle.jce.interfaces;

import java.math.BigInteger;
import java.security.PrivateKey;
import myorg.bouncycastle.jce.interfaces.ECKey;

public interface ECPrivateKey extends ECKey, PrivateKey {

   BigInteger getD();
}
