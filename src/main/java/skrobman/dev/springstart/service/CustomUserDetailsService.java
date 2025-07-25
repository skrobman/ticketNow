package skrobman.dev.springstart.service;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import skrobman.dev.springstart.entity.UserEntity;
import skrobman.dev.springstart.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);

        if(user == null){
            throw new UsernameNotFoundException("User with email: " + email + " is not found");
        }

        if (!user.isEnabled()){
            throw new DisabledException("Account is not activated!");
        }

        return User.builder().username(user.getEmail()).password(user.getPassword()).build();
    }
}
