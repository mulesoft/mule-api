#!/bin/bash
set -e
set -x

if [ -z ${NEXUS_BASE_URL+x} ]; then
#	echo "warning: NEXUS_BASE_URL is not set. Setting default value"
	NEXUS_URL="https://nexus-proxy.repo.local.sfdc.net/nexus/content"
else
	NEXUS_URL=${NEXUS_BASE_URL}
fi

### Remove trailing slash if present
NEXUS_URL=$(echo "$NEXUS_URL" | sed "s,/$,,")

MAVEN_TAR_FILE_NAME=apache-maven-${MAVEN_VERSION}-bin.tar.gz

MAVEN_TAR_DOWNLOAD_URL=${NEXUS_URL}/repositories/central/org/apache/maven/apache-maven/${MAVEN_VERSION}/${MAVEN_TAR_FILE_NAME}

#echo "#### Download apache-maven binary from Nexus"

curl -sS "$MAVEN_TAR_DOWNLOAD_URL" -u $NEXUS_USERNAME:$NEXUS_PASSWORD -o ${MAVEN_TAR_FILE_NAME}
# Verify the sha values are correct for what we expect (no tampering)
#echo $(sha512sum ${MAVEN_TAR_FILE_NAME})
echo "$MAVEN_TAR_DOWNLOAD_SHA512 ${MAVEN_TAR_FILE_NAME}" | sha512sum --quiet -c -

MAVEN_TARGET_BASE_DIR=/usr/local
tar xzf ${MAVEN_TAR_FILE_NAME} --directory ${MAVEN_TARGET_BASE_DIR}

MAVEN_DIR=$(set -- ${MAVEN_TARGET_BASE_DIR}/apache*; echo $1)
#ln -s ${MAVEN_DIR}/bin/* /usr/bin/
rm -rf ${MAVEN_TAR_FILE_NAME}

echo ${MAVEN_DIR}

