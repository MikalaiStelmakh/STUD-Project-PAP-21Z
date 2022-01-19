# System wypożyczania książek
### Zespół 11
- Mikalai Stelmakh
- Denys Savytskyi
- Roman Ishchuk
### Opiekunowie
- **PAP**: Gracki Krzysztof
- **BD**: Szmurło Agnieszka
## Opis projektu
Celem projektu jest stworzenie aplikacji do ułatwienia używania/zarządzania biblioteką zarówno dla użytkowników biblioteki jak i dla jej administatorów.

### Założenia techniczne
- Aplikacja desktopowa
- Napisana w języku Java z wykorzystaniem biblioteki JavaFx
- Dane przechowywane w bazie danych
- Testowanie za pomocą biblioteki *junit 5.8.2*
- Używanie protokołu MQTT do komunikacji między elementami systemu

W ramach projektu są dwa poziomy uprawnień: użytkownik oraz administrator. Każdy z nich ma osobną aplikację, wybieraną po zalogowaniu się na swoje konto.

Niżej przedstawiono uprawnienia każdej z dwóch grup:
### Użytkownik
- Przeglądanie wszystkich dostępnych książek wraz z informacją o nich
- Przeglądanie wszystkich dostępnych autorów wraz z inforamcją o nich
- Przeglądanie wszystkich dostępnych kategorii książek
- Przeglądanie wypozyczonych przez niego książek wraz z informacją o nich
- Rezerwacja, odbiór oraz zwrot książki
### Administrator
Przeglądanie informacji oraz możliwość dodawania/usuwania:
- Książek i ich atrybutów
- Autorów i ich atrybutów
- Użytkowników
## Opis rozwiązania
Każdy użytkownik może stworzyć własne konto, które będzie używane do logowania do biblioteki oraz wypożyczania książek.

![SignUp interface](/src/main/resources/z11/libraryapp/img/docs/SignUp.png)

Po zarejestrowaniu informacja o uzytkowniku wysyłana jest do bazy danych, gdzie jest przechowywana do momentu ew. usunięcia użytkownika.

Po zarejestrowaniu użytkownik może zalogować się na swoje konto.
![SignIn interface](/src/main/resources/z11/libraryapp/img/docs/SignIn.png)



Po zalogowaniu się użytkownik jest przekierowywany do aplikacji użytkownika, a szczególnie do strony z książkami

![MainWindow](/src/main/resources/z11/libraryapp/img/docs/MainWindow.png)
Na tej stronie dostępne są wszystkie książki przechowywane w bibliotece, 6 ostatnio dodanych książek są dodatkowo wyświetlane jako `Recently Added`



Następnie użytkownik może albo przejść na stronę konkretnej książki albo do innych stron aplikacji, pokazanych niżej:

`Authors`

![Authors](/src/main/resources/z11/libraryapp/img/docs/Authors.png)
oraz strony konkretnego autora,



![Author](/src/main/resources/z11/libraryapp/img/docs/Author.png)
`Genres`

![Genres](/src/main/resources/z11/libraryapp/img/docs/Genres.png)
oraz strony konkretnej kategorii.



![Genre](/src/main/resources/z11/libraryapp/img/docs/Genre.png)



Jeśli użytkownik chce zobaczyć informację na temat konkretnej książki, dostaje następującą stronę:

![Book](/src/main/resources/z11/libraryapp/img/docs/Book.png)
Oprócz informacji o tej książce użytkownik ma możliwość rezerwacji tej książki dla jej późniejszego wynajęcia.

Założyliśmy, że nasza biblioteka będzie miała na swoim terenie skaner do obsługi wynajęcia/zwracania książek, dzięki czemu nie potrzebujemy w naszej bibliotece żadnych ludzi do jej obsługi. Działa to w następujący sposób:
1. Użytkownik rezerwuje książkę i dostaje jej unikalny identyfikator,
1. Przychodzi do biblioteki i wyszukuje zarezerwowanej przez siebie książkę,
1. Skanuje na kasie książkę oraz 'kartę czytelnika',
1. Jeśli rezerwacja danej książki dla tego użytkownika istnieje, dostaje jej w wypożyczenie.

W celu symulacji takiego skanera zrobiliśmy osobną aplikację na Androida, która jako parametry pobiera id użytkownika i id książki i za pomocą brokera MQTT wysyła dane na serwer o jej wynajęciu/zwracaniu. Poniżej pokazany jest wygląd tej aplikacji:

![AndroidApp](/src/main/resources/z11/libraryapp/img/docs/AndroidApp.jpg)

Zmianę statusu książki użytkownik może zaobserwować na stronie `Reading` aplikacji użytkownika.

Po rezerwacji książki:

![ReservedBook](/src/main/resources/z11/libraryapp/img/docs/ReservedBook.png)



Po jej wynajęciu:

![BorrowedBook](/src/main/resources/z11/libraryapp/img/docs/BorrowedBook.png)



Po jej zwrocie:

![ReturnedBook](/src/main/resources/z11/libraryapp/img/docs/ReturnedBook.png)

## Baza Danych
### Model ER
*`[TODO]`*
<!-- ![ERModel](/src/main/resources/z11/libraryapp/img/docs/ERModel.png) -->

### Model Relacyjny
![RelativeModel](/src/main/resources/z11/libraryapp/img/docs/RelativeModel.png)

## Podział pracy
**Mikalai Stelmakh** - stworzenie aplikacji użytkownika, stron logowania oraz rejestracji, dokumentacja

**Denys Savytskyi** - baza danych oraz klasa do jej obsługi z poziomu javy, stworzenie aplikacji mobilnej do sumylacji odbioru/zwrotu książek

**Roman Ishchuk** - Implementacja wstępna, interfejs panelu administracyjnego oraz logowania

## Kierunek dalszego rozwoju projektu
Oczywyście nie zdążyliśmy spełnić wszystkie ustawione przez nas na etapie planowania wymagania. Na zaimplementowanie niektórych z nich nie wystarczyło nam czasu, a niektóre okazały się mniej priorytetowe niż pozostałe. Poniżej przedstawiamy listę elementów wartych zaimplementowania i dodania do projektu:
- Dodanie do strony wyszukiwania możliwość filtracji wyników wyszukiwania po wszystkich atrybutach książki/autora,
- Dodanie mechanizmu rekomendacji książek/autorów dla poszczególnych użytkowników na podstawie ich zainteresowań,
- Dodanie możliwości dla użytkownika usunięcia swojego konta,

