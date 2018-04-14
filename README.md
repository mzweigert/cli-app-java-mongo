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
