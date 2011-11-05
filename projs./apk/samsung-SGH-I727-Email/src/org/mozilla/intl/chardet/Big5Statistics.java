package org.mozilla.intl.chardet;

import org.mozilla.intl.chardet.nsEUCStatistics;

public class Big5Statistics extends nsEUCStatistics {

   static float[] mFirstByteFreq;
   static float mFirstByteMean;
   static float mFirstByteStdDev;
   static float mFirstByteWeight;
   static float[] mSecondByteFreq;
   static float mSecondByteMean;
   static float mSecondByteStdDev;
   static float mSecondByteWeight;


   public Big5Statistics() {
      mFirstByteFreq = new float[]{(float)false, (float)false, (float)false, (float)1038768308, (float)1031411700, (float)1033556768, (float)1028010086, (float)1031995547, (float)1021178940, (float)1036266490, (float)1022484074, (float)1023431919, (float)1024930863, (float)1018339430, (float)1017020875, (float)1025086824, (float)1016333144, (float)1011945298, (float)1022767542, (float)1015941765, (float)1015615347, (float)1017102480, (float)1010313210, (float)1023872690, (float)1015576156, (float)1015154175, (float)1018153136, (float)1011535128, (float)1015761376, (float)1017800949, (float)1011616733, (float)1008164653, (float)1012019386, (float)1006750535, (float)1009066596, (float)1008783128, (float)1009093439, (float)false, (float)false, (float)false, (float)945704017, (float)940756215, (float)953955186, (float)939656703, (float)951618724, (float)937590287, (float)937590287, (float)937040531, (float)940756215, (float)935941020, (float)940481337, (float)969186214, (float)936490775, (float)938140043, (float)933741996, (float)935941020, (float)938140043, (float)939656703, (float)947353285, (float)942130604, (float)939239554, (float)950381773, (float)941305971, (float)942130604, (float)935391264, (float)939239554, (float)937590287, (float)948320189, (float)933741996, (float)937590287, (float)936490775, (float)949694579, (float)933192240, (float)935391264, (float)934291752, (float)932642485, (float)932092729, (float)940756215, (float)934291752, (float)933192240, (float)936490775, (float)932092729, (float)941305971, (float)932642485, (float)932642485, (float)933192240, (float)934841508, (float)940206459, (float)939931581, (float)false, (float)false, (float)false, (float)false, (float)false};
      mFirstByteStdDev = 0.020606F;
      mFirstByteMean = 0.010638F;
      mFirstByteWeight = 0.675261F;
      mSecondByteFreq = new float[]{(float)1017507817, (float)995610463, (float)1027318865, (float)1015571861, (float)1005029863, (float)998759211, (float)1008153915, (float)1005603242, (float)995623348, (float)1001106411, (float)1007137082, (float)997749357, (float)1002989754, (float)996654140, (float)998497218, (float)1015574008, (float)1008266658, (float)1001514433, (float)1023985165, (float)1003000491, (float)1007889775, (float)993497339, (float)998920272, (float)1009365096, (float)1014022988, (float)1033870703, (float)1003414956, (float)998488628, (float)1005598947, (float)1005210252, (float)1001325454, (float)1008117408, (float)1001344782, (float)1002092106, (float)999459291, (float)1005407821, (float)998529430, (float)1010220868, (float)1015321679, (float)1000036964, (float)1012003280, (float)1006346271, (float)1006882605, (float)1015561660, (float)1008392286, (float)1015484351, (float)1005504457, (float)1011867988, (float)1015985788, (float)1012423113, (float)1023471916, (float)996087205, (float)1008068016, (float)1007823203, (float)1005991936, (float)1007567652, (float)1007052256, (float)1023451247, (float)1010842565, (float)1015012978, (float)1014679044, (float)998454269, (float)1009947064, (float)1008939894, (float)1007250898, (float)1009969613, (float)998284617, (float)1007462426, (float)1008023993, (float)1014936742, (float)999860870, (float)1004746396, (float)1004937522, (float)1017505670, (float)1001424238, (float)1019895819, (float)1004808673, (float)992741425, (float)1008457784, (float)994227484, (float)1007084468, (float)1008981770, (float)1011100263, (float)1009575549, (float)1002622534, (float)1003490118, (float)1008338599, (float)1007453836, (float)1007034003, (float)1007005012, (float)1000664029, (float)1007600938, (float)1009641048, (float)1003930352};
      mSecondByteStdDev = 0.009909F;
      mSecondByteMean = 0.010638F;
      mSecondByteWeight = 0.324739F;
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
