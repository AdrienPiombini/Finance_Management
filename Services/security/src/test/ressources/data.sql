CREATE TABLE "Users" (
    "id" INT PRIMARY KEY AUTO_INCREMENT,
    "username" VARCHAR(255),
    "password" VARCHAR(255),
    "role" VARCHAR(255),
    "created_at" TIMESTAMP,
    "updated_at" TIMESTAMP
);
