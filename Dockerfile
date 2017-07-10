FROM openjdk:latest

RUN apt-get update \
	&& apt-get install -y --no-install-recommends firefox-esr xvfb 

RUN mkdir /arpit
COPY . /arpit/

ENTRYPOINT /arpit/entrypoint.sh
