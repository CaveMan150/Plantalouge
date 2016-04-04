# Plantalouge
In order to demo this web application you need to follow the instructions below


Install netbeans:
follow instuctions below:
Apache tomcat 8.0.27.0  however it is better that you download wampserver you can get it from here -> http://iweb.dl.sourceforge.net/project/wampserver/WampServer%203/WampServer%203.0.0/wampserver3_x64_apache2.4.17_mysql5.7.9_php5.6.16_php7.0.0.exe

1. install wamp and run it
   NOTE! You might get an error during the installation states that "The program can't start because VCRUNTIME140.DLL is missing from your computer, try reinstalling the program to fix this problem"
To fix this problem download the follwing: 



2. Copy this URL https://github.com/CaveMan150/Plantalouge.git
Go in netBeans and click on "Team" -> Git -> Clone -> Paste Repository URL 
put your username and password if you want or you can leave it blank.

3.  Go netbeans -> services-> click on databases-> new connection -> click on driver -> choose MYSQL(connector/driver) 
 -In the driver file, click on Add -> find the jar file that you donwloaded from this project which is named "mysql-connector-java-5.1.38-bin" then click next UserName should be "root" , Database should be "plant_db"
 Now you can either test the connection or just click on next ->next then finish.




if mysql connector jar file that was included within this project does not work or for anyother reason you can go to www.mysql.com 







