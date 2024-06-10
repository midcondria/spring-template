# health_check.sh

#!/bin/bash

# 환경 변수 설정
# service_url.inc에서 현재 서비스 중인 WAS의 포트 번호 확인
# 해당 포트 번호로 health_check 실행
CURRENT_PORT=$(cat /home/ubuntu/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0

if [ ${CURRENT_PORT} -eq 8081 ]; then
    TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
    TARGET_PORT=8081
else
    echo "> Any WAS is connected to nginx"  # 헬스체크 시 Nginx에 어떤 WAS도 연결돼있지 않으면 에러 코드
    exit 1
fi

echo "> Start health check of WAS at 'http://127.0.0.1:${TARGET_PORT}' ..."

# "/" 경로로 헬스 체크하여 응답 코드를 보고 서버가 정상적으로 작동하는지 확인
# 최대 10번까지 테스트 해서 그 안에 성공하면 통과(WAS가 늦게 뜨는 경우를 대비한 안전 장치)
for RETRY_COUNT in 1 2 3 4 5 6 7 8 9 10
do
    echo "> #${RETRY_COUNT} trying..."
    # 테스트할 API 주소를 통해 http 상태 코드 확인
    RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://127.0.0.1:${TARGET_PORT})

	  # RESPONSE_CODE의 http 상태가 200번인 경우 성공
    if [ ${RESPONSE_CODE} -eq 200 ]; then
        echo "> New WAS successfully running"
        exit 0
    elif [ ${RETRY_COUNT} -eq 10 ]; then
        echo "> Health check failed."
        exit 1
    fi
    sleep 5  # 각 시도 마다 5초간 대기
done