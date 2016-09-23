
 System Requirements
  -------------------
  JDK: 1.7 or above.
  SERVER: Apache Tomcat 8.0.35
  DATABASE: MySQL 5.7.12
  MAVEN: any version
  
  The application was tested at Windows 7 system
  
 Preparing actions
  --------------------
  Check that you have specified all of the path variables.
  Such as JAVA_HOME and M2_HOME.
  
  Create a database using one of SQL Scripts from the root folder of the project.
  rcshop_db_DAMP_DB_22_09_2016.sql - creates a database with the name "rcshop_db" and fills it the data.
  rcshop_db_TABLES_DAMP_DB_22_09_2016.sql - creates the only tables with data. In this case you must create a database yourselves.
  
  Open the RCShopModular\WebApp\src\main\webapp\WEB-INF\database.properties file and change the database settings.
  You need to change the username, password and url values.
 ************************************************************************************************** 
    jdbc.url	  = jdbc:mysql://localhost:3306/rcshop_db?useUnicode=yes&amp;characterEncoding=UTF-8
    jdbc.username = root
    jdbc.password = golfik3
 ************************************************************************************************** 
  
  Here rcshop_db is a name of the database. You should specify your database name (in the case if you have used your name of database).
  Now you need to register a user in the tomcat-users.xml file (you can find this file in a folder conf of Tomcat server):
 **************************************************************************************************   
	 <tomcat-users>
		<role roleame="admin-gui"/>
		<role rolename="manager-gui"/>
		<role rolename="manager-script"/>
		<role rolename="manager-jmx"/>
		<role rolename="manager-status"/>
		<role rolename="manager"/>
		<user username="username" password="password" roles="admin-gui,manager-gui,manager-script,manager-jmx,manager-status,manager" />
	</tomcat-users>
 **************************************************************************************************
  
  Register the Tomcat server in a settings.xml file of Maven (you can find this file in a folder conf of Maven).
  You can use your personal values inside the <id>, <username> and <password> tags: 
 **************************************************************************************************
	<servers>
		<server>
		  <id>Tomcat-1.8-localhost</id>
		  <username>username</username>
		  <password>password</password>
		</server>
	</servers>
 **************************************************************************************************

  Open a pom.xml file from a root folder of the RCShopModular project.
  Set the <server> value according that you have specified in settings.xml.
  Change the localhost and port values if you have others ones.
 **************************************************************************************************
	 <plugin>
		<groupId>org.apache.tomcat.maven</groupId>
		<artifactId>tomcat7-maven-plugin</artifactId>
		<version>2.2</version>
			<configuration>
				<!-- id from settings.xml -->
				<server>Tomcat-1.8-localhost</server>
				<url>http://localhost:8080/manager/text</url>
				<path>/${project.name}</path>
			</configuration>
	</plugin>
 **************************************************************************************************

 Deploy and start of the application.
 -------------------------------------
  Start the tomcat server.
  Go to the root folder of project.
  Build the project, using a console command: 
	mvn clean install
  Deploy the application, using a console command:
    tomcat7: deploy
	
  Now you can start the web application using next url in your web browser: http://localhost:8080/MRCShop/
  
  You also can use the MRCShop.war file from the root folder of the project.
  Change the settings of the database.properties file how it was shown above.
  Copy this file to the folder "conf" of Tomcat server.
  Start the server.
  
	
