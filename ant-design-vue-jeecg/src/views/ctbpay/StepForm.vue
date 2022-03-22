<template>
  <a-card :bordered="false">
    <a-steps class="steps" :current="currentTab">
      <a-step title="填写支付信息" />
      <a-step title="确认支付信息" />
      <a-step title="扫码" />
      <a-step title="完成" />
    </a-steps>
    <div class="content">
      <step1 v-show="currentTab === 0" @nextStep="nextStep" @toStep2="toStep2"/>
      <step2 v-show="currentTab === 1" ref="step2" @toStep3="toStep3" @prevStep="prevStep"/>
      <step3 v-show="currentTab === 2" ref="step3" @prevStep="prevStep" @toStep4="toStep4"/>
      <step4 v-show="currentTab === 3" ref="step4" @prevStep="prevStep" @finish="finish"/>
    </div>
  </a-card>
</template>

<script>
  import Step1 from './Step1'
  import Step2 from './Step2'
  import Step3 from './Step3'
  import Step4 from './Step4'

  export default {
    name: "StepForm",
    components: {
      Step1,
      Step2,
      Step3,
      Step4
    },
    data () {
      return {
        description: '',
        currentTab: 0,

        // form
        form: null,
        params:null,
      }
    },
    methods: {

      toStep2(params){
        this.params = params
        this.$refs.step2.dataInit(params)
        this.currentTab = 1
      },

      toStep3 (params) {
        this.$refs.step3.dataInit(params)
        this.currentTab = 2
      },

      toStep4 (params) {
        this.$refs.step4.dataInit(params)
        this.currentTab = 3
      },

      // handler
      nextStep () {
        if (this.currentTab < 2) {
          this.currentTab += 1
        }
      },
      prevStep () {
        if (this.currentTab > 0) {
          this.currentTab -= 1
        }
      },
      finish () {
        this.currentTab = 0
      }
    }
  }
</script>

<style lang="scss" scoped>
  .steps {
    max-width: 750px;
    margin: 16px auto;
  }
</style>