package me.skrib.messages.model;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Created by damien on 11/01/2017.
 */
public enum DistanceUnit {
    KILOMETER("KM","kilometer",1000)
    , MILE("MI","mile", 1609.34)
    , METER("M", "meters", 1)
    , INCH("I", "inches", 0.0254)
    , CENTIMETER("CM", "cm", 0.01)
    , MILLIMETER("MM", "mm", 0.001);

    private String dbValue;
    private String jsonValue;
    private final double inMeters;

    DistanceUnit(String dbValue, String jsonValue, double inMeters) {
        this.dbValue = dbValue;
        this.jsonValue= jsonValue;
        this.inMeters = inMeters;
    }

    public String getDbValue() {
        return null;
    }

    @JsonValue
    public String getJsonValue() {
        return jsonValue;
    }

    public double getInMeters() {
        return inMeters;
    }

    public static DistanceUnit fromDbValue(String dbValue) {
        switch (dbValue) {
            case "KM" :
                return KILOMETER;
            case "M" :
                return MILE;
        }

        return null;
    }

}
