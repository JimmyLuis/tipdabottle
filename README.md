# tipdabottle 🇩🇪
tipdabottle ist ein Getränkebuchungssystem für Vereine, Organisationen oder gemeinschaftliche Einrichtungen. Es ermöglicht über ein Endgerät mit Internetzugang digital Getränkeschulden einzutragen. Keine lästigen Getränkelisten mehr, die immer neu ausgefüllt und gedruckt werden müssen. Dabei kann ganz individuell ein Sortiment hinzugefügt, Preise angepasst und Mitglieder verwaltet werden.
> Im frühen Stadium dieses Projekts soll zunächst das Buchen und Bezahlen von Getränken ermöglcht werden. Erst später soll das Sortiment über den Client verändert, sowie Mitglieder hinzugefügt werden können.
> Dieses Projekt wird von mir hauptsächlich in meiner Freizeit entwickelt. Der momentane Projektstatus: **nicht funktionsfähig**, under construction ;)
> 🚧

# Projektstatus
> under construction

# Inhalt
- [Planung und Aufbau](#planung-und-aufbau)
  - [Anforderungen](#anforderungen)
  - [Datenmodell](#datenmodell)
  - [Technologie Stack](#technologie-stack)
    - [Frontend](#frontend)
    - [Backend](#backend)
    - [Datenbank](#datenbank)
  - [UI](#ui)
    - [MockUps](#mockups)
  - [Architektur](#architektur)
    - [Flussdiagramme](#flussdiagramme)
    - [Sequenzdiagramme](#sequenzdiagramme)
  - [Externe Tools](#externe-tools)
# Planung und Aufbau
## Anforderungen
| Priorität  | Kürzel | Anforderung | Status |
|-------|------|---------------------------|-|
| Must | M-M1 | Mitglieder können verschiedene Artikel auf ihre Rechnung hinzufügen. | ❌|
| Must | M-M2 | Mitglieder können ihre Getränkeschulden durch einen Administrator begleichen lassen. | ❌|
| Must | M-M3 | Mitglieder können verschiedene Artikel auf eine gemeinschaftliche Rechnung hinzufügen. | ❌|
| Must | M-M4 | Mitglieder können ihre Schulden einsehen. | ❌|
| Must | M-A1 | Administratoren können über ein Passwort Schulden von Mitgliedern begleichen. | ❌|
|| |
| Should | S-M5 | Mitglieder werden daran erinnert, wenn sie einen gewissen Schuldbetrag überschreiten. | ❌|
| Should | S-M6 | Mitglieder werden gesprerrt, wenn sie einen gewissen Schuldbetrag überschreiten. | ❌|
| Should | S-M7 | Mitglieder können ihre letzen Buchungen einsehen. | ❌|
| Should | S-A2 | Administratoren können Mitglieder oder Gemeinschaftskonten anlegen. | ❌|
| Should | S-A3 | Administratoren können Mitglieder oder Gemeinschaftskonten sperren. | ❌|
| Should | S-A4 | Administratoren können Mitglieder oder Gemeinschaftskonten löschen. | ❌|
| Should | S-A5 | Administratoren können Mitglieder oder Gemeinschaftskonten editieren. | ❌|
| Should | S-M8 | Mitglieder können individuelle Rechnungen erstellen. | ❌|
| Should | S-C1 | Das System kann auf mehreren Endgeräten verwendet werden. | ❌|
|| |
| Could | C-B1 | Eine Schuldenübersicht kann angezeigt, gefiltert und sortiert werden. | ❌|
| Could | C-B2 | Eine Bilanz kann angezeigt werden. | ❌|
| Could | C-B3 | Zahlbereitschaft, -verlauf und verhalten der Mitglieder kann ermittelt werden. | ❌|
|| |
| Nicetohave | N-A6 | Administratoren können ein Sortiment anlegen. | ❌|
| Nicetohave | N-A7 | Administratoren können ein Sortiment bearbeiten. | ❌|
| Nicetohave | N-A8 | Administratoren bekommen automatisch generierte Liste für den nächsten Einkauf. | ❌|
| Nicetohave | N-C1 | Das System ist über öffentliche Netzwerke zugänglich. | ❌|
|| |
| ++1 | AA-B4 | Mitglieder können ihren Schuldbetrag digital begleichen. | ❌|

## Datenmodell
![image](https://github.com/user-attachments/assets/7f1e1a97-7f4f-4ef8-a547-4d43d819ff3d)
Das ER-Modell beschreibt das Bestellsystem, in dem Mitglieder Bestellungen aufgeben, die aus Produkten bestehen.

Mitglieder (Customer) haben eine 1:N-Beziehung zu Bestellungen (Order), da ein Kunde mehrere Bestellungen aufgeben kann.
Bestellungen (Order) enthalten Produkte, die über eine N:1-Beziehung mit der Produkte-Tabelle (Products) verknüpft sind.
Produkte (Products) haben eine feste Verpackung (Container), daher besteht eine 1:1-Beziehung zwischen Produkten und Containern. Ein Container kann also nur eine bestimmte Produktart enthalten (z. B. ein Getränkekasten für eine bestimmte Biersorte).
Wichtig: Bestellungen enthalten nur Produkte, keine Container – die Container sind nur eine Eigenschaft der Produkte. Das Modell bildet somit ein System für den Verkauf von verpackten Produkten ab. 

## Technologie Stack
### Frontend
![JavaScript](https://img.shields.io/badge/javascript-%23323330.svg?style=for-the-badge&logo=javascript&logoColor=%23F7DF1E)
![HTML5](https://img.shields.io/badge/html5-%23E34F26.svg?style=for-the-badge&logo=html5&logoColor=white)
![Vue.js](https://img.shields.io/badge/vuejs-%2335495e.svg?style=for-the-badge&logo=vuedotjs&logoColor=%234FC08D)
![Vuetify](https://img.shields.io/badge/Vuetify-1867C0?style=for-the-badge&logo=vuetify&logoColor=AEDDFF)
### Backend
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
### Datenbank
![SQLite](https://img.shields.io/badge/sqlite-%2307405e.svg?style=for-the-badge&logo=sqlite&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
## UI
### MockUps
- Main Page
  
  ![image](https://github.com/user-attachments/assets/1fbfed75-2894-4c3a-af7a-e5f7672aa214)
- Main Page Popup
  
  ![image](https://github.com/user-attachments/assets/fe084452-0f95-4d93-aa68-abdad2480a95)
- Main Page Popup Selection (click numbers to remove item)

  ![image](https://github.com/user-attachments/assets/0536fca9-4b0e-48fe-af9e-aac4a9ee37a8)

## Architektur
### Flussdiagramme
### Sequenzdiagramme
# Installation & Setup
# Externe Tools
[miroboard](https://miro.com/app/board/uXjVIOwNnL4=/?share_link_id=971222298874)
# Mitwirken
Falls du Ideen hast oder mithelfen möchtest, gerne einen Pull Request oder ein Issue erstellen!
