package springbootStudy.spring_boot_study.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import springbootStudy.spring_boot_study.controller.Form.EditForm;
import springbootStudy.spring_boot_study.controller.Form.UpLoadForm;
import springbootStudy.spring_boot_study.domain.Board;
import springbootStudy.spring_boot_study.service.BoardService;

import java.util.List;

@Controller
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "num", direction = Sort.Direction.DESC) Pageable pageable){
        Page<Board> boards = boardService.getBoardList(pageable);
        model.addAttribute("boards",boards);
        return "board/boardList";
    }

    @GetMapping("/board/new")
    public String uploadView(){
        return "board/uploadView";
    }

    @PostMapping("/board/new")
    public String realUpload(UpLoadForm form){
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setWriter(form.getWriter());
        board.setContent(form.getContent());
        boardService.upload(board);
        return "redirect:/board";
    }

    @GetMapping("/board/detail/{num}")
    public String detailView(@PathVariable("num") Long num,Model model){
        Board board = boardService.selectOne(num);
        model.addAttribute("board",board);
        return "board/detailView";
    }

    @GetMapping("/board/edit/{num}")
    public String editView(@PathVariable("num") Long num, Model model){
        Board board = boardService.selectOne(num);
        model.addAttribute("board",board);
        return "board/editView";
    }

    @PostMapping("/board/edit/{num}")
    public String realEdit(@PathVariable("num") Long num, EditForm form){
        boardService.updateBoard(num,form.getTitle(), form.getContent());
        return "redirect:/board/detail/" + num;
    }

    @GetMapping("/board/delete/{num}")
    public String deleteView(@PathVariable("num") Long num ,Model model){
        Board board = boardService.selectOne(num);
        model.addAttribute("board",board);
        return "board/deleteView";
    }

    @PostMapping("/board/delete/{num}")
    public String realDelete(@PathVariable("num") Long num){
        boardService.deleteBoard(num);
        return "redirect:/board";
    }
}
