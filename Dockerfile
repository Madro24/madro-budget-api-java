FROM amazoncorretto:22-alpine-jdk

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
COPY ./app/build/libs/madro-budget-api.jar madro-budget-api.jar
#COPY ./app/upload_heapdump_gc_s3.sh upload_heapdump_gc_s3.sh

# Set execute permissions for the script
#RUN chmod +x upload_heapdump_gc_s3.sh

ENTRYPOINT ["java", "-XX:+UseContainerSupport", "-XX:MaxRAMPercentage=70", "-XX:+UseG1GC", "-XX:+UseStringDeduplication", "-Xlog:gc*:file=/var/log/gc-%t.log:time,uptime,tid,tags,level:filecount=10,filesize=10M", "-XX:+HeapDumpOnOutOfMemoryError", "-XX:HeapDumpPath=/var/log/oom.hprof", "-XX:OnOutOfMemoryError=/app/upload_heapdump_gc_s3.sh", "-XshowSettings:vm", "-XX:+PrintFlagsFinal", "-XX:+UnlockDiagnosticVMOptions", "-jar", "madro-budget-api.jar"]

