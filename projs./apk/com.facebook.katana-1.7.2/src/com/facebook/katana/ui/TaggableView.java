package com.facebook.katana.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.FrameLayout.LayoutParams;
import com.facebook.katana.model.FacebookPhotoTag;
import com.facebook.katana.ui.TagSuggestionView;
import com.facebook.katana.ui.TagView;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class TaggableView extends FrameLayout implements OnGestureListener {

   private Context mContext;
   private GestureDetector mGestureDetector;
   private int mLastHeight;
   private int mLastWidth;
   private TaggableView.TaggableViewListener mListener;
   private ArrayList<TagSuggestionView> mSuggestions;
   private HashMap<Long, TagView> mTags;
   private int mTextId;


   public TaggableView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.mContext = var1;
      View var3 = ((LayoutInflater)var1.getSystemService("layout_inflater")).inflate(2130903172, this);
      GestureDetector var4 = new GestureDetector(this);
      this.mGestureDetector = var4;
      HashMap var5 = new HashMap();
      this.mTags = var5;
      ArrayList var6 = new ArrayList();
      this.mSuggestions = var6;
      this.mTextId = -1;
      ViewTreeObserver var7 = this.getViewTreeObserver();
      TaggableView.1 var8 = new TaggableView.1();
      var7.addOnGlobalLayoutListener(var8);
   }

   private int getTagPositionX(TagView var1) {
      ImageView var2 = (ImageView)this.findViewById(2131624165);
      float var3 = var1.x;
      float var4 = (float)var2.getWidth();
      int var5 = (int)(var3 * var4);
      int var6 = var1.getFullWidth();
      int var7 = var6 / 2;
      int var8 = var5 - var7;
      int var9 = var8 + var6;
      int var10 = this.mLastWidth;
      if(var9 > var10) {
         var8 = this.mLastWidth - var6;
      }

      if(var8 < 0) {
         var8 = 0;
      }

      return var8;
   }

   private int getTagPositionY(TagView var1) {
      ImageView var2 = (ImageView)this.findViewById(2131624165);
      float var3 = var1.y;
      float var4 = (float)var2.getHeight();
      int var5 = (int)(var3 * var4);
      int var6 = var1.getFullHeight();
      int var7 = var5 + var6;
      int var8 = this.mLastHeight;
      if(var7 > var8) {
         var5 = this.mLastHeight - var6;
      }

      return var5;
   }

   private int getTagSuggestionPositionX(TagSuggestionView var1) {
      ImageView var2 = (ImageView)this.findViewById(2131624165);
      float var3 = var1.getX();
      float var4 = (float)var2.getWidth();
      int var5 = (int)(var3 * var4);
      int var6 = var1.getFullWidth();
      int var7 = var6 / 2;
      int var8 = var5 - var7;
      int var9 = var8 + var6;
      int var10 = this.mLastWidth;
      if(var9 > var10) {
         var8 = this.mLastWidth - var6;
      }

      return var8;
   }

   private int getTagSuggestionPositionY(TagSuggestionView var1) {
      ImageView var2 = (ImageView)this.findViewById(2131624165);
      float var3 = var1.getY();
      float var4 = (float)var2.getHeight();
      int var5 = (int)(var3 * var4);
      int var6 = var1.getFullHeight();
      int var7 = var1.getFaceBoxHeight() / 3;
      int var8 = var5 - var7;
      int var9 = var8 + var6;
      int var10 = this.mLastHeight;
      if(var9 > var10) {
         var8 = this.mLastHeight - var6;
      }

      return var8;
   }

   public TagSuggestionView addSuggestion(float var1, float var2, float var3) {
      TagSuggestionView var4 = (TagSuggestionView)((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903169, (ViewGroup)null);
      int var5 = this.mLastWidth;
      var4.setImageSize(var5);
      var4.setEyeDistance(var3);
      var4.setX(var1);
      var4.setY(var2);
      LayoutParams var6 = new LayoutParams(-1, -1, 0);
      int var7 = this.getTagSuggestionPositionX(var4);
      int var8 = this.getTagSuggestionPositionY(var4);
      var6.setMargins(var7, var8, 0, 0);
      this.addView(var4, var6);
      this.mSuggestions.add(var4);
      TaggableView.2 var10 = new TaggableView.2(var4);
      var4.setOnClickListener(var10);
      return var4;
   }

   public TagView addTag(long var1, float var3, float var4, String var5) {
      TagView var8;
      if(var1 != 65535L) {
         HashMap var6 = this.mTags;
         Long var7 = Long.valueOf(var1);
         if(var6.containsKey(var7)) {
            HashMap var14 = this.mTags;
            Long var15 = Long.valueOf(var1);
            var8 = (TagView)var14.get(var15);
            var8.x = var3;
            var8.y = var4;
            this.updateTag(var8);
            return var8;
         }
      }

      var8 = (TagView)((LayoutInflater)this.mContext.getSystemService("layout_inflater")).inflate(2130903170, (ViewGroup)null);
      var8.x = var3;
      var8.y = var4;
      var8.userId = var1;
      var8.setText(var5);
      if(var1 == 65535L) {
         long var9 = (long)this.getNextTextId();
         var8.userId = var9;
      }

      this.createTag(var8);
      HashMap var11 = this.mTags;
      Long var12 = Long.valueOf(var8.userId);
      var11.put(var12, var8);
      return var8;
   }

   public void createTag(TagView var1) {
      LayoutParams var2 = new LayoutParams(-1, -1, 0);
      int var3 = this.getTagPositionX(var1);
      int var4 = this.getTagPositionY(var1);
      var2.setMargins(var3, var4, 0, 0);
      this.addView(var1, var2);
   }

   public void deleteSuggestion(TagSuggestionView var1) {
      if(this.mSuggestions.contains(var1)) {
         this.mSuggestions.remove(var1);
         this.removeView(var1);
      }
   }

   public void deleteTag(long var1) {
      HashMap var3 = this.mTags;
      Long var4 = Long.valueOf(var1);
      if(var3.containsKey(var4)) {
         HashMap var5 = this.mTags;
         Long var6 = Long.valueOf(var1);
         TagView var7 = (TagView)var5.get(var6);
         HashMap var8 = this.mTags;
         Long var9 = Long.valueOf(var1);
         var8.remove(var9);
         this.removeView(var7);
      }
   }

   public int getNextTextId() {
      int var1 = this.mTextId;
      int var2 = var1 - 1;
      this.mTextId = var2;
      return var1;
   }

   public List<FacebookPhotoTag> getTags() {
      ArrayList var1 = new ArrayList();

      FacebookPhotoTag var13;
      boolean var16;
      for(Iterator var2 = this.mTags.keySet().iterator(); var2.hasNext(); var16 = var1.add(var13)) {
         long var3 = ((Long)var2.next()).longValue();
         HashMap var5 = this.mTags;
         Long var6 = Long.valueOf(var3);
         TagView var7 = (TagView)var5.get(var6);
         if(var7.userId <= 65535L) {
            String var8 = var7.getText().toString();
            double var9 = (double)(var7.x * 100.0F);
            double var11 = (double)(var7.y * 100.0F);
            var13 = new FacebookPhotoTag("", var8, 65535L, var9, var11, 65535L);
         } else {
            long var17 = var7.userId;
            double var19 = (double)(var7.x * 100.0F);
            double var21 = (double)(var7.y * 100.0F);
            var13 = new FacebookPhotoTag("", (String)null, var17, var19, var21, 65535L);
         }
      }

      return var1;
   }

   public boolean onDown(MotionEvent var1) {
      return false;
   }

   public boolean onFling(MotionEvent var1, MotionEvent var2, float var3, float var4) {
      return false;
   }

   public void onLongPress(MotionEvent var1) {}

   public boolean onScroll(MotionEvent var1, MotionEvent var2, float var3, float var4) {
      return false;
   }

   public void onShowPress(MotionEvent var1) {}

   public boolean onSingleTapUp(MotionEvent var1) {
      ImageView var2 = (ImageView)this.findViewById(2131624165);
      float var3 = var1.getX();
      float var4 = (float)var2.getWidth();
      float var5 = var3 / var4;
      float var6 = var1.getY();
      float var7 = (float)var2.getHeight();
      float var8 = var6 / var7;
      this.mListener.onClicked(var5, var8);
      return false;
   }

   public boolean onTouchEvent(MotionEvent var1) {
      this.mGestureDetector.onTouchEvent(var1);
      return true;
   }

   public void setImage(Bitmap var1) {
      ImageView var2 = (ImageView)this.findViewById(2131624165);
      var2.setImageBitmap(var1);
      this.findViewById(2131624165).setVisibility(0);
      int var3 = var2.getWidth();
      this.mLastWidth = var3;
      int var4 = var2.getHeight();
      this.mLastHeight = var4;
   }

   public void setListener(TaggableView.TaggableViewListener var1) {
      this.mListener = var1;
   }

   public void updateTag(TagView var1) {
      LayoutParams var2 = (LayoutParams)var1.getLayoutParams();
      int var3 = this.getTagPositionX(var1);
      int var4 = this.getTagPositionY(var1);
      var2.setMargins(var3, var4, 0, 0);
      var1.setLayoutParams(var2);
   }

   public void updateTagPosition() {
      ImageView var1 = (ImageView)this.findViewById(2131624165);
      int var2 = var1.getWidth();
      int var3 = var1.getHeight();
      if(this.mLastWidth != var2) {
         this.mLastWidth = var2;
         this.mLastHeight = var3;
         Iterator var4 = this.mTags.keySet().iterator();

         while(var4.hasNext()) {
            long var5 = ((Long)var4.next()).longValue();
            HashMap var7 = this.mTags;
            Long var8 = Long.valueOf(var5);
            TagView var9 = (TagView)var7.get(var8);
            this.updateTag(var9);
         }

         Iterator var10 = this.mSuggestions.iterator();

         while(var10.hasNext()) {
            TagSuggestionView var11 = (TagSuggestionView)var10.next();
            this.updateTagSuggestion(var11);
         }

      }
   }

   public void updateTagSuggestion(TagSuggestionView var1) {
      LayoutParams var2 = (LayoutParams)var1.getLayoutParams();
      int var3 = this.mLastWidth;
      var1.setImageSize(var3);
      int var4 = this.getTagSuggestionPositionX(var1);
      int var5 = this.getTagSuggestionPositionY(var1);
      var2.setMargins(var4, var5, 0, 0);
      var1.setLayoutParams(var2);
   }

   class 2 implements OnClickListener {

      // $FF: synthetic field
      final TagSuggestionView val$tagSugg;


      2(TagSuggestionView var2) {
         this.val$tagSugg = var2;
      }

      public void onClick(View var1) {
         TaggableView var2 = TaggableView.this;
         TagSuggestionView var3 = this.val$tagSugg;
         var2.deleteSuggestion(var3);
         TaggableView.TaggableViewListener var4 = TaggableView.this.mListener;
         float var5 = this.val$tagSugg.getX();
         float var6 = this.val$tagSugg.getY();
         var4.onClicked(var5, var6);
      }
   }

   class 1 implements OnGlobalLayoutListener {

      1() {}

      public void onGlobalLayout() {
         TaggableView.this.updateTagPosition();
      }
   }

   public interface TaggableViewListener {

      void onClicked(float var1, float var2);
   }
}
