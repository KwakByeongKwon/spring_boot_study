package springbootStudy.spring_boot_study.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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

    @ModelAttribute
    public void addAttributes(HttpServletRequest request, Model model){
        String nickName = getNickNameCookie(request);
        model.addAttribute("nickName", nickName);
    }


    @GetMapping("/board")
    public String boardList(HttpServletRequest request,Model model,
                            @RequestParam(value = "page", defaultValue = "1") int page,
                            @RequestParam(value = "size", defaultValue = "10") int size){
        if (getNickNameCookie(request) == null){
            return "redirect:/account/login";
        }
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("num").descending());
        Page<Board> boards = boardService.getBoardList(pageable);
        int nowPage = boards.getPageable().getPageNumber();
        int startPage = Math.max(nowPage - 4, 1);
        int endPage = Math.min(nowPage + 5,boards.getTotalPages());

        model.addAttribute("boards",boards);
        model.addAttribute("nowPage",nowPage);
        model.addAttribute("startPage",startPage);
        model.addAttribute("endPage",endPage);

        System.out.println("nowPage: "+ nowPage);
        System.out.println("boards.getTotalPages() = " + boards.getTotalPages());
        return "board/boardList";
    }

    @GetMapping("/board/new")
    public String uploadView(HttpServletRequest request,Model model){
        if (getNickNameCookie(request) == null){
            return "redirect:/account/login";
        }
        model.addAttribute("nickName",getNickNameCookie(request));
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
    public String detailView(@PathVariable("num") Long num, Model model){
        boardService.cntUp(num);
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

    public String getNickNameCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String nickName = null;
        if (cookies != null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("nickName")){
                    nickName = cookie.getValue();
                    break;
                }
            }
        }
        return nickName;
    }
}
