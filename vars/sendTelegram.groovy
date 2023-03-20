def call(String config){
def botUrl = "https://api.telegram.org/bot6105612158:AAES_JhvfeoZOBo3koQxaq9e-ZQphRw_Alc/sendMessage"
def bot = { text -> sh "curl -s -X POST ${botUrl} -d chat_id=-1001891493640 -d text='${text}'" }
  bot("${config}")
}
