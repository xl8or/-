package com.android.i18n.addressinput;

import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

class RegionDataConstants {

   private static final Map<String, String> COUNTRY_FORMAT_MAP = new HashMap();


   static {
      RegionDataConstants.RegionDataEnum[] var0 = RegionDataConstants.RegionDataEnum.values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         RegionDataConstants.RegionDataEnum var3 = var0[var2];
         Map var4 = COUNTRY_FORMAT_MAP;
         String var5 = var3.toString();
         String var6 = var3.getJsonString();
         var4.put(var5, var6);
      }

   }

   RegionDataConstants() {}

   static String convertArrayToJsonString(String[] var0) {
      JSONObject var1 = new JSONObject();
      int var2 = 0;

      while(true) {
         int var3 = var0.length;
         if(var2 >= var3) {
            return var1.toString();
         }

         try {
            String var4 = var0[var2];
            int var5 = var2 + 1;
            String var6 = var0[var5];
            var1.put(var4, var6);
         } catch (JSONException var9) {
            ;
         }

         var2 += 2;
      }
   }

   static Map<String, String> getCountryFormatMap() {
      return COUNTRY_FORMAT_MAP;
   }

   private static enum RegionDataEnum {

      // $FF: synthetic field
      private static final RegionDataConstants.RegionDataEnum[] $VALUES;
      AD,
      AE,
      AF,
      AG,
      AI,
      AL,
      AM,
      AN,
      AO,
      AQ,
      AR,
      AS,
      AT,
      AU,
      AW,
      AX,
      AZ,
      BA,
      BB,
      BD,
      BE,
      BF,
      BG,
      BH,
      BI,
      BJ,
      BL,
      BM,
      BN,
      BO,
      BR,
      BS,
      BT,
      BV,
      BW,
      BY,
      BZ,
      CA,
      CC,
      CD,
      CF,
      CG,
      CH,
      CI,
      CK,
      CL,
      CM,
      CN,
      CO,
      CR,
      CS,
      CV,
      CX,
      CY,
      CZ,
      DE,
      DJ,
      DK,
      DM,
      DO,
      DZ,
      EC,
      EE,
      EG,
      EH,
      ER,
      ES,
      ET,
      FI,
      FJ,
      FK,
      FM,
      FO,
      FR,
      GA,
      GB,
      GD,
      GE,
      GF,
      GG,
      GH,
      GI,
      GL,
      GM,
      GN,
      GP,
      GQ,
      GR,
      GS,
      GT,
      GU,
      GW,
      GY,
      HK,
      HM,
      HN,
      HR,
      HT,
      HU,
      ID,
      IE,
      IL,
      IM,
      IN,
      IO,
      IQ,
      IS,
      IT,
      JE,
      JM,
      JO,
      JP,
      KE,
      KG,
      KH,
      KI,
      KM,
      KN,
      KR,
      KW,
      KY,
      KZ,
      LA,
      LB,
      LC,
      LI,
      LK,
      LR,
      LS,
      LT,
      LU,
      LV,
      LY,
      MA,
      MC,
      MD,
      ME,
      MF,
      MG,
      MH,
      MK,
      ML,
      MN,
      MO,
      MP,
      MQ,
      MR,
      MS,
      MT,
      MU,
      MV,
      MW,
      MX,
      MY,
      MZ,
      NA,
      NC,
      NE,
      NF,
      NG,
      NI,
      NL,
      NO,
      NP,
      NR,
      NU,
      NZ,
      OM,
      PA,
      PE,
      PF,
      PG,
      PH,
      PK,
      PL,
      PM,
      PN,
      PR,
      PS,
      PT,
      PW,
      PY,
      QA,
      RE,
      RO,
      RS,
      RU,
      RW,
      SA,
      SB,
      SC,
      SE,
      SG,
      SH,
      SI,
      SJ,
      SK,
      SL,
      SM,
      SN,
      SO,
      SR,
      ST,
      SV,
      SZ,
      TC,
      TD,
      TF,
      TG,
      TH,
      TJ,
      TK,
      TL,
      TM,
      TN,
      TO,
      TR,
      TT,
      TV,
      TW,
      TZ,
      UA,
      UG,
      UM,
      US,
      UY,
      UZ,
      VA,
      VC,
      VE,
      VG,
      VI,
      VN,
      VU,
      WF,
      WS,
      YE,
      YT,
      YU,
      ZA,
      ZM,
      ZW,
      ZZ;
      private String jsonString;


      static {
         // $FF: Couldn't be decompiled
      }

      private RegionDataEnum(String var1, int var2, String[] var3) {
         String var4 = RegionDataConstants.convertArrayToJsonString(var3);
         this.jsonString = var4;
      }

      String getJsonString() {
         return this.jsonString;
      }
   }
}
