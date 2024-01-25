package springbootStudy.spring_boot_study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import springbootStudy.spring_boot_study.domain.Board;
import springbootStudy.spring_boot_study.service.BoardService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void testGetBoardListWithPagination() {
        int page = 0; // 페이지 번호 (0부터 시작)
        int size = 10; // 페이지당 데이터 수

        Pageable pageable = PageRequest.of(page, size);
        Page<Board> boardPage = boardService.getBoardList(pageable);

        assertNotNull(boardPage);
        assertFalse(boardPage.getContent().isEmpty(), "페이지에 내용이 없습니다.");

        // 추가적인 검증 로직을 여기에 작성할 수 있습니다.
        // 예: 특정 페이지의 요소 수 검증, 특정 데이터 존재 여부 등
    }

    // 기타 필요한 테스트 메소드 추가...
}