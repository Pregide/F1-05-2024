Pour que le fichier FXMLDemo s'éxécute correctement :
- avoir les librérie de JavaFX
- insérer dans launch.json (à adapter):
```json
"vmArgs": "--module-path /home/thomas/Bureau/IHM/lib --add-modules=javafx.controls,javafx.fxml"
```