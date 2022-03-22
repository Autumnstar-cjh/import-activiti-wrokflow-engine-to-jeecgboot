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
            <a-form-item label="父级id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-dict-select-tag type="list" v-decorator="['parentId']" :trigger-change="true" placeholder="请选择父级目标id" disabled/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="部门" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-depart v-decorator="['sysDepart',validatorRules.sysDepart]" :trigger-change="true"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="目标" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'objectives', validatorRules.objectives]" placeholder="请输入目标"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择开始时间" v-decorator="[ 'startTime', validatorRules.startTime]" :trigger-change="true" style="width: 100%" @change="startTimeChange"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="进度" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'percent', validatorRules.percent]" style="width: 100%" disabled/>
            </a-form-item>
          </a-col>
           <a-col :span="12">
            <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-date placeholder="请选择结束时间" v-decorator="[ 'endTime', validatorRules.endTime]" :trigger-change="true" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <j-select-user-by-dep v-decorator="['chargePerson',validatorRules.chargePerson]" :trigger-change="true"/>
            </a-form-item>
          </a-col>
          <a-col :span="24">
            <a-form-item label="目标说明" :labelCol="labelCol2" :wrapperCol="wrapperCol2">
              <a-textarea v-decorator="['comment']" rows="4" placeholder="请输入目标说明"/>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="关键结果" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="keyResultsTable.loading"
            :columns="keyResultsTable.columns"
            :dataSource="keyResultsTable.dataSource"
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
  import JSelectDepart from '@/components/jeecgbiz/JSelectDepart'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import {initDictOptions, filterDictText} from '@/components/dict/JDictSelectUtil'

  export default {
    name: 'ObjectivesModal',
    mixins: [JEditableTableMixin],
    components: {
      JDate,
      JSelectDepart,
      JSelectUserByDep,
      JDictSelectTag,
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
          parentId:{},
          sysDepart: { rules: [{ required: true, message: '请选择部门!' }] },
          objectives: { rules: [{ required: true, message: '请输入目标!' }] },
          startTime: { rules: [{ required: true, message: '请输入开始时间!' }] },
          endTime: { rules: [{ required: true, message: '请输入结束时间!' }] },
          percent:{},
          chargePerson: { rules: [{ required: true, message: '请选择负责人!' }] },
          comment:{},
        },
        refKeys: ['keyResults', ],
        tableKeys:['keyResults', ],
        activeKey: 'keyResults',
        // 关键结果
        keyResultsTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '指标内容',
              key: 'content',
              type: FormTypes.input,
              width:"260px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '目标数',
              key: 'targetAmount',
              type: FormTypes.input,
              width:"140px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '完成数',
              key: 'finishedAmount',
              type: FormTypes.input,
              width:"140px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '度量单位',
              key: 'unit',
              type: FormTypes.select,
              // dictCode:"OKR_UNIT",
              options: [ // 下拉选项
                
              ],
              width:"100px",
              placeholder: '请选择',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },
            {
              title: '信心指数',
              key: 'confidence',
              type: FormTypes.input,
              width:"160px",
              placeholder: '请输入${title}',
              defaultValue: '',
              validateRules: [{ required: true, message: '${title}不能为空' }],
            },

            {
              title: '执行人',
              key: 'assignUser',
              type: FormTypes.input,
              width:"140px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            // {
            //   title: '评分',
            //   key: 'ranking',
            //   type: FormTypes.input,
            //   width:"200px",
            //   placeholder: '请输入${title}',
            //   defaultValue: '',
            // },
            {
              title: '备注',
              key: 'remark',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/okr/objectives/add",
          edit: "/okr/objectives/edit",
          keyResults: {
            list: '/okr/objectives/queryKeyResultsByMainId'
          },
        }
      }
    },
    created(){
      this.initDictConfig();
    },
    methods: {
      startTimeChange(value,dateString){
        console.log("value="+value+",dateString="+dateString)
      },
      getAllTable() {
        let values = this.tableKeys.map(key => getRefPromise(this, key))
        return Promise.all(values)
      },
      /** 调用完edit()方法之后会自动调用此方法 */
      editAfter() {
        let fieldval = pick(this.model,'parentId','sysDepart','objectives','startTime','endTime','percent','chargePerson','comment')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.keyResults.list, params, this.keyResultsTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          keyResultsList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'parentId','sysDepart','objectives','startTime','endTime','percent','chargePerson','comment'))
     },

    initDictConfig() {
        //初始化字典 - 度量单位
        initDictOptions('OKR_UNIT').then((res) => {
          if (res.success) {
            this.keyResultsTable.columns[3].options = res.result
          }
        });
      },
    }
  }
</script>

<style scoped>
</style>