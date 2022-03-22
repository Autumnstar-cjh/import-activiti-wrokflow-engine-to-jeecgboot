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
          <a-input v-decorator="[ 'merchantCode', validatorRules.merchantCode]" placeholder="请输入商户号"></a-input>
        </a-form-item>
        <a-form-item label="商户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'merchantName', validatorRules.merchantName]" placeholder="请输入商户名称"></a-input>
        </a-form-item>
        <a-form-item label="拓展人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'developPeople', validatorRules.developPeople]" placeholder="请输入拓展人"></a-input>
        </a-form-item>
        <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'department', validatorRules.department]" placeholder="请输入部门"></a-input>
        </a-form-item>
        <a-form-item label="是否为新增商户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['isNew']" :trigger-change="true" dictCode="is_new" placeholder="请选择是否为新增商户"/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'

  export default {
    name: "QualityMerchantModal",
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
        merchantCode:{},
        merchantName:{},
        developPeople:{},
        department:{},
        },
        url: {
          add: "/quality/qualityMerchant/add",
          edit: "/quality/qualityMerchant/edit",
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
        console.log("编辑商户：",record)
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'merchantCode','merchantName','developPeople','department','isNew'))
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
        this.form.setFieldsValue(pick(row,'merchantCode','merchantName','developPeople','department','isNew'))
      },

      
    }
  }
</script>