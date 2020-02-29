package Model;

/**
 * Student
 */
public class Student {
    private int id;
    private String name;
	private String DOB;
	private int batch;

	public Student(){}

	public Student(final String name,final String DOB,final int batch) {
		this.name=name;
		this.DOB=DOB;
		this.batch=batch;
	}
	
	public Student(final int id ,final String name,final String DOB, final int batch) {
		this.id=id;
		this.name=name;
		this.DOB=DOB;
		this.batch=batch;
	}

	public int getId() {
		return id;
	}
	public String getDOB() {
		return DOB;
	}
	public int getBatch() { return batch; }
	public String getName() {
		return name;
	}
	public void setName(final String name) {
		this.name = name;
	}
	public void setId(final int id) {
		this.id = id;
	}
	public void setDOB(final String DOB) { this.DOB = DOB; }
	public void setBatch(final int batch) { this.batch = batch; }
}