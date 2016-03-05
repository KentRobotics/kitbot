package org.usfirst.frc.team2785.misc;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Recorder {

    /**
     * Records arrays of Doubles in tables and then saves them to a file.
     */
    private boolean enabled = false;
    private Map<String, ArrayList<Double>> data;
    private Map<String, ArrayList<Long>> timeStamps;
    private Map<String, Long> timeStampOffsets; //base time stamps, not written to state, used only for subtraction.
    
    public Recorder() {
        clear();
    }
    /**
     * Re-instantiates the underlying data structures with new instances.
     * In other words, clear the Recorder of previous data.
     */
    public void clear() {
        data = new HashMap<String, ArrayList<Double>>();
        timeStamps = new HashMap<String, ArrayList<Long>>();
        timeStampOffsets = new HashMap<String, Long>();
    }
    public void setEnabled(boolean yes) {
        enabled = yes;
    }
    public boolean isEnabled() {
        return enabled;
    }
    
    public boolean put(String tableName, double value) {
        if (enabled) {
            Date current = new Date();
            if (!data.containsKey(tableName)) {
                data.put(tableName, new ArrayList<Double>());
                timeStampOffsets.put(tableName, current.getTime());
                timeStamps.put(tableName, new ArrayList<Long>());
            }
            data.get(tableName).add(value);
            timeStamps.get(tableName).add(current.getTime() - timeStampOffsets.get(tableName));
        }
        return enabled;
    }
    public void write(String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(data);
            objOut.writeObject(timeStamps);
            objOut.close();
            fileOut.close();
            System.out.println("Wrote record file to "+filePath);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
