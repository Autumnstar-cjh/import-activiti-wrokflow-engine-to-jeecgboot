<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" >
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="计划名称">
              <a-input placeholder="请输入计划名称" v-model="queryParam.title"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="负责人">
              <!--<a-input placeholder="请输入负责人" v-model="queryParam.person"></a-input>-->
              <j-select-user-by-dep v-model="queryParam.person"/>
            </a-form-item>
          </a-col>
           <template v-if="toggleSearchStatus">
            <a-col :md="12" :sm="16">
              <a-form-item label="时间周期">
                <a-range-picker @change="dateChange" :style="{width: '256px'}"></a-range-picker>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8" >
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQueryData" icon="search">查询</a-button>
              <a-button type="primary" @click="searchResetData" icon="reload" style="margin-left: 8px">重置</a-button>
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
      <a-button type="primary" icon="download" @click="handleExportXls('工作计划')">导出</a-button>
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
        :pagination="false"
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
          <a @click="editPlan(record)">查看</a><br>
        </span>

      </a-table>
      <div style="margin-top:2%;margin-right:1%">
        <a-pagination style="float:right;" 
          :defaultCurrent="defaultCurrentPage" 
          :defaultPageSize="defaultPageSize" 
          :total="totalRecord" 
          :current="current"
          :pageSize="pageSize"
          showSizeChanger
          showQuickJumper
          @showSizeChange="onShowSizeChange"
          @change="plansPageChange"/>
      </div>
    </div>

    <workPlan-modal ref="modalForm" @ok="modalFormOk"></workPlan-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import WorkPlanModal from './modules/WorkPlanModal'
  import { getAction} from '@/api/manage'
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'

  export default {
    name: "WorkPlanList",
    mixins:[JeecgListMixin],
    components: {
      WorkPlanModal,
      JSelectUserByDep,
    },
    data () {
      return {
        queryParam:{
              title:'',
              person:'',
              startTime:'',
              endTime:''
        },
        dataSource:[],
        current:1,
        defaultCurrentPage:1,
        totalRecord:0,
        defaultPageSize:10,
        pageSize:10,
        description: '工作计划管理页面',
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
            title:'计划名称',
            align:"left",
            dataIndex: 'title'
          },
          {
            title:'负责人',
            align:"center",
            dataIndex: 'person'
          },
          {
            title:'开始时间',
            align:"center",
            dataIndex: 'startTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'结束时间',
            align:"center",
            dataIndex: 'endTime',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'计划描述',
            align:"left",
            dataIndex: 'planDescribe'
          },
          {
            title:'完成情况',
            align:"left",
            dataIndex: 'completedSituation',
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          },
        ],
        url: {
          list: "/plan/workPlanV2/allPlanList",
          delete: "/plan/workPlanV2/delete",
          deleteBatch: "/plan/workPlanV2/deleteBatch",
          exportXlsUrl: "/plan/workPlanV2/exportXls",
          importExcelUrl: "plan/workPlanV2/importExcel",
        },
        dictOptions:{
        //  associatedTarget:[],
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    mounted() {
      this.searchQueryData()
    },
    methods: {
      editPlan(record){
        var that = this;
        console.log("recorde是："+JSON.stringify(record))
        that.$refs.modalForm.edit(record);
        that.$refs.modalForm.title = "查看工作计划";
        that.$refs.modalForm.disableSubmit = false;//禁用提交
        that.$forceUpdate()
      },
      searchQueryData:function(){
        // console.log("参数是："+JSON.stringify(this.queryParam))
        console.log("this.current="+this.current+",this.defaultPageSize="+this.defaultPageSize)
        var that = this;

        if(that.queryParam.title != '' || that.queryParam.person != '' || that.queryParam.startTime != '' || that.queryParam.endTime != ''){
            that.current = 1;
            that.defaultPageSize = 10;
        }
       
        let params = {
          pageNo:that.current, 
          pageSize:that.defaultPageSize,
          title:that.queryParam.title,
          person:that.queryParam.person,
          startTime:that.queryParam.startTime,
          endTime:that.queryParam.endTime
        }
        getAction(that.url.list, params).then((res)=>{
          if(res.success){
            // console.log("表分页信息是："+JSON.stringify(res.result))
            that.dataSource = res.result.records
            //分页参数
            that.totalRecord = res.result.total
            that.defaultCurrentPage = res.result.current
            that.defaultPageSize = res.result.size

          }
        });
      },
      dateChange:function(date,dateString){
        // console.log("date是："+date+"  ,dateString是："+dateString)
        this.queryParam.startTime = dateString[0]
        this.queryParam.endTime = dateString[1]
      },
      
      //重置按钮事件
      searchResetData:function(){
        var that = this;
        that.current = 1
        that.defaultPageSize = 10
        that.pageSize = 10
        that.queryParam = {
          title:'',
          person:'',
          startTime:'',
          endTime:''
        }
        that.searchQueryData()
      },

      //页码改变触发事件
      plansPageChange(current,pageSize){
        // console.log("当前页="+current+",显示条数="+pageSize)
        this.current = current
        this.defaultPageSize = pageSize
        this.searchQueryData()
      },

      //页面显示条数改变触发事件
      onShowSizeChange(current, pageSize){
        this.current = 1
        this.defaultPageSize = pageSize
        this.pageSize = pageSize
        this.searchQueryData()
      },
      initDictConfig(){
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>