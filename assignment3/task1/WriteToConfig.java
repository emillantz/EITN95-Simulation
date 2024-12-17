
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONTokener;
import java.util.HashMap;
import java.util.Locale;
import java.io.FileNotFoundException;
import org.json.JSONArray;

import org.json.JSONObject;


public class WriteToConfig {
    String file;
    JSONObject jsonObject;
    JSONArray sensors;

    public WriteToConfig(String file) {
        this.file = file;
        openFile();
    }

    private void openFile() {
        try {
            JSONTokener token = new JSONTokener(new FileReader(file));
            jsonObject = new JSONObject(token);
            jsonObject.clear();
            jsonObject.put("sensors", new JSONArray());
            sensors = jsonObject.getJSONArray("sensors");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void configUpdate(Sensor s) {

        JSONObject jsonS = toJSON(s);

        sensors.put(jsonS);
        configWrite();
    }

    private void configWrite() {
        try {
            FileWriter fw = new FileWriter(file);
            fw.write(jsonObject.toString());

            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JSONObject toJSON(Sensor s) {
        HashMap<String, Object> sensorObject = new HashMap<String, Object>();


        sensorObject.put("x", Double.parseDouble(String.format(Locale.ENGLISH, "%,.3f", s.x)));
        sensorObject.put("y", Double.parseDouble(String.format(Locale.ENGLISH, "%,.3f", s.y)));


        JSONObject jsonSensor = new JSONObject(sensorObject);

        return jsonSensor;
    }

}
