package com.bonappetit.controller;

import com.bonappetit.model.dtos.UserRegisterDto;
import com.bonappetit.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("userRegister")
    public UserRegisterDto userRegisterDto() {
        return new UserRegisterDto();
    }

    @GetMapping("/login")
    public String getLogin(Model model) {
        if (!model.containsAttribute("bad_credentials")) {
            model.addAttribute("bad_credentials");
        }
        return "login";
    }
    @PostMapping("/login-error")
    public String onFailedLogin(
            String username,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("bad_credentials", true);

        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/";
    }

    @GetMapping("/register")
    public String getRegister(Model model) {
        if (!model.containsAttribute("doesEmailExist")) {
            model.addAttribute("doesEmailExist", false);
        }
        if (!model.containsAttribute("doesUsernameExist")) {
            model.addAttribute("doesUsernameExist", false);
        }

        if (!model.containsAttribute("doesPasswordsMatch")) {
            model.addAttribute("doesPasswordsMatch", true);
        }

        return "register";
    }

    @PostMapping("/register")
    public String crateRegister(@Valid UserRegisterDto userRegisterDto, BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userRegister", userRegisterDto);
            redirectAttributes
                    .addFlashAttribute("org.springframework.validation.BindingResult.userRegister", bindingResult);
            return "redirect:/users/register";
        }

        boolean doesEmailExist = userService.doesEmailExist(userRegisterDto);
        boolean doesUsernameExist = userService.doesUsernameExist(userRegisterDto);
        if (doesUsernameExist && doesEmailExist){
            redirectAttributes.addFlashAttribute("userRegister",userRegisterDto);
            redirectAttributes.addFlashAttribute("doesUsernameExist", true);
            redirectAttributes.addFlashAttribute("doesEmailExist", true);
            return "redirect:/users/register";
        }

        if (doesEmailExist) {
            redirectAttributes.addFlashAttribute("userRegister",userRegisterDto);
            redirectAttributes.addFlashAttribute("doesEmailExist", true);
            return "redirect:/users/register";
        }

        if (doesUsernameExist) {
            redirectAttributes.addFlashAttribute("userRegister",userRegisterDto);
            redirectAttributes.addFlashAttribute("doesUsernameExist", true);
            return "redirect:/users/register";
        }



        boolean doesPasswordsMatch = userService.doesPasswordsMatch(userRegisterDto);
        if (!doesPasswordsMatch) {
            redirectAttributes.addFlashAttribute("userRegister",userRegisterDto);
            redirectAttributes.addFlashAttribute("doesPasswordsMatch", false);
            return "redirect:/users/register";
        }


        userService.createUser(userRegisterDto);

        return "redirect:/users/login";
    }

}
