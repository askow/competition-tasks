if [ -n `which java` ]; then
  java -jar ./build/quarkus-app/quarkus-run.jar
else
  echo "Java is not available in the path!"
fi
