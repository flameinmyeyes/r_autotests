# FROM bellsoft/liberica-openjdk-alpine-musl:8 as builder
# # FROM maven:3.6-openjdk-8-slim as builder
#
# COPY . /mvn
# RUN cd mvn && mvn verify clean --fail-never
#
# FROM bellsoft/liberica-openjdk-alpine-musl:8
# # FROM maven:3.6-openjdk-8-slim
# COPY --from=builder /root/.m2 /root/.m2
#
# RUN apt-get update && apt-get install cifs-utils -y


FROM maven:3.6 as builder
# FROM maven:3.6-openjdk-8-slim as builder

ENV TZ=Europe/Moscow

COPY . /mvn
RUN cd mvn && mvn verify clean --fail-never

FROM maven:3.6
# FROM svenruppert/maven-3.5.4-liberica
COPY --from=builder /root/.m2 /root/.m2

# RUN apt-get update && apt-get install cifs-utils -y