package com.android.exchange.adapter;

import com.android.email.SecurityPolicy;
import com.android.exchange.EasSyncService;
import com.android.exchange.adapter.Parser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

public class ProvisionParser extends Parser {

   boolean mIsSupportable = 1;
   String mPolicyKey = null;
   SecurityPolicy.PolicySet mPolicySet = null;
   boolean mRemoteWipe = 0;
   private EasSyncService mService;


   public ProvisionParser(InputStream var1, EasSyncService var2) throws IOException {
      super(var1);
      this.mService = var2;
   }

   private void parseProvisionDocWbxml() throws IOException {
      int var1 = 0;
      byte var2 = 0;
      int var3 = 0;
      int var4 = 0;
      boolean var5 = true;
      boolean var6 = false;

      while(this.nextTag(909) != 3) {
         switch(this.tag) {
         case 910:
            if(this.getValueInt() == 1) {
               if(var2 == 0) {
                  var2 = 32;
               }

               var6 = true;
            }
            break;
         case 911:
            if(this.getValueInt() == 1) {
               var2 = 64;
            }
            break;
         case 912:
         case 913:
         case 919:
         case 921:
         case 922:
            if(this.getValueInt() == 1) {
               var5 = false;
            }
            break;
         case 914:
         default:
            this.skipTag();
            break;
         case 915:
            if(this.getValueInt() == 0) {
               var5 = false;
            }
            break;
         case 916:
            var1 = this.getValueInt();
            break;
         case 917:
            var4 = this.getValueInt();
            break;
         case 918:
            var3 = this.getValueInt();
            break;
         case 920:
            String var10 = this.getValue();
         }

         if(!var5) {
            StringBuilder var7 = (new StringBuilder()).append("Policy not supported: ");
            int var8 = this.tag;
            String var9 = var7.append(var8).toString();
            this.log(var9);
            this.mIsSupportable = (boolean)0;
         }
      }

      if(!var6) {
         var2 = 0;
      }

      SecurityPolicy.PolicySet var11 = new SecurityPolicy.PolicySet(var1, var2, var3, var4, (boolean)1);
      this.mPolicySet = var11;
   }

   public String getPolicyKey() {
      return this.mPolicyKey;
   }

   public SecurityPolicy.PolicySet getPolicySet() {
      return this.mPolicySet;
   }

   public boolean getRemoteWipe() {
      return this.mRemoteWipe;
   }

   public boolean hasSupportablePolicySet() {
      boolean var1;
      if(this.mPolicySet != null && this.mIsSupportable) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean parse() throws IOException {
      // $FF: Couldn't be decompiled
   }

   void parseCharacteristic(XmlPullParser var1, ProvisionParser.ShadowPolicySet var2) throws XmlPullParserException, IOException {
      boolean var3 = true;

      while(true) {
         int var4 = var1.nextTag();
         if(var4 == 3 && var1.getName().equals("characteristic")) {
            return;
         }

         if(var4 == 2 && var1.getName().equals("parm")) {
            String var5 = var1.getAttributeValue((String)null, "name");
            String var6 = var1.getAttributeValue((String)null, "value");
            if(var5.equals("AEFrequencyValue")) {
               if(var3) {
                  if(var6.equals("0")) {
                     var2.mMaxScreenLockTime = 1;
                  } else {
                     int var7 = Integer.parseInt(var6) * 60;
                     var2.mMaxScreenLockTime = var7;
                  }
               }
            } else if(var5.equals("AEFrequencyType")) {
               if(var6.equals("0")) {
                  var3 = false;
               }
            } else if(var5.equals("DeviceWipeThreshold")) {
               int var8 = Integer.parseInt(var6);
               var2.mMaxPasswordFails = var8;
            } else if(!var5.equals("CodewordFrequency")) {
               if(var5.equals("MinimumPasswordLength")) {
                  int var9 = Integer.parseInt(var6);
                  var2.mMinPasswordLength = var9;
               } else if(var5.equals("PasswordComplexity")) {
                  if(var6.equals("0")) {
                     var2.mPasswordMode = 64;
                  } else {
                     var2.mPasswordMode = 32;
                  }
               }
            }
         }
      }
   }

   public void parsePolicies() throws IOException {
      while(this.nextTag(902) != 3) {
         if(this.tag == 903) {
            this.parsePolicy();
         } else {
            this.skipTag();
         }
      }

   }

   public void parsePolicy() throws IOException {
      String var1 = null;

      while(this.nextTag(903) != 3) {
         switch(this.tag) {
         case 904:
            var1 = this.getValue();
            EasSyncService var2 = this.mService;
            String[] var3 = new String[]{"Policy type: ", var1};
            var2.userLog(var3);
            break;
         case 905:
            String var4 = this.getValue();
            this.mPolicyKey = var4;
            break;
         case 906:
            if(var1.equalsIgnoreCase("MS-WAP-Provisioning-XML")) {
               String var8 = this.getValue();
               this.parseProvisionDocXml(var8);
            } else {
               this.parseProvisionData();
            }
            break;
         case 907:
            EasSyncService var5 = this.mService;
            String[] var6 = new String[]{"Policy status: ", null};
            String var7 = this.getValue();
            var6[1] = var7;
            var5.userLog(var6);
            break;
         default:
            this.skipTag();
         }
      }

   }

   public void parseProvisionData() throws IOException {
      while(this.nextTag(906) != 3) {
         if(this.tag == 909) {
            this.parseProvisionDocWbxml();
         } else {
            this.skipTag();
         }
      }

   }

   public void parseProvisionDocXml(String var1) throws IOException {
      ProvisionParser.ShadowPolicySet var2 = new ProvisionParser.ShadowPolicySet();

      try {
         XmlPullParser var3 = XmlPullParserFactory.newInstance().newPullParser();
         byte[] var4 = var1.getBytes();
         ByteArrayInputStream var5 = new ByteArrayInputStream(var4);
         var3.setInput(var5, "UTF-8");
         if(var3.getEventType() == 0 && var3.next() == 2 && var3.getName().equals("wap-provisioningdoc")) {
            this.parseWapProvisioningDoc(var3, var2);
         }
      } catch (XmlPullParserException var12) {
         throw new IOException();
      }

      int var6 = var2.mMinPasswordLength;
      int var7 = var2.mPasswordMode;
      int var8 = var2.mMaxPasswordFails;
      int var9 = var2.mMaxScreenLockTime;
      SecurityPolicy.PolicySet var10 = new SecurityPolicy.PolicySet(var6, var7, var8, var9, (boolean)1);
      this.mPolicySet = var10;
   }

   void parseRegistry(XmlPullParser var1, ProvisionParser.ShadowPolicySet var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.nextTag();
         if(var3 == 3 && var1.getName().equals("characteristic")) {
            return;
         }

         if(var3 == 2 && var1.getName().equals("characteristic")) {
            this.parseCharacteristic(var1, var2);
         }
      }
   }

   boolean parseSecurityPolicy(XmlPullParser var1, ProvisionParser.ShadowPolicySet var2) throws XmlPullParserException, IOException {
      boolean var3 = true;

      while(true) {
         int var4 = var1.nextTag();
         if(var4 == 3 && var1.getName().equals("characteristic")) {
            return var3;
         }

         if(var4 == 2 && var1.getName().equals("parm") && var1.getAttributeValue((String)null, "name").equals("4131") && var1.getAttributeValue((String)null, "value").equals("1")) {
            var3 = false;
         }
      }
   }

   void parseWapProvisioningDoc(XmlPullParser var1, ProvisionParser.ShadowPolicySet var2) throws XmlPullParserException, IOException {
      while(true) {
         int var3 = var1.nextTag();
         if(var3 == 3 && var1.getName().equals("wap-provisioningdoc")) {
            return;
         }

         if(var3 == 2 && var1.getName().equals("characteristic")) {
            String var4 = var1.getAttributeValue((String)null, "type");
            if(var4.equals("SecurityPolicy")) {
               if(!this.parseSecurityPolicy(var1, var2)) {
                  return;
               }
            } else if(var4.equals("Registry")) {
               this.parseRegistry(var1, var2);
               return;
            }
         }
      }
   }

   class ShadowPolicySet {

      int mMaxPasswordFails = 0;
      int mMaxScreenLockTime = 0;
      int mMinPasswordLength = 0;
      int mPasswordMode = 0;


      ShadowPolicySet() {}
   }
}
