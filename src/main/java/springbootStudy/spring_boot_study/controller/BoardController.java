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
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springbootStudy.spring_boot_study.controller.Form.EditForm;
import springbootStudy.spring_boot_study.controller.Form.UpLoadForm;
import springbootStudy.spring_boot_study.domain.Board;
import springbootStudy.spring_boot_study.domain.FileEntity;
import springbootStudy.spring_boot_study.service.BoardService;
import springbootStudy.spring_boot_study.service.FileService;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Controller
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;

    @Autowired
    public BoardController(BoardService boardService, FileService fileService) {
        this.boardService = boardService;
        this.fileService = fileService;
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
    public String realUpload(UpLoadForm form, @RequestParam("file") MultipartFile file){
        Board board = new Board();
        board.setTitle(form.getTitle());
        board.setWriter(form.getWriter());
        board.setContent(form.getContent());

        board = boardService.upload(board);

        if (file != null && !file.isEmpty()) {
            fileService.fileSave(file,board.getNum());
        }
        return "redirect:/board";
    }

    @GetMapping("/board/detail/{num}")
    public String detailView(@PathVariable("num") Long num, Model model){
        boardService.cntUp(num);
        Board board = boardService.selectOne(num);
        FileEntity file = fileService.fileExtractor(num);
        model.addAttribute("file",file);
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
        fileService.fileDelete(num);
        boardService.deleteBoard(num);
        return "redirect:/board";
    }







    // 다운로드 로직
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileService.loadFileAsResource(fileName);

        String s = "스";
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        // 콘텐츠 타입 결정
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            // 파일 타입을 결정하지 못하는 경우
            contentType = "application/octet-stream";
        }

        // 적절한 Content-Disposition 헤더 설정
        String contentDisposition = "attachment; filename=\"" + resource.getFilename() + "\"";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(resource);
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
