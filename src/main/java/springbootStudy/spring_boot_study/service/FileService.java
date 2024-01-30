package springbootStudy.spring_boot_study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import springbootStudy.spring_boot_study.domain.Board;
import springbootStudy.spring_boot_study.domain.FileEntity;
import springbootStudy.spring_boot_study.repository.BoardRepository;
import springbootStudy.spring_boot_study.repository.FileRepository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final BoardRepository boardRepository;
    private final Path fileStorageLocation;

    @Autowired
    public FileService(FileRepository fileRepository,BoardRepository boardRepository) {
        this.fileRepository = fileRepository;
        this.boardRepository = boardRepository;
        this.fileStorageLocation = Paths.get("src/main/resources/static").toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException e){
            throw new IllegalStateException("파일 저장 위치를 생성할 수 없습니다.");
        }
    }


    public void fileSave(MultipartFile file,Long boardId){
        if (!file.isEmpty()){
            FileEntity fileEntity = new FileEntity();
            fileEntity.setFileName(file.getOriginalFilename());
            fileEntity.setFileSize(file.getSize());

            Board board = boardRepository.findById(boardId)
                    .orElseThrow(() -> new IllegalStateException("해당하는 Board가 없습니다."));
            fileEntity.setBoard(board);

            Path filePath = this.fileStorageLocation.resolve(file.getOriginalFilename()).normalize();
            try {
                Files.createDirectories(filePath.getParent());
                file.transferTo(filePath.toFile());

                fileEntity.setFilePath(filePath.toString());

                fileRepository.save(fileEntity);

            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    public void fileDelete(Long boardId){
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalStateException("해당하는 Board가 없습니다."));
        List<FileEntity> files = fileRepository.findAll();
        for (FileEntity file : files) {
            Path path = Paths.get(file.getFilePath());
            if (file.getBoard().getNum() == boardId){
                try{
                    Files.deleteIfExists(path);
                } catch (IOException e){
                    throw new IllegalStateException("해당 파일이 없습니다.");
                }
            }
        }
    }

    public FileEntity fileExtractor(Long id){
        List<FileEntity> files = fileRepository.findAll();
        for (FileEntity file : files) {
            if (file.getBoard().getNum() == id) {
                return fileRepository.findById(file.getId()).orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));
            }
        }
        return null;
    }

    public Resource loadFileAsResource(String fileName){
        try {
            Path filePath = this.fileStorageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("파일을 찾을 수 없습니다 " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("파일을 찾을 수 없습니다 " + fileName, ex);
        }
    }
}
