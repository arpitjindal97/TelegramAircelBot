FROM openjdk:latest

RUN apt-get update \
	&& apt-get install -y --no-install-recommends firefox-esr xvfb 

RUN mkdir /arpit
COPY . /arpit/

RUN chmod +x /arpit/entrypoint.sh
ENTRYPOINT /arpit/entrypoint.sh
