package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Event {
    private final IntegerProperty eventId = new SimpleIntegerProperty(this, "eventId");
    private final StringProperty eventName = new SimpleStringProperty(this, "eventName");
    private final StringProperty time = new SimpleStringProperty(this, "time");
    private final StringProperty eventAddr = new SimpleStringProperty(this, "eventAddr");
    private final StringProperty eventDate= new SimpleStringProperty(this, "eventDate");

    public StringProperty eventNameProperty() { return eventName; }
    public final String getEventName() {return eventNameProperty().get(); }
    public final void setEventName(String eventName) { eventNameProperty().set(eventName); }


    public StringProperty eventAddrProperty() { return eventAddr; }
    public final String getEventAddr() {return eventAddrProperty().get(); }
    public final void setevEntAddr(String eventAddr) { eventAddrProperty().set(eventAddr); }

    public StringProperty eventDateProperty() { return eventDate; }
    public final String getEventDate() {return eventDateProperty().get(); }
    public final void setEventDate(String eventDate) { eventDateProperty().set(eventDate); }


    public StringProperty timeProperty() { return time; }
    public final String getTime() { return timeProperty().get(); }
    public final void setTime(String time) { timeProperty().set(time);}

    public IntegerProperty eventIdProperty() { return eventId ; }
    public final int getEventId () { return eventIdProperty().get(); }
    public final void setEventId (Integer eventId ) { eventIdProperty().set(eventId ); }

    public Event(int id,String name, String address, String date, String time) {
        setEventId(id);
        setEventName(name);
        setevEntAddr(address);
        setEventDate(date);
        setTime(time);
    }
}
