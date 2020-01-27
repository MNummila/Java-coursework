/*Tietokannan luominen*/
CREATE TABLE "paketti" (
	"luokka" INTEGER PRIMARY KEY,
	"koko" INTEGER NOT NULL,
	"paino" INTEGER NOT NULL,
	"muuta" VARCHAR(100) DEFAULT 'ei',
	"kantamakm" VARCHAR(10) DEFAULT '1000',

	CHECK("koko" > 0),
	CHECK("paino" > 0),
	CHECK("luokka" IN (1,2,3))
);

CREATE TABLE "esineet" (
	"esineID" INTEGER PRIMARY KEY,
	"nimi" VARCHAR(30) NOT NULL,
	"koko" INTEGER NOT NULL,
	"paino" INTEGER NOT NULL,
	"hajoaa" BOOLEAN NOT NULL,
	
	CHECK("esineID" > 0),
	CHECK("koko" > 0),
	CHECK("paino" > 0)
);

CREATE TABLE "varasto" (
	"varastoID" INTEGER PRIMARY KEY,
	"varastointiaika" DATETIME NOT NULL,

	CHECK("varastoID" > 0)
);

CREATE TABLE "osoitteet" (
	"postinumero" VARCHAR(5) PRIMARY KEY,
	"kaupunki" VARCHAR(30) NOT NULL
);

CREATE TABLE "luodutpaketit" (
	"pakettiID" INTEGER PRIMARY KEY,
	"esineID" INTEGER NOT NULL, 
	"varastoID" INTEGER,
	"luokka" INTEGER NOT NULL,
	"lahtopaikka" VARCHAR(60) NOT NULL,
	"maaranpaa" VARCHAR(60) NOT NULL,
	"matka" VARCHAR(10) NOT NULL,

	CHECK("pakettiID" > 0),
	
	FOREIGN KEY("esineID") REFERENCES "esineet" ("esineID"),
	FOREIGN KEY("luokka") REFERENCES "paketti" ("luokka"),
	FOREIGN KEY("varastoID") REFERENCES "varasto" ("varastoID")
);

CREATE TABLE "smartpost" (
	"automaattiID" INTEGER PRIMARY KEY,
	"postinumero" VARCHAR(5) NOT NULL,
	"aukioloajat" VARCHAR(60) NOT NULL,
	"postitoimisto" VARCHAR(60) NOT NULL,
	"katuosoite" VARCHAR(30) NOT NULL,
	"latitude" CHAR(10) NOT NULL,
	"longitude" CHAR(10) NOT NULL,
	"luotu_ei" BOOLEAN NOT NULL,

	CHECK("automaattiID" > 0),
	CHECK("osoiteID" > 0),

	FOREIGN KEY("postinumero") REFERENCES "osoitteet" ("postinumero") ON DELETE CASCADE
);

CREATE TABLE "automaatissaolevatpaketit" (
		"automaattiID" INTEGER ,
		"pakettiID" INTEGER ,
		"saapumisaika" DATETIME NOT NULL,
		
		PRIMARY KEY("automaattiID","pakettiID"),
		FOREIGN KEY("pakettiID") REFERENCES "paketti" ("pakettiID"),
		FOREIGN KEY("automaattiID") REFERENCES "smartpost" ("automaattiID") 
);