package fr.esgi.baldwarsapi.configuration;

import fr.esgi.baldwarsapi.domain.user.UserService;
import fr.esgi.baldwarsapi.domain.user.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            var user = userService.findOneByUsername(username);
            return new User(user.getUsername(), user.getPassword(), new ArrayList<>());

        } catch (UserNotFoundException exception) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

}
