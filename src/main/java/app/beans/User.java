package app.beans;

import jakarta.persistence.Entity;

import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "app_users")
public class User {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private String name; // the id
	private int yearNum;
	private int version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYearNum() {
		return yearNum;
	}

	public void setYearNum(int yearNum) {
		this.yearNum = yearNum;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public User(String name, int yearNum, int version) {
		this.name = name;
		this.yearNum = yearNum;
		this.version = version;
	}

	public User() {

	}

	public String toString() {
		return this.getName();
	}

}
