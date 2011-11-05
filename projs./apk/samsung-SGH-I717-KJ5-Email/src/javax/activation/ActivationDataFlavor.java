package javax.activation;

import java.io.InputStream;
import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import myjava.awt.datatransfer.DataFlavor;

public class ActivationDataFlavor extends DataFlavor {

   private String humanPresentableName;
   private String mimeType;
   private Class representationClass;


   public ActivationDataFlavor(Class var1, String var2) {
      super(var1, var2);
      String var3 = super.getMimeType();
      this.mimeType = var3;
      this.representationClass = var1;
      this.humanPresentableName = var2;
   }

   public ActivationDataFlavor(Class var1, String var2, String var3) {
      super(var2, var3);
      this.mimeType = var2;
      this.humanPresentableName = var3;
      this.representationClass = var1;
   }

   public ActivationDataFlavor(String var1, String var2) {
      super(var1, var2);
      this.mimeType = var1;
      this.humanPresentableName = var2;
      this.representationClass = InputStream.class;
   }

   public boolean equals(DataFlavor var1) {
      boolean var4;
      if(this.isMimeTypeEqual(var1)) {
         Class var2 = var1.getRepresentationClass();
         Class var3 = this.representationClass;
         if(var2 == var3) {
            var4 = true;
            return var4;
         }
      }

      var4 = false;
      return var4;
   }

   public String getHumanPresentableName() {
      return this.humanPresentableName;
   }

   public String getMimeType() {
      return this.mimeType;
   }

   public Class getRepresentationClass() {
      return this.representationClass;
   }

   public boolean isMimeTypeEqual(String var1) {
      boolean var5;
      boolean var6;
      try {
         String var2 = this.mimeType;
         MimeType var3 = new MimeType(var2);
         MimeType var4 = new MimeType(var1);
         var5 = var3.match(var4);
      } catch (MimeTypeParseException var8) {
         var6 = false;
         return var6;
      }

      var6 = var5;
      return var6;
   }

   protected String normalizeMimeType(String var1) {
      return var1;
   }

   protected String normalizeMimeTypeParameter(String var1, String var2) {
      return var1 + '=' + var2;
   }

   public void setHumanPresentableName(String var1) {
      this.humanPresentableName = var1;
   }
}
