package com.getgreetapp.greetapp.domain;

public class NearbyGang {
    private Gang gang;

    private Double distance;

    public NearbyGang(Gang gang, Double distance) {
        this.gang = gang;
        this.distance = distance;
    }

    public Gang getGang() {
        return gang;
    }

    public void setGang(Gang gang) {
        this.gang = gang;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
