package br.com.fernandapcaetano.SpringSecurity6.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import br.com.fernandapcaetano.SpringSecurity6.models.AppUser;
import br.com.fernandapcaetano.SpringSecurity6.models.RegisterDTO;
import br.com.fernandapcaetano.SpringSecurity6.repositories.AppUserRepositories;
import jakarta.validation.Valid;

@Controller
public class AccountController {
  
    @Autowired
    private AppUserRepositories appUserRepositories;

    @GetMapping("/register")
    public String register(Model model){
        RegisterDTO registerDTO = new RegisterDTO();
        model.addAttribute("success", false);
        model.addAttribute(registerDTO);
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute RegisterDTO registerDTO, BindingResult result){
        
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            result.addError(
                new FieldError("registerDTO", "confirmPassword", "Verifique a senha novamente")
            );
        }

        AppUser appUser = appUserRepositories.findByEmail(registerDTO.getEmail());
        if (appUser !=null) {
            result.addError(
                new FieldError("registerDTO", "email", "Esse e-mail já existe")
            );
        }

        if (result.hasErrors()) {
            return "register"; 
        }

        try {
            var bCryptEncoder = new BCryptPasswordEncoder();

            AppUser newUser = new AppUser();
            newUser.setFirsName(registerDTO.getFirstName());
            newUser.setLastName(registerDTO.getLastName());
            newUser.setEmail(registerDTO.getEmail());
            newUser.setPassword(bCryptEncoder.encode(registerDTO.getPassword()));
            newUser.setRole("client");
            newUser.setCreatedAt(new Date());

            appUserRepositories.save(newUser);

            model.addAttribute("registerDTO", new RegisterDTO());
            model.addAttribute("success", true);

        } catch (Exception e) {
            result.addError(
                new FieldError("regsisterDTO", "firstName", "e.getMessage()")
            );
        }
        return "register";
    }
}
