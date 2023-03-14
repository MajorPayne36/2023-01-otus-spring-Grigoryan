DROP TABLE IF EXISTS books;
CREATE TABLE IF NOT EXISTS books
(
    id               bigint primary key not null,
    name             varchar            not null,
    publication_date date               not null
);

DROP TABLE IF EXISTS authors;
CREATE TABLE IF NOT EXISTS authors
(
    id          bigint primary key not null,
    first_name  varchar            not null,
    last_name   varchar            not null,
    birthday    date
);

DROP TABLE IF EXISTS genres;
CREATE TABLE IF NOT EXISTS genres
(
    id   bigint primary key not null,
    name varchar            not null
);

DROP TABLE IF EXISTS books_id_authors_id;
CREATE TABLE IF NOT EXISTS books_id_authors_id
(
    book_id   bigint REFERENCES books (id)   not null,
    author_id bigint REFERENCES authors (id) not null,
    CONSTRAINT books_id_authors_id_pk
        PRIMARY KEY (book_id, author_id)
);

DROP TABLE IF EXISTS books_id_genres_id;
CREATE TABLE IF NOT EXISTS books_id_genres_id
(
    book_id  bigint REFERENCES books (id)  not null,
    genre_id bigint REFERENCES genres (id) not null,
    CONSTRAINT books_id_genres_id_pk
        PRIMARY KEY (book_id, genre_id)
);
