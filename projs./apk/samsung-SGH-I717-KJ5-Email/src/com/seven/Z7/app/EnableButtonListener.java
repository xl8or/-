package com.seven.Z7.app;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.TextView;

public class EnableButtonListener implements TextWatcher {

   private Button mButton;
   private EnableButtonListener.OperationMode mOperationMode;
   private TextView[] mTextViews;


   public EnableButtonListener(Button var1, TextView[] var2) {
      EnableButtonListener.OperationMode var3 = EnableButtonListener.OperationMode.AND;
      this(var1, var2, var3);
   }

   public EnableButtonListener(Button var1, TextView[] var2, EnableButtonListener.OperationMode var3) {
      if(var1 != null && var2 != null) {
         this.mButton = var1;
         this.mTextViews = var2;
         this.mOperationMode = var3;
      } else {
         throw new IllegalArgumentException("Arguments cannot be null");
      }
   }

   public static void setListener(Button var0, TextView[] var1) {
      EnableButtonListener.OperationMode var2 = EnableButtonListener.OperationMode.AND;
      setListener(var0, var1, var2);
   }

   public static void setListener(Button var0, TextView[] var1, EnableButtonListener.OperationMode var2) {
      EnableButtonListener var3 = new EnableButtonListener(var0, var1, var2);
      int var4 = 0;

      while(true) {
         int var5 = var1.length;
         if(var4 >= var5) {
            return;
         }

         var1[var4].addTextChangedListener(var3);
         ++var4;
      }
   }

   public void afterTextChanged(Editable var1) {
      EnableButtonListener.OperationMode var2 = this.mOperationMode;
      EnableButtonListener.OperationMode var3 = EnableButtonListener.OperationMode.AND;
      byte var4;
      if(var2 == var3) {
         var4 = 1;
      } else {
         var4 = 0;
      }

      int var5 = 0;

      while(true) {
         int var6 = this.mTextViews.length;
         if(var5 >= var6) {
            if(this.mButton.isEnabled() == var4) {
               return;
            }

            this.mButton.setEnabled((boolean)var4);
            return;
         }

         label30: {
            int var7 = this.mTextViews[var5].getText().length();
            if(var7 > 0) {
               EnableButtonListener.OperationMode var8 = this.mOperationMode;
               EnableButtonListener.OperationMode var9 = EnableButtonListener.OperationMode.OR;
               if(var8 == var9) {
                  var4 = 1;
                  break label30;
               }
            }

            if(var7 == 0) {
               EnableButtonListener.OperationMode var10 = this.mOperationMode;
               EnableButtonListener.OperationMode var11 = EnableButtonListener.OperationMode.AND;
               if(var10 == var11) {
                  var4 = 0;
               }
            }
         }

         ++var5;
      }
   }

   public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}

   public static enum OperationMode {

      // $FF: synthetic field
      private static final EnableButtonListener.OperationMode[] $VALUES;
      AND("AND", 0),
      OR("OR", 1);


      static {
         EnableButtonListener.OperationMode[] var0 = new EnableButtonListener.OperationMode[2];
         EnableButtonListener.OperationMode var1 = AND;
         var0[0] = var1;
         EnableButtonListener.OperationMode var2 = OR;
         var0[1] = var2;
         $VALUES = var0;
      }

      private OperationMode(String var1, int var2) {}
   }
}
