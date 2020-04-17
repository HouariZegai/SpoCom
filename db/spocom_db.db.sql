BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `competition` (
	`id_com`	LONG NOT NULL,
	`edition`	INTEGER NOT NULL,
	`type`	VARCHAR NOT NULL,
	`date`	DATE NOT NULL,
	`lieu`	VARCHAR NOT NULL,
	`benjemine`	VARCHAR NOT NULL,
	`minime`	VARCHAR NOT NULL,
	`cadet`	VARCHAR NOT NULL,
	`junior`	VARCHAR NOT NULL,
	`senior`	VARCHAR NOT NULL,
	PRIMARY KEY(`id_com`)
);
CREATE TABLE IF NOT EXISTS `classement` (
	`id_com`	LONG NOT NULL,
	`n_doss`	LONG NOT NULL,
	`classement`	INTEGER NOT NULL,
	PRIMARY KEY(`n_doss`,`id_com`),
	FOREIGN KEY(`n_doss`) REFERENCES `athlete`(`n_doss`),
	FOREIGN KEY(`id_com`) REFERENCES `competition`(`id_com`)
);
CREATE TABLE IF NOT EXISTS `athlete` (
	`n_doss`	LONG NOT NULL,
	`nom`	VARCHAR NOT NULL,
	`prenom`	VARCHAR,
	`date_naiss`	DATE,
	`sexe`	INTEGER NOT NULL,
	`club`	VARCHAR NOT NULL,
	`cw`	VARCHAR NOT NULL,
	`obs`	BOOLEAN NOT NULL,
	`id_com`	LONG NOT NULL,
	PRIMARY KEY(`n_doss`),
	FOREIGN KEY(`id_com`) REFERENCES `competition`(`id_com`)
);
COMMIT;
