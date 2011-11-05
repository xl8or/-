package myorg.bouncycastle.asn1.x509;

import myorg.bouncycastle.asn1.ASN1Choice;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DERBMPString;
import myorg.bouncycastle.asn1.DERIA5String;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.DERUTF8String;
import myorg.bouncycastle.asn1.DERVisibleString;

public class DisplayText extends ASN1Encodable implements ASN1Choice {

   public static final int CONTENT_TYPE_BMPSTRING = 1;
   public static final int CONTENT_TYPE_IA5STRING = 0;
   public static final int CONTENT_TYPE_UTF8STRING = 2;
   public static final int CONTENT_TYPE_VISIBLESTRING = 3;
   public static final int DISPLAY_TEXT_MAXIMUM_SIZE = 200;
   int contentType;
   DERString contents;


   public DisplayText(int var1, String var2) {
      if(var2.length() > 200) {
         var2 = var2.substring(0, 200);
      }

      this.contentType = var1;
      switch(var1) {
      case 0:
         DERIA5String var4 = new DERIA5String(var2);
         this.contents = var4;
         return;
      case 1:
         DERBMPString var7 = new DERBMPString(var2);
         this.contents = var7;
         return;
      case 2:
         DERUTF8String var5 = new DERUTF8String(var2);
         this.contents = var5;
         return;
      case 3:
         DERVisibleString var6 = new DERVisibleString(var2);
         this.contents = var6;
         return;
      default:
         DERUTF8String var3 = new DERUTF8String(var2);
         this.contents = var3;
      }
   }

   public DisplayText(String var1) {
      if(var1.length() > 200) {
         var1 = var1.substring(0, 200);
      }

      this.contentType = 2;
      DERUTF8String var2 = new DERUTF8String(var1);
      this.contents = var2;
   }

   public DisplayText(DERString var1) {
      this.contents = var1;
   }

   public static DisplayText getInstance(Object var0) {
      DisplayText var2;
      if(var0 instanceof DERString) {
         DERString var1 = (DERString)var0;
         var2 = new DisplayText(var1);
      } else {
         if(!(var0 instanceof DisplayText)) {
            StringBuilder var3 = (new StringBuilder()).append("illegal object in getInstance: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         var2 = (DisplayText)var0;
      }

      return var2;
   }

   public static DisplayText getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(var0.getObject());
   }

   public String getString() {
      return this.contents.getString();
   }

   public DERObject toASN1Object() {
      return (DERObject)this.contents;
   }
}
