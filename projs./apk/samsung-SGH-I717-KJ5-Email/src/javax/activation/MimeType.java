package javax.activation;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import javax.activation.MimeTypeParameterList;
import javax.activation.MimeTypeParseException;

public class MimeType implements Externalizable {

   static final String TSPECIALS = "()<>@,;:/[]?=\\\"";
   private MimeTypeParameterList parameters;
   private String primaryType;
   private String subType;


   public MimeType() {
      this.primaryType = "application";
      this.subType = "*";
      MimeTypeParameterList var1 = new MimeTypeParameterList();
      this.parameters = var1;
   }

   public MimeType(String var1) throws MimeTypeParseException {
      this.parse(var1);
   }

   public MimeType(String var1, String var2) throws MimeTypeParseException {
      checkValidity(var1, "Primary type is invalid");
      checkValidity(var2, "Sub type is invalid");
      String var3 = var1.toLowerCase();
      this.primaryType = var3;
      String var4 = var2.toLowerCase();
      this.subType = var4;
      MimeTypeParameterList var5 = new MimeTypeParameterList();
      this.parameters = var5;
   }

   static void checkValidity(String var0, String var1) throws MimeTypeParseException {
      int var2 = var0.length();
      if(var2 == 0) {
         throw new MimeTypeParseException(var1, var0);
      } else {
         for(int var3 = 0; var3 < var2; ++var3) {
            if(!isValidChar(var0.charAt(var3))) {
               throw new MimeTypeParseException(var1, var0);
            }
         }

      }
   }

   static boolean isValidChar(char var0) {
      boolean var1;
      if(var0 > 32 && var0 <= 126 && "()<>@,;:/[]?=\\\"".indexOf(var0) == -1) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void parse(String var1) throws MimeTypeParseException {
      int var2 = var1.indexOf(47);
      int var3 = var1.indexOf(59);
      if(var2 == -1) {
         throw new MimeTypeParseException("Unable to find a sub type.");
      } else {
         if(var3 == -1) {
            String var4 = var1.substring(0, var2).toLowerCase().trim();
            this.primaryType = var4;
            int var5 = var2 + 1;
            String var6 = var1.substring(var5).toLowerCase().trim();
            this.subType = var6;
            MimeTypeParameterList var7 = new MimeTypeParameterList();
            this.parameters = var7;
         } else {
            if(var2 >= var3) {
               throw new MimeTypeParseException("Unable to find a sub type.");
            }

            String var8 = var1.substring(0, var2).toLowerCase().trim();
            this.primaryType = var8;
            int var9 = var2 + 1;
            String var10 = var1.substring(var9, var3).toLowerCase().trim();
            this.subType = var10;
            String var11 = var1.substring(var3);
            MimeTypeParameterList var12 = new MimeTypeParameterList(var11);
            this.parameters = var12;
         }

         checkValidity(this.primaryType, "Primary type is invalid");
         checkValidity(this.subType, "Sub type is invalid");
      }
   }

   public String getBaseType() {
      String var1 = this.primaryType;
      StringBuffer var2 = (new StringBuffer(var1)).append('/');
      String var3 = this.subType;
      return var2.append(var3).toString();
   }

   public String getParameter(String var1) {
      return this.parameters.get(var1);
   }

   public MimeTypeParameterList getParameters() {
      return this.parameters;
   }

   public String getPrimaryType() {
      return this.primaryType;
   }

   public String getSubType() {
      return this.subType;
   }

   public boolean match(String var1) throws MimeTypeParseException {
      MimeType var2 = new MimeType(var1);
      return this.match(var2);
   }

   public boolean match(MimeType var1) {
      String var2 = var1.getPrimaryType();
      String var3 = var1.getSubType();
      boolean var5;
      if(this.primaryType.equals(var2)) {
         label24: {
            if(!this.subType.equals(var3)) {
               String var4 = this.subType;
               if(!"*".equals(var4) && !"*".equals(var3)) {
                  break label24;
               }
            }

            var5 = true;
            return var5;
         }
      }

      var5 = false;
      return var5;
   }

   public void readExternal(ObjectInput var1) throws IOException, ClassNotFoundException {
      try {
         String var2 = var1.readUTF();
         this.parse(var2);
      } catch (MimeTypeParseException var4) {
         String var3 = var4.getMessage();
         throw new IOException(var3);
      }
   }

   public void removeParameter(String var1) {
      this.parameters.remove(var1);
   }

   public void setParameter(String var1, String var2) {
      this.parameters.set(var1, var2);
   }

   public void setPrimaryType(String var1) throws MimeTypeParseException {
      checkValidity(var1, "Primary type is invalid");
      String var2 = var1.toLowerCase();
      this.primaryType = var2;
   }

   public void setSubType(String var1) throws MimeTypeParseException {
      checkValidity(var1, "Sub type is invalid");
      String var2 = var1.toLowerCase();
      this.subType = var2;
   }

   public String toString() {
      String var1 = this.primaryType;
      StringBuffer var2 = (new StringBuffer(var1)).append('/');
      String var3 = this.subType;
      StringBuffer var4 = var2.append(var3);
      String var5 = this.parameters.toString();
      return var4.append(var5).toString();
   }

   public void writeExternal(ObjectOutput var1) throws IOException {
      String var2 = this.toString();
      var1.writeUTF(var2);
      var1.flush();
   }
}
