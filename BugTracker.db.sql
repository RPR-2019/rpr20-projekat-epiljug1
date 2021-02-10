BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "connections" (
	"pr_id"	INTEGER,
	"de_id"	INTEGER,
	FOREIGN KEY("de_id") REFERENCES "developer"("developer_id"),
	FOREIGN KEY("pr_id") REFERENCES "project"("project_id")
);
CREATE TABLE IF NOT EXISTS "developer" (
	"developer_id"	INTEGER,
	"ime"	TEXT,
	"prezime"	TEXT,
	"email"	TEXT UNIQUE,
	"username"	TEXT UNIQUE,
	"password"	TEXT,
	PRIMARY KEY("developer_id")
);
CREATE TABLE IF NOT EXISTS "bug" (
	"bug_id"	INTEGER,
	"bug_name"	TEXT,
	"bug_type"	TEXT,
	"status"	TEXT,
	"date_created"	TEXT,
	"projectID"	INTEGER,
	"complexity"	TEXT,
	PRIMARY KEY("bug_id"),
	FOREIGN KEY("projectID") REFERENCES "project"("project_id")
);
CREATE TABLE IF NOT EXISTS "project" (
	"project_id"	INTEGER,
	"naziv"	TEXT,
	"opis"	TEXT,
	"creator_id"	INTEGER,
	"date_created"	TEXT,
	"client_name"	TEXT,
	"client_email"	TEXT,
	PRIMARY KEY("project_id")
);
INSERT INTO "connections" VALUES (1,2);
INSERT INTO "connections" VALUES (2,1);
INSERT INTO "developer" VALUES (1,'Evelin','Piljug','epiljug1@etf.unsa.ba','pilja','pass');
INSERT INTO "developer" VALUES (2,'Evelin2','Piljug2','epiljug2@etf.unsa.ba','pilja2','pass');
INSERT INTO "developer" VALUES (3,'test','test','test','test','test');
INSERT INTO "developer" VALUES (4,'Novi','korisniilk','mail@mail.com','username','password');
INSERT INTO "project" VALUES (1,'projekat1','opis1',1,'09.02.2021.','kompanije.doo','client@mail.com');
INSERT INTO "project" VALUES (2,'projekat2','opis2',2,'09.02.2021.','kompanija2.doo','client2@mail.com');
COMMIT;
