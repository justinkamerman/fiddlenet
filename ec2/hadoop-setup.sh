#!/bin/bash
#
# Install and configure Hadoop on this node
#
# This script will be run as root
#

HADOOP_HOME=/home/hadoop
HADOOP_VERSION=0.20.203.0
HADOOP_INSTALL=${HADOOP_HOME}/hadoop-${HADOOP_VERSION}
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
wget http://mirror.csclub.uwaterloo.ca/apache//hadoop/common/hadoop-0.20.203.0/hadoop-${HADOOP_VERSION}rc1.tar.gz -O /mnt/hadoop-${HADOOP_VERSION}rc1.tar.gz
tar zxvf /mnt/hadoop-${HADOOP_VERSION}rc1.tar.gz -C ${HADOOP_HOME}
sed -i 's#<\/configuration>#<property>\n    <name>io.compression.codecs</name>\n    <value>org.apache.hadoop.io.compress.GzipCodec,org.apache.hadoop.io.compress.DefaultCodec,org.apache.hadoop.io.compress.BZip2Codec,com.hadoop.compression.lzo.LzoCodec,com.hadoop.compression.lzo.LzopCodec</value>\n</property>\n<property>\n    <name>io.compression.codec.lzo.class</name>\n    <value>com.hadoop.compression.lzo.LzoCodec</value>\n</property>\n\0#' ${HADOOP_INSTALL}/conf/core-site.xml
HADOOP_PLATFORM=$(hadoop org.apache.hadoop.util.PlatformName)


# Download hadoop-lzo distribution
cd /mnt
git clone https://github.com/kevinweil/hadoop-lzo.git
cd hadoop-lzo
ant compile-native tar
cp build/hadoop-lzo-*.jar ${HADOOP_INSTALL}/lib
cp -r build/native/${HADOOP_PLATFORM}/lib/libgplcompression.* ${HADOOP_INSTALL}/lib/native/${HADOOP_PLATFORM}

# Change file ownership to hadoop
chown -R hadoop:hadoop ${HADOOP_INSTALL}

# Set up local data area
mkdir /mnt/data
chown ubuntu:ubuntu /mnt/data

# Clean up
rm -rf /mnt/hadoop-${HADOOP_VERSION}rc1.tar.gz
rm -rf /mnt/hadoop-lzo

# Get mapreduce code
su -l ubuntu -c 'git clone git@github.com:justinkamerman/fiddlenet.git'