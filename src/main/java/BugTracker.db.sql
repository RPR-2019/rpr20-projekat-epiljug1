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
CREATE TABLE IF NOT EXISTS "project" (
	"project_id"	INTEGER,
	"naziv"	TEXT,
	"opis"	TEXT,
	"creator_id"	INTEGER,
	"date_created"	TEXT,
	"client_name"	TEXT,
	"client_email"	TEXT,
	"code_link" TEXT,
	PRIMARY KEY("project_id"),
	FOREIGN KEY("creator_id") REFERENCES "developer"("developer_id")
);
CREATE TABLE IF NOT EXISTS "bug_assigned" (
	"project_id"	INTEGER,
	"bug_id"	INTEGER,
	"developer_id"	INTEGER
);
CREATE TABLE IF NOT EXISTS "bug" (
	"bug_id"	INTEGER,
	"bug_name"	TEXT,
	"bug_desc"	TEXT,
	"bug_type"	TEXT,
	"status"	TEXT,
	"date_created"	TEXT,
	"projectID"	INTEGER,
	"complexity"	TEXT,
	"solver_id"	INTEGER,
	"request_id"	INTEGER,
	PRIMARY KEY("bug_id"),
	FOREIGN KEY("projectID") REFERENCES "project"("project_id")
);
INSERT INTO "developer" VALUES (1,'Evelin','Piljug','piljugevelin28@gmail.com','admin','Password1');
INSERT INTO "developer" VALUES (2,'Evelin2','Piljug2','epiljug1@etf.unsa.ba','admin2','Password2');
INSERT INTO "project" VALUES (1,'projekat1','opis projekta 1',1,'09-Feb-2021','kompanije.doo','client@mail.com','https://github.com/RPR-2019/rpr20-projekat-epiljug1');
INSERT INTO "bug" VALUES (1,'bug_name','bug_desc','bug_type','status','01-Feb-2021',1,'High',0,0);
COMMIT;
