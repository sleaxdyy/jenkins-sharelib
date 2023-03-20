# jenkins-sharelib

## Tạo repo để chứa tạo thư viện Pipelines
- Tạo thư mục vars -> tạo file sendTelegram.groovy 
```
def call(String config){
def botUrl = "https://api.telegram.org/bot6105612158:AAES_JhvfeoZOBo3koQxaq9e-ZQphRw_Alc/sendMessage"
def bot = { text -> sh "curl -s -X POST ${botUrl} -d chat_id=-1001891493640 -d text='${text}'" }
  bot("${config}")
}
```
## Cấu hình để thêm thư viện vào Jenkins
Dashboard -> Manage Jenkins -> System ->  Global Pipeline Libraries
- Đặt tên cho thư viện (Tên này sẽ được sử dụng trong lệnh @Library(Tên thư viện))
- Default version là phiên bản mặc định của thư viện (Ở đây nó chính là branch repo github "main")
- Chọn vào Load implicitly
- Ở mục Retrieval method -> chọn Mordern SCM
  - Tại Source Code Management -> Git -> Điền URL Repo + add Credential

## Pipeline
```
@Library('notify-shared-lib')_
pipeline {
  agent any
  stages{
    stage('Build') {
      steps{
          sh "echo Hello"
      }
    }
  }
  post {
    failure {
      sendTelegram("Log: ${BUILD_URL}console\nJob: ${JOB_NAME}\nBuild # ${BUILD_NUMBER}\nStatus: failure")
    }
    success {
      sendTelegram("Log: ${BUILD_URL}console\nJob: ${JOB_NAME}\nBuild # ${BUILD_NUMBER}\nStatus: success")
    }
  }
}
```
## Thêm credentials cho Git
Dashboard -> Manage Jenkins -> Credentials -> System -> Global credentials (unrestricted) -> Add Credentials
- Kind : chọn SSH Username with 
- Scope : chọn Global
- Username : Username của github 
- Password : Access token của tài khoản github
