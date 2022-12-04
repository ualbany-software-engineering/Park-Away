package com.parkway.service;

import com.parkway.model.DistanceMatrixResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.parkway.constants.Constants.*;

@Service
public class DistanceCalculatorService {

    @Value("${distance.matrix.api.url}")
    private String API_URL;
    @Value("${distance.matrix.api.key}")
    private String API_KEY;

    private RestTemplate template;

    public DistanceCalculatorService() {
        this.template = new RestTemplate();
    }

    public String getDistanceInMiles(double sourceLatitude, double sourceLongitude,
                                     double destLatitude, double destLongitude) {
        String URL = API_URL + ORIGINS + EQUALS + sourceLatitude + COMMA
                + sourceLongitude + AND + DESTINATIONS + EQUALS + destLatitude + COMMA
                + destLongitude + AND + UNITS + EQUALS + MILES + AND + KEY + EQUALS + API_KEY;
        try {
            DistanceMatrixResponse result = template.getForObject(URL, DistanceMatrixResponse.class);
            if (result.getStatus().equalsIgnoreCase("OK") && result.getRows()[0].getElements()[0].getStatus().equalsIgnoreCase("OK")) {
                String distance = result.getRows()[0].getElements()[0].getDistance().getText();
                return distance;
                //return Double.valueOf(distance.substring(0,distance.length()-3));
            } else {
                return distance(sourceLatitude, sourceLongitude, destLatitude, destLongitude, 'N') + " mi";
            }
        } catch (Exception e) {
            throw new RuntimeException("Distance Matrix API Service Failed");
        }
    }

    public String getDistanceInKilometers(double sourceLatitude, double sourceLongitude,
                                          double destLatitude, double destLongitude) {
        String URL = API_URL + ORIGINS + EQUALS + sourceLatitude + COMMA
                + sourceLongitude + AND + DESTINATIONS + EQUALS + destLatitude + COMMA
                + destLongitude + AND + UNITS + EQUALS + KILOMETERS + AND + KEY + EQUALS + API_KEY;
        try {
            DistanceMatrixResponse result = template.getForObject(URL, DistanceMatrixResponse.class);
            if (result.getStatus().equalsIgnoreCase("OK") && result.getRows()[0].getElements()[0].getStatus().equalsIgnoreCase("OK")) {
                String distance = result.getRows()[0].getElements()[0].getDistance().getText();
                return distance;
                //return Double.valueOf(distance.substring(0,distance.length()-3));
            } else {
                return distance(sourceLatitude, sourceLongitude, destLatitude, destLongitude, 'K') + " km";
            }
        } catch (Exception e) {
            throw new RuntimeException("Distance Matrix API Service Failed");
        }
    }

    private double distance(double lat1, double lon1, double lat2, double lon2, char unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == 'K') {
            dist = dist * 1.609344;
        } else if (unit == 'N') {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
}
