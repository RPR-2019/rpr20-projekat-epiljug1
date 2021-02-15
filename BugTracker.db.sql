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
	PRIMARY KEY("project_id"),
	FOREIGN KEY("creator_id") REFERENCES "developer"("developer_id")
);
CREATE TABLE IF NOT EXISTS "request" (
	"developer_id"	INTEGER,
	"project_id"	INTEGER,
	"bug_id"	INTEGER
);
CREATE TABLE IF NOT EXISTS "bug" (
	"bug_id"	INTEGER,
	"bug_name"	TEXT,
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
INSERT INTO "connections" VALUES (1,2);
INSERT INTO "connections" VALUES (1,3);
INSERT INTO "connections" VALUES (1,4);
INSERT INTO "connections" VALUES (2,1);
INSERT INTO "connections" VALUES (2,4);
INSERT INTO "connections" VALUES (3,2);
INSERT INTO "connections" VALUES (3,1);
INSERT INTO "developer" VALUES (1,'Evelin','Piljug','piljugevelin28@gmail.com','pilja','pass');
INSERT INTO "developer" VALUES (2,'Evelin2','Piljug2','epiljug1@etf.unsa.ba','pilja2','pass');
INSERT INTO "developer" VALUES (3,'test','test','test','test','test');
INSERT INTO "developer" VALUES (4,'Novi','korisniilk','mail@mail.com','username','password');
INSERT INTO "developer" VALUES (5,'novi','korisnik','mail@noviMail.com','nkorisnik','test');
INSERT INTO "project" VALUES (1,'projekat1','opis111fasodjkng;ljsdfn;lgjns;dkfjng;kdfsjng;vkjsdfn;kvnbsdfg;kjnbeiastrngbs;dkfljnbstirgnbkdjfneustrbngpk;djfnbvigusrtnbvikjnsdfigb',1,'09.02.2021.','kompanije.doo','client@mail.com');
INSERT INTO "project" VALUES (2,'projekat2','opis2',2,'09.02.2021.','kompanija2.doo','client2@mail.com');
INSERT INTO "project" VALUES (3,'Novi projekat','Deskripcija',1,'10.02.2021.','Klijent','Klijent email');
INSERT INTO "project" VALUES (4,'nazivEditova','desc',1,'10.02.2021.','client','mejl');
INSERT INTO "project" VALUES (5,'RPR-tutorijal-5','aasdasasd',1,'10.02.2021.','ahjh','a2');
INSERT INTO "project" VALUES (6,'RPR-tutorijal-7','a',1,'10.02.2021.','aaa','a2');
INSERT INTO "project" VALUES (7,'naziv','desc',1,'10.02.2021.','client','mejl');
INSERT INTO "project" VALUES (8,'naziv','desc',1,'11.02.2021.','client','mejl');
INSERT INTO "request" VALUES (2,1,1);
INSERT INTO "bug" VALUES (1,'bugName','bugType','new','11.02.2021.',1,'high',0,2);
INSERT INTO "bug" VALUES (2,'asdasd','asd','sdf','11.02.2021.',1,'asd',0,0);
COMMIT;
