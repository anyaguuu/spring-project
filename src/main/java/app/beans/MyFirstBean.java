package app.beans;

public class MyFirstBean {
	
	private String name;
	private int year;
	private Long version;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	public MyFirstBean(String name, int year, Long version) {
		this.name = name;
		this.year = year;
		this.version = version;
	}
	
	public MyFirstBean() {
		
	}
	
}
