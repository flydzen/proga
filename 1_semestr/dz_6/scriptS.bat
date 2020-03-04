del *.class
del *.java
copy src\*.java
javac Scan.java
javac IntList.java
javac WordStatSortedLastIndex.java
java -jar WordStatSortedLastIndexTest.jar
pause

