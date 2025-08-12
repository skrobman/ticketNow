package skrobman.dev.springstart.service;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import skrobman.dev.springstart.entity.Oauth2UserEntity;
import skrobman.dev.springstart.entity.UserEntity;
import skrobman.dev.springstart.exception.EmailAlreadyExist;
import skrobman.dev.springstart.exception.NoEmailProvidedException;
import skrobman.dev.springstart.exception.NoNameProvidedException;
import skrobman.dev.springstart.exception.NoRequiredParameterException;
import skrobman.dev.springstart.repository.Oauth2UserRepository;
import skrobman.dev.springstart.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Authorizes users that log in via oAuth2 with OpenID Connect
 */
@Service
public class Oauth2RegistrationService extends OidcUserService {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2RegistrationService.class);
    private final Oauth2UserRepository oauth2UserRepository;
    private final UserRepository userRepository;

    public Oauth2RegistrationService(Oauth2UserRepository oauth2UserRepository, UserRepository userRepository) {
        logger.error("Oauth2OidcRegistrationService [OpenID Connect] constructor HAS BEEN called.");
        this.oauth2UserRepository = oauth2UserRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        logger.error("loadUser called");
        System.out.println(userRequest.getAccessToken().getTokenValue());
        OidcUser oAuth2User = super.loadUser(userRequest);
        logger.error(oAuth2User.getAuthorities().toString());
        logger.error(oAuth2User.getAttributes().toString());
        logger.error(oAuth2User.toString());
        return processUser(oAuth2User, userRequest);
    }

    private OidcUser processUser(OidcUser oAuth2User, OAuth2UserRequest request) {
        // TODO: Rename this local variable!
        Oauth2UserEntity user = oauth2UserRepository.findByEmail(oAuth2User.getAttribute("email"));
        UserEntity defaultUser = userRepository.findByEmail(oAuth2User.getAttribute("email"));

        if (user == null && defaultUser == null) {
            try {
                user = registerUser(oAuth2User, request);
            } catch (NoRequiredParameterException e) {
                throw new RuntimeException(e);
            }
        } else if (user != null && defaultUser == null) {
            updateUser(oAuth2User, user);
        } else {
            throw new EmailAlreadyExist("This user already exists. Provide login and password!");
        }

        if (user == null) {
            throw new RuntimeException("User was not registered properly.");
        }

        return oAuth2User;
    }

    private Oauth2UserEntity registerUser(OAuth2User oAuth2User, OAuth2UserRequest request) throws NoRequiredParameterException {
        Oauth2UserEntity user = new Oauth2UserEntity();
        logger.error(request.getAccessToken().getTokenValue());
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

        return oauth2UserRepository.save(user);
    }

    private void updateUser(OAuth2User oAuth2User, Oauth2UserEntity user) {
        user.setEmail(oAuth2User.getAttribute("email"));
        oauth2UserRepository.save(user);
    }
}
