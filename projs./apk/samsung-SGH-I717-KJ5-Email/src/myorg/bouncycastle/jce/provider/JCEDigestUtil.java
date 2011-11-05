package myorg.bouncycastle.jce.provider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import myorg.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.crypto.Digest;
import myorg.bouncycastle.crypto.digests.MD5Digest;
import myorg.bouncycastle.crypto.digests.SHA1Digest;
import myorg.bouncycastle.crypto.digests.SHA224Digest;
import myorg.bouncycastle.crypto.digests.SHA256Digest;
import myorg.bouncycastle.crypto.digests.SHA384Digest;
import myorg.bouncycastle.crypto.digests.SHA512Digest;
import myorg.bouncycastle.util.Strings;

class JCEDigestUtil {

   private static Set md5 = new HashSet();
   private static Map oids = new HashMap();
   private static Set sha1 = new HashSet();
   private static Set sha224 = new HashSet();
   private static Set sha256 = new HashSet();
   private static Set sha384 = new HashSet();
   private static Set sha512 = new HashSet();


   static {
      boolean var0 = md5.add("MD5");
      Set var1 = md5;
      String var2 = PKCSObjectIdentifiers.md5.getId();
      var1.add(var2);
      boolean var4 = sha1.add("SHA1");
      boolean var5 = sha1.add("SHA-1");
      Set var6 = sha1;
      String var7 = OIWObjectIdentifiers.idSHA1.getId();
      var6.add(var7);
      boolean var9 = sha224.add("SHA224");
      boolean var10 = sha224.add("SHA-224");
      Set var11 = sha224;
      String var12 = NISTObjectIdentifiers.id_sha224.getId();
      var11.add(var12);
      boolean var14 = sha256.add("SHA256");
      boolean var15 = sha256.add("SHA-256");
      Set var16 = sha256;
      String var17 = NISTObjectIdentifiers.id_sha256.getId();
      var16.add(var17);
      boolean var19 = sha384.add("SHA384");
      boolean var20 = sha384.add("SHA-384");
      Set var21 = sha384;
      String var22 = NISTObjectIdentifiers.id_sha384.getId();
      var21.add(var22);
      boolean var24 = sha512.add("SHA512");
      boolean var25 = sha512.add("SHA-512");
      Set var26 = sha512;
      String var27 = NISTObjectIdentifiers.id_sha512.getId();
      var26.add(var27);
      Map var29 = oids;
      DERObjectIdentifier var30 = PKCSObjectIdentifiers.md5;
      var29.put("MD5", var30);
      Map var32 = oids;
      String var33 = PKCSObjectIdentifiers.md5.getId();
      DERObjectIdentifier var34 = PKCSObjectIdentifiers.md5;
      var32.put(var33, var34);
      Map var36 = oids;
      DERObjectIdentifier var37 = OIWObjectIdentifiers.idSHA1;
      var36.put("SHA1", var37);
      Map var39 = oids;
      DERObjectIdentifier var40 = OIWObjectIdentifiers.idSHA1;
      var39.put("SHA-1", var40);
      Map var42 = oids;
      String var43 = OIWObjectIdentifiers.idSHA1.getId();
      DERObjectIdentifier var44 = OIWObjectIdentifiers.idSHA1;
      var42.put(var43, var44);
      Map var46 = oids;
      DERObjectIdentifier var47 = NISTObjectIdentifiers.id_sha224;
      var46.put("SHA224", var47);
      Map var49 = oids;
      DERObjectIdentifier var50 = NISTObjectIdentifiers.id_sha224;
      var49.put("SHA-224", var50);
      Map var52 = oids;
      String var53 = NISTObjectIdentifiers.id_sha224.getId();
      DERObjectIdentifier var54 = NISTObjectIdentifiers.id_sha224;
      var52.put(var53, var54);
      Map var56 = oids;
      DERObjectIdentifier var57 = NISTObjectIdentifiers.id_sha256;
      var56.put("SHA256", var57);
      Map var59 = oids;
      DERObjectIdentifier var60 = NISTObjectIdentifiers.id_sha256;
      var59.put("SHA-256", var60);
      Map var62 = oids;
      String var63 = NISTObjectIdentifiers.id_sha256.getId();
      DERObjectIdentifier var64 = NISTObjectIdentifiers.id_sha256;
      var62.put(var63, var64);
      Map var66 = oids;
      DERObjectIdentifier var67 = NISTObjectIdentifiers.id_sha384;
      var66.put("SHA384", var67);
      Map var69 = oids;
      DERObjectIdentifier var70 = NISTObjectIdentifiers.id_sha384;
      var69.put("SHA-384", var70);
      Map var72 = oids;
      String var73 = NISTObjectIdentifiers.id_sha384.getId();
      DERObjectIdentifier var74 = NISTObjectIdentifiers.id_sha384;
      var72.put(var73, var74);
      Map var76 = oids;
      DERObjectIdentifier var77 = NISTObjectIdentifiers.id_sha512;
      var76.put("SHA512", var77);
      Map var79 = oids;
      DERObjectIdentifier var80 = NISTObjectIdentifiers.id_sha512;
      var79.put("SHA-512", var80);
      Map var82 = oids;
      String var83 = NISTObjectIdentifiers.id_sha512.getId();
      DERObjectIdentifier var84 = NISTObjectIdentifiers.id_sha512;
      var82.put(var83, var84);
   }

   JCEDigestUtil() {}

   static Digest getDigest(String var0) {
      String var1 = Strings.toUpperCase(var0);
      Object var2;
      if(sha1.contains(var1)) {
         var2 = new SHA1Digest();
      } else if(md5.contains(var1)) {
         var2 = new MD5Digest();
      } else if(sha224.contains(var1)) {
         var2 = new SHA224Digest();
      } else if(sha256.contains(var1)) {
         var2 = new SHA256Digest();
      } else if(sha384.contains(var1)) {
         var2 = new SHA384Digest();
      } else if(sha512.contains(var1)) {
         var2 = new SHA512Digest();
      } else {
         var2 = null;
      }

      return (Digest)var2;
   }

   static DERObjectIdentifier getOID(String var0) {
      return (DERObjectIdentifier)oids.get(var0);
   }

   static boolean isSameDigest(String var0, String var1) {
      boolean var2;
      if((!sha1.contains(var0) || !sha1.contains(var1)) && (!sha224.contains(var0) || !sha224.contains(var1)) && (!sha256.contains(var0) || !sha256.contains(var1)) && (!sha384.contains(var0) || !sha384.contains(var1)) && (!sha512.contains(var0) || !sha512.contains(var1)) && (!md5.contains(var0) || !md5.contains(var1))) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }
}
