<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="应用编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'appId', validatorRules.appId]" placeholder="请输入应用编号"></a-input>
        </a-form-item>
        <a-form-item label="应用名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'appName', validatorRules.appName]" placeholder="请输入应用名称"></a-input>
        </a-form-item>
        <a-form-item label="总金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'amountTotal', validatorRules.amountTotal]" placeholder="请输入总金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="总笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'orderTotal', validatorRules.orderTotal]" placeholder="请输入总笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="入金金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'incomeAmountTotal', validatorRules.incomeAmountTotal]" placeholder="请输入入金金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="入金笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'incomeOrderTotal', validatorRules.incomeOrderTotal]" placeholder="请输入入金笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="退款金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'refundAmountTotal', validatorRules.refundAmountTotal]" placeholder="请输入退款金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="退款笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'refundOrderTotal', validatorRules.refundOrderTotal]" placeholder="请输入退款笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="出金金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'withdrawAmountTotal', validatorRules.withdrawAmountTotal]" placeholder="请输入出金金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="出金笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'withdrawOrderTotal', validatorRules.withdrawOrderTotal]" placeholder="请输入出金笔数" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "CloudAccTrxStatModal",
    components: { 
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        appId:{},
        appName:{},
        amountTotal:{},
        orderTotal:{},
        incomeAmountTotal:{},
        incomeOrderTotal:{},
        refundAmountTotal:{},
        refundOrderTotal:{},
        withdrawAmountTotal:{},
        withdrawOrderTotal:{},
        },
        url: {
          add: "/cloudacc/cloudAccTrxStat/add",
          edit: "/cloudacc/cloudAccTrxStat/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'appId','appName','amountTotal','orderTotal','incomeAmountTotal','incomeOrderTotal','refundAmountTotal','refundOrderTotal','withdrawAmountTotal','withdrawOrderTotal'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              httpurl+=this.url.add;
              method = 'post';
            }else{
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
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
          }
         
        })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'appId','appName','amountTotal','orderTotal','incomeAmountTotal','incomeOrderTotal','refundAmountTotal','refundOrderTotal','withdrawAmountTotal','withdrawOrderTotal'))
      },

      
    }
  }
</script>