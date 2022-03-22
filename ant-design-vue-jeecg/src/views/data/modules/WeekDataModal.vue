<template>
  <div>
    <a-modal v-model="visible" title="商户数据分析" width="80%">
      <template slot="footer">
        
        <a-button key="submit" type="primary"@click="handleCancel">
          返回
        </a-button>
      </template>

      <div>
        <div class="code">{{merchant_name}}</div>
        <div class="tip">交易金额（元）</div>
        <v-chart :forceFit="true" :height="height" :data="data" :scale="scale">
          <v-tooltip />
          <v-axis />
          <v-legend />
          <v-smooth-line position="month*amount" color="city" shape="smooth" />
          <v-point position="month*amount" color="city" shape="circle" />
        </v-chart> 
        <!-- 
        <line-chart-multid :fields="visitFields" :dataSource="visitInfo"></line-chart-multid>
        -->
        <div class="code">商户号：{{merchant_code}}</div>
      </div>

    </a-modal>
  </div>
</template>
<script>
  
import LineChartMultid from '@/components/chart/LineChartMultid'

export default {
    components: {
        LineChartMultid
    },
  data() {
    return {
      loading: false,
      visible: false,

      data:[],
      height: 400,

      
      scale: [{
        dataKey: 'key',
        min: 0,
        max: 1,
      }],

      merchant_code: '',
      merchant_name: '',
    };
  },
  methods: {
    showModal(list) {
      
      var that = this

      that.merchant_code = list[0].merchantCode
      that.merchant_name = list[0].merchantName

      console.log("接收到的参数：",list)

      const row = []

      list.forEach(element => {
        row.push({
          amount: element.amount,
          city: '交易金额',
          month: element.transDate
        })
      });

      that.data = row
      
      console.info("数据源：",that.data)
       
      that.visible = true;
    },
    handleCancel(e) {
      this.visible = false;
    },
  },
};
</script>

<style lang="scss" >

.tip{
  width: 100%;
  text-align: left;
}
.code{
  width: 100%;
  text-align: center;
}
</style>