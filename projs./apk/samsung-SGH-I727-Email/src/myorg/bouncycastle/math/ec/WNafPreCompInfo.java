package myorg.bouncycastle.math.ec;

import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.math.ec.PreCompInfo;

class WNafPreCompInfo implements PreCompInfo {

   private ECPoint[] preComp = null;
   private ECPoint twiceP = null;


   WNafPreCompInfo() {}

   protected ECPoint[] getPreComp() {
      return this.preComp;
   }

   protected ECPoint getTwiceP() {
      return this.twiceP;
   }

   protected void setPreComp(ECPoint[] var1) {
      this.preComp = var1;
   }

   protected void setTwiceP(ECPoint var1) {
      this.twiceP = var1;
   }
}
