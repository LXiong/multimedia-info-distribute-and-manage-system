#!/bin/bash

#pscount=`ps -e |grep -e "com\.sanss\.octupus\.ftp\.DaemonRunner" -c`
#if [ $pscount -ge 1 ] ; then 
#	echo "The program is started!" 
#	exit 0 
#fi


# resolve links - $0 may be a softlink
PRG="$0"

while [ -h "$PRG" ] ; do
  ls=`ls -ld "$PRG"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "$PRG"`/"$link"
  fi
done
 
# Change this to work directory
PRGDIR=`dirname "$PRG"`
cd $PRGDIR


#JAVA_HOME should be changed according to installed machine
#JAVA_HOME="/usr/local/java/jdk1.6.0_13"
CLASSPATH="$JAVA_HOME/lib/tools.jar:$CLASSPATH"


for f in lib/*.jar; do
  CLASSPATH=${CLASSPATH}:$f
done
 
CLASSPATH=${CLASSPATH}:./conf
 
JAVA=$JAVA_HOME/bin/java

#echo $CLASSPATH
 
#exec "$JAVA" -Xms128M -Xmx512M -classpath "$CLASSPATH"  com.yunling.mediacenter.server.AuthServer "$@" >> start-log`date +%Y%m%d`.txt 2>&1
exec "$JAVA" -Xms128M -Xmx1024M -classpath "$CLASSPATH"  com.yunling.mediacenter.server.AuthServer "$@" 