package sample.model;

import javafx.beans.property.*;

public class Group {

        private final IntegerProperty groupId = new SimpleIntegerProperty(this, "groupId");
    private final IntegerProperty ageMin = new SimpleIntegerProperty(this, "ageMin");
    private final IntegerProperty ageMax = new SimpleIntegerProperty(this, "ageMax");
    private final IntegerProperty maxMemberNum = new SimpleIntegerProperty(this, "maxMemberNum");
    private final IntegerProperty managerId = new SimpleIntegerProperty(this, "managerId");
    private final IntegerProperty sectionId = new SimpleIntegerProperty(this, "sectionId");
    private final StringProperty managerName = new SimpleStringProperty(this, "managerName");
    private final StringProperty sectionName = new SimpleStringProperty(this, "sectionName");

        public IntegerProperty groupIdProperty() {
            return groupId;
        }
        public final int getGroupId() {
            return groupIdProperty().get();
        }
        public final void setGroupId(Integer groupId) {
            groupIdProperty().set(groupId);
        }


        public IntegerProperty sectionIdProperty() {
            return sectionId;
        }
        public final int getSectionId() {
            return sectionIdProperty().get();
        }
        public final void setSectionId(Integer sectionId) {
            sectionIdProperty().set(sectionId);
        }


    public IntegerProperty managerIdProperty() {
        return managerId;
    }
    public final int getManagerId() {
        return managerIdProperty().get();
    }
    public final void setManagerId(Integer managerId) {
        managerIdProperty().set(managerId);
    }

    public IntegerProperty maxMemberNumProperty() {
        return maxMemberNum;
    }
    public final int getMaxMemberNum() {
        return maxMemberNumProperty().get();
    }
    public final void setMaxMemberNum(Integer maxMemberNum) {
        maxMemberNumProperty().set(maxMemberNum);
    }

    public IntegerProperty ageMaxProperty() {
        return ageMax;
    }
    public final int getAgeMax() {
        return ageMaxProperty().get();
    }
    public final void setAgeMax(Integer ageMax) {
        ageMaxProperty().set(ageMax);
    }

    public IntegerProperty ageMinProperty() {
        return ageMin;
    }
    public final int getAgeMin() {
        return sectionIdProperty().get();
    }
    public final void setAgeMin(Integer ageMin) {
        sectionIdProperty().set(ageMin);
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

    public StringProperty sectionNameProperty() {
        return sectionName;
    }
    public final String getSectionName() {
        return sectionNameProperty().get();
    }
    public final void setSectionName(String sectionName) {
        sectionNameProperty().set(sectionName);
    }

        public Group(int groupId,int ageMin,int ageMax,int max_num,int managerId,int sectionId,String managerName,String sectionName) {
           setGroupId(groupId);
           setAgeMax(ageMax);
           setAgeMin(ageMin);
           setManagerId(managerId);
           setSectionId(sectionId);
           setMaxMemberNum(max_num);
           setManagerName(managerName);
           setSectionName(sectionName);
        }

    }
