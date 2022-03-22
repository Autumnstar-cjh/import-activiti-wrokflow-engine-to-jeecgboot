<template>
  <div>
    <a-form style="max-width: 500px; margin: 40px auto 0;" :form="form">

      <a-form-item label="交易金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'trxamt', validatorRules.trxamt]" placeholder="请输入金额"></a-input>
      </a-form-item>

      <a-form-item label="支付授权码" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'authcode', validatorRules.authcode]" placeholder="请输入支付授权码"></a-input>
      </a-form-item>
      
      <a-form-item label="订单标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'body', validatorRules.body]" placeholder="请输入订单标题"></a-input>
      </a-form-item>

      <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="支付限制" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'limit_pay', validatorRules.limit_pay]" placeholder="请输入支付限制"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="订单优惠标记" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'goods_tag', validatorRules.goods_tag]" placeholder="请输入订单优惠标记"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="优惠信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'benefitdetail', validatorRules.benefitdetail]" placeholder="请输入优惠信息"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="渠道门店编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'chnlstoreid', validatorRules.chnlstoreid]" placeholder="请输入渠道门店编号"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="门店号" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'subbranch', validatorRules.subbranch]" placeholder="请输入门店号"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="证件号" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'idno', validatorRules.idno]" placeholder="请输入证件号"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="拓展参数" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'extendparams', validatorRules.extendparams]" placeholder="请输入拓展参数"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="付款人真实姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'truename', validatorRules.truename]" placeholder="请输入付款人真实姓名"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="分账信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'asinfo', validatorRules.asinfo]" placeholder="请输入分账信息"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="花呗分期" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'fqnum', validatorRules.fqnum]" placeholder="请输入花呗分期"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="商品ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'goodsId', validatorRules.goodsId]" placeholder="请输入商品ID"></a-input>
      </a-form-item>

      <a-form-item v-show="false" label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
        <a-input v-decorator="[ 'goodsName', validatorRules.goodsName]" placeholder="请输入商品名称"></a-input>
      </a-form-item>




      <a-form-item :wrapperCol="{span: 19, offset: 5}">
        <a-button type="primary" @click="nextStep">下一步</a-button>
      </a-form-item>
    </a-form>
  </div>
</template>

<script>
  import pick from 'lodash.pick'

  export default {
    name: "Step1",
    data(){
      return{
        form: this.$form.createForm(this),
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        validatorRules:{
            trxamt:{rules: [{ required: true, message: '请输入交易金额!' }]},
            body:{},
            remark:{},
            authcode:{rules: [{ required: true, message: '请输入支付授权码!' }]},
            limit_pay:{},
            goods_tag:{},
            benefitdetail:{},
            chnlstoreid:{},
            subbranch:{},
            idno:{},
            extendparams:{},
            truename:{},
            asinfo:{},
            fqnum:{},
            goodsId:{},
            goodsName:{}
            
        },
      }
    },
    created(){
      this.form.resetFields();
        this.model = Object.assign({});
        this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model,'trxamt','body','remark','authcode','limit_pay','goods_tag','benefitdetail','chnlstoreid','subbranch','idno','extendparams','truename','asinfo','fqnum','goodsId','goodsName'))
        })
    },
    methods: {
      nextStep () {
        // console.log("交易...")
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            let formData = Object.assign(this.model, values);
            
            if(formData.body == null || formData.body == undefined){
              formData.body = ''
            }
            if(formData.remark == null || formData.remark == undefined){
              formData.remark = ''
            }
            if(formData.limit_pay == null || formData.limit_pay == undefined){
              formData.limit_pay = ''
            }
            if(formData.goods_tag == null || formData.goods_tag == undefined){
              formData.goods_tag = ''
            }
            if(formData.benefitdetail == null || formData.benefitdetail == undefined){
              formData.benefitdetail = ''
            }
            if(formData.chnlstoreid == null || formData.chnlstoreid == undefined){
              formData.chnlstoreid = ''
            }
            if(formData.subbranch == null || formData.subbranch == undefined){
              formData.subbranch = ''
            }
            if(formData.idno == null || formData.idno == undefined){
              formData.idno = ''
            }
            if(formData.extendparams == null || formData.extendparams == undefined){
              formData.extendparams = ''
            }
            if(formData.truename == null || formData.truename == undefined){
              formData.truename = ''
            }
            if(formData.asinfo == null || formData.asinfo == undefined){
              formData.asinfo = ''
            }
            if(formData.fqnum == null || formData.fqnum == undefined){
              formData.fqnum = ''
            }
            if(formData.goodsId == null || formData.goodsId == undefined){
              formData.goodsId = ''
            }
            if(formData.goodsName == null || formData.goodsName == undefined){
              formData.goodsName = ''
            }
            this.$emit('toStep2',formData)
          }else{
            console.log("错误！",err)
          }
         
        })
      }
    }
  }
</script>

<style scoped>

</style>