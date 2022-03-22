<template>
  <div>
    <a-form style="max-width: 500px; margin: 40px auto 0;">
      <a-alert
        :closable="true"
        message="请您确认支付信息"
        style="margin-bottom: 24px;"
      />
          
      <a-form-item label="交易金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.trxamt}}
      </a-form-item>

      <a-form-item label="交易方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.paytype}}
      </a-form-item>
      
      <a-form-item label="订单标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.body}}
      </a-form-item>

      <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.remark}}
      </a-form-item>

      <a-form-item v-show="false" label="有效时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.validtime}}
      </a-form-item>

      <a-form-item v-show="false" label="支付平台用户标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.acct}}
      </a-form-item>

      <a-form-item v-show="false" label="交易结果通知地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.notify_url}}
      </a-form-item>

      <a-form-item v-show="false" label="支付限制" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.limit_pay}}
      </a-form-item>
        
      <a-form-item v-show="false" label="微信子appid" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.sub_appid}}
      </a-form-item>


      <a-form-item v-show="false" label="订单优惠标记" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.goods_tag}}
      </a-form-item>

      <a-form-item v-show="false" label="优惠信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.benefitdetail}}
      </a-form-item>

      <a-form-item v-show="false" label="渠道门店编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.chnlstoreid}}
      </a-form-item>

      <a-form-item v-show="false" label="门店号" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.subbranch}}
      </a-form-item>

      <a-form-item v-show="false" label="证件号" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.idno}}
      </a-form-item>

      <a-form-item v-show="false" label="拓展参数" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.extendparams}}
      </a-form-item>

      <a-form-item v-show="false" label="终端ip" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.cusip}}
      </a-form-item>

      <a-form-item v-show="false" label="支付完成跳转" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.front_url}}
      </a-form-item>

      <a-form-item v-show="false" label="付款人真实姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.truename}}
      </a-form-item>

      <a-form-item v-show="false" label="分账信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.asinfo}}
      </a-form-item>

      <a-form-item v-show="false" label="花呗分期" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.fqnum}}
      </a-form-item>

      <a-form-item label="商品ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.goodsId}}
      </a-form-item>

      <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.goodsName}}
      </a-form-item>

      <a-form-item :wrapperCol="{span: 19, offset: 5}">
        <a-button :loading="loading" type="primary" @click="submitHandle">支付</a-button>
        <a-button style="margin-left: 8px" @click="prevStep">上一步</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
import { httpAction,getAction } from '@/api/manage'

var count = 0

  export default {
    name: "Step2",
    data () {
      return {
        loading: false,
        params:{
          trxamt:'',
          paytype:'',
          body:'',
          remark:'',
          validtime:'',
          acct:'',
          notify_url:'',
          limit_pay:'',
          sub_appid:'',
          goods_tag:'',
          benefitdetail:'',
          chnlstoreid:'',
          subbranch:'',
          extendparams:'',
          cusip:'',
          front_url:'',
          idno:'',
          truename:'',
          asinfo:'',
          fqnum:'',
          goodsId:'',
          goodsName:''
        },
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
      }
    },
    created(){
      
    },
    methods: {
      dataInit(params){
        // console.log("确认页接收到表单数据：",params)
        this.params = params
      },

      //支付提交
      submitHandle(){
        let that = this
          console.log("表单提交数据",that.params)
          let httpurl = '/order/pay/pay'
          let method = 'post'

          httpAction(httpurl,that.params,method).then((res)=>{
            console.log("c扫b测试返回数据：",res)
            if(res.success){
                if(res.result.trxstatus == '0000'){
                  that.$emit('toStep3',res.result)
                }
            }else{
                that.$message.error(res.message,4);
            }
          }).finally(() => {
              
          })
      },

      nextStep (param) {
        let that = this
        that.$emit('toStep3',param)
      },
      prevStep () {
        this.loading = false
        this.$emit('prevStep')
      }
    }
  }
</script>

<style lang="scss" scoped>
  .stepFormText {
    margin-bottom: 24px;

    .ant-form-item-label,
    .ant-form-item-control {
      line-height: 22px;
    }
  }

</style>