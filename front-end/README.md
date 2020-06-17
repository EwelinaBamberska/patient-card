Aby odpalić projekt:

- należy mieć zainstalowanego node.js
- oraz angulara: wpisać w konsoli `npm install -g @angular/cli`

W konsoli:
`npm install`
`ng serve`

Aby nie wyrzucało błędów no access origin, należy uruchomić projekt w specjalnym trybie chrome:

- wyszukać cmd i uruchomić
- wpisać w konsoli: "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" --disable-web-security --disable-gpu --user-data-dir=~/chromeTemp
- otworzy się chrome, orworzyć w nowej karcie http://localhost:4200