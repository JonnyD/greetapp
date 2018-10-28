package com.getgreetapp.greetapp.service;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.Greet;
import com.getgreetapp.greetapp.domain.NearbyGang;
import com.getgreetapp.greetapp.domain.NearbyGreet;
import com.getgreetapp.greetapp.repository.GreetRepository;
import com.getgreetapp.greetapp.repository.GreetRepositoryImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GreetService {
    private final GreetRepository greetRepository;
    private final GreetRepositoryImpl greetRepositoryImpl;

    public GreetService (GreetRepository greetRepository,
                         GreetRepositoryImpl greetRepositoryImpl) {
        this.greetRepository = greetRepository;
        this.greetRepositoryImpl = greetRepositoryImpl;
    }

    public List<NearbyGreet> getNearbyGreets(Double longitude, Double latitude, int radius) {
        List<Object> nearbyGreetsFromDatabase = this.greetRepositoryImpl.findNearbyGreets(longitude, latitude, radius);
        List<NearbyGreet> nearbyGreets = new ArrayList<NearbyGreet>();
        for (Object object : nearbyGreetsFromDatabase) {
            Class oClass = object.getClass();
            if (oClass.isArray()) {
                for (int i = 0; i < Array.getLength(object); i++) {
                    BigInteger greetId = (BigInteger) Array.get(object, 0);
                    Greet greet = this.getById(greetId.longValue()).get();

                    Double distance = (Double) Array.get(object, 1);

                    NearbyGreet nearbyGreet = new NearbyGreet(greet, distance);
                    nearbyGreets.add(nearbyGreet);
                }
            }
        }
        return nearbyGreets;
    }

    public Optional<Greet> getById(Long greetId) {
        return this.greetRepository.findById(greetId);
    }

    public List<Greet> getAll() {
        return this.greetRepository.findAll();
    }

    public List<Greet> getByUser(Long userId) {
        return this.greetRepository.findByUser(userId);
    }

    public List<Greet> getByGang(Long groupId) {
        return this.greetRepository.findByGroup(groupId);
    }

    public Greet save(Greet greet) {
        return this.greetRepository.save(greet);
    }

    public void deleteById(Long gangId) {
        this.greetRepository.deleteById(gangId);
    }
}
