package sample.model;

import javafx.beans.property.*;

public class Request {
    private final IntegerProperty requestId = new SimpleIntegerProperty(this, "requestId");
    private final StringProperty requestDate = new SimpleStringProperty(this, "requestDate");
    private final IntegerProperty memberId = new SimpleIntegerProperty(this, "memberId");
    private final StringProperty memberName = new SimpleStringProperty(this, "memberName");
    private final StringProperty sectionName = new SimpleStringProperty(this, "sectionName");
    private final IntegerProperty sectionId = new SimpleIntegerProperty(this, "sectionId");
    private final BooleanProperty verification = new SimpleBooleanProperty(this, "verification");

    private final StringProperty group = new SimpleStringProperty(this, "group");

    public StringProperty groupProperty() { return group; }
    public final String getGroup() { return groupProperty().get(); }
    public final void setGroup(String group) {
        groupProperty().set(group);
    }

    public IntegerProperty requestIdProperty() {
        return requestId;
    }
    public final int getRequestId() {
        return requestIdProperty().get();
    }
    public final void setRequestId(Integer paymentId) {
        requestIdProperty().set(paymentId);
    }

    public IntegerProperty memberIdProperty() {
        return memberId;
    }
    public final int getMemberId() {
        return memberIdProperty().get();
    }
    public final void setMemberId(Integer memberId) {
        memberIdProperty().set(memberId);
    }

    public StringProperty requestDateProperty() { return requestDate; }
    public final String getRequestDate() { return requestDateProperty().get(); }
    public final void setRequestDate(String requestDate) {
        requestDateProperty().set(requestDate);
    }

    public StringProperty memberNameProperty() { return memberName; }
    public final String getMemberName() { return memberNameProperty().get(); }
    public final void setMemberName(String memberName) {
        memberNameProperty().set(memberName);
    }

    public StringProperty sectionNameProperty() { return sectionName; }
    public final String getSectionName() { return sectionNameProperty().get(); }
    public final void setSectionName(String sectionName) {
        sectionNameProperty().set(sectionName);
    }

    public IntegerProperty sectionIdProperty() {
        return sectionId;
    }
    public final int getSectionIdId() {
        return sectionIdProperty().get();
    }
    public final void setSectionIdId(Integer sectionId) {
        sectionIdProperty().set(sectionId);
    }

    public BooleanProperty verificationProperty() {
        return verification;
    }
    public final boolean getVerification() {
        return verificationProperty().get();
    }
    public final void setVerification(boolean verification) {
        verificationProperty().set(verification);
    }

    public Request(int id,String date,int memberId,int sectionId,String section_name,String member_name,boolean ver){
        setMemberId(memberId);
        setSectionName(section_name);
        setMemberName(member_name);
        setSectionIdId(id);
        setVerification(ver);
        setRequestDate(date);
        setRequestId(id);
        setGroup("none");
    }
}
