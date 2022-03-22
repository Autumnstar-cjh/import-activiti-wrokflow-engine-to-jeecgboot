<template>
  <a-card :bordered="false">

    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="商户类型">
              <a-input placeholder="请输入类型" v-model="queryParam.type"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <a-form-item label="商户号">
              <a-input placeholder="请输入商户号" v-model="queryParam.merchantCode"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button>
      <a-button type="primary" icon="download" @click="handleExportXls('特色产业优质客户商户类型')">导出</a-button>
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

<!-- table区域 -->
    <div>
      <div class="ant-alert ant-alert-info" style="margin-bottom: 16px;">
        <i class="anticon anticon-info-circle ant-alert-icon"></i> 已选择 <a style="font-weight: 600">{{ selectedRowKeys.length }}</a>项
        <a style="margin-left: 24px" @click="onClearSelected">清空</a>
      </div>

      <a-table 
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      :expandIcon="expandIcon"
      :columns="columns" 
      :data-source="data" 
      @expand="expand" 
      :expandedRowKeys="expandedRowKeys"
      class="components-table-demo-nested">
        <a slot="operation" slot-scope="text, record">
          <a @click="outTableEdit(record)">编辑</a>
          <a slot="operation" >&nbsp;&nbsp;&nbsp;</a>
          <a-popconfirm title="删除商户类型同时会删除该类型下的所有商户，确定删除吗?" @confirm="() => outTableDelete(record.key)">
            <a>删除</a>
          </a-popconfirm>
        </a>
      <a-table
        slot="expandedRowRender"
        :columns="innerColumns"
        :data-source="innerData"
        :pagination="false"
      >
        <!-- <span slot="status" > <a-badge status="success" />Finished </span> -->
        <span slot="operation" slot-scope="text, record"  class="table-operation">
          <a @click="editInnerTable(record)">编辑</a>
          <a>&nbsp;</a>
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => deleteInnerTable(record.key)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
            <a> 更多 <a-icon type="down" /> </a>
          </a-dropdown>
        </span>
      </a-table>
    </a-table>
  </div>

    <qualityMerchantType-modal ref="modalForm" @ok="modalFormOk"></qualityMerchantType-modal>

    <quality-merchant-modal ref="merchantForm" @ok="merchantFormOk"></quality-merchant-modal>
  </a-card>
</template>

<script>
  import { getAction,deleteAction } from '@/api/manage'
  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import QualityMerchantTypeModal from './modules/QualityMerchantTypeModal'
  import QualityMerchantModal from './modules/QualityMerchantModal.vue'


  export default {
    name: "QualityMerchantTypeList",
    mixins:[JeecgListMixin],
    components: {
      QualityMerchantTypeModal,
      QualityMerchantModal
    },
    data () {
      return {
        // QualityMerchantModal,
        description: '特色产业优质客户商户类型管理页面',
        expandedRowKeys: [],
        //外表表头
        columns:[
          {title: '商户类型', dataIndex: 'name', key: 'name', align: 'left',width: '85%'},
          {title: '操作', key: 'operation', align: 'center', width: '15%', scopedSlots: { customRender: 'operation' } },
        ],
        //外表数据
        data:[],
        //内表表头
        innerColumns:[
          { title: '商户号', dataIndex: 'merchantCode', key: 'merchantCode' },
          { title: '商户名称', dataIndex: 'merchantName', key: 'merchantName' },
          { title: '拓展人', dataIndex: 'developPeople', key: 'developPeople' },
          { title: '部门', dataIndex: 'department', key: 'department' },
          { title: '操作', dataIndex: 'operation', key: 'operation', align: 'center', scopedSlots: { customRender: 'operation' }},
        ],
        //内表数据
        innerData:[],

        //查询数据
        queryParam: {
          type: '',
          merchantCode: ''
        },

        url: {
          list: "/quality/qualityMerchantType/list",
          delete: "/quality/qualityMerchantType/delete",
          deleteBatch: "/quality/qualityMerchantType/deleteBatch",
          exportXlsUrl: "/quality/qualityMerchantType/exportXls",
          importExcelUrl: "quality/qualityMerchantType/importExcel",
          queryPage: "/quality/qualityMerchantType/pagelist",
          deleteMerchant: "/quality/qualityMerchant/delete",
          pagelistByParam: "/quality/qualityMerchantType/pagelistByParam"
        },
        dictOptions:{
        },
        rowKey: '',
      }
    },
    created(){
      this.doQuery()
    },
    computed: {
    },
    methods: {
      //加载数据
      doQuery(){
        getAction(this.url.queryPage).then((res)=>{
              // console.log('列表查询返回',res)
              if(res.success){
                this.data = res.result
              }else{
                this.$message.error(res.message);
              }
            }).finally(() => {
              // that.confirmLoading = false;
              // that.close();
            })
      },

      //查询
      searchQuery(){
        var type = this.queryParam.type
        var code = this.queryParam.merchantCode
        console.log("type=",type,",merchantCode=",code)

        let param = {}
        //涉及到主表和字表，所以得做一下判断来封装查询数据
        if((type == null || type == '') && (code != null && code != '')){
          param = {
            type: '',
            merchantCode: code
          }
        }
        if((type != null && type != '') && (code == null || code == '')){
          param = {
            type: type,
            merchantCode: ''
          }
        }
        if((type != null && type != '') && (code != null && code != '')){
          param = {
            type: type,
            merchantCode: code
          }
        }
        getAction(this.url.pagelistByParam,param).then((res)=>{
              // console.log('列表查询返回',res)
              if(res.success){
                this.data = res.result
                this.expandedRowKeys = [] // 重置展开节点
              }else{
                this.$message.error(res.message);
              }
            }).finally(() => {
              // that.confirmLoading = false;
              // that.close();
            })
      },
      //重置
      searchReset(){
        this.doQuery()
        this.queryParam = {
          type: '',
          merchantCode: ''
        }
        this.expandedRowKeys = [] // 重置展开节点
      },

      //展开外表节点操作
      expand(expanded,record){
        this.rowKey = record.key
        console.log("expanded=",expanded)
        console.log("record=",record)
        this.expandedRowKeys = [] // 重置展开节点，只展开当前点击的节点（内部数据调用模板，无法做到同时几个内层表格数据直接缓存在页面）
        if (expanded) {
          this.expandedRowKeys = [record.key]
          this.innerData = record.qualityMerchantList //内表数据
        }
        
        
      },

      //编辑外表
      outTableEdit(record){
        this.handleEdit(record)
      },
      //编辑外表确定后查询数据刷新页面
      modalFormOk(){
        this.doQuery()
        this.expandedRowKeys = [] // 重置展开节点
      },

      //删除外表
      outTableDelete(key){
        var that = this;
        deleteAction(that.url.delete, {id: key}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            this.doQuery() //删除后执行查询刷新页面
          } else {
            that.$message.warning(res.message);
          }
        });
      },

      //批量删除外表
      batchDel(){
        if (this.selectedRowKeys.length <= 0) {
          this.$message.warning('请选择一条记录！');
          return;
        } else {
          var ids = "";
          for (var a = 0; a < this.selectedRowKeys.length; a++) {
            ids += this.selectedRowKeys[a] + ",";
          }
          var that = this;
          this.$confirm({
            title: "确认删除",
            content: "是否删除选中数据?",
            onOk: function () {
              deleteAction(that.url.deleteBatch, {ids: ids}).then((res) => {
                if (res.success) {
                  that.$message.success(res.message);
                  that.doQuery();
                  that.selectedRowKeys = [];
                  that.selectionRows = [];
                } else {
                  that.$message.warning(res.message);
                }
              });
            }
          });
        }
      },

      //编辑内表
      editInnerTable(record){
        console.log("编辑商户(内表):",record)
        let object = {
          id: record.key,
          merchantCode: record.merchantCode,
          merchantName: record.merchantCode,
          developPeople: record.developPeople,
          department: record.department,
          isNew: record.isNew
        }
        this.$refs.merchantForm.edit(object);
        this.$refs.merchantForm.title = "编辑";
        this.$refs.merchantForm.disableSubmit = false;
      },
      //编辑内表后刷新页面
      merchantFormOk(){
        console.log("编辑完成执行刷新页面")
        this.doQuery() //重新查询数据
        this.expandedRowKeys = [] // 重置展开节点
      },
      //删除内表
      deleteInnerTable(key){
        console.log("删除商户:",key)
        var that = this;
        deleteAction(that.url.deleteMerchant, {id: key}).then((res) => {
          if (res.success) {
            that.$message.success(res.message);
            this.doQuery() //删除后执行查询刷新页面
            this.expandedRowKeys = [] // 重置展开节点
          } else {
            that.$message.warning(res.message);
          }
        });
      },

      //自定义展开和收起的图标
      expandIcon(props) {
        // console.log(props);
        if (props.record.qualityMerchantList.length > 0) {
          if (props.expanded) {//有数据-展开时候图标
            return (
              <a
                style="color: 'black',margin-right:0px"
                onClick={(e) => {
                  props.onExpand(props.record, e);
                }}
              >
                <a-icon type="minus-square" />{" "}
              </a>
            );
          } else {//有数据-未展开时候图标
            return (
              <a
                style="color: 'black' ,margin-right:0px"
                onClick={(e) => {
                  props.onExpand(props.record, e);
                }}
              >
                <a-icon type="plus-square" />
              </a>
            );
          }
        }else {//无数据-图标
          return (
            <a style="color: 'black' ,margin-right:0px" >
              <a-icon type="pushpin" />
            </a>
          );
        }
      },

      //通过Excel表格导入数据
      importExcelUrl: function(){
        let result = `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
        this.doQuery();
        return result;
        
      },
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>