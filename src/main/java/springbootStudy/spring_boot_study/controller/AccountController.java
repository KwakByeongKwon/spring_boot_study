package springbootStudy.spring_boot_study.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import springbootStudy.spring_boot_study.controller.Form.LoginForm;
import springbootStudy.spring_boot_study.service.AccountService;

@Controller
public class AccountController {

    private final AccountService accountService;
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/account/login")
    public String login(){
        return "account/loginView";
    }

    @PostMapping("/account/login")
    public String realLogin(LoginForm form, HttpServletResponse response){
        String id = form.getId();
        String pw = form.getPw();

        if (accountService.accountCheck(id,pw)){
            cookieSet(id,response);
            return "redirect:/board";
        } else {
            return "redirect:/account/login";
        }
    }

    @GetMapping("/account/signup")
    public String signup(){
        return "account/signupView";
    }

    public void cookieSet(String id,HttpServletResponse response){
        String nickName = accountService.nickName(id);
        Cookie cookie1 = new Cookie("id",id);
        Cookie cookie2 = new Cookie("nickName",nickName);
        cookie1.setPath("/");
        cookie2.setPath("/");

        response.addCookie(cookie1);
        response.addCookie(cookie2);
    }
}
