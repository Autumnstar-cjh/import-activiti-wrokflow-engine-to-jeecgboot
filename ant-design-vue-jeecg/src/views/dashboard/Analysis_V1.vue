<template>
  <div class="page-header-index-wide">
    <a-row :gutter="24">

      <a-col class="data—title" :sm="24" :md="12" :xl="6">
          <div>四<br>个<br>产<br>业</div>
      </a-col>
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
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px',width: '21%' }">
        <chart-card :loading="loading" title="OKR完成度-四个产业" >
          <a-tooltip placement="leftTop"  :title="fourIndustry_okrPercentage_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <!-- 进度条 -->
          <div>
            <div class="okr-progress-title">项目</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.projectOnline"/>
            </div>
          </div>
          
          <div>
            <div class="okr-progress-title">收入</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.finishOfIncome"/>
            </div>
          </div>
          
          <div>
            <div class="okr-progress-title">交易</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.finishOfTrading"/>
            </div>
          </div>

          <div>
            <div class="okr-progress-title">笔数</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.tradingNum"/>
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



<a-row :gutter="24">
      <a-col class="data—title" :sm="24" :md="12" :xl="6">
        <div>旅<br>游<br>行<br>业</div>
      </a-col>
<!-- 交易笔数统计 -->
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="交易笔数(万笔)" :total="totalNum">
          <a-tooltip :title="totalNum_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <!-- 交易笔数面积图-->
            <mini-area :dataSource="numberData" />
          </div>
          <template slot="footer">日均笔数 <span>{{avgDayNum}}</span></template>
        </chart-card>
      </a-col>

<!-- 交易金额统计 -->
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="交易总额(万元)" :total="totalTradingMoney">
          <a-tooltip :title="tr_Money_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <!-- 交易额面积图-->
            <mini-area :dataSource="tradingMoneyData" />
          </div>
          <template slot="footer">日均交易额<span> {{avgDayTradingMoney}}</span></template>
        </chart-card>
      </a-col>

<!-- 公司收入统计 -->
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px' }">
        <chart-card :loading="loading" title="公司收入(万元)" :total="totalIncome">
          <a-tooltip :title="totalIncome_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <div>
            <!-- 公司收入面积图-->
            <mini-area :dataSource="incomeData" />
          </div>
          <template slot="footer">日均收入<span> {{ avgDayIncome}}</span></template>
        </chart-card>
      </a-col>

<!-- OKR总体完成度 -->
      <a-col :sm="24" :md="12" :xl="6" :style="{ marginBottom: '24px',width: '21%' }">
        <chart-card :loading="loading" title="OKR完成度-旅游行业" >
          <a-tooltip placement="leftBottom" :title="okrPercentage_tips" slot="action">
            <a-icon type="info-circle-o" />
          </a-tooltip>
          <!-- 进度条 -->
          <div>
            <div class="okr-progress-title">商户</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.tourismProjectOnline"/>
            </div>
          </div>
          
          <div>
            <div class="okr-progress-title">收入</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.tourismFinishOfIncome"/>
            </div>
          </div>
          
          <div>
            <div class="okr-progress-title">交易</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.tourismFinishOfTrading"/>
            </div>
          </div>

          <div>
            <div class="okr-progress-title">笔数</div>
            <div class="okr-progress-icon">
              <a-progress class="okr-progress" :percent="OKR.tourismTradingNum"/>
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
          <a-tab-pane loading="true" tab="交易金额" key="1">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                <!-- <bar title="交易金额(万元)" :dataSource="barMonthTrxData"/> -->
                <barMultid title="交易金额(万元)" :dataSource="trading_MultidDate" :fields="tranding_MultidFields"></barMultid>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="项目交易金额排行榜(万元)" :list="trxTopPojectList"/>
              </a-col>
            </a-row>
          </a-tab-pane>
          <a-tab-pane tab="公司收入" key="2">
            <a-row>
              <a-col :xl="16" :lg="12" :md="12" :sm="24" :xs="24">
                <!-- <bar title="公司收入(万元)" :dataSource="barMonthFeeData"/> -->
              <barMultid title="公司收入(万元)" :dataSource="income_MultidDate" :fields="income_MultidFields"></barMultid>
              </a-col>
              <a-col :xl="8" :lg="12" :md="12" :sm="24" :xs="24">
                <rank-list title="项目收入排行榜(万元)" :list="feeTopPojectList"/>
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
  import Bar from '@/components/chart/Bar'
  import LineChartMultid from '@/components/chart/LineChartMultid'
  import HeadInfo from '@/components/tools/HeadInfo.vue'
  import moment from 'moment';
  import Trend from '@/components/Trend'
  import BarMultid from '@/components/chart/BarMultid'
  import { getLoginfo,getVisitInfo,indexPageData,areaDataLoad } from '@/api/api'

  import { getAction,deleteAction,putAction,postAction} from '@/api/manage'

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
      Bar,
      BarMultid,
      Trend,
      LineChartMultid,
      HeadInfo
    },
    data() {
      return {
        trading_MultidDate:[],
        tranding_MultidFields:[],


        //收入条形图
        income_MultidDate:[],
        income_MultidFields:[],

        //杂项数据——旅游
        totalTradingMoney:'',
        avgDayTradingMoney:'',
        totalIncome:'',
        avgDayIncome:'',
        totalNum:'',
        avgDayNum:'',
        //杂项数据——四个产业
        fourIndustry_totalTradingMoney:'',
        fourIndustry_avgDayTradingMoney:'',
        fourIndustry_totalIncome:'',
        fourIndustry_avgDayIncome:'',
        fourIndustry_totalNum:'',
        fourIndustry_avgDayNum:'',
        
        dateFormat: 'YYYY-MM-DD',
        defaultStartTime:'',
        defaultEndTime:'',
        

        //面积图数据-旅游
        tradingMoneyData:[],//交易额
        incomeData:[],//公司收入
        numberData:[],//交易笔数
        okrPercentage: 0, // OKR完成比例
        //面积图数据-四个产业
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
          //四个产业
          projectOnline:'',//项目上线完成率
          finishOfIncome:'',//收入总额完成率
          finishOfTrading:'',//交易金额完成率
          tradingNum:'',//交易笔数完成率
          
          //旅游产业
          tourismProjectOnline:'',//项目上线完成率
          tourismFinishOfIncome:'',//收入总额完成率
          tourismFinishOfTrading:'',//交易金额完成率
          tourismTradingNum:''//交易笔数完成率
          
        },

        //指标说明——旅游
        tr_Money_tips:'',//交易金额
        totalIncome_tips:'',//公司收入
        totalNum_tips:'',//交易笔数
        okrPercentage_tips:'',//OKR完成度
        
        //指标说明——四个产业
        fourIndustry_tr_Money_tips:'',//交易金额
        fourIndustry_totalIncome_tips:'',//公司收入
        fourIndustry_totalNum_tips:'',//交易笔数
        fourIndustry_okrPercentage_tips:'',//OKR完成度

      }
    },
    created() {
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
      

    },
    methods: {
      moment,

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
        indexPageData(params1).then(res =>{
            console.log("==================首页杂项数据："+JSON.stringify(res.result))
            /**
             * 旅游产业
             */
            this.totalTradingMoney = JSON.stringify(res.result.tourismIndustryData.totalTradingMoney)
            this.avgDayTradingMoney = JSON.stringify(res.result.tourismIndustryData.avgDayTradingMoney)+"元"
            this.totalIncome = JSON.stringify(res.result.tourismIndustryData.totalIncome)
            this.avgDayIncome = JSON.stringify(res.result.tourismIndustryData.avgDayIncome)+"元"
            this.totalNum = JSON.stringify(res.result.tourismIndustryData.totalNum)
            this.avgDayNum = JSON.stringify(res.result.tourismIndustryData.avgDayNum)+"笔"
            this.tr_Money_tips = <p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>旅游行业交易金额为 {res.result.tourismIndustryData.totalTradingMoney} 万元</p>
            this.totalIncome_tips=<p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>旅游行业公司收入 {res.result.tourismIndustryData.totalIncome} 万元</p>
            this.totalNum_tips = <p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>旅游行业交易笔数为 {res.result.tourismIndustryData.totalNum} 万笔</p>
        
            /**
             * 四个产业
             */
            this.fourIndustry_totalTradingMoney = JSON.stringify(res.result.fourIndustryData.totalTradingMoney)
            this.fourIndustry_avgDayTradingMoney = JSON.stringify(res.result.fourIndustryData.avgDayTradingMoney)+"元"
            this.fourIndustry_totalIncome = JSON.stringify(res.result.fourIndustryData.totalIncome)
            this.fourIndustry_avgDayIncome = JSON.stringify(res.result.fourIndustryData.avgDayIncome)+"元"
            this.fourIndustry_totalNum = JSON.stringify(res.result.fourIndustryData.totalNum)
            this.fourIndustry_avgDayNum = JSON.stringify(res.result.fourIndustryData.avgDayNum)+"笔"
            this.fourIndustry_tr_Money_tips = <p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>四个产业交易金额为 {res.result.fourIndustryData.totalTradingMoney} 万元</p>
            this.fourIndustry_totalIncome_tips=<p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>四个产业公司收入 {res.result.fourIndustryData.totalIncome} 万元</p>
            this.fourIndustry_totalNum_tips = <p style="text-align: center;">{res.result.periodTime.startTime}至{res.result.periodTime.endTime}<br/>四个产业交易笔数为 {res.result.fourIndustryData.totalNum} 万笔</p>
        })  
      },

      //mini面积图数据
      areaDate(){
        areaDataLoad().then(res =>{
           console.log("mini面积图数据：",res)
           /** 
           旅游产业
           */
           //日交易金额
           for (let i = 0; i < res.result.tourismIndustryData.length; i+=1) {
              this.tradingMoneyData.push({
                  x: res.result.tourismIndustryData[i].transactionDate,
                  y: res.result.tourismIndustryData[i].totalTradingMoney
              })

            }
            //公司日收入
            for (let i = 0; i < res.result.tourismIndustryData.length; i+=1) {
             this.incomeData.push({
                  x: res.result.tourismIndustryData[i].transactionDate,
                  y: res.result.tourismIndustryData[i].totalIncome
              })
            }
            //日交易笔数
            for (let i = 0; i < res.result.tourismIndustryData.length; i+=1) {
              this.numberData.push({
                  x: res.result.tourismIndustryData[i].transactionDate,
                  y: res.result.tourismIndustryData[i].totalNum
              })
            }

            /** 
           四个产业
           */
           //日交易金额
           for (let i = 0; i < res.result.fourIndustryData.length; i+=1) {
              this.fourIndustry_tradingMoneyData.push({
                  x: res.result.fourIndustryData[i].transactionDate,
                  y: res.result.fourIndustryData[i].totalTradingMoney
              })

            }
            //公司日收入
            for (let i = 0; i < res.result.fourIndustryData.length; i+=1) {
             this.fourIndustry_incomeData.push({
                  x: res.result.fourIndustryData[i].transactionDate,
                  y: res.result.fourIndustryData[i].totalIncome
              })
            }
            //日交易笔数
            for (let i = 0; i < res.result.fourIndustryData.length; i+=1) {
              this.fourIndustry_numberData.push({
                  x: res.result.fourIndustryData[i].transactionDate,
                  y: res.result.fourIndustryData[i].totalNum
              })
            }
         })   
      },
      
      //OKR总体完成度
      getOkrPercent(){
        var that = this;
        let params1 = {};
        getAction("/okr/okrObjectives/getPercent", params1).then((res)=>{
          if(res.success){
            console.log("OKR总体完成度：", res.result.okrProgress );
            that.OKR = res.result.okrProgress;
            //四个产业
            that.fourIndustry_okrPercentage_tips = <p>{res.result.startTime}至{res.result.endTime}<br/>四个产业<br/>项目上线完成率：{that.OKR.projectOnline}%<br/>收入总额完成率：{that.OKR.finishOfIncome}%<br/>交易总额完成率：{that.OKR.finishOfTrading}%<br/>交易笔数完成率：{that.OKR.tradingNum}%</p>
            //旅游行业
            that.okrPercentage_tips = <p>{res.result.startTime}至{res.result.endTime}<br/>旅游行业<br/>有交易商户完成率：{that.OKR.tourismProjectOnline}%<br/>收入总额完成率：{that.OKR.tourismFinishOfIncome}%<br/>交易总额完成率：{that.OKR.tourismFinishOfTrading}%<br/>交易笔数完成率：{that.OKR.tourismTradingNum}%</p>
          } 
        });
      },

      //交易金额条形图
      getTrxBarData(params1){
        var that = this;
        getAction("/home/dayTrxStat/getTrxStatByMonth", params1).then((res)=>{
          if(res.success){
             console.log("交易金额条形图返回数据：", res.result );
             let list = []
            list.push(res.result.fourIndustry)
            list.push(res.result.tourismIndustry)
            that.trading_MultidDate = list
            // that.trading_MultidDate.push(res.result.fourIndustry);
            // that.trading_MultidDate.push(res.result.tourismIndustry);
            that.tranding_MultidFields = res.result.monthMap.monthList;
          }
        });
      },

      //公司收入条形图
      getFeeBarData(params1){
         var that = this;
        getAction("/home/dayTrxStat/getFeeStatByMonth", params1).then((res)=>{
          if(res.success){
             console.log("公司收入条形图返回数据：", res.result );
            let list = []
            list.push(res.result.fourIndustry)
            list.push(res.result.tourismIndustry)
            that.income_MultidDate = list
            // that.income_MultidDate.push(res.result.fourIndustry)
            // that.income_MultidDate.push(res.result.tourismIndustry)
            that.income_MultidFields = res.result.monthMap.monthList

          }
        });
      },

      //项目交易金额排行榜
      getTrxTopProject(params1){
        var that = this;
        getAction("/home/dayTrxStat/getTrxTopProject", params1).then((res)=>{
          if(res.success){
             console.log("getTrxTopProject", res.result );
             that.trxTopPojectList = res.result;
          }
        });
      },

      //项目收入排行榜
      getFeeTopProject(params1){
        var that = this;
        getAction("/home/dayTrxStat/getFeeTopProject", params1).then((res)=>{
          if(res.success){
             console.log("getFeeTopProject", res.result );
             that.feeTopPojectList = res.result;
          }
        });
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
        getAction("/home/dayTrxStat/getDefaultTime").then((res)=>{
          if(res.success){
            that.defaultStartTime = res.result.startTime.trim();
            that.defaultEndTime = res.result.endTime.trim();
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
    width:75%;
    float:right;
    height:21px;
    margin-right:5%;
  }
  .okr-progress-title{
    width:15%;
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