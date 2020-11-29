package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Employee {
    private final IntegerProperty employeeId = new SimpleIntegerProperty(this, "employeeId");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final StringProperty phone = new SimpleStringProperty(this, "phone");
    private final StringProperty position = new SimpleStringProperty(this, "position");
    private final StringProperty specialization = new SimpleStringProperty(this, "specialization");
    private final IntegerProperty maxHours = new SimpleIntegerProperty(this, "maxHours");


    public IntegerProperty employeeIdProperty() {
        return employeeId;
    }
    public final int getEmployeeId() {
        return employeeIdProperty().get();
    }
    public final void setEmployeeId(Integer Id) {
        employeeIdProperty().set(Id);
    }

    public StringProperty firstNameProperty() { return firstName; }
    public final String getFirstName() {
        return firstNameProperty().get();
    }
    public final void setFirstName(String firstName) {
        firstNameProperty().set(firstName);
    }

    public StringProperty lastNameProperty() { return lastName; }
    public final String getLastName() {
        return lastNameProperty().get();
    }
    public final void setLastName(String lastName) {
        lastNameProperty().set(lastName);
    }

    public StringProperty phoneProperty() { return phone; }
    public final String getPhone() { return phoneProperty().get(); }
    public final void setPhone(String phone) { phoneProperty().set(phone); }

    public StringProperty positionProperty() { return position; }
    public final String getPosition() { return positionProperty().get(); }
    public final void setPosition(String position) { positionProperty().set(position); }

    public IntegerProperty maxHoursProperty() {
        return maxHours;
    }
    public final int getMaxHours() {
        return maxHoursProperty().get();
    }
    public final void setMaxHours(Integer maxHours) {
        maxHoursProperty().set(maxHours);
    }

    public StringProperty specializationProperty() { return specialization; }
    public final String getSpecialization() { return specializationProperty().get(); }
    public final void setSpecialization(String specialization) { specializationProperty().set(specialization); }

    public Employee(int id, String fname, String lname, String phone, String spec, int maxHOurs, String pos) {
        setEmployeeId(id);
        setFirstName(fname);
        setLastName(lname);
        setMaxHours(maxHOurs);
        setPhone(phone);
        setPosition(pos);
        setSpecialization(spec);
    }
}
