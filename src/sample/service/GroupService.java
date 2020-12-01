package sample.service;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.dao.GroupDao;
import sample.dao.SectionsDao;
import sample.model.Group;
import java.sql.SQLException;

import java.util.HashMap;



public class GroupService {
    private GroupDao groupDao;
    private SectionsDao sectionDao;
    private ObservableList<Group> allGroups;

    public GroupService(){
        groupDao = new GroupDao();
        sectionDao=new SectionsDao();
        allGroups = FXCollections.observableArrayList(groupDao.getGroupsList());
    }
    public HashMap<String,Integer> getAllSections(){
       return sectionDao.getAllSectionsNames();
    }

    public void delete(Integer id) {
        try {
            groupDao.delete(id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public int create(int age_min,int age_max,int max_memb,int manager_id,int section_id) {
        try {
           groupDao.create(age_min,age_max,max_memb,manager_id,section_id);
           return 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }


    public ObservableList<Group> getAllGroups() {
        return allGroups;
    }

    public void updateMaxAge(int id, int newAge)  {
        try {
            groupDao.updateMaxAge(id,newAge);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public void updateMinAge(int id, int newAge)  {
        try {
            groupDao.updateMinAge(id,newAge);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public int updateMaxMemberNum(int id, int maxMemberNum)  {
        try {
           return groupDao.updateMaxMemberNum(id,maxMemberNum);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

   public int getLastAddedGroup(){
        try {
            return groupDao.getLastAddedgroup();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public ObservableList<Group>  getGroupsListByManagerId(int manager_id) {
        return  FXCollections.observableArrayList(groupDao.getGroupsListByManagerId(manager_id));
    }
}
