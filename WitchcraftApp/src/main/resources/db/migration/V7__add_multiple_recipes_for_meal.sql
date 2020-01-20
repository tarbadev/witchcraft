CREATE TABLE meal
(
    id        int(11)     NOT NULL AUTO_INCREMENT,
    meal_type varchar(20) NOT NULL,
    day_id    int(11),
    recipe_id int(11),
    PRIMARY KEY (id),
    FOREIGN KEY (day_id) REFERENCES `Day` (id),
    FOREIGN KEY (recipe_id) REFERENCES `Recipe` (id)
) ENGINE = MyISAM
  DEFAULT CHARSET = UTF8MB4;

INSERT INTO meal (meal_type, day_id, recipe_id)
SELECT 'LUNCH', id AS day_id, lunch_recipe_id AS recipe_id
FROM day
WHERE lunch_recipe_id IS NOT NULL;

insert into meal (meal_type, day_id, recipe_id)
SELECT 'DINER', id AS day_id, diner_recipe_id AS recipe_id
FROM day
WHERE diner_recipe_id IS NOT NULL;

ALTER TABLE day
    DROP COLUMN lunch_recipe_id;

ALTER TABLE day
    DROP COLUMN diner_recipe_id;