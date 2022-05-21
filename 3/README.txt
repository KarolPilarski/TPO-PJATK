Zadania

(1)  Parsowanie plików YAML

W klasie Tools dostarczyć metody:

    static Options createOptionsFromYaml(String fileName) throws Exception

która wczytuje podany plika YAML i na jego podstawie tworzy obiekt klasy Options. Klasa Options jest w projekcie.

Do parsowania użyć biblioeki SnakeYaml (https://mvnrepository.com/artifact/org.yaml/snakeyaml/1.26).

Przykładowa zawartość pliku YAML o nazwie PassTimeOptions.yaml jest nastepująca (proszę go umiescic w katalogu "user.home"):

host: localhost
port: 7777
concurMode: true   # czy klienci działają równolegle?
showSendRes: true  # czy pokazywać zwrócone przez serwer wyniki metody send(...)
clientsMap: # id_klienta -> lista żądań
  Asia:
    - 2016-03-30T12:00 2020-03-30T:10:15
    - 2019-01-10 2020-03-01
    - 2020-03-27T10:00 2020-03-28T10:00
    - 2016-03-30T12:00 2020-03-30T10:15
  Adam:
    - 2018-01-01 2020-03-27
    - 2019-01-01 2020-02-28
    - 2019-01-01 2019-02-29
    - 2020-03-28T10:00 2020-03-29T10:00

(2)  Raportowanie upływu czasu.

W klasie Time dostarczyć metody:

public static String passed(String from, String to)

zwracającej tekst opisujący upływ czas od daty from do daty to.

Daty są podawane jako napisy w formacie ISO-8601:
- data bez czasu: YYYY-MM-DD
- data z czasem: YYYY-MM-DDTGG:MM

Przyklady dat zawiera przykładowy plik  YAML.

Opis upływającego czasu ma formę:

Od x nazwa_miesiąca_PL (dzień_tygodnia_PL) do y nazwa_miesiąca_PL (dzień_tygodnia_PL)
- mija: d dni, tygodni t
[- godzin: g, minut: m ] 
[- kalendarzowo: [ r (lat|lata|rok}, ] [ m (miesięcy|miesiące|miesiąc ),]  [d  (dzień|dni)]  ]

gdzie x, y, d, g, m, r, - liczby całkowite
         t - jest liczbą całkowitą, gdy liczba tygodni jest całkowita, a rzeczywistą (dwa miejsca po kropce) w przeciwnym razie

Nawiasy kwadratowe oznaczają opcjonalność:
a) część opisująca upływ godzin i minut pojawia się tylko wtedy, gdy w formacie daty użyto czasu (np. 2020-10-10T10:00)
b) część "kalendarzowo:" pojawia się tylko wtedy, gdy minął co najmniej jeden dzień
c) w części kalendarzowej opis upływu lat pojawia się tylko wtedy, gdy minął co najmniej jeden rok, opis upływu miesięcy pojawia się tylko wtedy, gdy minął co najmniej jeden miesiąc, a opis upływu dni pojawia się tylko wtedy, gdy liczba dni nie mieści się w pełnych miesiącack.