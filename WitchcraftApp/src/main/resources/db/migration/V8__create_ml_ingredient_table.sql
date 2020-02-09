CREATE TABLE ml_ingredient
(
    id       int(11)      NOT NULL AUTO_INCREMENT,
    name     varchar(255) NOT NULL,
    quantity double,
    unit     varchar(20)  NOT NULL,
    line     varchar(255) NOT NULL,
    language varchar(255) NOT NULL,
    valid    boolean,
    PRIMARY KEY (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = UTF8MB4;

INSERT INTO ml_ingredient(name, quantity, unit, line, language, valid)
SELECT LOWER(name),
       quantity,
       unit,
       CONCAT(quantity, ' ', unit, ' ', name),
       'ENGLISH' as language,
       false as valid
FROM ingredient;