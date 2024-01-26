package springbootStudy.spring_boot_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import springbootStudy.spring_boot_study.domain.Account;


@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}


