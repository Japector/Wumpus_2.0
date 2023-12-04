# Wumpus játék
<br>

## Játék menete
1. A játék a felhasználó nevének bekérésével kezdődik
2. Ez követően a ki kell választani, hogy mely reposiotry-t akarjuk használni:
    * (1) - Json
    * (2) - Xml
    * (3) - Jdbc

3. Ezt követően egy scoreboard fogad, amely megmutatja, hogy a repository-ban tárolt adatok alapján eddig ki és hányszor nyert.

4. Végül megjelenik a menü, ahol választhatunk az alábbi opciók közül:

    * (1) - Create map
    * (2) - Read map from file
    * (3) - Load game from database
    * (4) - Save game into database
    * (5) - Play game
    * (6) - Quit

A menüpontok közül az 1-6 billenyűk lenyomásával navigálhatunk. 

*Megjegyzés:* Megoldása során a fájlból való beolvasását választottam, de ettől függetlenül implementáltam a térképszerkeztőt is.

## Egyes opciók leírása

#### **(1) - Create map**
Ezen opciót választva a felhasználónak először meg kell adnia, hogy pontosan milyen méretű térképet szeretne (5 < x < 21). Majd a szerkesztőben hozzáadhatja illetve elveheti az elemeket az alábbi parancsformátumnak megfelelően:
* Elem hozzáadása: *ADD + reguláris kifejezés: [WUGPH] [B-T] (1[0-9]|20|[2-9])*
* Elem eltávolítása: *REMOVE + reguláris kifejezés: [WUGPH] [B-T] (1[0-9]|20|[2-9])*
* Hős irányának megadása: *DIRECTION + reguláris kifejezés: [NESW]*
* Tábla validálása: *FINISH*

*Megjegyzés:* A hős karakter a tábla bal felső sarkába alapértelemzetten hozzáadódik. Levenni a tábláról nem lehet, viszont az ADD parancs használatával áthelyezhető tetszőleges valid mezőre.


#### **(2) - Read map from file**
Ezen opciót választva a felhasználónak meg kell adnia a beolvasni kívánt input fájl elérési útját, ami megfelel a feladatban szerepelő elvárásoknak. A beolvasást követően a validáció autómatikusan lefut.


#### **(3) - Load game from database**
Ezen opciót választva a felhasználónak meg kell adnia a beolvasni kívánt játék azonosítóját. Ebben egy táblázat lesz a segítségére, ahonnan a mentett állások legfőbb jellemzői elérhetőek (legutóbbi állás mindig az utolsó rekordban). Ha sikeres volt a beolvasás akkor Game successfully loaded! felirat jelenik meg.


#### **(4) - Save game into database**
Ezen opciót választva a felhasználónak lementheti a játékot adatbázisba, ami onnan visszatölthető. (Vesztett állás és feladás esetén nincs lehetőség visszatöltésre.)


#### **(5) - Play game**
Elindítja a játékmenetet, ahol az alábbi módon navigálhatunk:
* a - Balra fordulás
* d - Jobbra fordulás
* w - Lépée
* ' ' - Nyíl kilövése
* s - Arany felszedése
* q - Játék feladása (nem lesz lehetőség későbbi folytatásra)
* p - Játék elhalasztása (autómatikusan mentődik a játékmenet és visszatölthető, illetve folytatható) 

#### **(6) - Quit**
Kilép a wumpus játékból.

<br>

*Megjegyzés:* Amennyiben az artifact-et akarjuk futtatni konzolba, úgy előtte adjuk ki a chcp 65001 parancsot, az UTF-8 karakterek miatt.

<br>

## Előfeltétel az adatbázis műveletekhez.
Amennyiben szeretnénk adatbázisból betölteni játékot, illetve oda menteni úgy futnia kell a H2-nek, és léteznie kell a felhasználói könyvtárban (~) egy wumpusz nevű adatbázisnak admin felhasználónév és jelszó mellett. Ellenkező esetben runtime error mellett fog indulni a program és nem lesz lehetőség a 3-4 menüpontok használatára.


## Beadandó dolgozat
A tárgy teljesítéséhez szüksége *Tervezési minták egy OO programozási nyelvben. MVC, mint modell-nézet-vezérlő minta és néhány másik tervezési minta.* című dolgozat megtalálható jelen repositoryban Tervezési_minták.md név alatt.