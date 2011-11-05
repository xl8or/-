package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class TextInputCallback implements Callback, Serializable {

   private static final long serialVersionUID = -8064222478852811804L;
   private String defaultText;
   private String inputText;
   private String prompt;


   public TextInputCallback(String var1) {
      this.setPrompt(var1);
   }

   public TextInputCallback(String var1, String var2) {
      this.setPrompt(var1);
      this.setDefaultText(var2);
   }

   private void setDefaultText(String var1) {
      if(var1 != null && var1.length() != 0) {
         this.defaultText = var1;
      } else {
         throw new IllegalArgumentException("auth.15");
      }
   }

   private void setPrompt(String var1) {
      if(var1 != null && var1.length() != 0) {
         this.prompt = var1;
      } else {
         throw new IllegalArgumentException("auth.14");
      }
   }

   public String getDefaultText() {
      return this.defaultText;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public String getText() {
      return this.inputText;
   }

   public void setText(String var1) {
      this.inputText = var1;
   }
}
