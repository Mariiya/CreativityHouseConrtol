package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Member {

    private final IntegerProperty memberId = new SimpleIntegerProperty(this, "memberId");
    private final StringProperty birthCertificate = new SimpleStringProperty(this, "birthCertificate");
    private final StringProperty firstName = new SimpleStringProperty(this, "firstName");
    private final StringProperty lastName = new SimpleStringProperty(this, "lastName");
    private final StringProperty birthDate = new SimpleStringProperty(this, "birthDate");
    private final StringProperty medicalCertificateDate = new SimpleStringProperty(this, "medicalCertificateDate");
    private final StringProperty parentName = new SimpleStringProperty(this, "parentName");
    private final StringProperty parentPhone = new SimpleStringProperty(this, "parentPhone");


    public IntegerProperty memberIdProperty() {
        return memberId;
    }
    public final int getMemberId() {
        return memberIdProperty().get();
    }
    public final void setMemberId(Integer memberId) {
        memberIdProperty().set(memberId);
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

    public StringProperty birthDateProperty() { return birthDate; }
    public final String getBirthDate() { return birthDateProperty().get(); }
    public final void setBirthDate(String birthDate) {
        birthDateProperty().set(birthDate);
    }

    public StringProperty medicalCertificateDateProperty() { return medicalCertificateDate; }
    public final String getMedicalCertificateDate() {
        return medicalCertificateDateProperty().get();
    }
    public final void setMedicalCertificateDate(String medicalCertificateDate) {
        medicalCertificateDateProperty().set(medicalCertificateDate);
    }

    public StringProperty birthCertificateProperty() { return birthCertificate; }
    public final String getBirthCertificate() {
        return birthCertificateProperty().get();
    }
    public final void setBirthCertificate(String birthCertificate) {
        birthCertificateProperty().set(birthCertificate);
    }

    public StringProperty parentNameProperty() { return parentName; }
    public final String getParentName() { return parentNameProperty().get(); }
    public final void setParentName(String parentName) { parentNameProperty().set(parentName); }

    public StringProperty parentPhoneProperty() { return parentPhone; }
    public final String getParentPhone() { return parentPhoneProperty().get(); }
    public final void setParentPhone(String parentPhone) { parentPhoneProperty().set(parentPhone); }

    public Member(int id, String memberName,
                  String memberSurname,String birthDate,
                  String birthCertificate,String medicCertificate,
                  String parentName,String phone) {
        setBirthCertificate(birthCertificate);
        setBirthDate(birthDate);
        setFirstName(memberName);
        setLastName(memberSurname);
        setMedicalCertificateDate(medicCertificate);
        setMemberId(id);
        setParentName(parentName);
        setParentPhone(phone);
    }
}
