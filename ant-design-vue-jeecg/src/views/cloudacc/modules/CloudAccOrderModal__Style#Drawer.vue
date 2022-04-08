<template>
  <a-drawer
    :title="title"
    :width="width"
    placement="right"
    :closable="false"
    @close="close"
    :visible="visible">
  
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="创建人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入创建人"></a-input>
        </a-form-item>
        <a-form-item label="创建日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择创建日期" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="更新人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'updateBy', validatorRules.updateBy]" placeholder="请输入更新人"></a-input>
        </a-form-item>
        <a-form-item label="更新日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择更新日期" v-decorator="[ 'updateTime', validatorRules.updateTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="所属部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'sysOrgCode', validatorRules.sysOrgCode]" placeholder="请输入所属部门"></a-input>
        </a-form-item>
        <a-form-item label="应用编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'appId', validatorRules.appId]" placeholder="请输入应用编号"></a-input>
        </a-form-item>
        <a-form-item label="应用名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'appName', validatorRules.appName]" placeholder="请输入应用名称"></a-input>
        </a-form-item>
        <a-form-item label="起始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择起始时间" v-decorator="[ 'reportDate', validatorRules.reportDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="总金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'amountTotal', validatorRules.amountTotal]" placeholder="请输入总金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="总笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'orderTotal', validatorRules.orderTotal]" placeholder="请输入总笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="充值金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'popupAmount', validatorRules.popupAmount]" placeholder="请输入充值金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="充值笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'popupNum', validatorRules.popupNum]" placeholder="请输入充值笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="消费笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'consAmount', validatorRules.consAmount]" placeholder="请输入消费笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="消费笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'consNum', validatorRules.consNum]" placeholder="请输入消费笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="提现金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'withdrAmount', validatorRules.withdrAmount]" placeholder="请输入提现金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="提现笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'withdrNum', validatorRules.withdrNum]" placeholder="请输入提现笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="代收金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'collAmount', validatorRules.collAmount]" placeholder="请输入代收金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="代收笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'collNum', validatorRules.collNum]" placeholder="请输入代收笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="代付金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'paidAmount', validatorRules.paidAmount]" placeholder="请输入代付金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="代付笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'paidNum', validatorRules.paidNum]" placeholder="请输入代付笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="批量代付金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'batPaidAmount', validatorRules.batPaidAmount]" placeholder="请输入批量代付金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="批量代付笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'batPaidNum', validatorRules.batPaidNum]" placeholder="请输入批量代付笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="跨应用转账金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'transferAmount', validatorRules.transferAmount]" placeholder="请输入跨应用转账金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="跨应用转账笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'transferNum', validatorRules.transferNum]" placeholder="请输入跨应用转账笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="退款金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'refundAmount', validatorRules.refundAmount]" placeholder="请输入退款金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="退款笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'refundNum', validatorRules.refundNum]" placeholder="请输入退款笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="平台转账金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'plTraAmount', validatorRules.plTraAmount]" placeholder="请输入平台转账金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="平台转账笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'plaTraNum', validatorRules.plaTraNum]" placeholder="请输入平台转账笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="理财金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'fiAmount', validatorRules.fiAmount]" placeholder="请输入理财金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="理财笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'fiNum', validatorRules.fiNum]" placeholder="请输入理财笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="协议消费金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'agrPayAmount', validatorRules.agrPayAmount]" placeholder="请输入协议消费金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="协议消费笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'agrPayNum', validatorRules.agrPayNum]" placeholder="请输入协议消费笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="协议代收金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'agrCollAmount', validatorRules.agrCollAmount]" placeholder="请输入协议代收金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="协议代收笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'agrCollNum', validatorRules.agrCollNum]" placeholder="请输入协议代收笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="跨境提现金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'crossAmount', validatorRules.crossAmount]" placeholder="请输入跨境提现金额" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="跨境提现笔数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'crossNum', validatorRules.crossNum]" placeholder="请输入跨境提现笔数" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="跨境提现金额1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'croAmountO', validatorRules.croAmountO]" placeholder="请输入跨境提现金额1" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="跨境提现笔数1" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'croNumO', validatorRules.croNumO]" placeholder="请输入跨境提现笔数1" style="width: 100%"/>
        </a-form-item>
        
      </a-form>
    </a-spin>
    <a-button type="primary" @click="handleOk">确定</a-button>
    <a-button type="primary" @click="handleCancel">取消</a-button>
  </a-drawer>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  
  export default {
    name: "CloudAccOrderModal",
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
        createBy:{},
        createTime:{},
        updateBy:{},
        updateTime:{},
        sysOrgCode:{},
        appId:{},
        appName:{},
        reportDate:{},
        amountTotal:{},
        orderTotal:{},
        popupAmount:{},
        popupNum:{},
        consAmount:{},
        consNum:{},
        withdrAmount:{},
        withdrNum:{},
        collAmount:{},
        collNum:{},
        paidAmount:{},
        paidNum:{},
        batPaidAmount:{},
        batPaidNum:{},
        transferAmount:{},
        transferNum:{},
        refundAmount:{},
        refundNum:{},
        plTraAmount:{},
        plaTraNum:{},
        fiAmount:{},
        fiNum:{},
        agrPayAmount:{},
        agrPayNum:{},
        agrCollAmount:{},
        agrCollNum:{},
        crossAmount:{},
        crossNum:{},
        croAmountO:{},
        croNumO:{},
        },
        url: {
          add: "/cloudacc/cloudAccOrder/add",
          edit: "/cloudacc/cloudAccOrder/edit",
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','updateBy','updateTime','sysOrgCode','appId','appName','reportDate','amountTotal','orderTotal','popupAmount','popupNum','consAmount','consNum','withdrAmount','withdrNum','collAmount','collNum','paidAmount','paidNum','batPaidAmount','batPaidNum','transferAmount','transferNum','refundAmount','refundNum','plTraAmount','plaTraNum','fiAmount','fiNum','agrPayAmount','agrPayNum','agrCollAmount','agrCollNum','crossAmount','crossNum','croAmountO','croNumO'))
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','updateBy','updateTime','sysOrgCode','appId','appName','reportDate','amountTotal','orderTotal','popupAmount','popupNum','consAmount','consNum','withdrAmount','withdrNum','collAmount','collNum','paidAmount','paidNum','batPaidAmount','batPaidNum','transferAmount','transferNum','refundAmount','refundNum','plTraAmount','plaTraNum','fiAmount','fiNum','agrPayAmount','agrPayNum','agrCollAmount','agrCollNum','crossAmount','crossNum','croAmountO','croNumO'))
      }
      
    }
  }
</script>

<style lang="less" scoped>
/** Button按钮间距 */
  .ant-btn {
    margin-left: 30px;
    margin-bottom: 30px;
    float: right;
  }
</style>