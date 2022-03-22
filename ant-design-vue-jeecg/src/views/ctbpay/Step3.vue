<template>
  <div>
    <a-form style="margin: 40px auto 0;">
      <div class="body3">
        <div class="title-message">
            请扫描二维码完成支付
        </div>
          
        <div class="code">
          <div class="qrcode" id="qrcode" ref="qrcode"></div>
        </div>

        <div class="information">
          <div class="dosomething">
            <a-button type="primary" class="finish" @click="complete">完成</a-button>
            <a-button class="prevStep" @click="prevStep">上一步</a-button>
          </div>
        </div>
      </div>
    </a-form>
  </div>
</template>

<script>
  import Result from '../result/Result'
  import QRCode from 'qrcodejs2'

  export default {
    name: "Step3",
    components: {
      Result
    },
    data () {
      return {
        loading: false,
        params:{
          appid: '',
          chnltrxid: '',
          cusid: '',
          payinfo: '',
          randomstr: '',
          reqsn: '',
          retcode: '',
          sign: '',
          trxid: '',
          trxstatus: ''
        }
      }
    },
    methods: {

      dataInit(params){
        this.params = params
        this.createQrcode(params.payinfo)
      },

      createQrcode(content){
        // 生成二维码
        new QRCode('qrcode', {
          width: 200,  //宽度
          height: 200, // 高度
          text: content, // 二维码内容
        })
        
      },

      prevStep(){
        //清除已经生成的二维码   
        this.$refs.qrcode.innerHTML = ''
        this.$emit('prevStep')
      },
      complete(){
        //清除已经生成的二维码   
        this.$refs.qrcode.innerHTML = ''
        this.$emit('toStep4',this.params)
      },
    }
  }
</script>
<style lang="scss" scoped>
  .title-message{
    width: 100%;
    text-align: center;
    font-size: 20px;
    font-weight: bold;
    padding-top: 20px;
    color: rgb(0, 0, 0);
    
  }
  .information {
    width: 100%;
    height: 400px;
  }
  .money {
    font-family: "Helvetica Neue",sans-serif;
    font-weight: 500;
    font-size: 20px;
    line-height: 14px;
  }

  .body3{
    width: 100%;
  }
  .code{
    width: 100%;
    height: 200px;
    margin-top: 50px;
  }
  .qrcode{
    width: 200px;
    height: 200px;
    margin: auto;
  }
  .dosomething{
    width: 200px;
    margin: auto;
    margin-top: 50px;
    background-color: red;
  }
  .finish{
    float: left;
  }
  .prevStep{
    float: right;
  }
</style>