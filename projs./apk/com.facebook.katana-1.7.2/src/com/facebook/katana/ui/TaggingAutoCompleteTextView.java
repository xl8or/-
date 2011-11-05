package com.facebook.katana.ui;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.AttributeSet;
import android.widget.AutoCompleteTextView;
import com.facebook.katana.DropdownFriendsAdapter;
import com.facebook.katana.binding.AppSessionListener;
import com.facebook.katana.binding.ProfileImagesCache;
import com.facebook.katana.model.FacebookProfile;
import com.facebook.katana.util.Log;
import com.facebook.katana.util.Utils;
import java.util.HashMap;
import java.util.Map;

public class TaggingAutoCompleteTextView extends AutoCompleteTextView {

   public static final int MAX_TAGS = 6;
   public static final String TAG = Utils.getClassName(TaggingAutoCompleteTextView.class);
   protected Activity mActivity;
   protected Map<String, FacebookProfile> mCachedResults;
   protected DropdownFriendsAdapter mDropdownAdapter;
   protected int mNumTags;


   public TaggingAutoCompleteTextView(Context var1, AttributeSet var2) {
      super(var1, var2);
      HashMap var3 = new HashMap();
      this.mCachedResults = var3;
      int var4 = this.getInputType() & -65537;
      this.setInputType(var4);
      this.mNumTags = 0;
   }

   public AppSessionListener configureView(Activity var1, ProfileImagesCache var2) {
      this.mActivity = var1;
      DropdownFriendsAdapter var3 = new DropdownFriendsAdapter(var1, (Cursor)null, var2);
      this.mDropdownAdapter = var3;
      DropdownFriendsAdapter var4 = this.mDropdownAdapter;
      this.setAdapter(var4);
      TaggingAutoCompleteTextView.Watcher var5 = new TaggingAutoCompleteTextView.Watcher();
      this.addTextChangedListener(var5);
      return this.mDropdownAdapter.appSessionListener;
   }

   protected CharSequence convertSelectionToString(Object var1) {
      Cursor var2 = (Cursor)var1;
      int var3 = var2.getColumnIndexOrThrow("user_id");
      int var4 = var2.getColumnIndexOrThrow("display_name");
      int var5 = var2.getColumnIndexOrThrow("user_image_url");
      long var6 = var2.getLong(var3);
      String var8 = var2.getString(var4);
      String var9 = var2.getString(var5);
      FacebookProfile var10 = new FacebookProfile(var6, var8, var9, 0);
      Map var11 = this.mCachedResults;
      String var12 = var10.mDisplayName;
      var11.put(var12, var10);
      return new TaggingAutoCompleteTextView.TaggedUser(var10);
   }

   protected void performFiltering(CharSequence var1, int var2) {
      int var3 = this.getSelectionStart();
      boolean var4 = false;
      if(this.mNumTags < 6) {
         int var5 = var1.length();
         if(var3 <= var5) {
            int var6;
            for(var6 = var3 - 1; var6 >= 0; var6 += -1) {
               if(var1.charAt(var6) == 64) {
                  var4 = true;
                  break;
               }
            }

            if(!var4) {
               this.dismissDropDown();
            } else {
               int var7 = var6 + 1;
               CharSequence var8 = var1.subSequence(var7, var3);
               if(var8.length() < 1) {
                  this.dismissDropDown();
               } else {
                  super.performFiltering(var8, var2);
               }
            }
         }
      }
   }

   protected void replaceText(CharSequence var1) {
      Editable var2 = this.getEditableText();
      if(var2 == null) {
         throw new IllegalStateException("not editable text");
      } else {
         FacebookProfile var3;
         if(var1 instanceof TaggingAutoCompleteTextView.TaggedUser) {
            var3 = ((TaggingAutoCompleteTextView.TaggedUser)var1).profile;
         } else {
            Map var7 = this.mCachedResults;
            String var8 = var1.toString();
            var3 = (FacebookProfile)var7.remove(var8);
         }

         int var4 = this.getSelectionStart();
         boolean var5 = false;

         int var6;
         for(var6 = var4 - 1; var6 >= 0; var6 += -1) {
            if(var2.charAt(var6) == 64) {
               var5 = true;
               break;
            }
         }

         if(!var5) {
            throw new IllegalStateException("attempted to complete name without \"@\" marker");
         } else {
            int var9 = var2.length();
            int var10 = var4 - var6;
            int var11 = var9 - var10;
            int var12 = var1.length();
            int var13 = var11 + var12;
            int var14 = this.mActivity.getResources().getInteger(2131230720);
            if(var13 <= var14) {
               var2.delete(var6, var4);
               var2.insert(var6, var1);
               StyleSpan var17 = new StyleSpan(1);
               int var18 = var1.length() + var6;
               var2.setSpan(var17, var6, var18, 33);
               int var19 = var1.length() + var6;
               var2.setSpan(var3, var6, var19, 33);
               int var20 = this.mNumTags + 1;
               this.mNumTags = var20;
               this.mDropdownAdapter.changeCursor((Cursor)null);
            }
         }
      }
   }

   protected class Watcher implements TextWatcher {

      protected Watcher() {}

      public void afterTextChanged(Editable var1) {
         int var2 = var1.length();
         byte var4 = 0;
         Class var6 = FacebookProfile.class;
         FacebookProfile[] var7 = (FacebookProfile[])var1.getSpans(var4, var2, var6);
         int var8 = var7.length;

         for(int var9 = 0; var9 < var8; ++var9) {
            FacebookProfile var10 = var7[var9];
            int var13 = var1.getSpanStart(var10);
            int var16 = var1.getSpanEnd(var10);
            String var17 = var10.mDisplayName;
            String var21 = var1.subSequence(var13, var16).toString();
            if(!var17.equals(var21)) {
               var1.removeSpan(var10);
               int var24 = var13 + 1;
               Class var28 = StyleSpan.class;
               StyleSpan[] var29 = (StyleSpan[])var1.getSpans(var13, var24, var28);
               int var30 = var29.length;
               byte var31 = 1;
               if(var30 != var31) {
                  Log.e(TaggingAutoCompleteTextView.TAG, "unexpected number of style spans to invalidate");
               }

               StyleSpan[] var32 = var29;
               int var33 = var29.length;

               int var38;
               for(byte var34 = 0; var34 < var33; var38 = var34 + 1) {
                  StyleSpan var35 = var32[var34];
                  var1.removeSpan(var35);
               }

               TaggingAutoCompleteTextView var39 = TaggingAutoCompleteTextView.this;
               int var40 = var39.mNumTags - 1;
               var39.mNumTags = var40;
            }
         }

      }

      public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {}

      public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {}
   }

   public static class TaggedUser implements CharSequence {

      public final FacebookProfile profile;


      public TaggedUser(FacebookProfile var1) {
         this.profile = var1;
      }

      public char charAt(int var1) {
         return this.profile.mDisplayName.charAt(var1);
      }

      public int length() {
         return this.profile.mDisplayName.length();
      }

      public CharSequence subSequence(int var1, int var2) {
         return this.profile.mDisplayName.subSequence(var1, var2);
      }

      public String toString() {
         return this.profile.mDisplayName;
      }
   }
}
