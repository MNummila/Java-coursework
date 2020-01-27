
/*
Työssä käytettyjä komentoja

Komennoissa on sellaiset arvot että tiedoston voi ajaa suoraan .readilla

INSERTIT
*/

INSERT INTO smartpost (automaattiID,postinumero,aukioloajat,postitoimisto,katuosoite,latitude,longitude,luotu_ei)
    VALUES(1,'00104','ma-la 7.00 - 22.00, su 10.00 - 22.00','Smartpost, Lidl Graniittitalo','Salomonkatu 13',
    	'60.1695913','24.9319675','false');

INSERT INTO osoitteet (postinumero,kaupunki)
    VALUES('00104','HELSINKI');

INSERT INTO paketti 
	VALUES (1,500,400,'hajoaa','150');

INSERT INTO esineet (esineID,nimi,koko,paino,hajoaa)
    VALUES (null,'autonrengas',50,25,'false');

INSERT INTO luodutpaketit
    VALUES (null,1,null,1,'Smartpost, Lidl Graniittitalo','Smartpost, Lidl Graniittitalo','0.0');

INSERT INTO varasto 
    VALUES (1,CURRENT_TIMESTAMP);

INSERT INTO automaatissaolevatpaketit
	VALUES(1,1,CURRENT_TIMESTAMP);
/*
UPDATET
*/
UPDATE smartpost 
	SET luotu_ei = 'true' 
	WHERE automaattiID = 1;

UPDATE luodutpaketit 
	SET varastoID = '1'
	WHERE pakettiID = '1';
/*
SELECTIT
*/
SELECT latitude,longitude FROM smartpost WHERE postitoimisto = 'Smartpost, Lidl Graniittitalo';

SELECT latitude,longitude FROM smartpost WHERE postitoimisto = 'Smartpost, Lidl Graniittitalo';

SELECT * FROM smartpost 
	INNER JOIN osoitteet ON osoitteet.postinumero = smartpost.postinumero
    WHERE osoitteet.kaupunki = 'HELSINKI';

SELECT DISTINCT kaupunki FROM osoitteet ORDER BY kaupunki;

SELECT * FROM smartpost INNER JOIN osoitteet ON osoitteet.postinumero = smartpost.postinumero;

SELECT nimi FROM esineet;

SELECT luokka FROM paketti;

SELECT esineID FROM esineet WHERE nimi = 'autonrengas';

SELECT * FROM esineet;

SELECT luodutpaketit.pakettiID,nimi,luokka,lahtopaikka,maaranpaa,matka,postitoimisto,saapumisaika,varastointiaika 
    FROM luodutpaketit INNER JOIN esineet ON luodutpaketit.esineID = esineet.esineID 
    LEFT OUTER JOIN automaatissaolevatpaketit ON 
    luodutpaketit.pakettiID = automaatissaolevatpaketit.pakettiID 
    LEFT OUTER JOIN smartpost ON automaatissaolevatpaketit.automaattiID = smartpost.automaattiID
    LEFT OUTER JOIN varasto ON luodutpaketit.varastoID = varasto.varastoID;

SELECT esineet.nimi,luodutpaketit.luokka,luodutpaketit.pakettiID 
    FROM luodutpaketit 
    INNER JOIN esineet ON luodutpaketit.esineID = esineet.esineID 
    INNER JOIN varasto ON varasto.varastoID = luodutpaketit.varastoID;

SELECT COUNT(*) AS rows FROM esineet;

SELECT COUNT(*) AS rows FROM smartpost;

SELECT COUNT(*) AS rows FROM paketti;

SELECT postitoimisto FROM smartpost
	WHERE luotu_ei = 'true';

SELECT automaattiID FROM smartpost
    WHERE luotu_ei = 'true';

SELECT hajoaa FROM luodutpaketit 
	INNER JOIN esineet ON luodutpaketit.esineID = esineet.esineID 
    WHERE pakettiID = 1;

SELECT LAST_INSERT_ROWID();

SELECT automaattiID FROM smartpost 
  	WHERE latitude = '60.1695913';

SELECT smartpost.latitude, smartpost.longitude FROM smartpost 
    INNER JOIN luodutpaketit ON smartpost.postitoimisto = luodutpaketit.lahtopaikka 
    WHERE luodutpaketit.pakettiID = 1;

SELECT smartpost.latitude,smartpost.longitude FROM smartpost 
    INNER JOIN luodutpaketit ON smartpost.postitoimisto = luodutpaketit.maaranpaa 
    WHERE luodutpaketit.pakettiID = 1;

SELECT koko FROM esineet WHERE esineID = 1;

SELECT paino FROM esineet WHERE esineID = 1;

SELECT koko FROM paketti WHERE luokka = 1;

SELECT paino FROM paketti WHERE luokka = 1;
/*
DELETET
*/
DELETE FROM esineet WHERE nimi = 'autonrengas';

DELETE FROM luodutpaketit WHERE pakettiID = 1;

DELETE FROM varasto WHERE varastoID = 1;

DELETE FROM automaatissaolevatpaketit WHERE pakettiID = 1;

DELETE FROM smartpost;
DELETE FROM luodutpaketit;
DELETE FROM osoitteet;
DELETE FROM esineet;
DELETE FROM paketti;
DELETE FROM varasto;
DELETE FROM automaatissaolevatpaketit;