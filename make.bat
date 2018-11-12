@echo off
: File:         make.bat
: Purpose:      Windows version of Unix Makefile.
: Usage:        make [ clean| run | doc ]
: Contributor:	<Phil Berry>phil.berry@xinetica.com
:
: DOS BAT file performing similar task to Unix make.
: Last modified on 2000\06\23 
: Last modified by <Alfred Differ>adiffer@jpaerospace.com

if '%1' == 'help' goto help
if '%1' == 'clean' goto clean
if '%1' == 'run' goto run
if '%1' == 'doc' goto doc

: Default action.
        echo "Using SailAway rule"
        c:\jdk1.3\bin\javac -d .\class com\interworldtransport\sailgui\SailAway.java
        goto exit

:help
        echo usage make [ help ] [ clean ] [ run ] [ doc ]
        goto exit

:clean
        set DIRS=clados sailevent sailgui sailmodel
        for %%f in (%DIRS%) do del class\com\interworldtransport\%%f\*.class
        goto exit

:run
        java -classpath .\class com.interworldtransport.sailgui.SailAway -s ".\Sail.save" -p ".\Sail.profile" -c ".\SailAway.conf"
        goto exit

:doc
        javadoc -d .\docs\api -sourcepath . @Makedocpackage -overview com\interworldtransport\overview.html -windowtitle "SailAway Documentation" -doctitle "SailAway Package Documentation" -use -header "<a target="_top" href=\"http:\\www.interworldtransport.com\">Interworld Transport" -group "Sailing Packages" "com.interworldtransport.sail*" -group "Mathematics Packages" "com.interworldtransport.clados"
        goto exit

:exit
