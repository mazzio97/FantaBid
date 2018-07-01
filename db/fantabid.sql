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
     tipoAsta char not null,
     budgetPerSquadra numeric(4) not null,
     dataApertura date not null,
     numeroMassimoSquadre numeric(2),
     dataChiusura date,
     constraint IDCAMPIONATO primary key (idCampionato));

create table MEMBRI_SQUADRA (
     idSquadra numeric(6) not null,
     idCalciatore numeric(4) not null,
     constraint IDMEMBRI_SQUADRA primary key (idCalciatore, idSquadra));

create table PUNTATA (
     username varchar(30) not null,
     idCampionato numeric(6) not null,
     idCalciatore numeric(4) not null,
     valore numeric(4) not null,
     Successiva_idCalciatore numeric(4),
     Successiva_username varchar(30),
     Successiva_idCampionato numeric(6),
     Successiva_valore numeric(4),
     dataScadenza date not null,
     constraint IDPUNTATA primary key (idCalciatore, username, idCampionato, valore),
     constraint FKrialza_ID unique (Successiva_idCalciatore, Successiva_username, Successiva_idCampionato, Successiva_valore));

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

alter table PUNTATA add constraint FKrialza_FK
     foreign key (Successiva_idCalciatore, Successiva_username, Successiva_idCampionato, Successiva_valore)
     references PUNTATA;

alter table PUNTATA add constraint FKrialza_CHK
     check((Successiva_idCalciatore is not null and Successiva_username is not null and Successiva_idCampionato is not null and Successiva_valore is not null)
           or (Successiva_idCalciatore is null and Successiva_username is null and Successiva_idCampionato is null and Successiva_valore is null)); 

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

