package myorg.bouncycastle.asn1.util;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Enumeration;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1OctetString;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.BERApplicationSpecific;
import myorg.bouncycastle.asn1.BERConstructedOctetString;
import myorg.bouncycastle.asn1.BERConstructedSequence;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERSet;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DERApplicationSpecific;
import myorg.bouncycastle.asn1.DERBMPString;
import myorg.bouncycastle.asn1.DERBitString;
import myorg.bouncycastle.asn1.DERBoolean;
import myorg.bouncycastle.asn1.DERConstructedSequence;
import myorg.bouncycastle.asn1.DERConstructedSet;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DEREnumerated;
import myorg.bouncycastle.asn1.DERExternal;
import myorg.bouncycastle.asn1.DERGeneralizedTime;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERInteger;
import myorg.bouncycastle.asn1.DERNull;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DERPrintableString;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.DERT61String;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DERUTCTime;
import myorg.bouncycastle.asn1.DERUTF8String;
import myorg.bouncycastle.asn1.DERUnknownTag;
import myorg.bouncycastle.asn1.DERVisibleString;
import myorg.bouncycastle.util.encoders.Hex;

public class ASN1Dump {

   private static final int SAMPLE_SIZE = 32;
   private static final String TAB = "    ";


   public ASN1Dump() {}

   static void _dumpAsString(String var0, boolean var1, DERObject var2, StringBuffer var3) {
      String var4 = System.getProperty("line.separator");
      if(!(var2 instanceof ASN1Sequence)) {
         String var171;
         if(var2 instanceof DERTaggedObject) {
            var171 = var0 + "    ";
            var3.append(var0);
            if(var2 instanceof BERTaggedObject) {
               StringBuffer var22 = var3.append("BER Tagged [");
            } else {
               StringBuffer var32 = var3.append("Tagged [");
            }

            DERTaggedObject var23 = (DERTaggedObject)var2;
            String var24 = Integer.toString(var23.getTagNo());
            var3.append(var24);
            StringBuffer var26 = var3.append(']');
            if(!var23.isExplicit()) {
               StringBuffer var27 = var3.append(" IMPLICIT ");
            }

            var3.append(var4);
            if(var23.isEmpty()) {
               var3.append(var171);
               StringBuffer var30 = var3.append("EMPTY");
               var3.append(var4);
            } else {
               DERObject var33 = var23.getObject();
               _dumpAsString(var171, var1, var33, var3);
            }
         } else {
            Enumeration var170;
            if(var2 instanceof DERConstructedSet) {
               var170 = ((ASN1Set)var2).getObjects();
               var171 = var0 + "    ";
               var3.append(var0);
               StringBuffer var35 = var3.append("ConstructedSet");
               var3.append(var4);

               while(var170.hasMoreElements()) {
                  Object var37 = var170.nextElement();
                  if(var37 == null) {
                     var3.append(var171);
                     StringBuffer var39 = var3.append("NULL");
                     var3.append(var4);
                  } else if(var37 instanceof DERObject) {
                     DERObject var41 = (DERObject)var37;
                     _dumpAsString(var171, var1, var41, var3);
                  } else {
                     DERObject var42 = ((DEREncodable)var37).getDERObject();
                     _dumpAsString(var171, var1, var42, var3);
                  }
               }

            } else if(var2 instanceof BERSet) {
               var170 = ((ASN1Set)var2).getObjects();
               var171 = var0 + "    ";
               var3.append(var0);
               StringBuffer var44 = var3.append("BER Set");
               var3.append(var4);

               while(var170.hasMoreElements()) {
                  Object var46 = var170.nextElement();
                  if(var46 == null) {
                     var3.append(var171);
                     StringBuffer var48 = var3.append("NULL");
                     var3.append(var4);
                  } else if(var46 instanceof DERObject) {
                     DERObject var50 = (DERObject)var46;
                     _dumpAsString(var171, var1, var50, var3);
                  } else {
                     DERObject var51 = ((DEREncodable)var46).getDERObject();
                     _dumpAsString(var171, var1, var51, var3);
                  }
               }

            } else if(var2 instanceof DERSet) {
               var170 = ((ASN1Set)var2).getObjects();
               var171 = var0 + "    ";
               var3.append(var0);
               StringBuffer var53 = var3.append("DER Set");
               var3.append(var4);

               while(var170.hasMoreElements()) {
                  Object var55 = var170.nextElement();
                  if(var55 == null) {
                     var3.append(var171);
                     StringBuffer var57 = var3.append("NULL");
                     var3.append(var4);
                  } else if(var55 instanceof DERObject) {
                     DERObject var59 = (DERObject)var55;
                     _dumpAsString(var171, var1, var59, var3);
                  } else {
                     DERObject var60 = ((DEREncodable)var55).getDERObject();
                     _dumpAsString(var171, var1, var60, var3);
                  }
               }

            } else if(var2 instanceof DERObjectIdentifier) {
               StringBuilder var61 = (new StringBuilder()).append(var0).append("ObjectIdentifier(");
               String var62 = ((DERObjectIdentifier)var2).getId();
               String var63 = var61.append(var62).append(")").append(var4).toString();
               var3.append(var63);
            } else if(var2 instanceof DERBoolean) {
               StringBuilder var65 = (new StringBuilder()).append(var0).append("Boolean(");
               boolean var66 = ((DERBoolean)var2).isTrue();
               String var67 = var65.append(var66).append(")").append(var4).toString();
               var3.append(var67);
            } else if(var2 instanceof DERInteger) {
               StringBuilder var69 = (new StringBuilder()).append(var0).append("Integer(");
               BigInteger var70 = ((DERInteger)var2).getValue();
               String var71 = var69.append(var70).append(")").append(var4).toString();
               var3.append(var71);
            } else {
               ASN1OctetString var169;
               if(var2 instanceof BERConstructedOctetString) {
                  var169 = (ASN1OctetString)var2;
                  StringBuilder var73 = (new StringBuilder()).append(var0).append("BER Constructed Octet String").append("[");
                  int var74 = var169.getOctets().length;
                  String var75 = var73.append(var74).append("] ").toString();
                  var3.append(var75);
                  if(var1) {
                     byte[] var77 = var169.getOctets();
                     String var78 = dumpBinaryDataAsString(var0, var77);
                     var3.append(var78);
                  } else {
                     var3.append(var4);
                  }
               } else if(var2 instanceof DEROctetString) {
                  var169 = (ASN1OctetString)var2;
                  StringBuilder var81 = (new StringBuilder()).append(var0).append("DER Octet String").append("[");
                  int var82 = var169.getOctets().length;
                  String var83 = var81.append(var82).append("] ").toString();
                  var3.append(var83);
                  if(var1) {
                     byte[] var85 = var169.getOctets();
                     String var86 = dumpBinaryDataAsString(var0, var85);
                     var3.append(var86);
                  } else {
                     var3.append(var4);
                  }
               } else if(var2 instanceof DERBitString) {
                  DERBitString var168 = (DERBitString)var2;
                  StringBuilder var89 = (new StringBuilder()).append(var0).append("DER Bit String").append("[");
                  int var90 = var168.getBytes().length;
                  StringBuilder var91 = var89.append(var90).append(", ");
                  int var92 = var168.getPadBits();
                  String var93 = var91.append(var92).append("] ").toString();
                  var3.append(var93);
                  if(var1) {
                     byte[] var95 = var168.getBytes();
                     String var96 = dumpBinaryDataAsString(var0, var95);
                     var3.append(var96);
                  } else {
                     var3.append(var4);
                  }
               } else if(var2 instanceof DERIA5String) {
                  StringBuilder var99 = (new StringBuilder()).append(var0).append("IA5String(");
                  String var100 = ((DERIA5String)var2).getString();
                  String var101 = var99.append(var100).append(") ").append(var4).toString();
                  var3.append(var101);
               } else if(var2 instanceof DERUTF8String) {
                  StringBuilder var103 = (new StringBuilder()).append(var0).append("UTF8String(");
                  String var104 = ((DERUTF8String)var2).getString();
                  String var105 = var103.append(var104).append(") ").append(var4).toString();
                  var3.append(var105);
               } else if(var2 instanceof DERPrintableString) {
                  StringBuilder var107 = (new StringBuilder()).append(var0).append("PrintableString(");
                  String var108 = ((DERPrintableString)var2).getString();
                  String var109 = var107.append(var108).append(") ").append(var4).toString();
                  var3.append(var109);
               } else if(var2 instanceof DERVisibleString) {
                  StringBuilder var111 = (new StringBuilder()).append(var0).append("VisibleString(");
                  String var112 = ((DERVisibleString)var2).getString();
                  String var113 = var111.append(var112).append(") ").append(var4).toString();
                  var3.append(var113);
               } else if(var2 instanceof DERBMPString) {
                  StringBuilder var115 = (new StringBuilder()).append(var0).append("BMPString(");
                  String var116 = ((DERBMPString)var2).getString();
                  String var117 = var115.append(var116).append(") ").append(var4).toString();
                  var3.append(var117);
               } else if(var2 instanceof DERT61String) {
                  StringBuilder var119 = (new StringBuilder()).append(var0).append("T61String(");
                  String var120 = ((DERT61String)var2).getString();
                  String var121 = var119.append(var120).append(") ").append(var4).toString();
                  var3.append(var121);
               } else if(var2 instanceof DERUTCTime) {
                  StringBuilder var123 = (new StringBuilder()).append(var0).append("UTCTime(");
                  String var124 = ((DERUTCTime)var2).getTime();
                  String var125 = var123.append(var124).append(") ").append(var4).toString();
                  var3.append(var125);
               } else if(var2 instanceof DERGeneralizedTime) {
                  StringBuilder var127 = (new StringBuilder()).append(var0).append("GeneralizedTime(");
                  String var128 = ((DERGeneralizedTime)var2).getTime();
                  String var129 = var127.append(var128).append(") ").append(var4).toString();
                  var3.append(var129);
               } else if(var2 instanceof DERUnknownTag) {
                  StringBuilder var131 = (new StringBuilder()).append(var0).append("Unknown ");
                  String var132 = Integer.toString(((DERUnknownTag)var2).getTag(), 16);
                  StringBuilder var133 = var131.append(var132).append(" ");
                  byte[] var134 = Hex.encode(((DERUnknownTag)var2).getData());
                  String var135 = new String(var134);
                  String var136 = var133.append(var135).append(var4).toString();
                  var3.append(var136);
               } else if(var2 instanceof BERApplicationSpecific) {
                  String var138 = outputApplicationSpecific("BER", var0, var1, var2, var4);
                  var3.append(var138);
               } else if(var2 instanceof DERApplicationSpecific) {
                  String var140 = outputApplicationSpecific("DER", var0, var1, var2, var4);
                  var3.append(var140);
               } else if(var2 instanceof DEREnumerated) {
                  DEREnumerated var142 = (DEREnumerated)var2;
                  StringBuilder var143 = (new StringBuilder()).append(var0).append("DER Enumerated(");
                  BigInteger var144 = var142.getValue();
                  String var145 = var143.append(var144).append(")").append(var4).toString();
                  var3.append(var145);
               } else if(var2 instanceof DERExternal) {
                  DERExternal var147 = (DERExternal)var2;
                  String var148 = var0 + "External " + var4;
                  var3.append(var148);
                  var0 = var0 + "    ";
                  if(var147.getDirectReference() != null) {
                     StringBuilder var150 = (new StringBuilder()).append(var0).append("Direct Reference: ");
                     String var151 = var147.getDirectReference().getId();
                     String var152 = var150.append(var151).append(var4).toString();
                     var3.append(var152);
                  }

                  if(var147.getIndirectReference() != null) {
                     StringBuilder var154 = (new StringBuilder()).append(var0).append("Indirect Reference: ");
                     String var155 = var147.getIndirectReference().toString();
                     String var156 = var154.append(var155).append(var4).toString();
                     var3.append(var156);
                  }

                  if(var147.getDataValueDescriptor() != null) {
                     ASN1Object var158 = var147.getDataValueDescriptor();
                     _dumpAsString(var0, var1, var158, var3);
                  }

                  StringBuilder var159 = (new StringBuilder()).append(var0).append("Encoding: ");
                  int var160 = var147.getEncoding();
                  String var161 = var159.append(var160).append(var4).toString();
                  var3.append(var161);
                  DERObject var163 = var147.getExternalContent();
                  _dumpAsString(var0, var1, var163, var3);
               } else {
                  StringBuilder var164 = (new StringBuilder()).append(var0);
                  String var165 = var2.toString();
                  String var166 = var164.append(var165).append(var4).toString();
                  var3.append(var166);
               }
            }
         }
      } else {
         Enumeration var5 = ((ASN1Sequence)var2).getObjects();
         String var6 = var0 + "    ";
         var3.append(var0);
         if(var2 instanceof BERConstructedSequence) {
            StringBuffer var8 = var3.append("BER ConstructedSequence");
         } else if(var2 instanceof DERConstructedSequence) {
            StringBuffer var15 = var3.append("DER ConstructedSequence");
         } else if(var2 instanceof BERSequence) {
            StringBuffer var16 = var3.append("BER Sequence");
         } else if(var2 instanceof DERSequence) {
            StringBuffer var17 = var3.append("DER Sequence");
         } else {
            StringBuffer var18 = var3.append("Sequence");
         }

         var3.append(var4);

         while(var5.hasMoreElements()) {
            Object var10 = var5.nextElement();
            if(var10 != null) {
               DERNull var11 = new DERNull();
               if(!var10.equals(var11)) {
                  if(var10 instanceof DERObject) {
                     DERObject var19 = (DERObject)var10;
                     _dumpAsString(var6, var1, var19, var3);
                  } else {
                     DERObject var20 = ((DEREncodable)var10).getDERObject();
                     _dumpAsString(var6, var1, var20, var3);
                  }
                  continue;
               }
            }

            var3.append(var6);
            StringBuffer var13 = var3.append("NULL");
            var3.append(var4);
         }

      }
   }

   private static String calculateAscString(byte[] var0, int var1, int var2) {
      StringBuffer var3 = new StringBuffer();
      int var4 = var1;

      while(true) {
         int var5 = var1 + var2;
         if(var4 == var5) {
            return var3.toString();
         }

         if(var0[var4] >= 32 && var0[var4] <= 126) {
            char var6 = (char)var0[var4];
            var3.append(var6);
         }

         ++var4;
      }
   }

   public static String dumpAsString(Object var0) {
      return dumpAsString(var0, (boolean)0);
   }

   public static String dumpAsString(Object var0, boolean var1) {
      StringBuffer var2 = new StringBuffer();
      String var4;
      if(var0 instanceof DERObject) {
         DERObject var3 = (DERObject)var0;
         _dumpAsString("", var1, var3, var2);
      } else {
         if(!(var0 instanceof DEREncodable)) {
            StringBuilder var6 = (new StringBuilder()).append("unknown object type ");
            String var7 = var0.toString();
            var4 = var6.append(var7).toString();
            return var4;
         }

         DERObject var5 = ((DEREncodable)var0).getDERObject();
         _dumpAsString("", var1, var5, var2);
      }

      var4 = var2.toString();
      return var4;
   }

   private static String dumpBinaryDataAsString(String var0, byte[] var1) {
      String var2 = System.getProperty("line.separator");
      StringBuffer var3 = new StringBuffer();
      String var4 = var0 + "    ";
      var3.append(var2);
      int var6 = 0;

      while(true) {
         int var7 = var1.length;
         if(var6 >= var7) {
            return var3.toString();
         }

         if(var1.length - var6 > 32) {
            var3.append(var4);
            byte[] var9 = Hex.encode(var1, var6, 32);
            String var10 = new String(var9);
            var3.append(var10);
            StringBuffer var12 = var3.append("    ");
            String var13 = calculateAscString(var1, var6, 32);
            var3.append(var13);
            var3.append(var2);
         } else {
            var3.append(var4);
            int var17 = var1.length - var6;
            byte[] var18 = Hex.encode(var1, var6, var17);
            String var19 = new String(var18);
            var3.append(var19);

            for(int var21 = var1.length - var6; var21 != 32; ++var21) {
               StringBuffer var22 = var3.append("  ");
            }

            StringBuffer var23 = var3.append("    ");
            int var24 = var1.length - var6;
            String var25 = calculateAscString(var1, var6, var24);
            var3.append(var25);
            var3.append(var2);
         }

         var6 += 32;
      }
   }

   private static String outputApplicationSpecific(String var0, String var1, boolean var2, DERObject var3, String var4) {
      DERApplicationSpecific var5 = (DERApplicationSpecific)var3;
      StringBuffer var6 = new StringBuffer();
      String var18;
      if(var5.isConstructed()) {
         byte var7 = 16;

         try {
            ASN1Sequence var8 = ASN1Sequence.getInstance(var5.getObject(var7));
            StringBuilder var9 = (new StringBuilder()).append(var1).append(var0).append(" ApplicationSpecific[");
            int var10 = var5.getApplicationTag();
            String var11 = var9.append(var10).append("]").append(var4).toString();
            var6.append(var11);
            Enumeration var13 = var8.getObjects();

            while(var13.hasMoreElements()) {
               String var14 = var1 + "    ";
               DERObject var15 = (DERObject)var13.nextElement();
               _dumpAsString(var14, var2, var15, var6);
            }
         } catch (IOException var24) {
            var6.append(var24);
         }

         var18 = var6.toString();
      } else {
         StringBuilder var19 = (new StringBuilder()).append(var1).append(var0).append(" ApplicationSpecific[");
         int var20 = var5.getApplicationTag();
         StringBuilder var21 = var19.append(var20).append("] (");
         byte[] var22 = Hex.encode(var5.getContents());
         String var23 = new String(var22);
         var18 = var21.append(var23).append(")").append(var4).toString();
      }

      return var18;
   }
}
