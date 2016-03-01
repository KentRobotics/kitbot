package org.usfirst.frc.team2785.misc;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Recorder {

    /**
     * Records arrays of Doubles in tables and then saves them to a file.
     */
    private boolean enabled = false;
    private Map<String, ArrayList<Double>> data;
    
    public Recorder() {
        clear();
    }
    public void clear() {
        data = new HashMap<String, ArrayList<Double>>();
    }
    public void setEnabled(boolean yes) {
        enabled = yes;
    }
    public boolean isEnabled() {
        return enabled;
    }
    
    public void put(String tableName, Double value) {
        if (enabled) {
            if (!data.containsKey(tableName)) {
                data.put(tableName, new ArrayList<Double>());
            }
            data.get(tableName).add(value);
        }
    }
    public void write(String filePath) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objOut = new ObjectOutputStream(fileOut);
            objOut.writeObject(data);
            objOut.close();
            fileOut.close();
            System.out.println("Wrote record file to "+filePath);
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}
