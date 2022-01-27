-- get books
SELECT book.book_id, book.title, book.summary, book.publication_year, book.date_added, 
                book.pages, book.cover, country.name country, series.name series, language.name language FROM book
                JOIN country ON (book.country_id = country.country_id)
                JOIN series ON (book.series_id = series.series_id)
                JOIN language ON (book.language_id = language.language_id) ORDER BY book.date_added DESC;
                
-- get book by book_id    
SELECT book.book_id, book.title, book.summary, book.publication_year, book.date_added,
                book.pages, book.cover, country.name country, series.name series, language.name language FROM book
                LEFT JOIN country ON (book.country_id = country.country_id)
                LEFT JOIN series ON (book.series_id = series.series_id)
                LEFT JOIN language ON (book.language_id = language.language_id)
                WHERE book.book_id = 5;
                
-- get genres
select * from genre order by name asc;

-- get genre by genre_id
SELECT genre.name FROM genre WHERE genre_id = 1;

-- get genre by name
SELECT genre.name FROM genre WHERE name = 'Sci-fi';

-- get book genres by book id
SELECT genre.genre_id, genre.name 
                FROM genre
                JOIN book_genre ON (genre.genre_id = book_genre.genre_id)
                JOIN book ON (book.book_id = book_genre.book_id)
                WHERE book.book_id = 5;
                
-- get books by genre
SELECT book_id, title, summary, publication_year, 
                date_added, pages, cover, c.name, s.name, l.name
                FROM book join country c USING(country_id)
                JOIN series s using(series_id) JOIN language l using(language_id)
                JOIN book_genre using (book_id) JOIN genre using(genre_id) where genre_id = 4;
                
                
-- get book instances by book id
select book_instance_id, book_id, user_id, s.name 
                from book_instance bk join status s using(status_id)
                where book_id = 4;
                
-- get book instance by book instance id
select * from book_instance where book_instance_id = 14;

-- get books in same series
SELECT b2.book_id, b2.title, b2.summary, b2.publication_year, b2.date_added,
                b2.pages, b2.cover, c.name country, s.name series, l.name language
                from book b1 join book b2 on(b1.series_id = b2.series_id)
                join country c on (b2.country_id = c.country_id)
                join series s on (b2.series_id = s.series_id)
                join language l on (b2.language_id = l.language_id)
                where (b1.book_id = 5);
                
-- get authors
select author_id, first_name, last_name, birth_year,
                nvl(death_year, 0), biography, photo 
                from author order by last_name asc;

-- get author by author_id
select * from author where author_id = 20;

-- get book authors
SELECT author.* FROM  author
                JOIN book_author ON (author.author_id = book_author.author_id)
                JOIN book ON (book.book_id = book_author.book_id)
                WHERE book.book_id = 5;


-- get author books by author_id
select book_id, title, summary, publication_year, 
                date_added, pages, cover, c.name, s.name, l.name
                from book join country c using(country_id)
                join series s using(series_id) join language l using(language_id)
                join book_author using (book_id)
                join author using (author_id) where author_id = 2;

-- get author genres by author_id
select distinct genre_id, g.name
                from genre g join book_genre using (genre_id)
                join book_author using (book_id)
                join author using(author_id)
                where author_id = 5;

-- get users
select user_id, u.name, surname, login, password, p.name from users u join permissions p using(permission_id);

-- get user by login
select user_id, u.name, surname, login, password, p.name
                from users u join permissions p using(permission_id) where u.login = 'root';
                
-- get series
select * from series where series_id = 2;

-- get book instance status
SELECT status.name FROM book_instance
                JOIN status using(status_id)
                WHERE book_instance.book_instance_id = 7;
                
-- get country by country_id
SELECT country_id from country WHERE country_id = 1;

-- get country id by name
SELECT country_id from country WHERE name = 'United States';

-- get country name by country_id
SELECT name from country WHERE country_id = 0;

-- get language id by name
SELECT language_id from language WHERE name = 'English';

-- get language by language_id
SELECT name from language WHERE language_id = 0;


-- login uniqueness check
SELECT count(*) FROM users WHERE login = 'q';

-- add and del genre
INSERT INTO genre VALUES(18, 'gh');
DELETE FROM genre WHERE genre_id = 18;

-- add and del series
INSERT INTO SERIES VALUES(20, 'ghgh');
DELETE FROM SERIES WHERE series_id = 20;

-- add and del language
INSERT INTO language VALUES(5, 'G');
DELETE FROM language WHERE language_id = 5;

-- add and dell country
INSERT INTO country VALUES(null, 'Germany');
DELETE FROM country WHERE name = 'Germany';




                


                


