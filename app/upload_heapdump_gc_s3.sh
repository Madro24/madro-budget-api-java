#!/bin/sh

# Exit on any error
set -e

# Configure these variables in your environment or container
S3_BUCKET=${S3_BUCKET:-"your-bucket-name"}
S3_PREFIX=${S3_PREFIX:-"heapdumps"}
AWS_REGION=${AWS_REGION:-"us-east-1"}

# Get container ID (last 12 chars of hostname)
CONTAINER_ID=$(hostname | tail -c 13)
TIMESTAMP=$(date +%Y%m%d_%H%M%S)

# Create directory for temporary files
mkdir -p /tmp/dumps

# Compress heap dump (in background to avoid blocking)
if [ -f "/var/log/oom.hprof" ]; then
    echo "Compressing heap dump..."
    gzip -c /var/log/oom.hprof > "/tmp/dumps/heapdump_${CONTAINER_ID}_${TIMESTAMP}.hprof.gz" &
fi

# Collect and compress GC logs
echo "Collecting GC logs..."
tar czf "/tmp/dumps/gclogs_${CONTAINER_ID}_${TIMESTAMP}.tar.gz" /var/log/gc-*.log

# Wait for heap dump compression to finish
wait

# Upload to S3
echo "Uploading files to S3..."
for file in /tmp/dumps/*; do
    if [ -f "$file" ]; then
        aws s3 cp "$file" "s3://${S3_BUCKET}/${S3_PREFIX}/${CONTAINER_ID}/${TIMESTAMP}/$(basename $file)" \
            --region "${AWS_REGION}" \
            --quiet
    fi
done

# Cleanup
rm -rf /tmp/dumps

echo "Upload complete. Files available in s3://${S3_BUCKET}/${S3_PREFIX}/${CONTAINER_ID}/${TIMESTAMP}/"
