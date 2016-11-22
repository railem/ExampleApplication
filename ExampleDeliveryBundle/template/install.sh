#!/bin/bash

CUR=`dirname $0`
export ANT_HOME=${CUR}/contrib/apache-ant-1.9.7/bin/ant
chmod +x ${ANT_HOME}
${CUR}/contrib/apache-ant-1.9.7/bin/ant -f ${CUR}/install.xml $@
