<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">
        <a-form-item label="计划名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'title', validatorRules.title]" placeholder="请输入计划名称" readonly></a-input>
        </a-form-item>
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['person']" :trigger-change="true" readonly></a-input>
        </a-form-item>
        <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择开始时间" v-decorator="[ 'startTime', validatorRules.startTime]" :trigger-change="true" style="width: 100%" disabled/>
        </a-form-item>
        <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date placeholder="请选择结束时间" v-decorator="[ 'endTime', validatorRules.endTime]" :trigger-change="true" style="width: 100%" disabled/>
        </a-form-item>
        <a-form-item label="优先级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-button>{{priority}}</a-button>
        </a-form-item>
        <a-form-item label="计划描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['planDescribe']" rows="4" placeholder="请输入计划描述" readonly/>
        </a-form-item>
        <a-form-item label="关联目标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select  v-decorator="['associatedTarget', validatorRules.associatedTarget]" style="width: 500px" @change="handleChange" disabled>
            <a-select-option v-for="objectives in objectiveList" :value="objectives.associatedTarget" :key="objectives.id"><p>{{objectives.associatedTarget}}</p></a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-button>{{status}}</a-button>
        </a-form-item>
        <a-form-item label="完成情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['completedSituation', validatorRules.completedSituation]" :trigger-change="true" rows="4" placeholder="请输入完成情况" readonly/>
        </a-form-item>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"

  export default {
    name: "WorkPlanModal",
    components: { 
      JDate,
      JSelectUserByDep,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        objectiveList:[],
        priority:'',
        status:'',
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
        title:{},
        person:{},
        startTime:{},
        endTime:{},
        planDescribe:{},
        associatedTarget:{},
        completedSituation:{},
        },
        url: {
          add: "/plan/workPlan/add",
          edit: "/plan/workPlan/edit",
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
        console.log("工作计划是："+JSON.stringify(record))
        this.priority = record.priority;
        this.status = record.status;
        this.isDesabled = false;
        this.objectiveList.push({"associatedTarget":record.associatedTarget})
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'title','person','startTime','endTime','priority','planDescribe','associatedTarget','status','completedSituation'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'title','person','startTime','endTime','priority','planDescribe','associatedTarget','status','completedSituation'))
      },
    }
  }
</script>