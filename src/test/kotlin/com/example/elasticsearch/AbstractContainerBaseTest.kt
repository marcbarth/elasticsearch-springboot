package com.example.elasticsearch


import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.testcontainers.elasticsearch.ElasticsearchContainer
import org.testcontainers.utility.DockerImageName

internal abstract class AbstractContainerBaseTest {
    companion object {

        private val customImage = DockerImageName
            .parse("example.com/elasticsearch-with-phonetic-plugin:latest")
            .asCompatibleSubstituteFor("docker.elastic.co/elasticsearch/elasticsearch:7.17.3")

        var elasticsearchContainer: ElasticsearchContainer = ElasticsearchContainer(customImage)

// for fixed Port 9200, but then you don't need to set spring.elasticsearch.rest.uris via DynamicPropertySource
//        var elasticsearchContainer: ElasticsearchContainer = ElasticsearchContainer(customImage).withCreateContainerCmdModifier{cmd -> cmd.withHostConfig(
//        HostConfig().withPortBindings( PortBinding(Ports.Binding.bindPort(9200), ExposedPort(9200))))};

        init {
            elasticsearchContainer.start()
        }

        @DynamicPropertySource
        @JvmStatic
        fun elasticProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.elasticsearch.rest.uris") { elasticsearchContainer!!.httpHostAddress }
        }

    }
}