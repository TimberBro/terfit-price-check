CREATE TABLE IF NOT EXISTS price_changelog
(
    id INTEGER PRIMARY KEY NOT NULL AUTO_INCREMENT,
    club_id INTEGER NOT NULL,
    create_date DATE NOT NULL,
    card_name VARCHAR(30) NOT NULL,
    card_price INTEGER NOT NULL
    );