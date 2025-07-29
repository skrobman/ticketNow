package skrobman.dev.springstart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import skrobman.dev.springstart.entity.Oauth2UserEntity;
import skrobman.dev.springstart.exception.NoEmailProvidedException;
import skrobman.dev.springstart.exception.NoNameProvidedException;
import skrobman.dev.springstart.exception.NoRequiredParameterException;
import skrobman.dev.springstart.repository.Oauth2UserRepository;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
//@RequiredArgsConstructor
public class Oauth2RegistrationService extends DefaultOAuth2UserService {

    private final Oauth2UserRepository userRepository;

    public Oauth2RegistrationService(Oauth2UserRepository userRepository) {
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
        Oauth2UserEntity user = userRepository.findByEmail(oAuth2User.getAttribute("email"));

        if(user == null){
            try {
                user = registerUser(oAuth2User, request);
            } catch (NoRequiredParameterException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            updateUser(oAuth2User, user);
        }

        return oAuth2User;
    }

    private Oauth2UserEntity registerUser(OAuth2User oAuth2User, OAuth2UserRequest request) throws NoRequiredParameterException {
        Oauth2UserEntity user = new Oauth2UserEntity();

        Map<String, Object> attributes = oAuth2User.getAttributes();

        Object email = attributes.get("email");
        Object name = attributes.get("name");

        if (email == null) {
            throw new NoEmailProvidedException("Email was not provided by the provider.\nTry again with a different provider or contact support.");
        }
        if (name == null) {
            throw new NoNameProvidedException("No name was provided by the provider.\nTry again with a different provider or contact support.");
        }
        user.setEmail(email.toString());
        //user.setPassword(attributes.get("password").toString());

        return userRepository.save(user);
    }

    private void updateUser(OAuth2User oAuth2User, Oauth2UserEntity user) {
        user.setEmail(oAuth2User.getAttribute("email"));
        userRepository.save(user);
    }
}
