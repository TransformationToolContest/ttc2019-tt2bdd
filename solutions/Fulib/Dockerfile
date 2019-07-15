
FROM gradle:jdk8 as builder
COPY --chown=gradle:gradle . /FulibSolution
WORKDIR /FulibSolution
RUN pwd
RUN ls
RUN gradle build
CMD ["gradle", "cleanTest", "test"]

