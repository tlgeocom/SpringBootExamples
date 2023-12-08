:: 启动jar包
java -javaagent:D:\IdeaProjects\SpringBootExamples\springboot-skywalking\apache-skywalking-java-agent-8.16.0\skywalking-agent\skywalking-agent.jar=agent.service_name=skywalking-demo,collector.backend_service=127.0.0.1:11800 -jar skywalking-demo\target\skywalking-demo.jar

:: 按键继续
pause