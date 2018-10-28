package com.getgreetapp.greetapp.service;

import com.getgreetapp.greetapp.domain.Gang;
import com.getgreetapp.greetapp.domain.GangUser;
import com.getgreetapp.greetapp.domain.User;
import com.getgreetapp.greetapp.repository.GangUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GangUserService {
    private final GangUserRepository gangUserRepository;

    public GangUserService(GangUserRepository gangUserRepository) {
        this.gangUserRepository = gangUserRepository;
    }

    public GangUser createGangUser(Gang gang, User user, String role) {
        GangUser gangUser = new GangUser();
        gangUser.setGang(gang);
        gangUser.setUser(user);
        gangUser.setRole(role);
        return this.save(gangUser);
    }

    public Optional<GangUser> getById(Long id) {
        return this.gangUserRepository.findById(id);
    }

    public List<GangUser> findAll() {
        return this.gangUserRepository.findAll();
    }

    public List<GangUser> getByGroup(Long groupId) {
        return this.gangUserRepository.findByGroup(groupId);
    }

    public GangUser getByGangAndUser(Long gangId, Long userId) {
        return this.gangUserRepository.findByGangAndUser(gangId, userId);
    }

    public GangUser save(GangUser gangUser) {
        return this.gangUserRepository.saveAndFlush(gangUser);
    }

    public void deleteById(Long id) {
        this.gangUserRepository.deleteById(id);
    }
}
