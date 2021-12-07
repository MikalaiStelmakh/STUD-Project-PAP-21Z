Zespół 11:
- Mikalai Stelmakh
- Denys Savytskyi
- Roman Ishchuk
# System wypożyczania książek
Celem projektu jest stworzenie aplikacji zarządzania biblioteką.
## Wymagania funkcjonalne
- Użytkownicy z różnymi poziomami uprawnień
- Aplikacja przechowuje dane o książkach, autorach, roku wydania i inne kluczowe informacje
### Rodzaje uprawnień oraz ich możliwości
- Zwykły użytkownik
    - Przeglądanie wszystkich dostępnych książek wraz z informacją o nich
    - Przeglądanie wypozyczonych przez niego książek wraz z informacją o nich
    - Rezerwacja książki na określony czas
    - Zwrot wypożyczonej książki
- Bibliotekarz
    - Przeglądanie wszystkich dostępnych książek wraz z informacją o nich
    - Przeglądanie wypozyczonych przez pewnego użytkownika książek wraz z informacją o nich
    - Zmiana statusu książki
- Administrator
    - Uprawnienia bibliotekarza oraz dodawanie i usuwanie książek i informacji o nich
## Wymagania techniczne
- Aplikacja desktopowa
- Napisana w języku Java z wykorzystaniem biblioteki JavaFx
- Dane przechowywane w bazie danych
- Używanie protokołu MQTT do komunikacji między elementami systemu
## Wstępne zadania
1. Zaprojektowanie, stworzenie bazy danych oraz podłączenie jej do programu - Denys Savytskyi.
1. Zaprojektowanie oraz stworzenie GUI - Mikalai Stelmakh.
1. Implementacja wstępna, do poruszania się po programie - Roman Ishchuk.

#### Wszystkie zadania robimy razem, ale do każdego z nich jest przypisana jedna osóba odpowiedzialna, która kontroluje proces wykonania danego zadania.
# Wyniki pośrednie
Do tej pory zostało stworzono:
 - Interfejs logowania, ![LogIn interface](/src/main/resources/z11/libraryapp/img/docs/SignIn.png)
 - interfejs rejestracji, ![LogIn interface](/src/main/resources/z11/libraryapp/img/docs/SignUp.png)
 - interfejs głównego okna albo też sekcja `Dashboard`
 ![LogIn interface](/src/main/resources/z11/libraryapp/img/docs/MainWindow.png)

Nazwa oraz autor książek są ładowane z bazy danych podłączonej do aplikacji.
## Plan dalszego rozwoju aplikacji
### Alternatywny interfejs dla administratora
Jeśli logujący użytkownik jest administratorem, będzie miał on inny interfejs niż inni użytkownicy. ten interfejs nie musi być tak piękny jak interfejs użytkownika, ale musi być efektywny i łatwy w użyciu.
### Sekcja `Authors`
Strona autorów będzie wyglądać podobnie do strony `Dashboard`, czyli będzie podział na ostatnio dodanych autorów oraz na wszystkich autorów, których książki znajdują się w bibliotece.
### Sekcja `Categories`
Na tej stronie książki będą podzielone na kategorie. Dla każdej kategorii będzie wyświetlonych kilka książek, aby zobaczyć wszystkie książki w tej kategorii użytkownik będzie mógł kliknąć na nazwę kategorii, aby przejść do innej strony.
### Sekcja `Reading`
Na tej stronie będzie znajdowała się lista wypożyczonych przez użytkownika książek. Będzie widoczna data wypożyczenia książki oraz data do której tą książkę należy zwrócić.
### Sekcja `History`
Ta strona będzie zawierała historię użytkownika o wypożyczaniu książek.
### Wyszukiwanie
Planowane, że pole wyszukiwania będzię takie same na wszystkich stronach aplikacji i będzie możliwość wyszukiwania dla wszystkich parametrów książki, to znaczy, że na stronie `Authors` można będzie wyszukać książkę też po jej nazwie, a nie tylko autorze.
### MQTT
Planowane, żeby przy pomocy protokołu MQTT książka sama mogła wyznaczyć, czy jest wypożyczona, czy dostępna do wypożyczenia. W takim razie nie jest potrzebny bibliotekarz, bo książka sama będzie przesyłała informację, że została wypożyczona lub zwrócona.
