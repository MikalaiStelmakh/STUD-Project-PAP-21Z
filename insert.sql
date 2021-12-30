INSERT INTO DSAVYTSK."user" (USER_ID,FIRST_NAME,LAST_NAME,LOGIN,PASSWORD,SALT,IS_ADMIN,IS_STUFF) VALUES ('0','admin','admin','admin','admin','asd','1','1');
INSERT INTO DSAVYTSK."user" (USER_ID,FIRST_NAME,LAST_NAME,LOGIN,PASSWORD,SALT,IS_ADMIN,IS_STUFF) VALUES ('1','user','user','user','user','zxc','0','0');

INSERT INTO GENRE VALUES(0, 'Sci-fi');
INSERT INTO GENRE VALUES(0, 'Fantasy');
INSERT INTO GENRE VALUES(0, 'Romance');
INSERT INTO GENRE VALUES(4, 'Shakespearean tragedy');

INSERT INTO COUNTRY VALUES(0, 'United States');
INSERT INTO COUNTRY VALUES(0, 'United Kingdom');

INSERT INTO SERIES (SERIES_ID,NAME) VALUES ('0','Dune Chronicles');
INSERT INTO SERIES (SERIES_ID,NAME) VALUES ('1','Harry Potter');
INSERT INTO SERIES (SERIES_ID,NAME) VALUES ('2','The Madaris Family and Friends series');
INSERT INTO SERIES (SERIES_ID,NAME) VALUES ('3','First Quarto');

INSERT INTO LANGUAGE VALUES(0, 'ENGLISH');

INSERT INTO BOOK (BOOK_ID,TITLE,SUMMARY,PUBLICATION_YEAR,PAGES,COVER,COUNTRY_ID,SERIES_ID,LANGUAGE_ID) VALUES ('0','DUNE','In 10191, Duke Leto of House Atreides, ruler of the ocean planet Caladan, is assigned by the Padishah Emperor Shaddam Corrino IV to replace House Harkonnen as fief rulersL of Arrakis. Arrakis is a harsh desert planet and the only source of ''spice'', a valuable substance that bestows its users heightened vitality and expanded consciousness. It is critical for interstellar travel as it allows Spacing Guild Navigators to safely and instantaneously fold space. In reality, Shaddam intends to have House Harkonnen stage a coup to retake the planet with aid of the Emperor''s Sardaukar troops, eradicating House Atreides, whose influence threatens Shaddam''s control. Leto is apprehensive but sees the political advantages of controlling the spice planet and forming an alliance with its native population, skilled fighters known as the Fremen.','1965','412','FrankHerbert_Dune.jpg','0','0','0');
INSERT INTO BOOK (BOOK_ID,TITLE,SUMMARY,PUBLICATION_YEAR,PAGES,COVER,COUNTRY_ID,SERIES_ID,LANGUAGE_ID) VALUES ('1','Harry Potter and the Philosopher''s Stone','The life of ten-year-old Harry Potter cannot be called sweet: his parents died as soon as he was one year old, and from his uncle and aunt who took the orphan into foster care, they only get jabs and cuffs. But on Harry''s eleventh birthday, everything changes. A strange guest who unexpectedly appears on the doorstep brings a letter from which the boy learns that in fact he is a purebred wizard and accepted into Hogwarts - a school of magic. And in a couple of weeks Harry will be racing on the Hogwarts Express train towards a new life, where incredible adventures, loyal friends and, most importantly, the key to unraveling the mystery of his parents'' death await him.','1997','223','Harry_Potter_and_the_Philosopher''s_Stone_Book_Cover.jpg','1','1','0');
INSERT INTO BOOK (BOOK_ID,TITLE,SUMMARY,PUBLICATION_YEAR,PAGES,COVER,COUNTRY_ID,SERIES_ID,LANGUAGE_ID) VALUES ('2','Harry Potter and the Chamber of Secrets','While spending the summer at the Dursleys, twelve-year-old Harry is visited by a house-elf named Dobby. He warns that Harry is in danger and must not return to Hogwarts. Harry refuses, so Dobby magically ruins Aunt Petunia and Uncle Vernon''s dinner party. A furious Uncle Vernon locks Harry into his room in retaliation. The Ministry of Magic immediately sends a notice accusing Harry of performing underage magic and threatening dismissal from Hogwarts.','1998','251','220px-Harry_Potter_and_the_Chamber_of_Secrets.jpg','1','1','0');
INSERT INTO BOOK (BOOK_ID,TITLE,SUMMARY,PUBLICATION_YEAR,PAGES,COVER,COUNTRY_ID,SERIES_ID,LANGUAGE_ID) VALUES ('3','Whispered Promises','Called to her father''s deathbed, Caitlin is shocked that her former husband, Dex Madaris, has also been summoned. It''s been years since they''ve seen each other. Despite the past, Caitlin and Dex cannot resist their attraction, or fight the hunger to renew a passion they thought they''d lost forever.','1996','320','whispered-promises.jpg','0','2','0');
INSERT INTO BOOK (BOOK_ID,TITLE,SUMMARY,PUBLICATION_YEAR,PAGES,COVER,COUNTRY_ID,SERIES_ID,LANGUAGE_ID) VALUES ('4','Romeo and Juliet','An age-old vendetta between two powerful families erupts into bloodshed. A group of masked Montagues risk further conflict by gatecrashing a Capulet party. A young lovesick Romeo Montague falls instantly in love with Juliet Capulet, who is due to marry her father’s choice, the County Paris.','1597','480','romeo-and-juliet.jpg','1','3','0');

INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('0','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('1','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('2','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('3','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('4','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('5','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('6','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('7','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('8','0',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('9','1',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('10','1',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('11','1',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('12','2',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('13','2',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('14','2',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('15','3',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('16','3',null,null,null);
INSERT INTO BOOK_INSTANCE (BOOK_INSTANCE_ID,BOOK_ID,USER_ID,LEND_DATE,RETURN_DATE) VALUES ('17','4',null,null,null);

INSERT INTO AUTHOR (AUTHOR_ID,FIRST_NAME,LAST_NAME,BIRTH_YEAR,DEATH_YEAR) VALUES ('0','Frank','Herbert','1920','1986');
INSERT INTO AUTHOR (AUTHOR_ID,FIRST_NAME,LAST_NAME,BIRTH_YEAR,DEATH_YEAR) VALUES ('1','Brian','Herbert','1947',null);
INSERT INTO AUTHOR (AUTHOR_ID,FIRST_NAME,LAST_NAME,BIRTH_YEAR,DEATH_YEAR) VALUES ('2','Joanne','Rowling','1965',null);
INSERT INTO AUTHOR (AUTHOR_ID,FIRST_NAME,LAST_NAME,BIRTH_YEAR,DEATH_YEAR) VALUES ('3','Brenda ','Jackson','1953',null);
INSERT INTO AUTHOR (AUTHOR_ID,FIRST_NAME,LAST_NAME,BIRTH_YEAR,DEATH_YEAR) VALUES ('4','William ','Shakespeare','1564','1616');

INSERT INTO BOOK_AUTHOR VALUES(0, 0);
INSERT INTO BOOK_AUTHOR VALUES(1, 1);
INSERT INTO BOOK_AUTHOR VALUES(2, 1);
INSERT INTO BOOK_AUTHOR VALUES(3, 3);

INSERT INTO BOOK_GENRE VALUES(0, 0);
INSERT INTO BOOK_GENRE VALUES(1, 1);
INSERT INTO BOOK_GENRE VALUES(1, 2);
