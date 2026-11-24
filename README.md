# 面向 CNC 加工场景的 MES 实时监控与报警闭环系统 (Prototype)

本项目是一个面向离散制造（如 CNC 五轴加工）车间痛点开发的轻量级设备监控与报警系统原型。

##  技术栈
*   **边缘采集层**：Python, `asyncua` (OPC UA 协议), `pymysql`
*   **后端服务层**：Java, Spring Boot, Spring Data JPA, MySQL
*   **前端展示层**：Vue3, Element Plus, Axios, Vite

##  核心功能
1.  **真实的底层数据采集**：摒弃随机数模拟，通过 Python 实现 OPC UA Client，按 NodeId 读取机床仿真服务器的真实变量。
2.  **高可用断线重连**：针对车间网络波动，实现了 `Try-Catch` 自动重连机制，保障采集链路不中断。
3.  **全栈实时联动**：后端基于 JPA 提供 RESTful API，前端通过轮询（Polling）机制实现秒级刷新的监控大屏。
4.  **人工与自动报警闭环**：支持设备自动报警及操作工手动上报故障（POST 接口），数据实时落库并追溯。

##  快速启动说明
1. 确保本地 MySQL 运行，并创建 `mes_demo` 数据库。
2. 运行 `mes-backend` (Java) 启动后端服务。
3. 运行 `mes-frontend` (Vue) 启动前端服务。
4. 运行 `opcua_client.py` (Python) 模拟设备上报数据。
