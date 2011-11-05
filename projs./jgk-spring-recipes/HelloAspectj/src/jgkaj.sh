#!/bin/bash
echo "HELLO"
CP=/Users/jkroub/aspectj1.6/lib/aspectjrt.jar
AJOPTS="-cp ${CP} -source 6"
CS="${CS} aspinaction/AuthenticationException.java"
CS="${CS} aspinaction/Authenticator.java"
CS="${CS} aspinaction/Main.java"
CS="${CS} aspinaction/MessageCommunicator.java"
CS="${CS} aspinaction/aspects/ProfilingAspect.aj"
CS="${CS} aspinaction/aspects/FunkyAspect.java"
CS="${CS} aspinaction/aspects/SecurityAspect.aj"
CS="${CS} aspinaction/aspects/TrackingAspect.aj"
CMD="ajc -cp /Users/jkroub/aspectj1.6/lib/aspectjrt.jar aspinaction/*.java aspinaction/*.aj aspinaction/aspects/*.aj"
CMD="ajc ${AJOPTS} ${CS}"
echo "CMD: ${CMD}"
${CMD}

echo "Running"
RC="java -cp .:${CP} aspinaction/Main"
#RC="java -cp . aspinaction/Main"
echo "RC: ${RC}"
${RC}
