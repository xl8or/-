package org.mozilla.intl.chardet;

import org.mozilla.intl.chardet.nsEUCStatistics;

public class GB2312Statistics extends nsEUCStatistics {

   static float[] mFirstByteFreq;
   static float mFirstByteMean;
   static float mFirstByteStdDev;
   static float mFirstByteWeight;
   static float[] mSecondByteFreq;
   static float mSecondByteMean;
   static float mSecondByteStdDev;
   static float mSecondByteWeight;


   public GB2312Statistics() {
      mFirstByteFreq = new float[]{(float)1010729822, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)1010729822, (float)1011561972, (float)1008232298, (float)998114429, (float)1016204831, (float)1025009783, (float)1007400149, (float)1009065522, (float)1017037518, (float)1029587950, (float)1009065522, (float)1026050239, (float)1017869668, (float)1016621443, (float)1009065522, (float)1012394122, (float)1015372682, (float)1003174438, (float)1008232298, (float)1015788757, (float)1010729822, (float)1007400149, (float)999843690, (float)1003174438, (float)1016204831, (float)1019950580, (float)1017453593, (float)1013226272, (float)1023761290, (float)1024593708, (float)1032566107, (float)1009897672, (float)1024801745, (float)1039120898, (float)1021615417, (float)1036935968, (float)1009065522, (float)1016204831, (float)1031668861, (float)1011561972, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)986392927, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false};
      mFirstByteStdDev = 0.020081F;
      mFirstByteMean = 0.010638F;
      mFirstByteWeight = 0.586533F;
      mSecondByteFreq = new float[]{(float)1003174438, (float)1023280253, (float)1001507990, (float)994785830, (float)986392927, (float)994785830, (float)1034438981, (float)1014059495, (float)1004838737, (float)986392927, (float)1013226272, (float)false, (float)1013226272, (float)1009065522, (float)1007400149, (float)1004838737, (float)999843690, (float)994785830, (float)994785830, (float)994785830, (float)1007400149, (float)994785830, (float)1001507990, (float)1001507990, (float)1001507990, (float)994785830, (float)986392927, (float)1003174438, (float)1014059495, (float)1009897672, (float)false, (float)1010729822, (float)false, (float)1023553252, (float)1013226272, (float)1022864178, (float)1025634164, (float)1007400149, (float)1014891645, (float)false, (float)994785830, (float)1007400149, (float)1015372682, (float)1001507990, (float)986392927, (float)1013226272, (float)1013226272, (float)1027090695, (float)994785830, (float)999843690, (float)1004838737, (float)986392927, (float)1001507990, (float)1011561972, (float)986392927, (float)1014891645, (float)false, (float)1003174438, (float)986392927, (float)false, (float)1006503037, (float)1004838737, (float)986392927, (float)1008232298, (float)1010729822, (float)999843690, (float)1009897672, (float)1011561972, (float)1016204831, (float)1001507990, (float)1019950580, (float)false, (float)1003174438, (float)false, (float)1034438981, (float)1014891645, (float)999843690, (float)false, (float)1004838737, (float)999843690, (float)false, (float)1007400149, (float)1011561972, (float)999843690, (float)998114429, (float)994785830, (float)1018702355, (float)1019534505, (float)986392927, (float)1027715075, (float)1008232298, (float)986392927, (float)1001507990, (float)1015788757};
      mSecondByteStdDev = 0.014156F;
      mSecondByteMean = 0.010638F;
      mSecondByteWeight = 0.413467F;
   }

   public float[] mFirstByteFreq() {
      return mFirstByteFreq;
   }

   public float mFirstByteMean() {
      return mFirstByteMean;
   }

   public float mFirstByteStdDev() {
      return mFirstByteStdDev;
   }

   public float mFirstByteWeight() {
      return mFirstByteWeight;
   }

   public float[] mSecondByteFreq() {
      return mSecondByteFreq;
   }

   public float mSecondByteMean() {
      return mSecondByteMean;
   }

   public float mSecondByteStdDev() {
      return mSecondByteStdDev;
   }

   public float mSecondByteWeight() {
      return mSecondByteWeight;
   }
}
