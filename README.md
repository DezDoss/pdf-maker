# pdf-maker
Программа для генерации пдф файлов. В папке blanks хранятся бланки карт.

Программой можно управлять запуская main метод в классах которые находятся под src/main/java/...

Пример: src/main/java/konaev/NewFolder.java
1) Внутри класса есть несколько методов, но основной main метод, который запускает генерацию пдф
2) <b>static long bar=</b> отвечает за нумерацию
3) <b>COORDINATE_X, COORDINATE_Y</b> отвечают за координату карт на пдф файле
4) <b>blankBackPath</b> отвечает какую карту выбрать, бланка карт находятся в папке blanks, нужную карту надо указать с корня, например, если выбрать стандартную карту из конаева то, blankBackPath = "blanks/konaev/blue-front.png"
5) <b>setQRCode, setText</b> отвечают за генерацию QRCode и для записи баркода в карте соответственно. Методы принимают на вход параметры: BarCode, позицию X, позицию Y, пдф файл которую записывают, и размер шрифта
6) Если нужно генерировать обратную сторону карты, то можно убрать методы setQRCode, setText, чтобы тоже печатали на обратной стороне... или можно комментировать добавляя в начале //(двойной слэш), пример:
   <br>
   <code>if (column == 1) {
   <br>
   setImage(blankBackPath, COORDINATE_X * 2 + 86 - 160, coordinateY - 40, backContent);
   <br>
   //setQRCode(barCode, COORDINATE_X * 2 + 86 + 1f + 41, coordinateY - 33.5f, backContent);
   <br>
   //setText(editedBarCode, COORDINATE_X * 2 + 111, coordinateY + 43, backContent, 16);
   <br>   
   column++;
   <br>
   } else {
   <br>
   setImage(blankBackPath, COORDINATE_X - 159, coordinateY - 40, backContent);
   <br>
   //setQRCode(barCode, COORDINATE_X + 8 + 35f, coordinateY - 33.5f, backContent);
   <br>
   //setText(editedBarCode, COORDINATE_X + 26, coordinateY + 43, backContent, 16);
   <br>
   column = 1;
   <br>
   coordinateY -= moveY;
    <br>
    }

<h1>Генерация файлов</h1>

public static void main(String[] args) throws IOException, DocumentException {

    for (int j = 1; j <= 1; j++) {
      List<String> invalidList = new ArrayList<>();
      for (int i = 1; i <= 20; i++) {
        invalidList = createFile(i);
      }
      String sourceFile = "result";
      FileOutputStream fos = new FileOutputStream("zip/" + j + "dirCompressed.zip");
      ZipOutputStream zipOut = new ZipOutputStream(fos);
      File fileToZip = new File(sourceFile);


      zipFile(fileToZip, fileToZip.getName(), zipOut);
      zipOut.close();
      fos.close();

      for (String str : invalidList) {
        File file = new File(str);
        file.deleteOnExit();
      }
    }
}

1) Первый цикл for(int j = 1; j <= 1; j++) отвечает на сколько zip файлов разделить, можно менять j <= n, где n = номер на которую вы хотите делить
2) Второй цикл for(int i = 1; i <= 20; i++) отвечает сколько файлов будет, в одном файле будут 10 карт, тоже можно менять количество как на первом цикле. Диапазон номеров карты будут между bar ... bar + n * m * 10, где n = количество zip файлов, m = количество пдф файлов
3) После генерации пдф файлы будут архивированы и сохранены в папке zip/"m" + dirCompressed.zip, где "m" = номер zip файла 