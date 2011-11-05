package com.jgk.springrecipes.testing.martinfowler.regular;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;

public class Order {
	private String product;
	private Integer quantity;
	private boolean filled;
	private MailService mailer;

	public Order(String product, Integer quantity) {
		this.product = product;
		this.quantity = quantity;
	}

	/**
	 * fill the order
	 * 
	 * @param warehouse
	 */
	public void fill(Warehouse warehouse) {
		if (warehouse.hasInventory(product, quantity)) {
			warehouse.remove(product, quantity);
			filled = true;
		}
		if (mailer != null) {
			Message msg = new Message() {

				@Override
				public void addHeader(String arg0, String arg1)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public Enumeration getAllHeaders() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Object getContent() throws IOException,
						MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getContentType() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public DataHandler getDataHandler() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getDescription() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getDisposition() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getFileName() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String[] getHeader(String arg0)
						throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public InputStream getInputStream() throws IOException,
						MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public int getLineCount() throws MessagingException {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public Enumeration getMatchingHeaders(String[] arg0)
						throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Enumeration getNonMatchingHeaders(String[] arg0)
						throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public int getSize() throws MessagingException {
					// TODO Auto-generated method stub
					return 0;
				}

				@Override
				public boolean isMimeType(String arg0)
						throws MessagingException {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public void removeHeader(String arg0) throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setContent(Multipart arg0)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setContent(Object arg0, String arg1)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setDataHandler(DataHandler arg0)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setDescription(String arg0)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setDisposition(String arg0)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setFileName(String arg0) throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setHeader(String arg0, String arg1)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setText(String arg0) throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void writeTo(OutputStream arg0) throws IOException,
						MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void addFrom(Address[] arg0) throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void addRecipients(RecipientType arg0, Address[] arg1)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public Flags getFlags() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Address[] getFrom() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Date getReceivedDate() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Address[] getRecipients(RecipientType arg0)
						throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Date getSentDate() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String getSubject() throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Message reply(boolean arg0) throws MessagingException {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public void saveChanges() throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setFlags(Flags arg0, boolean arg1)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setFrom() throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setFrom(Address arg0) throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setRecipients(RecipientType arg0, Address[] arg1)
						throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setSentDate(Date arg0) throws MessagingException {
					// TODO Auto-generated method stub

				}

				@Override
				public void setSubject(String arg0) throws MessagingException {
					// TODO Auto-generated method stub

				}

			};
			mailer.send(msg);
		}
	}

	public boolean isFilled() {
		return filled;
	}

	public void setMailer(MailService mailer) {
		this.mailer = mailer;
	}

}
