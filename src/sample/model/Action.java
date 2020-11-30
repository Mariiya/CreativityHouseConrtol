package sample.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Action {
    private final IntegerProperty eventId = new SimpleIntegerProperty(this, "eventId");
    private final IntegerProperty number = new SimpleIntegerProperty(this, "number");
    private final IntegerProperty groupId = new SimpleIntegerProperty(this, "groupId");
    private final StringProperty eventName = new SimpleStringProperty(this, "eventName");
    private final StringProperty managerName = new SimpleStringProperty(this, "managerName");
    private final StringProperty groupName= new SimpleStringProperty(this, "groupName");
    private final StringProperty actionName = new SimpleStringProperty(this, "actionName");

    public StringProperty eventNameProperty() { return eventName; }
    public final String getEventName() {return eventNameProperty().get(); }
    public final void setEventName(String eventName) { eventNameProperty().set(eventName); }

    public StringProperty actionNameProperty() { return actionName; }
    public final String getactioneName() {return actionNameProperty().get(); }
    public final void setActionName(String actionName) { actionNameProperty().set(actionName); }


    public IntegerProperty numberProperty() { return number; }
    public final int getNumber () { return numberProperty().get(); }
    public final void setNumber(Integer number) { numberProperty().set(number ); }


    public IntegerProperty eventIdProperty() { return eventId ; }
    public final int getEventId () { return eventIdProperty().get(); }
    public final void setEventId (Integer eventId ) { eventIdProperty().set(eventId ); }

    public IntegerProperty groupIdProperty() {
        return groupId;
    }
    public final int getGroupId() {
        return groupIdProperty().get();
    }
    public final void setGroupId(Integer groupId) {
        groupIdProperty().set(groupId);
    }

    public StringProperty managerNameProperty() {
        return managerName;
    }
    public final String getManagerName() {
        return managerNameProperty().get();
    }
    public final void setManagerName(String managerName) {
        managerNameProperty().set(managerName);
    }


    public StringProperty groupNameProperty() {
        return groupName;
    }
    public final String getGroupName() {
        return groupNameProperty().get();
    }
    public final void setGroupName(String groupName) {
        groupNameProperty().set(groupName);
    }

    public Action(int eventId, int groupId,String groupName,String managerName,String title,String eventName, int number) {
        setActionName(title);
        setEventId(eventId);
        setEventName(eventName);
        setGroupId(groupId);
        setManagerName(managerName);
        setNumber(number);
        setGroupName(groupName);
    }
}

