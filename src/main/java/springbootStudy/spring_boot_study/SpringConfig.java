package springbootStudy.spring_boot_study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbootStudy.spring_boot_study.repository.AccountRepository;
import springbootStudy.spring_boot_study.repository.BoardRepository;
import springbootStudy.spring_boot_study.service.AccountService;
import springbootStudy.spring_boot_study.service.BoardService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public SpringConfig(BoardRepository boardRepository, AccountRepository accountRepository) {
        this.boardRepository = boardRepository;
        this.accountRepository = accountRepository;
    }

    @Bean
    public BoardService boardService(){
        return new BoardService(boardRepository);
    }

    @Bean
    public AccountService accountService(){
        return new AccountService(accountRepository);
    }

}
