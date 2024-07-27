package br.com.fernandapcaetano.SpringSecurity6.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.fernandapcaetano.SpringSecurity6.models.AppUser;
import br.com.fernandapcaetano.SpringSecurity6.repositories.AppUserRepositories;

@Service
public class AppUserService implements UserDetailsService{

    @Autowired
    private AppUserRepositories appUserRepositories;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = appUserRepositories.findByEmail(email);
        if (appUser != null) {
            var springUser = User
                                .withUsername(appUser.getEmail())
                                .password(appUser.getPassword())
                                .roles(appUser.getRole())
                                .build();
            return springUser;
        }
        return null;
    }
}
