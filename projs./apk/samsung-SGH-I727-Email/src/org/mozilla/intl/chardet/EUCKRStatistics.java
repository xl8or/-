package org.mozilla.intl.chardet;

import org.mozilla.intl.chardet.nsEUCStatistics;

public class EUCKRStatistics extends nsEUCStatistics {

   static float[] mFirstByteFreq;
   static float mFirstByteMean;
   static float mFirstByteStdDev;
   static float mFirstByteWeight;
   static float[] mSecondByteFreq;
   static float mSecondByteMean;
   static float mSecondByteStdDev;
   static float mSecondByteWeight;


   public EUCKRStatistics() {
      mFirstByteFreq = new float[]{(float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)970457524, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)1030457144, (float)1023928793, (float)991203827, (float)1015263697, (float)1013956416, (float)1023873496, (float)998707672, (float)1010416289, (float)1030733901, (float)1019800256, (float)1020574961, (float)1029627410, (float)1017144892, (float)1031902534, (float)1038707507, (float)1022677347, (float)1041867865, (float)1030899794, (float)992973353, (float)1008866880, (float)1014177607, (float)1020796152, (float)1014398798, (float)1032732403, (float)1035470981, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false};
      mFirstByteStdDev = 0.025593F;
      mFirstByteMean = 0.010638F;
      mFirstByteWeight = 0.647437F;
      mSecondByteFreq = new float[]{(float)1015595483, (float)false, (float)1011964625, (float)1022787943, (float)992973353, (float)1004018399, (float)989012857, (float)978846132, (float)1002691254, (float)1000034816, (float)994747175, (float)978846132, (float)1017144892, (float)1025035284, (float)1007096280, (float)1013956416, (float)981934751, (float)false, (float)995631938, (float)990319064, (float)1003133635, (float)1002691254, (float)978846132, (float)1018361979, (float)1014177607, (float)1029240057, (float)1015927269, (float)1006232454, (float)970457524, (float)978846132, (float)1010194025, (float)false, (float)970457524, (float)987243330, (float)1002691254, (float)1032234455, (float)1017476679, (float)1018140251, (float)1013956416, (float)998707672, (float)987243330, (float)978846132, (float)978846132, (float)1028963300, (float)1017144892, (float)1019136147, (float)1020796152, (float)1025865287, (float)1007981043, (float)985473804, (float)997401465, (float)1005790073, (float)985473804, (float)1012407007, (float)993858117, (float)970457524, (float)995631938, (float)1020574961, (float)970457524, (float)970457524, (float)1003133635, (float)995631938, (float)1032400482, (float)992973353, (float)991203827, (float)1010194025, (float)970457524, (float)1009088071, (float)1014619989, (float)1006232454, (float)1026805616, (float)997401465, (float)992973353, (float)985473804, (float)1009972834, (float)993858117, (float)1007981043, (float)1017366083, (float)970457524, (float)1007317470, (float)1000921727, (float)false, (float)1009088071, (float)false, (float)987243330, (float)1011301053, (float)1010637480, (float)1004018399, (float)989012857, (float)1009972834, (float)970457524, (float)1019357874, (float)997401465, (float)989012857};
      mSecondByteStdDev = 0.013937F;
      mSecondByteMean = 0.010638F;
      mSecondByteWeight = 0.352563F;
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
