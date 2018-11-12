#!/bin/sh
#
#       Sample shell script to fetch your project HTML
#
#       Creates a file called projhtml.cache in your home directory
#
/usr/bin/wget -q -O ~/projnews.tmp 'http://sourceforge.net/export/projnews.php?group_id=2080&limit=12&flat=1&show_summaries=1'  > /dev/null
/bin/mv -f ~/projnews.tmp /home/groups/s/sa/sailaway/htdocs/news.html
