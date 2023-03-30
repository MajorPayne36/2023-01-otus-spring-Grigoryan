INSERT INTO books (id, name, publication_date)
VALUES (1, 'book 1', '2023-02-11'),
       (2, 'book 2', '2023-02-12'),
       (3, 'book 3', '2023-03-13'),
       (4, 'book 4', '2023-04-14'),
       (5, 'book 5', '2023-05-15');

INSERT INTO authors (id, first_name, last_name, birthday)
VALUES (1, 'Andranik', 'Grigoryan', '2000-10-29'),
       (2, 'Vasya', 'Vasin', '2002-10-29'),
       (3, 'Ivan', 'Fomich', '2003-10-29'),
       (4, 'Ivan', 'Kuzmich', '2004-10-29');

INSERT INTO genres (id, name)
VALUES (1, 'Komedia'),
       (2, 'Roman'),
       (3, 'Detective'),
       (4, 'Prikluchenie');

INSERT INTO books_id_authors_id (book_id, author_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (4, 1),
       (5, 2);

INSERT INTO books_id_genres_id (book_id, genre_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 1),
       (1, 2),
       (2, 3),
       (3, 4),
       (4, 1),
       (5, 2);
