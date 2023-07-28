package app.beans;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MyFirstBean {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private String name; // the id
	private int yearNum;
	private Long version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getYearNum() {
		return yearNum;
	}

	public void setYearNum(int year) {
		this.yearNum = year;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public MyFirstBean(String name, int year, Long version) {
		this.name = name;
		this.yearNum = year;
		this.version = version;
	}

	public MyFirstBean() {

	}

}
