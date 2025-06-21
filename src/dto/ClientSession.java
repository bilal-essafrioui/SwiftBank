package dto;  /* Data transfer object */

import model.Client;
import model.Account;

// Saving The Client Session
public class ClientSession {
	private Client client;
	private Account account;
	
	public ClientSession(Client client, Account account) {
		this.client = client;
		this.account = account;
	}
	
	public Client getClient() { return client; }
    public Account getAccount() { return account; }

}
