# go
Go

Komunikacja klient-serwer:

0)Nowe połączenie:
S: WELCOME
C: CONNECTIONOK

1) Ustawianie imienia
S: SETNAME
C: USERNAME [myname]
S: (a) NAMEOK gdy zaakceptowane, (b) NAMETAKEN gdy już zajęte
C: (b) USERNAME
...

2) Wybór przeciwnika
C: LIST
S: LIST [lista graczy]
C: OPPONENT [opname]
S (do [myname]): czeka lub CHOOSEOPPONENTAGAIN [lista graczy]
S (do [opname]): INVITATIONFROM [myname]
C[oppname]: (a) INVAGREE [myname] lub (b) INVREJECT [myname] (TODO: zaimplementować odrzucanie)
S: (a) GAMESTART [color] lub CHOOSEOPPONENTAGAIN [lista graczy], gdy gracz się rozłączył, (b) CHOOSEOPPONENTAGAIN [lista graczy]

3) Rozgrywka
