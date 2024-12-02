#!/bin/bash

# Instalação das dependências a partir da pasta lib

# net.java.dev.genesis.annotation:annotation:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-annotation-jdk5-3.2.jar" \
  -DgroupId=net.java.dev.genesis.annotation \
  -DartifactId=annotation \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis:aspect:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-aspect-3.2.jar" \
  -DgroupId=net.java.dev.genesis \
  -DartifactId=aspect \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis.ui:client:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-client-3.2.jar" \
  -DgroupId=net.java.dev.genesis.ui \
  -DartifactId=client \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis.ui:client-swing:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-client-swing-3.2.jar" \
  -DgroupId=net.java.dev.genesis.ui \
  -DartifactId=client-swing \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis.ui:client-swt:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-client-swt-3.2.jar" \
  -DgroupId=net.java.dev.genesis.ui \
  -DartifactId=client-swt \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis.ui:client-thinlet:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-client-thinlet-3.2.jar" \
  -DgroupId=net.java.dev.genesis.ui \
  -DartifactId=client-thinlet \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis:container:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-container-3.2.jar" \
  -DgroupId=net.java.dev.genesis \
  -DartifactId=container \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis:server:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-server-3.2.jar" \
  -DgroupId=net.java.dev.genesis \
  -DartifactId=server \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis:shared:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-shared-3.2.jar" \
  -DgroupId=net.java.dev.genesis \
  -DartifactId=shared \
  -Dversion=3.2 \
  -Dpackaging=jar

# net.java.dev.genesis:shared-annotated:3.2
mvn install:install-file \
  -Dfile="lib/genesis/dist/genesis-shared-annotated-3.2.jar" \
  -DgroupId=net.java.dev.genesis \
  -DartifactId=shared-annotated \
  -Dversion=3.2 \
  -Dpackaging=jar

# forms:forms:1.0
mvn install:install-file \
  -Dfile="lib/forms.jar" \
  -DgroupId=forms \
  -DartifactId=forms \
  -Dversion=1.0 \
  -Dpackaging=jar

# db4o:db4o:7.4.106.13438-java5
mvn install:install-file \
  -Dfile="lib/db4o-7.4.106.13438-java5.jar" \
  -DgroupId=db4o \
  -DartifactId=db4o \
  -Dversion=7.4.106.13438-java5 \
  -Dpackaging=jar

# jcalendar:jcalendar:1.3.3
mvn install:install-file \
  -Dfile="lib/jcalendar-1.3.3.jar" \
  -DgroupId=jcalendar \
  -DartifactId=jcalendar \
  -Dversion=1.3.3 \
  -Dpackaging=jar

# TableLayout:TableLayout:1.0
mvn install:install-file \
  -Dfile="lib/TableLayout.jar" \
  -DgroupId=TableLayout \
  -DartifactId=TableLayout \
  -Dversion=1.0 \
  -Dpackaging=jar

  # TableLayout:TableLayout:1.0
  mvn install:install-file \
    -Dfile="lib/TableLayout-javadoc.jar" \
    -DgroupId=TableLayout \
    -DartifactId=TableLayout \
    -Dversion=1.0 \
    -Dpackaging=jar

  # TableLayout:TableLayout:1.0
  mvn install:install-file \
    -Dfile="lib/TableLayout-src.jar" \
    -DgroupId=TableLayout \
    -DartifactId=TableLayout \
    -Dversion=1.0 \
    -Dpackaging=jar

# genesis.backport175:backport:175-1.1.RC1-snapshot
mvn install:install-file \
  -Dfile="lib/genesis/lib/backport175/backport175-1.1.RC1-snapshot.jar" \
  -DgroupId=genesis.backport175 \
  -DartifactId=backport \
  -Dversion=175-1.1.RC1-snapshot \
  -Dpackaging=jar

  mvn install:install-file \
    -Dfile="lib/genesis/lib/commons/commons-beanutils-1.7.jar" \
    -DgroupId=commons-beanutils \
    -DartifactId=commons-beanutils \
    -Dversion=1.7 \
    -Dpackaging=jar


  mvn install:install-file \
    -Dfile="lib/genesis/lib/commons/commons-digester-1.8.jar" \
    -DgroupId=commons-digester \
    -DartifactId=commons-digester \
    -Dversion=1.8 \
    -Dpackaging=jar


  mvn install:install-file \
    -Dfile="lib/genesis/lib/commons/commons-logging-1.1.jar" \
    -DgroupId=commons-logging \
    -DartifactId=commons-logging \
    -Dversion=1.1 \
    -Dpackaging=jar


