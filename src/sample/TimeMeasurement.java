package sample;

import java.text.NumberFormat;

public class TimeMeasurement implements Runnable {

    private static int DIGITS = 3; //digits printed out, e.g: DIGITS = 3; -> 5.11s (thats 3 digits)
    private static String time = "Meassured Time:";
    private double seconds = 0.0;
    private int minutes = 0;

    public static String getTime() {
        return time;
    }

    @Override
    public void run() {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMaximumFractionDigits(DIGITS);
        numberFormat.setMinimumFractionDigits(DIGITS);
        while (!Controller.isFinished()) {
            time = "Meassured Time:\t" + minutes + " min. " + numberFormat.format(seconds) + "s.";
            try {
                Thread.sleep(Controller.getPrintSpeed());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds += (Controller.getPrintSpeed() / 1000.0);
            if (seconds > 60.0) {
                minutes++;
                seconds = 0.0;
            }
        }

    }
}
