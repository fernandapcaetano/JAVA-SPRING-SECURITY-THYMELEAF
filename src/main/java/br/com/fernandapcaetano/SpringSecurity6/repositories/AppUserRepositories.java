package br.com.fernandapcaetano.SpringSecurity6.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.fernandapcaetano.SpringSecurity6.models.AppUser;

@Repository
public interface AppUserRepositories extends JpaRepository<AppUser, Long>{
    public AppUser findByEmail(String email);
}
