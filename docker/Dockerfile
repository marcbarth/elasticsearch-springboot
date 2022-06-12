FROM docker.elastic.co/elasticsearch/elasticsearch:7.17.3
RUN elasticsearch-plugin list | grep -q analysis-phonetic || elasticsearch-plugin install analysis-phonetic
