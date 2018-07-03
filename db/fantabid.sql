create table ALLENATORE (
     username varchar(30) not null,
     password varchar(30) not null,
     nome varchar(30) not null,
     cognome varchar(30) not null,
     constraint IDALLENATORE primary key (username));

create table CALCIATORE (
     idCalciatore numeric(4) not null,
     nome varchar(30) not null,
     squadra varchar(20) not null,
     prezzoStandard numeric(2) not null,
     ruolo char(1) not null,
     constraint IDCALCIATORE primary key (idCalciatore));

create table CAMPIONATO (
     idCampionato numeric(6) not null,
     nomeCampionato varchar(40) not null,
     descrizione varchar(1000) not null,
     budgetPerSquadra numeric(4) not null,
     dataApertura date not null,
     dataChiusura date not null,
     astaRialzo boolean not null,
     numeroMassimoSquadre numeric(2),
     constraint IDCAMPIONATO primary key (idCampionato));

create table MEMBRI_SQUADRA (
     idSquadra numeric(6) not null,
     idCalciatore numeric(4) not null,
     constraint IDMEMBRI_SQUADRA primary key (idCalciatore, idSquadra));

create table PUNTATA (
     idPuntata numeric(9) not null,
     puntataSuccessiva numeric(9),
     valore numeric(4) not null,
     idCalciatore numeric(4) not null,
     idSquadra numeric(6) not null,
     constraint IDPUNTATA primary key (idPuntata),
     constraint FKrialza_ID unique (puntataSuccessiva),
     constraint IDPUNTATA_1 unique (valore, idCalciatore, idSquadra));

create table REGOLA (
     idRegola numeric(3) not null,
     nome varchar(30) not null,
     descrizione varchar(1000) not null,
     constraint IDREGOLA primary key (idRegola));

create table REGOLE_PER_CAMPIONATO (
     idRegola numeric(3) not null,
     idCampionato numeric(6) not null,
     constraint IDREGOLE_PER_CAMPIONATO primary key (idCampionato, idRegola));

create table SQUADRA (
     idSquadra numeric(6) not null,
     idCampionato numeric(6) not null,
     username varchar(30) not null,
     nomeSquadra varchar(20) not null,
     creditoResiduo numeric(4) not null,
     constraint IDSQUADRA primary key (idSquadra),
     constraint IDSQUADRA_1 unique (idCampionato, username));


-- Constraints Section
-- ___________________ 

alter table MEMBRI_SQUADRA add constraint FKR
     foreign key (idCalciatore)
     references CALCIATORE;

alter table MEMBRI_SQUADRA add constraint FKR_1
     foreign key (idSquadra)
     references SQUADRA;

alter table PUNTATA add constraint FKriceve
     foreign key (idCalciatore)
     references CALCIATORE;

alter table PUNTATA add constraint FKoffre
     foreign key (idSquadra)
     references SQUADRA;

alter table PUNTATA add constraint FKrialza_FK
     foreign key (puntataSuccessiva)
     references PUNTATA;

alter table REGOLE_PER_CAMPIONATO add constraint FKR
     foreign key (idCampionato)
     references CAMPIONATO;

alter table REGOLE_PER_CAMPIONATO add constraint FKR_1
     foreign key (idRegola)
     references REGOLA;

alter table SQUADRA add constraint FKgestisce
     foreign key (username)
     references ALLENATORE;

alter table SQUADRA add constraint FKpartecipa
     foreign key (idCampionato)
     references CAMPIONATO;


-- Index Section
-- _____________ 

