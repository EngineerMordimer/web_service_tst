package s12103.pjatk.pl.web_service_tst;

import java.io.Serializable;

/**
 * Created by maciek on 25/05/17.
 */
public class Person implements Serializable {

	private static final long serialVersionUID = -1138175850471335565L;

	private String name;
	private String surname;
	private String login;
	private double balance;

	public Person(String name, String surname, String login, double balance) {
		this.name = name;
		this.surname = surname;
		this.login = login;
		this.balance = balance;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getLogin() {
		return login;
	}

	public double getBalance() {
		return balance;
	}

	@Override
	public String toString() {
		return this.name + " " + this.surname + " (" + this.login + ")";
	}
}
