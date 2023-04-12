if [ -n `which java` ]; then
  java -Dquarkus.http.limits.max-body-size=1024M -jar ./build/quarkus-app/quarkus-run.jar
else
  echo "Java is not available in the path!"
fi