#!/bin/bash
#
# Install and configure Hadoop on this node
#
# This script will be run as root
#

HADOOP_HOME=/home/hadoop
HADOOP_VERSION=0.20.203.0
export JAVA_HOME=/usr/lib/jvm/java-6-sun
export CFLAGS=-m32
export CXXFLAGS=-m32
export HADOOP_INSTALL=${HADOOP_HOME}/hadoop-${HADOOP_VERSION}
export PATH=${JAVA_HOME}/bin:${HADOOP_INSTALL}/bin:${PATH}

# Add profile options
cat >>/etc/bash.bashrc <<EOF

# Added by cloud-init
export JAVA_HOME=/usr/lib/jvm/java-6-sun
export LD_LIBRARY_PATH=\$JAVA_HOME/lib:$LD_LIBRARY_PATH
export HADOOP_INSTALL=${HADOOP_INSTALL}
export PATH=\$JAVA_HOME/bin:\$HADOOP_INSTALL/bin:\$PATH
EOF

# Create hadoop user
useradd -s /bin/bash -c "Hadoop user" -m hadoop

# Download hadoop distribution
wget http://mirror.csclub.uwaterloo.ca/apache//hadoop/common/hadoop-0.20.203.0/hadoop-${HADOOP_VERSION}rc1.tar.gz -O /tmp/hadoop-${HADOOP_VERSION}rc1.tar.gz
tar zxvf /tmp/hadoop-${HADOOP_VERSION}rc1.tar.gz -C ${HADOOP_HOME}
chown -R hadoop:hadoop ${HADOOP_HOME}/hadoop-${HADOOP_VERSION}


# Download hadoop-lzo distribution
cd /tmp
git clone https://github.com/kevinweil/hadoop-lzo.git
cd hadoop-lzo
ant compile-native
