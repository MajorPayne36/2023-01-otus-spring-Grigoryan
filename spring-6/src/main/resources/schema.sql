DROP TABLE IF EXISTS books_id_genres_id;
DROP TABLE IF EXISTS books_id_authors_id;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS genres;

DROP SEQUENCE IF EXISTS author_seq;
DROP SEQUENCE IF EXISTS genre_seq;
DROP SEQUENCE IF EXISTS book_seq;

CREATE SEQUENCE IF NOT EXISTS author_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS genre_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS comment_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS book_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS books
(
    id               bigint PRIMARY KEY NOT NULL DEFAULT NEXTVAL('BOOK_SEQ'),
    name             varchar            NOT NULL,
    publication_date date               NOT NULL
);

CREATE TABLE IF NOT EXISTS authors
(
    id         bigint PRIMARY KEY NOT NULL DEFAULT NEXTVAL('AUTHOR_SEQ'),
    first_name varchar            NOT NULL,
    last_name  varchar            NOT NULL,
    birthday   date
);

CREATE TABLE IF NOT EXISTS genres
(
    id   bigint PRIMARY KEY NOT NULL DEFAULT NEXTVAL('GENRE_SEQ'),
    name varchar            NOT NULL
);

CREATE TABLE IF NOT EXISTS comments
(
    id      bigint PRIMARY KEY           NOT NULL DEFAULT NEXTVAL('COMMENT_SEQ'),
    content varchar                      NOT NULL,
    book_id bigint REFERENCES books (id) NOT NULL
);

CREATE TABLE IF NOT EXISTS books_id_genres_id
(
    book_id  bigint REFERENCES books (id)  NOT NULL,
    genre_id bigint REFERENCES genres (id) NOT NULL,
    CONSTRAINT books_id_genres_id_pk
        PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE IF NOT EXISTS books_id_authors_id
(
    book_id   bigint REFERENCES books (id)   NOT NULL,
    author_id bigint REFERENCES authors (id) NOT NULL,
    CONSTRAINT books_id_authors_id_pk
        PRIMARY KEY (book_id, author_id)
);