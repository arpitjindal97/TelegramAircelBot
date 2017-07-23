FROM openjdk:latest

RUN echo 'deb http://mozilla.debian.net/ jessie-backports firefox-release' >> /etc/apt/sources.list.d/debian-mozilla.list
RUN wget mozilla.debian.net/pkg-mozilla-archive-keyring_1.1_all.deb
RUN dpkg -i pkg-mozilla-archive-keyring_1.1_all.deb
RUN apt-get update \
	&& apt-get install -y --no-install-recommends firefox xvfb 

RUN mkdir /arpit
COPY . /arpit/

RUN wget $(curl -s https://api.github.com/repos/mozilla/geckodriver/releases/latest | grep -e browser_download_url | grep linux64 | cut -d '"' -f 4)
RUN tar -xzvf geckodriver-*-linux64.tar.gz
RUN cp geckodriver /arpit/

RUN chmod +x /arpit/entrypoint.sh
CMD bash /arpit/entrypoint.sh
#ENTRYPOINT ["/arpit/entrypoint.sh"]
