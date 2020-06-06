
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class DataEntryGUITest {

    @Test
    public void promptTextTest(){
        JFXPanel panel = new JFXPanel();
        TextField text = new TextField();
        String promptedText = DataEntryGUI.putPromptText(text,"Name");
        assertEquals("Name", promptedText);
    }

    @Test
    public void promptNumberTest(){
        JFXPanel panel = new JFXPanel();
        TextField numb = new TextField();
        String promptedText = DataEntryGUI.putPromptText(numb,"(###) ###-####");
        assertEquals("(###) ###-####", promptedText);
    }
    @Test
    public void invalidNameOne(){

        JFXPanel panel = new JFXPanel();
        boolean isName = DataEntryGUI.isValidName("Amta sulaiman");
        assertEquals(false, isName);
    }

    @Test
    public void invalidNameTwo() {
        JFXPanel panel = new JFXPanel();
        boolean isName = DataEntryGUI.isValidName("Amta SulaiMan");
        assertEquals(false, isName);
    }

    @Test
    public void validNameOne(){

        JFXPanel panel = new JFXPanel();
        boolean isName = DataEntryGUI.isValidName("Am Ta");
        assertEquals(true, isName);
    }

    @Test
    public void validNameTwo(){

        JFXPanel panel = new JFXPanel();
        boolean isName = DataEntryGUI.isValidName("Amta Salman");
        assertEquals(true, isName);
    }

    @Test
    public void invalidNumbOne(){

        JFXPanel panel = new JFXPanel();
        boolean isNumb = DataEntryGUI.isValidNumber("123-456-789");
        assertEquals(false, isNumb);
    }

    @Test
    public void invalidNumbTwo() {

        JFXPanel panel = new JFXPanel();
        boolean isNumb = DataEntryGUI.isValidNumber("(123)456-1232");
        assertEquals(false, isNumb);
    }

    @Test
    public void validNumbOne(){

        JFXPanel panel = new JFXPanel();
        boolean isNumb = DataEntryGUI.isValidNumber("(000) 000-0000");
        assertEquals(true, isNumb);
    }

    @Test
    public void validPhoneNumberTest2(){

        JFXPanel panel = new JFXPanel();
        boolean isNumb = DataEntryGUI.isValidNumber("(123) 892-0921");
        assertEquals(true, isNumb);
    }

    @Test
    public void button_isDisabled(){
        JFXPanel pane = new JFXPanel();
        Button click_me = new Button();
        TextField name1 = new DataEntryGUI.NameTextField();
        TextField name2 = new DataEntryGUI.NameTextField();
        TextField numb1 = new DataEntryGUI.NumberTextField();
        BooleanBinding bind_exp = Bindings.or(name1.textProperty().isEmpty(), name2.textProperty().isEmpty()).
                or(numb1.textProperty().isEmpty());
        click_me.disableProperty().bind(bind_exp);
        Boolean isOn = click_me.disableProperty().getValue();
        assertEquals(true, isOn);
        name1.setText("Am S");
        name2.setText("Amta Sal");
        numb1.setText("(111) 222-3333");
        isOn = click_me.disableProperty().getValue();
        assertEquals(false, isOn);


    }
 //   @Test
//    public void checkNamesTest(){
//        JFXPanel panel = new JFXPanel();
//        TextField name1 = new DataEntryGUI.NameTextField();
//        TextField name2 = new DataEntryGUI.NameTextField();
//        name1.setText("Am Ta");
//        name2.setText("   Am  sulaimansss");
//        ArrayList<DataEntryGUI.NameTextField> names = new ArrayList<>();
//        DataEntryGUI.checkNames(names);
//      //  assertEquals("-fx-text-fill: black", name1.getStyle().toString());
//        assertEquals("-fx-text-fill: red",name2.getStyle());
//
//
//    }

}