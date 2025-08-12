package skrobman.dev.springstart.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import skrobman.dev.springstart.dto.ProfileDto;
import skrobman.dev.springstart.entity.ProfileEntity;
import skrobman.dev.springstart.entity.UserEntity;
import skrobman.dev.springstart.repository.UserRepository;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ProfileDto getCurrentUserProfile(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = principal instanceof UserDetails ?
                ((UserDetails) principal).getUsername() :
                principal.toString();

        UserEntity user = userRepository.findByEmail(username);

        ProfileEntity profile = user.getProfile();

        return new ProfileDto(
                profile.getFullName(),
                profile.getPhoneNumber(),
                profile.getAvatarUrl()
        );
    }

    public ProfileDto updateUserProfile(ProfileDto profileDto){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String username = principal instanceof UserDetails ?
                ((UserDetails) principal).getUsername() :
                principal.toString();

        UserEntity user = userRepository.findByEmail(username);

        ProfileEntity profile = user.getProfile();

        //Change profile data
        profile.setFullName(profileDto.getFullName());
        profile.setPhoneNumber(profileDto.getPhoneNumber());

        userRepository.save(user);

        return new ProfileDto(
                profile.getFullName(),
                profile.getPhoneNumber(),
                profile.getAvatarUrl()
        );
    }
}
