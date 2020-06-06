
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * DataEntryGui Class
 * CSE-216 HOMEWORK 5 DUE AT 5/12/2019
 * BY Amta Sulaiman 111984510
 */

public class DataEntryGUI extends Application {

    ArrayList<NameTextField> names;
    ArrayList<NumberTextField> numbs;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        NameTextField text_1 = new NameTextField();
        NameTextField text_2 = new NameTextField();
        NameTextField text_3 = new NameTextField();
        NumberTextField numb_1 = new NumberTextField();
        NumberTextField numb_2 = new NumberTextField();
        NumberTextField numb_3 = new NumberTextField();
        names = new ArrayList<>();
        numbs = new ArrayList<>();

        names.add(text_1);
        names.add(text_2);
        names.add(text_3);
        numbs.add(numb_1);
        numbs.add(numb_2);
        numbs.add(numb_3);

        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));

        for (NameTextField nameField : names){
            putPromptText(nameField,"Name");
        }
        for (NumberTextField numbField : numbs){
            putPromptText(numbField,"(###) ###-####");
        }

        GridPane.setConstraints(text_1, 1, 0);
        GridPane.setConstraints(text_2, 1, 1);
        GridPane.setConstraints(text_3, 1, 2);
        GridPane.setConstraints(numb_1, 2, 0);
        GridPane.setConstraints(numb_2, 2, 1);
        GridPane.setConstraints(numb_3, 2, 2);

        Button click_me = new Button();
        BooleanBinding bind_exp = Bindings.or(text_1.textProperty().isEmpty(), text_2.textProperty().isEmpty()).
                or(text_3.textProperty().isEmpty()).or(numb_1.textProperty().isEmpty()).or(numb_2.textProperty().isEmpty()).
                or(numb_3.textProperty().isEmpty());
        click_me.disableProperty().bind(bind_exp);
        click_me.setText("Create Profiles");

        click_me.setPrefHeight(30);
        click_me.setPrefWidth(100);

        checkNames(names);
        checkNumb(numbs);

        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if (AreValidData(names, numbs)) {
                    for (NameTextField nameField : names) {
                        nameField.setEditable(false);
                    }
                    for (NumberTextField numbField : numbs) {
                        numbField.setEditable(false);
                    }
                    Alert alert = new Alert(Alert.AlertType.NONE, "The profiles have been saved and added to the" +
                            " database.", ButtonType.CLOSE);
                    alert.setTitle("SUCCESSFULLY ENTERED DATA");
                    alert.setHeight(300);
                    alert.setWidth(300);
                    alert.showAndWait();
                } else {

                    Alert alert = new Alert(Alert.AlertType.NONE, "INVALID INPUT: you have attempted to provide" +
                            " one or more invalid input(s). Please correct the information displayed in red and retry",
                            ButtonType.CLOSE);
                    alert.setTitle("INVALID INPUT ERROR");
                    alert.setHeight(300);
                    alert.setWidth(300);
                    alert.showAndWait();
                }
            }
        };

        // when button is clicked!
        click_me.setOnAction(event);

        root.getChildren().addAll(text_1, text_2, text_3, numb_1, numb_2, numb_3);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.add(click_me,0,0);
        root.add(grid,0,3,4,1);
        primaryStage.setTitle("Data Entry GUI");
        primaryStage.setScene(new Scene(root, 550, 325));
        primaryStage.show();
    }

    public static void checkNames(ArrayList<NameTextField> names){
        for (NameTextField nameField : names) {
            nameField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!nameField.isFocused()) {
                    if (!nameField.getText().equals("") && !isValidName(nameField.getText())) {
                        nameField.setStyle("-fx-text-fill: red");
                    }
                } else
                    nameField.setStyle("-fx-text-fill: black");

            });
        }
    }
    public static void checkNumb(ArrayList<NumberTextField> numbs){
        for (NumberTextField numbField : numbs) {
            numbField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!numbField.isFocused()) {
                    if (!numbField.getText().equals("") && !isValidNumber(numbField.getText())) {
                        numbField.setStyle("-fx-text-fill: red");
                    }
                } else
                    numbField.setStyle("-fx-text-fill: black");
            });

        }
    }

    public static String putPromptText(TextField field, String str){
        field.setPromptText(str);
        return field.getPromptText();
    }

    public static boolean isValidName(String name) {
        if (name.matches("^[a-zA-Z ]+$") && name.length() <= 20) {
            String sub[] = name.split(" ");
            if (sub.length == 2) {
                if (Character.isUpperCase(sub[0].charAt(0)) && sub[0].substring(1).matches("^[a-z]+$")
                        && Character.isUpperCase(sub[1].charAt(0)) && sub[1].substring(1).matches("^[a-z]+$"))
                    return true;
            }
        }
        return false;
    }

    public static boolean isValidNumber(String numb) {
        return numb.length() == 14 && numb.charAt(0) == '(' && numb.charAt(4) == ')' && numb.charAt(5) == ' ' &&
                numb.charAt(9) == '-' && isInt(numb.substring(1, 4)) && isInt(numb.substring(6, 9)) &&
                isInt(numb.substring(10, 14));
    }

    private static boolean isInt(String numb) {
        for (char ch : numb.toCharArray()) {
            if (ch < '0' || ch > '9')
                return false;
        }
        return true;
    }

    public static boolean AreValidData(ArrayList<NameTextField> names, ArrayList<NumberTextField> numbs) {
        int count = 0;
        for (NameTextField nameField : names) {
            if (nameField.getStyle().compareToIgnoreCase("-fx-text-fill: red") != 0) {
                count++;
            }
        }
        for (NumberTextField numbField : numbs) {
            if (numbField.getStyle().compareToIgnoreCase("-fx-text-fill: red") != 0) {
                count++;
            }
        }
        return (count == 6);
    }

    public static class NameTextField extends TextField {
        TextField textField;

        public NameTextField() {
            textField = new TextField();
        }
    }

    public static class NumberTextField extends TextField {
        TextField numbField;

        public NumberTextField() {
            numbField = new TextField();
        }
    }
}

