CREATE TABLE "BD"(
  "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "name" VARCHAR(45) NOT NULL,
  "date" DATETIME NOT NULL,
  "isbn" VARCHAR(45) NOT NULL,
  "language" VARCHAR(45) NOT NULL
);
CREATE TABLE "Author"(
  "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "name" VARCHAR(45) NOT NULL
);
CREATE TABLE "Collection"(
  "_id" INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
  "name" VARCHAR(45) NOT NULL
);
CREATE TABLE "shared_BD"(
  "BD_id" INTEGER PRIMARY KEY NOT NULL,
  "contact" VARCHAR(45) NOT NULL,
  CONSTRAINT "fk_shared_BD_BD"
    FOREIGN KEY("BD_id")
    REFERENCES "BD"("_id")
);
CREATE INDEX "shared_BD.fk_shared_BD_BD_idx" ON "shared_BD"("BD_id");
CREATE TABLE "BD_has_Author"(
  "BD_id" INTEGER NOT NULL,
  "Author_id" INTEGER NOT NULL,
  PRIMARY KEY("BD_id","Author_id"),
  CONSTRAINT "fk_BD_has_Author_BD1"
    FOREIGN KEY("BD_id")
    REFERENCES "BD"("_id"),
  CONSTRAINT "fk_BD_has_Author_Author1"
    FOREIGN KEY("Author_id")
    REFERENCES "Author"("_id")
);
CREATE INDEX "BD_has_Author.fk_BD_has_Author_Author1_idx" ON "BD_has_Author"("Author_id");
CREATE INDEX "BD_has_Author.fk_BD_has_Author_BD1_idx" ON "BD_has_Author"("BD_id");
CREATE TABLE "Collection_Has_BD"(
  "BD_id" INTEGER NOT NULL,
  "Collection_id" INTEGER NOT NULL,
  "order" INTEGER NOT NULL,
  PRIMARY KEY("Collection_id","BD_id"),
  CONSTRAINT "fk_Serie_Has_BD_BD1"
    FOREIGN KEY("BD_id")
    REFERENCES "BD"("_id"),
  CONSTRAINT "fk_Serie_Has_BD_Serie1"
    FOREIGN KEY("Collection_id")
    REFERENCES "Collection"("_id")
);
CREATE INDEX "Collection_Has_BD.fk_Serie_Has_BD_BD1_idx" ON "Collection_Has_BD"("BD_id");
CREATE INDEX "Collection_Has_BD.fk_Serie_Has_BD_Serie1_idx" ON "Collection_Has_BD"("Collection_id");
CREATE TABLE "Bought_Comics"(
  "BD__id" INTEGER PRIMARY KEY NOT NULL,
  "date" DATE NOT NULL,
  CONSTRAINT "fk_Bought_Comics_BD1"
    FOREIGN KEY("BD__id")
    REFERENCES "BD"("_id")
);
CREATE INDEX "Bought_Comics.fk_Bought_Comics_BD1_idx" ON "Bought_Comics"("BD__id");
CREATE TABLE "Watchlist"(
  "BD__id" INTEGER PRIMARY KEY NOT NULL,
  "date" VARCHAR(45) NOT NULL,
  CONSTRAINT "fk_Watchlist_BD1"
    FOREIGN KEY("BD__id")
    REFERENCES "BD"("_id")
);
CREATE INDEX "Watchlist.fk_Watchlist_BD1_idx" ON "Watchlist"("BD__id");