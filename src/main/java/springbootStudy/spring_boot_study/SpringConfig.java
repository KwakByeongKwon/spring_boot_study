package springbootStudy.spring_boot_study;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springbootStudy.spring_boot_study.repository.BoardRepository;
import springbootStudy.spring_boot_study.service.BoardService;

import javax.sql.DataSource;

@Configuration
public class SpringConfig {
    private final BoardRepository boardRepository;

    @Autowired
    public SpringConfig(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Bean
    public BoardService boardService(){
        return new BoardService(boardRepository);
    }

}
