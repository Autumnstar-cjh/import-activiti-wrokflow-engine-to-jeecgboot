<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    :destroyOnClose="true"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="父级ID" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-tree-select
            ref="treeSelect"
            placeholder="请选择上级目标"
            v-decorator="['parentId', validatorRules.parentId]"
            dict="okr_objectives,objectives,id"
            pidField="parent_id"
            pidValue="0"
            hasChildField="has_child">
          </j-tree-select>
        </a-form-item>

   
        <a-form-item  label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-depart :mulit="false" v-decorator="['sysDepart']" :trigger-change="true"/>
        </a-form-item>

        <a-form-item label="目标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'objectives', validatorRules.objectives]" placeholder="请输入目标"></a-input>
        </a-form-item>

        <a-form-item label="开时时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开时时间" v-decorator="[ 'startDate', validatorRules.startDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>

        <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束时间" v-decorator="[ 'endDate', validatorRules.endDate]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>

        <a-form-item label="度量单位" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-dict-select-tag type="list" v-decorator="['unit']" :trigger-change="true" dictCode="OKR_UNIT" placeholder="请选择度量单位"/>
        </a-form-item>

        <a-form-item label="完成数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'finishedAmount', validatorRules.finishedAmount]" placeholder="请输入完成数" style="width: 100%"/>
        </a-form-item>

        <a-form-item label="目标数" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'targetAmount', validatorRules.targetAmount]" placeholder="请输入目标数" style="width: 100%"/>
        </a-form-item>


          <a-form-item label="说明" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <a-textarea :rows="4" v-decorator="[ 'comment', validatorRules.comment]" placeholder="请输入说明"></a-textarea>
          </a-form-item>
 
          <a-form-item label="执行人" :labelCol="labelCol" :wrapperCol="wrapperCol">
            <j-select-user-by-dep  :multi="false" v-decorator="['assignUser']" :trigger-change="true"/>
          </a-form-item>
 

        <a-form-item label="评分" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input-number v-decorator="[ 'ranking', validatorRules.ranking]" placeholder="请输入评分" style="width: 100%"/>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JTreeSelect from '@/components/jeecg/JTreeSelect'
  
  export default {
    name: "OkrObjectivesModal",
    components: { 
      JDate,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
      JTreeSelect
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:960,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 12 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 12 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        parentId:{},
        sysDepart:{rules: [{ required: true, message: '请输入部门id!' }]},
        objectives:{},
        unit:{},
        finishedAmount:{},
        targetAmount:{},
        comment:{},
        assignUser:{},
        startDate:{},
        endDate:{},
        ranking:{},
        },
        url: {
          add: "/okr/okrObjectives/add",
          edit: "/okr/okrObjectives/edit",
        },
        expandedRowKeys:[],
        pidField:"parentId"
     
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
          this.form.setFieldsValue(pick(this.model,'parentId','sysDepart','objectives','unit','finishedAmount','targetAmount','comment','percent','assignUser','startDate','endDate','ranking'))
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
            let old_pid = this.model[this.pidField]
            let formData = Object.assign(this.model, values);
            let new_pid = this.model[this.pidField]
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.submitSuccess(formData,old_pid==new_pid)
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
        this.form.setFieldsValue(pick(row,'parentId','sysDepart','objectives','unit','finishedAmount','targetAmount','comment','percent','assignUser','startDate','endDate','ranking'))
      },
      submitSuccess(formData,flag){
        if(!formData.id){
          let treeData = this.$refs.treeSelect.getCurrTreeData()
          this.expandedRowKeys=[]
          this.getExpandKeysByPid(formData[this.pidField],treeData,treeData)
          this.$emit('ok',formData,this.expandedRowKeys.reverse());
        }else{
          this.$emit('ok',formData,flag);
        }
      },
      getExpandKeysByPid(pid,arr,all){
        if(pid && arr && arr.length>0){
          for(let i=0;i<arr.length;i++){
            if(arr[i].key==pid){
              this.expandedRowKeys.push(arr[i].key)
              this.getExpandKeysByPid(arr[i]['parentId'],all,all)
            }else{
              this.getExpandKeysByPid(pid,arr[i].children,all)
            }
          }
        }
      }
      
      
    }
  }
</script>