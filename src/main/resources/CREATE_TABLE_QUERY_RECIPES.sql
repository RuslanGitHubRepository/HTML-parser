DROP TABLE IF EXISTS recipes;
CREATE TABLE IF NOT EXISTS recipes (id INTEGER NOT NULL AUTO_INCREMENT, nameRecipes VARCHAR(255), PRIMARY KEY (id), FOREIGN KEY (recipe_id) REFERENCES recipe(id));