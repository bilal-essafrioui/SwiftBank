package model;
import java.time.LocalDate;

public class Client {
	
	private int id;
    private String firstName ;
    private String lastName;
    private LocalDate birthDate;
    private String cin;
    private String email;
    private String phoneNumber;
    private String password;
    
    // constructor
    public Client(String firstName, String lastName, LocalDate birthDate,
            String cin, String email, String phoneNumber, String password) {
	  this.firstName = firstName;
	  this.lastName = lastName;
	  this.birthDate = birthDate;
	  this.cin = cin;
	  this.email = email;
	  this.phoneNumber = phoneNumber;
	  this.password = password;
	}
    
    // getters 
    public int getId() { return id; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getCin() { return cin; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getPassword() { return password; }
    
    // Setters 
    public void setId(int id) { this.id = id; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }
    public void setCin(String cin) { this.cin = cin; }
    public void setEmail(String email) { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setPassword(String password) { this.password = password; }

}
