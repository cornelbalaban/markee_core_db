#!/bin/bash

echo
echo "<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>> "
echo "<<<<<<<<<< Build Starting >>>>>>>>>> "
echo "<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>> "
rm -rf build
./gradlew build -x test
#./gradlew publishBuild
echo "<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>> "
echo "<<<<<<<<<< Build end >>>>>>>>>> "
echo "<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>> "
