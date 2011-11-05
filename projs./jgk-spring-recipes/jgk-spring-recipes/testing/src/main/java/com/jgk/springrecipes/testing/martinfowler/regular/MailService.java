package com.jgk.springrecipes.testing.martinfowler.regular;

import javax.mail.Message;

public interface MailService {
	void send (Message msg);
}
