ARG ZULU_JRE_VERSION=21.0.3-jre
ARG UBUNTU_VERSION=24.04

ARG TIMEZONE="Europe/Zurich"

ARG APP_DIST_NAME=internetbox-reboot-bot
ARG APP_DIST_DIR=/app/build/install/$APP_DIST_NAME

############### JDK ################
FROM azul/zulu-openjdk:$ZULU_JRE_VERSION AS JDK

############## RUN IMAGE ###############
FROM ubuntu:$UBUNTU_VERSION
ARG TIMEZONE
ARG APP_DIST_NAME
ARG APP_DIST_DIR

# set locale
RUN apt-get update \
    && apt-get install -y language-pack-en language-pack-de \
    && rm -rf /var/lib/apt/lists/* \
    && apt-get clean
ENV LC_ALL en_US.UTF-8
ENV LANG en_US.UTF-8
ENV LANGUAGE en_US.UTF-8

# set timezone
ENV TZ="$TIMEZONE"
ENV DEBIAN_FRONTEND=noninteractive
RUN echo $TZ > /etc/timezone \
    && apt-get update \
    && apt-get install -y tzdata \
    && rm /etc/localtime \
    && ln -snf /usr/share/zoneinfo/$TZ /etc/localtime \
    && dpkg-reconfigure -f noninteractive tzdata \
    && rm -rf /var/lib/apt/lists/* \
    && apt-get clean

# setup jvm
RUN mkdir /usr/lib/jvm
COPY --from=JDK /usr/lib/jvm /usr/lib/jvm
RUN cd /usr/lib/jvm \
    && latest_zulu=$(ls -d zulu* | grep -E '^zulu[0-9]+$' | sort -V | tail -n 1) \
    && ln -sfn "$latest_zulu" zulu-current
RUN chmod -R a+rx /usr/lib/jvm

# Add JDK to the env-properties
ENV JAVA_HOME=/usr/lib/jvm/zulu-current
ENV PATH="$JAVA_HOME/bin:$PATH"

# setup npm required for playwright
RUN apt-get update \
    && apt-get install -y npm \
    && rm -rf /var/lib/apt/lists/* \
    && apt-get clean

# setup required packages for playwright
RUN apt-get update \
    && apt-get install -y libnss3 libnspr4 libgbm1 libasound2 \
    && rm -rf /var/lib/apt/lists/* \
    && apt-get clean

# prepare basedir
RUN mkdir /app \
    && chmod a+rw /app

WORKDIR /app

#optimize boot time https://youtu.be/8SdrYGIM384
RUN java -Xshare:dump
ENV INTERNETBOX_REBOOT_BOT_OPTS="-showversion -Xshare:on"

ENV APP_DIST_NAME=$APP_DIST_NAME

# create a user and group to start the application with a matching env
RUN groupadd botgroup \
    && useradd -r -m -g botgroup -s /sbin/nologin bot \
    && env > /home/bot/.profile

# copy data to target dir
COPY $APP_DIST_DIR /app

RUN cd /app/bin \
    && ln -sfn "$APP_DIST_NAME" launch

RUN chmod -R a+rw /app \
    && chmod +x /app/bin/$APP_DIST_NAME

RUN chown -R bot:botgroup /app

USER bot

# configure app
#VOLUME ["/config-server/data"]
#EXPOSE 8080


ENTRYPOINT ["/app/bin/launch"]
#ENTRYPOINT ["java", "-showversion", "-jar", "$PATIENT_FORMS_JAR"]
