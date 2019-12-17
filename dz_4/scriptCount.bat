del *.class
del *.java
copy src\*.java
javac Scan.java
javac WordStatCount.java
java -jar WordStatCountTest.jar
pause

