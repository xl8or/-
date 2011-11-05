package myorg.bouncycastle.jce.interfaces;

import myorg.bouncycastle.jce.spec.ECParameterSpec;

public interface ECKey {

   ECParameterSpec getParameters();
}
