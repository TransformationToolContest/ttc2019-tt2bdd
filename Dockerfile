FROM ubuntu:19.04
MAINTAINER "Antonio Garcia-Dominguez" a.garcia-dominguez@aston.ac.uk
WORKDIR /ttc
COPY . .

# Install base packages required to install TTC2019 language environments
RUN apt-get update && apt-get install -y \
    gnupg ca-certificates apt-transport-https wget

# Install TTC2019 language environments
RUN  (echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list) && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 && \
    wget -q https://packages.microsoft.com/config/ubuntu/19.04/packages-microsoft-prod.deb -O packages-microsoft-prod.deb && \
    dpkg -i packages-microsoft-prod.deb && \
    apt-get update && \
    apt-get install -y sbt python3 openjdk-11-jdk-headless dotnet-sdk-2.1

# Prepare YAMTL solution
WORKDIR /ttc/solutions/EMFSolutionYAMTL
RUN ./gradlew build && \
  tar tf build/distributions/EMFSolutionYAMTL-0.0.4-SNAPSHOT.tar && \
  sed -i 's#cmd=.*#cmd=JAVA_OPTS="-Xms4g -Xmx12g" EMFSolutionYAMTL-0.0.4-SNAPSHOT/bin/EMFSolutionYAMTL#g' solution.ini

# Build all TTC2019 solutions
WORKDIR /ttc
RUN scripts/run.py -b

# Run a shell by default (use /ttc/scripts/run.py to run the experiments, then use 'docker cp' to pull files)
CMD /bin/bash
