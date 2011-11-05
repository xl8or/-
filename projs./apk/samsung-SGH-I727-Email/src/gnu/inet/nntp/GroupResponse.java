package gnu.inet.nntp;

import gnu.inet.nntp.StatusResponse;

public class GroupResponse extends StatusResponse {

   public int count;
   public int first;
   public String group;
   public int last;


   GroupResponse(short var1, String var2) {
      super(var1, var2);
   }
}
