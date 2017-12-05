package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller implements Runnable {

    // CONSTANTS
    private static final String STATUS_FOUND = "Status:\tInput found";
    private static final String STATUS_STARTED = "Status:\tPrinting thread has started";
    private static final String STATUS_STOPED = "Status:\tPrinting thread has been stopped";
    private static int PRINT_SPEED = 30;
    private static int fontSize = 12;

    public static void setFontSize(int fontSize) {
        Controller.fontSize = fontSize;
    }

    public static int getPrintSpeed() {
        return PRINT_SPEED;
    }

    public static void setPrintSpeed(int printSpeed) { PRINT_SPEED = printSpeed; }


    /*
    --------------------------------------------------------------------------------------------
                        FXML STUFF
    --------------------------------------------------------------------------------------------
     */
    @FXML
    private Label time;
    @FXML
    private Label status;
    @FXML
    private TextField input;
    @FXML
    public TextArea textArea;
    @FXML
    private ProgressIndicator progress;

    /*
    Start Button implementation:
     */
    @FXML
    protected void onStartClicked() {
        if (progress.isVisible()) //if the progress indicator is visible...
            return;               //...do nothing
        if (!textArea.getText().equals(""))
            textArea.clear();
        isFound = false;
        Thread print = new Thread(this, "Printing thread");
        Thread measureTime = new Thread(new TimeMeasurement(), "Time measurement thread");
        print.start();
        measureTime.start();
        progress.setVisible(true);
        status.setText(STATUS_STARTED);
    }

    /*
    Stop Button implementation:
     */
    @FXML
    protected void onStopClicked() {
        if (!progress.isVisible() || status.getText().equals(STATUS_FOUND))
            return;
        isFound = true;
        sleep(PRINT_SPEED);
        progress.setVisible(false);
        status.setText(STATUS_STOPED);
    }

    /*
    Settings menue implementation:
     */
    public static Stage settingsStage = new Stage();
    @FXML
    protected void openSettings() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("desinged_settings.fxml"));
        Scene scene = new Scene(root);
        settingsStage.setScene(scene);
        settingsStage.setTitle("Settings");
        if(!settingsStage.getModality().equals(Modality.APPLICATION_MODAL))
            settingsStage.initModality(Modality.APPLICATION_MODAL);
            settingsStage.showAndWait();
        }

    /*
        --------------------------------------------------------------------------------------------
                        THREAD STUFF
    --------------------------------------------------------------------------------------------
     */

    private static RandomGenerator rnd = new RandomGenerator("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    private static boolean isFound = false;

    public static boolean isFinished() {
        return isFound;
    }

    @Override
    public void run() {

        /*
        PRINT AND SEARCH ALGORITHM, INEFFICIENT!
         */

        String randomChar;
        String word = input.getText().toUpperCase();
        int lengthOfWord = word.length();
        while (!isFound && !word.equals("")) {
            //before starting to print, set fontsize:
            textArea.setFont(new Font(fontSize));

            // the next line will get the time string from the time measurement thread and then print it to the GUI
            javafx.application.Platform.runLater( () -> time.setText(TimeMeasurement.getTime()));
            randomChar = rnd.generateChar();
            append(randomChar);
            sleep(PRINT_SPEED);
            if (randomChar.equals(String.valueOf(word.charAt(0)))) {
                for (int i = 0; i < lengthOfWord - 1; i++) {
                    append(rnd.generateChar());
                    sleep(PRINT_SPEED);
                }
                if (textArea.getText().contains(word)) {
                    isFound = true;
                }
            }
        }
        if (textArea.getText().contains(word)) {
            javafx.application.Platform.runLater(() -> status.setText(STATUS_FOUND));
            progress.setVisible(false);
        }

    }

    /*
    -------------------------------------------------------------------------------------------
                            private methods for cleaner code:
    -------------------------------------------------------------------------------------------
     */

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void append(String text) {
        javafx.application.Platform.runLater(() -> textArea.appendText(text));
    }

    public void changeFontSize(int size) {
        textArea.setFont(new Font(size));
    }
}