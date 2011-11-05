package myorg.bouncycastle.jce.interfaces;

import java.util.Enumeration;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;

public interface PKCS12BagAttributeCarrier {

   DEREncodable getBagAttribute(DERObjectIdentifier var1);

   Enumeration getBagAttributeKeys();

   void setBagAttribute(DERObjectIdentifier var1, DEREncodable var2);
}
