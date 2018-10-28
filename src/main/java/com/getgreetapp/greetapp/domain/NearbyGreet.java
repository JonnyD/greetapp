package com.getgreetapp.greetapp.domain;

public class NearbyGreet {
    private Greet greet;

    private Double distance;

    public NearbyGreet(Greet greet, Double distance) {
        this.greet = greet;
        this.distance = distance;
    }

    public Greet getGreet() {
        return greet;
    }

    public void setGreet(Greet greet) {
        this.greet = greet;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
