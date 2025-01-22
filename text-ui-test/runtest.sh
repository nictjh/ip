#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi

# Find all java files
find ../src/main/java -name "*.java" > sources.txt

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin @sources.txt
then
    echo "********** BUILD FAILURE **********"
    rm sources.txt
    exit 1
fi


# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin app.Solace < input.txt > ACTUAL.TXT

# convert to UNIX format
#cp EXPECTED.TXT EXPECTED-UNIX.TXT
#dos2unix ACTUAL.TXT EXPECTED-UNIX.TXT

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi