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

    public Account registration(Account account){
        return accountRepository.save(account);
    }


}
