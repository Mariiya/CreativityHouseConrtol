package sample.model;

import javafx.beans.property.*;

public class Register {

    private final StringProperty date = new SimpleStringProperty(this, "date");
    private final IntegerProperty memberId = new SimpleIntegerProperty(this, "memberId");
    private final IntegerProperty groupId = new SimpleIntegerProperty(this, "groupId");
    private final StringProperty memberName = new SimpleStringProperty(this, "memberName");
    private final StringProperty sectionName = new SimpleStringProperty(this, "sectionName");
    private final StringProperty day_of_week = new SimpleStringProperty(this, "day_of_week");
    private final BooleanProperty onLesson = new SimpleBooleanProperty(this, "onLesson");

    public StringProperty day_of_weekProperty() {
        return day_of_week;
    }

    public final String getDay_of_week() {
        return day_of_weekProperty().get();
    }

    public final void setDay_of_week(String day) {
        day_of_weekProperty().set(day);
    }

    public BooleanProperty onLessonProperty() {
        return onLesson;
    }

    public final boolean getOnLesson() {
        return onLessonProperty().get();
    }

    public final void setOnLesson(boolean onLesson) {
        onLessonProperty().set(onLesson);
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

    public StringProperty dateProperty() {
        return date;
    }

    public final String getDate() {
        return dateProperty().get();
    }

    public final void setDate(String paymentDate) {
        dateProperty().set(paymentDate);
    }


    public StringProperty memberNameProperty() {
        return memberName;
    }

    public final String getMemberName() {
        return memberNameProperty().get();
    }

    public final void setMemberName(String memberName) {
        memberNameProperty().set(memberName);
    }

    public StringProperty sectionNameProperty() {
        return sectionName;
    }

    public final String getSectionName() {
        return sectionNameProperty().get();
    }

    public final void setSectionName(String sectionName) {
        sectionNameProperty().set(sectionName);
    }

    public Register(String date, int memberId, int groupId, String section_name, String member_name, String weekday, boolean onLesson) {
        setDate(date);
        setMemberId(memberId);
        setGroupId(groupId);
        setSectionName(section_name);
        setMemberName(member_name);
        setDay_of_week(weekday);
        setOnLesson(onLesson);
    }
}

