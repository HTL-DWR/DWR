<?php

class specifiedData {
    public $specification;
    public $data;
	public $subelements=array();
}

function generateXML_Response()//name des fixen Feldes "operation" als erster Parameter, danach so viele objekte von Klasse/Strukt specifiedField wie gewollt
{
	$felder = func_get_args();
	$resp = new SimpleXMLElement('<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE collection SYSTEM "optionale_rueckgabe_definition.dtd"><response></response>');
	
	for($i=0;$i<func_num_args();$i++)
	{
		//echo "loop".$i.": ". $resp->asXML();
		$f=$felder[$i];
		insertIntoXMLTree($resp,$f);
	}
	echo $resp->asXML();
	
}

function insertIntoXMLTree($root,$parentElement)
{
	if($parentElement->data!="" && empty($parentElement->subelements))
	{
		$help = $root->addChild($parentElement->specification,$parentElement->data);
	}
	else if ($parentElement->data=="" && !empty($parentElement->subelements[0]))
	{
		$help = $root->addChild($parentElement->specification);
		
		for($i=0;$i<count($parentElement->subelements);$i++)
		{
			insertIntoXMLTree($help,$parentElement->subelements[$i]);
		}	
	}
	else
	{
		echo "xml-format fehler ";
	}
	
}
function behandleHTTP_GET()
{
	//die daten aus der datenbank zurückschreiben
	$root_xml = new SimpleXMLElement('<?xml version="1.0" encoding="UTF-8"?><!DOCTYPE collection SYSTEM "optionale_rueckgabe_definition.dtd"><wurzel_element></wurzel_element>');
	
	//TODO: hier -> connect oracle db
	if(isset($_GET["operation"]))
		{
			switch($_GET["operation"])
			{
				case "dorf_view":
					//TODO: select * from dorf where id=xyz (if user-passkey abfragen)
				break;
				
				case "map_data":
				
				
					// oracle db connect & sql-q
					$conn = oci_connect('d5bhifs11', 'd5bhifs11', '212.152.179.117');
					if (!$conn) {
						$e = oci_error();
						trigger_error(htmlentities($e['message'], ENT_QUOTES), E_USER_ERROR);
					}

					$stid = oci_parse($conn, 
					'select d.name as did, s.uname as owner,t.BOGEN,t.lanze,t.reiter,t.schwert, s.clan
					from dorf d inner join spieler s on s.uname=d.owner left join movable m on m.did=d.id
					left join truppe t on t.id=m.id;');
					oci_execute($stid);
					//TODO: bekommt parameter: zentrum. kriegt alle in dem bereich, 
					//nur: position, owner, clan, (wom. truppen)
					$map = new specifiedData();
					$map->specification = 'map';
					
					$i=0;
					while ($row = oci_fetch_array($stid, OCI_ASSOC+OCI_RETURN_NULLS)) //für alle selecteten dörfer
					{
						
						$row_name=$row[0];
						$row_did = $row[1];
						$row_owner=$row[2];
						$row_bogen=$row[3];
						$row_lanze=$row[4];
						$row_reiter=$row[5];
						$row_schwert=$row[6];
						$row_clan = $row[7];
						
						$dname = new specifiedData();
						$dname->specification = 'name';
						$dname->data=$row_name;
						
						$dx = new specifiedData();
						$dx->specification = 'Y';
						$dx->data=-1;//TODO X Position
						
						$dy = new specifiedData();
						$dy->specification = 'X';
						$dy->data=-1;//TODO Y Position
						
						$dpos = new specifiedData();
						$dpos->specification = 'position';
						$dpos->subelements[0]=$dx;
						$dpos->subelements[1]=$dy;
						
						$owner = new specifiedData();
						$owner->specification = 'owner';
						$owner->data=$row_owner;
						
						$clan = new specifiedData();
						$clan->specification = 'clan';
						$clan->data=$row_clan;//TODO Clan
						
						$dorf = new specifiedData();
						$dorf->specification = 'dorf';
						$dorf->subelements[0]=$dname; //TODO daten in dorf einfügen
						$dorf->subelements[1]=$dpos;
						$dorf->subelements[2]=$owner;
						$dorf->subelements[3]=$clan;
						
						
						$map->subelements[$i] = $dorf;
						$i++;
					}
				break;
				case "user_resources":
					
				break;
				case "user_troops":
					
				break;
				
				
			}
			// ------- anfang xml daten erstellen -------
			$daten = new specifiedData();
			$daten->specification = 'daten';
			$daten->data = $_GET["wert"];
			
			$infosammlung = new specifiedData();
			$infosammlung->specification = 'infoliste';
			
			$unterinfo1 = new specifiedData();
			$unterinfo1->specification = 'info';
			$unterinfo1->data = '123';
			
			$unterinfo2 = new specifiedData();
			$unterinfo2->specification = 'info';
			$unterinfo2->data = 'abc';
			
			$unterinfo3 = new specifiedData();
			$unterinfo3->specification = 'info';
			$unterinfo3->data = 'xyz';
			
			$infosammlung->subelements[0]=$unterinfo1;
			$infosammlung->subelements[1]=$unterinfo2;
			$infosammlung->subelements[2]=$unterinfo3;
			
			// ------- ende xml daten erstellen -------
			
			
			//in xml Baum einhängen und diesen ausgeben
			//links ist das parent und rechts das unterelement
			generateXML_Response($daten,$infosammlung);
			//den xml-tree als text ausgeben (mach keine anderen echos, sonst ist das xml invalid
		}
}
function behandleHTTP_POST()
{
	//Hierher kommen die datenbank-inserts und updates
	echo "methode post nicht implementiert";
}

function behandleHTTP_DELETE()
{
	//Hierher kommen die datenbank-deletes
	echo "methode delete nicht implementiert";
}

//xml Kopf und root-element erstellen (braucht man immer)


switch($_SERVER['REQUEST_METHOD'])
{
 case "GET":
		behandleHTTP_GET();
	break;
 case "POST":
		behandleHTTP_POST();
 case "DELETE":
		behandleHTTP_DELETE();
	break;
}

?>