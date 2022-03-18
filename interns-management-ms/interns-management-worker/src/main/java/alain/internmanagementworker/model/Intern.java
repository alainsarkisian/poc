package alain.internmanagementworker.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("Intern")
public class Intern {

    @Id
    private Long idIntern;

    private String firstName;

    private String lastName;

    public Long getIdIntern() {
        return idIntern;
    }

    public void setIdIntern(Long idIntern) {
        this.idIntern = idIntern;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Intern{" +
                "idIntern=" + idIntern +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
