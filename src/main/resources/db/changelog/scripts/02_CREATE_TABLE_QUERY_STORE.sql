CREATE TABLE IF NOT EXISTS storage (id BIGINT NOT NULL AUTO_INCREMENT, cost DOUBLE NOT NULL, tails INTEGER NOT NULL,ingredients_id BIGINT, PRIMARY KEY (id),FOREIGN KEY (ingredients_id) REFERENCES ingredients(id));

