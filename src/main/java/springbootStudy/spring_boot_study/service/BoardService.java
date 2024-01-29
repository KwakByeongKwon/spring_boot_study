package springbootStudy.spring_boot_study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import springbootStudy.spring_boot_study.domain.Board;
import springbootStudy.spring_boot_study.repository.BoardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BoardService {
    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    public Board upload(Board board){
        return boardRepository.save(board);
    }

    public Page<Board> getBoardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    public Board selectOne(Long num) {
        return boardRepository.findById(num)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
    }

    public void cntUp(long num){
        Optional<Board> boardOptional = boardRepository.findById(num);
        if (boardOptional.isPresent()){
            Board board = boardOptional.get();
            board.setCnt(board.getCnt() + 1);
            boardRepository.save(board);
        }
    }

    public Board updateBoard(Long num, String title, String content){
        Optional<Board> boardOptional = boardRepository.findById(num);
        if (boardOptional.isPresent()){
            Board board = boardOptional.get();
            board.setTitle(title);
            board.setContent(content);
            return boardRepository.save(board);
        } else {
            throw new IllegalStateException("존재하지 않는 게시글 입니다.");
        }
    }

    public void deleteBoard(Long num){
        if (boardRepository.existsById(num)){
            boardRepository.deleteById(num);
        } else {
            throw new IllegalStateException("존재하지 않는 게시글 입니다.");
        }
    }
}
