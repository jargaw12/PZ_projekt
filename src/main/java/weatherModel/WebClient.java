package weatherModel;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import configuration.Logger;
import events.WeatherEvent;
import events.listeners.WeatherListener;
import exceptions.LocationException;
import configuration.MyConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class WebClient {
    private ObjectMapper objectMapper = new ObjectMapper();
    MyConfiguration myConfiguration;
    private DefaultHttpClient httpClient = new DefaultHttpClient();
    private HttpGet getRequest;
    private HttpResponse resp;
    ArrayList<WeatherListener> weatherListeners;
    String name;
    String lang;

    public WebClient() {
        weatherListeners = new ArrayList<>();
    }

    public WeatherModel getWeatherModel(String name, String lang) throws IOException {
        WeatherModel weatherModel = null;
        myConfiguration = new MyConfiguration();
        myConfiguration.getValue("webApi");
        ResourceBundle resourceBundle=ResourceBundle.getBundle("resource", Locale.getDefault());
        String json = getJSON(myConfiguration.getValue("webApi") + name + "&lang=" + lang);
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        weatherModel = (WeatherModel) objectMapper.readValue(json, WeatherModel.class);

        try {
            sprawdzLokalizacje(weatherModel);
        } catch (LocationException e) {
            Logger.getInstance().logger.log(Level.WARNING,"Wprowadzono niepoprawną nazwę miasta",e);
            String namw = JOptionPane.showInputDialog(resourceBundle.getString("message.city") +" "+ myConfiguration.getValue("city") + "\n"+resourceBundle.getString("message.reenter"));
            myConfiguration.setValue("city", namw);
            weatherModel = this.getWeatherModel(myConfiguration.getValue("city"), lang);
        }

        if (severeType(weatherModel)!=""){
            fireSevereWather(severeType(weatherModel));
        }

        return weatherModel;
    }

    private void sprawdzLokalizacje(WeatherModel weatherModel) throws LocationException {
        if (weatherModel.getError() != null && weatherModel.getError().getCode() == 1006)
            throw new LocationException();
    }

    private String severeType (WeatherModel weatherModel){
        if (weatherModel.getCurrent().temp_c < -10)
            return  "cold";
        else if (weatherModel.getCurrent().temp_c > 25)
            return "heat";
        else if (weatherModel.getCurrent().wind_kph > 50)
            return  "wind";
        else if (weatherModel.getCurrent().precip_mm > 30)
            return "rain";
        else return "";
    }

    private String getJSON(String s) throws IOException {
        getRequest = new HttpGet(s);
        getRequest.addHeader("accept", "application/json");
        resp = httpClient.execute(getRequest);
        return EntityUtils.toString(resp.getEntity());
    }

    public void addWeatherListener(WeatherListener l) {
        weatherListeners.add(l);
    }

    public void removeWeatherListener(WeatherListener l) {
        weatherListeners.remove(l);
    }

    void fireSevereWather(String warning) {
        WeatherEvent weather = new WeatherEvent(this, warning);
        Iterator listeners = weatherListeners.iterator();
        while (listeners.hasNext()) {
            ((WeatherListener) listeners.next()).severeWeather(weather);
        }
    }


}
