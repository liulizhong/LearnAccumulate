#!/bin/bash
for i in atguigu@hadoop102 atguigu@hadoop103 atguigu@hadoop104
do
        echo "================           $i             ================"
        ssh $i '/opt/module/kafka/bin/kafka-server-start.sh -daemon /opt/module/kafka/config/server.properties'
##      ssh $i '/opt/module/kafka/bin/kafka-server-stop.sh /opt/module/kafka/config/server.properties'   ## kafkastop.sh
done
