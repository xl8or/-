package org.apache.harmony.javax.security.auth.callback;

import java.io.Serializable;
import org.apache.harmony.javax.security.auth.callback.Callback;

public class ChoiceCallback implements Callback, Serializable {

   private static final long serialVersionUID = -3975664071579892167L;
   private String[] choices;
   private int defaultChoice;
   private boolean multipleSelectionsAllowed;
   private String prompt;
   private int[] selections;


   public ChoiceCallback(String var1, String[] var2, int var3, boolean var4) {
      this.setPrompt(var1);
      this.setChoices(var2);
      this.setDefaultChoice(var3);
      this.multipleSelectionsAllowed = var4;
   }

   private void setChoices(String[] var1) {
      if(var1 != null && var1.length != 0) {
         int var2 = 0;

         while(true) {
            int var3 = var1.length;
            if(var2 >= var3) {
               this.choices = var1;
               return;
            }

            if(var1[var2] == false || var1[var2].length() == 0) {
               throw new IllegalArgumentException("auth.1C");
            }

            ++var2;
         }
      } else {
         throw new IllegalArgumentException("auth.1C");
      }
   }

   private void setDefaultChoice(int var1) {
      if(var1 >= 0) {
         int var2 = this.choices.length;
         if(var1 < var2) {
            this.defaultChoice = var1;
            return;
         }
      }

      throw new IllegalArgumentException("auth.1D");
   }

   private void setPrompt(String var1) {
      if(var1 != null && var1.length() != 0) {
         this.prompt = var1;
      } else {
         throw new IllegalArgumentException("auth.14");
      }
   }

   public boolean allowMultipleSelections() {
      return this.multipleSelectionsAllowed;
   }

   public String[] getChoices() {
      return this.choices;
   }

   public int getDefaultChoice() {
      return this.defaultChoice;
   }

   public String getPrompt() {
      return this.prompt;
   }

   public int[] getSelectedIndexes() {
      return this.selections;
   }

   public void setSelectedIndex(int var1) {
      int[] var2 = new int[1];
      this.selections = var2;
      this.selections[0] = var1;
   }

   public void setSelectedIndexes(int[] var1) {
      if(!this.multipleSelectionsAllowed) {
         throw new UnsupportedOperationException();
      } else {
         this.selections = var1;
      }
   }
}
