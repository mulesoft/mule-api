#!/bin/bash
set -e +x

if [ -z ${NEXUS_BASE_URL+x} ]; then
	echo "warning: NEXUS_BASE_URL is not set. Setting default value"
	NEXUS_URL="https://nexus-proxy.repo.local.sfdc.net/nexus/content"
else
	NEXUS_URL=${NEXUS_BASE_URL}
fi

### Remove trailing slash if present
NEXUS_URL=$(echo "$NEXUS_URL" | sed "s,/$,,")

MAVEN_TAR_DOWNLOAD_URL=${NEXUS_URL}/repositories/central/org/apache/maven/apache-maven/${MAVEN_VERSION}/apache-maven-${MAVEN_VERSION}-bin.tar.gz

echo "#### Download apache-maven binary from Nexus"
curl "$MAVEN_TAR_DOWNLOAD_URL" -u $NEXUS_USERNAME:$NEXUS_PASSWORD -o apache-maven-${MAVEN_VERSION}-bin.tar.gz
# Verify the sha values are correct for what we expect (no tampering)
echo $(sha512sum apache-maven-${MAVEN_VERSION}-bin.tar.gz)
echo "$MAVEN_TAR_DOWNLOAD_SHA512 apache-maven-${MAVEN_VERSION}-bin.tar.gz" | sha512sum -c -

MAVEN_DIR=$(set -- /usr/local/apache*; echo $1) && ln -s ${MAVEN_DIR}/bin/* /usr/bin/
export PATH=${MAVEN_HOME}/bin:${PATH}
mvn --version
rm -rf apache-maven-${MAVEN_VERSION}-bin.tar.gz

