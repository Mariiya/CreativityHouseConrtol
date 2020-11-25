package sample.model;

import javafx.beans.property.*;

public class Section {
    private final IntegerProperty sectionId = new SimpleIntegerProperty(this, "sectionId");
    private final StringProperty name = new SimpleStringProperty(this, "name");
    private final StringProperty type = new SimpleStringProperty(this, "type");
    private final IntegerProperty lessonsNum = new SimpleIntegerProperty(this, "lessonsNum");
    private final FloatProperty price = new SimpleFloatProperty(this, "price");
    private final StringProperty description = new SimpleStringProperty(this, "description");

    public StringProperty nameProperty() {
        return name;
    }

    public final String getName() {
        return nameProperty().get();
    }

    public final void setName(String name) {
        nameProperty().set(name);
    }


    public IntegerProperty lessonsNumProperty() {
        return lessonsNum;
    }

    public final int getLessonsNum() {
        return lessonsNumProperty().get();
    }

    public final void setLessonsNum(Integer lessonsNum) {
        lessonsNumProperty().set(lessonsNum);
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


    public StringProperty typeProperty() {
        return type;
    }

    public final String getType() {
        return typeProperty().get();
    }

    public final void setType(String type) {
        typeProperty().set(type);
    }


    public FloatProperty priceProperty() {
        return price;
    }

    public final float getPrice() {
        return priceProperty().get();
    }

    public final void setPrice(float price) {
        priceProperty().set(price);
    }


    public StringProperty descriptionProperty() {
        return description;
    }

    public final String getDescription() {
        return descriptionProperty().get();
    }

    public final void setDescription(String description) {
        descriptionProperty().set(description);
    }


    public Section(int sectionId, String name,String type,int les_num,int price,String description) {
        setDescription(description);
        setLessonsNum(les_num);
        setSectionIdId(sectionId);
        setName(name);
        setType(type);
        setPrice(price);

    }


}
