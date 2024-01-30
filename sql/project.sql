CREATE TABLE "BOARD" (
                         "NUM" number PRIMARY KEY ,
                         "TITLE" varchar2(50) NOT NULL,
                         "WRITER" varchar2(50) NOT NULL,
                         "CONTENT" varchar2(1000),
                         "REGDATE" DATE,
                         "CNT" number DEFAULT 0
);

CREATE SEQUENCE "BOARD_SEQ"
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 99999
    nocache
    nocycle
    noorder;

CREATE TABLE "ACCOUNT" (
                         "ID" number PRIMARY KEY ,
                         "NAME" varchar2(50) NOT NULL,
                         "ACCOUNTID" varchar2(50) NOT NULL,
                         "ACCOUNTPW" varchar2(50) NOT NULL,
                         "NICKNAME" varchar2(50)
);

CREATE SEQUENCE "ACCOUNT_SEQ"
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 99999
    nocache
    nocycle
    noorder;

CREATE TABLE "FILE_ENTITY" (
                         "ID" number PRIMARY KEY ,
                         "FILE_NAME" varchar2(50) ,
                         "FILE_PATH" varchar2(1000) ,
                         "FILE_SIZE" number,
                         "BOARD_NUM" number,
                         CONSTRAINT FK_FILE_BOARD FOREIGN KEY ("BOARD_NUM")
                            REFERENCES BOARD ("NUM")
);

CREATE SEQUENCE "FILE_SEQ"
    START WITH 1
    INCREMENT BY 1
    MAXVALUE 99999
    nocache
    nocycle
    noorder;



/* 지우기 */
  drop table "BOARD";
  drop table "ACCOUNT";
  drop table "FILE";

  drop sequence "BOARD_SEQ";
  drop sequence "ACCOUNT_SEQ";
  drop sequence "FILE_SEQ";