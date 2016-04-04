# Plantalouge
In order to demo this web application you need to follow the instructions below

Make sure you have the redistributables, if you don't wamp will not work and will give you an error read step 1
Install netbeans:
follow instuctions below:
Apache tomcat 8.0.27.0  however it is better that you download wampserver you can get it from here -> http://iweb.dl.sourceforge.net/project/wampserver/WampServer%203/WampServer%203.0.0/wampserver3_x64_apache2.4.17_mysql5.7.9_php5.6.16_php7.0.0.exe

1. install wamp and run it
   NOTE! You might get an error during the installation states that "The program can't start because VCRUNTIME140.DLL is missing from your computer, try reinstalling the program to fix this problem"
To fix this problem download the follwing:
Uinstall Wamp server
and install the following:
"-- VC9 Packages (Visual C++ 2008 SP1) http://www.microsoft.com/en-us/download/details.aspx?id=5582 http://www.microsoft.com/en-us/download/details.aspx?id=2092

-- VC10 Packages (Visual C++ 2010 SP1) http://www.microsoft.com/en-us/download/details.aspx?id=8328 http://www.microsoft.com/en-us/download/details.aspx?id=13523

-- VC11 Packages (Visual C++ 2012 Update 4) The two files VSU4\vcredist_x86.exe and VSU4\vcredist_x64.exe to be download are on the same page: http://www.microsoft.com/en-us/download/details.aspx?id=30679 -- VC13 Packages[/b] (Visual C++ 2013[)

The two files VSU4\vcredist_x86.exe and VSU4\vcredist_x64.exe to be download are on the same page: https://www.microsoft.com/en-us/download/details.aspx?id=40784

-- VC14 Packages (Visual C++ 2015) The two files vcredist_x86.exe and vcredist_x64.exe to be download are on the same page: http://www.microsoft.com/fr-fr/download/details.aspx?id=48145"
For more information here:
http://stackoverflow.com/questions/34215395/wamp-wont-turn-green-vcruntime140-dll-error


2. Copy this URL https://github.com/CaveMan150/Plantalouge.git
Go in netBeans and click on "Team" -> Git -> Clone -> Paste Repository URL 
put your username and password if you want or you can leave it blank.

3.  Go netbeans -> services-> click on databases-> new connection -> click on driver -> choose MYSQL(connector/driver) 
 -In the driver file, click on Add -> find the jar file that you donwloaded from this project which is named "mysql-connector-java-5.1.38-bin" then click next UserName should be "root" , Database should be "plant_db"
 Now you can either test the connection or just click on next ->next then finish.




if mysql connector jar file that was included within this project does not work or for anyother reason you can go to www.mysql.com 







