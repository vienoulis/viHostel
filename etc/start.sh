#!/bin/bash
cd /Users/roman.lukyanov/IdeaProjects/viHostel && ./gradlew clean jar ||  exit 1

cd etc || exit 1

#java -jar viHostelBot.jar \
java -Dspring.config.location=file:/Users/roman.lukyanov/IdeaProjects/viHostel/etc/application.properties \
                   -cp /Users/roman.lukyanov/IdeaProjects/viHostel/build/libs/viHostelBot.jar \
                   ru.vienoulis.vihostelbot.ViHostelBotApplication