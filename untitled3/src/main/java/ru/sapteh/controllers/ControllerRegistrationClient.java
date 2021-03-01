package ru.sapteh.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import ru.sapteh.dao.DAO;
import ru.sapteh.entity.Client;
import ru.sapteh.entity.Gender;
import ru.sapteh.service.ClientDaoImpl;
import ru.sapteh.service.GenderDaoImpl;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ControllerRegistrationClient {
    private final SessionFactory factory;

    public ControllerRegistrationClient() {
        factory = new Configuration().configure().buildSessionFactory();
    }
    ObservableList<Gender> genders = FXCollections.observableArrayList();


    @FXML
    private DatePicker dateBirthday;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtPatronymic;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtDateReg;

    @FXML
    private TextField txtPhotoPath;

    @FXML
    private ComboBox<Gender> listGenders;

    @FXML
    private Label labelStatus;

    public void initialize() throws ParseException {
//        DAO<Gender, Integer> daoGender =  new GenderDaoImpl(factory);
//        genders.addAll(daoGender.findByAll().toArray(new Gender[0]));
        listGenders.setItems(genders);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        txtDateReg.setText(format.format(new Date()));

    }


    @FXML
    void buttonAddClient(ActionEvent event) throws ParseException {
        DAO<Client, Integer> daoClient = new ClientDaoImpl(factory);
        Client client = new Client();

        client.setFirstName(txtFirstName.getText());
        client.setLastName(txtLastName.getText());
        client.setPatronymic(txtPatronymic.getText());
        client.setEmail(txtEmail.getText());
        client.setPhone(txtPhone.getText());
        client.setPhotoPath(txtPhotoPath.getText());
        client.setGender(listGenders.getValue());

//        Date
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date birthday = format.parse(String.valueOf(dateBirthday.getValue()));
        client.setRegistrationDate(new Date());
        client.setBirthday(birthday);

        daoClient.create(client);

        labelStatus.setTextFill(Color.GREEN);
        labelStatus.setText("Пользователь добавлен:)");
    }


    @FXML
    public void buttonSelectPathToPhoto(){
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Выбор фотографии");
        File file = chooser.showOpenDialog(new Stage());
        if (file != null)
            txtPhotoPath.setText(file.getAbsolutePath());
    }
}
