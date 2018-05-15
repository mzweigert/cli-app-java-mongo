# app-cli-mzweigert

Aplikacja służąca do wykonywania operacji na danych dotyczących przejazdów taxi w Chicago w 2016r.
Użytkownik w głownym menu ma do dyspozycji opcje znajdywania kolekcji poprzez wypisanie w konsole polecenia
find - znajduje kolekcje po zadanych kryteriach, tzn. pary klucz wartość oddzielonych przecinkami
findAll - znajduje wszystkie kolekcje
findByTaxiId - znajduje rekordy bo taxiId
createIndex - tworzy indeks danego typu (ascending / descending) dla podanych kluczy
Ponadto bedąc w jakieś z opcji "find*" program z każdym rekordem pyta się nas co chcemy z nim zrobić, tzn.
usunać, edytować, ominąć, czy pokazać ponownie oraz wrócić do głownego menu.

Skrypty do importowania danych oraz ich opis znajdują się [tu](https://github.com/nosql/app-cli-mzweigert/tree/master/scripts)

Do uruchomienia projektu potrzebne są

- Java: 1.8

- Maven: 3.3.x

- MongoDB: 3.6.x


#### Uruchomienie projektu
cd /scripts/
./prepare_data.sh
./start_replica.sh
cd ..
mvn install exec:java

### Operacja wyszukania z indeksem

Wykonane zostały dwa zapytania na bazie, pobierające dane po taxi_id i po payment_category na liczbie 1705805 rekordów.

#### payment_category
Wykonanie zapytania db.getCollection('taxitrips').find({ payment_category : "Cash" }).
Czas wykonania: 

- bez indeksu 92.976 sekundy.
- z indeksem 32.175 sekundy.

Następne zapytanie zostało wykonane znacznie szybciej, około 4 sekund.  
Warto podkreślić, że kolumna payment_category może mieć tylko 2 wartości : Cash i Credit.

Drugie zapytanie to db.getCollection('taxitrips').find({ taxi_id : 1234 }).
Czas wykonania: 

- bez indeksu 5.221 sekund.
- z indeksem 0.072 sekundy.

Jak widać jest znaczny skok wydajności w porównaniu do poprzedniego przykładu, ponieważ tu indeks może posortować i zapamiętać miejsce danych, a w pierwszym przykładzie mógł posortować tylko po dwóch wartościach : Cash i Credit Card, a dodatkowo zbiór który pobierał był o wiele większy niż ten pobierany po taxi_id.
