<template>
  <a-modal
    :title="title"
    :width="1200"
    :visible="visible"
    :maskClosable="false"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel">
    <a-spin :spinning="confirmLoading">
      <!-- 主表单区域 -->
      <a-form :form="form">
        <a-row>

          <a-col :span="12">
            <a-form-item label="项目名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'projectName', validatorRules.projectName]" placeholder="请输入项目名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="立项时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择立项时间" v-decorator="[ 'startTime', validatorRules.startTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="项目主办部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'hostDepartment', validatorRules.hostDepartment]" placeholder="请输入项目主办部门"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="项目编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'projectCode', validatorRules.projectCode]" placeholder="请输入项目编号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="预算总额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'budget', validatorRules.budget]" placeholder="请输入预算总额"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="预计上线时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择预计上线时间" v-decorator="[ 'expectOnlineTime', validatorRules.expectOnlineTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="项目负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'projectLeader', validatorRules.projectLeader]" placeholder="请输入项目负责人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="项目经办人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'projectTransactor', validatorRules.projectTransactor]" placeholder="请输入项目经办人"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="项目方案简述" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['projectPlanDescription']" rows="4" placeholder="请输入项目方案简述"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="市场战略分析" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['marketStrategyAnalysis']" rows="4" placeholder="请输入市场战略分析"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="业务实现模式" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['businessRealizationPattern']" rows="4" placeholder="请输入业务实现模式"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="业务流程详述" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['businessProcessDescribe']" rows="4" placeholder="请输入业务流程详述"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="风控合规分析" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['riskControlComplianceAnalysis']" rows="4" placeholder="请输入风控合规分析"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="投资效益分析" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['investmentBenefitAnalysis']" rows="4" placeholder="请输入投资效益分析"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="结论及建议" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['conclusionsRecommendations']" rows="4" placeholder="请输入结论及建议"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="商户信息" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="merchantTable.loading"
            :columns="merchantTable.columns"
            :dataSource="merchantTable.dataSource"
            :maxHeight="300"
            :rowNumber="true"
            :rowSelection="true"
            :actionButton="true"/>
        </a-tab-pane>
        
      </a-tabs>

    </a-spin>
  </a-modal>
</template>

<script>

  import pick from 'lodash.pick'
  import { FormTypes,getRefPromise } from '@/utils/JEditableTableUtil'
  import { JEditableTableMixin } from '@/mixins/JEditableTableMixin'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: 'ProjectInfoModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
    },
    data() {
      return {
        labelCol: {
          span: 6
        },
        wrapperCol: {
          span: 16
        },
        labelCol2: {
          span: 3
        },
        wrapperCol2: {
          span: 20
        },
        // 新增时子表默认添加几行空数据
        addDefaultRowNum: 1,
        validatorRules: {
          projectName: { rules: [{ required: true, message: '请输入项目名称!' }] },
          startTime: { rules: [{ required: true, message: '请输入立项时间!' }] },
          hostDepartment: { rules: [{ required: true, message: '请输入项目主办部门!' }] },
          projectCode: { rules: [{ required: true, message: '请输入项目编号!' }] },
          budget: { rules: [{ required: true, message: '请输入预算总额!' }] },
          expectOnlineTime: { rules: [{ required: true, message: '请输入预计上线时间!' }] },
          projectLeader: { rules: [{ required: true, message: '请输入项目负责人!' }] },
          projectTransactor: { rules: [{ required: true, message: '请输入项目经办人!' }] },
          projectPlanDescription: { rules: [{ required: true, message: '请输入项目方案简述!' }] },
          marketStrategyAnalysis: { rules: [{ required: true, message: '请输入市场战略分析!' }] },
          businessRealizationPattern: { rules: [{ required: true, message: '请输入业务实现模式!' }] },
          businessProcessDescribe: { rules: [{ required: true, message: '请输入业务流程详述!' }] },
          riskControlComplianceAnalysis: { rules: [{ required: true, message: '请输入风控合规分析!' }] },
          investmentBenefitAnalysis: { rules: [{ required: true, message: '请输入投资效益分析!' }] },
          conclusionsRecommendations:{},
        },
        refKeys: ['merchant', ],
        tableKeys:['merchant', ],
        activeKey: 'merchant',
        // 商户信息
        merchantTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '商户编号',
              key: 'code',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '商户姓名',
              key: 'name',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/project/projectInfo/add",
          edit: "/project/projectInfo/edit",
          merchant: {
            list: '/project/projectInfo/queryMerchantByMainId'
          },
        }
      }
    },
    methods: {
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'projectName','startTime','hostDepartment','projectCode','budget','expectOnlineTime','projectLeader','projectTransactor','projectPlanDescription','marketStrategyAnalysis','businessRealizationPattern','businessProcessDescribe','riskControlComplianceAnalysis','investmentBenefitAnalysis','conclusionsRecommendations')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.merchant.list, params, this.merchantTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          merchantList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'projectName','startTime','hostDepartment','projectCode','budget','expectOnlineTime','projectLeader','projectTransactor','projectPlanDescription','marketStrategyAnalysis','businessRealizationPattern','businessProcessDescribe','riskControlComplianceAnalysis','investmentBenefitAnalysis','conclusionsRecommendations'))
     },

    }
  }
</script>

<style scoped>
</style>