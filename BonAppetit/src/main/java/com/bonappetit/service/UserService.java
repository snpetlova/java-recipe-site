package com.bonappetit.service;

import com.bonappetit.model.dtos.UserRegisterDto;
import com.bonappetit.model.entity.RecipeEntity;
import com.bonappetit.model.entity.UserEntity;
import com.bonappetit.repo.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
        return null;
    }

    public boolean doesEmailExist(UserRegisterDto userRegisterDto) {
        String inputEmail = userRegisterDto.getEmail();

        Optional<UserEntity> optionalUser = userRepository.findByEmail(inputEmail);

        if (optionalUser.isPresent()) {
            return true;
        }

        return false;
    }

    public boolean doesUsernameExist(UserRegisterDto userRegisterDto) {
        String inputUsername = userRegisterDto.getUsername();

        Optional<UserEntity> optionalUser = userRepository.findByUsername(inputUsername);

        if (optionalUser.isPresent()) {
            return true;
        }

        return false;
    }

    public boolean doesPasswordsMatch(UserRegisterDto userRegisterDto) {
        String inputPassword = userRegisterDto.getPassword();
        String inputRePass = userRegisterDto.getConfirmPassword();

        if (inputPassword.equals(inputRePass)) {
            return true;
        }

        return false;
    }

    public void createUser(UserRegisterDto currentUser) {
        UserEntity newUser = new UserEntity();
        newUser.setUsername(currentUser.getUsername());
        newUser.setEmail(currentUser.getEmail());
        String encodedPass = passwordEncoder.encode(currentUser.getPassword());
        newUser.setPassword(encodedPass);
        userRepository.save(newUser);
    }


    public Set<RecipeEntity> getAllFavourites() {
        String username = getCurrentUser().getUsername();
        UserEntity userEntity = userRepository.findByUsername(username).get();

        return userEntity.getFavouriteRecipeEntity();
    }
}
