<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="商户号">
              <a-input placeholder="请输入商户号" v-model="queryParam.merchantNo"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="商户名称">
              <a-input placeholder="请输入商户名称" v-model="queryParam.merchantName"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="注册日期">
              <!-- <a-input placeholder="请输入注册日期" v-model="queryParam.注册日期"></a-input> -->
              <a-range-picker @change="createDateChange"  />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="变更日期">
              <!-- <a-input placeholder="请输入变更日期" v-model="queryParam.变更日期"></a-input> -->
              <a-range-picker @change="changeDateChange" />
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
              <!-- <a @click="handleToggleSearch" style="margin-left: 8px">
                {{ toggleSearchStatus ? '收起' : '展开' }}
                <a-icon :type="toggleSearchStatus ? 'up' : 'down'"/>
              </a> -->
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!-- 查询区域-END -->
    
    <!-- 操作按钮区域 -->
    <div class="table-operator">
      <!-- <a-button @click="handleAdd" type="primary" icon="plus">新增</a-button> -->
      <a-button type="primary" icon="download" @click="handleExportXls('Merchant')">导出</a-button>
      <!-- <a-upload name="file" :showUploadList="false" :multiple="false" :headers="tokenHeader" :action="importExcelUrl" @change="handleImportExcel">
        <a-button type="primary" icon="import">导入</a-button>
      </a-upload>
      <a-dropdown v-if="selectedRowKeys.length > 0">
        <a-menu slot="overlay">
          <a-menu-item key="1" @click="batchDel"><a-icon type="delete"/>删除</a-menu-item>
        </a-menu>
        <a-button style="margin-left: 8px"> 批量操作 <a-icon type="down" /></a-button>
      </a-dropdown> -->
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
          <a @click="handleEdit(record)">查看</a>

          <a-divider type="vertical" />
          <!-- <a-dropdown>
            <a class="ant-dropdown-link">更多 <a-icon type="down" /></a>
            <a-menu slot="overlay">
              <a-menu-item>
                <a-popconfirm title="确定删除吗?" @confirm="() => handleDelete(record.id)">
                  <a>删除</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown> -->
        </span>

      </a-table>
    </div>

    <Merchant-modal ref="modalForm" @ok="modalFormOk"></Merchant-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import MerchantModal from './modules/MerchantModal'

  export default {
    name: "MerchantList",
    mixins:[JeecgListMixin],
    components: {
      MerchantModal
    },
    data () {
      return {
        description: 'Merchant管理页面',
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
            title:'注册日期',
            align:"center",
            dataIndex: '注册日期',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'变更日期',
            align:"center",
            dataIndex: '变更日期',
            customRender:function (text) {
              return !text?"":(text.length>10?text.substr(0,10):text)
            }
          },
          {
            title:'商户号',
            align:"center",
            dataIndex: '商户号'
          },
          {
            title:'商户名称',
            align:"center",
            dataIndex: '商户名称'
          },
          {
            title:'渠道方备注',
            align:"center",
            dataIndex: '渠道方备注'
          },
          {
            title:'拓展人',
            align:"center",
            dataIndex: '拓展人'
          },
          {
            title:'结算账户',
            align:"center",
            dataIndex: '结算账户'
          },
          {
            title:'结算账户名',
            align:"center",
            dataIndex: '结算账户名'
          },
          {
            title:'商户结算开户行名称',
            align:"center",
            dataIndex: '商户结算开户行名称'
          },
          {
            title:'支行联行号',
            align:"center",
            dataIndex: '支行联行号'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' }
          }
        ],
        url: {
          list: "/sybData/Merchant/list",
          delete: "/sybData/Merchant/delete",
          deleteBatch: "/sybData/Merchant/deleteBatch",
          exportXlsUrl: "/sybData/Merchant/exportXls",
          importExcelUrl: "sybData/Merchant/importExcel",
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
      },


      createDateChange(moment,date){
        var dateStr = date[0] + ',' + date[1]
        console.log("选择的日期：",dateStr)
        this.queryParam.createDate = dateStr
      },
      changeDateChange(moment,date){
        var dateStr = date[0] + ',' + date[1]
        console.log("选择的日期：",dateStr)
        this.queryParam.changeDate = dateStr
      },
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>