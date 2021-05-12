package fr.esgi.baldwarsapi.configuration;

import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import fr.esgi.baldwarsapi.domain.user.UserService;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DomainUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public DomainUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            var appUser = userService.findOneByUsername(username);

            return User.builder()
                    .username(username)
                    .password(appUser.getPassword())
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        } catch (UserNotFoundException e) {
            throw new AuthenticationServiceException("username " + username + " not found");
        }

    }
}
