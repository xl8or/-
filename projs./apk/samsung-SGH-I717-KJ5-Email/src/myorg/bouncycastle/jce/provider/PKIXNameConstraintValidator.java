package myorg.bouncycastle.jce.provider;

import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Map.Entry;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.x509.GeneralName;
import myorg.bouncycastle.asn1.x509.GeneralSubtree;
import myorg.bouncycastle.jce.provider.PKIXNameConstraintValidatorException;
import myorg.bouncycastle.util.Arrays;
import myorg.bouncycastle.util.Strings;

public class PKIXNameConstraintValidator {

   private Set excludedSubtreesDN;
   private Set excludedSubtreesDNS;
   private Set excludedSubtreesEmail;
   private Set excludedSubtreesIP;
   private Set excludedSubtreesURI;
   private Set permittedSubtreesDN;
   private Set permittedSubtreesDNS;
   private Set permittedSubtreesEmail;
   private Set permittedSubtreesIP;
   private Set permittedSubtreesURI;


   public PKIXNameConstraintValidator() {
      HashSet var1 = new HashSet();
      this.excludedSubtreesDN = var1;
      HashSet var2 = new HashSet();
      this.excludedSubtreesDNS = var2;
      HashSet var3 = new HashSet();
      this.excludedSubtreesEmail = var3;
      HashSet var4 = new HashSet();
      this.excludedSubtreesURI = var4;
      HashSet var5 = new HashSet();
      this.excludedSubtreesIP = var5;
   }

   private void checkExcludedDN(Set var1, ASN1Sequence var2) throws PKIXNameConstraintValidatorException {
      if(!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            ASN1Sequence var4 = (ASN1Sequence)var3.next();
            if(withinDNSubtree(var2, var4)) {
               throw new PKIXNameConstraintValidatorException("Subject distinguished name is from an excluded subtree");
            }
         }

      }
   }

   private void checkExcludedDNS(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if(!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if(!var3.hasNext()) {
               return;
            }

            var4 = (String)var3.next();
         } while(!this.withinDomain(var2, var4) && !var2.equalsIgnoreCase(var4));

         throw new PKIXNameConstraintValidatorException("DNS is from an excluded subtree.");
      }
   }

   private void checkExcludedEmail(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if(!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            if(this.emailIsConstrained(var2, var4)) {
               throw new PKIXNameConstraintValidatorException("Email address is from an excluded subtree.");
            }
         }

      }
   }

   private void checkExcludedIP(Set var1, byte[] var2) throws PKIXNameConstraintValidatorException {
      if(!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            byte[] var4 = (byte[])((byte[])var3.next());
            if(this.isIPConstrained(var2, var4)) {
               throw new PKIXNameConstraintValidatorException("IP is from an excluded subtree.");
            }
         }

      }
   }

   private void checkExcludedURI(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if(!var1.isEmpty()) {
         Iterator var3 = var1.iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            if(this.isUriConstrained(var2, var4)) {
               throw new PKIXNameConstraintValidatorException("URI is from an excluded subtree.");
            }
         }

      }
   }

   private void checkPermittedDN(Set var1, ASN1Sequence var2) throws PKIXNameConstraintValidatorException {
      if(var1 != null) {
         if(!var1.isEmpty() || var2.size() != 0) {
            Iterator var3 = var1.iterator();

            ASN1Sequence var4;
            do {
               if(!var3.hasNext()) {
                  throw new PKIXNameConstraintValidatorException("Subject distinguished name is not from a permitted subtree");
               }

               var4 = (ASN1Sequence)var3.next();
            } while(!withinDNSubtree(var2, var4));

         }
      }
   }

   private void checkPermittedDNS(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if(var1 != null) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if(!var3.hasNext()) {
               if(var2.length() == 0 && var1.size() == 0) {
                  return;
               }

               throw new PKIXNameConstraintValidatorException("DNS is not from a permitted subtree.");
            }

            var4 = (String)var3.next();
            if(this.withinDomain(var2, var4)) {
               return;
            }
         } while(!var2.equalsIgnoreCase(var4));

      }
   }

   private void checkPermittedEmail(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if(var1 != null) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if(!var3.hasNext()) {
               if(var2.length() == 0 && var1.size() == 0) {
                  return;
               }

               throw new PKIXNameConstraintValidatorException("Subject email address is not from a permitted subtree.");
            }

            var4 = (String)var3.next();
         } while(!this.emailIsConstrained(var2, var4));

      }
   }

   private void checkPermittedIP(Set var1, byte[] var2) throws PKIXNameConstraintValidatorException {
      if(var1 != null) {
         Iterator var3 = var1.iterator();

         byte[] var4;
         do {
            if(!var3.hasNext()) {
               if(var2.length == 0 && var1.size() == 0) {
                  return;
               }

               throw new PKIXNameConstraintValidatorException("IP is not from a permitted subtree.");
            }

            var4 = (byte[])((byte[])var3.next());
         } while(!this.isIPConstrained(var2, var4));

      }
   }

   private void checkPermittedURI(Set var1, String var2) throws PKIXNameConstraintValidatorException {
      if(var1 != null) {
         Iterator var3 = var1.iterator();

         String var4;
         do {
            if(!var3.hasNext()) {
               if(var2.length() == 0 && var1.size() == 0) {
                  return;
               }

               throw new PKIXNameConstraintValidatorException("URI is not from a permitted subtree.");
            }

            var4 = (String)var3.next();
         } while(!this.isUriConstrained(var2, var4));

      }
   }

   private boolean collectionsAreEqual(Collection var1, Collection var2) {
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else if(var1 != null && var2 != null) {
         int var4 = var1.size();
         int var5 = var2.size();
         if(var4 != var5) {
            var3 = false;
         } else {
            Iterator var6 = var1.iterator();

            while(true) {
               if(var6.hasNext()) {
                  Object var7 = var6.next();
                  Iterator var8 = var2.iterator();
                  boolean var9 = false;

                  while(var8.hasNext()) {
                     Object var10 = var8.next();
                     if(this.equals(var7, var10)) {
                        var9 = true;
                        break;
                     }
                  }

                  if(var9) {
                     continue;
                  }

                  var3 = false;
                  break;
               }

               var3 = true;
               break;
            }
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   private static int compareTo(byte[] var0, byte[] var1) {
      byte var2;
      if(Arrays.areEqual(var0, var1)) {
         var2 = 0;
      } else if(Arrays.areEqual(max(var0, var1), var0)) {
         var2 = 1;
      } else {
         var2 = -1;
      }

      return var2;
   }

   private boolean emailIsConstrained(String var1, String var2) {
      int var3 = var1.indexOf(64) + 1;
      String var4 = var1.substring(var3);
      boolean var5;
      if(var2.indexOf(64) != -1) {
         if(var1.equalsIgnoreCase(var2)) {
            var5 = true;
            return var5;
         }
      } else if(var2.charAt(0) != 46) {
         if(var4.equalsIgnoreCase(var2)) {
            var5 = true;
            return var5;
         }
      } else if(this.withinDomain(var4, var2)) {
         var5 = true;
         return var5;
      }

      var5 = false;
      return var5;
   }

   private boolean equals(Object var1, Object var2) {
      boolean var3;
      if(var1 == var2) {
         var3 = true;
      } else if(var1 != null && var2 != null) {
         if(var1 instanceof byte[] && var2 instanceof byte[]) {
            byte[] var4 = (byte[])((byte[])var1);
            byte[] var5 = (byte[])((byte[])var2);
            var3 = Arrays.areEqual(var4, var5);
         } else {
            var3 = var1.equals(var2);
         }
      } else {
         var3 = false;
      }

      return var3;
   }

   private static String extractHostFromURL(String var0) {
      int var1 = var0.indexOf(58) + 1;
      String var2 = var0.substring(var1);
      if(var2.indexOf("//") != -1) {
         int var3 = var2.indexOf("//") + 2;
         var2 = var2.substring(var3);
      }

      if(var2.lastIndexOf(58) != -1) {
         int var4 = var2.lastIndexOf(58);
         var2 = var2.substring(0, var4);
      }

      int var5 = var2.indexOf(58) + 1;
      String var6 = var2.substring(var5);
      int var7 = var6.indexOf(64) + 1;
      String var8 = var6.substring(var7);
      if(var8.indexOf(47) != -1) {
         int var9 = var8.indexOf(47);
         var8 = var8.substring(0, var9);
      }

      return var8;
   }

   private byte[][] extractIPsAndSubnetMasks(byte[] var1, byte[] var2) {
      int var3 = var1.length / 2;
      byte[] var4 = new byte[var3];
      byte[] var5 = new byte[var3];
      System.arraycopy(var1, 0, var4, 0, var3);
      System.arraycopy(var1, var3, var5, 0, var3);
      byte[] var6 = new byte[var3];
      byte[] var7 = new byte[var3];
      System.arraycopy(var2, 0, var6, 0, var3);
      System.arraycopy(var2, var3, var7, 0, var3);
      byte[] var8 = new byte[]{(byte)var4, (byte)var5, (byte)var6, (byte)var7};
      return (byte[][])var8;
   }

   private String extractNameAsString(GeneralName var1) {
      return DERIA5String.getInstance(var1.getName()).getString();
   }

   private int hashCollection(Collection var1) {
      int var2;
      if(var1 == null) {
         var2 = 0;
      } else {
         int var3 = 0;
         Iterator var4 = var1.iterator();

         while(var4.hasNext()) {
            Object var5 = var4.next();
            if(var5 instanceof byte[]) {
               int var6 = Arrays.hashCode((byte[])((byte[])var5));
               var3 += var6;
            } else {
               int var7 = var5.hashCode();
               var3 += var7;
            }
         }

         var2 = var3;
      }

      return var2;
   }

   private Set intersectDN(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         ASN1Sequence var5 = ASN1Sequence.getInstance(((GeneralSubtree)var4.next()).getBase().getName().getDERObject());
         if(var1 == null) {
            if(var5 != null) {
               var3.add(var5);
            }
         } else {
            Iterator var7 = var1.iterator();

            while(var7.hasNext()) {
               ASN1Sequence var8 = (ASN1Sequence)var7.next();
               if(withinDNSubtree(var5, var8)) {
                  var3.add(var5);
               } else if(withinDNSubtree(var8, var5)) {
                  var3.add(var8);
               }
            }
         }
      }

      return var3;
   }

   private Set intersectDNS(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         GeneralName var5 = ((GeneralSubtree)var4.next()).getBase();
         String var6 = this.extractNameAsString(var5);
         if(var1 == null) {
            if(var6 != null) {
               var3.add(var6);
            }
         } else {
            Iterator var8 = var1.iterator();

            while(var8.hasNext()) {
               String var9 = (String)var8.next();
               if(this.withinDomain(var9, var6)) {
                  var3.add(var9);
               } else if(this.withinDomain(var6, var9)) {
                  var3.add(var6);
               }
            }
         }
      }

      return var3;
   }

   private Set intersectEmail(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         GeneralName var5 = ((GeneralSubtree)var4.next()).getBase();
         String var6 = this.extractNameAsString(var5);
         if(var1 == null) {
            if(var6 != null) {
               var3.add(var6);
            }
         } else {
            Iterator var8 = var1.iterator();

            while(var8.hasNext()) {
               String var9 = (String)var8.next();
               this.intersectEmail(var6, var9, var3);
            }
         }
      }

      return var3;
   }

   private void intersectEmail(String var1, String var2, Set var3) {
      if(var1.indexOf(64) != -1) {
         int var4 = var1.indexOf(64) + 1;
         String var5 = var1.substring(var4);
         if(var2.indexOf(64) != -1) {
            if(var1.equalsIgnoreCase(var2)) {
               var3.add(var1);
            }
         } else if(var2.startsWith(".")) {
            if(this.withinDomain(var5, var2)) {
               var3.add(var1);
            }
         } else if(var5.equalsIgnoreCase(var2)) {
            var3.add(var1);
         }
      } else if(var1.startsWith(".")) {
         if(var2.indexOf(64) != -1) {
            int var9 = var1.indexOf(64) + 1;
            String var10 = var2.substring(var9);
            if(this.withinDomain(var10, var1)) {
               var3.add(var2);
            }
         } else if(var2.startsWith(".")) {
            if(!this.withinDomain(var1, var2) && !var1.equalsIgnoreCase(var2)) {
               if(this.withinDomain(var2, var1)) {
                  var3.add(var2);
               }
            } else {
               var3.add(var1);
            }
         } else if(this.withinDomain(var2, var1)) {
            var3.add(var2);
         }
      } else if(var2.indexOf(64) != -1) {
         int var15 = var2.indexOf(64) + 1;
         if(var2.substring(var15).equalsIgnoreCase(var1)) {
            var3.add(var2);
         }
      } else if(var2.startsWith(".")) {
         if(this.withinDomain(var1, var2)) {
            var3.add(var1);
         }
      } else if(var1.equalsIgnoreCase(var2)) {
         var3.add(var1);
      }
   }

   private Set intersectIP(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         byte[] var5 = ASN1OctetString.getInstance(((GeneralSubtree)var4.next()).getBase().getName()).getOctets();
         if(var1 == null) {
            if(var5 != null) {
               var3.add(var5);
            }
         } else {
            Iterator var7 = var1.iterator();

            while(var7.hasNext()) {
               byte[] var8 = (byte[])((byte[])var7.next());
               Set var9 = this.intersectIPRange(var8, var5);
               var3.addAll(var9);
            }
         }
      }

      return var3;
   }

   private Set intersectIPRange(byte[] var1, byte[] var2) {
      int var3 = var1.length;
      int var4 = var2.length;
      Set var5;
      if(var3 != var4) {
         var5 = Collections.EMPTY_SET;
      } else {
         byte[][] var6 = this.extractIPsAndSubnetMasks(var1, var2);
         byte[] var7 = var6[0];
         byte[] var8 = var6[1];
         byte[] var9 = var6[2];
         byte[] var10 = var6[3];
         byte[][] var11 = this.minMaxIPs(var7, var8, var9, var10);
         byte[] var12 = var11[1];
         byte[] var13 = var11[3];
         byte[] var14 = min(var12, var13);
         byte[] var15 = var11[0];
         byte[] var16 = var11[2];
         if(compareTo(max(var15, var16), var14) == 1) {
            var5 = Collections.EMPTY_SET;
         } else {
            byte[] var17 = var11[0];
            byte[] var18 = var11[2];
            byte[] var19 = or(var17, var18);
            byte[] var20 = or(var8, var10);
            var5 = Collections.singleton(this.ipWithSubnetMask(var19, var20));
         }
      }

      return var5;
   }

   private Set intersectURI(Set var1, Set var2) {
      HashSet var3 = new HashSet();
      Iterator var4 = var2.iterator();

      while(var4.hasNext()) {
         GeneralName var5 = ((GeneralSubtree)var4.next()).getBase();
         String var6 = this.extractNameAsString(var5);
         if(var1 == null) {
            if(var6 != null) {
               var3.add(var6);
            }
         } else {
            Iterator var8 = var1.iterator();

            while(var8.hasNext()) {
               String var9 = (String)var8.next();
               this.intersectURI(var9, var6, var3);
            }
         }
      }

      return var3;
   }

   private void intersectURI(String var1, String var2, Set var3) {
      if(var1.indexOf(64) != -1) {
         int var4 = var1.indexOf(64) + 1;
         String var5 = var1.substring(var4);
         if(var2.indexOf(64) != -1) {
            if(var1.equalsIgnoreCase(var2)) {
               var3.add(var1);
            }
         } else if(var2.startsWith(".")) {
            if(this.withinDomain(var5, var2)) {
               var3.add(var1);
            }
         } else if(var5.equalsIgnoreCase(var2)) {
            var3.add(var1);
         }
      } else if(var1.startsWith(".")) {
         if(var2.indexOf(64) != -1) {
            int var9 = var1.indexOf(64) + 1;
            String var10 = var2.substring(var9);
            if(this.withinDomain(var10, var1)) {
               var3.add(var2);
            }
         } else if(var2.startsWith(".")) {
            if(!this.withinDomain(var1, var2) && !var1.equalsIgnoreCase(var2)) {
               if(this.withinDomain(var2, var1)) {
                  var3.add(var2);
               }
            } else {
               var3.add(var1);
            }
         } else if(this.withinDomain(var2, var1)) {
            var3.add(var2);
         }
      } else if(var2.indexOf(64) != -1) {
         int var15 = var2.indexOf(64) + 1;
         if(var2.substring(var15).equalsIgnoreCase(var1)) {
            var3.add(var2);
         }
      } else if(var2.startsWith(".")) {
         if(this.withinDomain(var1, var2)) {
            var3.add(var1);
         }
      } else if(var1.equalsIgnoreCase(var2)) {
         var3.add(var1);
      }
   }

   private byte[] ipWithSubnetMask(byte[] var1, byte[] var2) {
      int var3 = var1.length;
      byte[] var4 = new byte[var3 * 2];
      System.arraycopy(var1, 0, var4, 0, var3);
      System.arraycopy(var2, 0, var4, var3, var3);
      return var4;
   }

   private boolean isIPConstrained(byte[] var1, byte[] var2) {
      int var3 = var1.length;
      int var4 = var2.length / 2;
      byte var5;
      if(var3 != var4) {
         var5 = 0;
      } else {
         byte[] var6 = new byte[var3];
         System.arraycopy(var2, var3, var6, 0, var3);
         byte[] var7 = new byte[var3];
         byte[] var8 = new byte[var3];

         for(int var9 = 0; var9 < var3; ++var9) {
            byte var10 = var2[var9];
            byte var11 = var6[var9];
            byte var12 = (byte)(var10 & var11);
            var7[var9] = var12;
            byte var13 = var1[var9];
            byte var14 = var6[var9];
            byte var15 = (byte)(var13 & var14);
            var8[var9] = var15;
         }

         var5 = Arrays.areEqual(var7, var8);
      }

      return (boolean)var5;
   }

   private boolean isUriConstrained(String var1, String var2) {
      String var3 = extractHostFromURL(var1);
      boolean var4;
      if(!var2.startsWith(".")) {
         if(var3.equalsIgnoreCase(var2)) {
            var4 = true;
            return var4;
         }
      } else if(this.withinDomain(var3, var2)) {
         var4 = true;
         return var4;
      }

      var4 = false;
      return var4;
   }

   private static byte[] max(byte[] var0, byte[] var1) {
      int var2 = 0;

      byte[] var6;
      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            var6 = var1;
            break;
         }

         int var4 = var0[var2] & '\uffff';
         int var5 = var1[var2] & '\uffff';
         if(var4 > var5) {
            var6 = var0;
            break;
         }

         ++var2;
      }

      return var6;
   }

   private static byte[] min(byte[] var0, byte[] var1) {
      int var2 = 0;

      byte[] var6;
      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            var6 = var1;
            break;
         }

         int var4 = var0[var2] & '\uffff';
         int var5 = var1[var2] & '\uffff';
         if(var4 < var5) {
            var6 = var0;
            break;
         }

         ++var2;
      }

      return var6;
   }

   private byte[][] minMaxIPs(byte[] var1, byte[] var2, byte[] var3, byte[] var4) {
      int var5 = var1.length;
      byte[] var6 = new byte[var5];
      byte[] var7 = new byte[var5];
      byte[] var8 = new byte[var5];
      byte[] var9 = new byte[var5];

      for(int var10 = 0; var10 < var5; ++var10) {
         byte var11 = var1[var10];
         byte var12 = var2[var10];
         byte var13 = (byte)(var11 & var12);
         var6[var10] = var13;
         byte var14 = var1[var10];
         byte var15 = var2[var10];
         int var16 = var14 & var15;
         int var17 = ~var2[var10];
         byte var18 = (byte)(var16 | var17);
         var7[var10] = var18;
         byte var19 = var3[var10];
         byte var20 = var4[var10];
         byte var21 = (byte)(var19 & var20);
         var8[var10] = var21;
         byte var22 = var3[var10];
         byte var23 = var4[var10];
         int var24 = var22 & var23;
         int var25 = ~var4[var10];
         byte var26 = (byte)(var24 | var25);
         var9[var10] = var26;
      }

      byte[] var27 = new byte[]{(byte)var6, (byte)var7, (byte)var8, (byte)var9};
      return (byte[][])var27;
   }

   private static byte[] or(byte[] var0, byte[] var1) {
      byte[] var2 = new byte[var0.length];
      int var3 = 0;

      while(true) {
         int var4 = var0.length;
         if(var3 >= var4) {
            return var2;
         }

         byte var5 = var0[var3];
         byte var6 = var1[var3];
         byte var7 = (byte)(var5 | var6);
         var2[var3] = var7;
         ++var3;
      }
   }

   private String stringifyIP(byte[] var1) {
      String var2 = "";
      int var3 = 0;

      while(true) {
         int var4 = var1.length / 2;
         if(var3 >= var4) {
            int var7 = var2.length() - 1;
            String var8 = var2.substring(0, var7);
            String var9 = var8 + "/";
            int var10 = var1.length / 2;

            while(true) {
               int var11 = var1.length;
               if(var10 >= var11) {
                  int var14 = var9.length() - 1;
                  return var9.substring(0, var14);
               }

               StringBuilder var12 = (new StringBuilder()).append(var9);
               String var13 = Integer.toString(var1[var10] & 255);
               var9 = var12.append(var13).append(".").toString();
               ++var10;
            }
         }

         StringBuilder var5 = (new StringBuilder()).append(var2);
         String var6 = Integer.toString(var1[var3] & 255);
         var2 = var5.append(var6).append(".").toString();
         ++var3;
      }
   }

   private String stringifyIPCollection(Set var1) {
      String var2 = "" + "[";

      StringBuilder var4;
      String var6;
      for(Iterator var3 = var1.iterator(); var3.hasNext(); var2 = var4.append(var6).append(",").toString()) {
         var4 = (new StringBuilder()).append(var2);
         byte[] var5 = (byte[])((byte[])var3.next());
         var6 = this.stringifyIP(var5);
      }

      if(var2.length() > 1) {
         int var7 = var2.length() - 1;
         var2 = var2.substring(0, var7);
      }

      return var2 + "]";
   }

   private Set unionDN(Set var1, ASN1Sequence var2) {
      Object var3;
      if(var1.isEmpty()) {
         if(var2 == null) {
            var3 = var1;
         } else {
            var1.add(var2);
            var3 = var1;
         }
      } else {
         HashSet var5 = new HashSet();
         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            ASN1Sequence var7 = (ASN1Sequence)var6.next();
            if(withinDNSubtree(var2, var7)) {
               var5.add(var7);
            } else if(withinDNSubtree(var7, var2)) {
               var5.add(var2);
            } else {
               var5.add(var7);
               var5.add(var2);
            }
         }

         var3 = var5;
      }

      return (Set)var3;
   }

   private Set unionEmail(Set var1, String var2) {
      Object var3;
      if(var1.isEmpty()) {
         if(var2 == null) {
            var3 = var1;
         } else {
            var1.add(var2);
            var3 = var1;
         }
      } else {
         HashSet var5 = new HashSet();
         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            this.unionEmail(var7, var2, var5);
         }

         var3 = var5;
      }

      return (Set)var3;
   }

   private void unionEmail(String var1, String var2, Set var3) {
      if(var1.indexOf(64) != -1) {
         int var4 = var1.indexOf(64) + 1;
         String var5 = var1.substring(var4);
         if(var2.indexOf(64) != -1) {
            if(var1.equalsIgnoreCase(var2)) {
               var3.add(var1);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if(var2.startsWith(".")) {
            if(this.withinDomain(var5, var2)) {
               var3.add(var2);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if(var5.equalsIgnoreCase(var2)) {
            var3.add(var2);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if(var1.startsWith(".")) {
         if(var2.indexOf(64) != -1) {
            int var15 = var1.indexOf(64) + 1;
            String var16 = var2.substring(var15);
            if(this.withinDomain(var16, var1)) {
               var3.add(var1);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if(var2.startsWith(".")) {
            if(!this.withinDomain(var1, var2) && !var1.equalsIgnoreCase(var2)) {
               if(this.withinDomain(var2, var1)) {
                  var3.add(var1);
               } else {
                  var3.add(var1);
                  var3.add(var2);
               }
            } else {
               var3.add(var2);
            }
         } else if(this.withinDomain(var2, var1)) {
            var3.add(var1);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if(var2.indexOf(64) != -1) {
         int var27 = var1.indexOf(64) + 1;
         if(var2.substring(var27).equalsIgnoreCase(var1)) {
            var3.add(var1);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if(var2.startsWith(".")) {
         if(this.withinDomain(var1, var2)) {
            var3.add(var2);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if(var1.equalsIgnoreCase(var2)) {
         var3.add(var1);
      } else {
         var3.add(var1);
         var3.add(var2);
      }
   }

   private Set unionIP(Set var1, byte[] var2) {
      Object var3;
      if(var1.isEmpty()) {
         if(var2 == null) {
            var3 = var1;
         } else {
            var1.add(var2);
            var3 = var1;
         }
      } else {
         HashSet var5 = new HashSet();
         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            byte[] var7 = (byte[])((byte[])var6.next());
            Set var8 = this.unionIPRange(var7, var2);
            var5.addAll(var8);
         }

         var3 = var5;
      }

      return (Set)var3;
   }

   private Set unionIPRange(byte[] var1, byte[] var2) {
      HashSet var3 = new HashSet();
      if(Arrays.areEqual(var1, var2)) {
         var3.add(var1);
      } else {
         var3.add(var1);
         var3.add(var2);
      }

      return var3;
   }

   private Set unionURI(Set var1, String var2) {
      Object var3;
      if(var1.isEmpty()) {
         if(var2 == null) {
            var3 = var1;
         } else {
            var1.add(var2);
            var3 = var1;
         }
      } else {
         HashSet var5 = new HashSet();
         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            this.unionURI(var7, var2, var5);
         }

         var3 = var5;
      }

      return (Set)var3;
   }

   private void unionURI(String var1, String var2, Set var3) {
      if(var1.indexOf(64) != -1) {
         int var4 = var1.indexOf(64) + 1;
         String var5 = var1.substring(var4);
         if(var2.indexOf(64) != -1) {
            if(var1.equalsIgnoreCase(var2)) {
               var3.add(var1);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if(var2.startsWith(".")) {
            if(this.withinDomain(var5, var2)) {
               var3.add(var2);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if(var5.equalsIgnoreCase(var2)) {
            var3.add(var2);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if(var1.startsWith(".")) {
         if(var2.indexOf(64) != -1) {
            int var15 = var1.indexOf(64) + 1;
            String var16 = var2.substring(var15);
            if(this.withinDomain(var16, var1)) {
               var3.add(var1);
            } else {
               var3.add(var1);
               var3.add(var2);
            }
         } else if(var2.startsWith(".")) {
            if(!this.withinDomain(var1, var2) && !var1.equalsIgnoreCase(var2)) {
               if(this.withinDomain(var2, var1)) {
                  var3.add(var1);
               } else {
                  var3.add(var1);
                  var3.add(var2);
               }
            } else {
               var3.add(var2);
            }
         } else if(this.withinDomain(var2, var1)) {
            var3.add(var1);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if(var2.indexOf(64) != -1) {
         int var27 = var1.indexOf(64) + 1;
         if(var2.substring(var27).equalsIgnoreCase(var1)) {
            var3.add(var1);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if(var2.startsWith(".")) {
         if(this.withinDomain(var1, var2)) {
            var3.add(var2);
         } else {
            var3.add(var1);
            var3.add(var2);
         }
      } else if(var1.equalsIgnoreCase(var2)) {
         var3.add(var1);
      } else {
         var3.add(var1);
         var3.add(var2);
      }
   }

   private static boolean withinDNSubtree(ASN1Sequence var0, ASN1Sequence var1) {
      boolean var2;
      if(var1.size() < 1) {
         var2 = false;
      } else {
         int var3 = var1.size();
         int var4 = var0.size();
         if(var3 > var4) {
            var2 = false;
         } else {
            int var5 = var1.size() - 1;

            while(true) {
               if(var5 < 0) {
                  var2 = true;
                  break;
               }

               DEREncodable var6 = var1.getObjectAt(var5);
               DEREncodable var7 = var0.getObjectAt(var5);
               if(!var6.equals(var7)) {
                  var2 = false;
                  break;
               }

               var5 += -1;
            }
         }
      }

      return var2;
   }

   private boolean withinDomain(String var1, String var2) {
      String var3 = var2;
      if(var2.startsWith(".")) {
         var3 = var2.substring(1);
      }

      String[] var4 = Strings.split(var3, '.');
      String[] var5 = Strings.split(var1, '.');
      int var6 = var5.length;
      int var7 = var4.length;
      boolean var8;
      if(var6 <= var7) {
         var8 = false;
      } else {
         int var9 = var5.length;
         int var10 = var4.length;
         int var11 = var9 - var10;
         int var12 = -1;

         while(true) {
            int var13 = var4.length;
            if(var12 >= var13) {
               var8 = true;
               break;
            }

            if(var12 == -1) {
               int var14 = var12 + var11;
               if(var5[var14].equals("")) {
                  var8 = false;
                  break;
               }
            } else {
               String var15 = var4[var12];
               int var16 = var12 + var11;
               String var17 = var5[var16];
               if(!var15.equalsIgnoreCase(var17)) {
                  var8 = false;
                  break;
               }
            }

            ++var12;
         }
      }

      return var8;
   }

   public void addExcludedSubtree(GeneralSubtree var1) {
      GeneralName var2 = var1.getBase();
      switch(var2.getTagNo()) {
      case 1:
         Set var3 = this.excludedSubtreesEmail;
         String var4 = this.extractNameAsString(var2);
         Set var5 = this.unionEmail(var3, var4);
         this.excludedSubtreesEmail = var5;
         return;
      case 2:
         Set var6 = this.excludedSubtreesDNS;
         String var7 = this.extractNameAsString(var2);
         Set var8 = this.unionDNS(var6, var7);
         this.excludedSubtreesDNS = var8;
         return;
      case 3:
      case 5:
      default:
         return;
      case 4:
         Set var9 = this.excludedSubtreesDN;
         ASN1Sequence var10 = (ASN1Sequence)var2.getName().getDERObject();
         Set var11 = this.unionDN(var9, var10);
         this.excludedSubtreesDN = var11;
         return;
      case 6:
         Set var12 = this.excludedSubtreesURI;
         String var13 = this.extractNameAsString(var2);
         Set var14 = this.unionURI(var12, var13);
         this.excludedSubtreesURI = var14;
         return;
      case 7:
         Set var15 = this.excludedSubtreesIP;
         byte[] var16 = ASN1OctetString.getInstance(var2.getName()).getOctets();
         Set var17 = this.unionIP(var15, var16);
         this.excludedSubtreesIP = var17;
      }
   }

   public void checkExcluded(GeneralName var1) throws PKIXNameConstraintValidatorException {
      switch(var1.getTagNo()) {
      case 1:
         Set var2 = this.excludedSubtreesEmail;
         String var3 = this.extractNameAsString(var1);
         this.checkExcludedEmail(var2, var3);
         return;
      case 2:
         Set var4 = this.excludedSubtreesDNS;
         String var5 = DERIA5String.getInstance(var1.getName()).getString();
         this.checkExcludedDNS(var4, var5);
         return;
      case 3:
      case 5:
      default:
         return;
      case 4:
         ASN1Sequence var6 = ASN1Sequence.getInstance(var1.getName().getDERObject());
         this.checkExcludedDN(var6);
         return;
      case 6:
         Set var7 = this.excludedSubtreesURI;
         String var8 = DERIA5String.getInstance(var1.getName()).getString();
         this.checkExcludedURI(var7, var8);
         return;
      case 7:
         byte[] var9 = ASN1OctetString.getInstance(var1.getName()).getOctets();
         Set var10 = this.excludedSubtreesIP;
         this.checkExcludedIP(var10, var9);
      }
   }

   public void checkExcludedDN(ASN1Sequence var1) throws PKIXNameConstraintValidatorException {
      Set var2 = this.excludedSubtreesDN;
      this.checkExcludedDN(var2, var1);
   }

   public void checkPermitted(GeneralName var1) throws PKIXNameConstraintValidatorException {
      switch(var1.getTagNo()) {
      case 1:
         Set var2 = this.permittedSubtreesEmail;
         String var3 = this.extractNameAsString(var1);
         this.checkPermittedEmail(var2, var3);
         return;
      case 2:
         Set var4 = this.permittedSubtreesDNS;
         String var5 = DERIA5String.getInstance(var1.getName()).getString();
         this.checkPermittedDNS(var4, var5);
         return;
      case 3:
      case 5:
      default:
         return;
      case 4:
         ASN1Sequence var6 = ASN1Sequence.getInstance(var1.getName().getDERObject());
         this.checkPermittedDN(var6);
         return;
      case 6:
         Set var7 = this.permittedSubtreesURI;
         String var8 = DERIA5String.getInstance(var1.getName()).getString();
         this.checkPermittedURI(var7, var8);
         return;
      case 7:
         byte[] var9 = ASN1OctetString.getInstance(var1.getName()).getOctets();
         Set var10 = this.permittedSubtreesIP;
         this.checkPermittedIP(var10, var9);
      }
   }

   public void checkPermittedDN(ASN1Sequence var1) throws PKIXNameConstraintValidatorException {
      Set var2 = this.permittedSubtreesDN;
      this.checkPermittedDN(var2, var1);
   }

   public boolean equals(Object var1) {
      boolean var2;
      if(!(var1 instanceof PKIXNameConstraintValidator)) {
         var2 = false;
      } else {
         PKIXNameConstraintValidator var3 = (PKIXNameConstraintValidator)var1;
         Set var4 = var3.excludedSubtreesDN;
         Set var5 = this.excludedSubtreesDN;
         if(this.collectionsAreEqual(var4, var5)) {
            Set var6 = var3.excludedSubtreesDNS;
            Set var7 = this.excludedSubtreesDNS;
            if(this.collectionsAreEqual(var6, var7)) {
               Set var8 = var3.excludedSubtreesEmail;
               Set var9 = this.excludedSubtreesEmail;
               if(this.collectionsAreEqual(var8, var9)) {
                  Set var10 = var3.excludedSubtreesIP;
                  Set var11 = this.excludedSubtreesIP;
                  if(this.collectionsAreEqual(var10, var11)) {
                     Set var12 = var3.excludedSubtreesURI;
                     Set var13 = this.excludedSubtreesURI;
                     if(this.collectionsAreEqual(var12, var13)) {
                        Set var14 = var3.permittedSubtreesDN;
                        Set var15 = this.permittedSubtreesDN;
                        if(this.collectionsAreEqual(var14, var15)) {
                           Set var16 = var3.permittedSubtreesDNS;
                           Set var17 = this.permittedSubtreesDNS;
                           if(this.collectionsAreEqual(var16, var17)) {
                              Set var18 = var3.permittedSubtreesEmail;
                              Set var19 = this.permittedSubtreesEmail;
                              if(this.collectionsAreEqual(var18, var19)) {
                                 Set var20 = var3.permittedSubtreesIP;
                                 Set var21 = this.permittedSubtreesIP;
                                 if(this.collectionsAreEqual(var20, var21)) {
                                    Set var22 = var3.permittedSubtreesURI;
                                    Set var23 = this.permittedSubtreesURI;
                                    if(this.collectionsAreEqual(var22, var23)) {
                                       var2 = true;
                                       return var2;
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      Set var1 = this.excludedSubtreesDN;
      int var2 = this.hashCollection(var1);
      Set var3 = this.excludedSubtreesDNS;
      int var4 = this.hashCollection(var3);
      int var5 = var2 + var4;
      Set var6 = this.excludedSubtreesEmail;
      int var7 = this.hashCollection(var6);
      int var8 = var5 + var7;
      Set var9 = this.excludedSubtreesIP;
      int var10 = this.hashCollection(var9);
      int var11 = var8 + var10;
      Set var12 = this.excludedSubtreesURI;
      int var13 = this.hashCollection(var12);
      int var14 = var11 + var13;
      Set var15 = this.permittedSubtreesDN;
      int var16 = this.hashCollection(var15);
      int var17 = var14 + var16;
      Set var18 = this.permittedSubtreesDNS;
      int var19 = this.hashCollection(var18);
      int var20 = var17 + var19;
      Set var21 = this.permittedSubtreesEmail;
      int var22 = this.hashCollection(var21);
      int var23 = var20 + var22;
      Set var24 = this.permittedSubtreesIP;
      int var25 = this.hashCollection(var24);
      int var26 = var23 + var25;
      Set var27 = this.permittedSubtreesURI;
      int var28 = this.hashCollection(var27);
      return var26 + var28;
   }

   public void intersectEmptyPermittedSubtree(int var1) {
      switch(var1) {
      case 1:
         HashSet var2 = new HashSet();
         this.permittedSubtreesEmail = var2;
         return;
      case 2:
         HashSet var3 = new HashSet();
         this.permittedSubtreesDNS = var3;
         return;
      case 3:
      case 5:
      default:
         return;
      case 4:
         HashSet var4 = new HashSet();
         this.permittedSubtreesDN = var4;
         return;
      case 6:
         HashSet var5 = new HashSet();
         this.permittedSubtreesURI = var5;
         return;
      case 7:
         HashSet var6 = new HashSet();
         this.permittedSubtreesIP = var6;
      }
   }

   public void intersectPermittedSubtree(ASN1Sequence var1) {
      HashMap var2 = new HashMap();
      Enumeration var3 = var1.getObjects();

      while(var3.hasMoreElements()) {
         GeneralSubtree var4 = GeneralSubtree.getInstance(var3.nextElement());
         if(var4 != null) {
            int var5 = var4.getBase().getTagNo();
            Integer var6 = new Integer(var5);
            if(var2.get(var6) == null) {
               HashSet var7 = new HashSet();
               var2.put(var6, var7);
            }

            boolean var9 = ((Set)var2.get(var6)).add(var4);
         }
      }

      Iterator var10 = var2.entrySet().iterator();

      while(var10.hasNext()) {
         Entry var11 = (Entry)var10.next();
         switch(((Integer)var11.getKey()).intValue()) {
         case 1:
            Set var12 = this.permittedSubtreesEmail;
            Set var13 = (Set)var11.getValue();
            Set var14 = this.intersectEmail(var12, var13);
            this.permittedSubtreesEmail = var14;
            break;
         case 2:
            Set var15 = this.permittedSubtreesDNS;
            Set var16 = (Set)var11.getValue();
            Set var17 = this.intersectDNS(var15, var16);
            this.permittedSubtreesDNS = var17;
         case 3:
         case 5:
         default:
            break;
         case 4:
            Set var18 = this.permittedSubtreesDN;
            Set var19 = (Set)var11.getValue();
            Set var20 = this.intersectDN(var18, var19);
            this.permittedSubtreesDN = var20;
            break;
         case 6:
            Set var21 = this.permittedSubtreesURI;
            Set var22 = (Set)var11.getValue();
            Set var23 = this.intersectURI(var21, var22);
            this.permittedSubtreesURI = var23;
            break;
         case 7:
            Set var24 = this.permittedSubtreesIP;
            Set var25 = (Set)var11.getValue();
            Set var26 = this.intersectIP(var24, var25);
            this.permittedSubtreesIP = var26;
         }
      }

   }

   public String toString() {
      String var1 = "" + "permitted:\n";
      if(this.permittedSubtreesDN != null) {
         String var2 = var1 + "DN:\n";
         StringBuilder var3 = (new StringBuilder()).append(var2);
         String var4 = this.permittedSubtreesDN.toString();
         var1 = var3.append(var4).append("\n").toString();
      }

      if(this.permittedSubtreesDNS != null) {
         String var5 = var1 + "DNS:\n";
         StringBuilder var6 = (new StringBuilder()).append(var5);
         String var7 = this.permittedSubtreesDNS.toString();
         var1 = var6.append(var7).append("\n").toString();
      }

      if(this.permittedSubtreesEmail != null) {
         String var8 = var1 + "Email:\n";
         StringBuilder var9 = (new StringBuilder()).append(var8);
         String var10 = this.permittedSubtreesEmail.toString();
         var1 = var9.append(var10).append("\n").toString();
      }

      if(this.permittedSubtreesURI != null) {
         String var11 = var1 + "URI:\n";
         StringBuilder var12 = (new StringBuilder()).append(var11);
         String var13 = this.permittedSubtreesURI.toString();
         var1 = var12.append(var13).append("\n").toString();
      }

      if(this.permittedSubtreesIP != null) {
         String var14 = var1 + "IP:\n";
         StringBuilder var15 = (new StringBuilder()).append(var14);
         Set var16 = this.permittedSubtreesIP;
         String var17 = this.stringifyIPCollection(var16);
         var1 = var15.append(var17).append("\n").toString();
      }

      String var18 = var1 + "excluded:\n";
      if(!this.excludedSubtreesDN.isEmpty()) {
         String var19 = var18 + "DN:\n";
         StringBuilder var20 = (new StringBuilder()).append(var19);
         String var21 = this.excludedSubtreesDN.toString();
         var18 = var20.append(var21).append("\n").toString();
      }

      if(!this.excludedSubtreesDNS.isEmpty()) {
         String var22 = var18 + "DNS:\n";
         StringBuilder var23 = (new StringBuilder()).append(var22);
         String var24 = this.excludedSubtreesDNS.toString();
         var18 = var23.append(var24).append("\n").toString();
      }

      if(!this.excludedSubtreesEmail.isEmpty()) {
         String var25 = var18 + "Email:\n";
         StringBuilder var26 = (new StringBuilder()).append(var25);
         String var27 = this.excludedSubtreesEmail.toString();
         var18 = var26.append(var27).append("\n").toString();
      }

      if(!this.excludedSubtreesURI.isEmpty()) {
         String var28 = var18 + "URI:\n";
         StringBuilder var29 = (new StringBuilder()).append(var28);
         String var30 = this.excludedSubtreesURI.toString();
         var18 = var29.append(var30).append("\n").toString();
      }

      if(!this.excludedSubtreesIP.isEmpty()) {
         String var31 = var18 + "IP:\n";
         StringBuilder var32 = (new StringBuilder()).append(var31);
         Set var33 = this.excludedSubtreesIP;
         String var34 = this.stringifyIPCollection(var33);
         var18 = var32.append(var34).append("\n").toString();
      }

      return var18;
   }

   protected Set unionDNS(Set var1, String var2) {
      Object var3;
      if(var1.isEmpty()) {
         if(var2 == null) {
            var3 = var1;
         } else {
            var1.add(var2);
            var3 = var1;
         }
      } else {
         HashSet var5 = new HashSet();
         Iterator var6 = var1.iterator();

         while(var6.hasNext()) {
            String var7 = (String)var6.next();
            if(this.withinDomain(var7, var2)) {
               var5.add(var2);
            } else if(this.withinDomain(var2, var7)) {
               var5.add(var7);
            } else {
               var5.add(var7);
               var5.add(var2);
            }
         }

         var3 = var5;
      }

      return (Set)var3;
   }
}
