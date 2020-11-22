package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Lesson {
    private final StringProperty day_of_week = new SimpleStringProperty(this, "lessonsDayOfWeek");
    private final IntegerProperty groupId = new SimpleIntegerProperty(this, "groupId");
    private final StringProperty time = new SimpleStringProperty(this, "time");
    private final IntegerProperty duration = new SimpleIntegerProperty(this, "duration");
    private final IntegerProperty roomId = new SimpleIntegerProperty(this, "roomId");

    public StringProperty day_of_weekProperty() {
        return day_of_week;
    }

    public final String getDay_of_week() {
        return day_of_weekProperty().get();
    }

    public final void setDay_of_week(String day) {
        day_of_weekProperty().set(day);
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

    public StringProperty timeProperty() {
        return time;
    }

    public final String getTime() {
        return timeProperty().get();
    }

    public final void setTime(String time) {
        timeProperty().set(time);
    }

    public IntegerProperty durationProperty() {
        return duration;
    }

    public final int getDuration() {
        return durationProperty().get();
    }

    public final void setDuration(int duration) {
        durationProperty().set(duration);
    }


    public IntegerProperty roomIdProperty() {
        return roomId;
    }

    public final Integer getRoomId() {
        return roomIdProperty().get();
    }

    public final void setRoomId(int roomId) {
        roomIdProperty().set(roomId);
    }

    public Lesson(String day,int groupId,String time,int duration,int room_id) {
        setDay_of_week(day);
        setDuration(duration);
        setGroupId(groupId);
        setTime(time);
        setRoomId(room_id);
    }
}
