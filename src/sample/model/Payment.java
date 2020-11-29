package sample.model;

import javafx.beans.property.*;

public class Payment {

    private final IntegerProperty paymentId = new SimpleIntegerProperty(this, "paymentId");
    private final StringProperty paymentDate = new SimpleStringProperty(this, "paymentDate");
    private final DoubleProperty amount = new SimpleDoubleProperty(this, "amount");
    private final StringProperty pref_category = new SimpleStringProperty(this, "pref_category");
    private final IntegerProperty memberId = new SimpleIntegerProperty(this, "memberId");
    private final IntegerProperty groupId = new SimpleIntegerProperty(this, "groupId");
    private final StringProperty memberName = new SimpleStringProperty(this, "memberName");
    private final StringProperty sectionName = new SimpleStringProperty(this, "sectionName");

    public IntegerProperty paymentIdProperty() {
        return paymentId;
    }
    public final int getPaymentId() {
        return paymentIdProperty().get();
    }
    public final void setPaymentId(Integer paymentId) {
        paymentIdProperty().set(paymentId);
    }

    public IntegerProperty groupIdProperty() {
        return groupId;
    }
    public final int getGroupId() {
        return groupIdProperty().get();
    }
    public final void setGroupId(Integer groupId) {
        groupIdProperty().set(groupId);
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

    public StringProperty paymentDateProperty() { return paymentDate; }
    public final String getPaymentDate() { return paymentDateProperty().get(); }
    public final void setPaymentDate(String paymentDate) {
        paymentDateProperty().set(paymentDate);
    }

    public DoubleProperty amountProperty() {
        return amount;
    }
    public final double getAmount() {
        return amountProperty().get();
    }
    public final void setAmount(double amount) {
        amountProperty().set(amount);
    }

    public StringProperty pref_categoryProperty() { return pref_category; }
    public final String getPref_category() { return pref_categoryProperty().get(); }
    public final void setPref_category(String pref_category) {
        pref_categoryProperty().set(pref_category);
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

    public Payment(int id,String payDate,double amount,String pref_cat,int memberId,int groupId,String section_name,String member_name){
        setGroupId(groupId);
        setPaymentId(id);
        setPaymentDate(payDate);
        setAmount(amount);
        setPref_category(pref_cat);
        setMemberId(memberId);
        setSectionName(section_name);
        setMemberName(member_name);
    }
}
