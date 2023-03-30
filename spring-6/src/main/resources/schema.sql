DROP TABLE IF EXISTS books_id_genres_id;
DROP TABLE IF EXISTS books_id_authors_id;
DROP TABLE IF EXISTS authors;
DROP TABLE IF EXISTS books;
DROP TABLE IF EXISTS genres;
DROP SEQUENCE IF EXISTS author_seq;
DROP SEQUENCE IF EXISTS genre_seq;
DROP SEQUENCE IF EXISTS book_seq;

CREATE TABLE IF NOT EXISTS books
(
    id               bigint primary key not null,
    name             varchar            not null,
    publication_date date               not null
);

CREATE TABLE IF NOT EXISTS authors
(
    id          bigint primary key not null,
    first_name  varchar            not null,
    last_name   varchar            not null,
    birthday    date
);

CREATE TABLE IF NOT EXISTS genres
(
    id   bigint primary key not null,
    name varchar            not null
);

CREATE TABLE IF NOT EXISTS books_id_genres_id
(
    book_id  bigint REFERENCES books (id)  not null,
    genre_id bigint REFERENCES genres (id) not null,
    CONSTRAINT books_id_genres_id_pk
        PRIMARY KEY (book_id, genre_id)
);

CREATE TABLE IF NOT EXISTS books_id_authors_id
(
    book_id   bigint REFERENCES books (id)   not null,
    author_id bigint REFERENCES authors (id) not null,
    CONSTRAINT books_id_authors_id_pk
    PRIMARY KEY (book_id, author_id)
);

CREATE SEQUENCE IF NOT EXISTS author_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS genre_seq START WITH 1;
CREATE SEQUENCE IF NOT EXISTS book_seq START WITH 1;