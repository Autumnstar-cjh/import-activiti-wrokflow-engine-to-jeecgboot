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
            <a-form-item label="流水号" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'sn', validatorRules.sn]" placeholder="请输入流水号"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="金额" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input-number v-decorator="[ 'amount', validatorRules.amount]" placeholder="请输入金额" style="width: 100%"/>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="商品id" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'goodsId', validatorRules.goodsId]" placeholder="请输入商品id"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="商品名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'goodsName', validatorRules.goodsName]" placeholder="请输入商品名称"></a-input>
            </a-form-item>
          </a-col>
          <a-col :span="12">
            <a-form-item label="支付状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
              <a-input v-decorator="[ 'payStatus', validatorRules.payStatus]" placeholder="请输入支付状态"></a-input>
            </a-form-item>
          </a-col>

        </a-row>
      </a-form>

      <!-- 子表单区域 -->
      <a-tabs v-model="activeKey" @change="handleChangeTabs">
        <a-tab-pane tab="支付表" :key="refKeys[0]" :forceRender="true">
          <j-editable-table
            :ref="refKeys[0]"
            :loading="payTable.loading"
            :columns="payTable.columns"
            :dataSource="payTable.dataSource"
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

  export default {
    name: 'OrdersModal',
    mixins: [JEditableTableMixin],
    components: {
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
          sn:{},
          amount:{},
          goodsId:{},
          goodsName:{},
          payStatus:{},
        },
        refKeys: ['pay', ],
        tableKeys:['pay', ],
        activeKey: 'pay',
        // 支付表
        payTable: {
          loading: false,
          dataSource: [],
          columns: [
            {
              title: '流水号',
              key: 'sn',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '支付金额',
              key: 'amount',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '支付时间',
              key: 'hostDate',
              type: FormTypes.date,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '订单号',
              key: 'orderId',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
            {
              title: '支付状态',
              key: 'payStatus',
              type: FormTypes.input,
              width:"200px",
              placeholder: '请输入${title}',
              defaultValue: '',
            },
          ]
        },
        url: {
          add: "/order/orders/add",
          edit: "/order/orders/edit",
          pay: {
            list: '/order/orders/queryPayByMainId'
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
        let fieldval = pick(this.model,'sn','amount','goodsId','goodsName','payStatus')
        this.$nextTick(() => {
          this.form.setFieldsValue(fieldval)
        })
        // 加载子表数据
        if (this.model.id) {
          let params = { id: this.model.id }
          this.requestSubTableData(this.url.pay.list, params, this.payTable)
        }
      },
      /** 整理成formData */
      classifyIntoFormData(allValues) {
        let main = Object.assign(this.model, allValues.formValue)

        return {
          ...main, // 展开
          payList: allValues.tablesValue[0].values,
        }
      },
      validateError(msg){
        this.$message.error(msg)
      },
     popupCallback(row){
       this.form.setFieldsValue(pick(row,'sn','amount','goodsId','goodsName','payStatus'))
     },

    }
  }
</script>

<style scoped>
</style>