package myTimer;

import java.util.Scanner;

/**
 * timer
 */

public class countDownTimer {

    public static void main(String[] args) {
        countDownTimer myTimer = new countDownTimer(0);
        if (args.length > 0) {
            myTimer.setTime(args[0]);
        } else {
            Scanner x = new Scanner(System.in);
            System.out.println("Enter time to count down");
            System.out.print("> ");
            String input = x.nextLine();
            x.close();
            myTimer.setTime(input);
            input = null;
        }
        myTimer.countDown();
    }

    int beepCount = 10;
    int[] timerTime = { 0, 0, 0 };
    int time; // time in seconds

    public countDownTimer(int _time) {
        time = _time;
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * @param _input Enter time to set in either seconds or in H:M:S format
     */
    public void setTime(String _input) {

        try {
            time = Integer.parseInt(_input);
        } catch (Exception e) {
            // assume that user is trying to enter in HMS format, if invalid format then it
            // will crash so it doesnt matter
            HMStoS(_input);
        }

    }

    // index 0 = seconds, 1 = minutes, 2 = hours
    void StoHMS(int seconds) {
        // reset timerTime
        for (int i = 0; i < timerTime.length; i++) {
            timerTime[i] = 0;
        }
        // calculate minutes and seconds
        timerTime[1] = seconds / 60;
        timerTime[0] = seconds % 60;

        // calculate hours and minutes
        timerTime[2] = timerTime[1] / 60;
        timerTime[1] = timerTime[1] % 60;

    }

    void HMStoS(String hmsString) {
        String[] hmsSplit = hmsString.split(":");
        int unit;
        this.time = 0;
        if (hmsSplit.length > 0 && hmsSplit.length < 4) {
            for (int i = 0; i < hmsSplit.length; i++) {
                try {
                    unit = Integer.parseInt(hmsSplit[hmsSplit.length - 1 - i]);
                } catch (Exception e) {
                    System.err.println("Invalid integer entered");
                    time = 0;
                    break;
                }

                if (i < 2 && (unit > 60 || unit < 0)) {
                    throw new IllegalArgumentException("Minutes and seconds cannot be > 60");
                }

                this.time += unit * (int) Math.pow(60, i);
            }
            printTime();
        } else {
            System.err.println("Enter a valid time");
            for (int i = 0; i < 3; i++) {
                timerTime[i] = 0;
            }
        }
    }

    void printTime() {
        StoHMS(time);

        for (int i = 2; i > 0; i--) {
            System.out.print(timerTime[i] + ":");
        }
        System.out.print(timerTime[0]);

        // end line
        System.out.printf("\r\n");
    }

    public void countDown() {
        while (time > 0) {
            clearScreen();

            printTime();
            time -= 1;

            // wait 1 second
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

        }
        alarm();
    }

    public void alarm() {
        for (int i = 0; i < beepCount; ++i) {
            System.out.println("Beep : " + i);
            // Ring the bell again using the Toolkit
            java.awt.Toolkit.getDefaultToolkit().beep();
            try {
                Thread.sleep(1000); // introduce delay
            } catch (InterruptedException e) {
            }
        }

    }

}
