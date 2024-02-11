package me.crispyxyz;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class TimeUpdater {

    private JLabel timeLabel;

    public TimeUpdater(JLabel timeLabel) {
        this.timeLabel = timeLabel;
    }

    public void init() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Date now = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String fmtTime = sdf.format(now);

                timeLabel.setText(fmtTime);
            }
        };
        timer.scheduleAtFixedRate(task, new Date(), 500);
    }
}
