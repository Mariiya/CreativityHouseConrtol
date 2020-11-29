package sample.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TimeTable {


    private final StringProperty mon = new SimpleStringProperty(this, "mon");
    private final StringProperty tue = new SimpleStringProperty(this, "thu");
    private final StringProperty wed = new SimpleStringProperty(this, "wed");
    private final StringProperty fri = new SimpleStringProperty(this, "fri");
    private final StringProperty thu = new SimpleStringProperty(this, "thu");
    private final StringProperty sat = new SimpleStringProperty(this, "sat");
    private final StringProperty sun = new SimpleStringProperty(this, "sun");
    private final StringProperty group = new SimpleStringProperty(this, "group");

    public StringProperty monProperty() {
        return mon;
    }
    public final String getMon() {
        return monProperty().get();
    }
    public final void setMon(String day) {
        monProperty().set(day);
    }

    public StringProperty tueProperty() {
        return tue;
    }
    public final String getTue() {
        return tueProperty().get();
    }
    public final void setTue(String tue) {
        tueProperty().set(tue);
    }

    public StringProperty wedProperty() {
        return wed;
    }
    public final String getWed() {
        return wedProperty().get();
    }
    public final void setWed(String wed) {
        wedProperty().set(wed);
    }

    public StringProperty friProperty() {
        return fri;
    }
    public final String getFri() {
        return friProperty().get();
    }
    public final void setFri(String fri) {
        friProperty().set(fri);
    }

    public StringProperty thuProperty() {
        return thu;
    }
    public final String getThu() {
        return thuProperty().get();
    }
    public final void setThu(String thu) {
        thuProperty().set(thu);
    }

    public StringProperty satProperty() {
        return sat;
    }
    public final String getSat() {
        return satProperty().get();
    }
    public final void setSat(String sat) {
        satProperty().set(sat);
    }

    public StringProperty sunProperty() {
        return sun;
    }
    public final String getSun() {
        return sunProperty().get();
    }
    public final void setSun(String sun) {
        sunProperty().set(sun);
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

    public TimeTable(String info, int day, String group) {
        if (day == 1) {
            setMon(info);
        }
        if (day == 2) {
            setTue(info);
        }
        if (day == 3) {
            setWed(info);
        }
        if (day == 4) {
            setThu(info);
        }
        if (day == 5) {
            setFri(info);
        }
        if (day == 6) {
            setSat(info);
        }
        if (day == 7) {
            setSun(info);
        }
        setGroup(group);

    }
}

