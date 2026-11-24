import asyncio
from asyncua import Client
import pymysql
from datetime import datetime

# 1. 数据库连接配置（换成你的WAMP密码）
db = pymysql.connect(host='localhost', user='root', password='123456', database='mes_demo', charset='utf8mb4')
cursor = db.cursor()

# 2. OPC UA 服务器地址和变量 NodeId（换成你Prosys里的）
OPC_URL = "opc.tcp://DESKTOP-47K4QO4:53530/OPCUA/SimulationServer"
NODE_SPEED = "ns=3;i=1001"  # 假设这个是转速
NODE_STATUS = "ns=3;i=1002" # 假设这个是状态

async def main():
    print("🚀 正在连接 OPC UA 仿真机床...")
    
    # 工业现场网络会断，所以要加 try...except 做断线重连
    while True:
        try:
            async with Client(url=OPC_URL) as client:
                print("✅ 已连接到机床，开始采集数据！")
                speed_node = client.get_node(NODE_SPEED)
                status_node = client.get_node(NODE_STATUS)
                
                while True:
                    # 读取真实变量数据
                    speed_val = await speed_node.read_value()
                    status_val = await status_node.read_value()
                    
                    # 存入 MySQL
                    now = datetime.now()
                    sql = "INSERT INTO telemetry (device_id, spindle_speed, run_status, collect_time) VALUES (%s, %s, %s, %s)"
                    cursor.execute(sql, (1, int(speed_val), int(status_val), now))
                    db.commit()
                    
                    print(f"[{now.strftime('%H:%M:%S')}] OPC UA读取成功 -> 转速: {int(speed_val)}, 状态: {int(status_val)}")
                    
                    # 每2秒采一次
                    await asyncio.sleep(2)
                    
        except Exception as e:
            print(f"❌ 连接断开或读取失败，5秒后重试。错误信息: {e}")
            await asyncio.sleep(5) # 断线了等5秒再重连

if __name__ == "__main__":
    asyncio.run(main())