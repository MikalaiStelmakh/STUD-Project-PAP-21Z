DROP TABLE author CASCADE CONSTRAINTS;

DROP TABLE book CASCADE CONSTRAINTS;

DROP TABLE book_author CASCADE CONSTRAINTS;

DROP TABLE book_genre CASCADE CONSTRAINTS;

DROP TABLE book_instance CASCADE CONSTRAINTS;

DROP TABLE country CASCADE CONSTRAINTS;

DROP TABLE genre CASCADE CONSTRAINTS;

DROP TABLE language CASCADE CONSTRAINTS;

DROP TABLE series CASCADE CONSTRAINTS;

DROP TABLE "user" CASCADE CONSTRAINTS;

DROP SEQUENCE author_author_id_seq;

DROP SEQUENCE book_book_id_seq;

DROP SEQUENCE country_country_id_seq;

DROP SEQUENCE genre_genre_id_seq;

DROP SEQUENCE language_language_id_seq;

DROP SEQUENCE series_series_id_seq;

DROP SEQUENCE user_user_id_seq;

-- predefined type, no DDL - MDSYS.SDO_GEOMETRY

-- predefined type, no DDL - XMLTYPE

CREATE SEQUENCE author_author_id_seq START WITH 1 NOCACHE ORDER;

CREATE SEQUENCE book_book_id_seq START WITH 1 NOCACHE ORDER;

CREATE SEQUENCE country_country_id_seq START WITH 1 NOCACHE ORDER;

CREATE SEQUENCE genre_genre_id_seq START WITH 1 NOCACHE ORDER;

CREATE SEQUENCE language_language_id_seq START WITH 1 NOCACHE ORDER;

CREATE SEQUENCE series_series_id_seq START WITH 1 NOCACHE ORDER;

CREATE SEQUENCE user_user_id_seq START WITH 1 NOCACHE ORDER;

CREATE TABLE author (
    author_id  NUMBER NOT NULL,
    first_name VARCHAR2(50) NOT NULL,
    last_name  VARCHAR2(255) NOT NULL,
    birth_year NUMBER NOT NULL,
    death_year NUMBER
)
LOGGING;

ALTER TABLE author ADD CONSTRAINT author_pk PRIMARY KEY ( author_id );

CREATE TABLE book (
    book_id              NUMBER NOT NULL,
    title                VARCHAR2(255) NOT NULL,
    summary              LONG,
    published            DATE,
    pages                NUMBER,
    cover                VARCHAR2(255),
    country_country_id   NUMBER,
    series_series_id     NUMBER NOT NULL,
    language_language_id NUMBER
)
LOGGING;

ALTER TABLE book ADD CONSTRAINT book_pk PRIMARY KEY ( book_id );

CREATE TABLE book_author (
    book_id   NUMBER NOT NULL,
    author_id NUMBER NOT NULL
)
LOGGING;

ALTER TABLE book_author ADD CONSTRAINT book_author_pk PRIMARY KEY ( book_id,
                                                                    author_id );

CREATE TABLE book_genre (
    genre_id NUMBER NOT NULL,
    book_id  NUMBER NOT NULL
)
LOGGING;

ALTER TABLE book_genre ADD CONSTRAINT book_genre_pk PRIMARY KEY ( genre_id,
                                                                  book_id );

CREATE TABLE book_instance (
    book_instance_id NUMBER NOT NULL,
    book_id     NUMBER NOT NULL,
    user_id     NUMBER,
    lend_date        DATE,
    return_date      DATE,
    is_available     NUMBER DEFAULT 1 NOT NULL
)
LOGGING;

ALTER TABLE book_instance ADD CONSTRAINT book_instance_pk PRIMARY KEY ( book_instance_id );

CREATE TABLE country (
    country_id NUMBER NOT NULL,
    name       VARCHAR2(255) NOT NULL
)
LOGGING;

ALTER TABLE country ADD CONSTRAINT country_pk PRIMARY KEY ( country_id );

CREATE TABLE genre (
    genre_id NUMBER NOT NULL,
    name     VARCHAR2(50) NOT NULL
)
LOGGING;

ALTER TABLE genre ADD CONSTRAINT genre_pk PRIMARY KEY ( genre_id );

CREATE TABLE language (
    language_id NUMBER NOT NULL,
    name        VARCHAR2(255) NOT NULL
)
LOGGING;

ALTER TABLE language ADD CONSTRAINT language_pk PRIMARY KEY ( language_id );

CREATE TABLE series (
    series_id NUMBER NOT NULL,
    name      VARCHAR2(255) NOT NULL
)
LOGGING;

ALTER TABLE series ADD CONSTRAINT series_pk PRIMARY KEY ( series_id );

CREATE TABLE "user" (
    user_id    NUMBER NOT NULL,
    first_name VARCHAR2(50) NOT NULL,
    last_name  VARCHAR2(255) NOT NULL,
    login      VARCHAR2(50) NOT NULL,
    password   VARCHAR2(255) NOT NULL,
    salt       VARCHAR2(255) NOT NULL,
    is_admin   CHAR(1),
    is_stuff   CHAR(1)
)
LOGGING;

ALTER TABLE "user" ADD CONSTRAINT user_pk PRIMARY KEY ( user_id );

ALTER TABLE book_author
    ADD CONSTRAINT book_author_author_fk FOREIGN KEY ( author_id )
        REFERENCES author ( author_id )
    NOT DEFERRABLE;

ALTER TABLE book_author
    ADD CONSTRAINT book_author_book_fk FOREIGN KEY ( book_id )
        REFERENCES book ( book_id )
    NOT DEFERRABLE;

ALTER TABLE book
    ADD CONSTRAINT book_country_fk FOREIGN KEY ( country_country_id )
        REFERENCES country ( country_id )
    NOT DEFERRABLE;

ALTER TABLE book_genre
    ADD CONSTRAINT book_genre_book_fk FOREIGN KEY ( book_id )
        REFERENCES book ( book_id )
    NOT DEFERRABLE;

ALTER TABLE book_genre
    ADD CONSTRAINT book_genre_genre_fk FOREIGN KEY ( genre_id )
        REFERENCES genre ( genre_id )
    NOT DEFERRABLE;

ALTER TABLE book_instance
    ADD CONSTRAINT book_instance_book_fk FOREIGN KEY ( book_book_id )
        REFERENCES book ( book_id )
    NOT DEFERRABLE;

ALTER TABLE book_instance
    ADD CONSTRAINT book_instance_user_fk FOREIGN KEY ( user_user_id )
        REFERENCES "user" ( user_id )
    NOT DEFERRABLE;

ALTER TABLE book
    ADD CONSTRAINT book_language_fk FOREIGN KEY ( language_language_id )
        REFERENCES language ( language_id )
    NOT DEFERRABLE;

ALTER TABLE book
    ADD CONSTRAINT book_series_fk FOREIGN KEY ( series_series_id )
        REFERENCES series ( series_id )
    NOT DEFERRABLE;

CREATE OR REPLACE TRIGGER author_author_id_trg BEFORE
    INSERT ON author
    FOR EACH ROW
    WHEN ( new.author_id IS NULL )
BEGIN
    :new.author_id := author_author_id_seq.nextval;
END;
/

CREATE OR REPLACE TRIGGER book_book_id_trg BEFORE
    INSERT ON book
    FOR EACH ROW
    WHEN ( new.book_id IS NULL )
BEGIN
    :new.book_id := book_book_id_seq.nextval;
END;
/

CREATE OR REPLACE TRIGGER country_country_id_trg BEFORE
    INSERT ON country
    FOR EACH ROW
    WHEN ( new.country_id IS NULL )
BEGIN
    :new.country_id := country_country_id_seq.nextval;
END;
/

CREATE OR REPLACE TRIGGER genre_genre_id_trg BEFORE
    INSERT ON genre
    FOR EACH ROW
    WHEN ( new.genre_id IS NULL )
BEGIN
    :new.genre_id := genre_genre_id_seq.nextval;
END;
/

CREATE OR REPLACE TRIGGER language_language_id_trg BEFORE
    INSERT ON language
    FOR EACH ROW
    WHEN ( new.language_id IS NULL )
BEGIN
    :new.language_id := language_language_id_seq.nextval;
END;
/

CREATE OR REPLACE TRIGGER series_series_id_trg BEFORE
    INSERT ON series
    FOR EACH ROW
    WHEN ( new.series_id IS NULL )
BEGIN
    :new.series_id := series_series_id_seq.nextval;
END;
/

CREATE OR REPLACE TRIGGER user_user_id_trg BEFORE
    INSERT ON "user"
    FOR EACH ROW
    WHEN ( new.user_id IS NULL )
BEGIN
    :new.user_id := user_user_id_seq.nextval;
END;
/
