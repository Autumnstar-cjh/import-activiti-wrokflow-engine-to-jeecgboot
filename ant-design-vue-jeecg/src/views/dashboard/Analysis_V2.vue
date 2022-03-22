<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">

      <!-- <a-col class="data—title" :sm="24" :md="12" :xl="6">
          <div>四<br>个<br>产<br>业</div>
      </a-col> -->
<!-- 交易笔数统计 -->
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="交易笔数(万笔)" :total="fourIndustry_totalNum">
          <a-tooltip :title="fourIndustry_totalNum_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <!-- 交易笔数面积图-->
            <mini-area :dataSource="fourIndustry_numberData" />
          </div>
          <template slot="footer">日均笔数 <span>{{fourIndustry_avgDayNum}}</span></template>
        </chart-card>
      </a-col>

<!-- 交易金额统计 -->
      <a-col :sm="24" :md="12" :xl="6">
        <chart-card :loading="loading" title="交易总额(万元)" :total="fourIndustry_totalTradingMoney">
          <a-tooltip :title="fourIndustry_tr_Money_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <!-- 交易额面积图-->
            <mini-area :dataSource="fourIndustry_tradingMoneyData"/>
          </div>
          <template slot="footer">日均交易额<span> {{fourIndustry_avgDayTradingMoney}}</span></template>
        </chart-card>
      </a-col>

<!-- 公司收入统计 -->
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="公司收入(万元)" :total="fourIndustry_totalIncome">
          <a-tooltip :title="fourIndustry_totalIncome_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <!-- 公司收入面积图-->
            <mini-area :dataSource="fourIndustry_incomeData" />
          </div>
          <template slot="footer">日均收入<span> {{ fourIndustry_avgDayIncome}}</span></template>
        </chart-card>
      </a-col>


<!-- OKR总体完成度 -->
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="OKR完成度-特色产业优质客户" >
          <a-tooltip placement="leftTop"  :title="fourIndustry_okrPercentage_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <!-- 进度条 -->
          <div>
            <div class="okr-progress-title">新增客户</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.newAdd"/>
            </div>
          </div>
          
          <div>
            <div class="okr-progress-title">交易笔数</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.tradingNum"/>
            </div>
          </div>
          
          <div>
            <div class="okr-progress-title">交易金额</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.finishOfTrading"/>
            </div>
          </div>

          <div>
            <div class="okr-progress-title">公司收入</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.finishOfIncome"/>
            </div>
          </div>


          <template slot="footer">
            <trend flag="" style="margin-right: 16px;">
              <span slot="term">完成情况</span>
              
            </trend>
        
          </template>
        </chart-card>
      </a-col>
    </a-row>



<!-- 项目交易金额排行榜 -->
    <a-card :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">
          <div class="extra-wrapper" slot="tabBarExtraContent">
            <div class="extra-wrapper" slot="tabBarExtraContent">
              <!-- 时间周期选择器 -->
              <a-range-picker :defaultValue="[moment(defaultStartTime, dateFormat), moment(defaultEndTime, dateFormat)]" :style="{width: '256px'}" @change="dateIsOk" />
            </div>
          </div>
          <a-tab-pane loading="true" tab="存量优质商户" key="1">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24" style="width:60%;">
                <barMultid v-if="userPowerValue" title="收入(万元)" :dataSource="trading_MultidDate" :fields="tranding_MultidFields"></barMultid>
                <barMultidOrdinaryUser v-else title="收入(万元)" :dataSource="trading_MultidDate" :fields="tranding_MultidFields"></barMultidOrdinaryUser>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24" style="width:40%;">
                <rank-list v-if="userPowerValue" title="存量优质商户收入排行榜(万元)" :list="trxTopPojectList"/>
                <rankList-ordinary-user v-else title="存量优质商户收入排行榜" :list="trxTopPojectList"/>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane tab="新增优质商户" key="2">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24" style="width:60%;">
              <barMultid v-if="userPowerValue" title="收入(万元)" :dataSource="income_MultidDate" :fields="income_MultidFields"></barMultid>
              <barMultidOrdinaryUser v-else title="收入(万元)" :dataSource="income_MultidDate" :fields="income_MultidFields"></barMultidOrdinaryUser>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24" style="width:40%;">
                <rank-list v-if="userPowerValue" title="新增优质商户收入排行榜(万元)" :list="feeTopPojectList"/>
                <rankList-ordinary-user v-else title="新增优质商户收入排行榜" :list="feeTopPojectList"/>
              </a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>

<!-- 通商云交易 -->
    <a-card v-if="hasPermisson('home:cloudacc')" :loading="loading" :bordered="false" :body-style="{padding: '0'}">
      <div class="salesCard">
        <a-tabs v-model="activeKeyCloudAcc" default-active-key="1" size="large" :tab-bar-style="{marginBottom: '24px', paddingLeft: '16px'}">
          <div class="extra-wrapper" slot="tabBarExtraContent">
            <div class="extra-wrapper" slot="tabBarExtraContent">
              <!-- 时间周期选择器 -->
              <a-range-picker v-if="activeKeyCloudAcc == '2'" :defaultValue="[moment(defaultStartTime, dateFormat), moment(defaultEndTime, dateFormat)]" :style="{width: '256px'}" @change="dateIsOkCloudAcc" />
            </div>
          </div>
          <a-tab-pane loading="true" tab="云商通近7日交易" key="1">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24" style="width:60%;">
                <barAndLine title="交易额(万元)" barText="交易额(万元)" lineText="交易笔数" :dataSource="cloudAccLastSevenDays" ></barAndLine>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24" style="width:40%;">
                <rank-list title="云商通近7日交易排行榜(元)" :list="topCoudAccAppLastSevenDays"/>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane tab="云商通本年累计交易" key="2">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24" style="width:60%;">
              <barAndLine title="交易额(万元)" barText="交易额(万元)" lineText="交易笔数" :dataSource="cloudAccAppCurrentYear" ></barAndLine>
              
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24" style="width:40%;">
                <rank-list title="交易排行榜(万元)" :list="topCoudAccAppCurrentYear"/>
              </a-col>
            </a-row>
          </a-tab-pane>
        </a-tabs>
      </div>
    </a-card>


    <!-- 最近一周访问次数统计 -->
    <!--
    <a-row>
      <a-col :span="24">
        <a-card :loading="loading" :bordered="false" title="最近一周访问次数统计" :style="{ marginTop: '24px' }">
          <a-row>
            <a-col :span="6">
              <head-info title="今日访问IP数" :content="loginfo.todayIp"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="environment" style="font-size: 24px"  />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="今日访问次数" :content="loginfo.todayVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="team" style="font-size: 24px"  />
              </a-spin>
            </a-col>
            <a-col :span="6">
              <head-info title="访问总次数" :content="loginfo.totalVisitCount"></head-info>
            </a-col>
            <a-col :span="2">
              <a-spin class='circle-cust'>
                <a-icon slot="indicator" type="rise" style="font-size: 24px"  />
              </a-spin>
            </a-col>
          </a-row>
          <line-chart-multid :fields="visitFields" :dataSource="visitInfo"></line-chart-multid>
        </a-card>
      </a-col>
    </a-row>
     -->
  </div>
</template>

<script>
  import ChartCard from '@/components/ChartCard'
  import ACol from "ant-design-vue/es/grid/Col"
  import ATooltip from "ant-design-vue/es/tooltip/Tooltip"
  import MiniArea from '@/components/chart/MiniArea'
  import MiniBar from '@/components/chart/MiniBar'
  import MiniProgress from '@/components/chart/MiniProgress'
  import RankList from '@/components/chart/RankList'
  import RankListOrdinaryUser from '@/components/chart/RankListOrdinaryUser'
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import moment from 'moment';
  import Trend from '@/components/Trend'
  import BarAndLine from '@/components/chart/BarAndLine'
  import BarMultid from '@/components/chart/BarMultid'
  import BarMultidOrdinaryUser from '@/components/chart/BarMultidOrdinaryUser'
  import { getLoginfo,getVisitInfo,indexPageDataV2,areaDataLoadV2 } from '@/api/api'

  import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
  import { USER_AUTH,SYS_BUTTON_AUTH } from "@/store/mutation-types"


  const rankList = []
  for (let i = 0; i < 7; i++) {
    rankList.push({
      name: (i+1) + '号XX项目',
      total: 1234.56 - i * 100
    })
  }

  export default {
    name: "Analysis",
    components: {
      ATooltip,
      ACol,
      ChartCard,
      MiniArea,
      MiniBar,
      MiniProgress,
      RankList,
      RankListOrdinaryUser,
      Bar,
      BarAndLine,
      BarMultid,
      BarMultidOrdinaryUser,
      Trend,
      LineChartMultid,
      HeadInfo
    },
    data() {
      return {
        userPowerValue:false,
        trading_MultidDate:[],
        tranding_MultidFields:[],


        //收入条形图
        income_MultidDate:[],
        income_MultidFields:[],

        //杂项数据——特色产业优质客户
        fourIndustry_totalTradingMoney:'',
        fourIndustry_avgDayTradingMoney:'',
        fourIndustry_totalIncome:'',
        fourIndustry_avgDayIncome:'',
        fourIndustry_totalNum:'',
        fourIndustry_avgDayNum:'',
        
        dateFormat: 'YYYY-MM-DD',
        defaultStartTime:'',
        defaultEndTime:'',
        
        //面积图数据-特色产业优质客户
        fourIndustry_tradingMoneyData:[],//交易额
        fourIndustry_incomeData:[],//公司收入
        fourIndustry_numberData:[],//交易笔数
        fourIndustry_okrPercentage: 0, // OKR完成比例

        loading: true,
        center: null,
        rankList,
        barMonthTrxData:[],
        // barMonthFeeData:[],
        trxTopPojectList:[],
        feeTopPojectList:[],
        loginfo:{},
        visitFields:['ip','visit'],
        visitInfo:[],
        indicator: <a-icon type="loading" style="font-size: 24px" spin />,

        //OKR面板数据
        OKR:{
          //特色产业优质客户
          newAdd:0,//新增客户完成率
          tradingNum:0,//交易笔数完成率
          finishOfTrading :0,//交易金额完成率
          finishOfIncome:0,//公司收入完成率
        },
        
        //指标说明——特色产业优质客户
        fourIndustry_tr_Money_tips:'',//交易金额
        fourIndustry_totalIncome_tips:'',//公司收入
        fourIndustry_totalNum_tips:'',//交易笔数
        fourIndustry_okrPercentage_tips:'',//OKR完成度

        //通商云相关统计
        activeKeyCloudAcc:"1", // 当前tab页

        cloudAccLastSevenDays: [],
        topCoudAccAppLastSevenDays: [],

        cloudAccAppCurrentYear:[],
        topCoudAccAppCurrentYear:[],

        permissionList:[] // 功能权限

      }
    },
    created() {
      this.userPower();
      this.defaultTime();
      setTimeout(() => {
        this.loading = !this.loading
      }, 1000)
      this.indexDate(null);
      this.areaDate();
      this.getOkrPercent();
      this.getTrxBarData(null);
      this.getFeeBarData(null);
      this.getTrxTopProject(null);
      this.getFeeTopProject(null);
      this.initLogInfo();

      // 通商云
      this.initPermission();
      this.getCloudAccLastSevenDays(null);
      this.getTopCoudAccAppLastSevenDays(null);
      
    },
    methods: {
      moment,

      hasPermisson(permission){
        if(this.permissionList == null || this.permissionList.length<=0 || this.permissionList==undefined){
          return false;
        }

        let hasPerm = false;
        this.permissionList.forEach( element =>{
            if(element.action === permission){
              hasPerm = true;
            }
        });

        return hasPerm;

      },
      initPermission(){
           let that = this;

          let allPermissionList = [];

          console.log("菜单权限--Global--",sessionStorage.getItem(USER_AUTH));
          let authList = JSON.parse(sessionStorage.getItem(USER_AUTH) || "[]");
          for (var auth of authList) {
              that.permissionList.push(auth); 
          }

          console.log("页面权限--Global--",sessionStorage.getItem(SYS_BUTTON_AUTH));
          let allAuthList = JSON.parse(sessionStorage.getItem(SYS_BUTTON_AUTH) || "[]");
          for (var gauth of allAuthList) {
              allPermissionList.push(gauth);
          }

          console.log("permissionList", that.permissionList);
          console.log("allPermissionList", allPermissionList);

          console.log("hasPermisson", this.hasPermisson("home:cloudacc"));
      },

      initLogInfo () {
        getLoginfo(null).then((res)=>{
          if(res.success){
            Object.keys(res.result).forEach(key=>{
              res.result[key] =res.result[key]+""
            })
            this.loginfo = res.result;
          }
        })
        getVisitInfo().then(res=>{
          if(res.success){
             console.log("aaaaaa",res.result)
             this.visitInfo = res.result;
           }
         }) 
      },

      //顶部数据
      indexDate(params1){
        indexPageDataV2(params1).then(res =>{
            console.log("==================首页杂项数据："+JSON.stringify(res.result))
            /**
             * 特色产业优质客户
             */
            this.fourIndustry_totalTradingMoney = JSON.stringify(res.result.tourismIndustryData.totalTradingMoney)
            this.fourIndustry_avgDayTradingMoney = JSON.stringify(res.result.tourismIndustryData.avgDayTradingMoney)+"元"
            this.fourIndustry_totalIncome = JSON.stringify(res.result.tourismIndustryData.totalIncome)
            this.fourIndustry_avgDayIncome = JSON.stringify(res.result.tourismIndustryData.avgDayIncome)+"元"
            this.fourIndustry_totalNum = JSON.stringify(res.result.tourismIndustryData.totalNum)
            this.fourIndustry_avgDayNum = JSON.stringify(res.result.tourismIndustryData.avgDayNum)+"笔"
            this.fourIndustry_tr_Money_tips = <p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>特色产业优质客户<br/>交易金额为 {res.result.tourismIndustryData.totalTradingMoney} 万元</p>
            this.fourIndustry_totalIncome_tips=<p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>特色产业优质客户<br/>公司收入 {res.result.tourismIndustryData.totalIncome} 万元</p>
            this.fourIndustry_totalNum_tips = <p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>特色产业优质客户<br/>交易笔数为 {res.result.tourismIndustryData.totalNum} 万笔</p>
        })  
      },

      //mini面积图数据
      areaDate(){
        areaDataLoadV2().then(res =>{
            /** 
           特色产业优质客户
           */
           let that = this;
         
           res.result.tourismIndustryData.forEach(element => {
               //日交易金额
              that.fourIndustry_tradingMoneyData.push({
                  x: element.transactionDate,
                  y: element.totalTradingMoney
              })

              //公司日收入
               that.fourIndustry_incomeData.push({
                  x: element.transactionDate,
                  y: element.totalIncome
              })

              //日交易笔数
               that.fourIndustry_numberData.push({
                  x: element.transactionDate,
                  y: element.totalNum
              })

            });
            
         })   
      },
      
      //OKR总体完成度
      getOkrPercent(){
        var that = this;
        let params1 = {};
        getAction("/home/dayTrxStatV2/getPercent", params1).then((res)=>{
          if(res.success){
            // console.log("OKR总体完成度：", res.result.okrProgress );
            that.OKR = res.result.okrProgress;
            //特色产业优质客户
            that.fourIndustry_okrPercentage_tips = <p>{res.result.startTime}至{res.result.endTime}<br/>特色产业优质客户<br/>
            新增客户数完成率：{that.OKR.newAdd}%<br/>交易笔数完成率：{that.OKR.tradingNum}%<br/>
            交易金额完成率：{that.OKR.finishOfTrading}%<br/>公司收入完成率：{that.OKR.finishOfIncome}%</p>
          } 
        });
      },

      //存量优质商户收入条形图
      getTrxBarData(params1){
        var that = this;
        getAction("/home/dayTrxStatV2/getTrxStatByMonth", params1).then((res)=>{
          if(res.success){
            //  console.log("存量优质商户收入条形图返回", res.result );
             let list = []
            list.push(res.result.fourIndustry)
            that.trading_MultidDate = list
            that.tranding_MultidFields = res.result.monthMap.monthList;
          }
        });
      },

      //新增优质商户收入条形图
      getFeeBarData(params1){
         var that = this;
        getAction("/home/dayTrxStatV2/getFeeStatByMonth", params1).then((res)=>{
          if(res.success){
            //  console.log("存量优质商户收入条形图返回", res.result );
            let list = []
            list.push(res.result.fourIndustry)
            that.income_MultidDate = list
            that.income_MultidFields = res.result.monthMap.monthList

            console.log("that.income_MultidDate", that.income_MultidDate);
            console.log("that.income_MultidFields", that.income_MultidFields);

          }
        });
      },

      //存量优质商户收入金额排行榜
      getTrxTopProject(params1){
        var that = this;
        getAction("/home/dayTrxStatV2/getTrxTopProject", params1).then((res)=>{
          if(res.success){
            console.log("存量优质商户收入金额排行榜", res.result );

            let data = []
            res.result.forEach( element => { 
              data.push(
                  { name: element.name, 
                   total: element.total.toFixed(2)}
                  );
              } 
            );
             
            that.trxTopPojectList = data;
          }
        });
      },

      //新增优质商户收入金额排行榜
      getFeeTopProject(params1){
        var that = this;
        getAction("/home/dayTrxStatV2/getFeeTopProject", params1).then((res)=>{
          if(res.success){
             console.log("新增优质商户收入金额排行榜", res.result );

             let data = []
            res.result.forEach( element => { 
              data.push(
                  { name: element.name, 
                   total: element.total.toFixed(2)}
                  );
              } 
            );

             that.feeTopPojectList = data;
          }
        });
      },


       //通商云近七日交易统计
      getCloudAccLastSevenDays(params1){
         var that = this;
        getAction("/cloudacc/cloudAccRpt/lastSevenDaysTransList", params1).then((res)=>{
          if(res.success){
            console.log("通商云近七日交易统计", res.result );

            var data = [];

            res.result.forEach( element => { 
              data.push(
                  { type: element.reportDate, 
                    bar: element.incomeAmountTotal , 
                   line: element.incomeOrderTotal }
                  );
              } 
            );


            that.cloudAccLastSevenDays = data;

            console.log("cloudAccLastSevenDays:", that.cloudAccLastSevenDays);

          }
        });
      },

      //通商云近七日应用交易排行
      getTopCoudAccAppLastSevenDays(params1){
         var that = this;
        getAction("/cloudacc/cloudAccRpt/topLastSevenDaysAppTrans", params1).then((res)=>{
          if(res.success){
            console.log("通商云近七日交易排行", res.result );

            var data = [];

            res.result.forEach( element => { 
              data.push(
                  { name: element.appName, 
                   total: element.incomeAmountTotal}
                  );
              } 
            );

            that.topCoudAccAppLastSevenDays = data;

            console.log("topCoudAccAppLastSevenDays:", that.topCoudAccAppLastSevenDays);

          }
        });
      },


      //通商云近本年应用交易排行
      getTopCoudAccAppCurYear(params1){
         var that = this;
        getAction("/cloudacc/cloudAccRpt/topCurrentYearAppTrans", params1).then((res)=>{
          if(res.success){
            console.log("通商云本年交易排行", res.result );

            var data = [];

            res.result.forEach( element => { 
              data.push(
                  { name: element.appName, 
                   total: element.incomeAmountTotal}
                  );
              } 
            );

            that.topCoudAccAppCurrentYear = data;

            console.log("cloudAccAppCurrentYear:", that.topCoudAccAppCurrentYear);

          }
        });
      },

      //通商云近本年应用交易按月统计
      getCloudAccTransSummaryByMonth(params1){
        var that = this;
        getAction("/cloudacc/cloudAccRpt/cloudAccTransSummaryByMonth", params1).then((res)=>{
          if(res.success){
            console.log("通商云近按月统计", res.result );

            var data = [];

            res.result.forEach( element => { 
              data.push(
                  { type: element.reportMonth, 
                   bar: element.incomeAmountTotal , 
                   line: element.incomeOrderTotal }
                  );
              } 
            );


            that.cloudAccAppCurrentYear = data;

            console.log("cloudAccAppCurrentYear:", that.cloudAccAppCurrentYear);

          }
        });

      },

      dateIsOkCloudAcc: function(dateString){
        // alert();
         let param = {beginDate:moment(dateString[0]).format('YYYY-MM-DD'),endDate:moment(dateString[1]).format('YYYY-MM-DD')}
          console.log("dateIsOkCloudAcc", param);
          this.getCloudAccTransSummaryByMonth(param);
          this.getTopCoudAccAppCurYear(param);
         
      },
      

      //根据时间周期选择数据加载
      dateIsOk:function(dateString){
        // alert();
        let param = {startTime:moment(dateString[0]).format('YYYYMMDD'),endTime:moment(dateString[1]).format('YYYYMMDD')}
        this.getTrxBarData(param);//加载交易金额条形图
        this.getFeeBarData(param);//加载公司收入条形图
        this.indexDate(param);//加载顶部细项数据
        this.getTrxTopProject(param);//加载项目交易金额排行榜
        this.getFeeTopProject(param);//加载项目收入排行榜
      },


      defaultTime(){
        var that = this;
        getAction("/home/dayTrxStatV2/getDefaultTime").then((res)=>{
          if(res.success){
            that.defaultStartTime = res.result.startTime.trim();
            that.defaultEndTime = res.result.endTime.trim();

            let param = {beginDate:moment( that.defaultStartTime).format('YYYY-MM-DD'),endDate:moment(that.defaultEndTime).format('YYYY-MM-DD')};
            console.log("getDefaultTime", param);
            this.getCloudAccTransSummaryByMonth(param);
            this.getTopCoudAccAppCurYear(param);
          }
        });
      },

      //获取字典，查询查看数据权限
      userPower(){
        var that = this;
        getAction("/home/dayTrxStatV2/barDataDic").then((res)=>{
          console.log("接收到返回的字典：",res)
          if(res.success){
            that.userPowerValue = res.result
          }
        });
      },

      timePrint(){
        var myDate = new Date();
        var y=myDate.getFullYear();    //获取完整的年份(4位,1970-????)
        var m=myDate.getMonth()+1;       //获取当前月份(0-11,0代表1月)
        var d=myDate.getDate();        //获取当前日(1-31)
        var t=myDate.getTime();        //获取当前时间(从1970.1.1开始的毫秒数)
        var h=myDate.getHours();       //获取当前小时数(0-23)
        var min=myDate.getMinutes();     //获取当前分钟数(0-59)
        var s=myDate.getSeconds();     //获取当前秒数(0-59)
        var ms=myDate.getMilliseconds();    //获取当前毫秒数(0-999)
        if (m >= 1 && m <= 9) {
          m = "0" + m;
        }
        if (d >= 0 && d <= 9) {
          d = "0" + d;
        }
        if (h >= 0 && h <= 9) {
          h = "0" + h;
        }
        if (min >= 0 && min <= 9) {
          min = "0" + min;
        }
        if (s >= 0 && s <= 9) {
          s = "0" + s;
        }
        var fh='-';
        var fh2=':'
        var tolocaltime=y+fh+m+fh+d+' '+h+fh2+min+fh2+s+fh2+ms;//当前时间
        return tolocaltime;
      }
    }
  }
</script>

<style lang="scss" scoped>
  .data—title{
    margin-bottom: 24px;
    margin-left: 0.7%;
    width: 3.3%;
    height:180px;
    background-color: #74BCFF;
    text-align: center;
    padding-top: 1.3%;
    padding-bottom: 0.9%;
    font-size: 22px;
    font-weight: 600;
    color: white;

  }
  .circle-cust{
    position: relative;
    top: 28px;
    left: -100%;
  }
  .extra-wrapper {
    line-height: 55px;
    padding-right: 24px;

    .extra-item {
      display: inline-block;
      margin-right: 24px;

      a {
        margin-left: 24px;
      }
    }
  }
  .okr-progress-icon{
    width:60%;
    float:right;
    height:21px;
    margin-right:5%;
  }
  .okr-progress-title{
    width:30%;
    float:left;
    height:21px;
    font-size:12px;
    padding-top:2px;
    text-align:center;
  }

  /* 首页访问量统计 */
  .head-info {
    position: relative;
    text-align: left;
    padding: 0 32px 0 0;
    min-width: 125px;

    &.center {
      text-align: center;
      padding: 0 32px;
    }

    span {
      color: rgba(0, 0, 0, .45);
      display: inline-block;
      font-size: .95rem;
      line-height: 42px;
      margin-bottom: 4px;
    }
    p {
      line-height: 42px;
      margin: 0;
      a {
        font-weight: 600;
        font-size: 1rem;
      }
    }
  }
</style>