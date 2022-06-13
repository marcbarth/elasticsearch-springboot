FROM docker.elastic.co/elasticsearch/elasticsearch:7.17.3
COPY analysis-phonetic-7.17.3.zip /tmp/plugin/
RUN elasticsearch-plugin list | grep -q analysis-phonetic || elasticsearch-plugin install file:///tmp/plugin/analysis-phonetic-7.17.3.zip
