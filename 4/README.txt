Serwer (klasa Server) podaje informacje o mijającym czasie. Klienci (klasa Client) :
a) łączą się z serwerem
b) wysyłają żądania

Dla każdego klienta serwer prowadzi log jego zapytań i ich wyników oraz ogólny log wszystkich żądań wszystkich klientów. Logi są realizowane w pamięci wewnętrznej serwera (poza systemem plikowym).

Protokół

Żądanie	Odpowiedź	Przykład
login id	logged in	login Adam
dataOd dataDo	opis upływu czasu wg secyfikacji z S_PASSTIME	2019-01-20 2020-04-01          
bye	logged out	
bye and log transfer	zawartośc logu klienta	w przykładowym wydruku z działania klasy Main


Budowa klasy Server

konstrukto: :
public Server(String host, int port)

Wymagane metody:
 metoda: public void startServer() - uruchamia server w odrębnym wątku,
 metoda:  public void stopServer()  - zatrzymuje działanie serwera i wątku w którym działa
 metoda: String getServerLog() - zwraca ogólny log serwera
Wymagania konstrukcyjne dla klasy Server
multipleksowania kanałów gniazd (użycie selektora).
serwer może obsługiwać równolegle wielu klientów, ale obsługa żądań klientów odbywa się w jednym wątku
Budowa klasy Client

konstruktor:
public Client(String host, int port, String id), gdzie id - identyfikator klienta

Wymagane metody:
metoda: public void connect() - łączy z serwerem
metoda: public String send(String req) - wysyła żądanie req i zwraca odpowiedź serwera
Wymagania konstrukcyjne dla klasy Client
nieblokujące wejście - wyjście

Dodatkowo stworzyć klasę ClientTask, umożliwiającą uruchamianie klientów w odrębnych wątkach poprzez ExecutorService.
Obiekty tej klasy tworzy statyczna metoda:

     public static ClientTask create(Client c, List<String> reqs, boolean showSendRes)

gdzie:
c - klient (obiekt klasy Client)
reqs - lista zapytań o uplyw czasu

Kod działający w wątku ma wykonywać następując działania:
łączy się z serwerem,
wysyła żądanie "login" z identyfikatorem klienta
wysyła kolejne żądania z listy reqs
wysyła "bye and log transfer" i odbiera od serwera log zapytań i ich wyników dla danego klienta
Jeżeki parametr showSendRes jest true to po każdym send odpowiedź serwera jest wypisywana na konsoli. Niezależnie od wartości parametru należy zapewnić, by log klienta był dostępny jak tylko klient zakończy działanie.

Dodatkowo dostarczyć klasy Time (logika obliczania czasu) oraz Tools (wczytywanie opcji i żadań klientów, potrzebnych do dzialania klasy Main).