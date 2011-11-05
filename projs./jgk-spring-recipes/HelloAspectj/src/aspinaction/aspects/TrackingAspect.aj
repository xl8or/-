package aspinaction.aspects;
import aspinaction.MessageCommunicator;
import java.io.Serializable;

public aspect TrackingAspect {
	declare parents: MessageCommunicator implements AccessTracked;
	declare parents: MessageCommunicator implements Serializable;

	private long AccessTracked.lastAccessedTime;

	public void AccessTracked.updateLastAccessedTime() {
		lastAccessedTime = System.currentTimeMillis();
	}

	public long AccessTracked.getLastAccessedTime() {
		return lastAccessedTime;
	}

	before(AccessTracked accessTracked) : execution(* AccessTracked+.*(..))
		&& !execution(* AccessTracked.*(..))
		&& this(accessTracked) {
		accessTracked.updateLastAccessedTime();
	}

	private static interface AccessTracked {
	}
}
