FROM maven:3.8-openjdk-8-slim as builder
COPY . /mvn
RUN cd mvn && mvn verify clean --fail-never

FROM maven:3.8-openjdk-8-slim
COPY --from=builder /root/.m2 /root/.m2
