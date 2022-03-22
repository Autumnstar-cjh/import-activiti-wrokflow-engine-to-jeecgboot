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
        <a-form-item label="报表日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择报表日期" v-decorator="[ 'reportDate', validatorRules.reportDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="充值金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'rechargeTotal', validatorRules.rechargeTotal]" placeholder="请输入充值金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="充值订单笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'rechargeOrderTotal', validatorRules.rechargeOrderTotal]" placeholder="请输入充值订单笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="消费金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'purchaseTotal', validatorRules.purchaseTotal]" placeholder="请输入消费金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="消费订单笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'purchaseOrderTotal', validatorRules.purchaseOrderTotal]" placeholder="请输入消费订单笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="提现金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'withdrawTotal', validatorRules.withdrawTotal]" placeholder="请输入提现金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="提现订单笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'withdrawOrderTotal', validatorRules.withdrawOrderTotal]" placeholder="请输入提现订单笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="委托代收金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'agentCollectTotal', validatorRules.agentCollectTotal]" placeholder="请输入委托代收金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="委托代收笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'agentCollectOrderTotal', validatorRules.agentCollectOrderTotal]" placeholder="请输入委托代收笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="委托代付金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'agentPayTotal', validatorRules.agentPayTotal]" placeholder="请输入委托代付金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="委托代付笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'agentPayOrderTotal', validatorRules.agentPayOrderTotal]" placeholder="请输入委托代付笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="批量代付金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'batchPayTotal', validatorRules.batchPayTotal]" placeholder="请输入批量代付金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="批量代付笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'batchPayOrderTotal', validatorRules.batchPayOrderTotal]" placeholder="请输入批量代付笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="退款金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'refundTotal', validatorRules.refundTotal]" placeholder="请输入退款金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="退款笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'refundOrderTotal', validatorRules.refundOrderTotal]" placeholder="请输入退款笔数" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: "CloudAccTrxModal",
    components: { 
      JDate,
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
        reportDate:{},
        rechargeTotal:{},
        rechargeOrderTotal:{},
        purchaseTotal:{},
        purchaseOrderTotal:{},
        withdrawTotal:{},
        withdrawOrderTotal:{},
        agentCollectTotal:{},
        agentCollectOrderTotal:{},
        agentPayTotal:{},
        agentPayOrderTotal:{},
        batchPayTotal:{},
        batchPayOrderTotal:{},
        refundTotal:{},
        refundOrderTotal:{},
        },
        url: {
          add: "/cloudacc/cloudAccTrx/add",
          edit: "/cloudacc/cloudAccTrx/edit",
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
          this.form.setFieldsValue(pick(this.model,'appId','appName','reportDate','rechargeTotal','rechargeOrderTotal','purchaseTotal','purchaseOrderTotal','withdrawTotal','withdrawOrderTotal','agentCollectTotal','agentCollectOrderTotal','agentPayTotal','agentPayOrderTotal','batchPayTotal','batchPayOrderTotal','refundTotal','refundOrderTotal'))
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
        this.form.setFieldsValue(pick(row,'appId','appName','reportDate','rechargeTotal','rechargeOrderTotal','purchaseTotal','purchaseOrderTotal','withdrawTotal','withdrawOrderTotal','agentCollectTotal','agentCollectOrderTotal','agentPayTotal','agentPayOrderTotal','batchPayTotal','batchPayOrderTotal','refundTotal','refundOrderTotal'))
      },

      
    }
  }
</script>