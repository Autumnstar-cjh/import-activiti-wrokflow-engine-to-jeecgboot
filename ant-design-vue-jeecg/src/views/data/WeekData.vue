<template>
  <div>
  <a-card :bordered="false">
    <!-- Table查询区域 -->
    <div class="table-page-search-wrapper">
      <a-form layout="inline" @keyup.enter.native="searchQuery">
        <a-row :gutter="24">
          
          <a-col :md="4" :sm="8">
            <a-form-item label="商户号">
              <a-input placeholder="请输入商户号" v-model="queryParam.merchantCode"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <a-form-item label="拓展人">
             <a-input placeholder="请输入拓展人姓名" v-model="queryParam.expandPeople"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <a-form-item label="所属机构">
             <a-input placeholder="请输入所属机构" v-model="queryParam.belongsTo"></a-input>
            </a-form-item>
          </a-col>

          <a-col :md="6" :sm="8">
            <span style="float: left;overflow: hidden;" class="table-page-search-submitButtons">
              <a-button type="primary" @click="searchQuery" icon="search">查询</a-button>
              <a-button type="primary" @click="searchReset" icon="reload" style="margin-left: 8px">重置</a-button>
             
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>


    <!--Table数据区域 -->
    <a-table
      :columns="columns"
      :data-source="data"
      :loading="loading"
      bordered style="background-color: rgb(255, 255, 255);"
    >
      <span slot="action" slot-scope="text, record">
        <a @click="getWeekDataDetail(text, record)">交易详情</a>
      </span>
    
    </a-table>
  </a-card>


  <weekData-modal ref="weekDataModal"></weekData-modal>
  </div>
</template>
<script>
import { getAction} from '@/api/manage'
import WeekDataModal from './modules/WeekDataModal'

export default {
  components: {
    WeekDataModal
  },
  data() {
    return {
      data: [],
      pagination: {},
      loading: false,
      columns,
      queryParam: {
        merchantCode: '',
        expandPeople: '',
        belongsTo: ''
      },

      url: {
        getWeekData: "/data/fluctuation/getWeekData",
        getWeekDataDetail: "/data/fluctuation/getWeekDataDetail",
      }
    };
  },
  mounted() {
    this.getWeekData()
  },
  methods: {
   getWeekData(){

    console.log("getWeekData...")
    var that = this 

    that.loading = true

    let params = {
      merchantCode: that.queryParam.merchantCode,
      expandPeople: that.queryParam.expandPeople,
      belongsTo: that.queryParam.belongsTo
    }

    if(params.merchantCode === null || params.merchantCode === undefined){
      params.merchantCode = ''
    }
    if(params.expandPeople === null || params.expandPeople === undefined){
      params.expandPeople = ''
    }
    if(params.belongsTo === null || params.belongsTo === undefined){
      params.belongsTo = ''
    }

    getAction(that.url.getWeekData,params).then(res=>{
      console.log("周环比交易结果:",res)
      if(res.success){
        that.data = res.result
        that.loading = false
      }
    })



   },


   //查询
    searchQuery(){
      var that = this
      that.getWeekData()
    },

    //重置
    searchReset(){
      var that = this

      that.queryParam.merchantCode = ''
      that.queryParam.expandPeople = ''
      that.queryParam.belongsTo = ''

      that.getWeekData()
    },

    //指定商户号查询近两周交易信息
    getWeekDataDetail(text, record){
      //console.log("text=",text,",record=",record)
      var that = this

      let params = {merchantCode: record.merchantCode}
      

      getAction(that.url.getWeekDataDetail,params).then(res=>{
      console.log("交易详情:",res)
      if(res.success){
        // that.data = res.result
        // that.loading = false
        that.$refs.weekDataModal.showModal(res.result)
      }
    })

    },

  },
};


const columns = [
      {
        title: '商户号',
        dataIndex: 'merchantCode',
        width: '10%',
        align: 'center'
      },
      {
        title: '商户名称',
        dataIndex: 'merchantName',
        width: '30%',
        align: 'center'
      },
      {
        title: '拓展人',
        dataIndex: 'expandPeople',
        width: '15%',
        align: 'center'
      },
      {
        title: '所属机构',
        dataIndex: 'belongsTo',
        width: '10%',
        align: 'center'
      },
      {
        title: '交易金额',
        dataIndex: 'amount',
        width: '8%',
        align: 'center',
        sorter: (a, b) => a.amount - b.amount
      },
      {
        title: '环比金额',
        dataIndex: 'weekAmount',
        width: '8%',
        align: 'center',
        sorter: (a, b) => a.weekAmount - b.weekAmount
      },
      {
        title: '环比率',
        dataIndex: 'weekRateStr',
        width: '10%',
        align: 'center',
        sorter: (a, b) => a.weekRate - b.weekRate,
        customCell:(record, rowIndex)=>{		//在此处定义，数值为负显示为红色，数值为正显示为绿色
          // console.log("record:",record,"rowIndex:",rowIndex)
          var num = record.weekRateStr.split('%')[0]
          // console.log("num=",num)
          if(num < 0){
            return {style:{'color': '#ff0000'}}    //环比率<0，字体设置成红色
          }else if(num > 0){
            return {style:{'color': '#00da29'}}    //环比率>0，字体设置成绿色
          }
        }
      },
      {
        title: '交易详情',
        align: 'center',
        width: '9%',
        scopedSlots: { customRender: 'action' },
      },
      
];
</script>