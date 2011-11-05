package com.facebook.orca.common.ui.widgets.refreshablelistview;


public enum RefreshableListViewState {

   // $FF: synthetic field
   private static final RefreshableListViewState[] $VALUES;
   BUFFERING("BUFFERING", 5),
   LOADING("LOADING", 4),
   NORMAL("NORMAL", 0),
   PULL_TO_REFRESH("PULL_TO_REFRESH", 1),
   PUSH_TO_REFRESH("PUSH_TO_REFRESH", 2),
   RELEASE_TO_REFRESH("RELEASE_TO_REFRESH", 3);


   static {
      RefreshableListViewState[] var0 = new RefreshableListViewState[6];
      RefreshableListViewState var1 = NORMAL;
      var0[0] = var1;
      RefreshableListViewState var2 = PULL_TO_REFRESH;
      var0[1] = var2;
      RefreshableListViewState var3 = PUSH_TO_REFRESH;
      var0[2] = var3;
      RefreshableListViewState var4 = RELEASE_TO_REFRESH;
      var0[3] = var4;
      RefreshableListViewState var5 = LOADING;
      var0[4] = var5;
      RefreshableListViewState var6 = BUFFERING;
      var0[5] = var6;
      $VALUES = var0;
   }

   private RefreshableListViewState(String var1, int var2) {}
}
