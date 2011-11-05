package org.mozilla.intl.chardet;

import org.mozilla.intl.chardet.nsEUCStatistics;

public class EUCJPStatistics extends nsEUCStatistics {

   static float[] mFirstByteFreq;
   static float mFirstByteMean;
   static float mFirstByteStdDev;
   static float mFirstByteWeight;
   static float[] mSecondByteFreq;
   static float mSecondByteMean;
   static float mSecondByteStdDev;
   static float mSecondByteWeight;


   public EUCJPStatistics() {
      mFirstByteFreq = new float[]{(float)1052428317, (float)false, (float)false, (float)1041551380, (float)1050417836, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)988841058, (float)1009809625, (float)1003732783, (float)983017082, (float)990546697, (float)995344175, (float)984726479, (float)1005790073, (float)998851553, (float)995344175, (float)994489477, (float)1023930941, (float)1003391333, (float)1002878085, (float)1008952779, (float)991577489, (float)997229666, (float)1008052984, (float)987466668, (float)993458685, (float)990890294, (float)1014049832, (float)1011051944, (float)979773845, (float)1012336140, (float)982329888, (float)1008224782, (float)996374967, (float)993802282, (float)991916791, (float)981131055, (float)987131661, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)950519212, (float)965887679, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)950519212, (float)false};
      mFirstByteStdDev = 0.050407F;
      mFirstByteMean = 0.010638F;
      mFirstByteWeight = 0.640871F;
      mSecondByteFreq = new float[]{(float)992088590, (float)1025526521, (float)1042049327, (float)1008653205, (float)968636458, (float)1018540757, (float)977712261, (float)999107104, (float)958907820, (float)992260389, (float)994661276, (float)984382882, (float)997916861, (float)981986290, (float)996886069, (float)981131055, (float)958907820, (float)964336659, (float)1003732783, (float)986788064, (float)1023491780, (float)984382882, (float)990890294, (float)1015520858, (float)995515974, (float)976337871, (float)974963482, (float)1045652604, (float)977025066, (float)982329888, (float)997401465, (float)984726479, (float)984726479, (float)971385237, (float)999278902, (float)1002104991, (float)979773845, (float)1009166454, (float)1005019126, (float)1014006882, (float)975650677, (float)1020446649, (float)993458685, (float)977025066, (float)988497461, (float)1035697943, (float)977712261, (float)998250258, (float)974276287, (float)1023380648, (float)1005019126, (float)984382882, (float)1018754968, (float)1004847327, (float)1005446475, (float)982673485, (float)964336659, (float)1011265619, (float)979773845, (float)997745062, (float)982329888, (float)977712261, (float)990546697, (float)991062093, (float)976337871, (float)994317678, (float)995687773, (float)1019204329, (float)983704277, (float)993291181, (float)1000906695, (float)990890294, (float)1004933227, (float)1007367936, (float)1013022261, (float)1010880146, (float)1003391333, (float)989528253, (float)975650677, (float)1007667510, (float)980461040, (float)996203169, (float)1030484256, (float)971385237, (float)971385237, (float)977712261, (float)993802282, (float)987466668, (float)1010109199, (float)false, (float)965887679, (float)1011265619, (float)977025066, (float)1005704173};
      mSecondByteStdDev = 0.028247F;
      mSecondByteMean = 0.010638F;
      mSecondByteWeight = 0.359129F;
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
