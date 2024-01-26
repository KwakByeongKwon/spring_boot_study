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