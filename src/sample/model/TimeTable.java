package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class TimeTable  {
    private final StringProperty type = new SimpleStringProperty(this, "type");
    private final StringProperty day_of_week = new SimpleStringProperty(this, "day_of_week");
    private final IntegerProperty groupId = new SimpleIntegerProperty(this, "groupId");
    private final StringProperty group = new SimpleStringProperty(this, "group");
    private final StringProperty time = new SimpleStringProperty(this, "time");
    private final IntegerProperty duration = new SimpleIntegerProperty(this, "duration");
    private final IntegerProperty roomId = new SimpleIntegerProperty(this, "roomId");
    private final IntegerProperty room = new SimpleIntegerProperty(this, "room");


    public StringProperty typeProperty() {
        return type;
    }

    public final String getType() {
        return typeProperty().get();
    }

    public final void setType(String t) {
        typeProperty().set(t);
    }


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


    public StringProperty groupProperty() {
        return group;
    }

    public final String getGroup() {
        return groupProperty().get();
    }

    public final void setGroup(String group) {
        groupProperty().set(group);
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

    public final void setDuration(Integer d) {
       durationProperty().set(d);
    }

    public IntegerProperty roomIdProperty() {
        return roomId;
    }

    public final int getRoomId() {
        return roomIdProperty().get();
    }

    public final void setRoomId(Integer d) {
        roomIdProperty().set(d);
    }

    public IntegerProperty roomProperty() {
        return room;
    }

    public final int getRoom() {
        return roomProperty().get();
    }

    public final void setRoom(Integer d) {
        roomProperty().set(d);
    }

    public TimeTable(String type,String day,int group_id,String group,String time,int d, int room_id,int room_n) {
        setDay_of_week(day);
        setGroupId(group_id);
        setGroup(group);
        setTime(time);
        setDuration(d);
        setRoomId(room_id);
        setRoom(room_n);
        setType(type);

    }
}
