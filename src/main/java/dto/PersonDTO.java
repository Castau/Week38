package dto;

import entities.Address;
import entities.Person;
import java.util.Objects;

/**
 *
 * @author Camilla
 */
public class PersonDTO {

    private int id;
    private String fName;
    private String lName;
    private String phone;
    private Address address;

    public PersonDTO(String fName, String lName, String phone) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
    }
    
    public PersonDTO(Person person) {
        this.id = person.getId();
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.phone = person.getPhone();
        this.address = person.getAddress();
    }

    public PersonDTO(String fName, String lName, String phone, Address address) {
        this.fName = fName;
        this.lName = lName;
        this.phone = phone;
        this.address = address;
    }
    
    public PersonDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.fName);
        hash = 97 * hash + Objects.hashCode(this.lName);
        hash = 97 * hash + Objects.hashCode(this.phone);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PersonDTO other = (PersonDTO) obj;
        if (!Objects.equals(this.fName, other.fName)) {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName)) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        return true;
    }
    
    

}
