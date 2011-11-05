package com.android.email.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomizeDateScreen extends Activity implements OnClickListener {

   private static final int DATE_DIALOG_FROM = 100;
   private static final int DATE_DIALOG_TO = 200;
   public static final String SEARCH_FROM_DAY = "FROM_DAY";
   public static final String SEARCH_FROM_HOUR = "FROM_HOUR";
   public static final String SEARCH_FROM_MINUTE = "FROM_MINUTE";
   public static final String SEARCH_FROM_MONTH = "FROM_MONTH";
   public static final String SEARCH_FROM_YEAR = "FROM_YEAR";
   public static final String SEARCH_STRING = "SEARCH_STRING";
   public static final String SEARCH_TO_DAY = "TO_DAY";
   public static final String SEARCH_TO_HOUR = "TO_HOUR";
   public static final String SEARCH_TO_MINUTE = "TO_MINUTE";
   public static final String SEARCH_TO_MONTH = "TO_MONTH";
   public static final String SEARCH_TO_YEAR = "TO_YEAR";
   private static final int TIME_DIALOG_FROM = 300;
   private static final int TIME_DIALOG_TO = 400;
   private Button cancelButton;
   private RelativeLayout customizeLayout;
   private RadioButton customizeRadioButton;
   private SimpleDateFormat dateFormatter;
   private Button fromDateButton;
   private TextView fromTextView;
   private Button fromTimeButton;
   private Date mDate;
   private int mDay;
   private int mDayFromSearch;
   private int mDayOfWeek;
   private int mDayToSearch;
   private OnDateSetListener mFromDateSetListener;
   private StringBuilder mFromDateStringBuffer;
   private int mFromDay;
   private int mFromDayOfWeek;
   private int mFromHourOfDay;
   private int mFromMinute;
   private int mFromMonth;
   private OnTimeSetListener mFromTimeSetListener;
   private StringBuilder mFromTimeStringBuffer;
   private int mFromYear;
   private int mHourFromSearch;
   private int mHourOfDay;
   private int mHourToSearch;
   private boolean mIsCustomize;
   private int mMinute;
   private int mMinuteFromSearch;
   private int mMinuteToSearch;
   private int mMonth;
   private int mMonthFromSearch;
   private int mMonthToSearch;
   private String mStringToReturn;
   private OnDateSetListener mToDateSetListener;
   private StringBuilder mToDateStringBuffer;
   private int mToDay;
   private int mToDayOfWeek;
   private int mToHourOfDay;
   private int mToMinute;
   private int mToMonth;
   private OnTimeSetListener mToTimeSetListener;
   private StringBuilder mToTimeStringBuffer;
   private int mToYear;
   private int mYear;
   private int mYearFromSearch;
   private int mYearToSearch;
   private Button okButton;
   private RelativeLayout oneDayLayout;
   private RadioButton oneDayRadioButton;
   private RelativeLayout oneMonthLayout;
   private RadioButton oneMonthRadioButton;
   private RelativeLayout oneWeekLayout;
   private RadioButton oneWeekRadioButton;
   private RelativeLayout oneYearLayout;
   private RadioButton oneYearRadioButton;
   private RelativeLayout sixMonthLayout;
   private RadioButton sixMonthRadioButton;
   private SimpleDateFormat timeFormatter;
   private Button toDateButton;
   private TextView toTextView;
   private Button toTimeButton;
   private RelativeLayout todayLayout;
   private RadioButton todayRadioButton;


   public CustomizeDateScreen() {
      Date var1 = new Date();
      this.mDate = var1;
      StringBuilder var2 = new StringBuilder();
      this.mFromDateStringBuffer = var2;
      StringBuilder var3 = new StringBuilder();
      this.mFromTimeStringBuffer = var3;
      StringBuilder var4 = new StringBuilder();
      this.mToDateStringBuffer = var4;
      StringBuilder var5 = new StringBuilder();
      this.mToTimeStringBuffer = var5;
      CustomizeDateScreen.1 var6 = new CustomizeDateScreen.1();
      this.mFromDateSetListener = var6;
      CustomizeDateScreen.2 var7 = new CustomizeDateScreen.2();
      this.mToDateSetListener = var7;
      CustomizeDateScreen.3 var8 = new CustomizeDateScreen.3();
      this.mToTimeSetListener = var8;
      CustomizeDateScreen.4 var9 = new CustomizeDateScreen.4();
      this.mFromTimeSetListener = var9;
   }

   private void disableCustomView() {
      this.fromTextView.setEnabled((boolean)0);
      this.toTextView.setEnabled((boolean)0);
      this.fromDateButton.setEnabled((boolean)0);
      this.fromTimeButton.setEnabled((boolean)0);
      this.toDateButton.setEnabled((boolean)0);
      this.toTimeButton.setEnabled((boolean)0);
   }

   private void enableCustomView() {
      this.fromTextView.setEnabled((boolean)1);
      this.toTextView.setEnabled((boolean)1);
      this.fromDateButton.setEnabled((boolean)1);
      this.fromTimeButton.setEnabled((boolean)1);
      this.toDateButton.setEnabled((boolean)1);
      this.toTimeButton.setEnabled((boolean)1);
   }

   private String getDayInString(int var1) {
      String var2;
      switch(var1) {
      case 1:
         var2 = "Sun";
         break;
      case 2:
         var2 = "Mon";
         break;
      case 3:
         var2 = "Tue";
         break;
      case 4:
         var2 = "Wed";
         break;
      case 5:
         var2 = "Thu";
         break;
      case 6:
         var2 = "Fri";
         break;
      case 7:
         var2 = "Sat";
         break;
      default:
         var2 = "Sat";
      }

      return var2;
   }

   private String getMonthInString(int var1) {
      String var2;
      switch(var1) {
      case 1:
         var2 = "Jan";
         break;
      case 2:
         var2 = "Feb";
         break;
      case 3:
         var2 = "Mar";
         break;
      case 4:
         var2 = "Apr";
         break;
      case 5:
         var2 = "May";
         break;
      case 6:
         var2 = "June";
         break;
      case 7:
         var2 = "July";
         break;
      case 8:
         var2 = "Aug";
         break;
      case 9:
         var2 = "Sep";
         break;
      case 10:
         var2 = "Oct";
         break;
      case 11:
         var2 = "Nov";
         break;
      case 12:
         var2 = "Dec";
         break;
      default:
         var2 = "";
      }

      return var2;
   }

   private void init() {
      Locale var1 = Locale.getDefault();
      SimpleDateFormat var2 = new SimpleDateFormat("EEE, d MMM, yyyy", var1);
      this.dateFormatter = var2;
      Locale var3 = Locale.getDefault();
      SimpleDateFormat var4 = new SimpleDateFormat("h:mm a ", var3);
      this.timeFormatter = var4;
      Calendar var5 = Calendar.getInstance();
      int var6 = var5.get(1);
      this.mYear = var6;
      int var7 = var5.get(2);
      this.mMonth = var7;
      int var8 = var5.get(5);
      this.mDay = var8;
      int var9 = var5.get(5);
      this.mDayOfWeek = var9;
      int var10 = var5.get(11);
      this.mHourOfDay = var10;
      int var11 = var5.get(12);
      this.mMinute = var11;
      RelativeLayout var12 = (RelativeLayout)this.findViewById(2131361993);
      this.todayLayout = var12;
      RelativeLayout var13 = (RelativeLayout)this.findViewById(2131361996);
      this.oneDayLayout = var13;
      RelativeLayout var14 = (RelativeLayout)this.findViewById(2131361999);
      this.oneWeekLayout = var14;
      RelativeLayout var15 = (RelativeLayout)this.findViewById(2131362002);
      this.oneMonthLayout = var15;
      RelativeLayout var16 = (RelativeLayout)this.findViewById(2131362005);
      this.sixMonthLayout = var16;
      RelativeLayout var17 = (RelativeLayout)this.findViewById(2131362008);
      this.oneYearLayout = var17;
      RelativeLayout var18 = (RelativeLayout)this.findViewById(2131362011);
      this.customizeLayout = var18;
      RadioButton var19 = (RadioButton)this.findViewById(2131361995);
      this.todayRadioButton = var19;
      this.todayRadioButton.setChecked((boolean)1);
      RadioButton var20 = (RadioButton)this.findViewById(2131361998);
      this.oneDayRadioButton = var20;
      RadioButton var21 = (RadioButton)this.findViewById(2131362001);
      this.oneWeekRadioButton = var21;
      RadioButton var22 = (RadioButton)this.findViewById(2131362004);
      this.oneMonthRadioButton = var22;
      RadioButton var23 = (RadioButton)this.findViewById(2131362007);
      this.sixMonthRadioButton = var23;
      RadioButton var24 = (RadioButton)this.findViewById(2131362010);
      this.oneYearRadioButton = var24;
      RadioButton var25 = (RadioButton)this.findViewById(2131362013);
      this.customizeRadioButton = var25;
      TextView var26 = (TextView)this.findViewById(2131362014);
      this.fromTextView = var26;
      TextView var27 = (TextView)this.findViewById(2131362018);
      this.toTextView = var27;
      Button var28 = (Button)this.findViewById(2131362016);
      this.fromDateButton = var28;
      Button var29 = (Button)this.findViewById(2131362017);
      this.fromTimeButton = var29;
      Button var30 = (Button)this.findViewById(2131362020);
      this.toDateButton = var30;
      Button var31 = (Button)this.findViewById(2131362021);
      this.toTimeButton = var31;
      Button var32 = (Button)this.findViewById(2131362023);
      this.okButton = var32;
      Button var33 = (Button)this.findViewById(2131362024);
      this.cancelButton = var33;
      this.updateDisplay();
      this.todayLayout.setOnClickListener(this);
      this.oneDayLayout.setOnClickListener(this);
      this.oneWeekLayout.setOnClickListener(this);
      this.oneMonthLayout.setOnClickListener(this);
      this.sixMonthLayout.setOnClickListener(this);
      this.oneYearLayout.setOnClickListener(this);
      this.customizeLayout.setOnClickListener(this);
      this.fromDateButton.setOnClickListener(this);
      this.fromTimeButton.setOnClickListener(this);
      this.toDateButton.setOnClickListener(this);
      this.toTimeButton.setOnClickListener(this);
      this.okButton.setOnClickListener(this);
      this.cancelButton.setOnClickListener(this);
   }

   private void setDefaultValues() {
      int var1 = this.mDate.getYear() + 1900;
      this.mYearToSearch = var1;
      int var2 = this.mDate.getMonth();
      this.mMonthToSearch = var2;
      int var3 = this.mDate.getDate();
      this.mDayToSearch = var3;
      this.mHourFromSearch = 0;
      this.mMinuteFromSearch = 0;
      int var4 = this.mDate.getHours();
      this.mHourToSearch = var4;
      int var5 = this.mDate.getMinutes();
      this.mMinuteToSearch = var5;
   }

   private void toggleAllRadioButtons() {
      this.todayRadioButton.setChecked((boolean)0);
      this.oneDayRadioButton.setChecked((boolean)0);
      this.oneWeekRadioButton.setChecked((boolean)0);
      this.oneMonthRadioButton.setChecked((boolean)0);
      this.sixMonthRadioButton.setChecked((boolean)0);
      this.oneYearRadioButton.setChecked((boolean)0);
      this.customizeRadioButton.setChecked((boolean)0);
   }

   private void updateDisplay() {
      Date var1 = new Date();
      new Date();
      Button var3 = this.fromDateButton;
      String var4 = this.dateFormatter.format(var1);
      var3.setText(var4);
      Button var5 = this.toDateButton;
      String var6 = this.dateFormatter.format(var1);
      var5.setText(var6);
      StringBuilder var7 = new StringBuilder();
      if(var1.getHours() < 10) {
         StringBuilder var8 = var7.append(0);
      }

      int var9 = var1.getHours();
      var7.append(var9);
      StringBuilder var11 = var7.append(":");
      if(var1.getMinutes() < 10) {
         StringBuilder var12 = var7.append(0);
      }

      int var13 = var1.getMinutes();
      var7.append(var13);
      Button var15 = this.fromTimeButton;
      String var16 = var7.toString();
      var15.setText(var16);
      StringBuilder var17 = new StringBuilder();
      if(var1.getHours() < 10) {
         StringBuilder var18 = var17.append(0);
      }

      int var19 = var1.getHours();
      var17.append(var19);
      StringBuilder var21 = var17.append(":");
      if(var1.getMinutes() < 10) {
         StringBuilder var22 = var17.append(0);
      }

      int var23 = var1.getMinutes();
      var17.append(var23);
      Button var25 = this.toTimeButton;
      String var26 = var17.toString();
      var25.setText(var26);
   }

   private void updateFromDate() {
      int var1 = this.mFromYear;
      int var2 = this.mFromMonth;
      int var3 = this.mFromDay;
      Date var4 = new Date(var1, var2, var3);
      StringBuilder var5 = this.mFromDateStringBuffer;
      int var6 = this.mFromDateStringBuffer.length();
      var5.delete(0, var6);
      StringBuilder var8 = this.mFromDateStringBuffer;
      int var9 = var4.getDay();
      String var10 = this.getDayInString(var9);
      var8.append(var10);
      StringBuilder var12 = this.mFromDateStringBuffer.append(", ");
      StringBuilder var13 = this.mFromDateStringBuffer;
      int var14 = var4.getDate();
      var13.append(var14);
      StringBuilder var16 = this.mFromDateStringBuffer.append(" ");
      StringBuilder var17 = this.mFromDateStringBuffer;
      int var18 = var4.getMonth() + 1;
      String var19 = this.getMonthInString(var18);
      var17.append(var19);
      StringBuilder var21 = this.mFromDateStringBuffer.append(", ");
      StringBuilder var22 = this.mFromDateStringBuffer;
      int var23 = var4.getYear();
      var22.append(var23);
      Button var25 = this.fromDateButton;
      String var26 = this.mFromDateStringBuffer.toString();
      var25.setText(var26);
   }

   private void updateFromTime() {
      StringBuilder var1 = this.mFromTimeStringBuffer;
      int var2 = this.mFromTimeStringBuffer.length();
      var1.delete(0, var2);
      if(this.mFromHourOfDay < 10) {
         StringBuilder var4 = this.mFromTimeStringBuffer.append(0);
      }

      StringBuilder var5 = this.mFromTimeStringBuffer;
      int var6 = this.mFromHourOfDay;
      var5.append(var6);
      StringBuilder var8 = this.mFromTimeStringBuffer.append(":");
      if(this.mFromMinute < 10) {
         StringBuilder var9 = this.mFromTimeStringBuffer.append(0);
      }

      StringBuilder var10 = this.mFromTimeStringBuffer;
      int var11 = this.mFromMinute;
      var10.append(var11);
      Button var13 = this.fromTimeButton;
      String var14 = this.mFromTimeStringBuffer.toString();
      var13.setText(var14);
   }

   private void updateToDate() {
      int var1 = this.mToYear;
      int var2 = this.mToMonth;
      int var3 = this.mToDay;
      Date var4 = new Date(var1, var2, var3);
      StringBuilder var5 = this.mToDateStringBuffer;
      int var6 = this.mToDateStringBuffer.length();
      var5.delete(0, var6);
      StringBuilder var8 = this.mToDateStringBuffer;
      int var9 = var4.getDay();
      String var10 = this.getDayInString(var9);
      var8.append(var10);
      StringBuilder var12 = this.mToDateStringBuffer.append(", ");
      StringBuilder var13 = this.mToDateStringBuffer;
      int var14 = var4.getDate();
      var13.append(var14);
      StringBuilder var16 = this.mToDateStringBuffer.append(" ");
      StringBuilder var17 = this.mToDateStringBuffer;
      int var18 = var4.getMonth() + 1;
      String var19 = this.getMonthInString(var18);
      var17.append(var19);
      StringBuilder var21 = this.mToDateStringBuffer.append(", ");
      StringBuilder var22 = this.mToDateStringBuffer;
      int var23 = var4.getYear();
      var22.append(var23);
      Button var25 = this.toDateButton;
      String var26 = this.mToDateStringBuffer.toString();
      var25.setText(var26);
   }

   private void updateToTime() {
      StringBuilder var1 = this.mToTimeStringBuffer;
      int var2 = this.mToTimeStringBuffer.length();
      var1.delete(0, var2);
      if(this.mToHourOfDay < 10) {
         StringBuilder var4 = this.mToTimeStringBuffer.append(0);
      }

      StringBuilder var5 = this.mToTimeStringBuffer;
      int var6 = this.mToHourOfDay;
      var5.append(var6);
      StringBuilder var8 = this.mToTimeStringBuffer.append(":");
      if(this.mToMinute < 10) {
         StringBuilder var9 = this.mToTimeStringBuffer.append(0);
      }

      StringBuilder var10 = this.mToTimeStringBuffer;
      int var11 = this.mToMinute;
      var10.append(var11);
      Button var13 = this.toTimeButton;
      String var14 = this.mToTimeStringBuffer.toString();
      var13.setText(var14);
   }

   public void onClick(View var1) {
      RelativeLayout var2 = this.todayLayout;
      if(var1 == var2) {
         this.mIsCustomize = (boolean)0;
         this.toggleAllRadioButtons();
         this.todayRadioButton.setChecked((boolean)1);
         int var3 = this.mDate.getYear() + 1900;
         this.mYearFromSearch = var3;
         int var4 = this.mDate.getMonth();
         this.mMonthFromSearch = var4;
         int var5 = this.mDate.getDate();
         this.mDayFromSearch = var5;
         this.setDefaultValues();
         String var6 = this.getString(2131166999);
         this.mStringToReturn = var6;
         this.disableCustomView();
      } else {
         RelativeLayout var7 = this.oneDayLayout;
         if(var1 == var7) {
            this.mIsCustomize = (boolean)0;
            this.toggleAllRadioButtons();
            this.oneDayRadioButton.setChecked((boolean)1);
            Date var8 = new Date();
            int var9 = this.mDate.getDate() - 1;
            var8.setDate(var9);
            int var10 = var8.getYear() + 1900;
            this.mYearFromSearch = var10;
            int var11 = var8.getMonth();
            this.mMonthFromSearch = var11;
            int var12 = var8.getDate();
            this.mDayFromSearch = var12;
            this.setDefaultValues();
            String var13 = this.getString(2131167000);
            this.mStringToReturn = var13;
            this.disableCustomView();
         } else {
            RelativeLayout var14 = this.oneWeekLayout;
            if(var1 == var14) {
               this.mIsCustomize = (boolean)0;
               this.toggleAllRadioButtons();
               this.oneWeekRadioButton.setChecked((boolean)1);
               Date var15 = new Date();
               int var16 = this.mDate.getDate() - 7;
               var15.setDate(var16);
               int var17 = var15.getYear() + 1900;
               this.mYearFromSearch = var17;
               int var18 = var15.getMonth();
               this.mMonthFromSearch = var18;
               int var19 = var15.getDate();
               this.mDayFromSearch = var19;
               this.setDefaultValues();
               String var20 = this.getString(2131167001);
               this.mStringToReturn = var20;
               this.disableCustomView();
            } else {
               RelativeLayout var21 = this.oneMonthLayout;
               if(var1 == var21) {
                  this.mIsCustomize = (boolean)0;
                  this.toggleAllRadioButtons();
                  this.oneMonthRadioButton.setChecked((boolean)1);
                  Date var22 = new Date();
                  int var23 = this.mDate.getMonth() - 1;
                  var22.setMonth(var23);
                  int var24 = var22.getYear() + 1900;
                  this.mYearFromSearch = var24;
                  int var25 = var22.getMonth();
                  this.mMonthFromSearch = var25;
                  int var26 = var22.getDate();
                  this.mDayFromSearch = var26;
                  this.setDefaultValues();
                  String var27 = this.getString(2131167002);
                  this.mStringToReturn = var27;
                  this.disableCustomView();
               } else {
                  RelativeLayout var28 = this.sixMonthLayout;
                  if(var1 == var28) {
                     this.mIsCustomize = (boolean)0;
                     this.toggleAllRadioButtons();
                     this.sixMonthRadioButton.setChecked((boolean)1);
                     Date var29 = new Date();
                     int var30 = this.mDate.getMonth() - 6;
                     var29.setMonth(var30);
                     int var31 = var29.getYear() + 1900;
                     this.mYearFromSearch = var31;
                     int var32 = var29.getMonth();
                     this.mMonthFromSearch = var32;
                     int var33 = var29.getDate();
                     this.mDayFromSearch = var33;
                     this.setDefaultValues();
                     String var34 = this.getString(2131167003);
                     this.mStringToReturn = var34;
                     this.disableCustomView();
                  } else {
                     RelativeLayout var35 = this.oneYearLayout;
                     if(var1 == var35) {
                        this.mIsCustomize = (boolean)0;
                        this.toggleAllRadioButtons();
                        this.oneYearRadioButton.setChecked((boolean)1);
                        Date var36 = new Date();
                        int var37 = this.mDate.getYear() - 1;
                        var36.setYear(var37);
                        int var38 = var36.getYear() + 1900;
                        this.mYearFromSearch = var38;
                        int var39 = var36.getMonth();
                        this.mMonthFromSearch = var39;
                        int var40 = var36.getDate();
                        this.mDayFromSearch = var40;
                        this.setDefaultValues();
                        String var41 = this.getString(2131167004);
                        this.mStringToReturn = var41;
                        this.disableCustomView();
                     } else {
                        RelativeLayout var42 = this.customizeLayout;
                        if(var1 == var42) {
                           Date var43 = new Date();
                           int var44 = var43.getYear() + 1900;
                           this.mYearFromSearch = var44;
                           int var45 = var43.getMonth();
                           this.mMonthFromSearch = var45;
                           int var46 = var43.getDate();
                           this.mDayFromSearch = var46;
                           int var47 = var43.getHours();
                           this.mHourFromSearch = var47;
                           int var48 = var43.getMinutes();
                           this.mMinuteFromSearch = var48;
                           int var49 = var43.getYear() + 1900;
                           this.mYearToSearch = var49;
                           int var50 = var43.getMonth();
                           this.mMonthToSearch = var50;
                           int var51 = var43.getDate();
                           this.mDayToSearch = var51;
                           int var52 = var43.getHours();
                           this.mHourToSearch = var52;
                           int var53 = var43.getMinutes();
                           this.mMinuteToSearch = var53;
                           this.mIsCustomize = (boolean)1;
                           StringBuilder var54 = new StringBuilder();
                           String var55 = this.dateFormatter.format(var43).toString();
                           var54.append(var55);
                           StringBuilder var57 = var54.append("~");
                           String var58 = this.dateFormatter.format(var43).toString();
                           var54.append(var58);
                           String var60 = var54.toString();
                           this.mStringToReturn = var60;
                           this.toggleAllRadioButtons();
                           this.customizeRadioButton.setChecked((boolean)1);
                           this.enableCustomView();
                        } else {
                           Button var61 = this.fromDateButton;
                           if(var1 == var61) {
                              this.showDialog(100);
                           } else {
                              Button var62 = this.fromTimeButton;
                              if(var1 == var62) {
                                 this.showDialog(300);
                              } else {
                                 Button var63 = this.toDateButton;
                                 if(var1 == var63) {
                                    this.showDialog(200);
                                 } else {
                                    Button var64 = this.toTimeButton;
                                    if(var1 == var64) {
                                       this.showDialog(400);
                                    } else {
                                       Button var65 = this.okButton;
                                       if(var1 == var65) {
                                          if(this.mIsCustomize) {
                                             StringBuilder var66 = new StringBuilder();
                                             StringBuilder var67 = this.mFromDateStringBuffer;
                                             var66.append(var67);
                                             StringBuilder var69 = var66.append(" ");
                                             StringBuilder var70 = this.mFromTimeStringBuffer;
                                             var66.append(var70);
                                             StringBuilder var72 = var66.append("~");
                                             StringBuilder var73 = this.mToDateStringBuffer;
                                             var66.append(var73);
                                             StringBuilder var75 = var66.append(" ");
                                             StringBuilder var76 = this.mToTimeStringBuffer;
                                             var66.append(var76);
                                             String var78 = var66.toString();
                                             this.mStringToReturn = var78;
                                          }

                                          Calendar var79 = Calendar.getInstance();
                                          Calendar var80 = Calendar.getInstance();
                                          int var81 = this.mYearFromSearch;
                                          int var82 = this.mMonthFromSearch;
                                          int var83 = this.mDayFromSearch;
                                          int var84 = this.mHourFromSearch;
                                          int var85 = this.mMinuteFromSearch;
                                          var79.set(var81, var82, var83, var84, var85);
                                          int var86 = this.mYearToSearch;
                                          int var87 = this.mMonthToSearch;
                                          int var88 = this.mDayToSearch;
                                          int var89 = this.mHourToSearch;
                                          int var90 = this.mMinuteToSearch;
                                          var80.set(var86, var87, var88, var89, var90);
                                          if(var79.after(var80)) {
                                             Toast.makeText(this.getApplicationContext(), 2131166933, 2000).show();
                                          } else {
                                             Intent var91 = new Intent();
                                             int var92 = this.mYearFromSearch;
                                             var91.putExtra("FROM_YEAR", var92);
                                             int var94 = this.mMonthFromSearch;
                                             var91.putExtra("FROM_MONTH", var94);
                                             int var96 = this.mDayFromSearch;
                                             var91.putExtra("FROM_DAY", var96);
                                             int var98 = this.mYearToSearch;
                                             var91.putExtra("TO_YEAR", var98);
                                             int var100 = this.mMonthToSearch;
                                             var91.putExtra("TO_MONTH", var100);
                                             int var102 = this.mDayToSearch;
                                             var91.putExtra("TO_DAY", var102);
                                             int var104 = this.mHourFromSearch;
                                             var91.putExtra("FROM_HOUR", var104);
                                             int var106 = this.mMinuteFromSearch;
                                             var91.putExtra("FROM_MINUTE", var106);
                                             int var108 = this.mHourToSearch;
                                             var91.putExtra("TO_HOUR", var108);
                                             int var110 = this.mMinuteToSearch;
                                             var91.putExtra("TO_MINUTE", var110);
                                             String var112 = this.mStringToReturn;
                                             var91.putExtra("SEARCH_STRING", var112);
                                             this.setResult(-1, var91);
                                             this.finish();
                                          }
                                       } else {
                                          Button var114 = this.cancelButton;
                                          if(var1 == var114) {
                                             this.setResult(0);
                                             this.finish();
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
      this.setContentView(2130903078);
      this.init();
      StringBuilder var2 = this.mFromDateStringBuffer;
      SimpleDateFormat var3 = this.dateFormatter;
      Date var4 = this.mDate;
      String var5 = var3.format(var4).toString();
      var2.append(var5);
      StringBuilder var7 = this.mFromTimeStringBuffer;
      int var8 = this.mDate.getHours();
      var7.append(var8);
      StringBuilder var10 = this.mFromTimeStringBuffer.append(":");
      StringBuilder var11 = this.mFromTimeStringBuffer;
      int var12 = this.mDate.getMinutes();
      var11.append(var12);
      StringBuilder var14 = this.mToDateStringBuffer;
      SimpleDateFormat var15 = this.dateFormatter;
      Date var16 = this.mDate;
      String var17 = var15.format(var16).toString();
      var14.append(var17);
      StringBuilder var19 = this.mToTimeStringBuffer;
      int var20 = this.mDate.getHours();
      var19.append(var20);
      StringBuilder var22 = this.mToTimeStringBuffer.append(":");
      StringBuilder var23 = this.mToTimeStringBuffer;
      int var24 = this.mDate.getMinutes();
      var23.append(var24);
   }

   protected Dialog onCreateDialog(int var1) {
      Object var2;
      switch(var1) {
      case 100:
         OnDateSetListener var3 = this.mFromDateSetListener;
         int var4 = this.mYear;
         int var5 = this.mMonth;
         int var6 = this.mDay;
         var2 = new DatePickerDialog(this, var3, var4, var5, var6);
         break;
      case 200:
         OnDateSetListener var8 = this.mToDateSetListener;
         int var9 = this.mYear;
         int var10 = this.mMonth;
         int var11 = this.mDay;
         var2 = new DatePickerDialog(this, var8, var9, var10, var11);
         break;
      case 300:
         OnTimeSetListener var13 = this.mFromTimeSetListener;
         int var14 = this.mHourOfDay;
         int var15 = this.mMinute;
         var2 = new TimePickerDialog(this, var13, var14, var15, (boolean)1);
         break;
      case 400:
         OnTimeSetListener var17 = this.mToTimeSetListener;
         int var18 = this.mHourOfDay;
         int var19 = this.mMinute;
         var2 = new TimePickerDialog(this, var17, var18, var19, (boolean)1);
         break;
      default:
         var2 = null;
      }

      return (Dialog)var2;
   }

   class 3 implements OnTimeSetListener {

      3() {}

      public void onTimeSet(TimePicker var1, int var2, int var3) {
         CustomizeDateScreen var4 = CustomizeDateScreen.this;
         int var5 = CustomizeDateScreen.this.mToHourOfDay = var2;
         var4.mHourToSearch = var5;
         CustomizeDateScreen var7 = CustomizeDateScreen.this;
         int var8 = CustomizeDateScreen.this.mToMinute = var3;
         var7.mMinuteToSearch = var8;
         CustomizeDateScreen.this.updateToTime();
      }
   }

   class 2 implements OnDateSetListener {

      2() {}

      public void onDateSet(DatePicker var1, int var2, int var3, int var4) {
         CustomizeDateScreen.this.mToYear = var2;
         CustomizeDateScreen.this.mToMonth = var3;
         CustomizeDateScreen.this.mToDay = var4;
         Date var8 = new Date(var2, var3, var4);
         CustomizeDateScreen var9 = CustomizeDateScreen.this;
         int var10 = var8.getYear();
         var9.mYearToSearch = var10;
         CustomizeDateScreen var12 = CustomizeDateScreen.this;
         int var13 = var8.getMonth();
         var12.mMonthToSearch = var13;
         CustomizeDateScreen var15 = CustomizeDateScreen.this;
         int var16 = var8.getDate();
         var15.mDayToSearch = var16;
         CustomizeDateScreen.this.updateToDate();
      }
   }

   class 4 implements OnTimeSetListener {

      4() {}

      public void onTimeSet(TimePicker var1, int var2, int var3) {
         CustomizeDateScreen var4 = CustomizeDateScreen.this;
         int var5 = CustomizeDateScreen.this.mFromHourOfDay = var2;
         var4.mHourFromSearch = var5;
         CustomizeDateScreen var7 = CustomizeDateScreen.this;
         int var8 = CustomizeDateScreen.this.mFromMinute = var3;
         var7.mMinuteFromSearch = var8;
         CustomizeDateScreen.this.updateFromTime();
      }
   }

   class 1 implements OnDateSetListener {

      1() {}

      public void onDateSet(DatePicker var1, int var2, int var3, int var4) {
         CustomizeDateScreen.this.mFromYear = var2;
         CustomizeDateScreen.this.mFromMonth = var3;
         CustomizeDateScreen.this.mFromDay = var4;
         Date var8 = new Date(var2, var3, var4);
         CustomizeDateScreen var9 = CustomizeDateScreen.this;
         int var10 = var8.getYear();
         var9.mYearFromSearch = var10;
         CustomizeDateScreen var12 = CustomizeDateScreen.this;
         int var13 = var8.getMonth();
         var12.mMonthFromSearch = var13;
         CustomizeDateScreen var15 = CustomizeDateScreen.this;
         int var16 = var8.getDate();
         var15.mDayFromSearch = var16;
         CustomizeDateScreen.this.updateFromDate();
      }
   }
}
