--
-- Table structure for table `cart`
--
CREATE TABLE cart (
  id int(11) NOT NULL AUTO_INCREMENT,
  created_at datetime DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;

--
-- Table structure for table `cart_recipe`
--
CREATE TABLE cart_recipe (
  cart_id int(11) NOT NULL,
  recipe_id int(11),
  PRIMARY KEY (cart_id,recipe_id),
  FOREIGN KEY (recipe_id) REFERENCES Recipe(id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;

--
-- Table structure for table `day`
--
CREATE TABLE day (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  diner_recipe_id int(11),
  lunch_recipe_id int(11),
  week_id int(11),
  PRIMARY KEY (id),
  FOREIGN KEY (diner_recipe_id) REFERENCES Recipe(id),
  FOREIGN KEY (lunch_recipe_id) REFERENCES Recipe(id),
  FOREIGN KEY (week_id) REFERENCES Week(id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;

--
-- Table structure for table `ingredient`
--
CREATE TABLE ingredient (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  quantity double NOT NULL,
  unit varchar(255) DEFAULT NULL,
  recipe_id int(11),
  PRIMARY KEY (id),
  FOREIGN KEY (recipe_id) REFERENCES Recipe(id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;

--
-- Table structure for table `item`
--
CREATE TABLE item (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(255) DEFAULT NULL,
  quantity double NOT NULL,
  unit varchar(255) DEFAULT NULL,
  cart_id int(11),
  PRIMARY KEY (id),
  FOREIGN KEY (cart_id) REFERENCES Cart(id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;

--
-- Table structure for table `recipe`
--
CREATE TABLE recipe (
  id int(11) NOT NULL AUTO_INCREMENT,
  favorite bit(1) NOT NULL,
  img_url varchar(255) DEFAULT NULL,
  name varchar(255) DEFAULT NULL,
  origin_url varchar(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;

--
-- Table structure for table `step`
--
CREATE TABLE step (
  id int(11) NOT NULL AUTO_INCREMENT,
  name varchar(1000) DEFAULT NULL,
  recipe_id int(11),
  PRIMARY KEY (id),
  FOREIGN KEY (recipe_id) REFERENCES Recipe(id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;

--
-- Table structure for table `weeks`
--
CREATE TABLE week (
  id int(11) NOT NULL AUTO_INCREMENT,
  week_number int(11) NOT NULL,
  year int(11) NOT NULL,
  PRIMARY KEY (id)
) ENGINE=MyISAM DEFAULT CHARSET=UTF8MB4;
