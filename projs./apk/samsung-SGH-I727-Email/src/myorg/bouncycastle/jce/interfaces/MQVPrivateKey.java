package myorg.bouncycastle.jce.interfaces;

import java.security.PrivateKey;
import java.security.PublicKey;

public interface MQVPrivateKey extends PrivateKey {

   PrivateKey getEphemeralPrivateKey();

   PublicKey getEphemeralPublicKey();

   PrivateKey getStaticPrivateKey();
}
