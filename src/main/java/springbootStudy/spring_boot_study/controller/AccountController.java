package springbootStudy.spring_boot_study.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import springbootStudy.spring_boot_study.controller.Form.LoginForm;
import springbootStudy.spring_boot_study.controller.Form.SignupForm;
import springbootStudy.spring_boot_study.domain.Account;
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
        String accountId = form.getAccountId();
        String accountPw = form.getAccountPw();
        if (accountService.accountCheck(accountId,accountPw)){
            cookieSet(accountId,response);
            return "redirect:/board";
        } else {
            return "redirect:/account/login";
        }
    }

    @GetMapping("/account/signup")
    public String signup(){
        return "account/signupView";
    }

    @PostMapping("/account/signup")
    public String realSignup(SignupForm form, HttpServletResponse response, RedirectAttributes redirectAttributes){
        String accountPw = form.getAccountPw();
        String accountPwCheck = form.getAccountPwCheck();
        String accountId = form.getAccountId();

        if (accountPw.equals(accountPwCheck)){
            Account account = new Account();
            account.setName(form.getName());
            account.setAccountId(accountId);
            account.setAccountPw(accountPw);
            account.setNickName(form.getNickName());

            accountService.registration(account);
            cookieSet(accountId,response);
            return "redirect:/board";
        } else {
            redirectAttributes.addFlashAttribute("signupError", "비밀번호가 일치하지 않습니다.");
            return "redirect:/account/signup";
        }
    }

    @GetMapping("/account/logout")
    public String logout(){
        return "account/logoutView";
    }
    @PostMapping("/account/logout")
    public String realLogout(HttpServletRequest request,HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("nickName")){
                    cookie.setMaxAge(0);
                    cookie.setPath("/");
                    response.addCookie(cookie);
                    break;
                }
            }
        }
        return "redirect:/account/login";
    }


    public void cookieSet(String accountId,HttpServletResponse response){
        String nickName = accountService.nickName(accountId);
        Cookie cookie = new Cookie("nickName",nickName);
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
