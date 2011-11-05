package com.android.settings;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.android.internal.widget.LockPatternUtils;
import com.android.settings.ChooseLockPattern;
import com.android.settings.ChooseLockPatternExample;

public class ChooseLockPatternTutorial extends Activity implements OnClickListener {

   private View mNextButton;
   private View mSkipButton;


   public ChooseLockPatternTutorial() {}

   private void initViews() {
      this.setContentView(2130903063);
      View var1 = this.findViewById(2131427369);
      this.mNextButton = var1;
      this.mNextButton.setOnClickListener(this);
      View var2 = this.findViewById(2131427370);
      this.mSkipButton = var2;
      this.mSkipButton.setOnClickListener(this);
   }

   public void onClick(View var1) {
      View var2 = this.mSkipButton;
      if(var1.equals(var2)) {
         this.setResult(1);
         this.finish();
      } else {
         View var3 = this.mNextButton;
         if(var1.equals(var3)) {
            Intent var4 = new Intent(this, ChooseLockPatternExample.class);
            Intent var5 = var4.addFlags(33554432);
            this.startActivity(var4);
            this.finish();
         }
      }
   }

   protected void onCreate(Bundle var1) {
      super.onCreate(var1);
      LockPatternUtils var2 = new LockPatternUtils(this);
      if(var1 == null && var2.isPatternEverChosen()) {
         Intent var3 = new Intent(this, ChooseLockPattern.class);
         Intent var4 = var3.setFlags(33554432);
         this.startActivity(var3);
         this.finish();
      } else {
         this.initViews();
      }
   }
}
