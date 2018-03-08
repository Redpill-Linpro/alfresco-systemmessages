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
The build produces several jar files. Attach them to your own maven project using dependencies or put them under tomcat/shared/lib. Amp files are also produced if you prefer this installation type.

SDK 1 and SDK 2

For inclusion in an pre Alfresco 5.1 (pre SDK3) project use the pre 2.0-versions of the jar-files, navigate to our support-branch for this and follow instructions in the README.md file: https://github.com/Redpill-Linpro/alfresco-systemmessages/tree/support/pre-5.x-support

SDK 3

Platform/Repository module (parent pom):
```xml
<moduleDependency>
	<groupId>com.redpill-linpro.alfresco</groupId>
	<artifactId>alfresco-systemmessages-platform</artifactId>
	<version>2.0.0-SNAPSHOT</version>
</moduleDependency>
```

Share module (parent pom): 
```xml
<moduleDependency>
	<groupId>com.redpill-linpro.alfresco</groupId>
	<artifactId>alfresco-systemmessages-share</artifactId>
	<version>2.0.0-SNAPSHOT</version>
</moduleDependency>
```
The artifacts are deployed to the Maven Central Repository and can be downloaded from there as well.


License
-------

This application is licensed under the LGPLv3 License. See the [LICENSE file](LICENSE) for details.

Authors
-------

Erik Billerby - Redpill Linpro AB
Magnus Pedersen - Redpill Linpro AB
