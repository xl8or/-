package com.facebook.katana.dialog;

import android.app.Dialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.facebook.katana.binding.AppSession;
import com.facebook.katana.service.method.FriendsAddFriend;

public class Dialogs {

   public Dialogs() {}

   public static Dialog addFriend(Context var0, long var1, Dialogs.AddFriendListener var3) {
      Builder var4 = new Builder(var0);
      View var5 = LayoutInflater.from(var0).inflate(2130903110, (ViewGroup)null);
      var4.setView(var5);
      Builder var7 = var4.setTitle(2131362118);
      TextView var8 = (TextView)var5.findViewById(2131624145);
      AppSession var9 = AppSession.getActiveSession(var0, (boolean)0);
      Dialogs.1 var14 = new Dialogs.1(var8, var9, var0, var1, var3);
      Dialogs.2 var15 = new Dialogs.2(var14);
      var8.setOnEditorActionListener(var15);
      String var16 = var0.getString(2131362164);
      Dialogs.3 var17 = new Dialogs.3(var14);
      var4.setPositiveButton(var16, var17);
      return var4.create();
   }

   public interface AddFriendListener {

      void onAddFriendStart(String var1);
   }

   static class 2 implements OnEditorActionListener {

      // $FF: synthetic field
      final Runnable val$friendRequestSender;


      2(Runnable var1) {
         this.val$friendRequestSender = var1;
      }

      public boolean onEditorAction(TextView var1, int var2, KeyEvent var3) {
         if(var2 == 102) {
            this.val$friendRequestSender.run();
         }

         return false;
      }
   }

   static class 3 implements OnClickListener {

      // $FF: synthetic field
      final Runnable val$friendRequestSender;


      3(Runnable var1) {
         this.val$friendRequestSender = var1;
      }

      public void onClick(DialogInterface var1, int var2) {
         this.val$friendRequestSender.run();
      }
   }

   static class 1 implements Runnable {

      // $FF: synthetic field
      final Dialogs.AddFriendListener val$addFriendListener;
      // $FF: synthetic field
      final AppSession val$appSession;
      // $FF: synthetic field
      final Context val$context;
      // $FF: synthetic field
      final TextView val$textInput;
      // $FF: synthetic field
      final long val$userId;


      1(TextView var1, AppSession var2, Context var3, long var4, Dialogs.AddFriendListener var6) {
         this.val$textInput = var1;
         this.val$appSession = var2;
         this.val$context = var3;
         this.val$userId = var4;
         this.val$addFriendListener = var6;
      }

      public void run() {
         String var1 = this.val$textInput.getText().toString().trim();
         if(var1.length() == 0) {
            var1 = null;
         }

         AppSession var2 = this.val$appSession;
         Context var3 = this.val$context;
         Long var4 = Long.valueOf(this.val$userId);
         String var5 = FriendsAddFriend.friendsAddFriend(var2, var3, var4, var1);
         this.val$addFriendListener.onAddFriendStart(var5);
      }
   }
}
