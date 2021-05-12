package ru.geekbrains.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekbrains.model.Authority;
import ru.geekbrains.model.Role;
import ru.geekbrains.model.User;
import ru.geekbrains.repo.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class UserAuthService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findUserByName(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getName(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        Collection collection = new HashSet<>();
        Set<Authority> authorities = new HashSet<>();
        roles.forEach(r -> {
            r.getAuthorities().forEach(a -> authorities.add(a));
        });
        authorities.forEach(a -> collection.add(new SimpleGrantedAuthority(a.getName())));
        roles.forEach(r -> collection.add(new SimpleGrantedAuthority(r.getName())));
        return collection;
    }
}
