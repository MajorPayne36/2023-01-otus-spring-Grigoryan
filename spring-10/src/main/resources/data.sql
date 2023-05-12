INSERT INTO books (name, publication_date)
VALUES ('book 1', '2023-02-11'),
       ('book 2', '2023-02-12'),
       ('book 3', '2023-03-13'),
       ('book 4', '2023-04-14'),
       ('book 5', '2023-05-15');

INSERT INTO authors (first_name, last_name, birthday)
VALUES ('Andranik', 'Grigoryan', '2000-10-29'),
       ('Vasya', 'Vasin', '2002-10-29'),
       ('Ivan', 'Fomich', '2003-10-29'),
       ('Ivan', 'Kuzmich', '2004-10-29');

INSERT INTO genres (name)
VALUES ('Komedia'),
       ('Roman'),
       ('Detective'),
       ('Prikluchenie');

INSERT INTO comments (content, book_id)
VALUES ('comment 1', (SELECT b.id FROM books b WHERE b.name = 'book 1')),
       ('comment 2', (SELECT b.id FROM books b WHERE b.name = 'book 2')),
       ('comment 3', (SELECT b.id FROM books b WHERE b.name = 'book 3')),
       ('comment 4', (SELECT b.id FROM books b WHERE b.name = 'book 4')),
       ('comment 5', (SELECT b.id FROM books b WHERE b.name = 'book 5')),
       ('comment 1', (SELECT b.id FROM books b WHERE b.name = 'book 1'));

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
