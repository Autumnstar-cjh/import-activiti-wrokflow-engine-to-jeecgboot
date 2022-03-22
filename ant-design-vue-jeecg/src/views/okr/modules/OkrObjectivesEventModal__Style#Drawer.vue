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

        <a-form-item label="createBy" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'createBy', validatorRules.createBy]" placeholder="请输入createBy"></a-input>
        </a-form-item>
        <a-form-item label="createTime" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择createTime" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="目标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'okrObjectives', validatorRules.okrObjectives]" placeholder="请输入目标"></a-input>
        </a-form-item>
        <a-form-item label="执行人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'okrAssignUser', validatorRules.okrAssignUser]" placeholder="请输入执行人"></a-input>
        </a-form-item>
        <a-form-item label="targetAmount" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'targetAmount', validatorRules.targetAmount]" placeholder="请输入targetAmount" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="执行人id" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'okrObjectivesId', validatorRules.okrObjectivesId]" placeholder="请输入执行人id"></a-input>
        </a-form-item>
        <a-form-item label="备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'remark', validatorRules.remark]" placeholder="请输入备注"></a-input>
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
    name: "OkrObjectivesEventModal",
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
        okrObjectives:{},
        okrAssignUser:{},
        targetAmount:{},
        okrObjectivesId:{},
        remark:{},
        },
        url: {
          add: "/okr/okrObjectivesEvent/add",
          edit: "/okr/okrObjectivesEvent/edit",
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','okrObjectives','okrAssignUser','targetAmount','okrObjectivesId','remark'))
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','okrObjectives','okrAssignUser','targetAmount','okrObjectivesId','remark'))
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