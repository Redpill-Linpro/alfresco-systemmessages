Alfresco System Messages
=============================================

This module is sponsored by Redpill Linpro AB - http://www.redpill-linpro.com.

Description
-----------
This project contains some tools for displaying system wide messages to users. Example usage when system is going down for maintenance


![Add a message](https://github.com/Redpill-Linpro/alfresco-systemmessages/blob/master/admin-console-sm.png)

Depending on priority different colours will be used for the message.

![How its presented](https://github.com/Redpill-Linpro/alfresco-systemmessages/blob/master/all-pages-sm.png)

Structure
------------

The project consists of a repository module and a share module packaged as jar files.

Building & Installation
------------
The build produces several jar files. Attach them to your own maven project using dependencies or put them under tomcat/shared/lib.

SDK 1 and SDK 2

Repository dependency:
```xml
<dependency>
  <groupId>org.redpill-linpro.alfresco.systemmessages</groupId>
  <artifactId>alfresco-systemmessages-repo</artifactId>
  <version>1.1.0</version>
</dependency>
```

Share dependency:
```xml
<dependency>
  <groupId>org.redpill-linpro.alfresco.systemmessages</groupId>
  <artifactId>alfresco-systemmessages-share</artifactId>    
  <version>1.1.0</version>
</dependency>
```

SDK 3

Platform/Repository module (parent pom):
```xml
<moduleDependency>
	<groupId>org.redpill-linpro.alfresco.systemmessages</groupId>
	<artifactId>alfresco-systemmessages-repo</artifactId>
	<version>1.1.0</version>
</moduleDependency>
```

Share module (parent pom): 
```xml
<moduleDependency>
	<groupId>org.redpill-linpro.alfresco.systemmessages</groupId>
	<artifactId>alfresco-systemmessages-share</artifactId>
	<version>1.1.0</version>
</moduleDependency>
```

Maven repository:
```xml
<repository>
  <id>redpill-public</id>
  <url>http://maven.redpill-linpro.com/nexus/content/groups/public</url>
</repository>
```

The jar files are also downloadable from: https://maven.redpill-linpro.com/nexus/index.html#nexus-search;quick~alfresco-systemmessages


License
-------

This application is licensed under the LGPLv3 License. See the [LICENSE file](LICENSE) for details.

Authors
-------

Erik Billerby - Redpill Linpro AB
Magnus Pedersen - Redpill Linpro AB
