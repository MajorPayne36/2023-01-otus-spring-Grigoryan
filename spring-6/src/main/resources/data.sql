INSERT INTO books (id, name, publication_date)
VALUES (NEXTVAL('book_seq'), 'book 1', '2023-02-11'),
       (NEXTVAL('book_seq'), 'book 2', '2023-02-12'),
       (NEXTVAL('book_seq'), 'book 3', '2023-03-13'),
       (NEXTVAL('book_seq'), 'book 4', '2023-04-14'),
       (NEXTVAL('book_seq'), 'book 5', '2023-05-15');

INSERT INTO authors (id, first_name, last_name, birthday)
VALUES (NEXTVAL('author_seq'), 'Andranik', 'Grigoryan', '2000-10-29'),
       (NEXTVAL('author_seq'), 'Vasya', 'Vasin', '2002-10-29'),
       (NEXTVAL('author_seq'), 'Ivan', 'Fomich', '2003-10-29'),
       (NEXTVAL('author_seq'), 'Ivan', 'Kuzmich', '2004-10-29');

INSERT INTO genres (id, name)
VALUES (NEXTVAL('genre_seq'), 'Komedia'),
       (NEXTVAL('genre_seq'), 'Roman'),
       (NEXTVAL('genre_seq'), 'Detective'),
       (NEXTVAL('genre_seq'), 'Prikluchenie');

INSERT INTO books_id_authors_id (book_id, author_id)
VALUES ((SELECT b.id FROM books b WHERE b.name = 'book 1'), (SELECT a.id FROM authors a WHERE a.last_name = 'Grigoryan')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 2'), (SELECT a.id FROM authors a WHERE a.last_name = 'Vasin')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 3'), (SELECT a.id FROM authors a WHERE a.last_name = 'Fomich')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 4'), (SELECT a.id FROM authors a WHERE a.last_name = 'Kuzmich')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 5'), (SELECT a.id FROM authors a WHERE a.last_name = 'Grigoryan')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 1'), (SELECT a.id FROM authors a WHERE a.last_name = 'Vasin')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 2'), (SELECT a.id FROM authors a WHERE a.last_name = 'Fomich')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 3'), (SELECT a.id FROM authors a WHERE a.last_name = 'Kuzmich')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 4'), (SELECT a.id FROM authors a WHERE a.last_name = 'Grigoryan')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 5'), (SELECT a.id FROM authors a WHERE a.last_name = 'Vasin'));

INSERT INTO books_id_genres_id (book_id, genre_id)
VALUES ((SELECT b.id FROM books b WHERE b.name = 'book 1'), (SELECT g.id FROM genres g WHERE g.name = 'Komedia')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 2'), (SELECT g.id FROM genres g WHERE g.name = 'Roman')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 3'), (SELECT g.id FROM genres g WHERE g.name = 'Detective')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 4'), (SELECT g.id FROM genres g WHERE g.name = 'Prikluchenie')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 5'), (SELECT g.id FROM genres g WHERE g.name = 'Komedia')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 1'), (SELECT g.id FROM genres g WHERE g.name = 'Roman')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 2'), (SELECT g.id FROM genres g WHERE g.name = 'Detective')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 3'), (SELECT g.id FROM genres g WHERE g.name = 'Prikluchenie')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 4'), (SELECT g.id FROM genres g WHERE g.name = 'Komedia')),
       ((SELECT b.id FROM books b WHERE b.name = 'book 5'), (SELECT g.id FROM genres g WHERE g.name = 'Roman'));
