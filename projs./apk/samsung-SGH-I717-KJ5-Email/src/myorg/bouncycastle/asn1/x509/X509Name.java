package myorg.bouncycastle.asn1.x509;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import myorg.bouncycastle.asn1.ASN1Encodable;
import myorg.bouncycastle.asn1.ASN1EncodableVector;
import myorg.bouncycastle.asn1.ASN1Object;
import myorg.bouncycastle.asn1.ASN1Sequence;
import myorg.bouncycastle.asn1.ASN1Set;
import myorg.bouncycastle.asn1.ASN1TaggedObject;
import myorg.bouncycastle.asn1.DEREncodable;
import myorg.bouncycastle.asn1.DERObject;
import myorg.bouncycastle.asn1.DERObjectIdentifier;
import myorg.bouncycastle.asn1.DERSequence;
import myorg.bouncycastle.asn1.DERSet;
import myorg.bouncycastle.asn1.DERString;
import myorg.bouncycastle.asn1.DERUniversalString;
import myorg.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import myorg.bouncycastle.asn1.x509.X509DefaultEntryConverter;
import myorg.bouncycastle.asn1.x509.X509NameEntryConverter;
import myorg.bouncycastle.asn1.x509.X509NameTokenizer;
import myorg.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import myorg.bouncycastle.util.Strings;
import myorg.bouncycastle.util.encoders.Hex;

public class X509Name extends ASN1Encodable {

   public static final DERObjectIdentifier BUSINESS_CATEGORY = new DERObjectIdentifier("2.5.4.15");
   public static final DERObjectIdentifier C = new DERObjectIdentifier("2.5.4.6");
   public static final DERObjectIdentifier CN = new DERObjectIdentifier("2.5.4.3");
   public static final DERObjectIdentifier COUNTRY_OF_CITIZENSHIP = new DERObjectIdentifier("1.3.6.1.5.5.7.9.4");
   public static final DERObjectIdentifier COUNTRY_OF_RESIDENCE = new DERObjectIdentifier("1.3.6.1.5.5.7.9.5");
   public static final DERObjectIdentifier DATE_OF_BIRTH = new DERObjectIdentifier("1.3.6.1.5.5.7.9.1");
   public static final DERObjectIdentifier DC = new DERObjectIdentifier("0.9.2342.19200300.100.1.25");
   public static final DERObjectIdentifier DMD_NAME = new DERObjectIdentifier("2.5.4.54");
   public static final DERObjectIdentifier DN_QUALIFIER = new DERObjectIdentifier("2.5.4.46");
   public static final Hashtable DefaultLookUp = new Hashtable();
   public static boolean DefaultReverse = 0;
   public static final Hashtable DefaultSymbols = new Hashtable();
   public static final DERObjectIdentifier E = EmailAddress;
   public static final DERObjectIdentifier EmailAddress = PKCSObjectIdentifiers.pkcs_9_at_emailAddress;
   private static final Boolean FALSE = new Boolean((boolean)0);
   public static final DERObjectIdentifier GENDER = new DERObjectIdentifier("1.3.6.1.5.5.7.9.3");
   public static final DERObjectIdentifier GENERATION = new DERObjectIdentifier("2.5.4.44");
   public static final DERObjectIdentifier GIVENNAME = new DERObjectIdentifier("2.5.4.42");
   public static final DERObjectIdentifier INITIALS = new DERObjectIdentifier("2.5.4.43");
   public static final DERObjectIdentifier L = new DERObjectIdentifier("2.5.4.7");
   public static final DERObjectIdentifier NAME = X509ObjectIdentifiers.id_at_name;
   public static final DERObjectIdentifier NAME_AT_BIRTH = new DERObjectIdentifier("1.3.36.8.3.14");
   public static final DERObjectIdentifier O = new DERObjectIdentifier("2.5.4.10");
   public static final Hashtable OIDLookUp = DefaultSymbols;
   public static final DERObjectIdentifier OU = new DERObjectIdentifier("2.5.4.11");
   public static final DERObjectIdentifier PLACE_OF_BIRTH = new DERObjectIdentifier("1.3.6.1.5.5.7.9.2");
   public static final DERObjectIdentifier POSTAL_ADDRESS = new DERObjectIdentifier("2.5.4.16");
   public static final DERObjectIdentifier POSTAL_CODE = new DERObjectIdentifier("2.5.4.17");
   public static final DERObjectIdentifier PSEUDONYM = new DERObjectIdentifier("2.5.4.65");
   public static final Hashtable RFC1779Symbols = new Hashtable();
   public static final Hashtable RFC2253Symbols = new Hashtable();
   public static final DERObjectIdentifier SERIALNUMBER = SN;
   public static final DERObjectIdentifier SN = new DERObjectIdentifier("2.5.4.5");
   public static final DERObjectIdentifier ST = new DERObjectIdentifier("2.5.4.8");
   public static final DERObjectIdentifier STREET = new DERObjectIdentifier("2.5.4.9");
   public static final DERObjectIdentifier SURNAME = new DERObjectIdentifier("2.5.4.4");
   public static final Hashtable SymbolLookUp = DefaultLookUp;
   public static final DERObjectIdentifier T = new DERObjectIdentifier("2.5.4.12");
   public static final DERObjectIdentifier TELEPHONE_NUMBER = X509ObjectIdentifiers.id_at_telephoneNumber;
   private static final Boolean TRUE = new Boolean((boolean)1);
   public static final DERObjectIdentifier UID = new DERObjectIdentifier("0.9.2342.19200300.100.1.1");
   public static final DERObjectIdentifier UNIQUE_IDENTIFIER = new DERObjectIdentifier("2.5.4.45");
   public static final DERObjectIdentifier UnstructuredAddress = PKCSObjectIdentifiers.pkcs_9_at_unstructuredAddress;
   public static final DERObjectIdentifier UnstructuredName = PKCSObjectIdentifiers.pkcs_9_at_unstructuredName;
   private Vector added;
   private X509NameEntryConverter converter;
   private int hashCodeValue;
   private boolean isHashCodeCalculated;
   private Vector ordering;
   private ASN1Sequence seq;
   private Vector values;


   static {
      Hashtable var0 = DefaultSymbols;
      DERObjectIdentifier var1 = C;
      var0.put(var1, "C");
      Hashtable var3 = DefaultSymbols;
      DERObjectIdentifier var4 = O;
      var3.put(var4, "O");
      Hashtable var6 = DefaultSymbols;
      DERObjectIdentifier var7 = T;
      var6.put(var7, "T");
      Hashtable var9 = DefaultSymbols;
      DERObjectIdentifier var10 = OU;
      var9.put(var10, "OU");
      Hashtable var12 = DefaultSymbols;
      DERObjectIdentifier var13 = CN;
      var12.put(var13, "CN");
      Hashtable var15 = DefaultSymbols;
      DERObjectIdentifier var16 = L;
      var15.put(var16, "L");
      Hashtable var18 = DefaultSymbols;
      DERObjectIdentifier var19 = ST;
      var18.put(var19, "ST");
      Hashtable var21 = DefaultSymbols;
      DERObjectIdentifier var22 = SN;
      var21.put(var22, "SERIALNUMBER");
      Hashtable var24 = DefaultSymbols;
      DERObjectIdentifier var25 = EmailAddress;
      var24.put(var25, "E");
      Hashtable var27 = DefaultSymbols;
      DERObjectIdentifier var28 = DC;
      var27.put(var28, "DC");
      Hashtable var30 = DefaultSymbols;
      DERObjectIdentifier var31 = UID;
      var30.put(var31, "UID");
      Hashtable var33 = DefaultSymbols;
      DERObjectIdentifier var34 = STREET;
      var33.put(var34, "STREET");
      Hashtable var36 = DefaultSymbols;
      DERObjectIdentifier var37 = SURNAME;
      var36.put(var37, "SURNAME");
      Hashtable var39 = DefaultSymbols;
      DERObjectIdentifier var40 = GIVENNAME;
      var39.put(var40, "GIVENNAME");
      Hashtable var42 = DefaultSymbols;
      DERObjectIdentifier var43 = INITIALS;
      var42.put(var43, "INITIALS");
      Hashtable var45 = DefaultSymbols;
      DERObjectIdentifier var46 = GENERATION;
      var45.put(var46, "GENERATION");
      Hashtable var48 = DefaultSymbols;
      DERObjectIdentifier var49 = UnstructuredAddress;
      var48.put(var49, "unstructuredAddress");
      Hashtable var51 = DefaultSymbols;
      DERObjectIdentifier var52 = UnstructuredName;
      var51.put(var52, "unstructuredName");
      Hashtable var54 = DefaultSymbols;
      DERObjectIdentifier var55 = UNIQUE_IDENTIFIER;
      var54.put(var55, "UniqueIdentifier");
      Hashtable var57 = DefaultSymbols;
      DERObjectIdentifier var58 = DN_QUALIFIER;
      var57.put(var58, "DN");
      Hashtable var60 = DefaultSymbols;
      DERObjectIdentifier var61 = PSEUDONYM;
      var60.put(var61, "Pseudonym");
      Hashtable var63 = DefaultSymbols;
      DERObjectIdentifier var64 = POSTAL_ADDRESS;
      var63.put(var64, "PostalAddress");
      Hashtable var66 = DefaultSymbols;
      DERObjectIdentifier var67 = NAME_AT_BIRTH;
      var66.put(var67, "NameAtBirth");
      Hashtable var69 = DefaultSymbols;
      DERObjectIdentifier var70 = COUNTRY_OF_CITIZENSHIP;
      var69.put(var70, "CountryOfCitizenship");
      Hashtable var72 = DefaultSymbols;
      DERObjectIdentifier var73 = COUNTRY_OF_RESIDENCE;
      var72.put(var73, "CountryOfResidence");
      Hashtable var75 = DefaultSymbols;
      DERObjectIdentifier var76 = GENDER;
      var75.put(var76, "Gender");
      Hashtable var78 = DefaultSymbols;
      DERObjectIdentifier var79 = PLACE_OF_BIRTH;
      var78.put(var79, "PlaceOfBirth");
      Hashtable var81 = DefaultSymbols;
      DERObjectIdentifier var82 = DATE_OF_BIRTH;
      var81.put(var82, "DateOfBirth");
      Hashtable var84 = DefaultSymbols;
      DERObjectIdentifier var85 = POSTAL_CODE;
      var84.put(var85, "PostalCode");
      Hashtable var87 = DefaultSymbols;
      DERObjectIdentifier var88 = BUSINESS_CATEGORY;
      var87.put(var88, "BusinessCategory");
      Hashtable var90 = DefaultSymbols;
      DERObjectIdentifier var91 = TELEPHONE_NUMBER;
      var90.put(var91, "TelephoneNumber");
      Hashtable var93 = DefaultSymbols;
      DERObjectIdentifier var94 = NAME;
      var93.put(var94, "Name");
      Hashtable var96 = RFC2253Symbols;
      DERObjectIdentifier var97 = C;
      var96.put(var97, "C");
      Hashtable var99 = RFC2253Symbols;
      DERObjectIdentifier var100 = O;
      var99.put(var100, "O");
      Hashtable var102 = RFC2253Symbols;
      DERObjectIdentifier var103 = OU;
      var102.put(var103, "OU");
      Hashtable var105 = RFC2253Symbols;
      DERObjectIdentifier var106 = CN;
      var105.put(var106, "CN");
      Hashtable var108 = RFC2253Symbols;
      DERObjectIdentifier var109 = L;
      var108.put(var109, "L");
      Hashtable var111 = RFC2253Symbols;
      DERObjectIdentifier var112 = ST;
      var111.put(var112, "ST");
      Hashtable var114 = RFC2253Symbols;
      DERObjectIdentifier var115 = STREET;
      var114.put(var115, "STREET");
      Hashtable var117 = RFC2253Symbols;
      DERObjectIdentifier var118 = DC;
      var117.put(var118, "DC");
      Hashtable var120 = RFC2253Symbols;
      DERObjectIdentifier var121 = UID;
      var120.put(var121, "UID");
      Hashtable var123 = RFC1779Symbols;
      DERObjectIdentifier var124 = C;
      var123.put(var124, "C");
      Hashtable var126 = RFC1779Symbols;
      DERObjectIdentifier var127 = O;
      var126.put(var127, "O");
      Hashtable var129 = RFC1779Symbols;
      DERObjectIdentifier var130 = OU;
      var129.put(var130, "OU");
      Hashtable var132 = RFC1779Symbols;
      DERObjectIdentifier var133 = CN;
      var132.put(var133, "CN");
      Hashtable var135 = RFC1779Symbols;
      DERObjectIdentifier var136 = L;
      var135.put(var136, "L");
      Hashtable var138 = RFC1779Symbols;
      DERObjectIdentifier var139 = ST;
      var138.put(var139, "ST");
      Hashtable var141 = RFC1779Symbols;
      DERObjectIdentifier var142 = STREET;
      var141.put(var142, "STREET");
      Hashtable var144 = DefaultLookUp;
      DERObjectIdentifier var145 = C;
      var144.put("c", var145);
      Hashtable var147 = DefaultLookUp;
      DERObjectIdentifier var148 = O;
      var147.put("o", var148);
      Hashtable var150 = DefaultLookUp;
      DERObjectIdentifier var151 = T;
      var150.put("t", var151);
      Hashtable var153 = DefaultLookUp;
      DERObjectIdentifier var154 = OU;
      var153.put("ou", var154);
      Hashtable var156 = DefaultLookUp;
      DERObjectIdentifier var157 = CN;
      var156.put("cn", var157);
      Hashtable var159 = DefaultLookUp;
      DERObjectIdentifier var160 = L;
      var159.put("l", var160);
      Hashtable var162 = DefaultLookUp;
      DERObjectIdentifier var163 = ST;
      var162.put("st", var163);
      Hashtable var165 = DefaultLookUp;
      DERObjectIdentifier var166 = SN;
      var165.put("sn", var166);
      Hashtable var168 = DefaultLookUp;
      DERObjectIdentifier var169 = SN;
      var168.put("serialnumber", var169);
      Hashtable var171 = DefaultLookUp;
      DERObjectIdentifier var172 = STREET;
      var171.put("street", var172);
      Hashtable var174 = DefaultLookUp;
      DERObjectIdentifier var175 = E;
      var174.put("emailaddress", var175);
      Hashtable var177 = DefaultLookUp;
      DERObjectIdentifier var178 = DC;
      var177.put("dc", var178);
      Hashtable var180 = DefaultLookUp;
      DERObjectIdentifier var181 = E;
      var180.put("e", var181);
      Hashtable var183 = DefaultLookUp;
      DERObjectIdentifier var184 = UID;
      var183.put("uid", var184);
      Hashtable var186 = DefaultLookUp;
      DERObjectIdentifier var187 = SURNAME;
      var186.put("surname", var187);
      Hashtable var189 = DefaultLookUp;
      DERObjectIdentifier var190 = GIVENNAME;
      var189.put("givenname", var190);
      Hashtable var192 = DefaultLookUp;
      DERObjectIdentifier var193 = INITIALS;
      var192.put("initials", var193);
      Hashtable var195 = DefaultLookUp;
      DERObjectIdentifier var196 = GENERATION;
      var195.put("generation", var196);
      Hashtable var198 = DefaultLookUp;
      DERObjectIdentifier var199 = UnstructuredAddress;
      var198.put("unstructuredaddress", var199);
      Hashtable var201 = DefaultLookUp;
      DERObjectIdentifier var202 = UnstructuredName;
      var201.put("unstructuredname", var202);
      Hashtable var204 = DefaultLookUp;
      DERObjectIdentifier var205 = UNIQUE_IDENTIFIER;
      var204.put("uniqueidentifier", var205);
      Hashtable var207 = DefaultLookUp;
      DERObjectIdentifier var208 = DN_QUALIFIER;
      var207.put("dn", var208);
      Hashtable var210 = DefaultLookUp;
      DERObjectIdentifier var211 = PSEUDONYM;
      var210.put("pseudonym", var211);
      Hashtable var213 = DefaultLookUp;
      DERObjectIdentifier var214 = POSTAL_ADDRESS;
      var213.put("postaladdress", var214);
      Hashtable var216 = DefaultLookUp;
      DERObjectIdentifier var217 = NAME_AT_BIRTH;
      var216.put("nameofbirth", var217);
      Hashtable var219 = DefaultLookUp;
      DERObjectIdentifier var220 = COUNTRY_OF_CITIZENSHIP;
      var219.put("countryofcitizenship", var220);
      Hashtable var222 = DefaultLookUp;
      DERObjectIdentifier var223 = COUNTRY_OF_RESIDENCE;
      var222.put("countryofresidence", var223);
      Hashtable var225 = DefaultLookUp;
      DERObjectIdentifier var226 = GENDER;
      var225.put("gender", var226);
      Hashtable var228 = DefaultLookUp;
      DERObjectIdentifier var229 = PLACE_OF_BIRTH;
      var228.put("placeofbirth", var229);
      Hashtable var231 = DefaultLookUp;
      DERObjectIdentifier var232 = DATE_OF_BIRTH;
      var231.put("dateofbirth", var232);
      Hashtable var234 = DefaultLookUp;
      DERObjectIdentifier var235 = POSTAL_CODE;
      var234.put("postalcode", var235);
      Hashtable var237 = DefaultLookUp;
      DERObjectIdentifier var238 = BUSINESS_CATEGORY;
      var237.put("businesscategory", var238);
      Hashtable var240 = DefaultLookUp;
      DERObjectIdentifier var241 = TELEPHONE_NUMBER;
      var240.put("telephonenumber", var241);
      Hashtable var243 = DefaultLookUp;
      DERObjectIdentifier var244 = NAME;
      var243.put("name", var244);
   }

   public X509Name(String var1) {
      boolean var2 = DefaultReverse;
      Hashtable var3 = DefaultLookUp;
      this(var2, var3, var1);
   }

   public X509Name(String var1, X509NameEntryConverter var2) {
      boolean var3 = DefaultReverse;
      Hashtable var4 = DefaultLookUp;
      this(var3, var4, var1, var2);
   }

   public X509Name(Hashtable var1) {
      this((Vector)null, var1);
   }

   public X509Name(Vector var1, Hashtable var2) {
      X509DefaultEntryConverter var3 = new X509DefaultEntryConverter();
      this(var1, var2, var3);
   }

   public X509Name(Vector var1, Hashtable var2, X509NameEntryConverter var3) {
      this.converter = null;
      Vector var4 = new Vector();
      this.ordering = var4;
      Vector var5 = new Vector();
      this.values = var5;
      Vector var6 = new Vector();
      this.added = var6;
      this.converter = var3;
      if(var1 != null) {
         int var7 = 0;

         while(true) {
            int var8 = var1.size();
            if(var7 == var8) {
               break;
            }

            Vector var9 = this.ordering;
            Object var10 = var1.elementAt(var7);
            var9.addElement(var10);
            Vector var11 = this.added;
            Boolean var12 = FALSE;
            var11.addElement(var12);
            ++var7;
         }
      } else {
         Enumeration var13 = var2.keys();

         while(var13.hasMoreElements()) {
            Vector var14 = this.ordering;
            Object var15 = var13.nextElement();
            var14.addElement(var15);
            Vector var16 = this.added;
            Boolean var17 = FALSE;
            var16.addElement(var17);
         }
      }

      int var18 = 0;

      while(true) {
         int var19 = this.ordering.size();
         if(var18 == var19) {
            return;
         }

         DERObjectIdentifier var20 = (DERObjectIdentifier)this.ordering.elementAt(var18);
         if(var2.get(var20) == null) {
            StringBuilder var21 = (new StringBuilder()).append("No attribute for object id - ");
            String var22 = var20.getId();
            String var23 = var21.append(var22).append(" - passed to distinguished name").toString();
            throw new IllegalArgumentException(var23);
         }

         Vector var24 = this.values;
         Object var25 = var2.get(var20);
         var24.addElement(var25);
         ++var18;
      }
   }

   public X509Name(Vector var1, Vector var2) {
      X509DefaultEntryConverter var3 = new X509DefaultEntryConverter();
      this(var1, var2, var3);
   }

   public X509Name(Vector var1, Vector var2, X509NameEntryConverter var3) {
      this.converter = null;
      Vector var4 = new Vector();
      this.ordering = var4;
      Vector var5 = new Vector();
      this.values = var5;
      Vector var6 = new Vector();
      this.added = var6;
      this.converter = var3;
      int var7 = var1.size();
      int var8 = var2.size();
      if(var7 != var8) {
         throw new IllegalArgumentException("oids vector must be same length as values.");
      } else {
         int var9 = 0;

         while(true) {
            int var10 = var1.size();
            if(var9 >= var10) {
               return;
            }

            Vector var11 = this.ordering;
            Object var12 = var1.elementAt(var9);
            var11.addElement(var12);
            Vector var13 = this.values;
            Object var14 = var2.elementAt(var9);
            var13.addElement(var14);
            Vector var15 = this.added;
            Boolean var16 = FALSE;
            var15.addElement(var16);
            ++var9;
         }
      }
   }

   public X509Name(ASN1Sequence var1) {
      this.converter = null;
      Vector var2 = new Vector();
      this.ordering = var2;
      Vector var3 = new Vector();
      this.values = var3;
      Vector var4 = new Vector();
      this.added = var4;
      this.seq = var1;
      Enumeration var5 = var1.getObjects();

      while(var5.hasMoreElements()) {
         ASN1Set var6 = ASN1Set.getInstance(var5.nextElement());
         int var7 = 0;

         while(true) {
            int var8 = var6.size();
            if(var7 >= var8) {
               break;
            }

            ASN1Sequence var9 = ASN1Sequence.getInstance(var6.getObjectAt(var7));
            if(var9.size() != 2) {
               throw new IllegalArgumentException("badly sized pair");
            }

            Vector var10 = this.ordering;
            DERObjectIdentifier var11 = DERObjectIdentifier.getInstance(var9.getObjectAt(0));
            var10.addElement(var11);
            DEREncodable var12 = var9.getObjectAt(1);
            if(var12 instanceof DERString && !(var12 instanceof DERUniversalString)) {
               String var13 = ((DERString)var12).getString();
               if(var13.length() > 0 && var13.charAt(0) == 35) {
                  Vector var14 = this.values;
                  String var15 = "\\" + var13;
                  var14.addElement(var15);
               } else {
                  this.values.addElement(var13);
               }
            } else {
               Vector var18 = this.values;
               StringBuilder var19 = (new StringBuilder()).append("#");
               byte[] var20 = Hex.encode(var12.getDERObject().getDEREncoded());
               String var21 = this.bytesToString(var20);
               String var22 = var19.append(var21).toString();
               var18.addElement(var22);
            }

            Vector var16 = this.added;
            Boolean var17;
            if(var7 != 0) {
               var17 = TRUE;
            } else {
               var17 = FALSE;
            }

            var16.addElement(var17);
            ++var7;
         }
      }

   }

   public X509Name(boolean var1, String var2) {
      Hashtable var3 = DefaultLookUp;
      this(var1, var3, var2);
   }

   public X509Name(boolean var1, String var2, X509NameEntryConverter var3) {
      Hashtable var4 = DefaultLookUp;
      this(var1, var4, var2, var3);
   }

   public X509Name(boolean var1, Hashtable var2, String var3) {
      X509DefaultEntryConverter var4 = new X509DefaultEntryConverter();
      this(var1, var2, var3, var4);
   }

   public X509Name(boolean var1, Hashtable var2, String var3, X509NameEntryConverter var4) {
      Object var5 = null;
      this.converter = (X509NameEntryConverter)var5;
      Vector var6 = new Vector();
      this.ordering = var6;
      Vector var7 = new Vector();
      this.values = var7;
      Vector var8 = new Vector();
      this.added = var8;
      this.converter = var4;
      X509NameTokenizer var10 = new X509NameTokenizer(var3);

      while(var10.hasMoreTokens()) {
         String var13 = var10.nextToken();
         byte var15 = 61;
         int var16 = var13.indexOf(var15);
         char var18 = '\uffff';
         if(var16 == var18) {
            throw new IllegalArgumentException("badly formated directory string");
         }

         byte var20 = 0;
         String var22 = var13.substring(var20, var16);
         int var23 = var16 + 1;
         String var26 = var13.substring(var23);
         DERObjectIdentifier var30 = this.decodeOID(var22, var2);
         byte var32 = 43;
         if(var26.indexOf(var32) > 0) {
            X509NameTokenizer var33 = new X509NameTokenizer;
            char var36 = 43;
            var33.<init>(var26, var36);
            String var37 = var33.nextToken();
            Vector var38 = this.ordering;
            var38.addElement(var30);
            Vector var40 = this.values;
            var40.addElement(var37);
            Vector var42 = this.added;
            Boolean var43 = FALSE;
            var42.addElement(var43);

            while(var33.hasMoreTokens()) {
               String var44 = var33.nextToken();
               byte var46 = 61;
               int var47 = var44.indexOf(var46);
               if(var47 >= 0) {
                  byte var49 = 0;
                  String var51 = var44.substring(var49, var47);
                  int var52 = var47 + 1;
                  String var55 = var44.substring(var52);
                  Vector var56 = this.ordering;
                  DERObjectIdentifier var60 = this.decodeOID(var51, var2);
                  var56.addElement(var60);
                  Vector var61 = this.values;
                  var61.addElement(var55);
                  Vector var63 = this.added;
                  Boolean var64 = TRUE;
                  var63.addElement(var64);
               }
            }
         } else {
            Vector var65 = this.ordering;
            var65.addElement(var30);
            Vector var67 = this.values;
            var67.addElement(var26);
            Vector var69 = this.added;
            Boolean var70 = FALSE;
            var69.addElement(var70);
         }
      }

      if(var1) {
         Vector var71 = new Vector();
         Vector var72 = new Vector();
         Vector var73 = new Vector();
         int var74 = 1;
         int var75 = 0;

         while(true) {
            int var76 = this.ordering.size();
            if(var75 >= var76) {
               this.ordering = var71;
               this.values = var72;
               this.added = var73;
               return;
            }

            Vector var79 = this.added;
            if(((Boolean)var79.elementAt(var75)).booleanValue()) {
               Vector var81 = this.ordering;
               Object var83 = var81.elementAt(var75);
               var71.insertElementAt(var83, var74);
               Vector var87 = this.values;
               Object var89 = var87.elementAt(var75);
               var72.insertElementAt(var89, var74);
               Vector var93 = this.added;
               Object var95 = var93.elementAt(var75);
               var73.insertElementAt(var95, var74);
               ++var74;
            } else {
               Vector var99 = this.ordering;
               Object var101 = var99.elementAt(var75);
               byte var104 = 0;
               var71.insertElementAt(var101, var104);
               Vector var105 = this.values;
               Object var107 = var105.elementAt(var75);
               byte var110 = 0;
               var72.insertElementAt(var107, var110);
               Vector var111 = this.added;
               Object var113 = var111.elementAt(var75);
               byte var116 = 0;
               var73.insertElementAt(var113, var116);
               var74 = 1;
            }

            ++var75;
         }
      }
   }

   private void appendValue(StringBuffer var1, Hashtable var2, DERObjectIdentifier var3, String var4) {
      String var5 = (String)var2.get(var3);
      if(var5 != null) {
         var1.append(var5);
      } else {
         String var12 = var3.getId();
         var1.append(var12);
      }

      StringBuffer var7 = var1.append('=');
      int var8 = var1.length();
      var1.append(var4);
      int var10 = var1.length();
      if(var4.length() >= 2 && var4.charAt(0) == 92 && var4.charAt(1) == 35) {
         var8 += 2;
      }

      for(; var8 != var10; ++var8) {
         if(var1.charAt(var8) == 44 || var1.charAt(var8) == 34 || var1.charAt(var8) == 92 || var1.charAt(var8) == 43 || var1.charAt(var8) == 61 || var1.charAt(var8) == 60 || var1.charAt(var8) == 62 || var1.charAt(var8) == 59) {
            var1.insert(var8, "\\");
            ++var8;
            ++var10;
         }
      }

   }

   private String bytesToString(byte[] var1) {
      char[] var2 = new char[var1.length];
      int var3 = 0;

      while(true) {
         int var4 = var2.length;
         if(var3 == var4) {
            return new String(var2);
         }

         char var5 = (char)(var1[var3] & 255);
         var2[var3] = var5;
         ++var3;
      }
   }

   private String canonicalize(String var1) {
      String var2 = Strings.toLowerCase(var1.trim());
      if(var2.length() > 0 && var2.charAt(0) == 35) {
         ASN1Object var3 = this.decodeObject(var2);
         if(var3 instanceof DERString) {
            var2 = Strings.toLowerCase(((DERString)var3).getString().trim());
         }
      }

      return var2;
   }

   private DERObjectIdentifier decodeOID(String var1, Hashtable var2) {
      DERObjectIdentifier var4;
      if(Strings.toUpperCase(var1).startsWith("OID.")) {
         String var3 = var1.substring(4);
         var4 = new DERObjectIdentifier(var3);
      } else if(var1.charAt(0) >= 48 && var1.charAt(0) <= 57) {
         var4 = new DERObjectIdentifier(var1);
      } else {
         String var5 = Strings.toLowerCase(var1);
         DERObjectIdentifier var6 = (DERObjectIdentifier)var2.get(var5);
         if(var6 == null) {
            String var7 = "Unknown object id - " + var1 + " - passed to distinguished name";
            throw new IllegalArgumentException(var7);
         }

         var4 = var6;
      }

      return var4;
   }

   private ASN1Object decodeObject(String var1) {
      try {
         ASN1Object var2 = ASN1Object.fromByteArray(Hex.decode(var1.substring(1)));
         return var2;
      } catch (IOException var5) {
         String var4 = "unknown encoding in name: " + var5;
         throw new IllegalStateException(var4);
      }
   }

   private boolean equivalentStrings(String var1, String var2) {
      String var3 = this.canonicalize(var1);
      String var4 = this.canonicalize(var2);
      boolean var7;
      if(!var3.equals(var4)) {
         String var5 = this.stripInternalSpaces(var3);
         String var6 = this.stripInternalSpaces(var4);
         if(!var5.equals(var6)) {
            var7 = false;
            return var7;
         }
      }

      var7 = true;
      return var7;
   }

   public static X509Name getInstance(Object var0) {
      X509Name var1;
      if(var0 != null && !(var0 instanceof X509Name)) {
         if(!(var0 instanceof ASN1Sequence)) {
            StringBuilder var3 = (new StringBuilder()).append("unknown object in factory: ");
            String var4 = var0.getClass().getName();
            String var5 = var3.append(var4).toString();
            throw new IllegalArgumentException(var5);
         }

         ASN1Sequence var2 = (ASN1Sequence)var0;
         var1 = new X509Name(var2);
      } else {
         var1 = (X509Name)var0;
      }

      return var1;
   }

   public static X509Name getInstance(ASN1TaggedObject var0, boolean var1) {
      return getInstance(ASN1Sequence.getInstance(var0, var1));
   }

   private String stripInternalSpaces(String var1) {
      StringBuffer var2 = new StringBuffer();
      if(var1.length() != 0) {
         char var3 = var1.charAt(0);
         var2.append(var3);
         int var5 = 1;

         while(true) {
            int var6 = var1.length();
            if(var5 >= var6) {
               break;
            }

            char var7 = var1.charAt(var5);
            if(var3 != 32 || var7 != 32) {
               var2.append(var7);
            }

            var3 = var7;
            ++var5;
         }
      }

      return var2.toString();
   }

   public boolean equals(Object var1) {
      boolean var4;
      if(var1 == this) {
         var4 = true;
      } else if(!(var1 instanceof X509Name) && !(var1 instanceof ASN1Sequence)) {
         var4 = false;
      } else {
         DEREncodable var5 = (DEREncodable)var1;
         DERObject var6 = var5.getDERObject();
         DERObject var7 = this.getDERObject();
         if(var7.equals(var6)) {
            var4 = true;
         } else {
            X509Name var9;
            try {
               var9 = getInstance(var1);
            } catch (IllegalArgumentException var39) {
               var4 = false;
               return var4;
            }

            X509Name var10 = var9;
            int var11 = this.ordering.size();
            int var12 = var9.ordering.size();
            if(var11 != var12) {
               var4 = false;
            } else {
               boolean[] var16 = new boolean[var11];
               Object var17 = this.ordering.elementAt(0);
               Object var18 = var9.ordering.elementAt(0);
               int var19;
               int var20;
               if(var17.equals(var18)) {
                  var19 = 0;
                  var20 = var11;
                  var5 = null;
               } else {
                  var19 = var11 - 1;
                  var20 = -1;
               }

               int var21 = var19;

               while(true) {
                  if(var21 == var20) {
                     var4 = true;
                     break;
                  }

                  boolean var22 = false;
                  Vector var23 = this.ordering;
                  DERObjectIdentifier var25 = (DERObjectIdentifier)var23.elementAt(var21);
                  Vector var26 = this.values;
                  String var28 = (String)var26.elementAt(var21);

                  for(int var29 = 0; var29 < var11; ++var29) {
                     if(!var16[var29]) {
                        Vector var30 = var10.ordering;
                        DERObjectIdentifier var32 = (DERObjectIdentifier)var30.elementAt(var29);
                        if(var25.equals(var32)) {
                           Vector var33 = var10.values;
                           String var35 = (String)var33.elementAt(var29);
                           if(this.equivalentStrings(var28, var35)) {
                              var16[var29] = true;
                              var22 = true;
                              break;
                           }
                        }
                     }
                  }

                  if(!var22) {
                     var4 = false;
                     break;
                  }

                  var21 += var5;
               }
            }
         }
      }

      return var4;
   }

   public boolean equals(Object var1, boolean var2) {
      boolean var3;
      if(!var2) {
         var3 = this.equals(var1);
      } else if(var1 == this) {
         var3 = true;
      } else if(!(var1 instanceof X509Name) && !(var1 instanceof ASN1Sequence)) {
         var3 = false;
      } else {
         DERObject var4 = ((DEREncodable)var1).getDERObject();
         if(this.getDERObject().equals(var4)) {
            var3 = true;
         } else {
            X509Name var5;
            try {
               var5 = getInstance(var1);
            } catch (IllegalArgumentException var15) {
               var3 = false;
               return var3;
            }

            X509Name var6 = var5;
            int var7 = this.ordering.size();
            int var8 = var5.ordering.size();
            if(var7 != var8) {
               var3 = false;
            } else {
               int var10 = 0;

               while(true) {
                  if(var10 < var7) {
                     DERObjectIdentifier var11 = (DERObjectIdentifier)this.ordering.elementAt(var10);
                     DERObjectIdentifier var12 = (DERObjectIdentifier)var6.ordering.elementAt(var10);
                     if(var11.equals(var12)) {
                        String var13 = (String)this.values.elementAt(var10);
                        String var14 = (String)var6.values.elementAt(var10);
                        if(!this.equivalentStrings(var13, var14)) {
                           var3 = false;
                           break;
                        }

                        ++var10;
                        continue;
                     }

                     var3 = false;
                     break;
                  }

                  var3 = true;
                  break;
               }
            }
         }
      }

      return var3;
   }

   public Vector getOIDs() {
      Vector var1 = new Vector();
      int var2 = 0;

      while(true) {
         int var3 = this.ordering.size();
         if(var2 == var3) {
            return var1;
         }

         Object var4 = this.ordering.elementAt(var2);
         var1.addElement(var4);
         ++var2;
      }
   }

   public Vector getValues() {
      Vector var1 = new Vector();
      int var2 = 0;

      while(true) {
         int var3 = this.values.size();
         if(var2 == var3) {
            return var1;
         }

         Object var4 = this.values.elementAt(var2);
         var1.addElement(var4);
         ++var2;
      }
   }

   public Vector getValues(DERObjectIdentifier var1) {
      Vector var2 = new Vector();
      int var3 = 0;

      while(true) {
         int var4 = this.values.size();
         if(var3 == var4) {
            return var2;
         }

         if(this.ordering.elementAt(var3).equals(var1)) {
            String var5 = (String)this.values.elementAt(var3);
            if(var5.length() > 2 && var5.charAt(0) == 92 && var5.charAt(1) == 35) {
               String var6 = var5.substring(1);
               var2.addElement(var6);
            } else {
               var2.addElement(var5);
            }
         }

         ++var3;
      }
   }

   public int hashCode() {
      int var1;
      if(this.isHashCodeCalculated) {
         var1 = this.hashCodeValue;
      } else {
         this.isHashCodeCalculated = (boolean)1;
         int var2 = 0;

         while(true) {
            int var3 = this.ordering.size();
            if(var2 == var3) {
               var1 = this.hashCodeValue;
               break;
            }

            String var4 = (String)this.values.elementAt(var2);
            String var5 = this.canonicalize(var4);
            String var6 = this.stripInternalSpaces(var5);
            int var7 = this.hashCodeValue;
            int var8 = var6.hashCode();
            int var9 = var7 ^ var8;
            this.hashCodeValue = var9;
            ++var2;
         }
      }

      return var1;
   }

   public DERObject toASN1Object() {
      if(this.seq == null) {
         ASN1EncodableVector var1 = new ASN1EncodableVector();
         ASN1EncodableVector var2 = new ASN1EncodableVector();
         DERObjectIdentifier var3 = null;
         int var4 = 0;

         while(true) {
            int var5 = this.ordering.size();
            if(var4 == var5) {
               DERSet var13 = new DERSet(var2);
               var1.add(var13);
               DERSequence var14 = new DERSequence(var1);
               this.seq = var14;
               break;
            }

            ASN1EncodableVector var6 = new ASN1EncodableVector();
            DERObjectIdentifier var7 = (DERObjectIdentifier)this.ordering.elementAt(var4);
            var6.add(var7);
            String var8 = (String)this.values.elementAt(var4);
            DERObject var9 = this.converter.getConvertedValue(var7, var8);
            var6.add(var9);
            if(var3 != null && !((Boolean)this.added.elementAt(var4)).booleanValue()) {
               DERSet var11 = new DERSet(var2);
               var1.add(var11);
               var2 = new ASN1EncodableVector();
               DERSequence var12 = new DERSequence(var6);
               var2.add(var12);
            } else {
               DERSequence var10 = new DERSequence(var6);
               var2.add(var10);
            }

            var3 = var7;
            ++var4;
         }
      }

      return this.seq;
   }

   public String toString() {
      boolean var1 = DefaultReverse;
      Hashtable var2 = DefaultSymbols;
      return this.toString(var1, var2);
   }

   public String toString(boolean var1, Hashtable var2) {
      StringBuffer var3 = new StringBuffer();
      Vector var4 = new Vector();
      boolean var5 = true;
      StringBuffer var6 = null;
      int var7 = 0;

      while(true) {
         int var8 = this.ordering.size();
         if(var7 >= var8) {
            int var14;
            if(var1) {
               for(var14 = var4.size() - 1; var14 >= 0; var14 += -1) {
                  if(var5) {
                     var5 = false;
                  } else {
                     StringBuffer var17 = var3.append(',');
                  }

                  String var15 = var4.elementAt(var14).toString();
                  var3.append(var15);
               }
            } else {
               var14 = 0;

               while(true) {
                  int var18 = var4.size();
                  if(var14 >= var18) {
                     break;
                  }

                  if(var5) {
                     var5 = false;
                  } else {
                     StringBuffer var21 = var3.append(',');
                  }

                  String var19 = var4.elementAt(var14).toString();
                  var3.append(var19);
                  ++var14;
               }
            }

            return var3.toString();
         }

         if(((Boolean)this.added.elementAt(var7)).booleanValue()) {
            StringBuffer var9 = var6.append('+');
            DERObjectIdentifier var10 = (DERObjectIdentifier)this.ordering.elementAt(var7);
            String var11 = (String)this.values.elementAt(var7);
            this.appendValue(var6, var2, var10, var11);
         } else {
            var6 = new StringBuffer();
            DERObjectIdentifier var12 = (DERObjectIdentifier)this.ordering.elementAt(var7);
            String var13 = (String)this.values.elementAt(var7);
            this.appendValue(var6, var2, var12, var13);
            var4.addElement(var6);
         }

         ++var7;
      }
   }
}
