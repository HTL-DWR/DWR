


CREATE OR REPLACE PROCEDURE ATK_RESOLVE 
(
  ATK_DORF IN NUMBER 
, DEF_DORF IN NUMBER 
, TRUPPE IN NUMBER 
) AS 

m_id number;
t_id number;
atk_def number := 0;
atk_atk number := 0;
atk_tmp number;
tmp number;
newSchwert number;
newReiter number;
newBogen number;
newlanze number;

BEGIN
  select m.id into m_id from movable m JOIN Dorf d on m.did = d.id where d.id = DEF_DORF;
  select t.id into t_id from truppe t JOIN movable m on m.id = t.id where m.id = m_id;
  select schwert into atk_tmp from truppe where id = t_id;
  atk_def := atk_def + atk_tmp;
  select reiter into atk_tmp from truppe where id = t_id;
  atk_def := atk_def + atk_tmp*2;
  select bogen into atk_tmp from truppe where id = t_id;
  atk_def := atk_def + atk_tmp*3;
  select lanze into atk_tmp from truppe where id = t_id;
  atk_def := atk_def + atk_tmp*4;
  
  select schwert into atk_tmp from truppe where id = TRUPPE;
  atk_atk := atk_atk + atk_tmp;
  select reiter into atk_tmp from truppe where id = TRUPPE;
  atk_atk := atk_atk + atk_tmp*2;
  select bogen into atk_tmp from truppe where id = TRUPPE;
  atk_atk := atk_atk + atk_tmp*3;
  select lanze into atk_tmp from truppe where id = TRUPPE;
  atk_atk := atk_atk + atk_tmp*4;
  
  IF atk_atk > atk_def THEN
  --all def truppen werden 0
    atk_tmp := atk_def;
    select schwert into tmp from truppe where id = TRUPPE;
    IF tmp <= atk_tmp THEN
      newSchwert := 0;
      atk_tmp := atk_tmp - tmp;
          --restlichen truppen bis 0
               select reiter into tmp from truppe where id = TRUPPE;
               tmp := tmp*2;
               IF tmp <= atk_tmp THEN
                   newReiter := 0;
                   atk_tmp := atk_tmp - tmp;
                   select bogen into tmp from truppe where id = TRUPPE;
                   tmp := tmp*3;
                   IF tmp <= atk_tmp THEN
                      newBogen := 0;
                      atk_tmp := atk_tmp - tmp;
                      select lanze into tmp from truppe where id = TRUPPE;
                      tmp := tmp*4;
                      IF tmp <= atk_tmp THEN
                          newLanze := 0;
                          atk_tmp := atk_tmp - tmp;
                      ELSE
                          newLanze := tmp-atk_tmp;
                      END IF;
                   ELSE
                        newBogen := tmp-atk_tmp;
                        select lanze into tmp from truppe where id = TRUPPE;
                        newLanze := tmp;
                   END IF;
              ELSE
                   newReiter := tmp-atk_tmp;
                    select bogen into tmp from truppe where id = TRUPPE;
                    newBogen := tmp;
                    select lanze into tmp from truppe where id = TRUPPE;
                    newLanze := tmp;
              END IF;
    ELSE
      newSchwert := tmp-atk_tmp;
      select reiter into tmp from truppe where id = TRUPPE;
      newReiter := tmp;
      select bogen into tmp from truppe where id = TRUPPE;
      newBogen := tmp;
      select lanze into tmp from truppe where id = TRUPPE;
      newLanze := tmp;
    END IF;
    update truppe SET schwert=newSchwert, reiter=newReiter, bogen=newBogen, lanze=newLanze where id = TRUPPE;
    --Loot here mehr oder weniger nur new event für alle ressis von dorf 1 move to dorf 2 und truppen wd zurück
    
  ELSE
    --umgekehrt
     --all atk truppen werden 0
    atk_tmp := atk_atk;
    select schwert into tmp from truppe where id = t_id;
    IF tmp <= atk_tmp THEN
      newSchwert := 0;
      atk_tmp := atk_tmp - tmp;
          --restlichen truppen bis 0
               select reiter into tmp from truppe where id = t_id;
               tmp := tmp*2;
               IF tmp <= atk_tmp THEN
                   newReiter := 0;
                   atk_tmp := atk_tmp - tmp;
                   select bogen into tmp from truppe where id = t_id;
                   tmp := tmp*3;
                   IF tmp <= atk_tmp THEN
                      newBogen := 0;
                      atk_tmp := atk_tmp - tmp;
                      select lanze into tmp from truppe where id = t_id;
                      tmp := tmp*4;
                      IF tmp <= atk_tmp THEN
                          newLanze := 0;
                          atk_tmp := atk_tmp - tmp;
                      ELSE
                          newLanze := tmp-atk_tmp;
                      END IF;
                   ELSE
                        newBogen := tmp-atk_tmp;
                        select lanze into tmp from truppe where id = t_id;
                        newLanze := tmp;
                   END IF;
              ELSE
                   newReiter := tmp-atk_tmp;
                    select bogen into tmp from truppe where id = t_id;
                    newBogen := tmp;
                    select lanze into tmp from truppe where id = t_id;
                    newLanze := tmp;
              END IF;
    ELSE
      newSchwert := tmp-atk_tmp;
      select reiter into tmp from truppe where id = t_id;
      newReiter := tmp;
      select bogen into tmp from truppe where id = t_id;
      newBogen := tmp;
      select lanze into tmp from truppe where id = t_id;
      newLanze := tmp;
    END IF;
    
    update truppe SET schwert=newSchwert, reiter=newReiter, bogen=newBogen, lanze=newLanze where id = t_id;
  END IF;
  
  
END ATK_RESOLVE;