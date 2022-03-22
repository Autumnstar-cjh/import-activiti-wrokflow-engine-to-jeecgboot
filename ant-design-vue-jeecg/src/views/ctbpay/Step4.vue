<template>
  <div>
    <a-form style="margin: 40px auto 0;">
      <result title="支付成功" :is-success="true" description="">
        <div class="information">
          <a-row>
            <a-col :sm="8" :xs="24">商户号：</a-col>
            <a-col :sm="16" :xs="24">{{params.cusid}}</a-col>
          </a-row>
          
          <a-row>
            <a-col :sm="8" :xs="24">收银宝交易流水号：</a-col>
            <a-col :sm="16" :xs="24">{{params.trxid}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">渠道平台交易单号：</a-col>
            <a-col :sm="16" :xs="24">{{params.chnltrxid}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">商户交易单号：</a-col>
            <a-col :sm="16" :xs="24">{{params.reqsn}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">交易完成时间：</a-col>
            <a-col :sm="16" :xs="24">{{timeFormate(params.fintime)}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">原交易金额：</a-col>
            <a-col :sm="16" :xs="24">{{params.initamt}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">实际交易金额：</a-col>
            <a-col :sm="16" :xs="24">{{params.trxamt}}</a-col>
          </a-row>
          <a-row>
            <a-col :sm="8" :xs="24">手续费：</a-col>
            <a-col :sm="16" :xs="24">{{params.fee}}</a-col>
          </a-row>

        </div>
        <div slot="action">
          <a-button type="primary" @click="finish">再转一笔</a-button>
          <a-button style="margin-left: 8px" @click="toOrderList">查看账单</a-button>
        </div>
      </result>
    </a-form>
  </div>
</template>

<script>
  import Result from '../result/Result'

  export default {
    name: "Step4",
    components: {
      Result
    },
    data () {
      return {
        loading: false,
        params:{
          cusid:'',

        }
      }
    },
    methods: {
      finish () {
        this.$emit('finish')
      },
      toOrderList () {
        this.$router.push('/okr/Orders')
      },

      dataInit(params){
        this.params = params
      },

      timeFormate(t){
        if(t === null || t === undefined){
          t=''
        }
        var year = t.slice(0,4)
        var month = t.slice(4,6)
        var day = t.slice(6,8)

        var h = t.slice(8,10)
        var m = t.slice(10,12)
        var s = t.slice(12,14)


        var time = year+"-"+month+"-"+day+" "+h+":"+m+":"+s
        console.log("装换后时间是："+year+"-"+month+"-"+day+" "+h+":"+m+":"+s)
        return time
      }
    }
  }
</script>
<style lang="scss" scoped>
  .information {
    line-height: 22px;

    .ant-row:not(:last-child) {
      margin-bottom: 24px;
    }
  }
  .money {
    font-family: "Helvetica Neue",sans-serif;
    font-weight: 500;
    font-size: 20px;
    line-height: 14px;
  }
</style>