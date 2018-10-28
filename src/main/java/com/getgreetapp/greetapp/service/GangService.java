package com.getgreetapp.greetapp.service;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.GangRepository;
import com.getgreetapp.greetapp.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GangService {
    private final GangRepository gangRepository;
    private final UserRepository userRepository;

    public GangService(GangRepository gangRepository,
                       UserRepository userRepository) {
        this.gangRepository = gangRepository;
        this.userRepository = userRepository;
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

    public Gang save(Gang gang) {
        return this.gangRepository.save(gang);
    }

    public void deleteById(Long gangId) {
        this.gangRepository.deleteById(gangId);
    }
}
