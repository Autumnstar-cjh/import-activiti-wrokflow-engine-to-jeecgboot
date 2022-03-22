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

        <a-form-item label="注册日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date read-only="readonly" placeholder="请选择注册日期" v-decorator="[ '注册日期', validatorRules.注册日期]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="变更日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <j-date read-only="readonly" placeholder="请选择变更日期" v-decorator="[ '变更日期', validatorRules.变更日期]" :trigger-change="true" style="width: 100%"/>
        </a-form-item>
        <a-form-item label="商户号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户号', validatorRules.商户号]" placeholder="请输入商户号"></a-input>
        </a-form-item>
        <a-form-item label="商户名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户名称', validatorRules.商户名称]" placeholder="请输入商户名称"></a-input>
        </a-form-item>
        <a-form-item label="商户简称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户简称', validatorRules.商户简称]" placeholder="请输入商户简称"></a-input>
        </a-form-item>
        <a-form-item label="所属分公司" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '所属分公司', validatorRules.所属分公司]" placeholder="请输入所属分公司"></a-input>
        </a-form-item>
        <a-form-item label="拓展机构" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '拓展机构', validatorRules.拓展机构]" placeholder="请输入拓展机构"></a-input>
        </a-form-item>
        <a-form-item label="维护机构" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '维护机构', validatorRules.维护机构]" placeholder="请输入维护机构"></a-input>
        </a-form-item>
        <a-form-item label="父商户号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '父商户号', validatorRules.父商户号]" placeholder="请输入父商户号"></a-input>
        </a-form-item>
        <a-form-item label="来源渠道" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '来源渠道', validatorRules.来源渠道]" placeholder="请输入来源渠道"></a-input>
        </a-form-item>
        <a-form-item label="合作方编号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '合作方编号', validatorRules.合作方编号]" placeholder="请输入合作方编号"></a-input>
        </a-form-item>
        <a-form-item label="渠道方备注" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '渠道方备注', validatorRules.渠道方备注]" placeholder="请输入渠道方备注"></a-input>
        </a-form-item>
        <a-form-item label="开户所在省" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '开户所在省', validatorRules.开户所在省]" placeholder="请输入开户所在省"></a-input>
        </a-form-item>
        <a-form-item label="开户所在市" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '开户所在市', validatorRules.开户所在市]" placeholder="请输入开户所在市"></a-input>
        </a-form-item>
        <a-form-item label="注册地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '注册地址', validatorRules.注册地址]" placeholder="请输入注册地址"></a-input>
        </a-form-item>
        <a-form-item label="mcc大类" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ 'mcc大类', validatorRules.mcc大类]" placeholder="请输入mcc大类"></a-input>
        </a-form-item>
        <a-form-item  label="mcc42域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ 'mcc42域', validatorRules.mcc42域]" placeholder="请输入mcc42域"></a-input>
        </a-form-item>
        <a-form-item label="mcc32域" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ 'mcc32域', validatorRules.mcc32域]" placeholder="请输入mcc32域"></a-input>
        </a-form-item>
        <a-form-item label="营业证件号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '营业证件号码', validatorRules.营业证件号码]" placeholder="请输入营业证件号码"></a-input>
        </a-form-item>
        <a-form-item label="法人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '法人姓名', validatorRules.法人姓名]" placeholder="请输入法人姓名"></a-input>
        </a-form-item>
        <a-form-item label="法人身份证" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '法人身份证', validatorRules.法人身份证]" placeholder="请输入法人身份证"></a-input>
        </a-form-item>
        <a-form-item label="财务联系人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '财务联系人', validatorRules.财务联系人]" placeholder="请输入财务联系人"></a-input>
        </a-form-item>
        <a-form-item label="财务联系人电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '财务联系人电话', validatorRules.财务联系人电话]" placeholder="请输入财务联系人电话"></a-input>
        </a-form-item>
        <a-form-item label="拓展人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '拓展人', validatorRules.拓展人]" placeholder="请输入拓展人"></a-input>
        </a-form-item>
        <a-form-item label="商户状态" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户状态', validatorRules.商户状态]" placeholder="请输入商户状态"></a-input>
        </a-form-item>
        <a-form-item label="账户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '账户类型', validatorRules.账户类型]" placeholder="请输入账户类型"></a-input>
        </a-form-item>
        <a-form-item label="卡折类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '卡折类型', validatorRules.卡折类型]" placeholder="请输入卡折类型"></a-input>
        </a-form-item>
        <a-form-item label="维护人" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '维护人', validatorRules.维护人]" placeholder="请输入维护人"></a-input>
        </a-form-item>
        <a-form-item label="维护人联系方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '维护人联系方式', validatorRules.维护人联系方式]" placeholder="请输入维护人联系方式"></a-input>
        </a-form-item>
        <a-form-item label="营业执照名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '营业执照名称', validatorRules.营业执照名称]" placeholder="请输入营业执照名称"></a-input>
        </a-form-item>
        <a-form-item label="营业执照有效期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '营业执照有效期', validatorRules.营业执照有效期]" placeholder="请输入营业执照有效期"></a-input>
        </a-form-item>
        <a-form-item label="法人身份证有效期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '法人身份证有效期', validatorRules.法人身份证有效期]" placeholder="请输入法人身份证有效期"></a-input>
        </a-form-item>
        <a-form-item label="公司控股股东" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '公司控股股东', validatorRules.公司控股股东]" placeholder="请输入公司控股股东"></a-input>
        </a-form-item>
        <a-form-item label="控股股东或公司实际控制人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '控股股东或公司实际控制人姓名', validatorRules.控股股东或公司实际控制人姓名]" placeholder="请输入控股股东或公司实际控制人姓名"></a-input>
        </a-form-item>
        <a-form-item label="签约类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '签约类型', validatorRules.签约类型]" placeholder="请输入签约类型"></a-input>
        </a-form-item>
        <a-form-item label="商户性质" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户性质', validatorRules.商户性质]" placeholder="请输入商户性质"></a-input>
        </a-form-item>
        <a-form-item label="结算账户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '结算账户', validatorRules.结算账户]" placeholder="请输入结算账户"></a-input>
        </a-form-item>
        <a-form-item label="结算账户名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '结算账户名', validatorRules.结算账户名]" placeholder="请输入结算账户名"></a-input>
        </a-form-item>
        <a-form-item label="商户结算开户行名称" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户结算开户行名称', validatorRules.商户结算开户行名称]" placeholder="请输入商户结算开户行名称"></a-input>
        </a-form-item>
        <a-form-item label="支行联行号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '支行联行号', validatorRules.支行联行号]" placeholder="请输入支行联行号"></a-input>
        </a-form-item>
        <a-form-item label="结算方式" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '结算方式', validatorRules.结算方式]" placeholder="请输入结算方式"></a-input>
        </a-form-item>
        <a-form-item label="结算时间" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '结算时间', validatorRules.结算时间]" placeholder="请输入结算时间"></a-input>
        </a-form-item>
        <a-form-item label="节假日清算" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '节假日清算', validatorRules.节假日清算]" placeholder="请输入节假日清算"></a-input>
        </a-form-item>
        <a-form-item label="是否绑定二维码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '是否绑定二维码', validatorRules.是否绑定二维码]" placeholder="请输入是否绑定二维码"></a-input>
        </a-form-item>
        <a-form-item label="商户评级" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户评级', validatorRules.商户评级]" placeholder="请输入商户评级"></a-input>
        </a-form-item>
        <a-form-item label="组织机构代码证" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '组织机构代码证', validatorRules.组织机构代码证]" placeholder="请输入组织机构代码证"></a-input>
        </a-form-item>
        <a-form-item label="代码证有效期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '代码证有效期', validatorRules.代码证有效期]" placeholder="请输入代码证有效期"></a-input>
        </a-form-item>
        <a-form-item label="法人代表证件类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '法人代表证件类型', validatorRules.法人代表证件类型]" placeholder="请输入法人代表证件类型"></a-input>
        </a-form-item>
        <a-form-item label="双免截止日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '双免截止日期', validatorRules.双免截止日期]" placeholder="请输入双免截止日期"></a-input>
        </a-form-item>
        <a-form-item label="录入用户" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '录入用户', validatorRules.录入用户]" placeholder="请输入录入用户"></a-input>
        </a-form-item>
        <a-form-item label="是否开通实时到账" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '是否开通实时到账', validatorRules.是否开通实时到账]" placeholder="请输入是否开通实时到账"></a-input>
        </a-form-item>
        <a-form-item label="一级行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '一级行业', validatorRules.一级行业]" placeholder="请输入一级行业"></a-input>
        </a-form-item>
        <a-form-item label="二级行业" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '二级行业', validatorRules.二级行业]" placeholder="请输入二级行业"></a-input>
        </a-form-item>
        <a-form-item label="控制人身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input  read-only="readonly" v-decorator="[ '控制人身份证号', validatorRules.控制人身份证号]" placeholder="请输入控制人身份证号"></a-input>
        </a-form-item>
        <a-form-item label="控制人身份证有效期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '控制人身份证有效期', validatorRules.控制人身份证有效期]" placeholder="请输入控制人身份证有效期"></a-input>
        </a-form-item>
        <a-form-item label="注册资本" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '注册资本', validatorRules.注册资本]" placeholder="请输入注册资本"></a-input>
        </a-form-item>
        <a-form-item label="合作方营业证件号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '合作方营业证件号码', validatorRules.合作方营业证件号码]" placeholder="请输入合作方营业证件号码"></a-input>
        </a-form-item>
        <a-form-item label="合作方法人身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '合作方法人身份证号', validatorRules.合作方法人身份证号]" placeholder="请输入合作方法人身份证号"></a-input>
        </a-form-item>
        <a-form-item label="是否三证合一" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '是否三证合一', validatorRules.是否三证合一]" placeholder="请输入是否三证合一"></a-input>
        </a-form-item>
        <a-form-item label="统一社会信用代码证" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '统一社会信用代码证', validatorRules.统一社会信用代码证]" placeholder="请输入统一社会信用代码证"></a-input>
        </a-form-item>
        <a-form-item label="社会信用代码证有效期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '社会信用代码证有效期', validatorRules.社会信用代码证有效期]" placeholder="请输入社会信用代码证有效期"></a-input>
        </a-form-item>
        <a-form-item label="税务登记号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '税务登记号码', validatorRules.税务登记号码]" placeholder="请输入税务登记号码"></a-input>
        </a-form-item>
        <a-form-item label="税务登记号码有效期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '税务登记号码有效期', validatorRules.税务登记号码有效期]" placeholder="请输入税务登记号码有效期"></a-input>
        </a-form-item>
        <a-form-item label="商户类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户类型', validatorRules.商户类型]" placeholder="请输入商户类型"></a-input>
        </a-form-item>
        <a-form-item label="银联商户号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '银联商户号', validatorRules.银联商户号]" placeholder="请输入银联商户号"></a-input>
        </a-form-item>
        <a-form-item label="受益所有人姓名" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '受益所有人姓名', validatorRules.受益所有人姓名]" placeholder="请输入受益所有人姓名"></a-input>
        </a-form-item>
        <a-form-item label="受益所有人证件类型" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '受益所有人证件类型', validatorRules.受益所有人证件类型]" placeholder="请输入受益所有人证件类型"></a-input>
        </a-form-item>
        <a-form-item label="受益所有人证件号码" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '受益所有人证件号码', validatorRules.受益所有人证件号码]" placeholder="请输入受益所有人证件号码"></a-input>
        </a-form-item>
        <a-form-item label="受益所有人证件有效日期" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '受益所有人证件有效日期', validatorRules.受益所有人证件有效日期]" placeholder="请输入受益所有人证件有效日期"></a-input>
        </a-form-item>
        <a-form-item label="受益所有人地址" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '受益所有人地址', validatorRules.受益所有人地址]" placeholder="请输入受益所有人地址"></a-input>
        </a-form-item>
        <a-form-item label="商户网站" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '商户网站', validatorRules.商户网站]" placeholder="请输入商户网站"></a-input>
        </a-form-item>
        <a-form-item label="结算身份证号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '结算身份证号', validatorRules.结算身份证号]" placeholder="请输入结算身份证号"></a-input>
        </a-form-item>
        <a-form-item label="直联结算标识" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '直联结算标识', validatorRules.直联结算标识]" placeholder="请输入直联结算标识"></a-input>
        </a-form-item>
        <a-form-item label="客服电话" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '客服电话', validatorRules.客服电话]" placeholder="请输入客服电话"></a-input>
        </a-form-item>
        <a-form-item label="所属事业部" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '所属事业部', validatorRules.所属事业部]" placeholder="请输入所属事业部"></a-input>
        </a-form-item>
        <a-form-item label="直联机构号" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '直联机构号', validatorRules.直联机构号]" placeholder="请输入直联机构号"></a-input>
        </a-form-item>
        <a-form-item label="结算归集" :labelCol="labelCol" :wrapperCol="wrapperCol">
          <a-input read-only="readonly" v-decorator="[ '结算归集', validatorRules.结算归集]" placeholder="请输入结算归集"></a-input>
        </a-form-item>

      </a-form>
    </a-spin>
  </a-modal>
</template>

<script>

  import { httpAction } from '@/api/manage'
  import pick from 'lodash.pick'
  import JDate from '@/components/jeecg/JDate'  

  export default {
    name: "MerchantModal",
    components: { 
      JDate,
    },
    data () {
      return {
        form: this.$form.createForm(this),
        title:"操作",
        width:800,
        visible: false,
        model: {},
        labelCol: {
          xs: { span: 24 },
          sm: { span: 5 },
        },
        wrapperCol: {
          xs: { span: 24 },
          sm: { span: 16 },
        },

        confirmLoading: false,
        validatorRules:{
        注册日期:{},
        变更日期:{},
        商户号:{},
        商户名称:{},
        商户简称:{},
        所属分公司:{},
        拓展机构:{},
        维护机构:{},
        父商户号:{},
        来源渠道:{},
        合作方编号:{},
        渠道方备注:{},
        开户所在省:{},
        开户所在市:{},
        注册地址:{},
        mcc大类:{},
        mcc42域:{},
        mcc32域:{},
        营业证件号码:{},
        法人姓名:{},
        法人身份证:{},
        财务联系人:{},
        财务联系人电话:{},
        拓展人:{},
        商户状态:{},
        账户类型:{},
        卡折类型:{},
        维护人:{},
        维护人联系方式:{},
        营业执照名称:{},
        营业执照有效期:{},
        法人身份证有效期:{},
        公司控股股东:{},
        控股股东或公司实际控制人姓名:{},
        签约类型:{},
        商户性质:{},
        结算账户:{},
        结算账户名:{},
        商户结算开户行名称:{},
        支行联行号:{},
        结算方式:{},
        结算时间:{},
        节假日清算:{},
        是否绑定二维码:{},
        商户评级:{},
        组织机构代码证:{},
        代码证有效期:{},
        法人代表证件类型:{},
        双免截止日期:{},
        录入用户:{},
        是否开通实时到账:{},
        一级行业:{},
        二级行业:{},
        控制人身份证号:{},
        控制人身份证有效期:{},
        注册资本:{},
        合作方营业证件号码:{},
        合作方法人身份证号:{},
        是否三证合一:{},
        统一社会信用代码证:{},
        社会信用代码证有效期:{},
        税务登记号码:{},
        税务登记号码有效期:{},
        商户类型:{},
        银联商户号:{},
        受益所有人姓名:{},
        受益所有人证件类型:{},
        受益所有人证件号码:{},
        受益所有人证件有效日期:{},
        受益所有人地址:{},
        商户网站:{},
        结算身份证号:{},
        直联结算标识:{},
        客服电话:{},
        所属事业部:{},
        直联机构号:{},
        结算归集:{},
        },
        url: {
          add: "/sybData/Merchant/add",
          edit: "/sybData/Merchant/edit",
        }
     
      }
    },
    created () {
    },
    methods: {
      add () {
        this.edit({});
      },
      edit (record) {
        this.form.resetFields();
        this.model = Object.assign({}, record);
        this.visible = true;
        this.$nextTick(() => {
          this.form.setFieldsValue(pick(this.model,'注册日期','变更日期','商户号','商户名称','商户简称','所属分公司','拓展机构','维护机构','父商户号','来源渠道','合作方编号','渠道方备注','开户所在省','开户所在市','注册地址','mcc大类','mcc42域','mcc32域','营业证件号码','法人姓名','法人身份证','财务联系人','财务联系人电话','拓展人','商户状态','账户类型','卡折类型','维护人','维护人联系方式','营业执照名称','营业执照有效期','法人身份证有效期','公司控股股东','控股股东或公司实际控制人姓名','签约类型','商户性质','结算账户','结算账户名','商户结算开户行名称','支行联行号','结算方式','结算时间','节假日清算','是否绑定二维码','商户评级','组织机构代码证','代码证有效期','法人代表证件类型','双免截止日期','录入用户','是否开通实时到账','一级行业','二级行业','控制人身份证号','控制人身份证有效期','注册资本','合作方营业证件号码','合作方法人身份证号','是否三证合一','统一社会信用代码证','社会信用代码证有效期','税务登记号码','税务登记号码有效期','商户类型','银联商户号','受益所有人姓名','受益所有人证件类型','受益所有人证件号码','受益所有人证件有效日期','受益所有人地址','商户网站','结算身份证号','直联结算标识','客服电话','所属事业部','直联机构号','结算归集'))
        })
      },
      close () {
        this.$emit('close');
        this.visible = false;
      },
      handleOk () {
        this.$emit('close');
        this.visible = false;
        // const that = this;
        // // 触发表单验证
        // this.form.validateFields((err, values) => {
        //   if (!err) {
        //     that.confirmLoading = true;
        //     let httpurl = '';
        //     let method = '';
        //     if(!this.model.id){
        //       httpurl+=this.url.add;
        //       method = 'post';
        //     }else{
        //       httpurl+=this.url.edit;
        //        method = 'put';
        //     }
        //     let formData = Object.assign(this.model, values);
        //     console.log("表单提交数据",formData)
        //     httpAction(httpurl,formData,method).then((res)=>{
        //       if(res.success){
        //         that.$message.success(res.message);
        //         that.$emit('ok');
        //       }else{
        //         that.$message.warning(res.message);
        //       }
        //     }).finally(() => {
        //       that.confirmLoading = false;
        //       that.close();
        //     })
        //   }
         
        // })
      },
      handleCancel () {
        this.close()
      },
      popupCallback(row){
        this.form.setFieldsValue(pick(row,'注册日期','变更日期','商户号','商户名称','商户简称','所属分公司','拓展机构','维护机构','父商户号','来源渠道','合作方编号','渠道方备注','开户所在省','开户所在市','注册地址','mcc大类','mcc42域','mcc32域','营业证件号码','法人姓名','法人身份证','财务联系人','财务联系人电话','拓展人','商户状态','账户类型','卡折类型','维护人','维护人联系方式','营业执照名称','营业执照有效期','法人身份证有效期','公司控股股东','控股股东或公司实际控制人姓名','签约类型','商户性质','结算账户','结算账户名','商户结算开户行名称','支行联行号','结算方式','结算时间','节假日清算','是否绑定二维码','商户评级','组织机构代码证','代码证有效期','法人代表证件类型','双免截止日期','录入用户','是否开通实时到账','一级行业','二级行业','控制人身份证号','控制人身份证有效期','注册资本','合作方营业证件号码','合作方法人身份证号','是否三证合一','统一社会信用代码证','社会信用代码证有效期','税务登记号码','税务登记号码有效期','商户类型','银联商户号','受益所有人姓名','受益所有人证件类型','受益所有人证件号码','受益所有人证件有效日期','受益所有人地址','商户网站','结算身份证号','直联结算标识','客服电话','所属事业部','直联机构号','结算归集'))
      },

      
    }
  }
</script>