DROP TABLE IF EXISTS `books`;
DROP TABLE IF EXISTS `authors`;

CREATE TABLE `authors` (
    `id` INTEGER PRIMARY KEY AUTO_INCREMENT,
    `name` VARCHAR(20),
    `age` INTEGER
);

CREATE TABLE `books` (
    `isbn` VARCHAR(255) PRIMARY KEY,
    `title` VARCHAR(255),
    `author_id` INTEGER,
    FOREIGN KEY (author_id) REFERENCES authors(id)
);
