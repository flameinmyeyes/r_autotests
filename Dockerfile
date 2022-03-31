ARG CI_ARTIFACTORY_URL_CLOUD_BASE
FROM $CI_ARTIFACTORY_URL_CLOUD_BASE/maven:3.8-openjdk-8-slim as builder
COPY . /mvn
RUN cd mvn && mvn verify clean --fail-never

ARG CI_ARTIFACTORY_URL_CLOUD_BASE
FROM $CI_ARTIFACTORY_URL_CLOUD_BASE/maven:3.8-openjdk-8-slim
COPY --from=builder /root/.m2 /root/.m2
