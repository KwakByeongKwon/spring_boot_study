package springbootStudy.spring_boot_study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import springbootStudy.spring_boot_study.domain.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {

}
