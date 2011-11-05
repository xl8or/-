package com.broadcom.bt.app.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;

public final class BluetoothDid {

   private static final int BLUETOOTH_DI_SPECIFICATION = 259;
   private static final int DI_VENDOR_ID_DEFAULT = 65535;
   private static final int DI_VENDOR_ID_SOURCE_BTSIG = 1;
   private static final int DI_VENDOR_ID_SOURCE_USBIF = 2;
   static final String FMT_DI_REC = "Record %1$s";
   private static final String[] STATUS;
   private static final String TAG = "BluetoothDid";
   private static final String[] VENDOR_ID_SOURCE;
   private static final String[] VENDOR_NAME;


   static {
      String[] var0 = new String[]{"Invalid", "BTSIG", "USBIF"};
      VENDOR_ID_SOURCE = var0;
      String[] var1 = new String[]{"Success", "Fail", "Pending"};
      STATUS = var1;
      String[] var2 = new String[]{"Ericsson Technology Licensing", "Nokia Mobile Phones", "Intel Corp.", "IBM Corp.", "Toshiba Corp.", "3Com", "Microsoft", "Lucent", "Motorola", "Infineon Technologies AG", "Cambridge Silicon Radio", "Silicon Wave", "Digianswer A/S", "Texas Instruments Inc.", "Parthus Technologies Inc.", "Broadcom Corporation", "Mitel Semiconductor", "Widcomm, Inc.", "Zeevo, Inc.", "Atmel Corporation", "Mitsubishi Electric Corporation", "RTX Telecom A/S", "KC Technology Inc.", "Newlogic", "Transilica, Inc.", "Rohde & Schwarz GmbH & Co. KG", "TTPCom Limited", "Signia Technologies, Inc.", "Conexant Systems Inc.", "Qualcomm", "Inventel", "AVM Berlin", "BandSpeed, Inc.", "Mansella Ltd", "NEC Corporation", "WavePlus Technology Co., Ltd.", "Alcatel", "Philips Semiconductors", "C Technologies", "Open Interface", "R F Micro Devices", "Hitachi Ltd", "Symbol Technologies, Inc.", "Tenovis", "Macronix International Co. Ltd.", "GCT Semiconductor", "Norwood Systems", "MewTel Technology Inc.", "ST Microelectronics", "Synopsys", "Red-M (Communications) Ltd", "Commil Ltd", "Computer Access Technology Corporation (CATC)", "Eclipse (HQ Espana) S.L.", "Renesas Technology Corp.", "Mobilian Corporation", "Terax", "Integrated System Solution Corp.", "Matsushita Electric Industrial Co., Ltd.", "Gennum Corporation", "Research In Motion", "IPextreme, Inc.", "Systems and Chips, Inc", "Bluetooth SIG, Inc", "Seiko Epson Corporation", "Integrated Silicon Solution Taiwan, Inc.", "CONWISE Technology Corporation Ltd", "PARROT SA", "Socket Mobile", "Atheros Communications, Inc.", "MediaTek, Inc.", "Bluegiga", "Marvell Technology Group Ltd.", "3DSP Corporation", "Accel Semiconductor Ltd.", "Continental Automotive Systems", "Apple, Inc.", "Staccato Communications, Inc.", "Avago Technologies", "APT Ltd.", "SiRF Technology, Inc.", "Tzero Technologies, Inc.", "J&M Corporation", "Free2move AB", "3DiJoy Corporation", "Plantronics, Inc.", "Sony Ericsson Mobile Communications", "Harman International Industries, Inc.", "Vizio, Inc.", "Nordic Semiconductor ASA", "EM Microelectronic-Marin SA", "Ralink Technology Corporation", "Belkin International, Inc."};
      VENDOR_NAME = var2;
   }

   public BluetoothDid() {}

   public static void getDidRecord(Context var0, Preference var1, Bundle var2, int var3) {
      int var4 = var2.getInt("android.bluetooth.adapter.extra.DI_STATUS");
      int var5 = var2.getInt("android.bluetooth.adapter.extra.DI_VENDOR_ID", '\uffff');
      int var6 = var2.getInt("android.bluetooth.adapter.extra.DI_VENDOR_ID_SOURCE", 0);
      int var7 = var2.getInt("android.bluetooth.adapter.extra.DI_HANDLE");
      if(var7 == var3 && var4 == 0 && var5 != '\uffff') {
         boolean var8 = var2.getBoolean("android.bluetooth.adapter.extra.DI_PRIMARY_RECORD", (boolean)0);
         int var9 = VENDOR_NAME.length;
         String var12;
         if(var5 < var9 && var6 == 1) {
            StringBuilder var10 = (new StringBuilder()).append("Vendor: ");
            String var11 = VENDOR_NAME[var5];
            var12 = var10.append(var11).toString();
         } else if(var6 >= 1 && var6 <= 2) {
            StringBuilder var41 = (new StringBuilder()).append("Vendor: ").append(var5).append(" (");
            String var42 = VENDOR_ID_SOURCE[var6];
            var12 = var41.append(var42).append(")").toString();
         } else {
            var12 = "Vendor: " + var5 + " (" + var6 + ")";
         }

         int var13 = var2.getInt("android.bluetooth.adapter.extra.DI_PRODUCT_ID", 0);
         int var14 = var2.getInt("android.bluetooth.adapter.extra.DI_VERSION", 0);
         String var15 = Integer.toHexString(var14 / 256);
         String var16 = Integer.toHexString(var14 % 256 / 16);
         String var17 = Integer.toHexString(var14 % 256 % 16);
         StringBuilder var18 = (new StringBuilder()).append(var12).append("\nProduct: ");
         String var19 = Integer.toHexString(var13);
         String var20 = var18.append(var19).append(" (v").append(var15).append(".").append(var16).append(".").append(var17).append(")").toString();
         var1.setSummary(var20);
         int var21 = var2.getInt("android.bluetooth.adapter.extra.DI_SPEC_ID", 0);
         String var22 = var2.getString("android.bluetooth.adapter.extra.DI_CLIENT_EXECUTABLE_URL");
         String var23 = var2.getString("android.bluetooth.adapter.extra.DI_SERVICE_DESCRIPTION");
         String var24 = var2.getString("android.bluetooth.adapter.extra.DI_DOCUMENTATION_URL");
         Object[] var25 = new Object[1];
         Integer var26 = Integer.valueOf(var7);
         var25[0] = var26;
         String var27 = String.format("Record %1$s", var25);
         Editor var28 = var0.getSharedPreferences(var27, 0).edit();
         var28.putInt("Handle", var7);
         var28.putInt("Status", var4);
         var28.putBoolean("PrimaryRecord", var8);
         var28.putInt("SpecId", var21);
         var28.putInt("VendorId", var5);
         var28.putInt("VendorIdSource", var6);
         var28.putInt("ProductId", var13);
         var28.putInt("Version", var14);
         var28.putString("ClientExecutableUrl", var22);
         var28.putString("ServiceDescription", var23);
         var28.putString("DocumentationUrl", var24);
         boolean var40 = var28.commit();
      } else {
         var1.setSummary("");
      }
   }

   public static String showRemoteDiRecord(Context var0, Integer var1) {
      Object[] var2 = new Object[]{var1};
      String var3 = String.format("Record %1$s", var2);
      SharedPreferences var4 = var0.getSharedPreferences(var3, 0);
      String var7;
      if(var4.getInt("Handle", 0) == 0) {
         String var5 = "Error storing/retrieving DID record " + var1;
         int var6 = Log.e("BluetoothDid", var5);
         var7 = null;
      } else {
         String var8 = "DID Record: " + var1;
         Integer var9 = Integer.valueOf(var4.getInt("Status", 1));
         StringBuilder var10 = (new StringBuilder()).append(var8).append("\nStatus: ");
         String[] var11 = STATUS;
         int var12 = var9.intValue();
         String var13 = var11[var12];
         String var58 = var10.append(var13).toString();
         if(var9.intValue() != 0) {
            var7 = var58;
         } else {
            Integer var14 = Integer.valueOf(var4.getInt("SpecId", 0));
            StringBuilder var15 = (new StringBuilder()).append(var58).append("\nSpecification Id: ");
            String var16 = Integer.toHexString(var14.intValue());
            StringBuilder var17 = var15.append(var16).append(" (v");
            String var18 = Integer.toString(var14.intValue() / 256);
            StringBuilder var19 = var17.append(var18).append(".");
            String var20 = Integer.toString(var14.intValue() % 256);
            String var21 = var19.append(var20).append(")").toString();
            StringBuilder var22 = (new StringBuilder()).append(var21).append("\nPrimary Record: ");
            boolean var23 = var4.getBoolean("PrimaryRecord", (boolean)0);
            String var24 = var22.append(var23).toString();
            Integer var25 = Integer.valueOf(var4.getInt("VendorId", 0));
            Integer var26 = Integer.valueOf(var4.getInt("VendorIdSource", 0));
            String var27 = var24 + "\nVendor Id: " + var25;
            int var28 = var25.intValue();
            int var29 = VENDOR_NAME.length;
            if(var28 < var29 && var26.intValue() == 1) {
               StringBuilder var30 = (new StringBuilder()).append(var27).append(" (");
               String[] var31 = VENDOR_NAME;
               int var32 = var25.intValue();
               String var33 = var31[var32];
               var27 = var30.append(var33).append(")").toString();
            }

            String var34 = var27 + "\nVendor Id Source: " + var26;
            String var39;
            if(var26.intValue() >= 1 && var26.intValue() <= 2) {
               StringBuilder var35 = (new StringBuilder()).append(var34).append(" (");
               String[] var36 = VENDOR_ID_SOURCE;
               int var37 = var26.intValue();
               String var38 = var36[var37];
               var39 = var35.append(var38).append(")").toString();
            } else {
               var39 = var34;
            }

            Integer var40 = Integer.valueOf(var4.getInt("ProductId", 0));
            StringBuilder var41 = (new StringBuilder()).append(var39).append("\nProduct Id: ");
            String var42 = Integer.toHexString(var40.intValue());
            String var43 = var41.append(var42).toString();
            Integer var44 = Integer.valueOf(var4.getInt("Version", 0));
            StringBuilder var45 = (new StringBuilder()).append(var43).append("\nVersion: ");
            String var46 = Integer.toHexString(var44.intValue());
            String var47 = var45.append(var46).toString();
            String var48 = Integer.toHexString(var44.intValue() / 256);
            Integer var49 = Integer.valueOf(var44.intValue() % 256);
            String var50 = Integer.toHexString(var49.intValue() / 16);
            String var51 = Integer.toHexString(var49.intValue() % 16);
            String var52 = var47 + " (v" + var48 + "." + var50 + "." + var51 + ")";
            String var53 = var4.getString("ClientExecutableUrl", "");
            String var54 = var52 + "\nClient Executable URL: " + var53;
            String var55 = var4.getString("ServiceDescription", "");
            String var56 = var54 + "\nService Description: " + var55;
            String var57 = var4.getString("DocumentationUrl", "");
            var7 = var56 + "\nDocumentation URL: " + var57;
         }
      }

      return var7;
   }
}
