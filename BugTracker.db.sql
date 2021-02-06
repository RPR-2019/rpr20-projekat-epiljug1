BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS "connections" (
	"pr_id"	INTEGER,
	"de_id"	INTEGER,
	FOREIGN KEY("pr_id") REFERENCES "project"("project_id"),
	FOREIGN KEY("de_id") REFERENCES "developer"("developer_id")
);
CREATE TABLE IF NOT EXISTS "project" (
	"project_id"	INTEGER,
	"naziv"	TEXT,
	"opis"	TEXT,
	"creator_id"	INTEGER,
	"date_created"	TEXT,
	FOREIGN KEY("creator_id") REFERENCES "developer"("developer_id"),
	PRIMARY KEY("project_id")
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
	FOREIGN KEY("projectID") REFERENCES "project"("project_id"),
	PRIMARY KEY("bug_id")
);
INSERT INTO "project" VALUES (1,'projekat1','opis1',1,NULL);
INSERT INTO "project" VALUES (2,'projekat2','opis2',2,NULL);
INSERT INTO "developer" VALUES (1,'Evelin','Piljug','epiljug1@etf.unsa.ba','pilja','pass');
INSERT INTO "developer" VALUES (2,'Evelin2','Piljug2','epiljug2@etf.unsa.ba','pilja2','pass');
COMMIT;