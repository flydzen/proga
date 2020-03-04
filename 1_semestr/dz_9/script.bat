del *.class
del *.java
copy src\*.java
javac md2html.Scan.java
javac md2html.Md2Html.java
java -jar Md2HtmlTest.jar

