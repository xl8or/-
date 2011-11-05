package com.android.email.smime;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.ContentProviderOperation.Builder;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.util.Base64;
import com.android.email.provider.EmailContent;
import com.android.email.smime.CertificateUtilExcpetion;
import com.android.exchange.provider.ExchangeProvider;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.cms.Attribute;
import myorg.bouncycastle.asn1.cms.AttributeTable;
import myorg.bouncycastle.cms.SignerInformation;

public class CertificateUtil {

   public static final String SMIME_CAPABILITIES_OID = "1.2.840.113549.1.9.15";
   public static final String TAG = "CertificateUtil";


   public CertificateUtil() {}

   private static CertificateUtil.EncryptionAlgorithm convertOIDtoAlgorithm(String var0, String var1) {
      CertificateUtil.EncryptionAlgorithm var2;
      if("1.2.840.113549.3.7".equalsIgnoreCase(var0)) {
         var2 = CertificateUtil.EncryptionAlgorithm.dES_EDE3_CBC;
      } else if("1.3.14.3.2.7".equalsIgnoreCase(var0)) {
         var2 = CertificateUtil.EncryptionAlgorithm.dES_CBC;
      } else {
         if("1.2.840.113549.3.2".equalsIgnoreCase(var0) && var1 != null) {
            switch(Integer.parseInt(var1)) {
            case 40:
               var2 = CertificateUtil.EncryptionAlgorithm.rC240_CBC;
               return var2;
            case 64:
               var2 = CertificateUtil.EncryptionAlgorithm.rC264_CBC;
               return var2;
            case 128:
               var2 = CertificateUtil.EncryptionAlgorithm.rC2128_CBC;
               return var2;
            }
         } else {
            if("2.16.840.1.101.3.4.1.2".equalsIgnoreCase(var0)) {
               var2 = CertificateUtil.EncryptionAlgorithm.aES128_CBC;
               return var2;
            }

            if("2.16.840.1.101.3.4.1.22".equalsIgnoreCase(var0)) {
               var2 = CertificateUtil.EncryptionAlgorithm.aES192_CBC;
               return var2;
            }

            if("2.16.840.1.101.3.4.1.42".equalsIgnoreCase(var0)) {
               var2 = CertificateUtil.EncryptionAlgorithm.aES256_CBC;
               return var2;
            }
         }

         var2 = CertificateUtil.EncryptionAlgorithm.UNKNOWN;
      }

      return var2;
   }

   public static X509Certificate convertToX509(String var0) throws CertificateUtilExcpetion {
      try {
         CertificateFactory var1 = CertificateFactory.getInstance("X.509");
         byte[] var2 = ("-----BEGIN CERTIFICATE-----\n" + var0 + "\n-----END CERTIFICATE-----").getBytes();
         ByteArrayInputStream var3 = new ByteArrayInputStream(var2);
         X509Certificate var4 = (X509Certificate)var1.generateCertificate(var3);
         return var4;
      } catch (Exception var9) {
         StringBuilder var6 = (new StringBuilder()).append("error while converting certificate. ");
         String var7 = var9.getMessage();
         String var8 = var6.append(var7).toString();
         throw new CertificateUtilExcpetion(var8);
      }
   }

   private static CertificateUtil.EncryptionAlgorithm easIdToEncryptionAlgorith(int var0) {
      CertificateUtil.EncryptionAlgorithm var1;
      switch(var0) {
      case 0:
         var1 = CertificateUtil.EncryptionAlgorithm.dES_EDE3_CBC;
         break;
      case 1:
         var1 = CertificateUtil.EncryptionAlgorithm.dES_CBC;
         break;
      case 2:
         var1 = CertificateUtil.EncryptionAlgorithm.rC2128_CBC;
         break;
      case 3:
         var1 = CertificateUtil.EncryptionAlgorithm.rC264_CBC;
         break;
      default:
         var1 = CertificateUtil.EncryptionAlgorithm.UNKNOWN;
      }

      return var1;
   }

   public static CertificateUtil.EncryptionAlgorithm evaluateBestAlghorithm(ASN1Set var0, int var1, int var2) {
      CertificateUtil.EncryptionAlgorithm var3 = easIdToEncryptionAlgorith(var1);
      CertificateUtil.EncryptionAlgorithm var23;
      if(var0 == null) {
         var23 = var3;
      } else {
         ArrayList var4 = new ArrayList();
         DEREncodable var5 = var0.getObjectAt(0);
         if(var5 instanceof ASN1Sequence) {
            ASN1Sequence var6 = (ASN1Sequence)var5;
            int var7 = 0;

            while(true) {
               int var8 = var6.size();
               if(var7 >= var8) {
                  break;
               }

               DEREncodable var9 = var6.getObjectAt(var7);
               if(var9 instanceof DERSequence) {
                  DERSequence var10 = (DERSequence)var9;
                  Object var11 = null;
                  Object var12 = null;
                  String var13;
                  String var24;
                  if(var10.size() == 1) {
                     var24 = var10.getObjectAt(0).toString();
                     var13 = (String)var12;
                  } else if(var10.size() == 2) {
                     String var16 = var10.getObjectAt(0).toString();
                     String var17 = var10.getObjectAt(1).toString();
                     var24 = var16;
                     var13 = var17;
                  } else {
                     var24 = (String)var11;
                     var13 = (String)var12;
                  }

                  if(var24 != null) {
                     CertificateUtil.EncryptionAlgorithm var25 = convertOIDtoAlgorithm(var24, var13);
                     CertificateUtil.EncryptionAlgorithm var14 = CertificateUtil.EncryptionAlgorithm.UNKNOWN;
                     if(var25 != var14) {
                        var4.add(var25);
                     }
                  }
               }

               ++var7;
            }
         }

         if(var4.size() == 0 && var1 >= 0) {
            var23 = var3;
         } else {
            if(var4.size() > 0 && var1 >= 0) {
               int var18 = CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation.DO_NOT_NEGOTIATE.ordinal();
               if(var2 == var18) {
                  if(isRequiredOnList(var4, var3)) {
                     var23 = var3;
                  } else {
                     var23 = null;
                  }

                  return var23;
               }
            }

            if(var4.size() > 0 && var1 >= 0) {
               int var19 = CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation.NEGOTIATE_STRONG.ordinal();
               if(var2 == var19) {
                  if(isRequiredOnList(var4, var3)) {
                     var23 = var3;
                     return var23;
                  } else {
                     var1 = getAlgorithmStrength(var3);
                     int var20 = 0;

                     while(true) {
                        int var21 = var4.size();
                        if(var20 >= var21) {
                           var23 = null;
                           return var23;
                        }

                        if(getAlgorithmStrength((CertificateUtil.EncryptionAlgorithm)var4.get(var20)) <= var1) {
                           var23 = (CertificateUtil.EncryptionAlgorithm)var4.get(var20);
                           return var23;
                        }

                        ++var20;
                     }
                  }
               }
            }

            if(var4.size() > 0) {
               int var22 = CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation.NEGOTIATE_ANY.ordinal();
               if(var2 == var22) {
                  if(isRequiredOnList(var4, var3)) {
                     var23 = var3;
                  } else {
                     var23 = (CertificateUtil.EncryptionAlgorithm)var4.get(0);
                  }

                  return var23;
               }
            }

            var23 = null;
         }
      }

      return var23;
   }

   private static int getAlgorithmStrength(CertificateUtil.EncryptionAlgorithm var0) {
      int[] var1 = CertificateUtil.1.$SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
      int var2 = var0.ordinal();
      byte var3;
      switch(var1[var2]) {
      case 1:
      case 2:
         var3 = 0;
         break;
      case 3:
      case 4:
         var3 = 1;
         break;
      case 5:
      case 6:
         var3 = 2;
         break;
      case 7:
         var3 = 3;
         break;
      case 8:
         var3 = 4;
         break;
      default:
         var3 = 100;
      }

      return var3;
   }

   public static ArrayList<CertificateUtil.RecipientCertificate> getCertificates(Address[] var0, long var1, Context var3) throws CertificateUtilExcpetion {
      ArrayList var4 = new ArrayList();
      InternetAddress[] var5 = new InternetAddress[var0.length];
      int var6 = 0;

      while(true) {
         int var7 = var0.length;
         if(var6 >= var7) {
            StringBuilder var9 = new StringBuilder();
            StringBuilder var10 = var9.append("accoundId=?").append(" AND ");
            String[] var11 = new String[var5.length + 1];
            String var12 = Long.toString(var1);
            var11[0] = var12;
            ArrayList var54;
            if(var5.length > 1) {
               int var13 = 0;

               while(true) {
                  int var14 = var5.length;
                  if(var13 >= var14) {
                     break;
                  }

                  if(var13 == 0) {
                     StringBuilder var15 = var9.append("(to=?");
                  } else if(var13 >= 1) {
                     StringBuilder var20 = var9.append(" OR to=?");
                  }

                  int var16 = var5.length - 1;
                  if(var13 == var16) {
                     StringBuilder var17 = var9.append(")");
                  }

                  int var18 = var13 + 1;
                  String var19 = var5[var13].getAddress();
                  var11[var18] = var19;
                  ++var13;
               }
            } else {
               if(var5.length != 1) {
                  var54 = var4;
                  return var54;
               }

               StringBuilder var21 = var9.append("to=?");
               String var22 = var5[0].getAddress();
               var11[1] = var22;
            }

            String[] var23 = new String[]{"to", "email", "certificate"};
            ContentResolver var24 = var3.getContentResolver();
            Uri var25 = ExchangeProvider.RESOLVERECIPIENTS_URI;
            String var26 = var9.toString();
            Cursor var27 = var24.query(var25, var23, var26, var11, (String)null);
            ArrayList var52 = new ArrayList();
            StringBuilder var28 = new StringBuilder();
            var5 = null;

            while(var27 != null && var27.moveToNext()) {
               String var53 = var27.getString(0);
               String var29 = var27.getString(1);
               String var30 = var27.getString(2);
               if(var5 != null) {
                  StringBuilder var31 = var28.append("email").append(" = \'").append(var29).append("\' ");
                  var5 = null;
               } else {
                  StringBuilder var40 = var28.append(" OR ").append("email").append(" = \'").append(var29).append("\' ");
               }

               Builder var32 = ContentProviderOperation.newInsert(EmailContent.CertificateCacheColumns.CONTENT_URI);
               ContentValues var33 = new ContentValues();
               var33.put("email", var29);
               var33.put("certificate", var30);
               ContentProviderOperation var34 = var32.withValues(var33).build();
               var52.add(var34);
               if(var30 != null && var30.length() > 0) {
                  X509Certificate var36 = convertToX509(var30);
                  CertificateUtil.RecipientCertificate var37 = new CertificateUtil.RecipientCertificate(var29, var36);
                  var4.add(var37);
               } else if(var29 != null) {
                  CertificateUtil.RecipientCertificate var41 = new CertificateUtil.RecipientCertificate(var29, (X509Certificate)null);
                  var4.add(var41);
               } else {
                  CertificateUtil.RecipientCertificate var43 = new CertificateUtil.RecipientCertificate(var53, (X509Certificate)null);
                  var4.add(var43);
               }
            }

            if(var27 != null) {
               var27.close();
            }

            if(var52.size() > 0) {
               try {
                  ContentResolver var45 = var3.getContentResolver();
                  Uri var46 = EmailContent.CertificateCacheColumns.CONTENT_URI;
                  String var47 = var28.toString();
                  var45.delete(var46, var47, (String[])null);
                  ContentProviderResult[] var49 = var3.getContentResolver().applyBatch("com.android.email.provider", var52);
               } catch (RemoteException var50) {
                  var50.printStackTrace();
               } catch (OperationApplicationException var51) {
                  var51.printStackTrace();
               }
            }

            var54 = var4;
            return var54;
         }

         InternetAddress var8 = (InternetAddress)var0[var6];
         var5[var6] = var8;
         ++var6;
      }
   }

   public static ASN1Set getSignerCapabilities(SignerInformation var0) {
      AttributeTable var1 = var0.getSignedAttributes();
      DERObjectIdentifier var2 = new DERObjectIdentifier("1.2.840.113549.1.9.15");
      Attribute var3 = var1.get(var2);
      ASN1Set var4;
      if(var3 == null) {
         var4 = null;
      } else {
         var4 = var3.getAttrValues();
      }

      return var4;
   }

   private static boolean isRequiredOnList(ArrayList<CertificateUtil.EncryptionAlgorithm> var0, CertificateUtil.EncryptionAlgorithm var1) {
      int var2 = 0;

      boolean var4;
      while(true) {
         int var3 = var0.size();
         if(var2 >= var3) {
            var4 = false;
            break;
         }

         if(var0.get(var2) == var1) {
            var4 = true;
            break;
         }

         ++var2;
      }

      return var4;
   }

   public static ArrayList<Boolean> validateCertificates(CertificateUtil.RecipientCertificate[] var0, long var1, Context var3) throws IllegalArgumentException {
      ArrayList var4 = new ArrayList();
      StringBuilder var5 = new StringBuilder();
      StringBuilder var6 = var5.append("accountId=?");
      ArrayList var7 = new ArrayList();
      String var8 = String.valueOf(var1);
      var7.add(var8);
      int var10 = 0;

      ArrayList var27;
      while(true) {
         label41: {
            try {
               int var11 = var0.length;
               if(var10 < var11) {
                  StringBuilder var12 = var5.append(" AND certificates=\'?\'");
                  String var13 = Base64.encodeToString(var0[var10].mCertificate.getEncoded(), 2);
                  var7.add(var13);
                  break label41;
               }
            } catch (CertificateEncodingException var26) {
               var26.printStackTrace();
               var27 = null;
               break;
            }

            StringBuilder var15 = var5.append(" AND checkCRL=\'?\'");
            boolean var16 = var7.add("true");
            ContentResolver var17 = var3.getContentResolver();
            Uri var18 = ExchangeProvider.VALIDATE_CERT_URI;
            String var19 = var5.toString();
            String[] var20 = new String[0];
            String[] var21 = (String[])var7.toArray(var20);
            Cursor var22 = var17.query(var18, (String[])null, var19, var21, (String)null);
            if(var22 != null) {
               while(var22.moveToNext()) {
                  byte var23;
                  if(var22.getInt(0) == 1) {
                     var23 = 1;
                  } else {
                     var23 = 0;
                  }

                  Boolean var24 = Boolean.valueOf((boolean)var23);
                  var4.add(var24);
               }

               var22.close();
            }

            var27 = var4;
            break;
         }

         ++var10;
      }

      return var27;
   }

   // $FF: synthetic class
   static class 1 {

      // $FF: synthetic field
      static final int[] $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm = new int[CertificateUtil.EncryptionAlgorithm.values().length];


      static {
         try {
            int[] var0 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var1 = CertificateUtil.EncryptionAlgorithm.dES_EDE3_CBC.ordinal();
            var0[var1] = 1;
         } catch (NoSuchFieldError var35) {
            ;
         }

         try {
            int[] var2 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var3 = CertificateUtil.EncryptionAlgorithm.dES_CBC.ordinal();
            var2[var3] = 2;
         } catch (NoSuchFieldError var34) {
            ;
         }

         try {
            int[] var4 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var5 = CertificateUtil.EncryptionAlgorithm.aES256_CBC.ordinal();
            var4[var5] = 3;
         } catch (NoSuchFieldError var33) {
            ;
         }

         try {
            int[] var6 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var7 = CertificateUtil.EncryptionAlgorithm.aES192_CBC.ordinal();
            var6[var7] = 4;
         } catch (NoSuchFieldError var32) {
            ;
         }

         try {
            int[] var8 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var9 = CertificateUtil.EncryptionAlgorithm.rC2128_CBC.ordinal();
            var8[var9] = 5;
         } catch (NoSuchFieldError var31) {
            ;
         }

         try {
            int[] var10 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var11 = CertificateUtil.EncryptionAlgorithm.aES128_CBC.ordinal();
            var10[var11] = 6;
         } catch (NoSuchFieldError var30) {
            ;
         }

         try {
            int[] var12 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var13 = CertificateUtil.EncryptionAlgorithm.rC264_CBC.ordinal();
            var12[var13] = 7;
         } catch (NoSuchFieldError var29) {
            ;
         }

         try {
            int[] var14 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var15 = CertificateUtil.EncryptionAlgorithm.rC240_CBC.ordinal();
            var14[var15] = 8;
         } catch (NoSuchFieldError var28) {
            ;
         }

         try {
            int[] var16 = $SwitchMap$com$android$email$smime$CertificateUtil$EncryptionAlgorithm;
            int var17 = CertificateUtil.EncryptionAlgorithm.UNKNOWN.ordinal();
            var16[var17] = 9;
         } catch (NoSuchFieldError var27) {
            ;
         }
      }
   }

   public static enum EncryptionAlgorithm {

      // $FF: synthetic field
      private static final CertificateUtil.EncryptionAlgorithm[] $VALUES;
      UNKNOWN("UNKNOWN", 0),
      aES128_CBC("aES128_CBC", 6),
      aES192_CBC("aES192_CBC", 7),
      aES256_CBC("aES256_CBC", 8),
      dES_CBC("dES_CBC", 1),
      dES_EDE3_CBC("dES_EDE3_CBC", 2),
      rC2128_CBC("rC2128_CBC", 3),
      rC240_CBC("rC240_CBC", 5),
      rC264_CBC("rC264_CBC", 4);


      static {
         CertificateUtil.EncryptionAlgorithm[] var0 = new CertificateUtil.EncryptionAlgorithm[9];
         CertificateUtil.EncryptionAlgorithm var1 = UNKNOWN;
         var0[0] = var1;
         CertificateUtil.EncryptionAlgorithm var2 = dES_CBC;
         var0[1] = var2;
         CertificateUtil.EncryptionAlgorithm var3 = dES_EDE3_CBC;
         var0[2] = var3;
         CertificateUtil.EncryptionAlgorithm var4 = rC2128_CBC;
         var0[3] = var4;
         CertificateUtil.EncryptionAlgorithm var5 = rC264_CBC;
         var0[4] = var5;
         CertificateUtil.EncryptionAlgorithm var6 = rC240_CBC;
         var0[5] = var6;
         CertificateUtil.EncryptionAlgorithm var7 = aES128_CBC;
         var0[6] = var7;
         CertificateUtil.EncryptionAlgorithm var8 = aES192_CBC;
         var0[7] = var8;
         CertificateUtil.EncryptionAlgorithm var9 = aES256_CBC;
         var0[8] = var9;
         $VALUES = var0;
      }

      private EncryptionAlgorithm(String var1, int var2) {}
   }

   public static enum AllowSMIMEEncryptionAlgorithmNegotiation {

      // $FF: synthetic field
      private static final CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation[] $VALUES;
      DO_NOT_NEGOTIATE("DO_NOT_NEGOTIATE", 0),
      NEGOTIATE_ANY("NEGOTIATE_ANY", 2),
      NEGOTIATE_STRONG("NEGOTIATE_STRONG", 1);


      static {
         CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation[] var0 = new CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation[3];
         CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation var1 = DO_NOT_NEGOTIATE;
         var0[0] = var1;
         CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation var2 = NEGOTIATE_STRONG;
         var0[1] = var2;
         CertificateUtil.AllowSMIMEEncryptionAlgorithmNegotiation var3 = NEGOTIATE_ANY;
         var0[2] = var3;
         $VALUES = var0;
      }

      private AllowSMIMEEncryptionAlgorithmNegotiation(String var1, int var2) {}
   }

   public static class RecipientCertificate {

      public X509Certificate mCertificate;
      public String mEmailAddress;


      public RecipientCertificate(String var1, X509Certificate var2) {
         this.mEmailAddress = var1;
         this.mCertificate = var2;
      }

      public X509Certificate getCertificate() {
         return this.mCertificate;
      }

      public String getEmailAddress() {
         return this.mEmailAddress;
      }
   }
}
