package org.apache.james.mime4j;

import java.io.IOException;
import java.io.InputStream;
import org.apache.james.mime4j.AbstractContentHandler;
import org.apache.james.mime4j.BodyDescriptor;
import org.apache.james.mime4j.decoder.Base64InputStream;
import org.apache.james.mime4j.decoder.QuotedPrintableInputStream;
import org.apache.james.mime4j.field.Field;
import org.apache.james.mime4j.message.Header;

public abstract class SimpleContentHandler extends AbstractContentHandler {

   private Header currHeader;


   public SimpleContentHandler() {}

   public final void body(BodyDescriptor var1, InputStream var2) throws IOException {
      if(var1.isBase64Encoded()) {
         Base64InputStream var3 = new Base64InputStream(var2);
         this.bodyDecoded(var1, var3);
      } else if(var1.isQuotedPrintableEncoded()) {
         QuotedPrintableInputStream var4 = new QuotedPrintableInputStream(var2);
         this.bodyDecoded(var1, var4);
      } else {
         this.bodyDecoded(var1, var2);
      }
   }

   public abstract void bodyDecoded(BodyDescriptor var1, InputStream var2) throws IOException;

   public final void endHeader() {
      Header var1 = this.currHeader;
      this.currHeader = null;
      this.headers(var1);
   }

   public final void field(String var1) {
      Header var2 = this.currHeader;
      Field var3 = Field.parse(var1);
      var2.addField(var3);
   }

   public abstract void headers(Header var1);

   public final void startHeader() {
      Header var1 = new Header();
      this.currHeader = var1;
   }
}
