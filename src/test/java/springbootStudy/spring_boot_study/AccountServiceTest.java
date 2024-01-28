package springbootStudy.spring_boot_study;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import springbootStudy.spring_boot_study.domain.Account;
import springbootStudy.spring_boot_study.domain.Board;
import springbootStudy.spring_boot_study.service.AccountService;
import springbootStudy.spring_boot_study.service.BoardService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class AccountServiceTest {

    @Autowired
    private AccountService accountService;

    @Test
    public void testSaveAccount() {
        Account account = new Account();
        account.setName("병권");
        account.setAccountId("123123");
        account.setAccountPw("1234");
        if (account.getNickName() == null || account.getNickName().isEmpty()) {
            account.setNickName(account.getName());
        }
        accountService.registration(account);

        assertNotNull(account);
        // 추가적인 검증 로직을 여기에 작성할 수 있습니다.
        // 예: 특정 페이지의 요소 수 검증, 특정 데이터 존재 여부 등
    }


//    @Test
//    public void testLogin(){
//        Account account = new Account();
//        account.setAccountId("1234");
//        account.setAccountPw("1111");
//
//        String id = "1234";
//        String pw = "1111";
//
//        assertFalse(accountService.accountCheck(id,pw));
//    }

    // 기타 필요한 테스트 메소드 추가...
}