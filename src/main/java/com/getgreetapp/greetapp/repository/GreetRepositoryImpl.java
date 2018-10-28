package com.getgreetapp.greetapp.repository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class GreetRepositoryImpl {
    @Autowired
    private EntityManager entityManager;

    public List<Object> findNearbyGreets(double lat, double lng, double distance) {
        Query query = this.entityManager.createNativeQuery("SELECT id, (6371 * acos (cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude))  + sin(radians(:latitude)) * sin(radians(latitude)))) AS distance FROM Greet g GROUP BY id HAVING distance < :distance ORDER BY distance")
            .setParameter("latitude", lat)
            .setParameter("longitude", lng)
            .setParameter("distance", distance);

        List<Object> objects = query.getResultList();
        return objects;
    }
}
