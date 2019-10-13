package me.skrib.messages.service;

import me.skrib.messages.model.DistanceUnit;
import me.skrib.messages.model.Geolocation;
import org.springframework.stereotype.Service;

@Service
public class GeolocationService {

    /**
     * @param unit
     * @param userGeolocation
     * @param messageGeolocation
     * @return
     */
    public double distanceBetweenUserAndMessage(DistanceUnit unit, Geolocation userGeolocation,
                                                Geolocation messageGeolocation) {
        return this.distance(userGeolocation.getLatitude(), messageGeolocation.getLatitude(),
                             userGeolocation.getLongitude(),
                             messageGeolocation.getLongitude(), userGeolocation.getAltitude(),
                             messageGeolocation.getAltitude(), unit);
    }

    /**
     * Calculate distance between two points in latitude and longitude taking into account height difference.
     * If you are not interested in height difference pass 0.0.
     * Uses Haversine method as its base.
     * Uses user's DistanceUnit.
     *
     * @param lat1
     * @param lat2
     * @param lon1
     * @param lon2
     * @param height1
     * @param height2
     * @param unit
     * @return Distance in meters.
     */
    public double distance(double lat1, double lat2, double lon1, double lon2, double height1, double height2,
                           DistanceUnit unit) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(lonDistance / 2) *
                   Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * DistanceUnit.KILOMETER.getInMeters(); // convert to meters

        double height = height1 - height2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);
        distance = Math.sqrt(distance);

        return distance / unit.getInMeters();
    }

}
