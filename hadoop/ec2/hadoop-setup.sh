#!/bin/bash
#
# Install and configure Hadoop on this node
#
# This script will be run as root
#

CONFIGURED_USER=ubuntu
HADOOP_HOME=/usr/local/hadoop
HADOOP_VERSION=0.20.203.0
HADOOP_INSTALL=${HADOOP_HOME}/hadoop-${HADOOP_VERSION}
HADOOP_TMP_DIR=${HADOOP_INSTALL}/datastore
export JAVA_HOME=/usr/lib/jvm/java-6-sun
export CFLAGS=-m32
export CXXFLAGS=-m32
export HADOOP_INSTALL=${HADOOP_HOME}/hadoop-${HADOOP_VERSION}
export PATH=${JAVA_HOME}/bin:${HADOOP_INSTALL}/bin:${PATH}

# Instance meta-data


# Add profile options
cat >>/etc/bash.bashrc <<EOF

# Added by cloud-init
export JAVA_HOME=${JAVA_HOME}
export LD_LIBRARY_PATH=${JAVA_HOME}/lib:$LD_LIBRARY_PATH
export HADOOP_HOME=${HADOOP_HOME}
export HADOOP_INSTALL=${HADOOP_INSTALL}
export PATH=\$JAVA_HOME/bin:\$HADOOP_INSTALL/bin:\$PATH
EOF

# Create hadoop user
useradd -s /bin/bash -c "Hadoop user" -m -d ${HADOOP_HOME} hadoop

# Download hadoop distribution
wget http://mirror.csclub.uwaterloo.ca/apache//hadoop/common/hadoop-0.20.203.0/hadoop-${HADOOP_VERSION}rc1.tar.gz -O /mnt/hadoop-${HADOOP_VERSION}rc1.tar.gz
tar zxvf /mnt/hadoop-${HADOOP_VERSION}rc1.tar.gz -C ${HADOOP_HOME}

# Hadoop config: pseudo cluster
cat >>${HADOOP_INSTALL}/conf/hadoop-env.sh <<EOF

# Added by cloud-init
export JAVA_HOME=${JAVA_HOME}
EOF

cat >${HADOOP_INSTALL}/conf/core-site.xml <<\EOF
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    <property>
        <name>io.compression.codecs</name>
        <value>org.apache.hadoop.io.compress.GzipCodec,org.apache.hadoop.io.compress.DefaultCodec,org.apache.hadoop.io.compress.BZip2Codec,com.hadoop.compression.lzo.LzoCodec,com.hadoop.compression.lzo.LzopCodec</value>
    </property>
    <property>
        <name>io.compression.codec.lzo.class</name>
        <value>com.hadoop.compression.lzo.LzoCodec</value>
    </property>
    <property>
        <name>fs.default.name</name>
        <value>hdfs://localhost:9000</value>
    </property>
    <property>
  <name>hadoop.tmp.dir</name>
  <value>/usr/local/hadoop/datastore/hadoop-${user.name}</value>
</property>
</configuration>
EOF

cat >${HADOOP_INSTALL}/conf/mapred-site.xml <<\EOF
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    <property>
        <name>mapred.job.tracker</name>
        <value>localhost:9001</value>
    </property>
    <property>
        <name>mapreduce.jobtracker.staging.root.dir</name>
        <value>/user</value>
    </property>
</configuration>
EOF

cat >${HADOOP_INSTALL}/conf/hdfs-site.xml <<\EOF
<?xml version="1.0"?>
<?xml-stylesheet type="text/xsl" href="configuration.xsl"?>
<configuration>
    <property>
        <name>dfs.replication</name>
        <value>1</value>
    </property>
</configuration>
EOF

# Download hadoop-lzo distribution
echo "### Downloading hadoop-lzo distibution"
HADOOP_PLATFORM=$(hadoop org.apache.hadoop.util.PlatformName)
pushd /mnt
git clone https://github.com/kevinweil/hadoop-lzo.git
cd hadoop-lzo
ant compile-native tar
cp build/hadoop-lzo-*.jar ${HADOOP_INSTALL}/lib
cp -r build/native/${HADOOP_PLATFORM}/lib/libgplcompression.* ${HADOOP_INSTALL}/lib/native/${HADOOP_PLATFORM}
popd

# Change file ownership to hadoop
chown -R hadoop:hadoop ${HADOOP_INSTALL}

# Set up Hadoop tmp dir
mkdir ${HADOOP_TMP_DIR}
chown hadoop:hadoop ${HADOOP_TMP_DIR}

# Clean up
echo "### Cleaning up hadoop tarball"
rm -rf /mnt/hadoop-${HADOOP_VERSION}rc1.tar.gz
rm -rf /mnt/hadoop-lzo

# Get mapreduce code
#su -l ${CONFIGURED_USER} -c 'git clone git://github.com/justinkamerman/fiddlenet.git'

# Set up passphraseless ssh
echo "### Setting up passphraseless ssh"
mkdir ${HADOOP_HOME}/.ssh
cat >${HADOOP_HOME}/.ssh/config <<EOF
Host *
    StrictHostKeyChecking no
EOF

cat >${HADOOP_HOME}/.ssh/id_dsa <<EOF
-----BEGIN RSA PRIVATE KEY-----
MIIEpAIBAAKCAQEAqq3q4eQi9xqPRoTa+gsNIfnB1tIvnNIu6P4B/rejcAgmfxNa
TLILqSDBWYwI6xsL3MaH0hBY+c/SghcxrT3JL2z0TKfmexaUtC3+js8h++F+vPv9
fZaXojelNyXsOlgTBa+e4thIn7j/44ozMs/Uvy6PkGVukrPXRV57b9KEriAtEYJF
3DdW/Y6S89+gsV7Scwty3pOJ7eeNJEirTCdl0ox+/aNFNxlM29sOqqB43q1fKLZx
223CginVtd+fO8VLzU0+PdyxFZ9qCMVPuEP+bl3uSWCdlU9cfj+LY5cXch+kFdoN
Key7VqH2aTxXBDL8t1dwssUnlSkwtOt4Wg1qzQIDAQABAoIBAFpFXeNXa/7Rd1HO
1ppE2g9ML29U/4WrzM/B+IAl1DVeui2fqLTDvlMXVevsmpLuXRnJjvBVYRnPBwFz
Dv0Xnp6Mu7EHZGlZihC5+tbBSrITk5qUlH+l9FEBqUo/rm81QepR9nD3/4EqsXxB
Dc8kCNuM3rV6UD8bCxJPZG3CJBaLZId2xueaejWgAnx9L6bx4G8hTfBGgGpPeZ6t
sfCtukMZnAAADZRImjxfIUjDTNbdGVTCmYgWL9cg7j8VsFa7nISbPCmmyyTU6wQa
6nXEdeazejYmGcjZopPlojcQJ6V6uQMljIrI+3mx7UzQuEz/Fjqa4vvTV7H+t6fJ
qSSErgECgYEA3f092vTWF3yRRVV6MSyrvvw4DfPgFZs1IXIP72WxmK4SfWY1vwEe
/pQprSvctd6jW71G9K3SZsFoDQmuDrYmda/66YC61jDgeInbTxb9Cc9Wiy11D4v1
ddeqkKX2yVBUAhd80bhAXFJNDWhesd6nXzgB9QuPHKnmLYSr1WMwO8ECgYEAxNQ5
cJpLH0W2AG94cfRjYAfdxT421tsYX0W/MRtq3Rqj0L74UVeYgG+YOl30GJkg5H1i
D04EWk94hfNviLg6HfIDo5iFqIbFWuzXjI194JzGAMPaEPJi3w/PgJAVc1QiDjkq
DV7BQXgvz/InTQnWznOVIp8sU6SBLB2kUzpa4g0CgYEA3d7kWdlnuaW5FFEwhcGe
Do7L/7YF+9JasgjswFslu/IPbOIhSbx3G/8+AGTcfbH+GAz/xEGPD0CzHITWQMHx
gqLW51bQZpAHarJuTYgudAWU/Bn87AL43EUnptcZ52+v5z9Oc9XyDdP8SzBLpP9i
zZqO6joZWY6+DjSSAf7XEIECgYEAifFaGCpqP45xkTiOJv7prmGU8Sk68bU3DX4q
ElZuvGpxKFjOWuOTA2AyRaWW7q5SuQ+Oa793mXtcsjP7lMvYHyh/mGXKNmPNaH3Y
Sq7W61W0BtE7wOi+linUePuBrQPnoiQ57ojb0/BRQeEp3fnpS2MBv/Ph8vS1ep+D
jLi2/PkCgYAzqknNfAkItNFRljQ1VDRpq8vrCANvQ1OaRa22zuUe7g8Vi6qr8uiB
eAWfWirpwIB2YnvKcpKlgM6DASO5a8t6tyCC6l1iV3BMgXLcUeMwmbi5ocwpCVoy
8mlCSVDCFzkwPSPr7h2OFI+fYPery0y9L3Vs19m+pbcNxPzYaoxD4w==
-----END RSA PRIVATE KEY-----
EOF

cat >${HADOOP_HOME}/.ssh/authorized_keys <<EOF
ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABAQCqrerh5CL3Go9GhNr6Cw0h+cHW0i+c0i7o/gH+t6NwCCZ/E1pMsgupIMFZjAjrGwvcxofSEFj5z9KCFzGtPckvbPRMp+Z7FpS0Lf6OzyH74X68+/19lpeiN6U3Jew6WBMFr57i2EifuP/jijMyz9S/Lo+QZW6Ss9dFXntv0oSuIC0RgkXcN1b9jpLz36CxXtJzC3Lek4nt540kSKtMJ2XSjH79o0U3GUzb2w6qoHjerV8otnHbbcKCKdW13587xUvNTT493LEVn2oIxU+4Q/5uXe5JYJ2VT1x+P4tjlxdyH6QV2g0p7LtWofZpPFcEMvy3V3CyxSeVKTC063haDWrN
EOF

chown -R hadoop:hadoop ${HADOOP_HOME}/.ssh
chmod 700 ${HADOOP_HOME}/.ssh
chmod 600 ${HADOOP_HOME}/.ssh/id_dsa

# Disable ipv6
echo "### Disabling ipv6 stack"
cat >>/etc/sysctl.conf <<EOF
# IPv6
net.ipv6.conf.all.disable_ipv6 = 1
net.ipv6.conf.default.disable_ipv6 = 1
net.ipv6.conf.lo.disable_ipv6 = 1
EOF
sysctl -p



# Namenode
# Format hdfs filesystem
echo "### Formatting hdfs filesystem"
su hadoop -c "${HADOOP_INSTALL}/bin/hadoop namenode -format"

# Start hadoop daemons
echo "### Starting Hadoop daemons"
su hadoop -c "${HADOOP_INSTALL}/bin/start-all.sh"

# Create user directory
echo "### Creating hdfs directory for user ${CONFIGURED_USER}"
su hadoop -c "${HADOOP_INSTALL}/bin/hadoop fs -mkdir /user/${CONFIGURED_USER}"

# Change ownership of user directory
echo "### Changing ownership of hdfs directory for user ${CONFIGURED_USER}"
su hadoop -c "${HADOOP_INSTALL}/bin/hadoop fs -chown ${CONFIGURED_USER} /user/${CONFIGURED_USER}"
