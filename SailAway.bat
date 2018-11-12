@echo off
: File:         SailAway.bat
: Purpose:      Runs the SailAway application
: Contributor:	<Phil Berry>phil.berry@xinetica.com
:
: Last modified on 2000\03\05
:
: Altered slightly for new directories by adiffer 2002/02/14
: Altered again to support launching from a jar file. 2002/02/19

:******** To launch directly from the SailAway class file after compilation
:******** uncomment these lines and comment out the next group
:set CLASSPATH=. com.interworldtransport.sailgui.SailAway
:set SAVEFILE=".\Sail.save"
:set PROFILE=".\Configuration\Sail.profile"
:set CONFFILE=".\Configuration\SailAway.conf"
:java -classpath %CLASSPATH% -s %SAVEFILE% -p %PROFILE% -c %CONFFILE%
:****************************************************************

:******** To launch SailAway from its jar file after distribution
:******** uncomment these lines and comment out the earlier group
:set CLASSPATH=. ./lib/clados.jar

:set SAVEFILE=".\Sail.save"
:set PROFILE=".\Configuration\Sail.profile"
:set CONFFILE=".\Configuration\SailAway.conf"

java -jar \home\adiffer\SailAway\SailAway.jar 

:****************************************************************
