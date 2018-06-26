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
     budgetSquadra numeric(4) not null,
     dataApertura date not null,
     numeroMassimoSquadre numeric(2),
     dataChiusura date,
     idRegola numeric(3),
     constraint IDCAMPIONATO primary key (idCampionato));

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
     descrizione varchar(140) not null,
     constraint IDREGOLA primary key (idRegola));

create table SQUADRA (
     idCampionato numeric(6) not null,
     username varchar(30) not null,
     nomeSquadra varchar(20) not null,
     creditoResiduo numeric(4) not null,
     idCalciatore numeric(4),
     constraint IDSQUADRA primary key (username, idCampionato));

alter table CAMPIONATO add constraint FKattinente_a
     foreign key (idRegola)
     references REGOLA;

alter table PUNTATA add constraint FKriceve
     foreign key (idCalciatore)
     references CALCIATORE;

alter table PUNTATA add constraint FKrialza_FK
     foreign key (Successiva_idCalciatore, Successiva_username, Successiva_idCampionato, Successiva_valore)
     references PUNTATA;

alter table PUNTATA add constraint FKrialza_CHK
     check((Successiva_idCalciatore is not null and Successiva_username is not null and Successiva_idCampionato is not null and Successiva_valore is not null)
           or (Successiva_idCalciatore is null and Successiva_username is null and Successiva_idCampionato is null and Successiva_valore is null)); 

alter table PUNTATA add constraint FKoffre
     foreign key (username, idCampionato)
     references SQUADRA;

alter table SQUADRA add constraint FKgestisce
     foreign key (username)
     references ALLENATORE;

alter table SQUADRA add constraint FKcompraAttaccante
     foreign key (idCalciatore)
     references CALCIATORE;

alter table SQUADRA add constraint FKpartecipa
     foreign key (idCampionato)
     references CAMPIONATO;
