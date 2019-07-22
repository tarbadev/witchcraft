CREATE TABLE step_note (
  id        int(11)      NOT NULL AUTO_INCREMENT,
  comment   varchar(500) NOT NULL,
  step_id int(11),
  PRIMARY KEY (id),
  FOREIGN KEY (step_id) REFERENCES `Step`(id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;