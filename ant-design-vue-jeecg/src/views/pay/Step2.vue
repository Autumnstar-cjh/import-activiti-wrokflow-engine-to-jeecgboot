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

      <a-form-item label="支付授权码" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.authcode}}
      </a-form-item>
      
      <a-form-item label="订单标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.body}}
      </a-form-item>

      <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.remark}}
      </a-form-item>

      <a-form-item v-show="false" label="支付限制" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.limit_pay}}
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

      <a-form-item v-show="false" label="付款人真实姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.truename}}
      </a-form-item>

      <a-form-item v-show="false" label="分账信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.asinfo}}
      </a-form-item>

      <a-form-item v-show="false" label="花呗分期" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.fqnum}}
      </a-form-item>

      <a-form-item v-show="false" label="商品ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
        {{params.goodsId}}
      </a-form-item>

      <a-form-item v-show="false" label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
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
          body:'',
          remark:'',
          authcode:'',
          limit_pay:'',
          goods_tag:'',
          benefitdetail:'',
          chnlstoreid:'',
          subbranch:'',
          idno:'',
          extendparams:'',
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
          let httpurl = '/order/pay/scanqrpay'
          let method = 'post'

          httpAction(httpurl,that.params,method).then((res)=>{
            if(res.success){
                if(res.code == '200'){
                  if(res.result.errmsg == '等待用户支付'){
                    that.$message.success(res.result.errmsg,3);
                    that.isPay(res.result.reqsn,res.result.trxid)
                  }else{
                    that.$message.error(res.result.errmsg,3);
                  }
                  
                }
            }else{
                that.$message.error(res.message,4);
            }
          }).finally(() => {
              
          })
      },

      
      //5秒钟查询一次是订单状态   query
      isPay(reqsn,trxid){
        let that = this
        that.loading = true
        console.log("流水号：",trxid)
        var myVar = setInterval(function(){ 
          count++
          console.log("标记数字count=",count)
          getAction('/order/pay/query', {reqsn:reqsn,trxid:trxid}).then((res)=>{
            console.log("查询返回结果：",res)
            if(res.success){
              if(res.result.trxstatus == '0000'){
                clearInterval(myVar)
                that.loading = false
                count=0
                that.nextStep(res.result)
              }else{
                if(count > 20){
                  that.loading = false
                  that.$message.error('支付超时！',2);
                  clearInterval(myVar)
                  count=0
                }
              }
            }else{
              that.$message.error(res.message,3);
            }
          })
        }, 3000);
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