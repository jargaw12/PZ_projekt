package configuration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyConfiguration {
    private Map<String, String> settings;
    private Properties tempproperties;

    public MyConfiguration() {
        tempproperties=new Properties();
        init();
    }

    public String getValue(String key) {
        return settings.get(key);
    }

    public void setValue(String key, String value) {
        settings.put(key, value);
        store(key, value);
    }

    public void init(){
        try (InputStream propertiesStream = getClass().getClassLoader().getResourceAsStream("defConfig.properties")) {
            //defproperties.load(propertiesStream);
            InputStream propertiesStream2 = getClass().getClassLoader().getResourceAsStream("tempConfig.properties");
            tempproperties=new Properties();
            tempproperties.load(propertiesStream);
            tempproperties.load(propertiesStream2);
            settings = new HashMap<>((Map) tempproperties);
            propertiesStream.close();
            propertiesStream2.close();
        } catch (Exception ex) {
        }
    }

    public void store(String key,String value) {
        Properties properties = new Properties();

        properties.put(key,value);
        try {
            FileOutputStream outputStream = new FileOutputStream(getClass().getClassLoader().getResource("tempConfig.properties").getPath().replace("%20", " "));
            properties.store(outputStream, null);
            outputStream.close();
        } catch (IOException ex) {
// Handle an exception
        }
    }

    public void removeKey(String key){
        try {
            File myFile = new File(getClass().getClassLoader().getResource("tempConfig.properties").getPath().replace("%20", " "));
            Properties properties = new Properties();
            properties.load(new FileInputStream(myFile));
            properties.remove(key);
            OutputStream out = new FileOutputStream(myFile);
            properties.store(out, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Properties getTempproperties() {
        return tempproperties;
    }
}
