package com.google.android.finsky.billing.carrierbilling.debug;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.google.android.finsky.billing.carrierbilling.debug.DcbDetail;
import com.google.android.finsky.billing.carrierbilling.debug.DcbDetailExtractor;
import com.google.android.finsky.utils.Lists;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class DcbDebugDetailsFragment extends DialogFragment {

   private final DcbDetailExtractor mDetailExtractor;
   private final String mTitle;


   public DcbDebugDetailsFragment(DcbDetailExtractor var1, String var2) {
      this.mDetailExtractor = var1;
      this.mTitle = var2;
   }

   private View buildView(AlertDialog var1) {
      Context var2 = var1.getContext();
      ListView var3 = new ListView(var2);
      Collection var4 = this.mDetailExtractor.getDetails();
      ArrayList var5 = Lists.newArrayList();
      Iterator var6 = var4.iterator();

      while(var6.hasNext()) {
         DcbDetail var7 = (DcbDetail)var6.next();
         DcbDebugDetailsFragment.DetailFormatter var8 = new DcbDebugDetailsFragment.DetailFormatter(var7);
         var5.add(var8);
      }

      Context var10 = var1.getContext();
      ArrayAdapter var11 = new ArrayAdapter(var10, 2130968609, var5);
      var3.setAdapter(var11);
      return var3;
   }

   public Dialog onCreateDialog(Bundle var1) {
      FragmentActivity var2 = this.getActivity();
      Builder var3 = new Builder(var2);
      DcbDebugDetailsFragment.1 var4 = new DcbDebugDetailsFragment.1();
      var3.setPositiveButton(2131231030, var4);
      Builder var6 = var3.setCancelable((boolean)0);
      String var7 = this.mTitle;
      var3.setTitle(var7);
      AlertDialog var9 = var3.create();
      View var10 = this.buildView(var9);
      var9.setView(var10);
      return var9;
   }

   private static class DetailFormatter {

      private final String mString;


      public DetailFormatter(DcbDetail var1) {
         StringBuilder var2 = new StringBuilder();
         String var3 = var1.getTitle();
         StringBuilder var4 = var2.append(var3).append(": ");
         String var5 = var1.getValue();
         String var6 = var4.append(var5).toString();
         this.mString = var6;
      }

      public String toString() {
         return this.mString;
      }
   }

   class 1 implements OnClickListener {

      1() {}

      public void onClick(DialogInterface var1, int var2) {
         var1.dismiss();
      }
   }
}
