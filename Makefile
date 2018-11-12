# General makefile for construction of the SailAway Application
# written by Dr Alfred Differ on 2000/01/11
# last modified on 2000/04/10

# Destination directory for the compiled classes
DESTPATH = -d ./class

# Source code top directory to be documented
SOURCEPATH = -sourcepath .

# Documentation target directory
TARGETDOC = -d ./docs/api

# Documentation overview file
OVERDOC = -overview com/interworldtransport/overview.html

# Window Title
WINDOWTITLE = -windowtitle "SailAway Documentation"

# Documentation Title
DOCTITLE = -doctitle "SailAway Package Documentation"

# Header
HEADER = -header "<a target=\"_top\" href=\"http://www.interworldtransport.com\">Interworld Transport</a>"

# Documentation Groups
GROUP1 = -group "Sailing Packages" "com.interworldtransport.sail*" 
GROUP2 = -group "Mathematics Package(s)" "com.interworldtransport.clados"
GROUP3 = -group "Fluidiom Package(s)" "com.fluidiom.*"

SailAway : SailAway.class
	@echo " "

Tester : MonadTester.class
	@echo " "

SailAway.class :
	@echo "Using SailAway rule"
	@javac $(DESTPATH) com/interworldtransport/sailgui/SailAway.java

clean :
	-rm -rf ./class/com;

cleanmodel :
	-rm -f ./class/com/interworldtransport/sailmodel/*.class

cleangui :
	-rm -f ./class/com/interworldtransport/sailgui/*.class

cleanevent :
	-rm -f ./class/com/interworldtransport/sailevent/*.class

cleanmath :
	-rm -f ./class/com/interworldtransport/clados/*.class

cleantest :
	-rm -f ./class/com/interworldtransport/clados/MonadTester.class

run :
	java -classpath ./class com.interworldtransport.sailgui.SailAway -c "./conf/SailAway.conf" -s "./Sail.save" -p "./conf/Sail.profile" -t "SailAway  v0.1 - Licensed under GPL" 

apidoc :
	@if [ -d ./docs/api ];\
	then\
	  javadoc -verbose $(TARGETDOC) $(SOURCEPATH) @Makedocpackage $(OVERDOC) $(WINDOWTITLE) $(DOCTITLE) -use $(HEADER) $(GROUP1) $(GROUP2) $(GROUP3);\
	else\
	  mkdir ./docs/api;\
	  javadoc -verbose $(TARGETDOC) $(SOURCEPATH) @Makedocpackage $(OVERDOC) $(WINDOWTITLE) $(DOCTITLE) -use $(HEADER) $(GROUP1) $(GROUP2) $(GROUP3);\
	fi 

install:
	@echo " "; \
	@echo "SailAway is currently undergoing unit testing.  It is not yet ready to be installed in an operating sense."; \
	@echo "SailAway will make use of an installed configuration file if it is located within the /etc directory on a *NIX OS." 
