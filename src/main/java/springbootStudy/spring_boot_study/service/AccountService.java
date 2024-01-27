package springbootStudy.spring_boot_study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springbootStudy.spring_boot_study.domain.Account;
import springbootStudy.spring_boot_study.domain.Board;
import springbootStudy.spring_boot_study.repository.AccountRepository;
import springbootStudy.spring_boot_study.repository.BoardRepository;

import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }


    // 회원 등록
    public Account registration(Account account){
        return accountRepository.save(account);
    }

    // 회원 확인
    public boolean accountCheck(String id, String pw){
        Optional<Account> account = accountRepository.findByAccountId(id);

        // 계정이 존재하고, 비밀번호가 일치하는지 확인
        if(account.isPresent() && account.get().getAccountPw().equals(pw)) {
            return true;
        } else {
            return false;
        }
    }

    public String nickName(String id){
        Optional<Account> account = accountRepository.findByAccountId(id);
        if(account.isPresent()){
            return account.get().getNickName();
        } else {
            throw new IllegalStateException("존재하지 않는 계정입니다.");
        }
    }

}
