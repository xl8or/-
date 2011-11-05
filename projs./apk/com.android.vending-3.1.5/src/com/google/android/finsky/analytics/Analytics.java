package com.google.android.finsky.analytics;


public interface Analytics {

   String KEY_REFERRER_LIST_COOKIE = "referrer_list_cookie";
   String KEY_REFERRER_URL = "referrer_url";
   String REFERRER_CREDITCARD_EXTERNAL = "externalPackage";
   String TAG_AUTO_UPDATE = "autoUpdate";
   String TAG_COMPLETEPURCHASE = "completePurchase";
   String TAG_CONFIRMFREEDOWNLOAD = "confirmFreeDownload";
   String TAG_CONTENT_FILTER = "contentFilter";
   String TAG_CREDITCARD = "addCreditCard";
   String TAG_CREDITCARD_CANCEL = "addCreditCardCancel";
   String TAG_CREDITCARD_CONFIRM = "addCreditCardConfirm";
   String TAG_CREDITCARD_ERROR = "addCreditCardError";
   String TAG_CREDITCARD_SUCCESS = "addCreditCardSuccess";
   String TAG_DCB = "addDcb";
   String TAG_DCB_CANCEL = "addDcbCancel";
   String TAG_DCB_CONFIRM = "addDcbConfirm";
   String TAG_DCB_EDIT = "addDcbEdit";
   String TAG_DCB_EDIT_CANCEL = "addDcbEditCancel";
   String TAG_DCB_EDIT_CONFIRM = "addDcbEditConfirm";
   String TAG_DCB_ERROR = "addDcbError";
   String TAG_DCB_PIN_ENTRY = "dcbPinEntry";
   String TAG_DCB_PIN_ENTRY_CANCEL = "dcbPinEntryCancel";
   String TAG_DCB_PIN_ENTRY_CONFIRM = "dcbPinEntryConfirm";
   String TAG_DCB_SUCCESS = "addDcbSuccess";
   String TAG_DCB_TOS = "addDcbTos";
   String TAG_DCB_TOS_CHANGED = "dcbTosChanged";
   String TAG_DCB_TOS_CHANGED_CANCEL = "dcbTosChangedCancel";
   String TAG_DCB_TOS_CHANGED_CONFIRM = "dcbTosChangedConfirm";
   String TAG_FREEDOWNLOAD = "freeDownload";
   String TAG_HELP = "help";
   String TAG_MANUAL_UPDATE = "manualUpdate";
   String TAG_MYAPPS = "myApps";
   String TAG_PLUSONE = "plusOne";
   String TAG_PURCHASE = "purchase";
   String TAG_SETTINGS = "settings";
   String TAG_SWITCH_ACCOUNT = "switchAccount";
   String TAG_UNINSTALL = "uninstall";
   String TAG_UPDATEALL = "updateAll";
   String TAG_VIEWPURCHASE = "viewPurchase";


   void logListViewOnPage(String var1, String var2, String var3, String var4);

   void logPageView(String var1, String var2, String var3);

   void reset();
}
