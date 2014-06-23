io.jrevolt.mvnlauncher
======================

Runs the application directly from Maven repository.

Features
--------

- supports any URL based Maven repository
- basic auth for http:// and https:// URLs
- caching
- snapshot updates check
- persistent releases (once cached, no more update checks)
- offline mode
- no cache mode (always download fresh copy)
- run from local repo
- optional SHA1 verification

Requirements
------------

Main artifact's manifest must define `Spring-Boot-Dependencies` attribute which lists all runtime-scope artifacts to be included on classpath.

Usage
-----

	java -jar io.jrevolt.mvnlauncher-develop-SNAPSHOT {group}:{artifact}:{version}
	
The bootstrap code is a part of experimental *MvnLauncher* branch of the latest *SpringBoot Loader* (based on v1.1.1)
[MvnLauncher](https://github.com/patrikbeno/spring-boot/commits/MvnLauncher)

Configuration
-------------

 1. System properties
 2. ~/.springboot/defaults.properties (or file defined by `-DMvnLauncher.defaults`)
 3. Built-in defaults

See example below, plus the configuration class for more details: [MvnLauncherCfg](https://github.com/patrikbeno/spring-boot/blob/MvnLauncher/spring-boot-tools/spring-boot-loader/src/main/java/org/springframework/boot/loader/MvnLauncherCfg.java) 

| Property | Description | Default |
|----------|---------|-------------|
| `MvnLauncher.defaults` | User-specific defaults overriding built-in defaults | `~/.springboot/defaults.properties` | 
| `MvnLauncher.debug` | Enables debug output of MvnLauncher's operations | `false` |
| `MvnLauncher.cache` | MvnLauncher's cache directory | `file://${user.home}/.springboot/cache` |
| `MvnLauncher.offline` | Disables all online operations. Artifacts must be cached locally to be resolved. | `false` | 
| `MvnLauncher.showClasspath` | Show final classpath used to configure the classloader. Actual file system URLs are show in the same order as passed to classloader. | `false` |
| `MvnLauncher.verify` | Enable download verification using the repository-provided SHA1 hash | `true` |
| `MvnLauncher.noCache` | Bypass MvnLauncher's cache: always download latest version from remote repository. | `false` |
| `MvnLauncher.failOnError` | Fail when any of the required artifacts is invalid or unresolved. This can be disabled if some optional artifacts keeps failing but its absence does not actually affect the application. | `true` |
| `MvnLauncher.cacheFileProtocol` | If set, MvnLauncher will download and cache also `file://` repositories. By default such URLs are not used directly to populate classloader but in some cases caching may be enabled to avoid the filesystem conflicts.  | `false` |
| `MvnLauncher.updateReleases` | By default, release artifacts are cached forever and never checked for updates. In some cases, overriding this may be useful. | `false` |
| `MvnLauncher.updateSnapshots` | Snapshot artifacts are always checked for updates. This may be disabled to speed-up application startup if snapshots are known to be up-to-date or if the updates are irrelevant or undesirable. | `true` |
| `MvnLauncher.repositoryUrl` | Maven repository URL | `file://${user.home}/.m2/repository/`
| `MvnLauncher.repositoryUsername` | Maven repositry user name |  
| `MvnLauncher.repositoryPassword` | Maven repository password |
| `MvnLauncher.cache` | MvnLauncher cache directory | `file://${user.name}/.springboot/cache` |
| `MvnLauncher.artifact` | Root Maven artifact URI to resolve and execute. If undefined, MvnLauncher uses bootstrap (current) manifest to configure then classloader and resolve the main class. Maven URI uses syntax `groupId:artifactId:version[:packaging[:classifier]]` | `undefined` |

Manifest
--------

Your artifact must contain *Spring-Boot-Dependencies* manifest attribute. Syntax:
 
	Spring-Boot-Dependencies: artifact,artifact,...
	artifact: group:artifact:version:jar

This is supported by both Maven and Gradle plugins in [MvnLauncher](https://github.com/patrikbeno/spring-boot/commits/MvnLauncher)
branch.

Download
--------

At the moment, latest build is part of repo: [build/libs](https://github.com/jrevolt/io.jrevolt.mvnlauncher/tree/develop/build/libs)

Example
-------

```
$ java -jar build/libs/io.jrevolt.mvnlauncher-develop-SNAPSHOT.jar \
		 io.jrevolt.sysmon:io.jrevolt.sysmon.agent:develop-SNAPSHOT
MvnLauncher.defaults           : C:\Users\patrik\.springboot\defaults.properties
MvnLauncher.debug              : true
MvnLauncher.showClasspath      : false
MvnLauncher.offline            : false
MvnLauncher.verify             : true
MvnLauncher.noCache            : false
MvnLauncher.failOnError        : true
MvnLauncher.cacheFileProtocol  : false
MvnLauncher.updateReleases     : false
MvnLauncher.updateSnapshots    : true
MvnLauncher.repositoryUrl      : https://gubuntu.greenhorn.sk/nexus/content/groups/public/
MvnLauncher.repositoryUsername : nexus
MvnLauncher.repositoryPassword : ***
MvnLauncher.cache              : C:\Users\patrik\.springboot\cache
MvnLauncher.artifact           : io.jrevolt.sysmon:io.jrevolt.sysmon.agent:develop-SNAPSHOT
## Verifying connection to https://gubuntu.greenhorn.sk/nexus/content/groups/public/
## Resolving main artifact:
NotModified    : io.jrevolt.sysmon:io.jrevolt.sysmon.agent:develop-20140620.180631-2:jar (106 KB)
## Dependencies:
Cached         : aopalliance:aopalliance:1.0:jar (4 KB)
Cached         : ch.qos.logback:logback-classic:1.1.2:jar (264 KB)
Cached         : ch.qos.logback:logback-core:1.1.2:jar (417 KB)
Cached         : com.fasterxml.jackson.core:jackson-annotations:2.4.0:jar (37 KB)
Cached         : com.fasterxml.jackson.core:jackson-core:2.4.0:jar (220 KB)
Cached         : com.fasterxml.jackson.core:jackson-databind:2.4.0:jar (1048 KB)
Cached         : com.rabbitmq:amqp-client:3.3.1:jar (384 KB)
Cached         : com.thoughtworks.xstream:xstream:1.4.7:jar (519 KB)
Cached         : commons-io:commons-io:2.4:jar (180 KB)
Cached         : commons-logging:commons-logging:1.1.3:jar (60 KB)
Cached         : dnsjava:dnsjava:2.1.6:jar (296 KB)
NotModified    : io.jrevolt.sysmon:io.jrevolt.sysmon.agent:develop-20140620.180631-2:jar (106 KB)
NotModified    : io.jrevolt.sysmon:io.jrevolt.sysmon.api:develop-20140620.180633-2:jar (1 KB)
NotModified    : io.jrevolt.sysmon:io.jrevolt.sysmon.common:develop-20140620.180633-2:jar (10 KB)
NotModified    : io.jrevolt.sysmon:io.jrevolt.sysmon.core:develop-20140620.180634-2:jar (4 KB)
Cached         : javax.ws.rs:javax.ws.rs-api:2.0:jar (110 KB)
Cached         : org.apache.activemq:activemq-amqp:5.9.1:jar (75 KB)
Cached         : org.apache.activemq:activemq-broker:5.9.1:jar (996 KB)
Cached         : org.apache.activemq:activemq-client:5.9.1:jar (1146 KB)
Cached         : org.apache.activemq:activemq-openwire-legacy:5.9.1:jar (669 KB)
Cached         : org.apache.commons:commons-lang3:3.2:jar (375 KB)
Cached         : org.apache.geronimo.specs:geronimo-j2ee-management_1.1_spec:1.0.1:jar (19 KB)
Cached         : org.apache.geronimo.specs:geronimo-jms_1.1_spec:1.1.1:jar (31 KB)
Cached         : org.apache.qpid:proton-api:0.6:jar (124 KB)
Cached         : org.apache.qpid:proton-j-impl:0.6:jar (502 KB)
Cached         : org.apache.qpid:proton-jms:0.6:jar (27 KB)
Cached         : org.aspectj:aspectjweaver:1.8.0:jar (1797 KB)
Cached         : org.bouncycastle:bcpkix-jdk15on:1.47:jar (502 KB)
Cached         : org.bouncycastle:bcprov-jdk15on:1.47:jar (1950 KB)
Cached         : org.fusesource.hawtbuf:hawtbuf:1.9:jar (48 KB)
Cached         : org.slf4j:jcl-over-slf4j:1.7.7:jar (16 KB)
Cached         : org.slf4j:jul-to-slf4j:1.7.7:jar (4 KB)
Cached         : org.slf4j:log4j-over-slf4j:1.7.7:jar (23 KB)
Cached         : org.slf4j:slf4j-api:1.7.7:jar (28 KB)
Downloaded     : org.springframework.amqp:spring-amqp:1.3.4.RELEASE:jar (78 KB)
Downloaded     : org.springframework.amqp:spring-rabbit:1.3.4.RELEASE:jar (240 KB)
Downloaded     : org.springframework.boot:spring-boot-autoconfigure:1.1.2.BUILD-20140617.010053-8:jar (322 KB)
Downloaded     : org.springframework.boot:spring-boot-starter-amqp:1.1.2.BUILD-20140617.010056-8:jar (2 KB)
Downloaded     : org.springframework.boot:spring-boot-starter-logging:1.1.2.BUILD-20140617.010054-8:jar (2 KB)
Downloaded     : org.springframework.boot:spring-boot-starter:1.1.2.BUILD-20140617.010055-8:jar (2 KB)
Downloaded     : org.springframework.boot:spring-boot:1.1.2.BUILD-20140617.010049-8:jar (299 KB)
Downloaded     : org.springframework.retry:spring-retry:1.1.0.RELEASE:jar (101 KB)
Downloaded     : org.springframework:spring-aop:4.1.0.BUILD-20140611.203954-4:jar (347 KB)
Downloaded     : org.springframework:spring-aspects:4.1.0.BUILD-20140611.203956-4:jar (54 KB)
Downloaded     : org.springframework:spring-beans:4.1.0.BUILD-20140611.204001-4:jar (677 KB)
Downloaded     : org.springframework:spring-context:4.1.0.BUILD-20140611.204003-4:jar (985 KB)
Downloaded     : org.springframework:spring-core:4.1.0.BUILD-20140611.204009-4:jar (970 KB)
Downloaded     : org.springframework:spring-expression:4.1.0.BUILD-20140611.204012-4:jar (200 KB)
Downloaded     : org.springframework:spring-jms:4.1.0.BUILD-20140611.204025-4:jar (258 KB)
Downloaded     : org.springframework:spring-messaging:4.1.0.BUILD-20140611.204028-4:jar (260 KB)
Downloaded     : org.springframework:spring-tx:4.1.0.BUILD-20140611.204040-4:jar (242 KB)
Cached         : org.yaml:snakeyaml:1.13:jar (267 KB)
Cached         : xmlpull:xmlpull:1.1.3.1:jar (7 KB)
Cached         : xpp3:xpp3_min:1.1.4c:jar (24 KB)
## Summary: 54 archives, 17354 KB total (downloaded: 5046 KB). Warnings/Errors: 0/0.
```	
	
... and entering main()	
