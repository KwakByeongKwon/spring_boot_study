package springbootStudy.spring_boot_study.controller.Form;

public class SignupForm {
    private String nickName;
    private String name;
    private String accountId;
    private String accountPw;
    private String accountPwCheck;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getAccountPw() {
        return accountPw;
    }

    public void setAccountPw(String accountPw) {
        this.accountPw = accountPw;
    }

    public String getAccountPwCheck() {
        return accountPwCheck;
    }

    public void setAccountPwCheck(String accountPwCheck) {
        this.accountPwCheck = accountPwCheck;
    }
}
