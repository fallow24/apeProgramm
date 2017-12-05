package sample;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Settings {

    @FXML
    private Slider slider;

    @FXML
    private CheckBox turboCheckBox;

    @FXML
    private CheckBox spaceCheckbox;

    @FXML
    private CheckBox umlautCheckbox;

    @FXML
    private CheckBox customCheckbox;

    @FXML
    private TextField customInput;

    @FXML
    private TextField fontSize;

    @FXML
    protected void onCancel() {
        Controller.settingsStage.close();
    }

    @FXML
    protected void onApply() {
        applyPerformance();
        applyAlphabet();
        applyFont(Integer.valueOf(fontSize.getText()));
    }

    @FXML
    protected void onReset() {
        resetAll();
    }

    @FXML
    protected void customClicked() {
        if(customCheckbox.isSelected()) {
            spaceCheckbox.setDisable(true);
            umlautCheckbox.setDisable(true);
        } else {
            spaceCheckbox.setDisable(false);
            umlautCheckbox.setDisable(false);
        }
    }

    /*
    ----------------------------------------------------------------------------------
                          Private setter methods (for cleaner code)
    ----------------------------------------------------------------------------------
     */

    private void applyPerformance() {
        if(turboCheckBox.isSelected()){
            Controller.setPrintSpeed(1);
        } else {
            Controller.setPrintSpeed((int)slider.getValue());
        }
    }

    private void applyAlphabet() {
        if(!customCheckbox.isSelected()) {
            RandomGenerator.setAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
            if (spaceCheckbox.isSelected()) {
                RandomGenerator.setAlphabet(RandomGenerator.getAlphabet().concat(" "));
            }
            if (umlautCheckbox.isSelected()) {
                RandomGenerator.setAlphabet(RandomGenerator.getAlphabet().concat("ÖÄÜ"));
            }
        } else {
            RandomGenerator.setAlphabet(customInput.getText().toUpperCase());
        }
    }

    private void applyFont(int fontSize) {
        Controller.setFontSize(fontSize);
    }

    private void resetAll() {
        RandomGenerator.setAlphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        turboCheckBox.setSelected(false);
        umlautCheckbox.setDisable(false);
        umlautCheckbox.setSelected(false);
        spaceCheckbox.setSelected(false);
        spaceCheckbox.setSelected(false);
        customCheckbox.setSelected(false);
        customCheckbox.setSelected(false);
        slider.setValue(30.0);
        Controller.setPrintSpeed(30);
    }

}
