package myorg.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import myorg.bouncycastle.asn1.ASN1Generator;

public class BERGenerator extends ASN1Generator {

   private boolean _isExplicit;
   private int _tagNo;
   private boolean _tagged = 0;


   protected BERGenerator(OutputStream var1) {
      super(var1);
   }

   public BERGenerator(OutputStream var1, int var2, boolean var3) {
      super(var1);
      this._tagged = (boolean)1;
      this._isExplicit = var3;
      this._tagNo = var2;
   }

   private void writeHdr(int var1) throws IOException {
      this._out.write(var1);
      this._out.write(128);
   }

   public OutputStream getRawOutputStream() {
      return this._out;
   }

   protected void writeBERBody(InputStream var1) throws IOException {
      while(true) {
         int var2 = var1.read();
         if(var2 < 0) {
            return;
         }

         this._out.write(var2);
      }
   }

   protected void writeBEREnd() throws IOException {
      this._out.write(0);
      this._out.write(0);
      if(this._tagged) {
         if(this._isExplicit) {
            this._out.write(0);
            this._out.write(0);
         }
      }
   }

   protected void writeBERHeader(int var1) throws IOException {
      if(this._tagged) {
         int var2 = this._tagNo | 128;
         if(this._isExplicit) {
            int var3 = var2 | 32;
            this.writeHdr(var3);
            this.writeHdr(var1);
         } else if((var1 & 32) != 0) {
            int var4 = var2 | 32;
            this.writeHdr(var4);
         } else {
            this.writeHdr(var2);
         }
      } else {
         this.writeHdr(var1);
      }
   }
}
