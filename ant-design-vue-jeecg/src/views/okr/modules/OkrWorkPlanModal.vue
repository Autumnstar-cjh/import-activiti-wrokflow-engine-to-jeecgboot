<template>
  <a-modal
    :title="title"
    :width="width"
    :visible="visible"
    :confirmLoading="confirmLoading"
    @ok="handleOk"
    @cancel="handleCancel"
    cancelText="关闭">
    <a-spin :spinning="confirmLoading">
      <a-form :form="form">

        <a-form-item label="计划名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="[ 'title', validatorRules.title]" placeholder="请输入计划名称"></a-input>
        </a-form-item>
        <a-form-item label="负责人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-select-user-by-dep v-decorator="['person', validatorRules.person]" :trigger-change="true"/>
        </a-form-item>
        <a-form-item label="开始时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-date-picker :defaultValue="moment(defaultStartTime,'YYYY-MM-DD')" v-decorator="[ 'startTime', validatorRules.startTime]"
           :trigger-change="true" style="width: 100%" />
        </a-form-item>
        <a-form-item label="结束时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-date-picker :defaultValue="moment(defaultEndTime,'YYYY-MM-DD')" v-decorator="[ 'endTime', validatorRules.endTime]"
           :trigger-change="true" style="width: 100%" />
        </a-form-item>
        <a-form-item label="优先级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group buttonStyle="solid" v-decorator="[ 'priority', validatorRules.priority ]" :trigger-change="true">
            <a-radio-button value="100">高</a-radio-button>
            <a-radio-button value="75">中</a-radio-button>
            <a-radio-button value="50">低</a-radio-button>
            <a-radio-button value="0">无</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="关联目标" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-select  v-decorator="['associatedTarget', validatorRules.associatedTarget]" style="width: 500px" @change="handleChange">
            <a-select-option value="无">无</a-select-option>
            <a-select-option v-for="objectives in objectiveList" :value="objectives.objectives.id" :key="objectives.id"><p>{{objectives.objectives.objectives}}</p></a-select-option>
          </a-select>
        </a-form-item>
        <a-form-item label="计划描述" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['planDescribe', validatorRules.planDescribe]" :trigger-change="true" rows="4" placeholder="请输入计划描述"/>
        </a-form-item>
        <a-form-item label="状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-radio-group defaultValue="0" buttonStyle="solid" v-decorator="[ 'status', validatorRules.status ]" :trigger-change="true">
            <a-radio-button value="0">未完成</a-radio-button>
            <a-radio-button value="1" :disabled="statusShow">已完成</a-radio-button>
          </a-radio-group>
        </a-form-item>
        <a-form-item label="完成情况" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-textarea v-decorator="['completedSituation', validatorRules.completedSituation]" :trigger-change="true" rows="4" placeholder="请输入完成情况"/>
        </a-form-item>
        <a-form-item v-show="false" label="是否是顺延计划" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <!-- <a-radio-group defaultValue="0" buttonStyle="solid" v-decorator="[ 'isPostpone', validatorRules.isPostpone ]" :trigger-change="true" @change="postponeFromChange">
            <a-radio-button value="0" :type="noType">否</a-radio-button>
            <a-radio-button value="1" :type="yesType">是</a-radio-button>
          </a-radio-group> -->
          <a-button-group defaultValue="0" buttonStyle="solid" v-decorator="[ 'isPostpone', validatorRules.isPostpone ]" :trigger-change="true">
            <a-button value="0" :type="noType">否</a-button>
            <a-button value="1" :type="yesType">是</a-button>
          </a-button-group>
        </a-form-item>
        <a-form-item v-show="false" label="顺延计划" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input v-decorator="['postponeFrom', validatorRules.postponeFrom]"></a-input>
        </a-form-item>

        <!-- <a-button type="primary" @click="toDelayPlan" style="width:40%;margin-left:30%;">顺延至下周</a-button> -->

        <a-popconfirm v-show="false" placement="top" okText="确定" cancelText="取消" @confirm="toDelayPlan">
          <template slot="title">
            <p>你确定要将本工作计划顺延至下周吗？</p>
          </template>
          <a-button type="primary" style="width:40%;margin-left:30%;">顺延至下周</a-button>
        </a-popconfirm>
      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  
  import JDictSelectTag from "@/components/dict/JDictSelectTag"
  import JSelectUserByDep from '@/components/jeecgbiz/JSelectUserByDep'
  import { message } from 'ant-design-vue'
  import moment from 'moment';

  export default {
    name: "WorkPlanModal",
    components: { 
      JDate,
      JSelectUserByDep,
      JDictSelectTag,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        postponeShow:false,//顺眼计划功能模块，控制隐藏和显示
        width:800,
        visible: false,
        yesType:'',
        noType:'',
        model: {},
        statusShow:false,
        defaultStartTime:'',
        defaultEndTime:'',
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },
        objectiveList:[],
        dateFormat: 'YYYY/MM/DD',
        confirmLoading: false,
        validatorRules:{
            title:{rules: [{ required: true, message: '请输入计划名称!' }]},
            person:{rules: [{ required: true, message: '请选择负责人!' }]},
            startTime:{},
            endTime:{},
            priority:{rules: [{ required: true, message: '请选择优先级!' }]},
            planDescribe:{rules: [{ required: true, message: '请输入计划描述!' }]},
            associatedTarget:{},
            status:{rules: [{ required: true, message: '请选择状态!' }]},
            completedSituation:{},
            isPostpone:{},
            postponeFrom:{}

        },
        url: {
          add: "/plan/workPlan/add",
          edit: "/plan/workPlan/edit",
        }
     
      }
    },
    created () {
      
    },
    methods: {
      moment,
      add (dateObj,objectives,username) {
        this.postponeShow = false;
        this.statusShow = true;
        this.objectiveList = objectives;
        this.defaultStartTime = dateObj.startTime;
        this.defaultEndTime = dateObj.endTime;
        // console.log("工作列表是："+JSON.stringify(workPlanList))
        var plan = {title:'',person:username,startTime:moment(this.defaultStartTime),endTime:moment(this.defaultEndTime),priority:'100',planDescribe:'',associatedTarget:'',status:'0'};
        this.form.resetFields();
        this.model = Object.assign({}, plan);
        this.visible = true;
        this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model,'title','person','startTime','endTime','priority','planDescribe','associatedTarget','status','completedSituation'))
        })
      },
      edit (plan,objectives) {
        this.postponeShow = true;
        this.yesOrNoStyle(plan);
        //  console.log("工作列表是："+JSON.stringify(workPlanList))
        this.statusString(plan);
        this.statusShow = false;
        this.objectiveList = objectives;
        this.defaultStartTime = plan.startTime;
        this.defaultEndTime = plan.endTime;
        // alert("默认的开始时间是："+this.defaultStartTime)
        plan.startTime = moment(plan.startTime);
        plan.endTime = moment(plan.endTime);
        this.form.resetFields();
        this.model = Object.assign({}, plan);
        this.visible = true;
        this.$nextTick(() => {
        this.form.setFieldsValue(pick(this.model,'title','person','startTime','endTime','priority','planDescribe','associatedTarget','status','completedSituation'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        console.log("添加工作计划...")
        const that = this;
        // 触发表单验证
        this.form.validateFields((err, values) => {
          if (!err) {
            that.confirmLoading = true;
            let httpurl = '';
            let method = '';
            if(!this.model.id){
              console.log("this.model.id为：",this.model)
              httpurl+=this.url.add;
              method = 'post';
            }else{
              console.log("this.model.id为：",this.model)
              httpurl+=this.url.edit;
               method = 'put';
            }
            let formData = Object.assign(this.model, values);
            console.log("表单提交数据",formData)
            httpAction(httpurl,formData,method).then((res)=>{
              if(res.success){
                that.$message.success(res.message);
                that.$emit('ok');
              }else{
                that.$message.warning(res.message);
              }
            }).finally(() => {
              that.confirmLoading = false;
              that.close();
            })
          }
         
        })
      },
      toDelayPlan(){
        console.log("顺延计划...")
        const that = this;
        this.form.validateFields((err, values) => {
          if (!err) {
            let formData = Object.assign(this.model, values);
            formData.postponeFrom = formData.id;
            formData.isPostpone = '1';
            console.log("表单提交数据",formData)
            httpAction(this.url.add,formData,'post').then((res)=>{
              if(res.success){
                message.info('操作成功');
              }else{
                message.info(res.message);
              }
            }).finally(() => {
              // that.confirmLoading = false;
              // that.close();
            })
          }
         
        })
      },
      statusString(plan){
        if(plan.status == '未完成'){
          plan.status = '0'
        }
        if(plan.status == '已完成'){
          plan.status = '1'
        }
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'title','person','startTime','endTime','priority','planDescribe','associatedTarget','status','completedSituation'))
      },
      yesOrNoStyle(plan){
        // console.log("我要改颜色了，工作计划是："+JSON.stringify(plan))
        if(plan.isPostpone == '1'){
          this.yesType = 'primary'
          this.noType = 'dashed'
        }else{
          this.yesType = 'dashed'
          this.noType = 'primary'
        }
      }
    }
  }
</script>