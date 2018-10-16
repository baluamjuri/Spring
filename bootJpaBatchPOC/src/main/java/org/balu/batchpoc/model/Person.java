package org.balu.batchpoc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class Person {
	/*@Id
	@Column(name = "person_id")
	@GeneratedValue(generator = "personId")
	@GenericGenerator(name = "personId", strategy = "org.balu.batchpoc.misc.PersonIdGenerator")
	private String id;
	*/
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="first_name")
    private String lastName;
	
	@Column(name="last_name")
    private String firstName;

    public Person() {

    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
	/*public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}*/

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}    

	public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


	public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "id: " + id + ", firstName: " + firstName + ", lastName: " + lastName;
    }

}