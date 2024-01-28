package springbootStudy.spring_boot_study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springbootStudy.spring_boot_study.domain.Account;
import springbootStudy.spring_boot_study.repository.AccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    // 회원 등록
    public void registration(Account account){
        accountRepository.save(account);
    }

    // 회원 확인
    public boolean accountCheck(String accountId, String accountPw){
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        // 계정이 존재하고, 비밀번호가 일치하는지 확인
        return account.isPresent() && account.get().getAccountPw().equals(accountPw);
    }

    public String nickName(String accountId){
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if(account.isPresent()){
            if(account.get().getNickName() != null || !account.get().getNickName().isEmpty()){
                return account.get().getNickName();
            } else {
                return account.get().getName();
            }
        } else {
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        }
    }

}
