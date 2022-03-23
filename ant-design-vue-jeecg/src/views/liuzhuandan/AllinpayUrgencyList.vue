<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
        <a-form @submit="handleSubmit" :form="form">
          <a-form-item
            label="标题"
            :labelCol="{lg: {span: 7}, sm: {span: 7}}"
            :wrapperCol="{lg: {span: 10}, sm: {span: 17} }">
            <a-input :disabled="disabled"
                     v-decorator="[
            'name',
            {rules: [{ required: true, message: '请输入标题' }]}
          ]"
                     name="name"
                     placeholder="给目标起个名字" />
          </a-form-item>
          <a-form-item v-if="!disabled"
                       :wrapperCol="{ span: 24 }"
                       style="text-align: center"
          >
            <a-button type="primary"  @click="handleSubmit">保存</a-button>
            <a-button style="margin-left: 8px" :disabled="disabled" @click="close">取消</a-button>
          </a-form-item>
          <a-form-item v-if="task"
                       :wrapperCol="{ span: 24 }"
                       style="text-align: center"
          >
            <a-button type="primary"  @click="passTask">通过</a-button>
            <a-button style="margin-left: 8px"  @click="backTask">驳回</a-button>
          </a-form-item>
        </a-form>


      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('1')">导出</a-button>
      <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown>
    </div>

    <!-- table区域-begin -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table
        ref="table"
        size="middle"
        bordered
        rowKey="id"
        :columns="columns"
        :dataSource="dataSource"
        :pagination="ipagination"
        :loading="loading"
        :rowSelection="{fixed:true,selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"

        @change="handleTableChange">

        <template slot="htmlSlot" slot-scope="text">
          <div v-html="text"></div>
        </template>
        <template slot="imgSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此图片</span>
          <img v-else :src="getImgView(text)" height="25px" alt="图片不存在" style="max-width:80px;font-size: 12px;font-style: italic;"/>
        </template>
        <template slot="fileSlot" slot-scope="text">
          <span v-if="!text" style="font-size: 12px;font-style: italic;">无此文件</span>
          <a-button
            v-else
            :ghost="true"
            type="primary"
            icon="download"
            size="small"
            @click="uploadFile(text)">
            下载
          </a-button>
        </template>

        <span slot="action" slot-scope="text, record">
          <a @click="handleEdit(record)">编辑</a>

          <a-divider type="vertical" />
          <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <allinpayUrgency-modal ref="modalForm" @ok="modalFormOk"></allinpayUrgency-modal>
  </a-card>
</template>

<script>

import { JeecgListMixin } from '@/mixins/JeecgListMixin'
import axios from 'axios'
import qs from 'querystring'
import AllinpayUrgencyModal from './modules/AllinpayUrgencyModal'
import {initDictOptions, filterMultiDictText} from '@/components/dict/JDictSelectUtil'
import pick from "lodash.pick"
import { deleteAction, getAction,downFile,postAction } from '@/api/manage';

export default {
  name: "AllinpayUrgencyList",
  mixins:[JeecgListMixin],
  components: {
    AllinpayUrgencyModal
  },
  props:{
    /*全局禁用，可表示查看*/
    disabled:{
      type:Boolean,
      default:false,
      required:false
    },
    /*流程数据*/
    processData:{
      type:Object,
      default:()=>{return {}},
      required:false
    },
    /*是否新增*/
    isNew: {type: Boolean, default: false, required: false},
    /*是否处理流程*/
    task: {type: Boolean, default: false, required: false}
  },
  data () {
    return {
      description: '1管理页面',
      // 表头
      columns: [
        {
          title: '#',
          dataIndex: '',
          key:'rowIndex',
          width:60,
          align:"center",
          customRender:function (t,r,index) {
            return parseInt(index)+1;
          }
        },
        {
          title:'提交人',
          align:"center",
          dataIndex: 'createBy'
        },
        {
          title:'提交时间',
          align:"center",
          dataIndex: 'createTime'
        },
        {
          title:'提交部门',
          align:"center",
          dataIndex: 'sysOrgCode',
          customRender:(text)=>{
            if(!text){
              return ''
            }else{
              return filterMultiDictText(this.dictOptions['sysOrgCode'], text+"")
            }
          }
        },
        {
          title:'详细内容',
          align:"center",
          dataIndex: 'details'
        },
        {
          title:'是否紧急',
          align:"center",
          dataIndex: 'ifUrgency',
          customRender:(text)=>{
            if(!text){
              return ''
            }else{
              return filterMultiDictText(this.dictOptions['ifUrgency'], text+"")
            }
          }
        },
        {
          title:'是否有附件',
          align:"center",
          dataIndex: 'ifAddition',
          scopedSlots: {customRender: 'imgSlot'}
        },
        {
          title:'附件名称',
          align:"center",
          dataIndex: 'additionname'
        },
        {
          title:'期望完成时间',
          align:"center",
          dataIndex: 'expectations',
          customRender:function (text) {
            return !text?"":(text.length>10?text.substr(0,10):text)
          }
        },
        {
          title:'最迟完成时间',
          align:"center",
          dataIndex: 'latest',
          customRender:function (text) {
            return !text?"":(text.length>10?text.substr(0,10):text)
          }
        },
        {
          title:'申请部门领导签章',
          align:"center",
          dataIndex: 'applydepartmentleadersign',
          scopedSlots: {customRender: 'imgSlot'}
        },
        {
          title:'申请部门领导签章时间',
          align:"center",
          dataIndex: 'applydepartmentleadersigntime',
          customRender:function (text) {
            return !text?"":(text.length>10?text.substr(0,10):text)
          }
        },
        {
          title:'流程状态',
          align:"center",
          dataIndex: 'actstatus'
        },
        {
          title:'表名',
          align:"center",
          dataIndex: 'tablename'
        },
        {
          title: '操作',
          dataIndex: 'action',
          align:"center",
          scopedSlots: { customRender: 'action' }
        }
      ],
      url: {
        list: "/biz/allinpayUrgency/list",
        delete: "/biz/allinpayUrgency/delete",
        deleteBatch: "/biz/allinpayUrgency/deleteBatch",
        exportXlsUrl: "/biz/allinpayUrgency/exportXls",
        importExcelUrl: "biz/allinpayUrgency/importExcel",
        getForm:'/actBusiness/getForm',
        addApply:'/actBusiness/add',
        editForm:'/actBusiness/editForm',
      },
      dictOptions:{
        sysOrgCode:[],
        ifUrgency:[],
      },

      // form
      form: this.$form.createForm(this),
      /*表单回显数据*/
      btndisabled: false
    }
  },
  created() {
    console.log("流程数据",this.processData)
    if (!this.isNew){
      this.init();
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
  methods: {
    /*回显数据*/
    init(){
      this.btndisabled = true;
      const r = this.processData;
      getAction(this.url.getForm,{
        tableId:r.tableId,
        tableName:r.tableName,
      }).then((res)=>{

        if (res.success){
          let formData = res.result;
          formData.tableName = r.tableName;
          formData.tableId = r.tableId;
          this.data = formData;
          console.log("表单回显数据",this.data)
          this.$nextTick(() => {
            this.form.setFieldsValue(pick(this.data,'name'))
          });
          this.btndisabled = false;
        }else {
          this.$message.error(res.message)
        }
      })
    },
    // handler
    handleSubmit (e) {
      e.preventDefault()
      this.form.validateFields((err, values) => {
        if (!err) {
          let formData = Object.assign(this.data||{}, values)
          formData.procDefId = this.processData.id;
          formData.procDeTitle = this.processData.name;
          if (!formData.tableName)formData.tableName = this.processData.businessTable;
          formData.filedNames = _.keys(values).join(",");
          console.log('formData', formData)

          var url = this.url.addApply;
          if (!this.isNew){
            url = this.url.editForm;
          }
          this.btndisabled = true;

          postAction(url,formData).then((res)=>{
            if (res.success){
              this.$message.success("保存成功！")
              //todo 将表单的数据传给父组件
              this.$emit('afterSubmit',formData)
            }else {
              this.$message.error(res.message)
            }
          }).finally(()=>{
            this.btndisabled = false;
          })
        }
      })
    },
    close() {
      //todo 关闭后的回调
      this.$emit('close')
    },
    /*通过审批*/
    passTask() {
      this.$emit('passTask')
    },
    /*驳回审批*/
    backTask() {
      this.$emit('backTask')
    },
    initDictConfig(){
      initDictOptions('sys_depart,depart_name,id').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'sysOrgCode', res.result)
        }
      })
      initDictOptions('').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'ifUrgency', res.result)
        }
      })
    }

  }
}
</script>
<style scoped>
@import '~@assets/less/common.less'
</style>