#!/bin/sh
#
#       Sample shell script to fetch your project HTML
#
#       Creates a file called projhtml.cache in your home directory
#
/usr/bin/wget -q -O ~/projhtml.tmp 'http://sourceforge.net/export/projhtml.php?group_id=2080&mode=full&no_table=1'  > /dev/null
/bin/mv -f ~/projnews.tmp /home/groups/s/sa/sailaway/htdocs/news.html
