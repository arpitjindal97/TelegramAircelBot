FROM openjdk:latest

RUN apt-get update \
	&& apt-get install -y --no-install-recommends firefox-esr xvfb 

RUN mkdir /arpit
COPY . /arpit/

#RUN wget $(curl -s https://api.github.com/repos/mozilla/geckodriver/releases/0.17.0 | grep -e browser_download_url | grep linux64 | cut -d '"' -f 4)
RUN wget https://github.com/mozilla/geckodriver/releases/download/v0.17.0/geckodriver-v0.17.0-linux64.tar.gz

RUN tar -xzvf geckodriver-*-linux64.tar.gz
RUN cp geckodriver /arpit/

RUN chmod +x /arpit/entrypoint.sh
CMD bash /arpit/entrypoint.sh
#ENTRYPOINT ["/arpit/entrypoint.sh"]
