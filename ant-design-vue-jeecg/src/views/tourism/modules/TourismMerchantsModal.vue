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

        <a-form-item label="商户号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'merchantCode', validatorRules.merchantCode]" placeholder="请输入商户号" :readonly="status ? false : 'readonly'"></a-input>
        </a-form-item>
        <a-form-item label="商户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'merchantsName', validatorRules.merchantsName]" placeholder="请输入商户名称"></a-input>
        </a-form-item>
        <a-form-item label="审核意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['auditStatus', validatorRules.auditStatus]" :trigger-change="true" dictCode="audit_status" placeholder="请选择审核意见"/>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['comment']" rows="4" placeholder="请输入备注"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "TourismMerchantsModal",
    components: { 
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        status: true,
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
        merchantCode:{rules: [{ required: true, message: '请输入商户号!' }]},
        merchantsName:{rules: [{ required: true, message: '请输入商户名称!' }]},
        auditStatus:{rules: [{ required: true, message: '请选择审核意见!' }]},
        comment:{},
        },
        url: {
          add: "/tourism/tourismMerchants/add",
          edit: "/tourism/tourismMerchants/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.status = true;//商户号输入框只读状态 为false只读
        let record = {};
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'merchantCode','merchantsName','auditStatus','comment'))
        })
      },
      edit (record) {
        this.status = false;//商户号输入框只读状态 为false只读
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'merchantCode','merchantsName','auditStatus','comment'))
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
        this.form.setFieldsValue(pick(row,'merchantCode','merchantsName','auditStatus','comment'))
      },

      
    }
  }
</script>