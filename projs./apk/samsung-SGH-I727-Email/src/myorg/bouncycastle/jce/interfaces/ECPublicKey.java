package myorg.bouncycastle.jce.interfaces;

import java.security.PublicKey;
import myorg.bouncycastle.jce.interfaces.ECKey;
import myorg.bouncycastle.math.ec.ECPoint;

public interface ECPublicKey extends ECKey, PublicKey {

   ECPoint getQ();
}
