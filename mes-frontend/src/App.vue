<template>
  <div class="app-container">
    <h1>📊 智能制造 MES 实时监控看板</h1>

    <div class="card">
      <h2>🏭 设备台账</h2>
      <!-- 使用 Element Plus 的表格组件 -->
      <el-table :data="deviceList" border style="width: 100%">
        <el-table-column prop="id" label="设备ID" width="100" />
        <el-table-column prop="deviceName" label="设备名称" />
        <el-table-column prop="deviceCode" label="设备编号" />
        <el-table-column label="运行状态" width="120">
          <template #default="scope">
            <el-tag :type="scope.row.status === 1 ? 'success' : 'info'">
              {{ scope.row.status === 1 ? '在线' : '离线' }}
            </el-tag>
          </template>
        </el-table-column>
      </el-table>
    </div>
        <!-- 报警记录卡片 -->
    <div class="card" style="margin-top: 20px;">
      <h2>🚨 实时报警记录</h2>
            <!-- 操作栏 -->
      <div style="margin-bottom: 15px; display: flex; gap: 10px; align-items: center;">
        <span style="font-size: 14px; color: #666;">模拟工人上报故障：</span>
        <el-select v-model="testDeviceId" placeholder="选择设备" style="width: 180px;">
          <el-option label="五轴加工中心A (ID:1)" :value="1" />
          <el-option label="车铣复合C (ID:4)" :value="4" />
          <el-option label="数控磨床E (ID:6)" :value="6" />
        </el-select>
        <el-button type="danger" @click="submitAlarm">📢 一键上报报警</el-button>
      </div>
      <el-table :data="alarmList" border style="width: 100%">
        <el-table-column prop="id" label="报警ID" width="100" />
        <el-table-column prop="deviceId" label="设备ID" width="100" />
        <el-table-column prop="alarmCode" label="报警代码" width="150" />
        <el-table-column prop="alarmDesc" label="报警描述" />
        <el-table-column prop="startTime" label="发生时间" />
      </el-table>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const deviceList = ref([])
const alarmList = ref([])

onMounted(() => {
  fetchDevices()
  fetchAlarms() 
})



const fetchAlarms = () =>{
  axios.get('http://localhost:8080/api/alarms')
    .then(response =>{
      alarmList.value = response.data
    })
    .catch(error => {
      console.error("报警请求失败", error)
    })
}

const fetchDevices = () => {
  // 访问你后端的接口
  axios.get('http://localhost:8080/api/devices')
    .then(response => {
      deviceList.value = response.data
    })
    .catch(error => {
      console.error("请求失败，看看后端是不是没启动或者没加跨域。", error)
    })
}
// 1. 定义一个变量，用来记录用户在下拉框里选了哪台设备（默认选1号）
const testDeviceId = ref(1)

// 2. 按钮点击时触发的函数
const submitAlarm = () => {
  // 组装要发给后端的 JSON 数据（字段名必须和 Java 实体类严格对应）
  const alarmData = {
    deviceId: testDeviceId.value,
    alarmCode: 'MANUAL-ERROR',
    alarmDesc: '操作工手动上报: 发现异响'
  }

  // 3. 用 axios.post 发送数据给后端
  axios.post('http://localhost:8080/api/alarms', alarmData)
    .then(response => {
      // 写入成功后的回调
      alert('✅ 上报成功！')
      fetchAlarms() // 立刻刷新一次报警表格，不用等 3 秒的定时器
    })
    .catch(error => {
      // 写入失败后的回调
      alert('❌ 上报失败，请查看控制台')
      console.error("上报失败原因：", error)
    })
}
</script>

<style>
.app-container {
  padding: 20px;
  background-color: #f5f7fa;
  min-height: 100vh;
}
.card {
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}
h1 {
  color: #333;
}
</style>