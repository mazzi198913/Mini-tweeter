package pl.coderslab.controller;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.dto.LoginDto;
import pl.coderslab.model.User;
import pl.coderslab.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private UserRepository userRepository;

    //---------------------------------------------------------------------------
    //---
    //---------------------------------------------------------------------------
    @GetMapping
    public String login(Model model) {
        model.addAttribute("loginDto", new LoginDto());
        return "/login/loginForm";
    }

    @PostMapping

    public String postLogin(@ModelAttribute LoginDto loginDto,HttpServletRequest request) {
        User user = userRepository.findByUsername(loginDto.getLogin());
        if (user == null) {
            return "login/error";
        }
        String userPasword = user.getPassword();
        boolean checkpw = BCrypt.checkpw(loginDto.getPassword(), userPasword);
        if (checkpw) {
            request.getSession(true).setAttribute("loggedIn",true);
            return "redirect:/app/home";
        } else {
            return "login/error";
        }
    }

}
