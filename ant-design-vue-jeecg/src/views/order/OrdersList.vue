<template>
  <a-card :bordered="false">
    <!-- 查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          <a-col :md="6" :sm="8">
            <a-form-item label="流水号">
              <a-input placeholder="请输入流水号" v-model="queryParam.sn"></a-input>
            </a-form-item>
          </a-col>
          <a-col :md="6" :sm="8">
            <a-form-item label="金额">
              <a-input placeholder="请输入金额" v-model="queryParam.amount"></a-input>
            </a-form-item>
          </a-col>
          <template v-if="toggleSearchStatus">
            <a-col :md="6" :sm="8">
              <a-form-item label="商品id">
                <a-input placeholder="请输入商品id" v-model="queryParam.goodsId"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="商品名称">
                <a-input placeholder="请输入商品名称" v-model="queryParam.goodsName"></a-input>
              </a-form-item>
            </a-col>
            <a-col :md="6" :sm="8">
              <a-form-item label="支付状态">
                <a-input placeholder="请输入支付状态" v-model="queryParam.payStatus"></a-input>
              </a-form-item>
            </a-col>
          </template>
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
      <a-button type="primary" icon="download" @click="handleExportXls('订单表')">导出</a-button>
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
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
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

              <a-menu-item>
                <a-popconfirm title="确定撤销该笔交易吗?" @confirm="() => handleRepeal(record.id)">
                  <a>撤销</a>
                </a-popconfirm>
              </a-menu-item>

              <a-menu-item>
                <a-popconfirm title="确定退款吗?" @confirm="() => handleRefund(record.id)">
                  <a>退款</a>
                </a-popconfirm>
              </a-menu-item>
            </a-menu>
          </a-dropdown>
        </span>

      </a-table>
    </div>

    <orders-modal ref="modalForm" @ok="modalFormOk"></orders-modal>

    <pay-modal ref="payForm" @ok="payFormOk"></pay-modal>
  </a-card>
</template>

<script>

  import { JeecgListMixin } from '@/mixins/JeecgListMixin'
  import OrdersModal from './modules/OrdersModal'
  import PayModal from './modules/PayModal'
  import { getAction } from '@/api/manage'

  export default {
    name: "OrdersList",
    mixins:[JeecgListMixin],
    components: {
      OrdersModal,
      PayModal
    },
    data () {
      return {
        description: '订单表管理页面',
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
            title:'流水号',
            align:"center",
            dataIndex: 'sn'
          },
          {
            title:'金额',
            align:"center",
            dataIndex: 'amount'
          },
          {
            title:'商品id',
            align:"center",
            dataIndex: 'goodsId'
          },
          {
            title:'商品名称',
            align:"center",
            dataIndex: 'goodsName'
          },
          {
            title:'支付状态',
            align:"center",
            dataIndex: 'payStatus'
          },
          {
            title: '操作',
            dataIndex: 'action',
            align:"center",
            scopedSlots: { customRender: 'action' },
          }
        ],
        url: {
          list: "/order/orders/list",
          delete: "/order/orders/delete",
          deleteBatch: "/order/orders/deleteBatch",
          exportXlsUrl: "/order/orders/exportXls",
          importExcelUrl: "order/orders/importExcel",
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
      payFormOk(){
        console.log("payFormOk！！！")
      },
      /**
       * 交易撤销
       */
      handleRepeal(id){
        var that = this
        console.log("交易退款，di=",id)
        getAction('/order/pay/cancel', {id:id}).then((res)=>{
            console.log("交易退款返回结果：",res)
            if(res.success){
              if(res.result.trxstatus === '0000'){
                that.$message.success("撤销完成");
                that.loadData()//这个方法是刷新列表，在jeecgListMixin.vue中。引入jeecgListMixin.vue组件后可直接调用
              }
              
            }else{
              that.$message.error(res.message,3);
            }
          })
      },

      /**
       * 交易退款
       */
      handleRefund(id){
        var that = this
        console.log("交易退款，di=",id)
        getAction('/order/pay/refund', {id:id}).then((res)=>{
            console.log("交易退款返回结果：",res)
            if(res.success){
              if(res.result.trxstatus === '0000'){
                that.$message.success("退款完成");
                that.loadData()//这个方法是刷新列表，在jeecgListMixin.vue中。引入jeecgListMixin.vue组件后可直接调用
              }
              
            }else{
              that.$message.error(res.message,3);
            }
          })
      },
      initDictConfig(){
      }
       
    }
  }
</script>
<style scoped>
  @import '~@assets/less/common.less'
</style>