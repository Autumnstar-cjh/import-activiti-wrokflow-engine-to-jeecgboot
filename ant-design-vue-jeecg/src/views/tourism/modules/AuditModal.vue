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
      <p class="tipsWords">您将对选中的{{selectedNum}}条数据进行审核</p>
      <a-form :form="form">
        <a-form-item label="审核意见" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['auditStatus',validatorRules.auditStatus]" :trigger-change="true" dictCode="audit_status" placeholder="请选择审核意见"/>
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
    name: "AuditModal",
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
        selectedNum:0,
        merchentIds:[],
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
            auditStatus:{rules: [{ required: true, message: '请选择审核意见!' }]},
            comment:{},
        },
        url: {
          edit: "/tourism/tourismMerchants/batchEdit",
        }
     
      }
    },
    created () {
    },
    methods: {
      auditMerchants (selectedRowKeys) {
        this.selectedNum = selectedRowKeys.length
        this.merchentIds = selectedRowKeys
        console.log("选中的ids",this.merchentIds)
        let record = {};
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'auditStatus','comment'))
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
            var ids = "";
            let formData = Object.assign(this.model, values);
            if(this.merchentIds <= 0){
              this.$message.warning('请选择一条记录！');
              return;
            }else{
              //将商户id封装成字符串
              for (var a = 0; a < this.merchentIds.length; a++) {
                // console.log("id=",this.merchentIds[a])
                ids += this.merchentIds[a] + ",";
              }
            }
            console.log("ids=",ids)
            //封装传到后台的数据
            let reqData = {
              ids:ids,
              auditStatus:formData.auditStatus,
              comment:formData.comment
            }
            console.log("表单提交数据，批量审核参数为：",reqData)
            httpAction(this.url.edit,reqData,'put').then((res)=>{
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
        this.form.setFieldsValue(pick(row,'auditStatus','comment'))
      },

      
    }
  }
</script>

<style >
.tipsWords{
  width: 100%;
  text-align: center;
  color: red;
}

</style>