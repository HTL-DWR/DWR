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

select * from bau b where b.did=21;
select * from geb_typ;
select * from spieler;
select * from dorf where owner='HansLookInTheAir';
select * from dorf;
create or replace view GeoDorf as select d.id as dorf_id, t.x as dorf_c_x,t.y  as dorf_c_y from dorf d, TABLE(SDO_UTIL.GETVERTICES(d.d_location)) t ;

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

select * from event e inner join stat_event se on e.id=se.id inner join dorf d on d.did=se.did;

CREATE INDEX index_dorf ON dorf(d_location) INDEXTYPE IS MDSYS.SPATIAL_INDEX;
--hier kommt sdo object




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

create table geb_typ(
id number primary key,
base_buildtime number,
base_build_price number references resgruppe(id),
name varchar2(20) unique
); --build time = base_buildtime * (lvl+1) * 1.75


create table bau(
did number references dorf(id),
tid number references geb_typ(id),
lvl number,
primary key (did,tid)
); -- check fpr level


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
--Sequenzen fÂ¸r Auto-Increment--

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



/*
insert into spieler(uname,clan) values('test user1',NULL);
insert into spieler(uname,clan) values('test user2',NULL);
insert into spieler(uname,clan) values('test user3',NULL);

insert into spieler values('stefan',1,'sha3-hash 1234');
insert into spieler values('hansi',1,'sha3-hash aaaa');
insert into spieler values('tino',1,'sha3-hash abcd');
insert into spieler values('alex',1,'sha3-hash 4321');
*/
--insert into dorf(id, name, owner, position, last_res_update) values (0,'urdorf','stefan',0, CURRENT_TIMESTAMP);
--insert into dorf(id, name, owner, position, last_res_update) values (1,'zweitbestes dorf','alex',0,CURRENT_TIMESTAMP);



------- Resourcen
/*insert into Movable(id,did) values( movable_id.nextval,1);
insert into RESGRUPPE(id,holz,stein,lehm) values(1,10,100,1000);
*/


/*insert into board(id,name,op) values(board_id.nextval,'testboard','tino');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,0,'hallo tino','stefan');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,0,'das ist mein board geh raus','tino');

insert into board(id,name,op) values(board_id.nextval,'Spamboard','hansi');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,2,'jetzt kaufen! premium hansi coins','hansi');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,2,'mods!!!','tino');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,2,'mods!!!','stefan');
insert into kommentar(id,board,text,op) values(kommentar_id.nextval,2,'mods!!!','alex');
*/

/*
commit;
select board.NAME,kommentar.TEXT from board inner join kommentar on board.id=kommentar.board;



select * from kommentar;*/
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
--erstellt resource für das event (erstellt nur!)
x := create_resource(10,10,10);
--(gibt das die resource dem bewegungsevent. heißt: es wird eine resource erstellt und an ein 
--dorf geliefert (würde nie passieren, aka macht keinen sinn, ist aber ein beispiel von 2 sachen
-- es ist sehr ähnlich wie wenn eine truppe ausgebildet wird, oder ein dorf geplündert wird
-- wir brauchen eine funktion die eine resourcengruppe aufteilt und eine die eine truppe aufteilt
insert into bew_event(id,von_dorf,nach_dorf,cargo,art) values(0,0,1,x,'res');
end;*/
--insert into event(id,BEGINN,DAUER) values(2,CURRENT_TIMESTAMP,60);
--insert into event(id,BEGINN,DAUER) values(3,CURRENT_TIMESTAMP,9999999);


/*
insert into movable(id,DID) values( movable_id.nextval,null);
insert into truppe(id,bogen,lanze,schwert,reiter) values (5,10,10,10,10);

--das sind beides truppenausbildungen. der häuserbau kommt noch
insert into stat_event(id,dorf,cargo) values(2,1,5); 
insert into stat_event(id,dorf,cargo) values(3,1,null);
*/

--!!!Bitte Testen!!!--

--wenn wir 15 sekunden so einfügen würden: 15/60/60/24 geht das
/*select is_in_past(e.beginn+e.dauer) from event e;
--wenn wir 15 sekunden so einfügen würden: 15
select id,is_in_past(e.beginn+e.dauer/60/60/24) from event e;
select * from dorf;


select * from dorf;
commit;*/
--select d.name as did, s.uname as owner,t.BOGEN,t.lanze,t.reiter,t.schwert,s.CLAN  from dorf d inner join spieler s on s.uname=d.owner left join movable m on m.did=d.id left join truppe t on t.id=m.id;


/*
declare 

begin

end;
insert into geb_typ(id,base_buildtime,name)values(geb_typ_id.nextval,10,'Hauptgebäude');
insert into geb_typ(id,base_buildtime,name)values(geb_typ_id.nextval,6,'Lehmgrube');
insert into geb_typ(id,base_buildtime,name)values(geb_typ_id.nextval,7,'Holzfäller');
insert into geb_typ(id,base_buildtime,name)values(geb_typ_id.nextval,10,'Steinbruch');*/
--delete from geb_typ;

/*select * from dorf;
select id from dorf where name='Dorf von sepp';
insert into bau d where did='Dorf von sepp';
select * from geb_typ;
select * from dorf d where SDO_ANYINTERACT(d.d_location,SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(9,9, 11,11))) = 'TRUE';
*/
--select * from dorf d where SDO_CONTAINS(d.d_location,SDO_GEOMETRY(2003,null,null,SDO_ELEM_INFO_ARRAY(1,1003,3),SDO_ORDINATE_ARRAY(2,2,4,6)))='TRUE';

--select gt.name, b.lvl from dorf d inner join bau b on b.did=d.id inner join geb_typ gt on b.tid=gt.id where d.name='Dorf von sepp';

/*--mache neues statisches event param: geb_name, did
declare
geb_name varchar2(20);
geb_id number;
did number;
x number;
dauer number;
begin
geb_name := 'Hauptgebäude';
x := event_id.nextval;
dauer := -1;
select MIN(gt.id) into geb_id from geb_typ gt where gt.NAME=geb_name;

dauer = GET_BUILD_TIME(geb_id,did);
insert into event(id,CURRENT_TIMESTAMP,dauer) values(x,,);
insert into stat_event(id,dorf,geb_typ,cargo)values(x,did,geb_id,null);
end;*/

/*--GET_BUILD_TIME param:geb_id, did
declare
geb_id number;
did number;

lvl number;
base_buildt number;
begin
select lvl,base_buildtime into lvl,base_buildt from geb_typ where id=geb_id;
return base_buildt + base_buildt*lvl);

end;*/

/*
--baue ein gebäude sofort, ohne event param: geb_name
declare
geb_name varchar2(20);
geb_id number;
begin
geb_name := 'Hauptgebäude';
select MIN(gt.id) into geb_id from geb_typ gt where gt.NAME=geb_name;
BAUE_GEBÄUDE_IN_DORF(did,geb_id);
end;
*/
--resolved ein stat_event


/*DECLARE
geb_id varchar2(20);

begin

BAUE_GEBÄUDE_IN_DORF(did,geb_id);
end;*/

/*
BEGIN
  CREATE_NEW_USER(
    '1111111',
    'neudorf',
    'asdffdsa'
  );
END;*/
--select * from spieler;
--select * from spieler s inner join dorf d on d.owner=s.uname inner join movable m on m.did=d.id inner join resgruppe r on r.id=m.id inner join bau b on b.did=d.id inner join geb_typ gt on gt.id=b.tid where s.uname='ugugug';



--commit;
/*
select d.id, t.x,t.y from dorf d, TABLE(SDO_UTIL.GETVERTICES(d.d_location)) t;

select MIN(b.lvl) from bau b where b.tid=9 and b.did=21;
select MIN(gt.id)  from geb_typ gt where gt.NAME='Hauptgebäude';

select MIN(b.lvl) from bau b where b.tid=1 and b.did=1;

select * from event e where e.

select * from clan;

select * from spieler inner join dorf d on d.owner=spieler.uname inner join movable m on m.did=d.id inner join truppe r on m.id=r.id where uname='dname';

select * from RESGRUPPE;*/

/*begin

update_resources();
end;*/
/*
declare 
x number;
begin

CREATE_NEW_USER('uname1','dname1','passhashfdsa');
CREATE_NEW_USER('uname2','dname2','fdas');
CREATE_NEW_USER('uname3','dname3','dddd');
CREATE_NEW_USER('uname4','dname4','passhaaaash');
end;*/
--commit;
/*select * from bau;
select * from dorf d full join bau b on d.id=b.DID inner join geb_typ gt on gt.id=b.TID;
select * from dorf;
select * from geb_typ;
select id from geb_typ where name in ('Hauptgebäude','Lehmgrube','Holzfäller');

select * from event;*/

begin
  BUILD_COMMAND(
    1,
    'Hauptgebäude'
  );
end;/**/
select * from geb_typ;

select * from event;
--select * from movable m inner join dorf on dorf.id=m.did where dorf.owner='Test';
/*select * from geb_typ;
select * from resgruppe;*/



begin
CREATE_GEBÄUDE_TYP('Hauptgebäude',1*60,15,10,10);
CREATE_GEBÄUDE_TYP('Steinmetz',1.5*60,10,14,6);
CREATE_GEBÄUDE_TYP('Lehmgrube',1*60,5,6,2);
CREATE_GEBÄUDE_TYP('Holzfäller',2*60,0,2,4);
CREATE_GEBÄUDE_TYP('Kaserne',3*60,10,14,6);

end;

begin
create_new_user('Batman-Fan1234','Gotham City','ec0e2603172c73a8b644bb9456c1ff6e');--batman
create_new_user('Gerhard der Barbar','Ödland-Oase','81dc9bdb52d04dc20036dbd8313ed055');--1234
create_new_user('Mario','Dorf 1-1','1b3231655cebb7a1f783eddf27d254ca');--super
create_new_user('Johanna die Kalte','Johannas Festung','9ea4d6718ed1e99ac40159487a14d00a');--hansi
create_new_user('Fritz der Eiserne','Südreich','31d9bb37999652d494ba78feb642a73f');--kota
create_new_user('Michael der Tapfere','Darnheim','f47636673b14c54021a69dc06f6a19fb');--karl
create_new_user('Kaiser Franz Joseph','Schönbrunn','c1dcc02668c89e3ec6552af19dabf59c');--franzl
end;
select d.owner,d.name from spieler inner join dorf d on d.owner=spieler.uname;

insert into clan values(1,'Griffindor');
insert into clan values(2,'Justice League');
insert into clan values(3,'Delta Red');
select * from spieler;
update spieler set clan=3 where uname='Batman-Fan1234';
update spieler set clan=3 where uname='Gerhard der Barbar';
update spieler set clan=1 where uname='Mario';
update spieler set clan=1 where uname='Johanna die Kalte';
update spieler set clan=1 where uname='Fritz der Eiserne';
update spieler set clan=2 where uname='Michael der Tapfere';
update spieler set clan=2 where uname='Kaiser Franz Joseph';

commit;
/*select * from resgruppe;
select * from truppe;
--update resgruppe set holz=1000,stein=1000,lehm=1000 where
begin
--create_new_user('username','dorfname','passwdhash');
BUILD_COMMAND (1,'Hauptgebäude');
end;


select lvl,base_buildtime from geb_typ where id=1;


select * from event e inner join stat_event se on se.id=e.id;
--commit;
select MIN(r.holz),Min(r.stein),Min(r.lehm),MIN(r.id) from resgruppe r inner join movable m on r.id=m.id where m.did=1; 

select MIN(gt.id),MIN(gt.base_build_price) from geb_typ gt where gt.NAME='Hauptgebäude';

  select id from geb_typ where geb_typ.NAME='kasserne';
insert into bau(DID,LVL,tid) values(1,1,21);
select NVL(MIN(id),0) from board;
select * from dorf inner join movable m on m.did=dorf.id inner join resgruppe r on r.id=m.id where m.did=1;
update resgruppe set stein=999 where id=4;
declare
x number; 
begin
--CREATE_GEBÄUDE_TYP('kasserne',120,15,20,7);
x := CREATE_Truppenausbildung(1,5);

end;
select * from event e inner join stat_event se on e.id=se.id;*/
--select * from geb_typ;

select * from spieler inner join dorf on dorf.owner=spieler.uname;
select owner from dorf where id=3;--74114725;
select DECODE(COUNT(*),0,'false',1,'true') from spieler where uname='Mario' and passwd_hash='1b3231655cebb7a1f783edf27d254ca';
select * from spieler;
begin
NEW_BOARD('HILFE','Hi, werde angegriffen.','Hugo');
NEW_BOARD('Handel','Hi, brauche holz','Mario');
/*POST_COMMENT(0,'schould work','Mario');
POST_COMMENT(1,'hab holz','Fritz der Eiserne');
POST_COMMENT(1,'hab stein, aber auch kein holz','Hugo');*/
end;
select * from event e  inner join stat_event se on e.id=se.id;
begin 
select * from spieler s inner join resgruppe r on r.owner=s.uname;
select * from resgruppe;
update resgruppe set holz=666, stein=666, lehm=666 where id=21;
select * from spieler s inner join dorf d on d.owner=s.uname inner join movable m on m.did=d.id inner join resgruppe r on m.id=r.id;
commit;
--POST_COMMENT(18,'hab stein, aber auch kein holz','Hugo');
--POST_COMMENT(19,'hab holz','Fritz der Eiserne');
end;

select MIN(gt.id),MIN(gt.base_build_price)from geb_typ gt where gt.NAME like '%'||'Hauptgebaeude'||'%';
select * from resgruppe where id=1;
  select MIN(r.holz),Min(r.stein),Min(r.lehm),MIN(r.id)  from resgruppe r inner join movable m on r.id=m.id where m.did=8; 
select * from dorf;
select * from dorf where id=8;
select * from spieler;
select * from geb_typ inner join resgruppe r on geb_typ.BASE_BUILD_PRICE=r.id;
select * from geb_typ where id=1;
select * from stat_event;
select MIN(gt.id),MIN(gt.base_build_price) from geb_typ gt where gt.NAME='Hauptgebaeude';
select holz,stein,lehm from resgruppe where id=1;
update geb_typ set name='Holzfaeller' where id=4;
 commit;
 delete from event e;
 select * from event;
 select * from resgruppe inner join movable m on m.id=resgruppe.id;
select * from board;
select * from clan;
select b.name as Board,b.op as poster from board b inner join spieler s on b.op=s.uname where s.clan=1;

begin
build_command(1,'Hauptgebaeude');
end;


DECLARE
  DID NUMBER;
  GEB_NAME VARCHAR2(200);
BEGIN
  DID := 8;
  GEB_NAME := 'Hauptgebaeude';

  BUILD_COMMAND(
    DID => DID,
    GEB_NAME => GEB_NAME
  );
--rollback; 
END;
select * from movable inner join resgruppe r on movable.id=r.id;
update truppe set schwert=80,reiter=80,bogen=80,lanze=80 where id=22;
select * from movable m inner join truppe r on r.id=m.id where m.did=8;
select * from movable m inner join dorf d on d.id=m.did where m.did=8;
select * from movable;
select * from resgruppe r inner join movable m on r.id=m.id where m.did=8; 