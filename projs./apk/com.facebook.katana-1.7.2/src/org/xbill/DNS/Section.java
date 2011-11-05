package org.xbill.DNS;

import org.xbill.DNS.Mnemonic;

public final class Section {

   public static final int ADDITIONAL = 3;
   public static final int ANSWER = 1;
   public static final int AUTHORITY = 2;
   public static final int PREREQ = 1;
   public static final int QUESTION = 0;
   public static final int UPDATE = 2;
   public static final int ZONE;
   private static String[] longSections = new String[4];
   private static Mnemonic sections = new Mnemonic("Message Section", 3);
   private static String[] updateSections = new String[4];


   static {
      sections.setMaximum(3);
      sections.setNumericAllowed((boolean)1);
      sections.add(0, "qd");
      sections.add(1, "an");
      sections.add(2, "au");
      sections.add(3, "ad");
      longSections[0] = "QUESTIONS";
      longSections[1] = "ANSWERS";
      longSections[2] = "AUTHORITY RECORDS";
      longSections[3] = "ADDITIONAL RECORDS";
      updateSections[0] = "ZONE";
      updateSections[1] = "PREREQUISITES";
      updateSections[2] = "UPDATE RECORDS";
      updateSections[3] = "ADDITIONAL RECORDS";
   }

   private Section() {}

   public static String longString(int var0) {
      sections.check(var0);
      return longSections[var0];
   }

   public static String string(int var0) {
      return sections.getText(var0);
   }

   public static String updString(int var0) {
      sections.check(var0);
      return updateSections[var0];
   }

   public static int value(String var0) {
      return sections.getValue(var0);
   }
}
