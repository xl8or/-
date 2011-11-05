package org.mozilla.intl.chardet;

import org.mozilla.intl.chardet.nsEUCStatistics;

public class EUCTWStatistics extends nsEUCStatistics {

   static float[] mFirstByteFreq;
   static float mFirstByteMean;
   static float mFirstByteStdDev;
   static float mFirstByteWeight;
   static float[] mSecondByteFreq;
   static float mSecondByteMean;
   static float mSecondByteStdDev;
   static float mSecondByteWeight;


   public EUCTWStatistics() {
      mFirstByteFreq = new float[]{(float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)false, (float)1039420472, (float)1029042757, (float)1026866551, (float)1029112819, (float)1027344366, (float)1016853908, (float)1023736325, (float)1020726895, (float)1020324242, (float)1015203030, (float)1029112550, (float)1016970946, (float)1014497582, (float)1023552984, (float)1015958408, (float)1016561851, (float)1014967881, (float)1015556291, (float)1005841612, (float)1010744854, (float)1013186543, (float)1016918870, (float)1015461802, (float)1006674299, (float)1010140338, (float)1018896165, (float)1014901309, (float)1009017204, (float)1008928083, (float)1009354359, (float)1012313591, (float)1014748838, (float)1011668272, (float)1005824432, (float)1016432465, (float)1013420619, (float)1007808170, (float)1001475778, (float)1008520061, (float)1012845093, (float)1002938214, (float)1005766450, (float)1007298143, (float)1002345509, (float)1008282764, (float)1001606775, (float)1005253202, (float)1005790073, (float)997276911, (float)1006011263, (float)999755644, (float)1003071358, (float)995846687, (float)1000752076, (float)1002573142, (float)1001138623, (float)999910262, (float)990456503, (float)false};
      mFirstByteStdDev = 0.016681F;
      mFirstByteMean = 0.010638F;
      mFirstByteWeight = 0.715599F;
      mSecondByteFreq = new float[]{(float)1022166246, (float)1010453870, (float)1010112420, (float)1005386346, (float)1009187929, (float)998645395, (float)1014396650, (float)1010862966, (float)1018651889, (float)1007274521, (float)1011610290, (float)1005903889, (float)996903248, (float)1005186630, (float)1016786263, (float)1002918887, (float)995649118, (float)1009212625, (float)1007683616, (float)1019644563, (float)1013391628, (float)1013392702, (float)1009307114, (float)1008689712, (float)1006702216, (float)1008680049, (float)1006782747, (float)1017922282, (float)1002233840, (float)1008274174, (float)1004009809, (float)1011684378, (float)1002570995, (float)1007888701, (float)1007662142, (float)1011990395, (float)1002581732, (float)1007201506, (float)994721405, (float)1004550975, (float)1006125080, (float)1000021932, (float)1015439790, (float)1007696501, (float)1006910522, (float)1001342634, (float)1007442024, (float)1009883713, (float)1014781050, (float)1023585733, (float)1007553694, (float)1010569834, (float)1009986793, (float)1006213127, (float)1007902660, (float)1006956693, (float)1007540809, (float)1002598912, (float)1009467102, (float)1008060500, (float)999560223, (float)1007198285, (float)1005910332, (float)1004946112, (float)1002794333, (float)1018338893, (float)1019651006, (float)1015166523, (float)997650573, (float)1009165380, (float)1008989286, (float)1009172896, (float)1007173589, (float)1004527352, (float)1003522330, (float)1008059426, (float)1008066942, (float)1011216227, (float)1011036912, (float)999442111, (float)1013552689, (float)1004688414, (float)1010369045, (float)1016630570, (float)1006994274, (float)999811478, (float)1004054906, (float)1012716244, (float)1011788531, (float)1000992594, (float)1027982974, (float)1004492993, (float)1004336226, (float)1007554767};
      mSecondByteStdDev = 0.00663F;
      mSecondByteMean = 0.010638F;
      mSecondByteWeight = 0.284401F;
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
