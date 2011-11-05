package myorg.bouncycastle.jce.interfaces;


public interface ConfigurableProvider {

   String EC_IMPLICITLY_CA = "ecImplicitlyCa";
   String THREAD_LOCAL_EC_IMPLICITLY_CA = "threadLocalEcImplicitlyCa";


   void setParameter(String var1, Object var2);
}
