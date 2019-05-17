

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Department implements Serializable
{
	public Department()
	{
		
	}
	
	public String toString()
	{
		return "Java Department object: " + this.name + " (" + this.ref + ")";
	}
	
	public Department(String name, String ref) 
	{
		this.name = name;
		this.ref = ref;
	}
	private String name;
	private String ref;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	
	
}
