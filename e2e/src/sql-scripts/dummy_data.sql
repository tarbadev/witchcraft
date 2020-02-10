insert into recipe (id, name, origin_url, img_url, favorite, portions)
VALUES (1, 'Lasagna alla bolognese', 'https://recipes.com/lasagna', 'https://recipes.com/lasagna.png', true, 2),
       (2, 'Tartiflette', 'https://recipes.com/tartiflette', 'https://recipes.com/tartiflette.png', false, 2),
       (3, 'Thai Chicken Salad', 'https://www.cookincanuck.com/thai-chicken-salad-recipe/',
        'https://www.cookincanuck.com/wp-content/uploads/2017/09/Thai-Chicken-Salad-Recipe-Cookin-Canuck-3.jpg', false,
        4);

INSERT INTO ingredient (id, name, quantity, unit, recipe_id)
VALUES (1, 'napa cabbage, thinly sliced (about 4 cups)', 0.5, '', 3),
       (2, 'small red cabbage, thinly sliced (about 2 cups)', 0.25, '', 3),
       (3, 'medium carrots, grated (about 1 cup)', 2, '', 3),
       (4, 'green onion, thinly sliced', 3, '', 3),
       (5, 'minced cilantro', 0.25, 'cup', 3),
       (6, 'cooked, shredded chicken breast', 2, 'cup', 3),
       (7, 'slivered almonds, toasted', 3, 'tbsp', 3),
       (8, 'lime, juiced', 1, '', 3),
       (9, 'natural peanut butter', 3, 'tbsp', 3),
       (10, 'low-sodium soy sauce', 2, 'tbsp', 3),
       (11, 'agave nectar (or honey)', 3, 'tsp', 3),
       (12, 'fish sauce', 2, 'tsp', 3),
       (13, 'rice vinegar', 2, 'tsp', 3),
       (14, 'chili garlic sauce', 1, 'tsp', 3);

INSERT INTO step (id, name, recipe_id)
VALUES (1,
        'In a large bowl, combine the Napa cabbage, red cabbage, carrot, green onion, cilantro and chicken breast. Toss with the dressing. Garnish with the toasted almonds. Serve.',
        3),
       (2,
        'In a small glass bowl, combine the lime juice, peanut butter, soy sauce, agave nectar, fish sauce, rice vinegar and chili garlic sauce. Whisk until smooth.',
        3);

INSERT INTO notes (id, comment, recipe_id)
VALUES (1, 'Please add more cheese if needed', 2);

INSERT INTO step_note (id, comment, step_id)
VALUES (1, 'Careful not to break the almonds', 1);

INSERT INTO ml_ingredient (id, name, quantity, unit, detail, line, language, valid)
VALUES (1, 'salt', '2', 'tsp', 'to taste', '2 teaspoons Salt', 'ENGLISH', false),
       (2, 'lait', '350', 'cl', '', '350cl de Lait', 'FRENCH', false),
       (3, 'chicken breast', '12', 'oz', 'boneless skinless', '12 oz Boneless Skinless Chicken Breast', 'ENGLISH', true)