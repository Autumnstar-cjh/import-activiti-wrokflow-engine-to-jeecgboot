<template>
  <div id="DataFluctuation">
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
            <a-form-item label="月份">
              <a-month-picker placeholder="请选择月份" v-model="queryParam.mom" @change="onChange"></a-month-picker>
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
    <a-table :columns="columns" 
      :loading="loading"
      @expand="expand" 
      :expandedRowKeys="expandedRowKeys"
      :data-source="data" class="components-table-demo-nested"
      bordered style="background-color: rgb(255, 255, 255);">
      <a-table
        slot="expandedRowRender"
        :columns="columns"
        :data-source="innerData"
        :pagination="false"
        :showHeader="false" :loading="innerLoading"
        style="background-color: rgb(255, 255, 255);"
      >
        <span slot="status"> <a-badge status="success" />Finished </span>
        <span slot="operation" class="table-operation">
          <a>Pause</a>
          <a>Stop</a>
          <a-dropdown>
            <a-menu slot="overlay">
              <a-menu-item>
                Action 1
              </a-menu-item>
              <a-menu-item>
                Action 2
              </a-menu-item>
            </a-menu>
            <a> More <a-icon type="down" /> </a>
          </a-dropdown>
        </span>
      </a-table>
    </a-table>

    </a-card>
  </div>
</template>
<script>

import { getAction} from '@/api/manage'

export default {
  data() {
    const columns = [
      {
        title: '月份',
        dataIndex: 'transDate',
        //表头宽度设置，使得内外表对齐
        width: '10%',
        //设置内容对齐方式
        align: 'center'
      },
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
        title: '所属分公司',
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
        dataIndex: 'momAmount',
        width: '8%',
        align: 'center',
        sorter: (a, b) => a.momAmount - b.momAmount
      },
      {
        title: '环比率',
        dataIndex: 'momRateStr',
        width: '10%',
        align: 'center',
        sorter: (a, b) => a.momRate - b.momRate,
        customCell:(record, rowIndex)=>{		//在此处定义，数值为负显示为红色，数值为正显示为绿色
          // console.log("record:",record,"rowIndex:",rowIndex)
          var num = record.momRateStr.split('%')[0]
          // console.log("num=",num)
          if(num < 0){
            return {style:{'color': '#ff0000'}}    //环比率<0，字体设置成红色
          }else if(num > 0){
            return {style:{'color': '#00da29'}}    //环比率>0，字体设置成绿色
          }
        }
      },
    ];


    return {
      data:[],
      columns,
      innerData:[],
      loading: false,
      innerLoading: false,
      expandedRowKeys: [],
      queryParam: {
        merchantCode:'',
        mom: ''
      },
      mom: '',

      url: {
          getMomData: "/data/fluctuation/getMomData",
          getLastMomData: "/data/fluctuation/getLastMomData",
        },
    };
  },

  created() {
    var that  = this
    that.getLastMomData()
  },

  methods:{
    //查询最后一个月环比交易
    getLastMomData(){
      var that = this

      that.loading = true

      let params = {
        merchantCode: that.queryParam.merchantCode,
        mom: that.mom
      }
      // console.log("merchantCode=",params.merchantCode,",mom=",params.mom)
      if(params.merchantCode === null || params.merchantCode === undefined){
        params.merchantCode = ''
      }
      if(params.mom === null || params.mom === undefined){
        params.mom = ''
      }

      getAction(that.url.getLastMomData,params).then(res=>{
        console.log("环比交易结果:",res)
        if(res.success){
          that.data = res.result
          that.loading = false
        }
      })
    },

    // 根据商户号查询环比交易，除去最近一个月的数据
    getMomData(code,date){
      var that = this

      that.innerLoading = true
      let params = {
        merchantCode: code,
        date: date  
      }
      // console.log("params：",params)

      getAction(that.url.getMomData,params).then(res=>{
        console.log("res=",res)
        that.innerData = res.result
        that.innerLoading = false
        
      })
    },

    //展开外表节点操作
      expand(expanded,record){
        var that = this

        console.log("expanded=",expanded)
        console.log("record=",record)
        var code = record.merchantCode
        var date = record.transDate
        that.expandedRowKeys = [] // 重置展开节点，只展开当前点击的节点（内部数据调用模板，无法做到同时几个内层表格数据直接缓存在页面）
        if (expanded) {
          that.expandedRowKeys = [record.key]
          // 加载内表数据
          that.getMomData(code,date)
        }
        
        
      },
      //自定义展开和收起的图标
      expandIcon(props) {
        // console.log("props=",props);
        // if (props.record.qualityMerchantList.length > 0) {
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
        // }else {//无数据-图标
        //   return (
        //     <a style="color: 'black' ,margin-right:0px" >
        //       <a-icon type="pushpin" />
        //     </a>
        //   );
        // }
      },


    //查询
    searchQuery(){
      var that = this
      console.log("merchantCode=",this.queryParam.merchantCode,"mom=",this.queryParam.mom)

      that.getLastMomData()

    },
    
    //选择月份
    onChange(date, dateString){
      // console.log("date=",date,"dateString=",dateString)
      var that = this
      that.mom = dateString
      // console.log("merchantCode=",this.queryParam.merchantCode,"mom=",this.queryParam.mom)
    },

    //重置
    searchReset(){
      var that = this

      that.queryParam.merchantCode = ''
      that.queryParam.mom = ''
      that.mom = ''

      that.getLastMomData()
    }

  },
};
</script>
