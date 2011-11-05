package com.facebook.katana.activity.profilelist;

import android.database.Cursor;
import com.facebook.katana.activity.profilelist.ProfileListActivity;
import java.util.Comparator;
import java.util.List;

public abstract class ProfileListCursorAdapter extends ProfileListActivity.ProfileListAdapter {

   protected Cursor mCursor;
   protected List<ProfileListCursorAdapter.Section> mSections;


   public ProfileListCursorAdapter() {}

   protected int getChildActualPosition(int var1, int var2) {
      return ((ProfileListCursorAdapter.Section)this.mSections.get(var1)).getCursorStartPosition() + var2;
   }

   public int getChildViewType(int var1, int var2) {
      return 1;
   }

   public int getChildrenCount(int var1) {
      return ((ProfileListCursorAdapter.Section)this.mSections.get(var1)).getChildrenCount();
   }

   public Cursor getCursor() {
      return this.mCursor;
   }

   public Object getSection(int var1) {
      return this.mSections.get(var1);
   }

   public int getSectionCount() {
      return this.mSections.size();
   }

   public int getSectionHeaderViewType(int var1) {
      return 0;
   }

   public int getViewTypeCount() {
      return 2;
   }

   public boolean isEmpty() {
      return false;
   }

   public boolean isEnabled(int var1, int var2) {
      return true;
   }

   public static class Section {

      static Comparator<ProfileListCursorAdapter.Section> mComparator = new ProfileListCursorAdapter.Section.1();
      protected int mCount;
      protected int mCursorStartPosition;
      protected final String mSectionName;


      public Section(String var1, int var2, int var3) {
         this.mSectionName = var1;
         this.mCursorStartPosition = var2;
         this.mCount = var3;
      }

      public int getChildrenCount() {
         return this.mCount;
      }

      public int getCursorStartPosition() {
         return this.mCursorStartPosition;
      }

      public String getTitle() {
         return this.mSectionName;
      }

      public String toString() {
         return this.mSectionName;
      }

      static class 1 implements Comparator<ProfileListCursorAdapter.Section> {

         1() {}

         public int compare(ProfileListCursorAdapter.Section var1, ProfileListCursorAdapter.Section var2) {
            String var3 = var1.mSectionName;
            String var4 = var2.mSectionName;
            return var3.compareTo(var4);
         }
      }
   }
}
