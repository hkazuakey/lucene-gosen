#!/bin/bash

OUTDIR=results_gosen

rm -rf ${OUTDIR}
mkdir -p ${OUTDIR}

CURDIR=`pwd`
CMD="java -cp ./src/main/java/net/java/sen/tools:./build/libs/lucene-gosen-6.2.1-ipadic.jar net.java.sen.tools.cli"

outcount=0
while IFS='' read -r line || [[ -n "$line" ]]; do
    ${CMD} "${line}" > ${OUTDIR}/results_${outcount}.mecab
    outcount=$((++outcount))
done < "$1"
