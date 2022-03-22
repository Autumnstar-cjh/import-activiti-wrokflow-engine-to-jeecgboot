<template>
  <page-layout :avatar="avatar">
    <div slot="headerContent">
      <div class="title">{{ timeFix }}，{{ nickname() }}<span class="welcome-text">，{{ welcome() }}</span></div>
      <div>通联支付云南分公司</div>
    </div>
    <div slot="extra">
      <a-row class="more-info">
        <a-col :span="8">
          <head-info title="目标数" :content="objectivesCount" :center="false" :bordered="false"/>
        </a-col>
      </a-row>
    </div>

    <div>
      <a-row :gutter="24">
        <a-col :xl="24" :lg="24" :md="24" :sm="24" :xs="24">
          
<!-- 我的目标 -->
          <a-card
            class="project-list"
            :loading="loading"
            style="margin-bottom: 24px;"
            :bordered="false"
            title="我的目标"
            :body-style="{ padding: 0 }">
            <!-- <a slot="extra"><a-switch class="a-switch" checkedChildren="全部" unCheckedChildren="已完成" size="default" :defaultChecked="checked" @change="objectivesChangeBtn"/></a> -->
            <a slot="extra"><a-button type="default" icon="plus" @click="addObjectives()"></a-button></a>
            <a slot="extra"></a>
            <div>
              <a-card-grid class="project-card-grid" :key="i" v-for="(item, i) in objectives">
                <a-card :bordered="false" :body-style="{ padding: 0 }">
                  <a-card-meta class="project-card-grid-objectives">
                    <div slot="title" class="card-title">
                      <img width="22" height="22" src="./../../assets/target.png"/>
                      <a class="objectives-title">
                        <!--关键指标列表：标题tips，鼠标悬浮以显示完整关键指标-->
                        <a-tooltip placement="topLeft">
                          <template slot="title">
                            <div slot="description" class="card-okr-result-tips" :key="j" v-for="(keyResult,j) in item.keyResultList">
                              <img width="10" height="10" src="./../../assets/red-point.png"/>
                              关键指标{{j+1}}：{{keyResult.content}}
                            </div>
                          </template>
                          <a class="objectives-title">目标：{{ item.objectives.objectives }}</a>
                        </a-tooltip>
                      </a>
                      <a slot="extra"><a-icon type="form" @click="handleEdit(item.objectives)"></a-icon></a>
                    </div>
                    <!--关键指标列表：卡片-->
                    <div slot="description" class="card-okr-result" :key="j" v-for="(keyResult,j) in item.keyResultList">
                      &nbsp;&nbsp;&nbsp;&nbsp;
                      <img width="10" height="10" src="./../../assets/point.png"/>
                      关键指标{{j+1}}：{{keyResult.content}}
                    </div>
                    
                  </a-card-meta>
                  <div class="project-item">
                    <img width="20" height="20" src="./../../assets/date.png"/>
                    &nbsp;
                    <a>{{item.objectives.startTime}} 至 {{item.objectives.endTime}}</a>
                    <div v-if="item.objectives.hasChild=='1'">[非末级]</div>
                    <span v-if="item.sysDepart!=null" class="datetime">{{ item.sysDepart.departName }}</span>
                  </div>
                </a-card>
              </a-card-grid>
              <!-- 分页部分 -->
              <a-card class="pageBtn">
                <a-pagination style="float:right;" :defaultCurrent="defaultObjCurrent" :defaultPageSize="defaultObjPageSize" :current="objCurrent" :total="objTotalRecord" @change="objPageChange"/>
              </a-card>
            </div>
          </a-card>

<!-- 工作计划 -->
          <a-card
            class="project-list"
            :loading="loading"
            style="margin-bottom: 24px;"
            :bordered="false"
            title="工作计划"
            :body-style="{ padding: 0 }">
            <a slot="extra"><a-switch class="a-switch" checkedChildren="全部" unCheckedChildren="未完成" size="default" :defaultChecked="checked" @change="switchChange"/></a>
            <a slot="extra"><a-button type="default" icon="plus" @click="addPlan()"></a-button></a>
            <div>
              <a-card-grid class="project-card-grid" :key="i" v-for="(item, i) in planList">
                <a-card :bordered="false" :body-style="{ padding: 0 }">
                  <a-card-meta>
                    <div slot="title" class="card-title">
                      <img width="28" height="28" src="./../../assets/plan.png"/>
                      <a class="plan-title">{{item.title}}</a>
                      <a slot="extra"><a-icon type="form" @click="edit_plan(item)"/></a>
                    </div>
                    <div slot="description" class="card-priority">
                      优先级：
                      <a-button-group class="btnGroup" size="small">
                        <a-button :type="btnType1(item.priority)">高</a-button>
                        <a-button :type="btnType2(item.priority)">中</a-button>
                        <a-button :type="btnType3(item.priority)">低</a-button>
                        <a-button :type="btnType4(item.priority)">无</a-button>
                      </a-button-group>
                    </div>
                    <div slot="description" class="card-description">
                      计划内容： {{item.planDescribe}}
                    </div>
                    <div slot="description" class="card-associated-description">
                      关联目标： {{item.associatedTargetContent}}
                    </div>
                    <div slot="description" class="card-associated-completedSituation">
                      完成情况： {{item.completedSituation}}
                    </div>
                  </a-card-meta>
                  <div class="project-plan-item">
                    <img width="20" height="20" src="./../../assets/week.png"/>
                    &nbsp;&nbsp;
                    <a>{{item.week}}</a>
                    <a-tag class="plan-status" :color="statusColor(item.status)">{{statusStr(item.status)}}</a-tag>
                  </div>
                </a-card>
              </a-card-grid>
              <!-- 分页部分 -->
              <a-card class="pageBtn">
                <a-pagination style="float:right;" :defaultCurrent="defaultCurrentPage" :defaultPageSize="defaultPageSize" :current="current" :total="totalRecord" @change="planPageChange"/>
              </a-card>
            </div>
          </a-card>
          
          <workplanList ref="workplanList"></workplanList>
          
<!-- 更新动态 -->
          <a-card :loading="loading" title="更新动态" :bordered="false">
            <a-list>
              <a-list-item :key="index" v-for="(item, index) in objectiveEvents">
                <a-list-item-meta>
                  <a-avatar slot="avatar" src="/assets/logo.png" />
                   <div slot="description">{{ item.createTime }}</div>
                  <div slot="title">
                    <span>{{ item.createBy }}</span>&nbsp;
                    的&nbsp;<a href="#">{{ item.okrObjectives }}</a>&nbsp;
                    <span>{{ item.userAction }}</span>&nbsp;
                    <span>，最新完成值：{{ item.newFinishedAmount }}，目标值：{{item.newTargetAmount}}</span>&nbsp;
                    <span>,{{ item.userAction }}</span>&nbsp;

                    <a v-if="item.remark!=null" href="#">，他高兴地说，{{ item.remark }}</a>
                    <a v-else href="#">。</a>
                  </div>
                 
                </a-list-item-meta>
              </a-list-item>
            </a-list>
              <a-pagination :defaultCurrent="defaultCurrentPage" :defaultPageSize="defaultPageSize" 
              :total="totalRecord" @change="getObjectiveEvent"/>
        
          </a-card>
        </a-col>
       
      </a-row>
    </div>
     <okrObjectivesUpdate-modal ref="modalForm" @ok="modalFormOk"></okrObjectivesUpdate-modal>

     <okrWorkPlan-modal ref="planForm" @ok="planFormOk"></okrWorkPlan-modal>

     <objectives-modal ref="objectivesForm" @ok="objectivesFormOk"></objectives-modal>

     
  </page-layout>

</template>

<script>
  import { timeFix } from "@/utils/util"
  import {mapGetters} from "vuex"
  import PageLayout from '@/components/page/PageLayout'
  import HeadInfo from '@/components/tools/HeadInfo'
  import Radar from '@/components/chart/Radar'
  import { getRoleList, getServiceList } from "@/api/manage"
  import { getAction,deleteAction,putAction,postAction} from '@/api/manage'
  import OkrObjectivesUpdateModal from './modules/OkrObjectivesUpdateModal'
  import OkrWorkPlanModal from './modules/OkrWorkPlanV2Modal'
  import ObjectivesModal from './modules/ObjectivesV2Modal'
  import WorkPlanList from './WorkPlanList'

  const DataSet = require('@antv/data-set')

  export default {
    name: "Workplace",
    components: {
      PageLayout,
      HeadInfo,
      OkrObjectivesUpdateModal,
      ObjectivesModal,
      OkrWorkPlanModal,
      "workplanList":WorkPlanList
    },
    data() {
      return {
        sysUser: '',
        timeFix: timeFix(),
        avatar: '',
        user: {},
        priority:80,
        week:'',
        objList:{},
        planStatus:'',
        projects: [],
        planList:[],
        loading: true,
        radarLoading: true,
        objectives: [],
        objectiveEvents: [],
        teams: [],

        current:1,
        checked:true,
        defaultCurrentPage:1,
        totalRecord:0,
        defaultPageSize:10,

        isAll:true,
        objCurrent:1,
        defaultObjCurrent:1,
        objTotalRecord:0,
        defaultObjPageSize:10,
        // data
        axis1Opts: {
          dataKey: 'item',
          line: null,
          tickLine: null,
          grid: {
            lineStyle: {
              lineDash: null
            },
            hideFirstLine: false
          }
        },
        axis2Opts: {
          dataKey: 'score',
          line: null,
          tickLine: null,
          grid: {
            type: 'polygon',
            lineStyle: {
              lineDash: null
            }
          }
        },
        scale: [{
          dataKey: 'score',
          min: 0,
          max: 80
        }],
        radarData: [],
        objectivesCount:"0个",
         url: {
          count: "/okrv2/objectives/countCurUser",
          // listByCurrentUser: "/okr/okrObjectives/listByCurrentUser",
          listByCurrentUser: "/okrv2/objectives/listByCurrentUser",
          listObjetivesEvent: "/okrv2/okrObjectivesEvent/list",
          getWorkPlanList:"/plan/workPlanV2/list",
          getDate:'/plan/workPlanV2/getDate'
        },
      }
    },
    computed: {
      userInfo() {
        return this.$store.getters.userInfo
      }
      
    },
    created() {
      this.user = this.userInfo
      this.sysUser = this.user.username
      this.avatar = window._CONFIG['imgDomainURL'] +"/"+ this.userInfo.avatar
      console.log('this.avatar :'+ this.avatar)

      getRoleList().then(res => {
        console.log('workplace -> call getRoleList()', res)
      })

      getServiceList().then(res => {
        console.log('workplace -> call getServiceList()', res)
      })
    },
    mounted() {
      this.getObjectiveCount();
      this.getObjective(1,6,true);
      this.getObjectiveEvent(1, 10);  
      this.getTeams();
      this.getWorkPlanList(1,6,null);
      this.getDate();
    },
    methods: {
      
      modalFormOk(formData,arr){
        var that = this;

        that.getObjective(1,6,true);
        that.getObjectiveEvent();


      },
      ...mapGetters(["nickname", "welcome"]),
      getObjectiveEvent(page, pageSize) {
        console.log("getObjectiveEvent...");
        var that = this;

        that.defaultCurrentPage = page;
        that.defaultPageSize = pageSize;
        // let params = {pageNo:that.defaultCurrentPage, 
        //               pageSize:that.defaultPageSize,
        //                column:"create_time",
        //               order:"DESC"};
        // getAction(that.url.listObjetivesEvent, params).then((res)=>{
        //   if(res.success){
        //     console.log( res.result );
			  //     this.loading = false;
        //     that.objectiveEvents = res.result.records;
        //     that.defaultCurrentPage = res.result.current;
        //     that.totalRecord = res.result.total;
        //     that.defaultPageSize = res.result.size;
        //     this.$forceUpdate()
        //   }
        // })
      },
      getObjectiveCount(){
        console.log("getObjectiveCount...");
        var that = this;
        let params = {};
        getAction(that.url.count,params).then((res)=>{
		
          if(res.success){
		   this.loading = false;
            that.objectivesCount = res.result + "个";
            this.$forceUpdate()
          }
        })
      },
      /**
       * 获取工作目标列表
       * 
       * pageNo 页编号
       * pageSize 每组页数
       * isAll 为true查询全部，为false查询已完成
       */
      getObjective(pageNo,pageSize,isAll) {
        console.log("getObjective...");
        var that = this;

        that.defaultObjCurrent = pageNo;
        that.defaultObjPageSize = pageSize;
        let params = {pageNo:pageNo,pageSize:pageSize,isAll:isAll};
        // console.log("参数：",params)
        getAction(that.url.listByCurrentUser, params).then((res)=>{
          if(res.success){
            console.log("收取到的工作目标数据为：",res)
		        this.loading = false;
            that.objectives = res.result;
            // that.defaultObjCurrent = res.result[0].pageList.current;
            // that.objTotalRecord = res.result[0].pageList.total;
            // that.defaultObjPageSize = res.result[0].pageList.size;
            this.$forceUpdate()
          }
        })
        
      },
      //工作目标查询条件切换按钮：‘全部’、‘已完成’，默认显示全部
      objectivesChangeBtn(checked){
        // console.log("当前状态：",checked)
        this.objCurrent = 1
        this.isAll = checked
        this.getObjective(1,6,checked)
      },
      //工作目标分页 改变页码触发
      objPageChange(current){
        // console.log("更改页码，isAll=",this.isAll)
        this.objCurrent = current
        this.getObjective(this.objCurrent,6,this.isAll)
      },

      getTeams() {
        this.$http.get('/api/workplace/teams')
          .then(res => {
            this.teams = res.result
          })
      },
      //新增OKR目标
      addObjectives(){
        this.$refs.objectivesForm.add();
        this.$refs.objectivesForm.title = "新增OKR目标";
        this.$refs.objectivesForm.disableSubmit = false;//禁用提交
      },
      //更新OKR目标
      handleEdit(item){
        console.log("目标对象是...", item);
        item.remark = "";
        this.$refs.objectivesForm.edit(item);
        this.$refs.objectivesForm.title = "更新OKR目标";
        this.$refs.objectivesForm.disableSubmit = false;//禁用提交modalForm
      },
      //新增/修改完成后刷新数据
      objectivesFormOk(){
        this.getObjective(1,6,true);
      },


      planFormOk(){
        var that = this;
        //我的工作计划列表加载
        that.getWorkPlanList(1,6,null);
        //所有人工作计划列表加载
        that.$refs.workplanList.searchQueryData();
      },
      getWorkPlanList(page,pageSize,status){
        console.log("getWorkPlanList...");
        var that = this;

        that.defaultCurrentPage = page;
        that.defaultPageSize = pageSize;
        let param = {
                    status,
                    pageNo:that.defaultCurrentPage, 
                    pageSize:that.defaultPageSize};
        getAction(that.url.getWorkPlanList, param).then((res)=>{
          if(res.success){
            // this.loading = false;
            // console.log("接收到的工作计划数据是："+JSON.stringify(res.result))
            that.planList = res.result.records;
            that.defaultCurrentPage = res.result.current;
            that.totalRecord = res.result.total;
            that.defaultPageSize = res.result.size;
            this.$forceUpdate()
          }else{
            console.log("返回结果异常！");
          }
        })
        
      },
      addPlan(){
        // alert(JSON.stringify(this.user.username))
        // this.objList.push({objectives:this.objectives,username:this.user.username});
        this.$refs.planForm.add(this.objList,this.objectives,this.user.username);
        this.$refs.planForm.title = "添加工作计划";
        this.$refs.planForm.disableSubmit = false;//禁用提交
      },
      edit_plan(plan){
        // alert(JSON.stringify(plan.startTime))
        this.$refs.planForm.edit(plan,this.objectives);
        this.$refs.planForm.title = "更新工作计划";
        this.$refs.planForm.disableSubmit = false;//禁用提交
      },
      getDate(){
        var that = this;
        let params = {};
        getAction(that.url.getDate, params).then((res)=>{
          if(res.success){
            // this.objList.push({dateObj:res.result})
            this.objList = res.result
            this.$forceUpdate()
          }else{
            console.log("返回结果异常！");
          }
        })
      },
      //页码改变触发事件
      planPageChange(current){
        // console.log("当前是第"+current+"页")
        // console.log("当前显示状态："+this.checked)
        this.current = current
        if(this.checked){
          this.getWorkPlanList(this.current,6,null)
        }else{
          this.getWorkPlanList(this.current,6,'0')
        }
      },
      //显示"全部"、"未完成"状态改变触发事件
      switchChange(checked){
        // console.log(`状态改变为： ${checked}`)
        // let params = {status:'0'}
        if(checked){
          this.checked = checked
          // console.log("显示状态："+this.checked)
          this.current = 1
          this.getWorkPlanList(1,6,null)
        }else{
          this.checked = checked
          // console.log("显示状态："+this.checked)
          this.current = 1
          this.getWorkPlanList(1,6,'0')
        }
      },
      statusColor(s){
        if(s.trim() == '未完成' || s.trim() == '0'){
          return '#f50'
        }
        if(s.trim() == '已完成' || s.trim() == '1'){
          return '#87d068'
        }
      },
      statusStr(status){
        if(status == '0'){
          return '未完成'
        }
        if(status == '1'){
          return '已完成'
        }
        return status
      },

      btnType1(priority){
        if(priority == 100){return 'primary'}return 'dashed'
      },
      btnType2(priority){
        if(priority == 75){return 'primary'}return 'dashed'
      },
      btnType3(priority){
        if(priority == 50){return 'primary'}return 'dashed'
      },
      btnType4(priority){
        if(priority == 0){return 'primary'}return 'dashed'
      },
    }
  }
</script>

<style lang="scss" scoped>
  .project-list {

    .project-card-grid-objectives{
        height: 132px;
        overflow: hidden;
      }
    .card-title {
      font-size: 0;

      a {
        color: rgba(0, 0, 0, 0.85);
        margin-left: 12px;
        line-height: 24px;
        height: 24px;
        display: inline-block;
        vertical-align: top;
        font-size: 14px;

        &:hover {
          color: #1890ff;
        }
      }
    }
    .objectives-title,.plan-title{
      width:82%;
      overflow: hidden;
    }
    .card-description {
      color: rgba(0, 0, 0, 0.45);
      height: 25px;
      line-height: 22px;
      //文字溢出时隐藏，溢出部分用...代替
      overflow:hidden;
      text-overflow:ellipsis;
      white-space:nowrap;
      margin-top: 10px;
    }
    .card-associated-description{
      height: 25px;
      //文字溢出时隐藏，溢出部分用...代替
      overflow:hidden;
      text-overflow:ellipsis;
      white-space:nowrap;
    }
    .card-associated-completedSituation{
      height: 25px;
      //文字溢出时隐藏，溢出部分用...代替
      overflow:hidden;
      text-overflow:ellipsis;
      white-space:nowrap;
    }
    .card-okr-result{
      height: 25px;
      //文字溢出时隐藏，溢出部分用...代替
      overflow:hidden;
      text-overflow:ellipsis;
      white-space:nowrap;
    }
    .card-priority{
      color: rgba(0, 0, 0, 0.45);
      height: 24px;
      line-height: 22px;
      margin-top: 14px;
      overflow: hidden;
    }
    .card-associated-target{
      color: rgba(0, 0, 0, 0.45);
      height: 50px;
      line-height: 22px;
      overflow: hidden;
      margin-bottom: 16px;
    }
    .a-switch{
      margin-right:20px;
    }
    .card-description-progress {
      color: rgba(0, 0, 0, 0.45);
      height: 22px;
      line-height: 22px;
      overflow: hidden;
      margin: 2px;
    }
    .project-item {
      display: flex;
      margin-top: 8px;
      overflow: hidden;
      font-size: 14px;
      height: 20px;
      line-height: 20px;
      a {
        color: #737373;
        display: inline-block;
        flex: 1 1 0;
      }
      .datetime {
        color: #737373;
        flex: 0 0 auto;
        float: right;
      }
    }
    .project-plan-item{
        display: flex;
      margin-top: 8px;
      overflow: hidden;
      font-size: 12px;
      height: 23px;
      line-height: 20px;
      a {
        font-size: 14px;
        color: rgba(0, 0, 0, 0.45);
        display: inline-block;
        flex: 1 1 0;

      }
      .datetime {
        color: rgba(0, 0, 0, 0.25);
        flex: 0 0 auto;
        float: right;
      }
    }
    .ant-card-meta-description {
      color: rgba(0, 0, 0, 0.45);
      height: 44px;
      line-height: 22px;
      overflow: hidden;
    }
  }

  .item-group {
    padding: 20px 0 8px 24px;
    font-size: 0;
    a {
      color: rgba(0, 0, 0, 0.65);
      display: inline-block;
      font-size: 14px;
      margin-bottom: 13px;
      width: 25%;
    }
  }

  .members {
    a {
      display: block;
      margin: 12px 0;
      line-height: 24px;
      height: 24px;
      .member {
        font-size: 14px;
        color: rgba(0, 0, 0, .65);
        line-height: 24px;
        max-width: 100px;
        vertical-align: top;
        margin-left: 12px;
        transition: all 0.3s;
        display: inline-block;
      }
      &:hover {
        span {
          color: #1890ff;
        }
      }
    }
  }

  .mobile {

    .project-list {

      .project-card-grid {
        width: 100%;
      }
      
    }

    .more-info {
      border: 0;
      padding-top: 16px;
      margin: 16px 0 16px;
    }

    .headerContent .title .welcome-text {
      display: none;
    }
  }
  .plan-status{
    float:right;
  }

  .pageBtn{
    width:100%;
    float:left;
    
  }
</style>