package weatherModel;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    public String name;

    @JsonProperty
    private String region;

    @JsonProperty
    private String country;

    @JsonProperty
    private String tz_id;

    @JsonProperty
    private String localtime;

    @JsonProperty
    private double lat;

    @JsonProperty
    private double lon;

    @JsonProperty
    private int localtime_epoch;

    public String getName() {
        return name;
    }

    public void setName(String mName) {
        this.name = mName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String mRegion) {
        this.region = mRegion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String mCountry) {
        this.country = mCountry;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double mLat) {
        this.lat = mLat;
    }

    public double getLong() {
        return lon;
    }

    public void setLong(double mLong) {
        this.lon = mLong;
    }

    public String getTzId() {
        return tz_id;
    }

    public void setTzId(String mTz_id) {
        this.tz_id = mTz_id;
    }

    public int getLocaltimeEpoch() {
        return localtime_epoch;
    }

    public void setLocaltimeEpoch(int mLocaltimeEpoch) {
        this.localtime_epoch = mLocaltimeEpoch;
    }

    public String getLocaltime() {
        return localtime;
    }

    public void setLocaltimeEpoch(String mLocaltime) {
        this.localtime = mLocaltime;
    }

}
