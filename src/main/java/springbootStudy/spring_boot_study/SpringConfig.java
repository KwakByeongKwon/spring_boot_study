package springbootStudy.spring_boot_study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbootStudy.spring_boot_study.repository.AccountRepository;
import springbootStudy.spring_boot_study.repository.BoardRepository;
import springbootStudy.spring_boot_study.repository.FileRepository;
import springbootStudy.spring_boot_study.service.AccountService;
import springbootStudy.spring_boot_study.service.BoardService;
import springbootStudy.spring_boot_study.service.FileService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final BoardRepository boardRepository;
    private final AccountRepository accountRepository;
    private final FileRepository fileRepository;

    @Autowired
    public SpringConfig(BoardRepository boardRepository, AccountRepository accountRepository, FileRepository fileRepository) {
        this.boardRepository = boardRepository;
        this.accountRepository = accountRepository;
        this.fileRepository = fileRepository;
    }

    @Bean
    public BoardService boardService(){
        return new BoardService(boardRepository);
    }

    @Bean
    public AccountService accountService(){
        return new AccountService(accountRepository);
    }

    @Bean
    public FileService fileService(){
        return new FileService(fileRepository,boardRepository);
    }

}
