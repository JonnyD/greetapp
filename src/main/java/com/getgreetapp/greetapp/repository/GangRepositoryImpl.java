package com.getgreetapp.greetapp.repository;

import com.getgreetapp.greetapp.domain.Gang;

import com.getgreetapp.greetapp.domain.NearbyGang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class GangRepositoryImpl {
    @Autowired
    private EntityManager entityManager;

    public List<Object> findNearbyGangs(double lat, double lng, double distance) {
        Query query = this.entityManager.createNativeQuery("SELECT id, (6371 * acos (cos(radians(:latitude)) * cos(radians(latitude)) * cos(radians(longitude) - radians(:longitude))  + sin(radians(:latitude)) * sin(radians(latitude)))) AS distance FROM Gang g GROUP BY id HAVING distance < :distance ORDER BY distance")
            .setParameter("latitude", lat)
            .setParameter("longitude", lng)
            .setParameter("distance", distance);

        List<Object> objects = query.getResultList();
        return objects;
    }
}
