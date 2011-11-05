package com.jgk.springrecipes.testing.martinfowler.regular;

import java.util.ArrayList;
import java.util.List;

import javax.mail.Message;

public class MailServiceStub implements MailService {
	private List<Message> messages = new ArrayList<Message>();

	@Override
	public void send(Message msg) {
		messages.add(msg);
	}

	public int numberSent() {
		return messages.size();
	}

}
