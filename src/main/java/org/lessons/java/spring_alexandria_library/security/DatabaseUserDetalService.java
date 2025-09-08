package org.lessons.java.spring_alexandria_library.security;

import java.util.Optional;

import org.lessons.java.spring_alexandria_library.model.User;
import org.lessons.java.spring_alexandria_library.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DatabaseUserDetalService implements UserDetailsService {

    // A SEGUITO DEL BLOCCO NEL CREARE SECURITY CONFIG, PER CUI HO DOVUTO CREARE
    // USERDETAILSERVICE, MI TROVO UN ALTRO PROBLEMA, MI SERVE UN METODO CHE MI
    // CONSENTA DI RECUPERARE LE INFO SULLA BASE DELLO USERNAME --> DEVO CREARE UNA
    // REPOSITORY CHE IMPLEMENTI TALE METODO --> VAI A USERREPOSITORY

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> userAttempt = userRepository.findByUsername(username);

        if (userAttempt.isEmpty()) {
            throw new UsernameNotFoundException("There are no users available with username " + username);
        }

        // ORA DEVO RITORNARE USERDETAILS, CHE NON HO ANCORA CREATO --> CREAZIONE
        // DATABASEUSERDETAILS
        return new DatabaseUserDetails(userAttempt.get());
    }

}
