package com.htc.android.mail.eassvc.common;

import java.util.ArrayList;

public class EASContact {

   public String AccountName;
   public String Anniversary;
   public String AssistantName;
   public String AssistantTelephoneNumber;
   public String Birthday;
   public String Body;
   public String BodySize;
   public String BodyTruncated;
   public String Business2TelephoneNumber;
   public String BusinessAddressCity;
   public String BusinessAddressCountry;
   public String BusinessAddressPostalCode;
   public String BusinessAddressState;
   public String BusinessAddressStreet;
   public String BusinessFaxNumber;
   public String BusinessTelephoneNumber;
   public String CarTelephoneNumber;
   public String Categories;
   public String[] Category;
   public String Child;
   public String Children;
   public String ClientId;
   public String CompanyMainPhone;
   public String CompanyName;
   public String CompressedRTF;
   public String CustomerId;
   public String Department;
   public String Email1Address;
   public String Email2Address;
   public String Email3Address;
   public String FileAs;
   public String FirstName;
   public String GovernmentId;
   public String Home2TelephoneNumber;
   public String HomeAddressCity;
   public String HomeAddressCountry;
   public String HomeAddressPostalCode;
   public String HomeAddressState;
   public String HomeAddressStreet;
   public String HomeFaxNumber;
   public String HomeTelephoneNumber;
   public String IMAddr;
   public String IMAddress2;
   public String IMAddress3;
   public String JobTitle;
   public String LastName;
   public String MMS;
   public String ManagerName;
   public String MiddleName;
   public String MobileTelephoneNumber;
   public String NickName;
   public String OfficeLocation;
   public String OtherAddressCity;
   public String OtherAddressCountry;
   public String OtherAddressPostalCode;
   public String OtherAddressState;
   public String OtherAddressStreet;
   public String PagerNumber;
   public String Picture;
   public String RadioTelephoneNumber;
   public String ServerID;
   public String Spouse;
   public String Suffix;
   public String Title;
   public ArrayList<EASContact.UnsupportedItem> UnsupportedList;
   public String Webpage;
   public String YomiCompanyName;
   public String YomiFirstName;
   public String YomiLastName;


   public EASContact() {
      ArrayList var1 = new ArrayList();
      this.UnsupportedList = var1;
   }

   public static class UnsupportedItem {

      public String name;
      public String value;


      public UnsupportedItem(String var1, String var2) {
         this.name = var1;
         this.value = var2;
      }
   }
}
