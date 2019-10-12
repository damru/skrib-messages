package me.skrib.messages.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Geolocation {

    public static final String HEADER_VALUE = "x-user-geolocation";

    private double latitude;
    private double longitude;
    private double altitude;

}
