<template>
  <a-drawer
    :title="title"
    :width="drawerWidth"
    @ok="handleOk"
    @close="handleCancel"
    :visible="visible"
    :confirmLoading="confirmLoading"
    :wrapStyle="{height: 'calc(100% - 108px)',overflow: 'auto',paddingBottom: '108px'}"
  >
    <div :style="{width: '100%',padding: '10px 16px',background: '#fff',}">
      
      <a-spin :spinning="confirmLoading">
        <a-form :form="form">

          <a-form-item label="交易金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'trxamt', validatorRules.trxamt]" placeholder="请输入金额"></a-input>
          </a-form-item>

          <a-form-item v-show="false" label="交易方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'paytype', validatorRules.paytype]" placeholder="请输入交易方式"></a-input>
          </a-form-item>

          <a-form-item label="订单标题" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'body', validatorRules.body]" placeholder="请输入标题"></a-input>
          </a-form-item>

          <a-form-item label="订单备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
          </a-form-item>

          <a-form-item v-show="false" label="支付平台用户标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'acct', validatorRules.acct]" placeholder="请输入用户标识"></a-input>
          </a-form-item>
          
          <a-form-item label="付款码编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'authcode', validatorRules.authcode]" placeholder="请输入付款码编号"></a-input>
          </a-form-item>

          <a-form-item v-show="false" label="交易结果通知地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'notify_url', validatorRules.notify_url]" placeholder="请输入交易结果通知地址url"></a-input>
          </a-form-item>

          <a-form-item v-show="false" label="支付限制" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'limit_pay', validatorRules.limit_pay]" placeholder="请输入支付限制"></a-input>
          </a-form-item>

          <a-form-item v-show="false" label="证件号" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'idno', validatorRules.idno]" placeholder="请输入证件号"></a-input>
          </a-form-item>

          <a-form-item v-show="false" label="姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'truename', validatorRules.truename]" placeholder="请输入姓名"></a-input>
          </a-form-item>
          

          <a-form-item v-show="false" label="分账信息" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-input v-decorator="[ 'asinfo', validatorRules.asinfo]" placeholder="请输入分账信息"></a-input>
          </a-form-item>

        </a-form>
    </a-spin>




      <a-row :style="{textAlign:'right'}">
        <a-button :style="{marginRight: '8px'}" @click="handleCancel">
          关闭
        </a-button>
        <a-button :disabled="disableSubmit" @click="handleOk" type="primary">确定</a-button>
        <a-button type="link" @click="handleQuey">查询</a-button>
      </a-row>
    </div>
  </a-drawer>
</template>

<script>
  import {addPermission,editPermission,queryTreeList} from '@/api/api'
  import { httpAction,getAction } from '@/api/manage'
  import pick from 'lodash.pick'


  export default {
    name: "PayModal", 
    data () {
      return {
        form: this.$form.createForm(this),
        drawerWidth:700,
        title:"操作",
        visible: false,
        disableSubmit:false,
        confirmLoading: false,
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
            paytype:{},
            body:{},
            remark:{},
            acct:{},
            authcode:{rules: [{ required: true, message: '请输入付款码编号!' }]},
            notify_url:{},
            limit_pay:{},
            idno:{},
            truename:{},
            asinfo:{},
            
        },
      }
    },
    created () {
    },
    methods: {
      pay() {
        this.form.resetFields();
        this.model = Object.assign({});
        this.visible = true;
        this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model,'trxamt','paytype','body','remark','acct','authcode','notify_url','limit_pay','idno','truename','asinfo'))
        })
      },
      close () {
        this.$emit('close');
        this.disableSubmit = false;
        this.visible = false;
      },
      /**
       * 支付
       */
      handleOk () {
        console.log("交易...")
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '/order/pay/pay';
            let method = 'post';
            
            let formData = Object.assign(this.model, values);

            var body = ''
            var remark = ''
            if(formData.body != undefined){
              body = formData.body
            }
            if(formData.remark != undefined){
              remark = formData.remark
            }

            let params = {
              trxamt:formData.trxamt,
              paytype:'W04',
              body:body,
              remark:remark,
              acct:'',
              authcode:formData.authcode,
              notify_url:'',
              limit_pay:'',
              idno:'',
              truename:'',
              asinfo:'',
              goodsId:'1001',
              goodsName:'测试用的商品'
            }
            console.log("表单提交数据",formData)
            // return
            httpAction(httpurl,params,method).then((res)=>{
              console.log("支付返回数据：",res)
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }else{
            console.log("错误！",err)
          }
         
        })
      },
      handleQuey(){
        getAction('/order/pay/query', {reqsn:'',trxid:'112021100002288419'}).then((res)=>{
          if(res.success){
            console.log(res)
          }
        })
      },
      handleCancel () {
        this.close()
      },
      
    }
  }
</script>

<style scoped>

</style>