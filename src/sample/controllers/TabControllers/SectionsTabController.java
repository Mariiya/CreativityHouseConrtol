package sample.controllers.TabControllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import sample.controllers.ScreenController;
import sample.model.Section;

import sample.service.SectionsService;
import java.sql.SQLException;


public class SectionsTabController {
    @FXML
    private Button section_add_btn,section_done_btn,section_delete_btn,section_edit_btn;

    @FXML
    private TextArea section_decription_input;

    @FXML
    private TableView<Section> section_table;

    @FXML
    private TextField section_name_input,section_type_input,section_price_input,section_num_of_lessons_input;

    @FXML
    private TableColumn<Section, String> section_type_col,sections_descr_col,section_name_col;

    @FXML
    private TableColumn<Section, Integer> section_row_num_col,section_l_nym_col;
    @FXML
    private TableColumn<Section, Float> section_price_col;

    private ScreenController screenController;
    private SectionsService service;


    public SectionsTabController() throws SQLException {
        service = new SectionsService();
        section_table = new TableView<Section>();
        section_table.setEditable(false);
    }


    void fillTable() {
        section_type_col.setCellValueFactory(
                new PropertyValueFactory<>("type"));
        section_l_nym_col.setCellValueFactory(
                new PropertyValueFactory<>("lessonsNum"));
        section_l_nym_col.setCellFactory(TextFieldTableCell.<Section,Integer>forTableColumn(new IntegerStringConverter()));
        section_name_col.setCellValueFactory(
                new PropertyValueFactory<>("name"));

       section_price_col.setCellValueFactory(
                new PropertyValueFactory<>("price"));
        section_row_num_col.setCellValueFactory(
                new PropertyValueFactory<>("sectionId"));

       section_type_col.setCellFactory(TextFieldTableCell.<Section>forTableColumn());
        section_price_col.setCellFactory(TextFieldTableCell.<Section,Float>forTableColumn(new FloatStringConverter()));
        sections_descr_col.setCellValueFactory(
                new PropertyValueFactory<>("description"));
        sections_descr_col.setCellFactory(TextFieldTableCell.<Section>forTableColumn());
        section_name_col.setCellFactory(TextFieldTableCell.<Section>forTableColumn());

        section_table.setItems(service.getAllSections());
    }

    @FXML
    public void initialize() {
        fillTable();

       section_delete_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                int index = section_table.getSelectionModel().getSelectedIndex();
                Section section = section_table.getItems().get(index);
               service.delete(section.getSectionIdId());
                section_table.getItems().remove(index);

            }
        });
        section_done_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                section_table.setEditable(false);
                section_done_btn.setDisable(true);

            }
        });
       section_edit_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
               section_table.setEditable(true);
                section_done_btn.setDisable(false);

            }
        });

      section_name_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Section, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Section, String> t) {
                        ((Section) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setName(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getSectionIdId();
                        service.updateName(id, t.getNewValue());
                    }
                }
        );


        sections_descr_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Section, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Section, String> t) {
                        ((Section) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getSectionIdId();
                        service.updateName(id, t.getNewValue());
                    }
                }
        );

        section_price_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Section, Float>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Section, Float> t) {
                        ((Section) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setPrice(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getSectionIdId();
                        service.updatePrice(id, t.getNewValue());
                    }
                }
        );

        section_l_nym_col.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Section,Integer>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Section, Integer> t) {
                        ((Section) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setLessonsNum(t.getNewValue());
                        int id = t.getTableView().getItems().get(
                                t.getTablePosition().getRow()).getSectionIdId();
                        service.updatePrice(id, t.getNewValue());
                    }
                }
        );

        section_add_btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if(section_name_input.getText().trim().isEmpty()
                || section_type_input.getText().isEmpty()
                        ||section_price_input.getText().trim().isEmpty()
                        ||section_num_of_lessons_input.getText().trim().isEmpty()
                        || section_decription_input.getText().trim().isEmpty()){
                    screenController.alert(Alert.AlertType.WARNING,"Can not creat Section","Enter all fields");
                }
                else {
                String name=section_name_input.getText().trim();
                String type=section_type_input.getText().trim();
                float price=Float.parseFloat(section_price_input.getText().trim());
                int less_num=Integer.parseInt(section_price_input.getText().trim());
                String descrition=section_decription_input.getText().trim();
                    service.create(name,type,less_num,price,descrition);
                }
            }
        });



    }

}

