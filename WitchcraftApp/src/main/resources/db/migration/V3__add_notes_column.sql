CREATE TABLE notes (
  id        int(11)      NOT NULL AUTO_INCREMENT,
  comment   varchar(500) NOT NULL,
  recipe_id int(11)      NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (recipe_id) REFERENCES `Recipe`(id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;