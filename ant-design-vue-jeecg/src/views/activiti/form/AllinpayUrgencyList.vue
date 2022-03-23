<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">

        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->

    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="startProcess(id)">发起申请</a-button>
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
import AllinpayUrgencyModal from '/Users/autumnstar/Downloads/allinpayboot/ant-design-vue-jeecg/src/views/liuzhuandan/modules/AllinpayUrgencyModal'
import pick from "lodash.pick";
import { postAction,getAction } from '@/api/manage'

export default {
  name: "AllinpayUrgencyList",
  props: ['formData'],
  mixins:[JeecgListMixin],
  components: {
    AllinpayUrgencyModal
  },
  created () {
    console.log("form start");
    console.log("formdata",this.formData);
    var params = {id:this.formData.dataId};//查询条件
    getAction(this.url.queryById,params).then((res)=>{
      if(res.success){
        console.log("获取流程节点信息",res);
        this.edit (res.result);
      }
    })
  },
  data () {

    return {
      description: '1管理页面',
      id:this.formData.dataId,
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
        //{
        //title:'是否紧急',
        //align:"center",
        //dataIndex: 'ifUrgency'
        //},
        //{
        //title:'是否有附件',
        //align:"center",
        //dataIndex: 'ifAddition'
        //},
        //{
        //title:'附件名称',
        //align:"center",
        //dataIndex: 'additionname'
        //},
        //{
        //title:'期望完成时间',
        //align:"center",
        //dataIndex: 'expectations',
        //customRender:function (text) {
        //return !text?"":(text.length>10?text.substr(0,10):text)
        //}
        //},

        {
          title:'最迟完成时间',
          align:"center",
          dataIndex: 'latest',
          customRender:function (text) {
            return !text?"":(text.length>10?text.substr(0,10):text)
          }
        },
        //{
        //title:'申请人签名',
        //align:"center",
        //dataIndex: 'appliersign'
        //},
        {
          title:'申请部门领导签章',
          align:"center",
          dataIndex: 'applydepartmentleadersign',
          scopedSlots: {customRender: 'imgSlot'}
        },
        //{
        //title:'申请部门领导签章时间',
        //align:"center",
        //dataIndex: 'applydepartmentleadersigntime',
        //customRender:function (text) {
        //return !text?"":(text.length>10?text.substr(0,10):text)
        //}
        //},
        //{
        //title:'接收部门',
        //align:"center",
        //dataIndex: 'receivedepartment',
        //customRender:(text)=>{
        //if(!text){
        //return ''
        //}else{
        //return filterMultiDictText(this.dictOptions['receivedepartment'], text+"")
        //}
        //}
        //},
        {
          title:'责任人',
          align:"center",
          dataIndex: 'receiver'
        },
        {
          title:'完成情况',
          align:"center",
          dataIndex: 'completion'
        },
        //{
        //title:'责任人签名',
        //align:"center",
        //dataIndex: 'receiversign'
        // },
        {
          title:'完成时间',
          align:"center",
          dataIndex: 'completiontime',
          customRender:function (text) {
            return !text?"":(text.length>10?text.substr(0,10):text)
          }
        },
        {
          title:'接收部门领导签章',
          align:"center",
          dataIndex: 'receivedepartmentleadersign',
          scopedSlots: {customRender: 'imgSlot'}
        },
        //{
        //title:'接收部门领导签章时间',
        //align:"center",
        //dataIndex: 'receivedepartmentleadersigntime',
        //customRender:function (text) {
        //return !text?"":(text.length>10?text.substr(0,10):text)
        //}
        //},
        {
          title:'流程状态',
          align:"center",
          dataIndex: 'bpmStatus'
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
        updateInsStatus:'/actProcessIns/updateInsStatus/',
      },
      dictOptions:{
        sysOrgCode:[],
        receivedepartment:[],
        flowCode:"dev_ext_biz_leave_001",
      },
    }
  },
  computed: {
    importExcelUrl: function(){
      return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
    }
  },
  methods: {
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
    startProcess: function(id){
      var that = this;
      this.$confirm({
        title:"提示",
        content:"确认提交流程吗?",
        onOk: function(){
          var param = {
            flowCode:that.flowCode,
            id:this.formData.dataId,
            formUrl:"/Users/autumnstar/Downloads/allinpayboot/ant-design-vue-jeecg/src/views/activiti/form/AllinpayUrgencyList",
            formUrlMobile:"/Users/autumnstar/Downloads/allinpayboot/ant-design-vue-jeecg/src/views/activiti/form/AllinpayUrgencyList"
          }

          console.log("id=",id)
          getAction(that.url.updateInsStatus,param).then((res)=>{
            if(res.success){
              that.$message.success(res.message);
              that.loadData();
              that.onClearSelected();
            }else{
              that.$message.warning(res.message);
            }
          });
        }
      });
    },
    initDictConfig(){
      initDictOptions('sys_depart,depart_name,id').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'sysOrgCode', res.result)
        }
      })
      initDictOptions('sys_depart,depart_name,id').then((res) => {
        if (res.success) {
          this.$set(this.dictOptions, 'receivedepartment', res.result)
        }
      })
    }

  }
}
</script>
<style scoped>
@import '~@assets/less/common.less'
</style>