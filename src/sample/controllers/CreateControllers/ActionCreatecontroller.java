package sample.controllers.CreateControllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.controllers.main.ScreenController;
import sample.model.Event;
import sample.model.Group;
import sample.service.*;

import java.util.ArrayList;
import java.util.List;


import static sample.controllers.main.AuthorizationController.activeUser;

public class ActionCreatecontroller {
    @FXML
    private Button add_btn;
    @FXML
    private ChoiceBox<String> group_id, event_id;
    @FXML
    private TextField title_input;

    ScreenController screenController;
    ActionService service;
    GroupService groupService;
    EventService eventService;
    ObservableList<Group> groups;
    List<String> groupsNames = new ArrayList<>();
    ObservableList<Event> events;
    List<String> eventsNames = new ArrayList<>();

    public ActionCreatecontroller() {
        screenController = new ScreenController();
        groupService = new GroupService();
        eventService = new EventService();
        service = new ActionService();
        groups = groupService.getGroupsListByManagerId(activeUser.getUserId());
        for (int i = 0; i < groups.size(); i++) {
            groupsNames.add(groups.get(i).getSectionName());
        }
        events = eventService.getEventList();
        for (int i = 0; i < events.size(); i++) {
            eventsNames.add(events.get(i).getEventName());
        }
    }


    public static void addTextLimiter(final TextField tf, final int maxLength) {
        tf.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
                if (tf.getText().length() > maxLength) {
                    String s = tf.getText().substring(0, maxLength);
                    tf.setText(s);
                }
            }
        });
    }

    @FXML
    public void initialize() {
        group_id.setItems(FXCollections.observableArrayList(groupsNames));
        event_id.setItems(FXCollections.observableArrayList(eventsNames));
        addTextLimiter(title_input, 50);

        add_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String title = title_input.getText().trim();
                int indexGroup = -1;
                indexGroup = group_id.getSelectionModel().getSelectedIndex();
                int indexEvent = -1;
                indexEvent = event_id.getSelectionModel().getSelectedIndex();
                if (indexEvent !=-1 && (indexGroup !=-1)) {
                    if (title.isEmpty()) {
                        screenController.alert(Alert.AlertType.WARNING, "title", "Enter action title");
                    } else {
                        if (service.create(title,  events.get(indexEvent).getEventId(),groups.get(indexGroup).getGroupId())!=-1) {
                            ScreenController.setNewAction(activeUser.getId(), "Выступление добавлено " + title + " " + group_id.getValue());
                            title_input.clear();
                        }
                    }
                } else {
                    screenController.alert(Alert.AlertType.WARNING, "error", "Select group and event");
                }
            }
        });
    }


}