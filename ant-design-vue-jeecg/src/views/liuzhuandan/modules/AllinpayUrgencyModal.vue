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

        <a-form-item label="提交人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-user-by-dep v-decorator="['createBy']" :trigger-change="true"/>
        </a-form-item>
        <a-form-item label="提交时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择提交时间" v-decorator="[ 'createTime', validatorRules.createTime]" :trigger-change="true" :show-time="true" date-format="YYYY-MM-DD HH:mm:ss" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="提交部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-depart v-decorator="['sysOrgCode']" :trigger-change="true"/>
        </a-form-item>
        <a-form-item label="详细内容" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['details']" rows="4" placeholder="请输入详细内容"/>
        </a-form-item>
        <a-form-item label="是否紧急" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="radio" v-decorator="['ifUrgency']" :trigger-change="true" dictCode="urgency" placeholder="请选择是否紧急"/>
        </a-form-item>
        <a-form-item label="是否有附件" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['ifAddition']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="附件名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'additionname', validatorRules.additionname]" placeholder="请输入附件名称"></a-input>
        </a-form-item>
        <a-form-item label="期望完成时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择期望完成时间" v-decorator="[ 'expectations', validatorRules.expectations]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="最迟完成时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择最迟完成时间" v-decorator="[ 'latest', validatorRules.latest]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="申请部门领导签章" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-upload v-decorator="['applydepartmentleadersign']" :trigger-change="true"></j-upload>
        </a-form-item>
        <a-form-item label="申请部门领导签章时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择申请部门领导签章时间" v-decorator="[ 'applydepartmentleadersigntime', validatorRules.applydepartmentleadersigntime]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="流程状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'actstatus', validatorRules.actstatus]" placeholder="请输入流程状态"></a-input>
        </a-form-item>
        <a-form-item label="表名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'tablename', validatorRules.tablename]" placeholder="请输入表名"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JUpload from '@/components/jeecg/JUpload'
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "AllinpayUrgencyModal",
    components: {
      JDate,
      JUpload,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
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
        sysOrgCode:{},
        details:{},
        ifUrgency:{},
        ifAddition:{},
        additionname:{},
        expectations:{},
        latest:{},
        applydepartmentleadersign:{},
        applydepartmentleadersigntime:{},
        actstatus:{},
        tablename:{},
        },
        url: {
          add: "/biz/allinpayUrgency/add",
          edit: "/biz/allinpayUrgency/edit",
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
          this.form.setFieldsValue(pick(this.model,'createBy','createTime','sysOrgCode','details','ifUrgency','ifAddition','additionname','expectations','latest','applydepartmentleadersign','applydepartmentleadersigntime','actstatus','tablename'))
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
        this.form.setFieldsValue(pick(row,'createBy','createTime','sysOrgCode','details','ifUrgency','ifAddition','additionname','expectations','latest','applydepartmentleadersign','applydepartmentleadersigntime','actstatus','tablename'))
      },

      
    }
  }
</script>