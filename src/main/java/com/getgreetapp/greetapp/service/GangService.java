package com.getgreetapp.greetapp.service;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.domain.NearbyGang;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.GangRepository;
import com.getgreetapp.greetapp.repository.GangRepositoryImpl;
import com.getgreetapp.greetapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GangService {
    private final GangRepository gangRepository;
    private final GangRepositoryImpl gangRepositoryImpl;
    private final UserRepository userRepository;

    public GangService(GangRepository gangRepository,
                       GangRepositoryImpl gangRepositoryImpl,
                       UserRepository userRepository) {
        this.gangRepository = gangRepository;
        this.userRepository = userRepository;
        this.gangRepositoryImpl = gangRepositoryImpl;
    }

    public List<Gang> findAll() {
        return this.gangRepository.findAll();
    }

    public Optional<Gang> getById(Long id) {
        return this.gangRepository.findById(id);
    }

    public List<Gang> getByUser(long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();

        List<Gang> gangs = new ArrayList<Gang>();
        for (GangUser gangUser : user.getGangUsers()) {
            Gang gang = gangUser.getGang();
            gangs.add(gang);
        }

        return gangs;
    }

    public List<NearbyGang> getNearbyGangs(Double longitude, Double latitude, int radius) {
        List<Object> nearbyGangsFromDatabase = this.gangRepositoryImpl.findNearbyGangs(longitude, latitude, radius);
        List<NearbyGang> nearbyGangs = new ArrayList<NearbyGang>();
        for (Object object : nearbyGangsFromDatabase) {
            Class oClass = object.getClass();
            if (oClass.isArray()) {
                for (int i = 0; i < Array.getLength(object); i++) {
                    BigInteger gangId = (BigInteger) Array.get(object, 0);
                    Gang gang = this.getById(gangId.longValue()).get();

                    Double distance = (Double) Array.get(object, 1);

                    NearbyGang nearbyGang = new NearbyGang(gang, distance);
                    nearbyGangs.add(nearbyGang);
                }
            }
        }
        return nearbyGangs;
    }

    public Gang save(Gang gang) {
        return this.gangRepository.save(gang);
    }

    public void deleteById(Long gangId) {
        this.gangRepository.deleteById(gangId);
    }
}
