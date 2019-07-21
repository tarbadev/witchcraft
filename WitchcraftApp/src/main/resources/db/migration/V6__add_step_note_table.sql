CREATE TABLE step_note (
  id        int(11)      NOT NULL AUTO_INCREMENT,
  comment   varchar(500) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;

ALTER TABLE step
    add column note_id int(11) DEFAULT NULL;

ALTER TABLE step ADD CONSTRAINT fk_note_id FOREIGN KEY (note_id) REFERENCES step_note(id);
