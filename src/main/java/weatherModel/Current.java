package weatherModel;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Current implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private int last_updated_epoch;

    @JsonProperty
    private String last_updated;

    @JsonProperty
    public double temp_c;

    @JsonProperty
    private double temp_f;

    @JsonProperty
    private double wind_mph;

    @JsonProperty
    public double wind_kph;

    @JsonProperty
    private int wind_degree;

    @JsonProperty
    private String wind_dir;

    @JsonProperty
    public double pressure_mb;

    @JsonProperty
    private double pressure_in;

    @JsonProperty
    public double precip_mm;

    @JsonProperty
    private double precip_in;

    @JsonProperty
    private int humidity;

    @JsonProperty
    private int cloud;

    @JsonProperty
    private double feelslike_c;

    @JsonProperty
    private double feelslike_f;

    @JsonProperty
    private int is_day;

    @JsonProperty
    private int vis_km;

    @JsonProperty
    private int vis_miles;

    public int getVis_km() {
        return vis_km;
    }

    public void setVis_km(int vis_km) {
        this.vis_km = vis_km;
    }

    public int getVis_miles() {
        return vis_miles;
    }

    public void setVis_miles(int vis_miles) {
        this.vis_miles = vis_miles;
    }

    public int getIs_day() {
        return is_day;
    }

    public void setIs_day(int is_day) {
        this.is_day = is_day;
    }

    private Condition mCondition = new Condition();

    public int getLastUpdateEpoch() {
        return last_updated_epoch;
    }

    public void setLastUpdateEpoch(int mLastUpdateEpoch) {
        this.last_updated_epoch = mLastUpdateEpoch;
    }

    public String getLastUpdated() {
        return last_updated;
    }

    public void setLastUpdated(String mLastUpdated) {
        this.last_updated = mLastUpdated;
    }

    public double getTempC() {
        return temp_c;
    }

    public void setTempC(Double mTempC) {
        this.temp_c = mTempC;
    }

    public double getTempF() {
        return temp_f;
    }

    public void setTempF(Double mTempF) {
        this.temp_f = mTempF;
    }

    public Condition getCondition() {
        return mCondition;
    }

    public void setCondition(Condition mCondition) {
        this.mCondition = mCondition;
    }

    public double getWindMph() {
        return wind_mph;
    }

    public void setWindMph(double mWindMph) {
        this.wind_mph = mWindMph;
    }

    public double getWindKph() {
        return wind_kph;
    }

    public void setWindKph(double mWindKph) {
        this.wind_kph = mWindKph;
    }

    public int getWindDegree() {
        return wind_degree;
    }

    public void setWindDegree(int mWindDegree) {
        this.wind_degree = mWindDegree;
    }

    public String getWindDir() {
        return wind_dir;
    }

    public void setWindDir(String mWindDir) {
        this.wind_dir = mWindDir;
    }

    public double getPressureMb() {
        return pressure_mb;
    }

    public void setPressureMb(double mPressureMb) {
        this.pressure_mb = mPressureMb;
    }

    public double getPressureIn() {
        return pressure_in;
    }

    public void setPressureIn(double mPressureIn) {
        this.pressure_in = mPressureIn;
    }

    public double getPrecipMm() {
        return precip_mm;
    }

    public void setPrecipMm(double mPrecipMm) {
        this.precip_mm = mPrecipMm;
    }

    public double getPrecipIn() {
        return precip_in;
    }

    public void setPrecipIn(double mPrecipIn) {
        this.precip_in = mPrecipIn;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int mHumidity) {
        this.humidity = mHumidity;
    }

    public int getCloud() {
        return cloud;
    }

    public void setCloud(int mCloud) {
        this.cloud = mCloud;
    }

    public double getFeelslikeC() {
        return feelslike_c;
    }

    public void setFeelslikeC(double mFeelslikeC) {
        this.feelslike_c = mFeelslikeC;
    }

    public double getFeelslikeF() {
        return feelslike_f;
    }

    public void setFeelslikeF(double mFeelslikeF) {
        this.feelslike_f = mFeelslikeF;
    }

}
