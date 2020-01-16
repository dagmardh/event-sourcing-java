Command -> Aggregate -> Event

Ein Event bekommt sämtlichen Payload vom Command plus bei Bedarf weiteren, aber nie weniger

Die recordedEvents eines Aggregate enthalten nur die Änderungen seit der letzten Persistierung

Ein Aggregate muss auf zwei Arten erstellt werden können (Factory-Methoden): Initiale Erstellung, Erstellung aus einem Event-Stream (reconstitute)

Im Zustand eines Aggregates werden nur Daten gehalten, die wirklich benötigt werden, z.B. ist die Id nicht nötig

Business-Logik liegt im Aggregate

keine primitiven Typen verwenden, immer Value Objects erstellen

der Zustand eines Aggregats muss gesetzt werden, wenn ein ensprechendes Event eintritt und wenn das Aggregate aus einem Event-Stream wiederhergestellt wird

# Tests
Nur Whitebox Test

es wird nur Verhalten getestet