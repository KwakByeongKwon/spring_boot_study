package springbootStudy.spring_boot_study.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "board_seq_generator")
    @SequenceGenerator(name = "board_seq_generator", sequenceName = "board_SEQ", allocationSize = 1)

    private Long num;
    private String title;
    private String writer;
    private String content;
    private LocalDateTime regdate;
    @PrePersist
    protected void onCreate() {
        regdate = LocalDateTime.now();
    }
    private int cnt;

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getRegdate() {
        return regdate;
    }

    public void setRegdate(LocalDateTime regdate) {
        this.regdate = regdate;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }


    public String changeDate(){
        String date = regdate.toString().substring(0,10) + " " + regdate.toString().substring(11,13) + "시 " + regdate.toString().substring(14,16) + "분";
        return date;
    }
}
