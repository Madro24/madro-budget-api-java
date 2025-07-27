

FROM amazoncorretto:21 AS builder

# Set working directory
WORKDIR /build

# Install protobuf compiler and required tools
RUN yum update -y && \
    yum install -y protobuf-compiler git unzip && \
    yum clean all
# Copy project files
COPY gradlew .
COPY gradle gradle
COPY settings.gradle .
COPY app app

# Build the application
RUN chmod +x ./gradlew && \
    ./gradlew clean build --no-daemon

FROM amazoncorretto:21-alpine-jdk

# Install AWS CLI and required tools
#RUN apk add --no-cache \
#    aws-cli \
#    gzip \
#    tar

# Create necessary directories
RUN mkdir -p /var/log && \
    chmod 777 /var/log

WORKDIR /app

# Copy application files from builder stage
COPY --from=builder /build/app/build/libs/madro-budget-api.jar madro-budget-api.jar
#COPY ./app/upload_heapdump_gc_s3.sh upload_heapdump_gc_s3.sh

# Set execute permissions for the script
#RUN chmod +x upload_heapdump_gc_s3.sh

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=70", "-XX:+UseG1GC", "-XX:+UseStringDeduplication", "-Xlog:gc*:file=/var/log/gc-%t.log:time,uptime,tid,tags,level:filecount=10,filesize=10M", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/var/log/oom.hprof", "-XX:OnOutOfMemoryError=/app/upload_heapdump_gc_s3.sh", "-XshowSettings:vm", "-XX:+PrintFlagsFinal", "-XX:+UnlockDiagnosticVMOptions", "-jar", "madro-budget-api.jar"]

