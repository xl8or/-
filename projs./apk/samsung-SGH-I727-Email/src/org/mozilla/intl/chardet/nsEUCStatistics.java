package org.mozilla.intl.chardet;


public abstract class nsEUCStatistics {

   public nsEUCStatistics() {}

   public abstract float[] mFirstByteFreq();

   public abstract float mFirstByteMean();

   public abstract float mFirstByteStdDev();

   public abstract float mFirstByteWeight();

   public abstract float[] mSecondByteFreq();

   public abstract float mSecondByteMean();

   public abstract float mSecondByteStdDev();

   public abstract float mSecondByteWeight();
}
