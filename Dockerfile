# open jdk 21 버전의 환경을 구성한다.
FROM openjdk:21-jdk-slim

# build가 될 때 JAR_FILE이라는 변수 명에 build/libs/*.jar 선언
# build/libs - gradle로 빌드했을 때 jar 파일이 생성되는 경로임
ARG JAR_FILE=build/libs/*.jar

# JAR_FILE을 agaproject.jar로 복사 (이 부분(.jar)은 개발환경에 따라 다름)
COPY ${JAR_FILE} wouldyouin.jar

# 운영 및 개발에서 사용되는 환경 설정을 분리한다.
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=deploy", "/wouldyouin.jar"]