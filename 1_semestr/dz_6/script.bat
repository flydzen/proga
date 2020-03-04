del *.class
del *.java
copy src\*.java
javac Scan.java
javac WordStatIndex.java
java -jar WordStatIndexTest.jar
pause

