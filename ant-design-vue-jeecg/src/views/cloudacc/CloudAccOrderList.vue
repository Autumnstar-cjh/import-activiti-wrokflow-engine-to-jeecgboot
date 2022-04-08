<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="应用编号">
              <a-input placeholder="请输入应用编号" v-model="queryParam.appId"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="应用名称">
              <a-input placeholder="请输入应用名称" v-model="queryParam.appName"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="12" :sm="16">
              <a-form-item label="起始时间">
                <j-date placeholder="请选择开始日期" class="query-group-cust" v-model="queryParam.reportDate_begin"></j-date>
                <span class="query-group-split-cust"></span>
                <j-date placeholder="请选择结束日期" class="query-group-cust" v-model="queryParam.reportDate_end"></j-date>
              </a-form-item>
            </a-col>
          </template>
          <a-col :md="6" :sm="8" >
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
      <a-button type="primary" icon="download" @click="handleExportXls('通商云订单交易报表')">导出</a-button>
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

    <cloudAccOrder-modal ref="modalForm" @ok="modalFormOk"></cloudAccOrder-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import CloudAccOrderModal from './modules/CloudAccOrderModal'
  import JDate from '@/components/jeecg/JDate.vue'

  export default {
    name: "CloudAccOrderList",
    mixins:[JeecgListMixin],
    components: {
      JDate,
      CloudAccOrderModal
    },
    data () {
      return {
        description: '通商云订单交易报表管理页面',
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
          // {
          //   title:'创建人',
          //   align:"center",
          //   dataIndex: 'createBy'
          // },
          // {
          //   title:'创建日期',
          //   align:"center",
          //   dataIndex: 'createTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'更新人',
          //   align:"center",
          //   dataIndex: 'updateBy'
          // },
          // {
          //   title:'更新日期',
          //   align:"center",
          //   dataIndex: 'updateTime',
          //   customRender:function (text) {
          //     return !text?"":(text.length>10?text.substr(0,10):text)
          //   }
          // },
          // {
          //   title:'所属部门',
          //   align:"center",
          //   dataIndex: 'sysOrgCode'
          // },
          {
            title:'应用编号',
            align:"center",
            dataIndex: 'appId'
          },
          {
            title:'应用名称',
            align:"center",
            dataIndex: 'appName'
          },
          {
            title:'起始时间',
            align:"center",
            dataIndex: 'reportDate',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'总金额',
            align:"center",
            dataIndex: 'amountTotal'
          },
          {
            title:'总笔数',
            align:"center",
            dataIndex: 'orderTotal'
          },
          {
            title:'充值金额',
            align:"center",
            dataIndex: 'popupAmount'
          },
          {
            title:'充值笔数',
            align:"center",
            dataIndex: 'popupNum'
          },
          {
            title:'消费笔数',
            align:"center",
            dataIndex: 'consAmount'
          },
          {
            title:'消费笔数',
            align:"center",
            dataIndex: 'consNum'
          },
          {
            title:'提现金额',
            align:"center",
            dataIndex: 'withdrAmount'
          },
          {
            title:'提现笔数',
            align:"center",
            dataIndex: 'withdrNum'
          },
          {
            title:'代收金额',
            align:"center",
            dataIndex: 'collAmount'
          },
          {
            title:'代收笔数',
            align:"center",
            dataIndex: 'collNum'
          },
          // {
          //   title:'代付金额',
          //   align:"center",
          //   dataIndex: 'paidAmount'
          // },
          // {
          //   title:'代付笔数',
          //   align:"center",
          //   dataIndex: 'paidNum'
          // },
          // {
          //   title:'批量代付金额',
          //   align:"center",
          //   dataIndex: 'batPaidAmount'
          // },
          // {
          //   title:'批量代付笔数',
          //   align:"center",
          //   dataIndex: 'batPaidNum'
          // },
          // {
          //   title:'跨应用转账金额',
          //   align:"center",
          //   dataIndex: 'transferAmount'
          // },
          // {
          //   title:'跨应用转账笔数',
          //   align:"center",
          //   dataIndex: 'transferNum'
          // },
          {
            title:'退款金额',
            align:"center",
            dataIndex: 'refundAmount'
          },
          {
            title:'退款笔数',
            align:"center",
            dataIndex: 'refundNum'
          },
          // {
          //   title:'平台转账金额',
          //   align:"center",
          //   dataIndex: 'plTraAmount'
          // },
          // {
          //   title:'平台转账笔数',
          //   align:"center",
          //   dataIndex: 'plaTraNum'
          // },
          // {
          //   title:'理财金额',
          //   align:"center",
          //   dataIndex: 'fiAmount'
          // },
          // {
          //   title:'理财笔数',
          //   align:"center",
          //   dataIndex: 'fiNum'
          // },
          // {
          //   title:'协议消费金额',
          //   align:"center",
          //   dataIndex: 'agrPayAmount'
          // },
          // {
          //   title:'协议消费笔数',
          //   align:"center",
          //   dataIndex: 'agrPayNum'
          // },
          // {
          //   title:'协议代收金额',
          //   align:"center",
          //   dataIndex: 'agrCollAmount'
          // },
          // {
          //   title:'协议代收笔数',
          //   align:"center",
          //   dataIndex: 'agrCollNum'
          // },
          // {
          //   title:'跨境提现金额',
          //   align:"center",
          //   dataIndex: 'crossAmount'
          // },
          // {
          //   title:'跨境提现笔数',
          //   align:"center",
          //   dataIndex: 'crossNum'
          // },
          // {
          //   title:'跨境提现金额1',
          //   align:"center",
          //   dataIndex: 'croAmountO'
          // },
          // {
          //   title:'跨境提现笔数1',
          //   align:"center",
          //   dataIndex: 'croNumO'
          // },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/cloudacc/cloudAccOrder/list",
          delete: "/cloudacc/cloudAccOrder/delete",
          deleteBatch: "/cloudacc/cloudAccOrder/deleteBatch",
          exportXlsUrl: "/cloudacc/cloudAccOrder/exportXls",
          importExcelUrl: "cloudacc/cloudAccOrder/importExcel",
        },
        dictOptions:{
        },
      }
    },
    computed: {
      importExcelUrl: function(){
        return `${window._CONFIG['domianURL']}/${this.url.importExcelUrl}`;
      }
    },
    methods: {
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>