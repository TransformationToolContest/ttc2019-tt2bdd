FROM ubuntu:19.04
MAINTAINER "Antonio Garcia-Dominguez" a.garcia-dominguez@aston.ac.uk
WORKDIR /ttc
COPY . .

# Install base packages required to install TTC2019 language environments
RUN apt-get update && apt-get install -y \
    gnupg ca-certificates apt-transport-https wget

# Install TTC2019 language environments
#
# Use plain wget to install dotnet to avoid outdated InRelease file:
# https://github.com/microsoft/vscode/issues/77562

RUN  (echo "deb https://dl.bintray.com/sbt/debian /" | tee -a /etc/apt/sources.list.d/sbt.list) && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 2EE0EA64E40A89B84B2DF73499E82A75642AC823 && \
    apt-get update -y && \
    apt-get install -y sbt python3 openjdk-11-jdk-headless && \
    wget -q https://packages.microsoft.com/ubuntu/19.04/prod/pool/main/a/aspnetcore-runtime-2.2/aspnetcore-runtime-2.2.6-x64.deb && \
    wget -q https://packages.microsoft.com/ubuntu/19.04/prod/pool/main/d/dotnet-host/dotnet-host-2.2.6-x64.deb && \
    wget -q https://packages.microsoft.com/ubuntu/19.04/prod/pool/main/d/dotnet-hostfxr-2.2/dotnet-hostfxr-2.2.6-x64.deb && \
    wget -q https://packages.microsoft.com/ubuntu/19.04/prod/pool/main/d/dotnet-runtime-2.2/dotnet-runtime-2.2.6-x64.deb && \
    wget -q https://packages.microsoft.com/ubuntu/19.04/prod/pool/main/d/dotnet-runtime-deps-2.2/dotnet-runtime-deps-2.2.6-x64.deb && \
    wget -q https://packages.microsoft.com/ubuntu/19.04/prod/pool/main/d/dotnet-sdk-2.2/dotnet-sdk-2.2.301-x64.deb && \
    apt install -y ./*2.2*.deb

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
