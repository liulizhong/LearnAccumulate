#!/bin/bash
for i in atguigu@hadoop102 atguigu@hadoop103 atguigu@hadoop104
do
        echo "================           $i             ================"
        /opt/module/kafka_2.11-0.11.0.2/bin/kafka-server-start.sh /opt/module/kafka_2.11-0.11.0.2/config/server.properties &
        /opt/module/kafka_2.11-0.11.0.2/bin/kafka-server-start.sh /opt/module/kafka_2.11-0.11.0.2/config/server.properties'
##      ssh $i '/opt/module/kafka_2.11-0.11.0.2/bin/kafka-server-stop.sh /opt/module/kafka_2.11-0.11.0.2/config/server.properties'   ## kafkastop.sh
done
