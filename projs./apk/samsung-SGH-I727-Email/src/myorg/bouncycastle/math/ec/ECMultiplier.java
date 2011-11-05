package myorg.bouncycastle.math.ec;

import java.math.BigInteger;
import myorg.bouncycastle.math.ec.ECPoint;
import myorg.bouncycastle.math.ec.PreCompInfo;

interface ECMultiplier {

   ECPoint multiply(ECPoint var1, BigInteger var2, PreCompInfo var3);
}
