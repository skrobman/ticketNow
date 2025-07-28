package skrobman.dev.springstart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import skrobman.dev.springstart.entity.UserEntity;
import skrobman.dev.springstart.repository.UserRepository;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
//@RequiredArgsConstructor
public class Oauth2RegistrationService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    public Oauth2RegistrationService(UserRepository userRepository) {
        logger.error("Oauth2RegistrationService constructor HAS BEEN called.");
        this.userRepository = userRepository;
    }

    private static final Logger logger = LoggerFactory.getLogger(Oauth2RegistrationService.class);

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        logger.error("loadUser called");
        System.out.println(userRequest.getAccessToken().getTokenValue());
        OAuth2User oAuth2User = super.loadUser(userRequest);
        logger.error(oAuth2User.getAuthorities().toString());
        logger.error(oAuth2User.getAttributes().toString());
        logger.error(oAuth2User.toString());
        return processUser(oAuth2User, userRequest);
    }

    private OAuth2User processUser(OAuth2User oAuth2User, OAuth2UserRequest request) {
        UserEntity user = userRepository.findByEmail(oAuth2User.getAttribute("email"));

        if(user == null){
            user = registerUser(oAuth2User, request);
        }
        else {
            updateUser(oAuth2User, user);
        }

        return oAuth2User;
    }

    private UserEntity registerUser(OAuth2User oAuth2User, OAuth2UserRequest request) {
        UserEntity user = new UserEntity();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        user.setEmail(attributes.get("email").toString());
        //user.setPassword(attributes.get("password").toString());

        return userRepository.save(user);
    }

    private void updateUser(OAuth2User oAuth2User, UserEntity user) {
        user.setEmail(oAuth2User.getAttribute("email"));
        userRepository.save(user);
    }
}
