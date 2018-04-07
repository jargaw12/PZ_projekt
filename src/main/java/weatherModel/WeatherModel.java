package weatherModel;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WeatherModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty
    private Location location;

    @JsonProperty
    private Current current;

    @JsonProperty
    private Error error;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location mLocation) {
        this.location = mLocation;
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current mCurrent) {
        this.current = mCurrent;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error mError) {
        this.error = mError;
    }

}
