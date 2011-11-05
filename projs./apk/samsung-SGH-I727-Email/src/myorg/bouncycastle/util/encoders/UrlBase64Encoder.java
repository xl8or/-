package myorg.bouncycastle.util.encoders;

import myorg.bouncycastle.util.encoders.Base64Encoder;

public class UrlBase64Encoder extends Base64Encoder {

   public UrlBase64Encoder() {
      byte[] var1 = this.encodingTable;
      int var2 = this.encodingTable.length - 2;
      var1[var2] = 45;
      byte[] var3 = this.encodingTable;
      int var4 = this.encodingTable.length - 1;
      var3[var4] = 95;
      this.padding = 46;
      this.initialiseDecodingTable();
   }
}
