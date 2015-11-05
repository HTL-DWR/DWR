drop table spieler cascade constraints;
drop table clan cascade constraints;
drop table board cascade constraints;
drop table kommentar cascade constraints;
drop table dorf cascade constraints;
drop table resgruppe cascade constraints;
drop table event cascade constraints;
drop table bew_event cascade constraints;
drop table stat_event cascade constraints;
drop SEQUENCE movable_id;
drop SEQUENCE geb_typ_id;
drop SEQUENCE dorf_id;
drop SEQUENCE event_id;
drop SEQUENCE clan_id;
drop SEQUENCE board_id;
drop SEQUENCE kommentar_id;
drop table geb_typ cascade constraints;
drop table movable cascade constraints;
drop table bau cascade constraints;

drop table truppe cascade constraints;

create table clan(
id number primary key,
name varchar2(20) 
);

create table spieler(
uname varchar2(20) primary key,
clan number references clan(id),
passwd_hash varchar(50)
);

create table board(
id number primary key,
name varchar2(50),
text varchar2(255),
op varchar2(20) references spieler(uname),
post_time timestamp
);

create table kommentar(
id number primary key,
board number references board(id),
op varchar2(20) references spieler(uname),
text varchar2(200),
post_time timestamp
);


create table dorf(
id number primary key,
name varchar2(20),
owner varchar2(20) references spieler(uname),
d_location SDO_GEOMETRY,
last_res_update timestamp
);


INSERT INTO user_sdo_geom_metadata
( TABLE_NAME,
COLUMN_NAME,
DIMINFO,
SRID
)
VALUES
( 'dorf',
'd_Location',
SDO_DIM_ARRAY( -- 20X20 grid
SDO_DIM_ELEMENT('X', 0, 0, 0.005),
SDO_DIM_ELEMENT('Y', 1000, 1000, 0.005)
),
NULL -- SRID
);

CREATE INDEX index_dorf ON dorf(d_location) INDEXTYPE IS MDSYS.SPATIAL_INDEX;
--hier kommt sdo object

create table geb_typ(
id number primary key,
base_buildtime number,
name varchar2(20)
); --build time = base_buildtime * (lvl+1) * 1.75



create table bau(
did number references dorf(id),
tid number references geb_typ(id),
lvl number,
primary key (did,tid)
); -- check fpr level


create table Movable(
id number primary key,
did number references dorf(id)
);


create table resgruppe(
id number references Movable(id),
holz number,
stein number,
lehm number,
primary key (id)
);

create table event(
id number primary key,
dauer number,
beginn timestamp
);

create table bew_event(
id number references event(id),
von_dorf number references dorf(id),
nach_dorf number references dorf(id),
cargo number references Movable(id),
art  VARCHAR2(3) CHECK( art IN ('res','sup','atk') ),
primary key(id)
);

create table truppe(
id number references Movable(id),
schwert number,
reiter number,
bogen number,
lanze number,
owner varchar2(20) references Spieler(uname),
primary key (id)
);



create table stat_event(
id number references event(id),
dorf number references dorf(id),
geb_typ number references geb_typ(id),
cargo number references Truppe(id),
primary key(id)
);


----------------------------------------------------------------
--Sequenzen f¸r Auto-Increment--

--Moveable
CREATE SEQUENCE movable_id
MINVALUE 1
START WITH 1
INCREMENT BY 1;

--Clan
CREATE SEQUENCE clan_id
MINVALUE 1
START WITH 1
INCREMENT BY 1;

--Board
CREATE SEQUENCE board_id
MINVALUE 1
START WITH 1
INCREMENT BY 1;

--Kommentar
CREATE SEQUENCE kommentar_id
MINVALUE 1
START WITH 1
INCREMENT BY 1;

--Dorf
CREATE SEQUENCE dorf_id
MINVALUE 1
START WITH 1
INCREMENT BY 1;

--geb_typ
CREATE SEQUENCE geb_typ_id
MINVALUE 1
START WITH 1
INCREMENT BY 1;


--event
CREATE SEQUENCE event_id
MINVALUE 1
START WITH 1
INCREMENT BY 1;

insert into clan values(1,'devs');

insert into spieler(uname,clan) values('test user1',NULL);
insert into spieler(uname,clan) values('test user2',NULL);
insert into spieler(uname,clan) values('test user3',NULL);

insert into spieler values('stefan',1,'sha3-hash 1234');
insert into spieler values('hansi',1,'sha3-hash aaaa');
insert into spieler values('tino',1,'sha3-hash abcd');
insert into spieler values('alex',1,'sha3-hash 4321');

--insert into dorf(id, name, owner, position, last_res_update) values (0,'urdorf','stefan',0, CURRENT_TIMESTAMP);
--insert into dorf(id, name, owner, position, last_res_update) values (1,'zweitbestes dorf','alex',0,CURRENT_TIMESTAMP);



------- Resourcen
/*insert into Movable(id,did) values( movable_id.nextval,1);
insert into RESGRUPPE(id,holz,stein,lehm) values(1,10,100,1000);
*/
insert into board(id,name,op) values(board_id.nextval,'testboard','tino');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,1,'hallo tino','stefan');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,1,'das ist mein board geh raus','tino');

insert into board(id,name,op) values(board_id.nextval,'Spamboard','hansi');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,2,'jetzt kaufen! premium hansi coins','hansi');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,2,'mods!!!','tino');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,2,'mods!!!','stefan');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,2,'mods!!!','alex');

commit;
select board.NAME,kommentar.TEXT from board inner join kommentar on board.id=kommentar.board;



select * from kommentar;
--wenn das passiert muss der auto_add trigger anspringen und die beiden resourcengruppen addieren!!!
/*
declare
x number;
begin


--resource erstellen
x := create_resource(10,10,10);
--diese in dorf 1 schieben
update movable set did=1 where id=x;

--2. resource erstellen
x := create_resource(10,10,10);
--diese auch in dorf 1 schieben
update movable set did=1 where id=x;
select * from RESGRUPPE;
end;*/


-------------Events-----------
--die brauchen auch eine create funktion (schaut GANZ gleich aus wie die create resource
--die dauer ist aktuell so wie's jetz ist, ziemlich sicher in tagen angegeben! 
--mit /60/60/24 angeben aber erst noch testen, bin mir aber ziemlich sicher
--
--insert into event(id,BEGINN,DAUER) values(1,CURRENT_TIMESTAMP,10);




/*declare
x number;
begin
--erstellt ein event (brauch auch noch create funktion
insert into event(id,BEGINN,DAUER) values(0,CURRENT_TIMESTAMP,1); 
--erstellt resource f�r das event (erstellt nur!)
x := create_resource(10,10,10);
--(gibt das die resource dem bewegungsevent. hei�t: es wird eine resource erstellt und an ein 
--dorf geliefert (w�rde nie passieren, aka macht keinen sinn, ist aber ein beispiel von 2 sachen
-- es ist sehr �hnlich wie wenn eine truppe ausgebildet wird, oder ein dorf gepl�ndert wird
-- wir brauchen eine funktion die eine resourcengruppe aufteilt und eine die eine truppe aufteilt
insert into bew_event(id,von_dorf,nach_dorf,cargo,art) values(0,0,1,x,'res');
end;*/
--insert into event(id,BEGINN,DAUER) values(2,CURRENT_TIMESTAMP,60);
--insert into event(id,BEGINN,DAUER) values(3,CURRENT_TIMESTAMP,9999999);


/*
insert into movable(id,DID) values( movable_id.nextval,null);
insert into truppe(id,bogen,lanze,schwert,reiter) values (5,10,10,10,10);

--das sind beides truppenausbildungen. der h�userbau kommt noch
insert into stat_event(id,dorf,cargo) values(2,1,5); 
insert into stat_event(id,dorf,cargo) values(3,1,null);
*/

--!!!Bitte Testen!!!--

--wenn wir 15 sekunden so einf�gen w�rden: 15/60/60/24 geht das
select is_in_past(e.beginn+e.dauer) from event e;
--wenn wir 15 sekunden so einf�gen w�rden: 15
select id,is_in_past(e.beginn+e.dauer/60/60/24) from event e;
select * from dorf;

declare 
x number;
begin
x := CREATE_DORF('hansidorf','hansi',1,1);
x := CREATE_DORF('d1','stefan',1,1);
x := CREATE_DORF('d2','tino',1,1);
x := CREATE_DORF('das dorf','alex',1,1);
x := CREATE_DORF('gawo-galgen','hansi',1,1);
x := CREATE_DORF('ZehnZehnDorf','alex',10,10);

end;
select * from dorf;
commit;
--select d.name as did, s.uname as owner,t.BOGEN,t.lanze,t.reiter,t.schwert,s.CLAN  from dorf d inner join spieler s on s.uname=d.owner left join movable m on m.did=d.id left join truppe t on t.id=m.id;

insert into geb_typ(id,base_buildtime,name)values(geb_typ_id.nextval,10,'Hauptgeb�ude');
insert into geb_typ(id,base_buildtime,name)values(geb_typ_id.nextval,6,'Lehmgrube');
insert into geb_typ(id,base_buildtime,name)values(geb_typ_id.nextval,7,'Holzf�ller');
insert into geb_typ(id,base_buildtime,name)values(geb_typ_id.nextval,10,'Steinbruch');

select * from dorf;
select id from dorf where name='Dorf von sepp';
insert into bau d where did='Dorf von sepp';

select * from dorf d where SDO_ANYINTERACT(d.d_location,SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(9,9, 11,11))) = 'TRUE';

--select * from dorf d where SDO_CONTAINS(d.d_location,SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(2,2,4,6)))='TRUE';

select gt.name, b.lvl from dorf d inner join bau b on b.did=d.id inner join geb_typ gt on b.tid=gt.id where d.name='Dorf von sepp';


/*
DECLARE
  UNAME VARCHAR2(200);
  D_NAME VARCHAR2(200);
  PASSWD_SHA3 VARCHAR2(200);
  v_Return varchar2(200);
BEGIN
  v_Return := CREATE_NEW_USER(
    'peter',
    'dname',
    'asdffdsa'
  );

 -- :v_Return := v_Return;
--rollback; 
END;*/

select * from spieler;

commit;

select MIN(b.lvl) from bau b where b.tid=1 and b.did=1;

select * from spieler inner join dorf d on d.owner=spieler.uname inner join movable m on m.did=d.id inner join truppe r on m.id=r.id where uname='dddd';
