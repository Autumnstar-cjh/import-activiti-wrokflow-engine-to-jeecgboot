<template>
  <a-card :bordered="false">
    <div class="send-div">
      <a-button type="primary" class="send-email-btn" @click="toSend('all')">群发</a-button>
      <a-button class="send-email-btn" @click="toSend('dingyan')">To 丁艳</a-button>
      <a-button class="send-email-btn" @click="toSend('yuanping')">To 袁平</a-button>
      <a-button class="send-email-btn" @click="toSend('hanyuzhe')">To 韩雨哲</a-button>
      <a-button class="send-email-btn" @click="toSend('dyhyz')">To 丁艳&韩雨哲</a-button>

      <!-- <a-button class="send-email-btn" @click="toSendV2()">发送邮件版本2测试</a-button> -->
    </div>
  </a-card>
</template>

<script>
  import { filterObj } from '@/utils/util'
  import { getAction,putAction } from '@/api/manage'

  export default {
    name: "SendEmail",
    data () {
      return {
        
      }
    },
    methods: {
      toSend(name){
        let sendTo = ''
        let copyTo = ''
        if(name=="all"){
          sendTo = 'dingyan@allinpay.com,lirj2@allinpay.com,dongyx@allinpay.com,gaote@allinpay.com,fuxl@allinpay.com,lanql@allinpay.com,lven@allinpay.com,wangsp@allinpay.com,wenbo@allinpay.com,yuanping1@allinpay.com'
          copyTo = 'shengdong@allinpay.com,make@allinpay.com,ziqi@allinpay.com,machi@allinpay.com'
        }
        if(name=="dingyan"){
          sendTo = 'dingyan@allinpay.com'
        }
        if(name=="hanyuzhe"){
          sendTo = 'hanyz1@allinpay.com'
        }
        if(name=='yuanping'){
          sendTo = 'yuanping1@allinpay.com'
        }
        if(name=="dyhyz"){
          sendTo = 'hanyz1@allinpay.com,dingyan@allinpay.com'
        }
        getAction('/email/sendEmail/toSendMail', {sendTo:sendTo,copyTo:copyTo}).then((res)=>{
          if(res.success){
            alert(res.message)
          }
        })
      },
      /**
       * 发送邮件版本二测试
       */
      toSendV2(){
        getAction('/email/mailConfig/queryList').then((res)=>{
          if(res.success){
            alert(res)
          }
        })
      }
    }
  }
</script>
<style scoped>
  .send-div{
    height: 660px;
  }
  .send-email-btn{
    margin: 10px;
  }
</style>