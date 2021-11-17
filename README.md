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
    - Zmiana statusu książki przez użytkowika z uprawnieniami bibliotekarza
- Administrator
    - Uprawnienia bibliotekarza oraz dodawanie i usuwanie książek i informacji o nich
## Wymagania techniczne
- Aplikacja desktopowa
- Napisana w języku Java z wykorzystaniem biblioteki JavaFx
- Dane przechowywane w bazie danych
- Używanie protokołu MQTT do komunikacji między elementami systemu
