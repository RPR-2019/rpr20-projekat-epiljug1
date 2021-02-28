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
INSERT INTO "developer" VALUES (1,'Evelin','Piljug','piljugevelin28@gmail.com','pilja','pass');
INSERT INTO "developer" VALUES (2,'Evelin2','Piljug2','epiljug1@etf.unsa.ba','pilja2','pass');
INSERT INTO "developer" VALUES (3,'test','test','test','test','test');
INSERT INTO "developer" VALUES (4,'Novi','korisniilk','mail@mail.com','username','password');
INSERT INTO "developer" VALUES (5,'novi','korisnik','mail@noviMail.com','nkorisnik','test');
INSERT INTO "project" VALUES (1,'projekat1','opis111fasodjkng;ljsdfn;lgjns;dkfjng;kdfsjng;vkjsdfn;kvnbsdfg;kjnbeiastrngbs;dkfljnbstirgnbkdjfneustrbngpk;djfnbvigusrtnbvikjnsdfigb',1,'09-Feb-2021','kompanije.doo','client@mail.com','https://github.com/RPR-2019/rpr20-projekat-epiljug1');
INSERT INTO "project" VALUES (2,'projekat2','opis2',2,'09-Feb-2021','kompanija2.doo','client2@mail.com','link');
INSERT INTO "project" VALUES (3,'Novi projekat','Deskripcija',1,'10-Feb-2021','Klijent','Klijent email','link');
COMMIT;
