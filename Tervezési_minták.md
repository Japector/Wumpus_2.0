
# *Tervezési minták egy OO programozási nyelvben. MVC, mint modell-nézet-vezérlő minta és néhány másik tervezési minta.*

## *MVC* architektúrális minta

Az MVC egy alkalmazás egészének magas szintű szervezését írja le. A Model-View-Controller (MVC)  a modern webes alkalmazások és felhasználói felületek tervezésének alapját szolgáltata éveken át. A minta az 1970-es évek végéről származik; három, egymással összefüggő összetevőre osztja az alkalmazást. Ezek a Modell, a Nézet és vezérlő, amelyek mindegyike felelős az alkalmazás funkcióinak bizonyos aspektusaiért. Az MVC lényege a feladatmegoszásban rejlik, amely megkönnyíti a modularizációt, leegyszerűsíti a fejlesztési folyamatot, valamint javítja a szoftveralkalmazások méretezhetőségét és karbantarthatóságát.

### Modell
A minta középpontjában áll, és az alkalmazás adatait és üzleti logikáját képviseli. Magába foglalja az alapadatokat és az alkalmazás állapotát, valamint az adatokon végrehajtható műveleteket. A modell független a felhasználói felülettől, és sok alkalmazásban felfogható az alkalmazás által kezelt valós errőforrások reprezentációjaként. Értesíti a Nézetet az állapotában bekövetkezett változásokról, így a kijelző ennek megfelelően frissíthető. Ez a szempont gyakran az Observer tervezési mintáját használja ki, ahol a modell az alap, a nézetek pedig megfigyelők, akik a modell megváltozásakor frissülnek.

### Nézet 
A felület, amellyel a felhasználók interakcióba lépnek; az alkalmazás prezentációs rétegét képviseli. Megjeleníti a modell adatait a felhasználó számára, és értelmezi a felhasználói parancsokat (például az egér- vagy a billentyűkattintásokat), hogy kéréseket küldhessen a modelltől vagy a vezérlőtől. Egy tipikus MVC-alkalmazásban több nézet is lehet egyetlen modellhez. Például egy adat ábrázolható oszlopdiagramként, kördiagramként vagy táblázatként. Ezen ábrázolások mindegyike külön nézetet képviselhet.

### Vezérlő
Közvetítőként működik a modell és a nézet között. Meghallgatja a nézet által összegyűjtött felhasználói inputot, és feldolgozza azokat (pl. érvényesíti, módosítja a modellt). A Vezérlő szétválasztja a modellt a nézetektől, megkönnyítve a bemeneti információ értelmezését, és lehetőséget teremt új módok hozzáadására az alkalmazással való interakcióhoz. Felelős azért, hogy a felhasználói nézeten keresztül közölt szándék a modell által végrehajtandó műveleté váljon.

### Összefoglalva
A felelősségi körök szétválasztása tehát az MVC minta legfontosabb előnye. Az MVC az adathozzáférés és az üzleti logika szétválasztásával az adatmegjelenítéstől a felhasználói interakciókon át rugalmasabbá és könnyebben karbantarthatóbbá teszi az alkalmazásokat. Ez a szétválasztás lehetővé teszi a fejlesztők számára, hogy az alapvető üzleti logika érintése nélkül módosítsák például az alkalmazások megjelenését, és fordítva. Ezenkívül ez a szétválasztás megkönnyíti a fejlesztési folyamatot, ahol a az egyes csoportok elkülönülve fejleszthetik a nézetet, a modellet és a vezérlő egységeket.

Az MVC által biztosított modularitás továbbá elősegíti a tesztelést, mivel minden komponens önállóan, tesztelhető. Az architektúra jól illeszkedik a modern webes alkalmazásokhoz. Az állapot a modellben vagy a munkamenetben megmarad, lehetővé téve a különböző nézetek és vezérlők számára, hogy egymástól függetlenül dolgozzák fel akár az egyes kéréseket.

### Az MVC és tervezési minták kapcsolat tervezési minta vagy paradigma?

Az MVC számos tervezési mintát valósít meg, beleértve az observer-t (mint a modell-nézet kapcsolat) és a strategy-t (a vezérlés és a nézet között). <br><br>

## Tervezési minták

### Design pattern kategorizálás
A tervezési minták (design pattern) taxonómiája során az alábbi legelterjetebb a nappalis órán bemutaott felépítést követtem (a patternek megnevezésére az angol szakkifejezést használom a továbbiakban):
1. Creational:
    * Singleton 
    * Builder 
    * Factory
2. Structural
    * Adapter
    * Decorator
3. Behavioral:
    * Strategy
    * Command
    * Observer

<br>

### Creational patterns

#### 1. Singleton
A Singleton design pattern általában akkor kerül alkalmazásra, mikor egy osztálynak csak egy példányosításra van szükség, és ehhez kell globális hozzáférési pontot biztosítsani. Ez akkor hasznos, ha ez az egyetlen objektum képes összes szükséges művelet végrehajtására. Ilyen eset lehet pédául egy adatbáziskapcsolathoz, vagy naplózási funkcióhoz való hozzáférés, de akár megemlíthető a fájlkezelési funkció is. Általában a megvalósításához az osztály konstruktorát privát elérésűvé kell tenni, és biztosítani egy statikus metódust, amely lehetővé teszi ezen példány visszaadását.

#### 2. Builder
A Builder alkalmazása abban az esetben különösen hasznos, amennyiben egy összetett obijektumot kell felépítenünk lépésről lépésre haladva. Azaz segít elválasztani az objektum felépítésési folyamatát és lehetővé teszi különböző konfigurációk létrehozást. Így tehát egyetlen eljárás különböző konfigurlálási lépéseivel eltérő reprezentációk hozhatók létre. A tervezési minta tehát akkor a leghasznosabb, ha egy objektumot sok lehetséges konfigurációjára van szükség. Ilyen lehet egy olyan program ahol autókat akarunk konfigurálni, azok viszont rengeteg egyedi komponenessel bírnak, így hasznos lehetet a folyamatot a builder pattern segítségével áttekinthetőbbé tenni.

#### 3. Factory
Lehetővé teszi egymással rokonságban álló osztályok létrehozását anélkül, hogy felfedné azoknak egyedi típusát. Ebből adódóan olyan esetekben különösen hasznos, ha szét kell választani az obijektum általános felhasználását és egyedi tulajdonságait. Ennek megvalósítás leggyakrabban kettős absztrakció alkalmazásával történik, ahol egy absztakt osztályból való öröklés után történik az obijektum létrehozása az általános tulajdonságot leíró interfészt implemntáló gyerek által. Az autós példánál maradva példul feltehetjük, hogy vannak gépjármű gyárak, és azok speciális gépjárműveket állítanak elő, autókat, kamionokat stb. Ekkor képezhetünk egy absztrak gépjármű gyárat amitől az egyes gyárak, az autó- és teherautógyár örökölnek. A specializált gyárak pedig olyan járműveket hoznak létre amelyek egyszerre teljesítik a járművek általános felhasználását leíró interfészt követelményeit, valamint a példánál maradva az autókra jellmező speciális tulajdonságokat. 

<br>

### Structural patterns

#### 1. Adapter
Az adapter minta döntő szerepet játszik az inkompatibilis osztályok együttműködésének lehetővé tételében. Növeli a meglévő kód rugalmasságát és újrafelhasználhatóságát, különösen olyan esetekben, amikor az eredeti forráskód módosítása nem praktikus vagy lehetetlen bizonyos korlátok miatt. Lényegében az adapter minta egy új osztály (az adapter) létrehozását foglalja magában, amely lefordítja vagy adaptálja egy meglévő osztály (az adaptált) interfészét a kompatibilitás biztosítása véget. Lényegében becsomagolja a már meglévő osztályt, így bizosítva, hogy a hívó interakcióba léphessen az adaptált oszállyal, anélkül hogy az tudatában lenne az adapter jelenlétével. Ez a beállítás hasonlít ahhoz, ahogyan a hálózati adapter lehetővé teszi, hogy az egyik országból származó eszközt egy másik országban eltérő alakú és feszültségű konnektorban használatát. Típusa szerint Kétféle adapter létezik: osztályadapter és objektum adapter. Az osztályadapterek több öröklődést használnak az egyik interfész a másikhoz való adaptálásához, míg az objektum-adapterek összetételt használnak az általuk burkolt osztály példányának tárolására. 

#### 2. Decorator
Dinamikus megközelítést kínál egy objektum funkcionalitásának kiterjesztésére. Ez a minta alternatívát kínál az örökléssel szemben a viselkedés kiterjesztésére. Azáltal, hogy lehetővé teszi funkcionalitás hozzáadását az egyes objektumokhoz, akár statikusan, akár dinamikusan, anélkül, hogy befolyásolná az azonos osztályba tartozó többi objektum viselkedését, a decorator megfelel a Nyitott/Zárt elvnek, amely egyik S*O*LID-elvenek. Ehhez olyan dekorátor osztályokat definiál, amelyek az eredeti osztályt csomagolják magukban. Minden dekorátor osztály ugyanazon interfész kontraktust teljesíti (implementálja), mint az alap oszály. Így javítva annak viselkedését anélkül, hogy megváltoztatná a kapcsolati viszonyt. Ez a struktúra lehetővé teszi a funkciók rugalmas hozzáadását vagy eltávolítását akár futás közben. Klasszikus példa a GUI toolkit, ahol a grafikus komponensek további funkciókkal bővíthetők. Az adatpterhez hasonlóan legnagyobb előnye, hogy elősegíti az újrafelhasználást.

<br>

### Behavioral patterns

#### 1. Strategy
Különböző viselkedési minták egymással felcserélhető osztályok halmazába való foglalását teszi lehetővé. Elősegítve a szoftverfejlesztés rugalmasabb megközelítését az alkalmazás funkcióinak dinamikus megváltoztatását. Fő eleme az algoritmusok családjának meghatározása, és felcserélhetővé tétele az őket meghívó metódusok számára. Lényegében egy *stratégiai interfész* létrehozását jelenti, amely egy viselkedést reprezentál, és ennek az interfésznek az implementálása a különböző osztályokban teszi lehetővé az eltérő akciókat. Ugyanis z interfészt haszáló kontextus osztály, hivatkozást tart fenn az interfészre (mondjuk egy metóduásnak argumentuma által), és delegálja az adott parancs végrehajtását. Ez a fajta szétválasztása lehetővé teszi új stratégiák hozzáadását vagy a meglévők módosítását a kontextus osztály megváltoztatása nélkül. A laza csatolás és a nyitott/zárt elvek elősegítésével a stratégiai minta javítja a szoftver skálázhatóságát és tesztelhetőségét. Ahogy az a nappalis órán egy fiztési akció különböző módokon történő végrehajtásával szemléltetésre került, ahol lehetőségünk volt akár új mód definiálására is

#### 2. Iterator
Szabványosított módot kínál a gyűjtemények elemeinek egymás utáni elérésére anélkül, hogy felfedné azok mögöttes reprezentációt. Ez a viselkedési minta elősegíti a hatékony kód írását a bejárási mechanizmust elvonatkoztatásával magától a gyűjteménytől, bitosítva a különböző gyűjtemények egységes navigálását. Gyakorlatban két kulcsfontosságú komponensből áll: az iterátorból, ami a bejárásáért és hozzáféréséért felelős, és a bejárt gyűjtemény. Széles körben használják szoftverkönyvtárakban és keretrendszerekben, különösen azokban, amelyek összetett adatstruktúrákat tartalmaznak. Mivel az említett jelemzők növelik az adatkezelés robusztusságát, rugalmasságát és karbantarthatóságát. 

#### 3. Observer
Kulcsfontosságú koncepció olyan helyzetekben, amik magas szintű reagálási képességet és interaktivitást igényelnek. Ez a viselkedési minta 1:N függőséget hoz létre az objektumok között, így amikor egy objektum megváltoztatja állapotát, minden megfigyelő értesítést kap és automatikusan frissíthet. Ez a mechanizmus alapvető fontosságú az eseményvezérelt szituációkban, ahol a program egyik részében elindított műveleteket hatékonyan kell kommunikálni a többi rész felé. A megfigyeltnek (subject) nem kell ismernie a megfigyelők adatait, csak azt, hogy egy bizonyos interfészt implemenálnak. Ez az absztrakció olyan dinamikus kapcsolatot tesz lehetővé, amelyben a megfigyelők dinamikusan kapcsolódhatnak fel, vagy válhatnak le az obijektumról. Ezért a gyakorlati alkalmazásokban sokszor találkozhatunk vele a felhasználói felület tervezésében, ahol a felhasználói műveletek változásokat idézhetnek elő a rendszer állapotában.