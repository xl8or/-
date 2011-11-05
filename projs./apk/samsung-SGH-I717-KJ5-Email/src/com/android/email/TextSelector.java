package com.android.email;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebView.WebTextSelectionListener;
import com.android.email.CopyText;
import com.android.email.IconPopupMenu;
import com.android.email.SearchText;
import com.android.email.ShareText;
import com.android.email.ToolTip;

public class TextSelector implements WebTextSelectionListener {

   static final String LOGTAG = "TextSelector";
   private static final boolean LOGV_ENABLED = true;
   private static final int MENU_TYPE_ICONPOPUP = 2;
   private static final int MENU_TYPE_TOOLTIP = 0;
   private static final int STATE_SELECTION_CHANGING = 2;
   private static final int STATE_SELECTION_NONE = 0;
   private static final int STATE_SELECTION_SELECTED = 1;
   private static final int mnMenuType = 2;
   private WebView mComposerControl = null;
   private IconPopupMenu mIconPopup;
   private ToolTip mToolTip = null;
   private int m_nChangeCount = 0;
   private int m_nState = 0;


   public TextSelector(WebView var1) {
      this.mComposerControl = var1;
   }

   private void createToolTip() {
      if(this.mIconPopup == null) {
         Context var1 = this.getTopWindow().getContext();
         IconPopupMenu var2 = new IconPopupMenu(var1);
         this.mIconPopup = var2;
         IconPopupMenu var3 = this.mIconPopup;
         CopyText var4 = new CopyText(this, 2131167045);
         var3.addIcon(2130837918, var4, 0, 2131167045);
         IconPopupMenu var5 = this.mIconPopup;
         SearchText var6 = new SearchText(this, 2131167048);
         var5.addIcon(2130837919, var6, 0, 2131167048);
         IconPopupMenu var7 = this.mIconPopup;
         ShareText var8 = new ShareText(this, 2131167049);
         var7.addIcon(2130837920, var8, 0, 2131167049);
      }
   }

   private void hide() {
      int var1 = Log.v("TextSelector", "hide : Called.");
      if(this.mIconPopup != null) {
         this.mIconPopup.hide();
      }
   }

   public WebView GetComposerControl() {
      return this.mComposerControl;
   }

   public void clearSelection() {
      int var1 = Log.v("TextSelector", "clearSelection : Called.");
      this.hide();
      if(this.getTopWindow() != null) {
         this.getTopWindow().ClearWebTextSelection();
      }

      this.m_nState = 0;
      this.m_nChangeCount = 0;
   }

   public WebView getTopWindow() {
      return this.mComposerControl;
   }

   public void onSelectionChanged(int var1) {
      String var2 = "onSelectionChanged : Called " + var1;
      int var3 = Log.v("TextSelector", var2);
      switch(var1) {
      case 3:
      case 7:
         this.m_nState = 2;
         int var4 = this.m_nChangeCount + 1;
         this.m_nChangeCount = var4;
         this.hide();
         return;
      case 4:
      default:
         return;
      case 5:
      case 8:
         if(this.m_nState == 0) {
            return;
         } else {
            int var5 = this.m_nChangeCount - 1;
            this.m_nChangeCount = var5;
            if(this.m_nChangeCount > 0) {
               return;
            }
         }
      case 2:
         this.m_nState = 1;
         if(2 == var1) {
            this.m_nChangeCount = 0;
         }

         this.createToolTip();
         WebView var6 = this.getTopWindow();
         if(var6 == null) {
            return;
         } else if(var6.getWebTextSelectionControls() == null) {
            return;
         } else {
            Rect var7 = var6.getWebTextSelectionControls().getSelectionRect();
            new Point(0, 0);
            StringBuilder var9 = (new StringBuilder()).append("onSelectionChanged : rtSelection ");
            int var10 = var7.left;
            StringBuilder var11 = var9.append(var10).append(", ");
            int var12 = var7.right;
            StringBuilder var13 = var11.append(var12).append(", ");
            int var14 = var7.top;
            StringBuilder var15 = var13.append(var14).append(", ");
            int var16 = var7.bottom;
            String var17 = var15.append(var16).toString();
            int var18 = Log.v("TextSelector", var17);
            int var19 = var7.left;
            int var20 = var6.getScrollX();
            int var21 = var19 - var20;
            var7.left = var21;
            int var22 = var7.right;
            int var23 = var6.getScrollX();
            int var24 = var22 - var23;
            var7.right = var24;
            int var25 = var7.top;
            int var26 = var6.getScrollY();
            int var27 = var25 - var26;
            var7.top = var27;
            int var28 = var7.bottom;
            int var29 = var6.getScrollY();
            int var30 = var28 - var29;
            var7.bottom = var30;
            if((var7.left >= 0 || var7.right >= 0) && (var7.top >= 0 || var7.bottom >= 0)) {
               int var31 = var7.left;
               int var32 = var6.getWidth();
               if(var31 <= var32) {
                  int var33 = var7.top;
                  int var34 = var6.getHeight();
                  if(var33 <= var34) {
                     StringBuilder var36 = (new StringBuilder()).append("onSelectionChanged : adjusted rtSelection ");
                     int var37 = var7.left;
                     StringBuilder var38 = var36.append(var37).append(", ");
                     int var39 = var7.right;
                     StringBuilder var40 = var38.append(var39).append(", ");
                     int var41 = var7.top;
                     StringBuilder var42 = var40.append(var41).append(", ");
                     int var43 = var7.bottom;
                     String var44 = var42.append(var43).toString();
                     int var45 = Log.v("TextSelector", var44);
                     this.mIconPopup.show(var7, -1, var6);
                     return;
                  }
               }
            }

            int var35 = Log.v("TextSelector", "onSelectionChanged : Selection is out of secreen.");
            this.hide();
            return;
         }
      case 6:
         this.m_nState = 0;
         this.clearSelection();
         return;
      case 9:
         this.hide();
      }
   }
}
