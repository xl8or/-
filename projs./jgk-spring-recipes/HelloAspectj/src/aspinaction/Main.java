package aspinaction;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Authenticator a = new Authenticator();
		//a.authenticate();
		MessageCommunicator messageCommunicator = new MessageCommunicator();
		messageCommunicator.deliver("Wanna learn AspectJ");
		messageCommunicator.deliver("Harry","having fun?");
		System.out.println("Last accessed time for messageCommunicator "
				+ messageCommunicator.getLastAccessedTime());
	}

}
