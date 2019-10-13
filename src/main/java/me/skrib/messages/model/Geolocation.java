package me.skrib.messages.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Builder(builderMethodName = "location")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Geolocation {

    public static final String HEADER_VALUE = "x-user-geolocation";

    private double latitude;
    private double longitude;
    @Column(columnDefinition = "DOUBLE default '0.00'")
    private double altitude;

}
