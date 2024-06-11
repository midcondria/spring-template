# run_new_was.sh

#!/bin/bash

# 환경 변수 설정
PROJECT_ROOT="/home/ubuntu/app" # 프로젝트 루트
JAR_FILE="$PROJECT_ROOT/build/libs/team-0.0.1-SNAPSHOT.jar" # 빌드해서 생성된 jar 파일명

# service_url.inc 에서 현재 서비스 중인 WAS의 포트 번호 확인
CURRENT_PORT=$(cat /home/ubuntu/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

echo "> Current port of running WAS is ${CURRENT_PORT}."

# 서비스 중인 포트가 8081이면 8082 포트로 배포
# 서비스 중인 포트가 8082이면 8081 포트로 배포
if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else
  echo "> Any WAS is connected to nginx" # 애플리케이션이 실행되고 있지 않음
fi

# 타겟 포트 번호로 실행 중인 프로세스가 있는지 확인
TARGET_PID=$(lsof -Fp -i TCP:${TARGET_PORT} | grep -Po 'p[0-9]+' | grep -Po '[0-9]+')

# PID를 이용해 타겟 포트로 실행 중인 프로세스 Kill
if [ ! -z ${TARGET_PID} ]; then
  echo "> Kill ${TARGET_PORT}."
  sudo kill ${TARGET_PID}
fi

# jar파일을 이용해 타켓 포트에 새로운 서버 실행
nohup java -jar -Dserver.port=${TARGET_PORT} ${JAR_FILE} > /home/ubuntu/nohup.out 2>&1 &
echo "> Now new WAS runs at ${TARGET_PORT}."
exit 0