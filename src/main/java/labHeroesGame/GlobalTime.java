package labHeroesGame;

import java.io.Serializable;

public class GlobalTime extends Thread implements Serializable {
    private static int hours;
    private static int minutes;

    public GlobalTime(){
        setDaemon(true);
        hours = 6;
        minutes = 0;
    }

    public static synchronized int getHours() {
        return hours;
    }

    public static synchronized int getMinutes(){
        return minutes;
    }

    public static String getTime() {
        return getHours() + ":" + ((getMinutes()/10 == 0)?("0"+getMinutes()):(getMinutes()));
    }

    public void run() {
        while(true) {
            synchronized (this) {
                try {
                    this.wait(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                minutes += 10;
                if(minutes == 60) {
                    minutes = 0;
                    hours+=1;
                    if(hours == 24) {
                        hours = 0;
                    }
                }
            }
        }
    }
}
