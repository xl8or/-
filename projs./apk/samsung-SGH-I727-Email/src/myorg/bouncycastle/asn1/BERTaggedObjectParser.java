package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1ParsingException;
import myorg.bouncycastle.asn1.ASN1StreamParser;
import myorg.bouncycastle.asn1.ASN1TaggedObjectParser;
import myorg.bouncycastle.asn1.BERFactory;
import myorg.bouncycastle.asn1.BEROctetStringParser;
import myorg.bouncycastle.asn1.BERSequence;
import myorg.bouncycastle.asn1.BERSequenceParser;
import myorg.bouncycastle.asn1.BERSetParser;
import myorg.bouncycastle.asn1.BERTaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERFactory;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DEROctetString;
import myorg.bouncycastle.asn1.DEROctetStringParser;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERSequenceParser;
import myorg.bouncycastle.asn1.DERSetParser;
import myorg.bouncycastle.asn1.DERTaggedObject;
import myorg.bouncycastle.asn1.DefiniteLengthInputStream;
import myorg.bouncycastle.asn1.IndefiniteLengthInputStream;

public class BERTaggedObjectParser implements ASN1TaggedObjectParser {

   private int _baseTag;
   private InputStream _contentStream;
   private boolean _indefiniteLength;
   private int _tagNumber;


   protected BERTaggedObjectParser(int var1, int var2, InputStream var3) {
      this._baseTag = var1;
      this._tagNumber = var2;
      this._contentStream = var3;
      boolean var4 = var3 instanceof IndefiniteLengthInputStream;
      this._indefiniteLength = var4;
   }

   private ASN1EncodableVector rLoadVector(InputStream var1) {
      try {
         ASN1EncodableVector var2 = (new ASN1StreamParser(var1)).readVector();
         return var2;
      } catch (IOException var5) {
         String var4 = var5.getMessage();
         throw new ASN1ParsingException(var4, var5);
      }
   }

   public DERObject getDERObject() {
      ASN1EncodableVector var2;
      Object var5;
      if(this._indefiniteLength) {
         InputStream var1 = this._contentStream;
         var2 = this.rLoadVector(var1);
         if(var2.size() == 1) {
            int var3 = this._tagNumber;
            DEREncodable var4 = var2.get(0);
            var5 = new BERTaggedObject((boolean)1, var3, var4);
         } else {
            int var6 = this._tagNumber;
            BERSequence var7 = BERFactory.createSequence(var2);
            var5 = new BERTaggedObject((boolean)0, var6, var7);
         }
      } else if(this.isConstructed()) {
         InputStream var8 = this._contentStream;
         var2 = this.rLoadVector(var8);
         if(var2.size() == 1) {
            int var9 = this._tagNumber;
            DEREncodable var10 = var2.get(0);
            var5 = new DERTaggedObject((boolean)1, var9, var10);
         } else {
            int var11 = this._tagNumber;
            DERSequence var12 = DERFactory.createSequence(var2);
            var5 = new DERTaggedObject((boolean)0, var11, var12);
         }
      } else {
         try {
            DefiniteLengthInputStream var13 = (DefiniteLengthInputStream)this._contentStream;
            int var14 = this._tagNumber;
            byte[] var15 = var13.toByteArray();
            DEROctetString var16 = new DEROctetString(var15);
            var5 = new DERTaggedObject((boolean)0, var14, var16);
         } catch (IOException var18) {
            String var17 = var18.getMessage();
            throw new IllegalStateException(var17);
         }
      }

      return (DERObject)var5;
   }

   public DEREncodable getObjectParser(int var1, boolean var2) throws IOException {
      Object var4;
      if(var2) {
         InputStream var3 = this._contentStream;
         var4 = (new ASN1StreamParser(var3)).readObject();
      } else {
         switch(var1) {
         case 4:
            if(!this._indefiniteLength && !this.isConstructed()) {
               DefiniteLengthInputStream var15 = (DefiniteLengthInputStream)this._contentStream;
               var4 = new DEROctetStringParser(var15);
            } else {
               InputStream var13 = this._contentStream;
               ASN1StreamParser var14 = new ASN1StreamParser(var13);
               var4 = new BEROctetStringParser(var14);
            }
            break;
         case 16:
            if(this._indefiniteLength) {
               InputStream var9 = this._contentStream;
               ASN1StreamParser var10 = new ASN1StreamParser(var9);
               var4 = new BERSequenceParser(var10);
            } else {
               InputStream var11 = this._contentStream;
               ASN1StreamParser var12 = new ASN1StreamParser(var11);
               var4 = new DERSequenceParser(var12);
            }
            break;
         case 17:
            if(this._indefiniteLength) {
               InputStream var5 = this._contentStream;
               ASN1StreamParser var6 = new ASN1StreamParser(var5);
               var4 = new BERSetParser(var6);
            } else {
               InputStream var7 = this._contentStream;
               ASN1StreamParser var8 = new ASN1StreamParser(var7);
               var4 = new DERSetParser(var8);
            }
            break;
         default:
            throw new RuntimeException("implicit tagging not implemented");
         }
      }

      return (DEREncodable)var4;
   }

   public int getTagNo() {
      return this._tagNumber;
   }

   public boolean isConstructed() {
      boolean var1;
      if((this._baseTag & 32) != 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }
}
